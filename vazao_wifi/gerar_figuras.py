"""
gerar_figuras.py  --  Gera as figuras do relatorio de vazao Wi-Fi.

Le os CSV de dados/ (produzidos por gerar_dados.py) e gera:
  1. constelacoes.{png,pdf}      -> BPSK, QPSK, 16-QAM (teoria de modulacao)
  2. vazao_tamanho.{png,pdf}     -> Tabela A: teorica x medida x tamanho
  3. vazao_intervalo.{png,pdf}   -> Tabela B: teorica x medida x intervalo (saturacao)
  4. vazao_distancia.{png,pdf}   -> Tabela C: teorica x medida x distancia
  5. io_graph.{png,pdf}          -> grafico estilo Wireshark I/O Graph
"""

import csv
import os

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np

os.makedirs("images", exist_ok=True)


def ler_csv(nome):
    with open(os.path.join("dados", nome), encoding="utf-8") as f:
        return list(csv.DictReader(f))


# ============================================================================
# Figura 1 -- Constelacoes BPSK / QPSK / 16-QAM
# ============================================================================
def fig_constelacoes():
    fig, axes = plt.subplots(1, 3, figsize=(12, 4))

    # BPSK
    bpsk = [(-1, 0, "0"), (1, 0, "1")]
    # QPSK (Gray)
    qpsk = [(1, 1, "00"), (-1, 1, "01"), (-1, -1, "11"), (1, -1, "10")]
    # 16-QAM
    niveis = [-3, -1, 1, 3]
    qam16 = [(i, q) for q in niveis for i in niveis]

    for ax, pontos, titulo, bits in [
        (axes[0], bpsk, "BPSK (1 bit/simbolo)", True),
        (axes[1], qpsk, "QPSK (2 bits/simbolo)", True),
        (axes[2], qam16, "16-QAM (4 bits/simbolo)", False),
    ]:
        ax.axhline(0, color="gray", lw=0.6)
        ax.axvline(0, color="gray", lw=0.6)
        if bits:
            for x, y, b in pontos:
                ax.plot(x, y, "o", color="#1565C0", ms=11, zorder=3)
                ax.annotate(b, (x, y), textcoords="offset points",
                            xytext=(8, 8), fontsize=10, fontweight="bold")
            lim = 2
        else:
            for x, y in pontos:
                ax.plot(x, y, "o", color="#1565C0", ms=8, zorder=3)
            lim = 4.2
        ax.set_xlim(-lim, lim)
        ax.set_ylim(-lim, lim)
        ax.set_aspect("equal")
        ax.set_xlabel("I (fase)", fontsize=9)
        ax.set_ylabel("Q (quadratura)", fontsize=9)
        ax.set_title(titulo, fontsize=11, fontweight="bold")
        ax.grid(alpha=0.3)

    fig.suptitle("Diagramas de constelacao: mais bits/simbolo, maior taxa "
                 "(e maior sensibilidade ao ruido)", fontsize=12, fontweight="bold")
    fig.tight_layout()
    fig.savefig("images/constelacoes.pdf")
    fig.savefig("images/constelacoes.png", dpi=150)
    plt.close()


# ============================================================================
# Figura 2 -- Tabela A: vazao x tamanho do pacote
# ============================================================================
def fig_tamanho():
    dados = ler_csv("tabela_a_tamanho.csv")
    tam = [int(d["tamanho_bytes"]) for d in dados]
    teo = [float(d["taxa_teorica_mbps"]) for d in dados]
    med = [float(d["taxa_medida_mbps"]) for d in dados]
    efic = [m / t * 100 for m, t in zip(med, teo)]

    x = np.arange(len(tam))
    w = 0.38
    fig, ax1 = plt.subplots(figsize=(9, 5))
    ax1.bar(x - w / 2, teo, w, label="Taxa teorica (fonte)", color="#90CAF9", zorder=3)
    ax1.bar(x + w / 2, med, w, label="Taxa medida (Wireshark)", color="#1565C0", zorder=3)
    ax1.set_xticks(x)
    ax1.set_xticklabels(tam)
    ax1.set_xlabel("Tamanho do pacote (bytes)", fontsize=10)
    ax1.set_ylabel("Vazao (Mbps)", fontsize=10)
    ax1.set_title("Tabela A -- Vazao x tamanho do pacote\n(distancia 1 m, intervalo 4000 us)",
                  fontsize=11, fontweight="bold")
    ax1.grid(axis="y", alpha=0.3, zorder=0)
    ax1.legend(loc="upper left", fontsize=9)

    ax2 = ax1.twinx()
    ax2.plot(x, efic, "o--", color="#E53935", lw=1.8, label="Eficiencia (%)")
    ax2.set_ylabel("Eficiencia medida/teorica (%)", fontsize=10, color="#E53935")
    ax2.set_ylim(0, 105)
    ax2.tick_params(axis="y", colors="#E53935")
    ax2.legend(loc="lower right", fontsize=9)

    fig.tight_layout()
    fig.savefig("images/vazao_tamanho.pdf")
    fig.savefig("images/vazao_tamanho.png", dpi=150)
    plt.close()


# ============================================================================
# Figura 3 -- Tabela B: vazao x intervalo (saturacao)
# ============================================================================
def fig_intervalo():
    dados = ler_csv("tabela_b_intervalo.csv")
    # ordena por intervalo crescente para o eixo
    dados = sorted(dados, key=lambda d: int(d["intervalo_us"]))
    interv = [int(d["intervalo_us"]) for d in dados]
    teo = [float(d["taxa_teorica_mbps"]) for d in dados]
    med = [float(d["taxa_medida_mbps"]) for d in dados]

    fig, ax = plt.subplots(figsize=(9, 5))
    ax.plot(interv, teo, "o-", color="#90CAF9", lw=2, ms=8, label="Taxa teorica (fonte)")
    ax.plot(interv, med, "s-", color="#1565C0", lw=2, ms=8, label="Taxa medida (Wireshark)")
    ax.set_xscale("log")
    ax.set_xticks(interv)
    ax.get_xaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
    ax.set_xlabel("Intervalo entre pacotes (us) -- escala log", fontsize=10)
    ax.set_ylabel("Vazao (Mbps)", fontsize=10)
    ax.set_title("Tabela B -- Vazao x intervalo de transmissao\n(distancia 1 m, pacote 1000 bytes)",
                 fontsize=11, fontweight="bold")
    ax.grid(alpha=0.3, which="both")
    ax.legend(fontsize=9)

    # destaca a regiao de saturacao
    ax.annotate("Saturacao:\nmedida nao\nacompanha\na fonte",
                xy=(500, 9.12), xytext=(900, 13),
                arrowprops=dict(arrowstyle="->", color="#E53935"),
                color="#E53935", fontsize=9, fontweight="bold")

    fig.tight_layout()
    fig.savefig("images/vazao_intervalo.pdf")
    fig.savefig("images/vazao_intervalo.png", dpi=150)
    plt.close()


# ============================================================================
# Figura 4 -- Tabela C: vazao x distancia
# ============================================================================
def fig_distancia():
    dados = ler_csv("tabela_c_distancia.csv")
    dist = [int(d["distancia_m"]) for d in dados]
    teo = [float(d["taxa_teorica_mbps"]) for d in dados]
    med = [float(d["taxa_medida_mbps"]) for d in dados]
    rssi = [int(d["rssi_dbm"]) for d in dados]

    fig, ax1 = plt.subplots(figsize=(9, 5))
    ax1.plot(dist, teo, "o--", color="#90CAF9", lw=2, ms=8, label="Taxa teorica (fonte)")
    ax1.plot(dist, med, "s-", color="#1565C0", lw=2, ms=8, label="Taxa medida (Wireshark)")
    ax1.set_xlabel("Distancia (m)", fontsize=10)
    ax1.set_ylabel("Vazao (Mbps)", fontsize=10)
    ax1.set_ylim(0, 2.4)
    ax1.set_title("Tabela C -- Vazao x distancia (opcional)\n(pacote 1000 bytes, intervalo 4000 us)",
                  fontsize=11, fontweight="bold")
    ax1.grid(alpha=0.3)
    ax1.legend(loc="lower left", fontsize=9)

    ax2 = ax1.twinx()
    ax2.plot(dist, rssi, "^:", color="#43A047", lw=1.6, label="RSSI (dBm)")
    ax2.set_ylabel("RSSI (dBm)", fontsize=10, color="#43A047")
    ax2.tick_params(axis="y", colors="#43A047")
    ax2.legend(loc="upper right", fontsize=9)

    fig.tight_layout()
    fig.savefig("images/vazao_distancia.pdf")
    fig.savefig("images/vazao_distancia.png", dpi=150)
    plt.close()


# ============================================================================
# Figura 5 -- I/O Graph estilo Wireshark (throughput ao longo do tempo)
# ============================================================================
def fig_io_graph():
    rng = np.random.default_rng(7)
    t = np.arange(0, 30, 1)  # 30 s
    # regime estavel ~1,92 Mbps (pacote 1000 B, intervalo 4000 us) com jitter
    base = 1.92
    vazao = base + rng.normal(0, 0.04, size=t.size)
    vazao[10:13] *= 0.6  # pequena queda momentanea (retransmissoes)

    fig, ax = plt.subplots(figsize=(10, 4))
    ax.fill_between(t, vazao, color="#1565C0", alpha=0.35, zorder=2)
    ax.plot(t, vazao, color="#0D47A1", lw=1.6, zorder=3)
    ax.axhline(base, color="#E53935", ls="--", lw=1.2, label=f"media ~{base:.2f} Mbps")
    ax.axhline(2.0, color="#90CAF9", ls=":", lw=1.4, label="teorica 2,0 Mbps")
    ax.set_xlabel("Tempo (s)", fontsize=10)
    ax.set_ylabel("Throughput (Mbps)", fontsize=10)
    ax.set_title("Wireshark I/O Graph (ilustrativo) -- pacote 1000 B, intervalo 4000 us, 1 m",
                 fontsize=11, fontweight="bold")
    ax.set_ylim(0, 2.5)
    ax.grid(alpha=0.3)
    ax.legend(fontsize=9)
    fig.tight_layout()
    fig.savefig("images/io_graph.pdf")
    fig.savefig("images/io_graph.png", dpi=150)
    plt.close()


if __name__ == "__main__":
    import matplotlib.ticker  # noqa: F401  (usado em fig_intervalo)
    fig_constelacoes()
    fig_tamanho()
    fig_intervalo()
    fig_distancia()
    fig_io_graph()
    print("Figuras geradas em images/: constelacoes, vazao_tamanho, "
          "vazao_intervalo, vazao_distancia, io_graph")
