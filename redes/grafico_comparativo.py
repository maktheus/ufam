"""
grafico_comparativo.py
-------------------------------------------------------------------------------
Parte 4 - Comparacao e discussao.
Sobrepoe, em um unico grafico:
  - os dados reais (media do RSSI por distancia, com barras de desvio-padrao);
  - a curva teorica do modelo log-distance (Parte 3).

Adicionalmente:
  - ajusta o expoente de perda 'n' efetivo aos dados reais (regressao linear
    de RSSI vs. log10(d));
  - quantifica a aderencia teoria x pratica (RMSE e MAE das medias).

Este e o grafico comparativo entregue na atividade.

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

# -- Parametros do modelo teorico (iguais aos de log_distancia.py) -----------
Pt = 20.0          # potencia de transmissao do AP (dBm)
d0 = 1.0           # distancia de referencia (m)
PL_d0 = 59.0       # perda de caminho na distancia de referencia (dB)
n_teorico = 3.0    # expoente de perda assumido
sigma = 4.0        # desvio-padrao do shadowing (dB)


def carregar(arquivo):
    dados = defaultdict(list)
    with open(arquivo, newline="", encoding="utf-8") as f:
        for linha in csv.DictReader(f):
            dados[float(linha["distancia_m"])].append(int(linha["rssi_dbm"]))
    return dict(sorted(dados.items()))


def main():
    dados = carregar(ARQUIVO)
    dist = np.array(list(dados.keys()))
    media = np.array([np.mean(v) for v in dados.values()])
    desvio = np.array([np.std(v, ddof=1) for v in dados.values()])

    # -- curva teorica -------------------------------------------------------
    d_cont = np.linspace(dist.min(), dist.max(), 200)
    rssi_teorico = Pt - (PL_d0 + 10.0 * n_teorico * np.log10(d_cont / d0))
    rssi_teorico_pts = Pt - (PL_d0 + 10.0 * n_teorico * np.log10(dist / d0))

    # -- ajuste do n efetivo aos dados reais --------------------------------
    # RSSI = a + b*log10(d), com b = -10*n  ->  n_efetivo = -b/10
    x = np.log10(dist / d0)
    b, a = np.polyfit(x, media, 1)         # media ~ b*x + a
    n_efetivo = -b / 10.0
    rssi_ajustado = a + b * np.log10(d_cont / d0)

    # -- metricas de aderencia (media real x teorica) -----------------------
    erro = media - rssi_teorico_pts
    rmse = float(np.sqrt(np.mean(erro ** 2)))
    mae = float(np.mean(np.abs(erro)))

    print(f"n teorico assumido : {n_teorico:.2f}")
    print(f"n efetivo (ajuste) : {n_efetivo:.2f}")
    print(f"RSSI(1 m) real     : {media[0]:.1f} dBm")
    print(f"RMSE (real x teoria): {rmse:.2f} dB")
    print(f"MAE  (real x teoria): {mae:.2f} dB")

    # -- grafico comparativo ------------------------------------------------
    fig, ax = plt.subplots(figsize=(9, 5.5))

    ax.plot(d_cont, rssi_teorico, "--", color="#37474F", linewidth=2,
            label=f"Curva teorica log-distance (n={n_teorico:.1f}, "
                  f"$\\sigma$={sigma:.0f} dB)")
    # faixa de incerteza +/- sigma do modelo
    ax.fill_between(d_cont, rssi_teorico - sigma, rssi_teorico + sigma,
                    color="#90A4AE", alpha=0.25,
                    label=f"Faixa teorica +/- $\\sigma$ ({sigma:.0f} dB)")
    ax.plot(d_cont, rssi_ajustado, ":", color="#E65100", linewidth=1.8,
            label=f"Ajuste aos dados reais (n efetivo={n_efetivo:.2f})")

    ax.errorbar(dist, media, yerr=desvio, fmt="o", color="#1565C0",
                ecolor="#1565C0", elinewidth=1.2, capsize=4, markersize=8,
                zorder=5, label="Dados reais (media +/- desvio-padrao)")

    ax.set_xlabel("Distancia do AP (m)", fontsize=11)
    ax.set_ylabel("RSSI (dBm)", fontsize=11)
    ax.set_title("Comparacao: dados reais x modelo teorico log-distance\n"
                 "RSSI em funcao da distancia (AP 2,4 GHz, ESP32)",
                 fontsize=11, fontweight="bold")
    ax.grid(alpha=0.35)
    ax.legend(fontsize=8.5, loc="upper right")

    # caixa de metricas
    txt = (f"n teorico = {n_teorico:.1f}\n"
           f"n efetivo = {n_efetivo:.2f}\n"
           f"RMSE = {rmse:.2f} dB\n"
           f"MAE  = {mae:.2f} dB")
    ax.text(0.02, 0.04, txt, transform=ax.transAxes, fontsize=8.5,
            va="bottom", ha="left",
            bbox=dict(boxstyle="round", facecolor="#FFF8E1",
                      edgecolor="#E65100", alpha=0.9))

    fig.tight_layout()
    fig.savefig("images/comparativo.pdf")
    fig.savefig("images/comparativo.png", dpi=150)
    print("images/comparativo.pdf|png gravado.")


if __name__ == "__main__":
    main()
