"""
generate_project8.py
PDI Project 8 - Global Thresholding
Generates all result images for the LaTeX report.
"""

import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import os

OUT = "imgs_result"
os.makedirs(OUT, exist_ok=True)

def save(fig, name):
    fig.savefig(os.path.join(OUT, name), dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"  saved {name}")

# ── Global Thresholding algorithm ────────────────────────────────────────────

def global_thresh(f, det_T=0.01):
    f = np.array(f, dtype=np.float64)
    f_min, f_max = f.min(), f.max()
    f_sc = (f - f_min) / (f_max - f_min)

    T = f_sc.mean()
    iters = 0
    history = [T]

    while True:
        iters += 1
        G1 = f_sc[f_sc >  T]
        G2 = f_sc[f_sc <= T]
        m1 = G1.mean() if len(G1) > 0 else 1.0
        m2 = G2.mean() if len(G2) > 0 else 0.0
        T_new = (m1 + m2) / 2.0
        history.append(T_new)
        if abs(T_new - T) < det_T:
            T = T_new
            break
        T = T_new

    g = (f_sc > T).astype(np.uint8) * 255
    return g, T, iters, history, f_sc

# ── Load image ────────────────────────────────────────────────────────────────
print("Loading rice-shaded.tif ...")
img = np.array(Image.open("image.tif"), dtype=np.uint8)
print(f"  Shape: {img.shape}, dtype: {img.dtype}, range: [{img.min()}, {img.max()}]")

# ── Part (b): default detT = 0.01 ─────────────────────────────────────────────
print("\nPart (b): globalThresh with default detT=0.01")

g_def, T_def, it_def, hist_def, f_sc = global_thresh(img, det_T=0.01)

print(f"  T = {T_def:.6f}  |  iterations = {it_def}")
print(f"  White pixels (object): {(g_def > 0).sum()} / {g_def.size} ({100*(g_def>0).mean():.1f}%)")

fig, axes = plt.subplots(1, 3, figsize=(14, 5))
axes[0].imshow(img, cmap="gray", vmin=0, vmax=255)
axes[0].set_title("(b) rice-shaded.tif original")
axes[0].axis("off")

axes[1].hist(f_sc.flatten(), bins=64, color="steelblue", edgecolor="none")
axes[1].axvline(T_def, color="red", linewidth=2, label=f"T = {T_def:.4f}")
axes[1].set_title("Histograma normalizado + limiar")
axes[1].set_xlabel("Intensidade [0,1]")
axes[1].set_ylabel("Frequencia")
axes[1].legend()

axes[2].imshow(g_def, cmap="gray", vmin=0, vmax=255)
axes[2].set_title(f"Resultado: T={T_def:.4f} ({it_def} iter.)")
axes[2].axis("off")

fig.suptitle("Projeto 8 - Parte (b): Limiarizacao Global (detT = 0.01)", fontsize=13)
plt.tight_layout()
save(fig, "part_b_default.png")

# Convergence history
fig, ax = plt.subplots(figsize=(7, 4))
ax.plot(hist_def, "o-", color="steelblue", linewidth=2, markersize=6)
ax.axhline(T_def, color="red", linestyle="--", label=f"T final = {T_def:.4f}")
ax.set_title("Convergencia do limiar (detT = 0.01)")
ax.set_xlabel("Iteracao")
ax.set_ylabel("T")
ax.legend()
ax.grid(True, alpha=0.3)
save(fig, "part_b_convergence.png")

# Show limitation: profile scan to illustrate non-uniform background
fig, axes = plt.subplots(1, 2, figsize=(12, 5))
row_profiles = img[100, :], img[300, :], img[500, :]
axes[0].plot(row_profiles[0], label="Linha 100", alpha=0.8)
axes[0].plot(row_profiles[1], label="Linha 300", alpha=0.8)
axes[0].plot(row_profiles[2], label="Linha 500", alpha=0.8)
axes[0].axhline(T_def * 255 * (img.max() - img.min()) / 255 + img.min(),
               color="red", linestyle="--", linewidth=2, label=f"T (escala original)")
axes[0].set_title("Perfis horizontais (fundo nao uniforme)")
axes[0].set_xlabel("Coluna"); axes[0].set_ylabel("Intensidade")
axes[0].legend(fontsize=9)
axes[0].grid(True, alpha=0.3)

# compute T in original scale
T_orig = T_def * (img.max() - img.min()) + img.min()
axes[1].imshow(img, cmap="gray")
axes[1].axhline(100, color="cyan", alpha=0.6, linewidth=1)
axes[1].axhline(300, color="orange", alpha=0.6, linewidth=1)
axes[1].axhline(500, color="lime", alpha=0.6, linewidth=1)
axes[1].set_title("Linhas de perfil na imagem")
axes[1].axis("off")

fig.suptitle("Parte (b): Evidencia do fundo nao uniforme", fontsize=12)
plt.tight_layout()
save(fig, "part_b_nonuniform_evidence.png")

# ── Part (c): different detT values ──────────────────────────────────────────
print("\nPart (c): variation of detT")

det_T_vals = [0.1, 0.01, 0.001, 0.0001]
results_c = []

for dt in det_T_vals:
    g, T, it, hist, _ = global_thresh(img, det_T=dt)
    results_c.append((dt, g, T, it, hist))
    print(f"  detT={dt:.4f} -> T={T:.6f} | iters={it}")

fig, axes = plt.subplots(2, 4, figsize=(16, 8))
for k, (dt, g, T, it, _) in enumerate(results_c):
    axes[0, k].imshow(g, cmap="gray", vmin=0, vmax=255)
    axes[0, k].set_title(f"detT = {dt}\nT = {T:.4f} | {it} iter.")
    axes[0, k].axis("off")
    axes[1, k].hist(f_sc.flatten(), bins=64, color="steelblue", edgecolor="none", alpha=0.7)
    axes[1, k].axvline(T, color="red", linewidth=2, label=f"T={T:.4f}")
    axes[1, k].set_xlabel("Intensidade")
    axes[1, k].legend(fontsize=8)
    axes[1, k].set_title(f"Hist. detT={dt}")

fig.suptitle("Projeto 8 - Parte (c): Influencia do criterio de parada detT", fontsize=13)
plt.tight_layout()
save(fig, "part_c_detT_comparison.png")

# Convergence curves for all detT
fig, ax = plt.subplots(figsize=(9, 5))
colors = ["royalblue", "darkorange", "forestgreen", "crimson"]
for (dt, g, T, it, hist), col in zip(results_c, colors):
    ax.plot(hist, "o-", color=col, linewidth=2, markersize=5,
            label=f"detT={dt} | T={T:.4f} | {it}it")
ax.set_title("Convergencia do limiar para diferentes detT")
ax.set_xlabel("Iteracao"); ax.set_ylabel("T")
ax.legend(); ax.grid(True, alpha=0.3)
save(fig, "part_c_convergence_all.png")

# Side-by-side: best result comparison
fig, axes = plt.subplots(1, 3, figsize=(14, 5))
axes[0].imshow(img, cmap="gray")
axes[0].set_title("Original")
axes[0].axis("off")
axes[1].imshow(results_c[1][1], cmap="gray")  # detT=0.01
axes[1].set_title(f"detT=0.01 (padrao)\nT={results_c[1][2]:.4f}")
axes[1].axis("off")
axes[2].imshow(results_c[3][1], cmap="gray")  # detT=0.0001
axes[2].set_title(f"detT=0.0001 (precisao maxima)\nT={results_c[3][2]:.4f}")
axes[2].axis("off")
fig.suptitle("Parte (c): Comparacao padrao vs precisao maxima", fontsize=12)
plt.tight_layout()
save(fig, "part_c_best_comparison.png")

print(f"\nAll images saved to {OUT}/")
