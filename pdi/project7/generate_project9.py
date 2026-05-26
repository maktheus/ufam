"""
generate_project9.py
PDI Project 9 - Butterworth Filters and High-Emphasis Filter
Generates all result images for the LaTeX report.
"""

import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm
from PIL import Image
import os

OUT = "imgs_result"
os.makedirs(OUT, exist_ok=True)

# ── helpers ──────────────────────────────────────────────────────────────────

def load_gray(path):
    img = Image.open(path).convert("L")
    return np.array(img, dtype=np.float64)

def fft_spectrum(f):
    F = np.fft.fftshift(np.fft.fft2(f))
    return F, np.log1p(np.abs(F))

def butterworth_lp(rows, cols, D0, n):
    u = np.fft.fftfreq(rows) * rows
    v = np.fft.fftfreq(cols) * cols
    U, V = np.meshgrid(v, u)
    D = np.sqrt(U**2 + V**2)
    H = 1.0 / (1.0 + (D / D0) ** (2 * n))
    return np.fft.fftshift(H)

def butterworth_hp(rows, cols, D0, n):
    return 1.0 - butterworth_lp(rows, cols, D0, n)

def apply_filter(f, H):
    F = np.fft.fftshift(np.fft.fft2(f))
    G = H * F
    g = np.real(np.fft.ifft2(np.fft.ifftshift(G)))
    return g

def histeq(img):
    """Histogram equalization."""
    img = np.clip(img, 0, 255).astype(np.uint8)
    hist, bins = np.histogram(img.flatten(), 256, [0, 256])
    cdf = hist.cumsum()
    cdf_min = cdf[cdf > 0].min()
    total = img.size
    lut = np.round((cdf - cdf_min) / (total - cdf_min) * 255).astype(np.uint8)
    return lut[img]

def normalize_display(img):
    lo, hi = img.min(), img.max()
    if hi == lo:
        return np.zeros_like(img, dtype=np.uint8)
    return ((img - lo) / (hi - lo) * 255).astype(np.uint8)

def save(fig, name):
    fig.savefig(os.path.join(OUT, name), dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"  saved {name}")

# ── Part (a) ─────────────────────────────────────────────────────────────────
print("Part (a): woman.tif - Butterworth LP and HP filters")

woman = load_gray("imgs_orig/woman.tif")
rows, cols = woman.shape

D0 = 60
n  = 2

H_lp = butterworth_lp(rows, cols, D0, n)
H_hp = butterworth_hp(rows, cols, D0, n)

_, spec_orig = fft_spectrum(woman)
g_lp = apply_filter(woman, H_lp)
g_hp = apply_filter(woman, H_hp)
_, spec_lp = fft_spectrum(g_lp)
_, spec_hp = fft_spectrum(g_hp)

# figure: original + spectrum + LP result + HP result
fig, axes = plt.subplots(2, 3, figsize=(14, 9))
axes[0, 0].imshow(woman, cmap="gray"); axes[0, 0].set_title("(a) Imagem original"); axes[0, 0].axis("off")
axes[0, 1].imshow(spec_orig, cmap="gray"); axes[0, 1].set_title("Espectro centralizado"); axes[0, 1].axis("off")
axes[0, 2].imshow(normalize_display(H_lp), cmap="gray"); axes[0, 2].set_title(f"Filtro PB Butterworth\n$D_0={D0}$, $n={n}$"); axes[0, 2].axis("off")
axes[1, 0].imshow(normalize_display(g_lp), cmap="gray"); axes[1, 0].set_title("Imagem filtrada (Passa-Baixa)"); axes[1, 0].axis("off")
axes[1, 1].imshow(spec_lp, cmap="gray"); axes[1, 1].set_title("Espectro apos PB"); axes[1, 1].axis("off")
axes[1, 2].imshow(normalize_display(g_hp), cmap="gray"); axes[1, 2].set_title("Imagem filtrada (Passa-Alta)"); axes[1, 2].axis("off")
fig.suptitle("Projeto 9 - Parte (a): Filtro Butterworth PB e PA", fontsize=13)
plt.tight_layout()
save(fig, "part_a_results.png")

# LP filter 2D
fig, ax = plt.subplots(figsize=(6, 5))
im = ax.imshow(H_lp, cmap="hot", vmin=0, vmax=1)
ax.set_title(f"Filtro Passa-Baixa Butterworth 2D\n$D_0={D0}$, $n={n}$")
plt.colorbar(im, ax=ax)
ax.axis("off")
save(fig, "part_a_lp_2d.png")

# HP filter 2D
fig, ax = plt.subplots(figsize=(6, 5))
im = ax.imshow(H_hp, cmap="hot", vmin=0, vmax=1)
ax.set_title(f"Filtro Passa-Alta Butterworth 2D\n$D_0={D0}$, $n={n}$")
plt.colorbar(im, ax=ax)
ax.axis("off")
save(fig, "part_a_hp_2d.png")

# LP filter 3D
r3, c3 = 128, 128
H_lp_small = butterworth_lp(r3, c3, D0 * r3 / rows, n)
H_hp_small = butterworth_hp(r3, c3, D0 * r3 / rows, n)
uu = np.linspace(-r3//2, r3//2, r3)
vv = np.linspace(-c3//2, c3//2, c3)
UU, VV = np.meshgrid(vv, uu)

fig = plt.figure(figsize=(7, 5))
ax = fig.add_subplot(111, projection="3d")
ax.plot_surface(UU, VV, H_lp_small, cmap="viridis", linewidth=0, antialiased=True, alpha=0.9)
ax.set_title(f"Filtro PB Butterworth 3D\n$D_0={D0}$, $n={n}$")
ax.set_xlabel("u"); ax.set_ylabel("v"); ax.set_zlabel("H(u,v)")
save(fig, "part_a_lp_3d.png")

# HP filter 3D
fig = plt.figure(figsize=(7, 5))
ax = fig.add_subplot(111, projection="3d")
ax.plot_surface(UU, VV, H_hp_small, cmap="viridis", linewidth=0, antialiased=True, alpha=0.9)
ax.set_title(f"Filtro PA Butterworth 3D\n$D_0={D0}$, $n={n}$")
ax.set_xlabel("u"); ax.set_ylabel("v"); ax.set_zlabel("H(u,v)")
save(fig, "part_a_hp_3d.png")

# ── Part (b) ─────────────────────────────────────────────────────────────────
print("Part (b): highEnphasisFilt - 2D and 3D visualization")

def high_emphasis_filter(a, b, rows, cols, D0, n):
    H_hp = butterworth_hp(rows, cols, D0, n)
    H_hfe = a + b * H_hp
    return H_hfe

a, b = 0.5, 2.0
H_hfe = high_emphasis_filter(a, b, rows, cols, D0, n)

fig, axes = plt.subplots(1, 2, figsize=(12, 5))
im0 = axes[0].imshow(H_hfe, cmap="hot")
axes[0].set_title(f"HEF 2D: a={a}, b={b}, $D_0={D0}$, $n={n}$")
plt.colorbar(im0, ax=axes[0])
axes[0].axis("off")

H_hfe_small = high_emphasis_filter(a, b, r3, c3, D0 * r3 / rows, n)
axes[1].imshow(H_hfe_small, cmap="hot")
axes[1].set_title("HEF 2D (detalhe central)")
axes[1].axis("off")
fig.suptitle("Projeto 9 - Parte (b): Filtro de Alta Enfase (HEF)", fontsize=13)
plt.tight_layout()
save(fig, "part_b_hef_2d.png")

fig = plt.figure(figsize=(7, 5))
ax = fig.add_subplot(111, projection="3d")
ax.plot_surface(UU, VV, H_hfe_small, cmap="plasma", linewidth=0, antialiased=True, alpha=0.9)
ax.set_title(f"HEF 3D: a={a}, b={b}, $D_0={D0}$, $n={n}$")
ax.set_xlabel("u"); ax.set_ylabel("v"); ax.set_zlabel("H(u,v)")
save(fig, "part_b_hef_3d.png")

# Demonstrate on woman.tif
g_hfe = apply_filter(woman, H_hfe)
fig, axes = plt.subplots(1, 3, figsize=(14, 5))
axes[0].imshow(woman, cmap="gray"); axes[0].set_title("Original"); axes[0].axis("off")
axes[1].imshow(normalize_display(g_hfe), cmap="gray"); axes[1].set_title(f"Apos HEF (a={a}, b={b})"); axes[1].axis("off")
axes[2].imshow(normalize_display(g_hp), cmap="gray"); axes[2].set_title("Passa-Alta (referencia)"); axes[2].axis("off")
fig.suptitle("Parte (b): HEF aplicado a woman.tif", fontsize=13)
plt.tight_layout()
save(fig, "part_b_woman_hef.png")

# ── Part (c) ─────────────────────────────────────────────────────────────────
print("Part (c): chestXray.tif - highEnphasisFilt + histEq")

chest = load_gray("imgs_orig/chestXray.tif")
cr, cc = chest.shape

D0c = 40
nc  = 2
a_c, b_c = 0.5, 2.0

H_hfe_c = high_emphasis_filter(a_c, b_c, cr, cc, D0c, nc)
g_hfe_c = apply_filter(chest, H_hfe_c)
g_hfe_c_norm = normalize_display(g_hfe_c)
g_histeq = histeq(g_hfe_c_norm)

_, spec_chest_orig = fft_spectrum(chest)
_, spec_chest_filt = fft_spectrum(g_hfe_c)

# Main result figure
fig, axes = plt.subplots(2, 3, figsize=(14, 9))
axes[0, 0].imshow(chest, cmap="gray"); axes[0, 0].set_title("(c) RX de torax - original"); axes[0, 0].axis("off")
axes[0, 1].imshow(spec_chest_orig, cmap="gray"); axes[0, 1].set_title("Espectro original"); axes[0, 1].axis("off")
axes[0, 2].imshow(normalize_display(H_hfe_c), cmap="hot"); axes[0, 2].set_title(f"HEF ($D_0={D0c}$, a={a_c}, b={b_c})"); axes[0, 2].axis("off")
axes[1, 0].imshow(g_hfe_c_norm, cmap="gray"); axes[1, 0].set_title("Apos HEF"); axes[1, 0].axis("off")
axes[1, 1].imshow(spec_chest_filt, cmap="gray"); axes[1, 1].set_title("Espectro apos HEF"); axes[1, 1].axis("off")
axes[1, 2].imshow(g_histeq, cmap="gray"); axes[1, 2].set_title("HEF + Equalizacao de histograma"); axes[1, 2].axis("off")
fig.suptitle("Projeto 9 - Parte (c): HEF + Equalizacao em RX de Torax", fontsize=13)
plt.tight_layout()
save(fig, "part_c_chest_results.png")

# Histograms comparison
fig, axes = plt.subplots(1, 3, figsize=(14, 4))
axes[0].hist(chest.flatten(), bins=256, range=(0, 256), color="steelblue", edgecolor="none")
axes[0].set_title("Histograma: original"); axes[0].set_xlabel("Intensidade"); axes[0].set_ylabel("Frequencia")
axes[1].hist(g_hfe_c_norm.flatten(), bins=256, range=(0, 256), color="darkorange", edgecolor="none")
axes[1].set_title("Histograma: apos HEF"); axes[1].set_xlabel("Intensidade")
axes[2].hist(g_histeq.flatten(), bins=256, range=(0, 256), color="forestgreen", edgecolor="none")
axes[2].set_title("Histograma: HEF + histEq"); axes[2].set_xlabel("Intensidade")
fig.suptitle("Parte (c): Comparacao de histogramas", fontsize=12)
plt.tight_layout()
save(fig, "part_c_histograms.png")

# Spectra comparison (before / after HEF)
fig, axes = plt.subplots(1, 2, figsize=(12, 5))
axes[0].imshow(spec_chest_orig, cmap="gray"); axes[0].set_title("Espectro antes do HEF"); axes[0].axis("off")
axes[1].imshow(spec_chest_filt, cmap="gray"); axes[1].set_title("Espectro apos HEF"); axes[1].axis("off")
fig.suptitle("Parte (c): Espectro de Fourier - antes e apos HEF", fontsize=12)
plt.tight_layout()
save(fig, "part_c_spectra_comparison.png")

print("\nAll images saved to", OUT)
