"""
gerar_figuras.py  —  gera as figuras para o relatorio LaTeX
"""

import re
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np

ARQUIVO = "rssi_dados.txt"
PADRAO = re.compile(
    r"\[\d+\]\s+SSID:\s+(.+?)\s*\|\s+BSSID:\s+([0-9A-Fa-f:]{17})"
    r"\s*\|\s+Canal:\s*(\d+)\s*\|\s+RSSI:\s*(-?\d+)\s*dBm"
)

redes: dict[str, dict] = {}
with open(ARQUIVO, encoding="utf-8") as f:
    for linha in f:
        m = PADRAO.search(linha)
        if not m:
            continue
        ssid, bssid, canal, rssi = (
            m.group(1).strip(), m.group(2), int(m.group(3)), int(m.group(4))
        )
        if bssid not in redes:
            redes[bssid] = {"ssid": ssid, "canal": canal, "rssi": []}
        redes[bssid]["rssi"].append(rssi)

import os
os.makedirs("images", exist_ok=True)

# ── Figura 1: RSSI max/min por rede ──────────────────────────────────────────
nomes   = [info["ssid"] for info in redes.values()]
rssi_max = [max(info["rssi"]) for info in redes.values()]
rssi_min = [min(info["rssi"]) for info in redes.values()]
rssi_med = [round(sum(info["rssi"]) / len(info["rssi"]), 1) for info in redes.values()]

x = np.arange(len(nomes))
w = 0.28

fig, ax = plt.subplots(figsize=(13, 5.5))
b1 = ax.bar(x - w, rssi_max, w, label="RSSI máx", color="#2196F3", zorder=3)
b2 = ax.bar(x,     rssi_med, w, label="RSSI médio", color="#4CAF50", zorder=3)
b3 = ax.bar(x + w, rssi_min, w, label="RSSI mín",  color="#F44336", zorder=3)

ax.set_xticks(x)
ax.set_xticklabels(nomes, rotation=35, ha="right", fontsize=8.5)
ax.set_ylabel("RSSI (dBm)", fontsize=10)
ax.set_title("RSSI máximo, médio e mínimo por rede WiFi\n(8 varreduras — apartamento, sala)",
             fontsize=11, fontweight="bold")
ax.set_ylim(-105, -35)
ax.axhline(-70, color="orange", linestyle="--", linewidth=0.9, label="Limiar fraco (−70 dBm)")
ax.axhline(-85, color="red",    linestyle=":",  linewidth=0.9, label="Limiar crítico (−85 dBm)")
ax.legend(fontsize=8.5, loc="lower right")
ax.grid(axis="y", alpha=0.35, zorder=0)
ax.invert_yaxis()
fig.tight_layout()
fig.savefig("images/rssi_por_rede.pdf", dpi=150)
fig.savefig("images/rssi_por_rede.png", dpi=150)
plt.close()

# ── Figura 2: uso de canais ───────────────────────────────────────────────────
canais = [info["canal"] for info in redes.values()]
canais_24 = sorted([c for c in canais if c <= 14])
canais_5  = sorted([c for c in canais if c > 14])

fig, axes = plt.subplots(1, 2, figsize=(11, 4))
for ax, clist, titulo, cor in [
    (axes[0], canais_24, "Canais 2.4 GHz", "#1565C0"),
    (axes[1], canais_5,  "Canais 5 GHz",   "#6A1B9A"),
]:
    from collections import Counter
    cnt = Counter(clist)
    ch  = sorted(cnt.keys())
    ax.bar([str(c) for c in ch], [cnt[c] for c in ch], color=cor, edgecolor="white", zorder=3)
    ax.set_xlabel("Canal", fontsize=10)
    ax.set_ylabel("Número de redes", fontsize=10)
    ax.set_title(titulo, fontsize=11, fontweight="bold")
    ax.yaxis.set_major_locator(plt.MaxNLocator(integer=True))
    ax.grid(axis="y", alpha=0.35, zorder=0)

fig.suptitle("Distribuição de redes por canal WiFi", fontsize=12, fontweight="bold")
fig.tight_layout()
fig.savefig("images/canais_wifi.pdf", dpi=150)
fig.savefig("images/canais_wifi.png", dpi=150)
plt.close()

# ── Figura 3: variação RSSI ao longo dos scans (4 redes) ─────────────────────
fig, ax = plt.subplots(figsize=(10, 4.5))
cores = ["#1E88E5", "#43A047", "#E53935", "#FB8C00"]
destaques = list(redes.values())[:4]
nomes4    = [info["ssid"] for info in destaques]
for info, cor, nome in zip(destaques, cores, nomes4):
    scans = list(range(1, len(info["rssi"]) + 1))
    ax.plot(scans, info["rssi"], marker="o", linewidth=1.8, markersize=5,
            color=cor, label=nome)

ax.set_xlabel("Varredura (scan #)", fontsize=10)
ax.set_ylabel("RSSI (dBm)", fontsize=10)
ax.set_title("Variação de RSSI ao longo das varreduras\n(4 redes mais próximas)",
             fontsize=11, fontweight="bold")
ax.invert_yaxis()
ax.legend(fontsize=8.5)
ax.grid(alpha=0.35)
ax.set_xticks(range(1, 9))
fig.tight_layout()
fig.savefig("images/rssi_temporal.pdf", dpi=150)
fig.savefig("images/rssi_temporal.png", dpi=150)
plt.close()

print("Figuras salvas em images/: rssi_por_rede, canais_wifi, rssi_temporal")
