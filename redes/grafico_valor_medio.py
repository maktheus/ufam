"""
grafico_valor_medio.py
-------------------------------------------------------------------------------
Parte 2 - Grafico dos dados reais.
Le medicoes_rssi.csv, calcula a media (e o desvio-padrao) do RSSI para cada
distancia e gera o grafico Distancia (m) x RSSI medio (dBm).

Tambem grava medias_rssi.csv com o resumo por distancia.

Disciplina: Redes Sem Fio - PPGEEC/UFAM
-------------------------------------------------------------------------------
"""

import csv
from collections import defaultdict

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np

ARQUIVO = "medicoes_rssi.csv"


def carregar(arquivo):
    """Retorna {distancia: [rssi, ...]} a partir do CSV de medicoes."""
    dados = defaultdict(list)
    with open(arquivo, newline="", encoding="utf-8") as f:
        leitor = csv.DictReader(f)
        for linha in leitor:
            d = float(linha["distancia_m"])
            dados[d].append(int(linha["rssi_dbm"]))
    return dict(sorted(dados.items()))


def main():
    dados = carregar(ARQUIVO)
    dist = np.array(list(dados.keys()))
    media = np.array([np.mean(v) for v in dados.values()])
    desvio = np.array([np.std(v, ddof=1) for v in dados.values()])

    # -- resumo em CSV -------------------------------------------------------
    with open("medias_rssi.csv", "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["distancia_m", "rssi_medio_dbm", "desvio_padrao_dbm",
                    "rssi_min_dbm", "rssi_max_dbm", "n_amostras"])
        for d in dist:
            v = np.array(dados[d])
            w.writerow([f"{d:.1f}", f"{v.mean():.2f}", f"{v.std(ddof=1):.2f}",
                        int(v.min()), int(v.max()), len(v)])
    print("medias_rssi.csv gravado.")

    # -- grafico -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(8.5, 5))
    ax.errorbar(dist, media, yerr=desvio, fmt="o-", color="#1565C0",
                ecolor="#90A4AE", elinewidth=1.2, capsize=4, markersize=7,
                linewidth=1.8, label="RSSI medio +/- desvio-padrao")
    for d, m in zip(dist, media):
        ax.annotate(f"{m:.1f}", (d, m), textcoords="offset points",
                    xytext=(0, 10), ha="center", fontsize=8, color="#0D47A1")

    ax.set_xlabel("Distancia do AP (m)", fontsize=11)
    ax.set_ylabel("RSSI medio (dBm)", fontsize=11)
    ax.set_title("RSSI medio em funcao da distancia (medicoes reais)\n"
                 "ESP32 - AP 2,4 GHz - 30 amostras por distancia",
                 fontsize=11, fontweight="bold")
    ax.grid(alpha=0.35)
    ax.legend(fontsize=9)
    fig.tight_layout()
    fig.savefig("images/rssi_medio_real.pdf")
    fig.savefig("images/rssi_medio_real.png", dpi=150)
    print("images/rssi_medio_real.pdf|png gravado.")


if __name__ == "__main__":
    main()
