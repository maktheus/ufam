"""
generate_project9.py
PDI Project 7 - Segmentation: extract boundaries of the 2 largest blobs.

Pipeline per image:
  1. Convert RGB -> grayscale
  2. Otsu thresholding  -> binary mask
  3. Morphological opening (remove small noise)
  4. Label connected components
  5. Keep 2 largest components
  6. Boundary extraction: boundary = blob XOR erode(blob)
  7. Overlay on original image
"""

import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
from PIL import Image
from scipy import ndimage
import os

SRC = "images project #7"
OUT = "imgs_result"
os.makedirs(OUT, exist_ok=True)

COLORS = [(255, 50, 50), (50, 200, 255)]   # red for blob1, cyan for blob2

# ── helpers ──────────────────────────────────────────────────────────────────

def otsu_threshold(gray):
    hist, bins = np.histogram(gray.flatten(), bins=256, range=(0, 256))
    hist = hist.astype(np.float64)
    total = hist.sum()
    best_T, best_var = 0, 0.0
    w0, sum0 = 0.0, 0.0
    total_sum = np.dot(np.arange(256), hist)
    for t in range(256):
        w0 += hist[t]
        if w0 == 0:
            continue
        w1 = total - w0
        if w1 == 0:
            break
        sum0 += t * hist[t]
        m0 = sum0 / w0
        m1 = (total_sum - sum0) / w1
        var = w0 * w1 * (m0 - m1) ** 2
        if var > best_var:
            best_var = var
            best_T = t
    return best_T

def extract_boundary(mask):
    struct = ndimage.generate_binary_structure(2, 1)
    eroded = ndimage.binary_erosion(mask, structure=struct, iterations=2)
    return mask & ~eroded

def save(fig, name):
    path = os.path.join(OUT, name)
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"  saved {name}")

# ── process each image ───────────────────────────────────────────────────────

image_names = ["Imagem1.jpg", "Imagem2.jpg", "Imagem3.jpg"]

all_results = []

for img_name in image_names:
    print(f"\nProcessing {img_name} ...")
    base = img_name.replace(".jpg", "").lower()

    # 1. Load
    rgb = np.array(Image.open(os.path.join(SRC, img_name)))
    gray = np.array(Image.open(os.path.join(SRC, img_name)).convert("L"))

    # 2. Otsu threshold (objects darker than background? try both polarities)
    T = otsu_threshold(gray)
    mask_dark = gray < T    # dark objects on light background
    mask_light = gray > T   # light objects on dark background

    # Blobs are the minority class (smaller foreground fraction)
    frac_dark = mask_dark.mean()
    frac_light = mask_light.mean()
    if frac_dark <= frac_light:
        mask = mask_dark
        polarity = "dark"
    else:
        mask = mask_light
        polarity = "light"

    print(f"  Otsu T={T}  polarity={polarity}  foreground={mask.mean()*100:.1f}%")

    # 3. Morphological cleaning: opening to remove small noise
    struct = ndimage.generate_binary_structure(2, 2)
    mask_clean = ndimage.binary_opening(mask, structure=struct, iterations=3)
    mask_clean = ndimage.binary_closing(mask_clean, structure=struct, iterations=3)

    # 4. Label connected components
    labeled, n_components = ndimage.label(mask_clean)
    print(f"  Components after cleaning: {n_components}")

    # 5. Keep 2 largest
    if n_components == 0:
        print("  WARNING: no components found, using raw mask")
        mask_clean = mask
        labeled, n_components = ndimage.label(mask_clean)

    sizes = ndimage.sum(mask_clean, labeled, range(1, n_components + 1))
    sorted_idx = np.argsort(sizes)[::-1]  # largest first
    top2_labels = sorted_idx[:2] + 1      # labels are 1-indexed

    print(f"  Top-2 blob sizes: {[int(sizes[i-1]) for i in top2_labels]} px")

    # 6. Boundary extraction for each blob
    blob_masks = []
    boundaries = []
    for lbl in top2_labels:
        blob = (labeled == lbl)
        blob_masks.append(blob)
        boundaries.append(extract_boundary(blob))

    # 7. Build overlay image
    overlay = rgb.copy()
    for bnd, color in zip(boundaries, COLORS):
        overlay[bnd] = color

    all_results.append({
        "name": img_name,
        "base": base,
        "rgb": rgb,
        "gray": gray,
        "T": T,
        "mask": mask,
        "mask_clean": mask_clean,
        "labeled": labeled,
        "blob_masks": blob_masks,
        "boundaries": boundaries,
        "overlay": overlay,
        "sizes": [int(sizes[i-1]) for i in top2_labels],
        "polarity": polarity,
    })

    # --- per-image result figure ---
    fig, axes = plt.subplots(2, 3, figsize=(15, 10))
    axes[0, 0].imshow(rgb); axes[0, 0].set_title("Original"); axes[0, 0].axis("off")
    axes[0, 1].imshow(gray, cmap="gray"); axes[0, 1].set_title(f"Escala de cinza"); axes[0, 1].axis("off")
    axes[0, 2].imshow(mask, cmap="gray"); axes[0, 2].set_title(f"Otsu T={T} ({polarity})"); axes[0, 2].axis("off")
    axes[1, 0].imshow(mask_clean, cmap="gray"); axes[1, 0].set_title("Apos abertura+fechamento morfologico"); axes[1, 0].axis("off")
    # label map with color
    label_show = np.zeros((*labeled.shape, 3), dtype=np.uint8)
    for j, (lbl_id, color) in enumerate(zip(top2_labels, COLORS)):
        label_show[labeled == lbl_id] = color
    axes[1, 1].imshow(label_show); axes[1, 1].set_title(f"2 maiores blobs\n{[s for s in [int(sizes[i-1]) for i in top2_labels]]} px"); axes[1, 1].axis("off")
    axes[1, 2].imshow(overlay); axes[1, 2].set_title("Contornos sobrepostos"); axes[1, 2].axis("off")
    fig.suptitle(f"Projeto 7 - Segmentacao: {img_name}", fontsize=13)
    plt.tight_layout()
    save(fig, f"{base}_pipeline.png")

    # --- boundary overlay close-up ---
    fig, axes = plt.subplots(1, 2, figsize=(12, 6))
    axes[0].imshow(rgb); axes[0].set_title("Original")
    axes[0].axis("off")
    axes[1].imshow(overlay); axes[1].set_title(f"Contornos dos 2 maiores blobs\nBlob1={int(sizes[top2_labels[0]-1])}px  Blob2={int(sizes[top2_labels[1]-1])}px")
    axes[1].axis("off")
    patches = [mpatches.Patch(color=np.array(c)/255, label=f"Blob {j+1}") for j, c in enumerate(COLORS)]
    axes[1].legend(handles=patches, loc="lower right")
    fig.suptitle(f"{img_name} - Fronteiras dos 2 maiores blobs", fontsize=12)
    plt.tight_layout()
    save(fig, f"{base}_boundary.png")

# ── summary: all 3 images side by side ───────────────────────────────────────
fig, axes = plt.subplots(2, 3, figsize=(18, 12))
for col, res in enumerate(all_results):
    axes[0, col].imshow(res["rgb"]); axes[0, col].set_title(res["name"]); axes[0, col].axis("off")
    axes[1, col].imshow(res["overlay"]); axes[1, col].axis("off")
    axes[1, col].set_title(f"Contornos  T={res['T']}\n{res['sizes']} px")

axes[0, 0].set_ylabel("Original", fontsize=12)
axes[1, 0].set_ylabel("Segmentado", fontsize=12)
fig.suptitle("Projeto 7 - Segmentacao: 2 maiores blobs (todas as imagens)", fontsize=14)
plt.tight_layout()
save(fig, "summary_all.png")

print("\nDone.")
