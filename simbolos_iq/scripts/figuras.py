#!/usr/bin/python3.12
"""Gera as figuras a partir de dados/*.csv (capturados do flowgraph real) e dos screenshots reais do GRC."""
import csv, re, yaml, numpy as np, matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.image import imread
B = "/home/user/ufam/simbolos_iq"
NAMES = {"bpsk": "BPSK", "qpsk": "QPSK", "16qam": "16-QAM"}
LV = {"bpsk": [-1, 1], "qpsk": [-1/2**.5, 1/2**.5], "16qam": [-3/10**.5, -1/10**.5, 1/10**.5, 3/10**.5]}
LBL = {"bpsk": ["-1", "+1"], "qpsk": ["-1/√2 = -0,707", "+1/√2 = +0,707"],
       "16qam": ["-3/√10 = -0,949", "-1/√10 = -0,316", "+1/√10 = +0,316", "+3/√10 = +0,949"]}
SHORT = {"bpsk": ["-1", "+1"], "qpsk": ["-0,707", "+0,707"], "16qam": ["-0,949", "-0,316", "+0,316", "+0,949"]}
BLUE, ORANGE = "#1f77b4", "#ff7f0e"
plt.rcParams.update({"font.size": 12, "axes.titlesize": 15, "axes.labelsize": 13})

def load(mod):
    d = np.genfromtxt(f"{B}/dados/{mod}_iq.csv", delimiter=",", names=True)
    return d["I"], d["Q"]

for mod, N in NAMES.items():
    I, Q = load(mod)
    lv = LV[mod]
    qlv = [0.0] if mod == 'bpsk' else lv; qsh = ['0'] if mod == 'bpsk' else SHORT[mod]; qlb = ['0 (Q = 0 sempre)'] if mod == 'bpsk' else LBL[mod]
    # ---- constelacao
    fig, ax = plt.subplots(figsize=(8, 7))
    from collections import Counter
    c = Counter(zip(np.round(I, 4), np.round(Q, 4)))
    pts = np.array(sorted(c)); cnt = [c[tuple(p)] for p in pts]
    for v in lv:
        ax.axvline(v, color="gray", ls="--", lw=0.9, zorder=1)
    for v in qlv:
        ax.axhline(v, color="gray", ls="--", lw=0.9, zorder=1)
    ax.scatter(I[:2000], Q[:2000], s=60, color=BLUE, alpha=0.25, zorder=2, label=f"{len(I[:2000])} simbolos capturados (sobrepostos)")
    ax.scatter(pts[:, 0], pts[:, 1], s=90, marker="o", facecolor="none", edgecolor="red", lw=1.8, zorder=3,
               label=f"{len(pts)} pontos distintos observados (de {len(I)} simbolos)")
    ax.set_xticks(lv); ax.set_yticks(qlv)
    ax.set_xticklabels(SHORT[mod], rotation=0); ax.set_yticklabels(qsh)
    lim = 1.5 if mod != "bpsk" else 1.5
    ax.set_xlim(-lim, lim); ax.set_ylim(-lim, lim); ax.set_aspect("equal")
    ax.set_xlabel("I - componente em fase (amplitude normalizada)")
    ax.set_ylabel("Q - componente em quadratura (amplitude normalizada)")
    ax.set_title(f"Constelação {N} - plano I/Q (saída do Chunks to Symbols)", pad=100)
    ax.grid(True, alpha=0.3)
    # rotulos dos niveis nas bordas
    for v, t in zip(lv, LBL[mod]):
        ax.annotate(f"I = {t}", (v, 1.0), xycoords=("data", "axes fraction"), xytext=(0, 4), textcoords="offset points", rotation=90, ha="center", va="bottom", fontsize=8, color="dimgray")
    for v, t in zip(qlv, qlb):
        ax.annotate(f"Q = {t}", (1.0, v), xycoords=("axes fraction", "data"), xytext=(4, 0), textcoords="offset points", ha="left", va="center", fontsize=8, color="dimgray")
    ax.legend(loc="upper center", bbox_to_anchor=(0.5, -0.11), fontsize=10, ncol=1)
    fig.tight_layout(); fig.savefig(f"{B}/figuras/{mod}_constelacao.png", dpi=300); plt.close(fig)
    # ---- temporal
    n = 50; t = np.arange(n + 1) / 32000 * 1e3  # ms
    fig, axs = plt.subplots(2, 1, figsize=(11, 7), sharex=True)
    for ax, y, col, lab, ylab, L_, S_ in ((axs[0], I, BLUE, "I (parte real, em fase)", "I (amplitude)", lv, SHORT[mod]),
                                  (axs[1], Q, ORANGE, "Q (parte imaginária, quadratura)", "Q (amplitude)", qlv, qsh)):
        ax.step(np.arange(n + 1) / 32000 * 1e3, np.r_[y[:n], y[n - 1]], where="post", color=col, lw=2, label=lab)
        for v, t_ in zip(L_, S_):
            ax.axhline(v, color="gray", ls="--", lw=0.9)
            ax.annotate(t_, (t[-1], v), xytext=(36, 0), textcoords="offset points", va="center", fontsize=10, color="dimgray", annotation_clip=False)
        ax.set_yticks(L_); ax.set_yticklabels(S_)
        ax.set_ylim(-1.35 if mod != "16qam" else -1.25, 1.35 if mod != "16qam" else 1.25)
        ax.set_ylabel(ylab); ax.grid(True, alpha=0.3); ax.legend(loc="upper right", fontsize=10)
    axs[1].set_xlabel("Tempo (ms)  -  1 símbolo = 1/32000 s = 0,03125 ms")
    fig.suptitle(f"{N} - domínio do tempo: I(t) e Q(t), {n} primeiros símbolos (amostras 0 a {n-1})", fontsize=15)
    fig.tight_layout(rect=(0, 0, 0.94, 0.96)); fig.savefig(f"{B}/figuras/{mod}_temporal.png", dpi=300); plt.close(fig)
    # ---- fluxo: screenshot REAL do GRC + painel com parametros exatos lidos do .grc
    img = imread(f"{B}/build/grc_{mod}_screen.png")[156:965, 0:2000]
    g = yaml.safe_load(open(f"{B}/grc/aula2_{mod}_MATHEUS.grc"))
    bl = {b["name"]: b["parameters"] for b in g["blocks"]}
    rs, cs = bl["analog_random_source_x_0"], bl["digital_chunks_to_symbols_xx_0"]
    txt = (f"Random Source:  Output Type = {rs['type']},  Minimum = {rs['min']},  Maximum = {rs['max']}  (valores 0..{int(rs['max'])-1}),  "
           f"Num Samples = {rs['num_samps']},  Repeat = Yes\n"
           f"Chunks to Symbols:  Input Type = {cs['in_type']},  Output Type = {cs['out_type']},  Dimension = {cs['dimension']}\n"
           f"Symbol Table = {cs['symbol_table']}\n"
           f"Throttle: Sample Rate = samp_rate = 32000  |  Sinks: QT GUI Constellation Sink e QT GUI Time Sink (Complex)")
    fig = plt.figure(figsize=(14, 7.6))
    ax = fig.add_axes([0, 0.24, 1, 0.74]); ax.imshow(img); ax.axis("off")
    fig.suptitle(f"Fluxograma GNU Radio Companion - {N} (captura de tela do GRC com o arquivo aula2_{mod}_MATHEUS.grc)", fontsize=14, y=0.995)
    import textwrap
    wrapped = "\n".join(textwrap.fill(l, 150, subsequent_indent="      ") for l in txt.split("\n"))
    fig.text(0.02, 0.02, "Parâmetros exatos (extraídos do arquivo .grc):\n" + wrapped, fontsize=9.5, family="monospace", va="bottom",
             bbox=dict(boxstyle="round", fc="#f4f4f4", ec="gray"))
    fig.savefig(f"{B}/figuras/{mod}_fluxo.png", dpi=170); plt.close(fig)
    # extras: capturas reais Qt e GRC
    import shutil
    shutil.copy(f"{B}/build/qt_{mod}_janela.png", f"{B}/figuras/extra_{mod}_qtgui_real.png")
    shutil.copy(f"{B}/build/grc_{mod}_dialog.png", f"{B}/figuras/extra_{mod}_grc_dialogo_symbol_table.png")
print("figuras ok")
