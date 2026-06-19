"""
log_distancia.py
-------------------------------------------------------------------------------
Parte 3 - Simulacao teorica (Modelo Log-Distance de Potencia de Sinal).

Modelo:
    PL(d)   = PL(d0) + 10 * n * log10(d / d0) + X_sigma,   X_sigma ~ N(0, sigma)
    RSSI(d) = Pt - PL(d)

Para cada distancia sao geradas 30 amostras de RSSI simuladas; em seguida,
calcula-se a media por distancia e plota-se a curva teorica.

Parametros usados (proximos da realidade):
    Pt      = 20 dBm   -> potencia de transmissao tipica de roteador/ONT
                          residencial 2,4 GHz (100 mW EIRP).
    d0      = 1 m      -> distancia de referencia.
    PL(d0)  = 59 dB    -> perda de referencia a 1 m (perda de espaco livre a
                          2,4 GHz, ~40 dB, somada a perdas de implementacao:
                          eficiencia da antena do ESP32, casamento e obstaculos
                          proximos). Assim Pt - PL(d0) = -39 dBm em d0.
    n       = 3.0      -> expoente de perda (faixa 2,3 a 3,5 - ambiente interno).
    sigma   = 4 dB     -> desvio-padrao do shadowing (faixa 3 a 5 dB).

Saidas:
    images/rssi_medio_teorico.pdf|png
    medicoes_simuladas.csv  ([distancia_m, amostra, rssi_dbm])

Disciplina: Redes Sem Fio - PPGEEC/UFAM
-------------------------------------------------------------------------------
"""

import csv

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np

# -- Parametros do modelo ----------------------------------------------------
Pt = 20.0          # potencia de transmissao do AP (dBm)
d0 = 1.0           # distancia de referencia (m)
PL_d0 = 59.0       # perda de caminho na distancia de referencia (dB)
n = 3.0            # expoente de perda de caminho
sigma = 4.0        # desvio-padrao do shadowing (dB)
N_AMOSTRAS = 30    # amostras simuladas por distancia

# Mesmas distancias da campanha de medicao real
DISTANCIAS = np.array([1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0])

rng = np.random.default_rng(7)  # reprodutibilidade da simulacao


def rssi_simulado(distancias, n_amostras):
    """Gera n_amostras de RSSI por distancia segundo o modelo log-distance."""
    amostras = {}
    for d in distancias:
        pl = PL_d0 + 10.0 * n * np.log10(d / d0)
        x_sigma = rng.normal(0.0, sigma, n_amostras)
        rssi = Pt - pl + x_sigma
        amostras[d] = rssi
    return amostras


def main():
    amostras = rssi_simulado(DISTANCIAS, N_AMOSTRAS)
    media = np.array([amostras[d].mean() for d in DISTANCIAS])

    # curva teorica "limpa" (sem ruido) para referencia
    pl_curva = PL_d0 + 10.0 * n * np.log10(DISTANCIAS / d0)
    rssi_curva = Pt - pl_curva

    # -- grava amostras simuladas --------------------------------------------
    with open("medicoes_simuladas.csv", "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["distancia_m", "amostra", "rssi_dbm"])
        for d in DISTANCIAS:
            for k, r in enumerate(amostras[d], start=1):
                w.writerow([f"{d:.1f}", k, f"{r:.1f}"])
    print("medicoes_simuladas.csv gravado.")

    # -- grafico -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(8.5, 5))

    # nuvem de amostras simuladas
    for d in DISTANCIAS:
        ax.scatter(np.full(N_AMOSTRAS, d), amostras[d], s=12, alpha=0.25,
                   color="#9C27B0", zorder=2)
    ax.scatter([], [], s=14, color="#9C27B0", alpha=0.5,
               label="Amostras simuladas (30/dist.)")

    ax.plot(DISTANCIAS, media, "o-", color="#6A1B9A", linewidth=2,
            markersize=7, zorder=4, label="Media simulada por distancia")
    ax.plot(DISTANCIAS, rssi_curva, "--", color="#37474F", linewidth=1.6,
            zorder=3, label="Curva teorica (sem ruido)")

    ax.set_xlabel("Distancia do AP (m)", fontsize=11)
    ax.set_ylabel("RSSI (dBm)", fontsize=11)
    ax.set_title("Modelo Log-Distance - RSSI simulado em funcao da distancia\n"
                 f"Pt={Pt:.0f} dBm, n={n}, sigma={sigma:.0f} dB",
                 fontsize=11, fontweight="bold")
    ax.grid(alpha=0.35)
    ax.legend(fontsize=9)
    fig.tight_layout()
    fig.savefig("images/rssi_medio_teorico.pdf")
    fig.savefig("images/rssi_medio_teorico.png", dpi=150)
    print("images/rssi_medio_teorico.pdf|png gravado.")


if __name__ == "__main__":
    main()
