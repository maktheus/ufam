"""
frequency_filters.py
PDI - Projeto 7: Filtragem no dominio da frequencia
====================================================
Porte para Python do projeto MATLAB (main_project9.m + highEnphasisFilt.m).

Implementa:
  - Filtro Butterworth passa-baixa (PB) e passa-alta (PA)
  - Filtro de alta enfase (HEF): H_HFE = a + b * H_PA
  - Equalizacao de histograma
e gera todas as figuras de resultado em imgs_result/.

Uso:
    python frequency_filters.py                # roda partes (a), (b) e (c)
    python frequency_filters.py --part a       # roda apenas a parte (a)
    python frequency_filters.py --show         # tambem abre as janelas (GUI)

Imagens de entrada esperadas:
    imgs_orig/woman.tif
    imgs_orig/chestXray.tif
"""

from __future__ import annotations

import argparse
import os

import matplotlib

import numpy as np
from PIL import Image

OUT = "imgs_result"
ORIG = "imgs_orig"


# ============================================================================
# Nucleo: filtros no dominio da frequencia
# ============================================================================
def load_gray(path: str) -> np.ndarray:
    """Carrega imagem em tons de cinza como matriz float64."""
    return np.array(Image.open(path).convert("L"), dtype=np.float64)


def _distance_grid(rows: int, cols: int) -> np.ndarray:
    """Matriz D(u,v) = distancia ao centro do espectro (ja com fftshift)."""
    u = np.fft.fftshift(np.fft.fftfreq(rows) * rows)
    v = np.fft.fftshift(np.fft.fftfreq(cols) * cols)
    V, U = np.meshgrid(v, u)
    return np.sqrt(U ** 2 + V ** 2)


def butterworth_lp(rows: int, cols: int, D0: float, n: int) -> np.ndarray:
    """Filtro Butterworth passa-baixa:  H = 1 / (1 + (D/D0)^(2n))."""
    D = _distance_grid(rows, cols)
    return 1.0 / (1.0 + (D / D0) ** (2 * n))


def butterworth_hp(rows: int, cols: int, D0: float, n: int) -> np.ndarray:
    """Filtro Butterworth passa-alta:  H_PA = 1 - H_PB."""
    return 1.0 - butterworth_lp(rows, cols, D0, n)


def high_emphasis_filter(a: float, b: float, f: np.ndarray, D0: float, n: int):
    """
    Filtro de alta enfase (High-Emphasis Filter).

        H_HFE = a + b * H_PA(Butterworth)

    Retorna (H, g) onde H e o filtro e g e a imagem filtrada em uint8 [0,255].
    """
    f = np.asarray(f, dtype=np.float64)
    rows, cols = f.shape
    H = a + b * butterworth_hp(rows, cols, D0, n)
    g_raw = apply_filter(f, H)
    return H, normalize_uint8(g_raw)


def apply_filter(f: np.ndarray, H: np.ndarray) -> np.ndarray:
    """Aplica um filtro H (centralizado) a imagem f via FFT 2D."""
    F = np.fft.fftshift(np.fft.fft2(f))
    g = np.real(np.fft.ifft2(np.fft.ifftshift(H * F)))
    return g


def fft_spectrum(f: np.ndarray):
    """Espectro de Fourier centralizado e sua versao log para exibicao."""
    F = np.fft.fftshift(np.fft.fft2(f))
    return F, np.log1p(np.abs(F))


def histeq(img: np.ndarray) -> np.ndarray:
    """Equalizacao de histograma (imagem uint8 -> uint8)."""
    img = np.clip(img, 0, 255).astype(np.uint8)
    hist, _ = np.histogram(img.flatten(), 256, [0, 256])
    cdf = hist.cumsum()
    cdf_min = cdf[cdf > 0].min()
    lut = np.round((cdf - cdf_min) / (img.size - cdf_min) * 255).astype(np.uint8)
    return lut[img]


def normalize_uint8(img: np.ndarray) -> np.ndarray:
    """Normaliza para [0,255] em uint8."""
    lo, hi = img.min(), img.max()
    if hi == lo:
        return np.zeros_like(img, dtype=np.uint8)
    return ((img - lo) / (hi - lo) * 255).astype(np.uint8)


# ============================================================================
# Geracao das figuras de resultado
# ============================================================================
def _save(plt, fig, name: str):
    fig.savefig(os.path.join(OUT, name), dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"  salvo {OUT}/{name}")


def part_a(plt, D0: int = 60, n: int = 2):
    """Parte (a): woman.tif - espectro e filtros Butterworth PB/PA."""
    print("=== Parte (a): woman.tif - Butterworth PB e PA ===")
    woman = load_gray(os.path.join(ORIG, "woman.tif"))
    rows, cols = woman.shape

    H_lp = butterworth_lp(rows, cols, D0, n)
    H_hp = butterworth_hp(rows, cols, D0, n)

    _, spec_orig = fft_spectrum(woman)
    g_lp = apply_filter(woman, H_lp)
    g_hp = apply_filter(woman, H_hp)
    _, spec_lp = fft_spectrum(g_lp)

    # Painel principal
    fig, ax = plt.subplots(2, 3, figsize=(14, 9))
    ax[0, 0].imshow(woman, cmap="gray"); ax[0, 0].set_title("(a) Imagem original"); ax[0, 0].axis("off")
    ax[0, 1].imshow(spec_orig, cmap="gray"); ax[0, 1].set_title("Espectro centralizado"); ax[0, 1].axis("off")
    ax[0, 2].imshow(normalize_uint8(H_lp), cmap="gray"); ax[0, 2].set_title(f"Filtro PB Butterworth\n$D_0={D0}$, $n={n}$"); ax[0, 2].axis("off")
    ax[1, 0].imshow(normalize_uint8(g_lp), cmap="gray"); ax[1, 0].set_title("Filtrada (Passa-Baixa)"); ax[1, 0].axis("off")
    ax[1, 1].imshow(spec_lp, cmap="gray"); ax[1, 1].set_title("Espectro apos PB"); ax[1, 1].axis("off")
    ax[1, 2].imshow(normalize_uint8(g_hp), cmap="gray"); ax[1, 2].set_title("Filtrada (Passa-Alta)"); ax[1, 2].axis("off")
    fig.suptitle("Projeto 7 - Parte (a): Filtro Butterworth PB e PA", fontsize=13)
    fig.tight_layout()
    _save(plt, fig, "part_a_results.png")

    # Filtros 2D
    for H, tag, titulo in [(H_lp, "lp", "Passa-Baixa"), (H_hp, "hp", "Passa-Alta")]:
        fig, a2 = plt.subplots(figsize=(6, 5))
        im = a2.imshow(H, cmap="hot", vmin=0, vmax=1)
        a2.set_title(f"Filtro {titulo} Butterworth 2D\n$D_0={D0}$, $n={n}$")
        fig.colorbar(im, ax=a2); a2.axis("off")
        _save(plt, fig, f"part_a_{tag}_2d.png")

    # Filtros 3D
    r3 = c3 = 128
    H_lp_s = butterworth_lp(r3, c3, D0 * r3 / rows, n)
    H_hp_s = butterworth_hp(r3, c3, D0 * r3 / rows, n)
    uu = np.linspace(-r3 // 2, r3 // 2, r3)
    vv = np.linspace(-c3 // 2, c3 // 2, c3)
    UU, VV = np.meshgrid(vv, uu)
    for H, tag, titulo in [(H_lp_s, "lp", "PB"), (H_hp_s, "hp", "PA")]:
        fig = plt.figure(figsize=(7, 5))
        a3 = fig.add_subplot(111, projection="3d")
        a3.plot_surface(UU, VV, H, cmap="viridis", linewidth=0, antialiased=True)
        a3.set_title(f"Filtro {titulo} Butterworth 3D\n$D_0={D0}$, $n={n}$")
        a3.set_xlabel("u"); a3.set_ylabel("v"); a3.set_zlabel("H(u,v)")
        _save(plt, fig, f"part_a_{tag}_3d.png")


def part_b(plt, a: float = 0.5, b: float = 2.0, D0: int = 60, n: int = 2):
    """Parte (b): filtro de alta enfase (HEF) - visualizacao 2D/3D + demo."""
    print("=== Parte (b): High-Emphasis Filter (HEF) ===")
    woman = load_gray(os.path.join(ORIG, "woman.tif"))
    rows, cols = woman.shape

    H_hfe, g_hfe = high_emphasis_filter(a, b, woman, D0, n)
    print(f"  a={a}  b={b}  D0={D0}  n={n} | H_HFE: min={H_hfe.min():.3f} max={H_hfe.max():.3f}")

    # HEF 2D
    r3 = c3 = 128
    H_hfe_s = a + b * butterworth_hp(r3, c3, D0 * r3 / rows, n)
    fig, ax = plt.subplots(1, 2, figsize=(12, 5))
    im0 = ax[0].imshow(H_hfe, cmap="hot"); ax[0].set_title(f"HEF 2D: a={a}, b={b}, $D_0={D0}$, $n={n}$")
    fig.colorbar(im0, ax=ax[0]); ax[0].axis("off")
    ax[1].imshow(H_hfe_s, cmap="hot"); ax[1].set_title("HEF 2D (detalhe central)"); ax[1].axis("off")
    fig.suptitle("Projeto 7 - Parte (b): Filtro de Alta Enfase (HEF)", fontsize=13)
    fig.tight_layout()
    _save(plt, fig, "part_b_hef_2d.png")

    # HEF 3D
    uu = np.linspace(-r3 // 2, r3 // 2, r3)
    vv = np.linspace(-c3 // 2, c3 // 2, c3)
    UU, VV = np.meshgrid(vv, uu)
    fig = plt.figure(figsize=(7, 5))
    a3 = fig.add_subplot(111, projection="3d")
    a3.plot_surface(UU, VV, H_hfe_s, cmap="plasma", linewidth=0, antialiased=True)
    a3.set_title(f"HEF 3D: a={a}, b={b}, $D_0={D0}$, $n={n}$")
    a3.set_xlabel("u"); a3.set_ylabel("v"); a3.set_zlabel("H(u,v)")
    _save(plt, fig, "part_b_hef_3d.png")

    # Demonstracao em woman.tif
    H_hp = butterworth_hp(rows, cols, D0, n)
    g_hp = normalize_uint8(apply_filter(woman, H_hp))
    fig, ax = plt.subplots(1, 3, figsize=(14, 5))
    ax[0].imshow(woman, cmap="gray"); ax[0].set_title("Original"); ax[0].axis("off")
    ax[1].imshow(g_hfe, cmap="gray"); ax[1].set_title(f"Apos HEF (a={a}, b={b})"); ax[1].axis("off")
    ax[2].imshow(g_hp, cmap="gray"); ax[2].set_title("Passa-Alta (referencia)"); ax[2].axis("off")
    fig.suptitle("Parte (b): HEF aplicado a woman.tif", fontsize=13)
    fig.tight_layout()
    _save(plt, fig, "part_b_woman_hef.png")


def part_c(plt, a: float = 0.5, b: float = 2.0, D0: int = 40, n: int = 2):
    """Parte (c): chestXray.tif - HEF seguido de equalizacao de histograma."""
    print("=== Parte (c): chestXray.tif - HEF + equalizacao ===")
    chest = load_gray(os.path.join(ORIG, "chestXray.tif"))

    H_hfe, g_hfe = high_emphasis_filter(a, b, chest, D0, n)
    g_eq = histeq(g_hfe)

    _, spec_orig = fft_spectrum(chest)
    _, spec_filt = fft_spectrum(g_hfe.astype(np.float64))

    # Painel principal
    fig, ax = plt.subplots(2, 3, figsize=(14, 9))
    ax[0, 0].imshow(chest, cmap="gray"); ax[0, 0].set_title("(c) RX de torax - original"); ax[0, 0].axis("off")
    ax[0, 1].imshow(spec_orig, cmap="gray"); ax[0, 1].set_title("Espectro original"); ax[0, 1].axis("off")
    ax[0, 2].imshow(normalize_uint8(H_hfe), cmap="hot"); ax[0, 2].set_title(f"HEF ($D_0={D0}$, a={a}, b={b})"); ax[0, 2].axis("off")
    ax[1, 0].imshow(g_hfe, cmap="gray"); ax[1, 0].set_title("Apos HEF"); ax[1, 0].axis("off")
    ax[1, 1].imshow(spec_filt, cmap="gray"); ax[1, 1].set_title("Espectro apos HEF"); ax[1, 1].axis("off")
    ax[1, 2].imshow(g_eq, cmap="gray"); ax[1, 2].set_title("HEF + equalizacao"); ax[1, 2].axis("off")
    fig.suptitle("Projeto 7 - Parte (c): HEF + Equalizacao em RX de Torax", fontsize=13)
    fig.tight_layout()
    _save(plt, fig, "part_c_chest_results.png")

    # Histogramas
    fig, ax = plt.subplots(1, 3, figsize=(14, 4))
    ax[0].hist(chest.flatten(), bins=256, range=(0, 256), color="steelblue")
    ax[0].set_title("Histograma: original"); ax[0].set_xlabel("Intensidade"); ax[0].set_ylabel("Frequencia")
    ax[1].hist(g_hfe.flatten(), bins=256, range=(0, 256), color="darkorange")
    ax[1].set_title("Histograma: apos HEF"); ax[1].set_xlabel("Intensidade")
    ax[2].hist(g_eq.flatten(), bins=256, range=(0, 256), color="forestgreen")
    ax[2].set_title("Histograma: HEF + histEq"); ax[2].set_xlabel("Intensidade")
    fig.suptitle("Parte (c): Comparacao de histogramas", fontsize=12)
    fig.tight_layout()
    _save(plt, fig, "part_c_histograms.png")

    # Espectros antes/apos
    fig, ax = plt.subplots(1, 2, figsize=(12, 5))
    ax[0].imshow(spec_orig, cmap="gray"); ax[0].set_title("Espectro antes do HEF"); ax[0].axis("off")
    ax[1].imshow(spec_filt, cmap="gray"); ax[1].set_title("Espectro apos HEF"); ax[1].axis("off")
    fig.suptitle("Parte (c): Espectro de Fourier - antes e apos HEF", fontsize=12)
    fig.tight_layout()
    _save(plt, fig, "part_c_spectra_comparison.png")


def main():
    parser = argparse.ArgumentParser(description="PDI Projeto 7 - Filtragem na frequencia")
    parser.add_argument("--part", choices=["a", "b", "c", "all"], default="all",
                        help="qual parte executar (padrao: all)")
    parser.add_argument("--show", action="store_true", help="abre as janelas (modo GUI)")
    args = parser.parse_args()

    if not args.show:
        matplotlib.use("Agg")  # sem display (headless)
    import matplotlib.pyplot as plt  # importado apos definir o backend

    os.makedirs(OUT, exist_ok=True)

    if args.part in ("a", "all"):
        part_a(plt)
    if args.part in ("b", "all"):
        part_b(plt)
    if args.part in ("c", "all"):
        part_c(plt)

    if args.show:
        plt.show()
    print(f"\nConcluido. Figuras em {OUT}/")


if __name__ == "__main__":
    main()
