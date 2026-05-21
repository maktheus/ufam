"""
generate_project6.py
Project #6 – twodSFilter & medianSFilter  |  PDI – UFAM 2026

Python implementation of both filters (mirrors the MATLAB .m functions),
applies them to Fig3.37(a).jpg, and saves all output images for the report.
"""

import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from pathlib import Path
from PIL import Image
import sys
sys.path.insert(0, str(Path(__file__).parent.parent))


# ---------------------------------------------------------------------------
# Utility: replicate padding  (from Project #4)
# ---------------------------------------------------------------------------

def imagePad4e(f, r, c, padtype='zeros'):
    if padtype not in ('zeros', 'replicate'):
        raise ValueError("padtype must be 'zeros' or 'replicate'.")
    squeeze = f.ndim == 2
    if squeeze:
        f = f[:, :, np.newaxis]
    M, N, ch = f.shape
    g = np.zeros((M + 2*r, N + 2*c, ch), dtype=f.dtype)
    g[r:r+M, c:c+N, :] = f
    if padtype == 'replicate':
        g[:r,      c:c+N, :] = np.tile(f[0:1,   :,  :], (r, 1, 1))
        g[r+M:,    c:c+N, :] = np.tile(f[-1:,   :,  :], (r, 1, 1))
        g[r:r+M,   :c,    :] = np.tile(f[:,  0:1,   :], (1, c, 1))
        g[r:r+M,   c+N:,  :] = np.tile(f[:, -1:,    :], (1, c, 1))
        g[:r,      :c,    :] = f[0,   0,  :]
        g[:r,      c+N:,  :] = f[0,  -1,  :]
        g[r+M:,    :c,    :] = f[-1,  0,  :]
        g[r+M:,    c+N:,  :] = f[-1, -1,  :]
    return g[:, :, 0] if squeeze else g


# ---------------------------------------------------------------------------
# twodSFilter  –  2-D spatial linear filtering
# ---------------------------------------------------------------------------

def twodSFilter(f, w):
    """
    2-D spatial filtering (correlation) with replicate padding.

    Parameters
    ----------
    f : ndarray  Input image (H x W or H x W x C), any dtype.
    w : ndarray  Filter kernel (Kh x Kw), will be normalised to sum=1.

    Returns
    -------
    g : ndarray  float64 in [0,1], same spatial size as f.
    """
    # Scale to [0,1]
    f_d = f.astype(np.float64)
    fmax = f_d.max()
    if fmax > 0:
        f_d /= fmax

    # Handle colour images
    if f_d.ndim == 3:
        return np.stack([twodSFilter(f_d[:, :, ch], w)
                         for ch in range(f_d.shape[2])], axis=2)

    Kh, Kw = w.shape
    r, c = Kh // 2, Kw // 2

    # Replicate padding
    fp = imagePad4e(f_d, r, c, 'replicate').astype(np.float64)

    # Normalise kernel
    w_norm = w.astype(np.float64) / w.sum()

    # 2-D correlation using scipy
    from scipy.signal import correlate2d
    g = correlate2d(fp, w_norm, mode='valid')

    return np.clip(g, 0.0, 1.0)


# ---------------------------------------------------------------------------
# medianSFilter  –  2-D spatial median filtering
# ---------------------------------------------------------------------------

def medianSFilter(f, w):
    """
    2-D median filtering with replicate padding.

    Parameters
    ----------
    f : ndarray  Input image (H x W), any dtype.
    w : int      Neighbourhood size (odd), e.g. 3 → 3x3 window.

    Returns
    -------
    g : ndarray  float64 in [0,1], same spatial size as f.
    """
    # Scale to [0,1]
    f_d = f.astype(np.float64)
    fmax = f_d.max()
    if fmax > 0:
        f_d /= fmax

    if f_d.ndim == 3:
        return np.stack([medianSFilter(f_d[:, :, ch], w)
                         for ch in range(f_d.shape[2])], axis=2)

    M, N = f_d.shape
    r = w // 2
    fp = imagePad4e(f_d, r, r, 'replicate').astype(np.float64)

    g = np.zeros((M, N), dtype=np.float64)
    for i in range(M):
        for j in range(N):
            patch = fp[i:i+w, j:j+w]
            g[i, j] = np.median(patch)

    return np.clip(g, 0.0, 1.0)


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

def main():
    out = Path('images')
    out.mkdir(exist_ok=True)

    # Load image
    img_path = Path('..') / 'Fig3.37(a).jpg'
    f = np.array(Image.open(img_path).convert('L'))
    print(f'Loaded {img_path}  shape={f.shape}')
    Image.fromarray(f).save(out / 'original.png')

    # --- Averaging filter (low-pass) ----------------------------------------
    avg_results = {}
    for n in [3, 11, 21]:
        w = np.ones((n, n))
        print(f'  twodSFilter {n}x{n} ...', end='', flush=True)
        g = twodSFilter(f, w)
        avg_results[n] = g
        img_out = (g * 255).astype(np.uint8)
        Image.fromarray(img_out).save(out / f'avg_{n}x{n}.png')
        print(' done')

    # --- Median filter -------------------------------------------------------
    print('  medianSFilter 3x3 ...', end='', flush=True)
    g_med = medianSFilter(f, 3)
    img_med = (g_med * 255).astype(np.uint8)
    Image.fromarray(img_med).save(out / 'median_3x3.png')
    print(' done')

    # --- Figure 1: averaging comparison (1 row, 4 cols) ---------------------
    fig, axes = plt.subplots(1, 4, figsize=(16, 4.5))
    titles = ['(a) Original\n455×440',
              '(b) Avg 3×3',
              '(c) Avg 11×11',
              '(d) Avg 21×21']
    imgs = [f.astype(np.float64)/255,
            avg_results[3], avg_results[11], avg_results[21]]
    for ax, img, title in zip(axes, imgs, titles):
        ax.imshow(img, cmap='gray', vmin=0, vmax=1)
        ax.set_title(title, fontsize=11)
        ax.axis('off')
    fig.suptitle('twodSFilter — Averaging (Low-Pass) Filter on Fig 3.37(a)',
                 fontsize=12, fontweight='bold')
    fig.tight_layout()
    fig.savefig(out / 'comparison_lowpass.png', dpi=150, bbox_inches='tight')
    plt.close(fig)

    # --- Figure 2: median comparison ----------------------------------------
    fig2, axes2 = plt.subplots(1, 2, figsize=(10, 4.5))
    for ax, img, title in zip(axes2,
                               [f.astype(np.float64)/255, g_med],
                               ['(a) Original', '(b) Median 3×3']):
        ax.imshow(img, cmap='gray', vmin=0, vmax=1)
        ax.set_title(title, fontsize=12)
        ax.axis('off')
    fig2.suptitle('medianSFilter — Median Filter (3×3) on Fig 3.37(a)',
                  fontsize=12, fontweight='bold')
    fig2.tight_layout()
    fig2.savefig(out / 'comparison_median.png', dpi=150, bbox_inches='tight')
    plt.close(fig2)

    # --- Figure 3: side-by-side all five results ----------------------------
    fig3, axes3 = plt.subplots(1, 5, figsize=(20, 4.5))
    all_imgs  = [f.astype(np.float64)/255,
                 avg_results[3], avg_results[11], avg_results[21], g_med]
    all_titles = ['Original',
                  'Avg 3×3', 'Avg 11×11', 'Avg 21×21',
                  'Median 3×3']
    for ax, img, title in zip(axes3, all_imgs, all_titles):
        ax.imshow(img, cmap='gray', vmin=0, vmax=1)
        ax.set_title(title, fontsize=10)
        ax.axis('off')
    fig3.suptitle('Project #6 – All Results: Fig 3.37(a)',
                  fontsize=12, fontweight='bold')
    fig3.tight_layout()
    fig3.savefig(out / 'all_results.png', dpi=150, bbox_inches='tight')
    plt.close(fig3)

    # --- Figure 4: intensity profile (horizontal slice at row mid) ----------
    row = f.shape[0] // 2
    fig4, axes4 = plt.subplots(1, 3, figsize=(15, 3.5))
    cols_labels = [('Avg 3×3',  avg_results[3],  'tab:blue'),
                   ('Avg 11×11', avg_results[11], 'tab:orange'),
                   ('Avg 21×21', avg_results[21], 'tab:green')]
    for ax, (lbl, result, color) in zip(axes4, cols_labels):
        ax.plot(f[row, :].astype(np.float64)/255,
                color='gray', linewidth=0.8, label='Original', alpha=0.7)
        ax.plot(result[row, :], color=color, linewidth=1.2, label=lbl)
        ax.set_title(f'Profile row {row} – {lbl}', fontsize=10)
        ax.set_xlabel('Column'); ax.set_ylabel('Intensity [0,1]')
        ax.legend(fontsize=8); ax.set_ylim(-0.05, 1.05)
        ax.grid(True, alpha=0.3)
    fig4.suptitle('Horizontal intensity profiles – averaging filters',
                  fontsize=11, fontweight='bold')
    fig4.tight_layout()
    fig4.savefig(out / 'profiles.png', dpi=150, bbox_inches='tight')
    plt.close(fig4)

    print(f'\nAll images saved to {out}/')


if __name__ == '__main__':
    main()
