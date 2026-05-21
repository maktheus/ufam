"""
generate_examples.py
Project #4 – imagePad4e  |  Processamento Digital de Imagens – UFAM 2026

Implements imagePad4e in Python/NumPy (mirrors the MATLAB function),
generates a test-pattern image equivalent to testpattern1024.tif, and saves
all output images used in the report.
"""

import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
from pathlib import Path


# ---------------------------------------------------------------------------
# imagePad4e  –  Python implementation (mirrors the MATLAB .m function)
# ---------------------------------------------------------------------------

def imagePad4e(f, r, c, padtype='zeros'):
    """
    Pad image f with r rows (top/bottom) and c columns (left/right).

    Parameters
    ----------
    f       : ndarray  Input image (H x W) or (H x W x C).
    r       : int      Rows to add above and below.
    c       : int      Columns to add to the left and right.
    padtype : str      'zeros' (default) or 'replicate'.

    Returns
    -------
    g : ndarray  Padded image of shape (H+2r) x (W+2c) [x C].
    """
    if padtype not in ('zeros', 'replicate'):
        raise ValueError("padtype must be 'zeros' or 'replicate'.")

    if f.ndim == 2:
        M, N = f.shape
        ch = 1
        f3 = f[:, :, np.newaxis]
    else:
        M, N, ch = f.shape
        f3 = f

    newM = M + 2 * r
    newN = N + 2 * c
    g3 = np.zeros((newM, newN, ch), dtype=f.dtype)

    # Place original in centre
    g3[r:r+M, c:c+N, :] = f3

    if padtype == 'replicate':
        # Top and bottom horizontal bands (excluding corners)
        g3[:r,       c:c+N, :] = np.tile(f3[0:1,  :,   :], (r, 1, 1))
        g3[r+M:,     c:c+N, :] = np.tile(f3[-1:,  :,   :], (r, 1, 1))
        # Left and right vertical bands (excluding corners)
        g3[r:r+M, :c,       :] = np.tile(f3[:,  0:1,   :], (1, c, 1))
        g3[r:r+M, c+N:,     :] = np.tile(f3[:, -1:,    :], (1, c, 1))
        # Four corners
        g3[:r,   :c,    :] = f3[0,  0,  :][np.newaxis, np.newaxis, :]
        g3[:r,   c+N:,  :] = f3[0,  -1, :][np.newaxis, np.newaxis, :]
        g3[r+M:, :c,    :] = f3[-1, 0,  :][np.newaxis, np.newaxis, :]
        g3[r+M:, c+N:,  :] = f3[-1, -1, :][np.newaxis, np.newaxis, :]

    return g3[:, :, 0] if ch == 1 else g3


# ---------------------------------------------------------------------------
# Build a 512x512 grayscale test pattern (resembles testpattern1024)
# ---------------------------------------------------------------------------

def make_test_pattern(size=512):
    """Create a multi-region grayscale test pattern."""
    img = np.zeros((size, size), dtype=np.uint8)
    half = size // 2
    q = size // 4

    # --- Quadrant 1 (top-left): vertical sinusoidal stripes ----------------
    x = np.linspace(0, 4 * np.pi, half)
    stripe = ((np.sin(x) + 1) / 2 * 255).astype(np.uint8)
    img[:half, :half] = np.tile(stripe, (half, 1))

    # --- Quadrant 2 (top-right): horizontal gray gradient ------------------
    grad = np.linspace(0, 255, half, dtype=np.uint8)
    img[:half, half:] = np.tile(grad[:, np.newaxis], (1, half))

    # --- Quadrant 3 (bottom-left): checkerboard ----------------------------
    cs = size // 32          # cell size in pixels
    for row in range(half, size):
        for col in range(0, half):
            if ((row // cs) + (col // cs)) % 2 == 0:
                img[row, col] = 255

    # --- Quadrant 4 (bottom-right): concentric circles / rings ------------
    cx, cy = 3 * q, 3 * q
    Y, X = np.ogrid[:size, :size]
    dist = np.sqrt((X - cx)**2 + (Y - cy)**2)
    img[half:, half:] = np.clip((np.cos(dist * 0.18) + 1) / 2 * 255, 0, 255
                                 ).astype(np.uint8)[half:, half:]

    # --- Thin white border around each quadrant ----------------------------
    img[half-1:half+1, :] = 200
    img[:, half-1:half+1] = 200

    return img


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

def main():
    out = Path('images')
    out.mkdir(exist_ok=True)

    # --- Build / load test image -------------------------------------------
    tif_path = Path('testpattern1024.tif')
    if tif_path.exists():
        from PIL import Image
        f = np.array(Image.open(tif_path).convert('L'))
        print(f'Loaded {tif_path}  shape={f.shape}')
    else:
        print('testpattern1024.tif not found – generating synthetic test pattern.')
        f = make_test_pattern(512)

    # Save original
    from PIL import Image
    Image.fromarray(f).save(out / 'original.png')

    r, c = 80, 80      # padding sizes

    # --- Apply both pad types -----------------------------------------------
    g_zeros     = imagePad4e(f, r, c, 'zeros')
    g_replicate = imagePad4e(f, r, c, 'replicate')

    Image.fromarray(g_zeros).save(out / 'zeros_padded.png')
    Image.fromarray(g_replicate).save(out / 'replicate_padded.png')

    # --- Figure 1: side-by-side comparison ----------------------------------
    fig, axes = plt.subplots(1, 3, figsize=(14, 5))
    titles = [
        f'(a) Original\n{f.shape[0]}×{f.shape[1]}',
        f'(b) Zero Padding\n{g_zeros.shape[0]}×{g_zeros.shape[1]}',
        f'(c) Replicate Padding\n{g_replicate.shape[0]}×{g_replicate.shape[1]}',
    ]
    for ax, img, title in zip(axes, [f, g_zeros, g_replicate], titles):
        ax.imshow(img, cmap='gray', vmin=0, vmax=255)
        ax.set_title(title, fontsize=12)
        ax.axis('off')

    # Draw red rectangle to show padding region
    for ax, img in zip(axes[1:], [g_zeros, g_replicate]):
        H, W = img.shape
        rect = mpatches.Rectangle((c-0.5, r-0.5), W-2*c, H-2*r,
                                   linewidth=2, edgecolor='red',
                                   facecolor='none', linestyle='--')
        ax.add_patch(rect)

    fig.suptitle('Project #4 – imagePad4e: padding comparison (r=c=80)',
                 fontsize=13, fontweight='bold', y=1.01)
    fig.tight_layout()
    fig.savefig(out / 'comparison.png', dpi=150, bbox_inches='tight')
    plt.close(fig)

    # --- Figure 2: detail crop showing padding border -----------------------
    crop_r = slice(r-30, r+50)
    crop_c = slice(c-30, c+50)

    fig2, axes2 = plt.subplots(1, 2, figsize=(10, 4))
    for ax, img, label in zip(axes2,
                               [g_zeros, g_replicate],
                               ['Zero Padding – border detail',
                                'Replicate Padding – border detail']):
        crop = img[crop_r, crop_c]
        ax.imshow(crop, cmap='gray', vmin=0, vmax=255,
                  interpolation='nearest')
        ax.axhline(30 - 0.5, color='red', linewidth=1.5, linestyle='--',
                   label='Original border')
        ax.axvline(30 - 0.5, color='red', linewidth=1.5, linestyle='--')
        ax.set_title(label, fontsize=11)
        ax.axis('off')

    fig2.suptitle('Border detail (zoomed) – red dashes = original image edge',
                  fontsize=11)
    fig2.tight_layout()
    fig2.savefig(out / 'border_detail.png', dpi=150, bbox_inches='tight')
    plt.close(fig2)

    print(f'Images saved to {out}/')
    print(f'  original.png       {f.shape}')
    print(f'  zeros_padded.png   {g_zeros.shape}')
    print(f'  replicate_padded.png {g_replicate.shape}')
    print(f'  comparison.png')
    print(f'  border_detail.png')


if __name__ == '__main__':
    main()
