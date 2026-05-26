"""
gerar_dados_simulados.py
Gera rssi_dados.txt com dados realistas baseados em scans ESP32
tipicos de apartamento urbano brasileiro (Manaus/AM).

Referencias de valores tipicos:
  - RSSI rede propria (mesmo comodo): -40 a -55 dBm
  - Vizinhos mesmo andar:             -60 a -75 dBm
  - Vizinhos andar diferente:         -75 a -88 dBm
  - Redes muito distantes:            -88 a -95 dBm
  - Canais 2.4 GHz mais usados no BR: 1, 6, 11
  - Canais 5 GHz mais usados no BR:   36, 40, 44, 149, 153
"""

import random

random.seed(42)

# (SSID, BSSID, canal, rssi_base, variacao)
# OUIs reais: Sagemcom=D0:76:8F (VIVO), Arris=00:26:B6 (Claro/NET),
#             TP-Link=50:3E:AA, Intelbras=F4:6D:04, Motorola=00:1C:1C
REDES = [
    # propria / mesmo comodo
    ("VIVO-FTTB-5432",        "D0:76:8F:3A:1C:7E",  6,   -48,  3),
    ("VIVO-FTTB-5432_5G",     "D0:76:8F:3A:1C:7F",  36,  -50,  3),
    # vizinhos mesmo andar
    ("CLARO_2G_Apto301",      "00:26:B6:5C:2D:41",  11,  -65,  4),
    ("CLARO_5G_Apto301",      "00:26:B6:5C:2D:42",  149, -68,  4),
    ("Intelbras_GX300_2G",    "F4:6D:04:9B:3E:88",  1,   -71,  4),
    ("Intelbras_GX300_5G",    "F4:6D:04:9B:3E:89",  40,  -74,  4),
    # vizinhos andar diferente
    ("NET_Claro_Apto201",     "00:1C:1C:7F:4A:B3",  6,   -79,  5),
    ("TIM_Live_Fibra_8821",   "50:3E:AA:C1:82:05",  11,  -82,  5),
    ("TP-Link_AC1200_7F3B",   "EC:08:6B:A4:7F:3B",  1,   -80,  5),
    # redes distantes / condominio
    ("WiFi-Condominio-2G",    "C8:3A:35:D2:6E:90",  9,   -87,  5),
    ("DIRECT-55-Samsung_TV",  "5E:4F:AB:12:CD:34",  6,   -91,  3),
    ("AndroidHotspot_M52",    "72:B3:D1:55:AA:2F",  11,  -93,  3),
]

N_SCANS = 8
INTERVALO = 5  # segundos entre scans

linhas = []
for scan in range(N_SCANS):
    linhas.append("Escaneando redes WiFi...")
    linhas.append(f"Redes encontradas: {len(REDES)}")
    for i, (ssid, bssid, canal, rssi_base, var) in enumerate(REDES):
        rssi = rssi_base + random.randint(-var, var)
        linhas.append(
            f"[{i}] SSID: {ssid:<28} | BSSID: {bssid} | Canal: {canal:>3} | RSSI: {rssi} dBm"
        )

with open("rssi_dados.txt", "w", encoding="utf-8") as f:
    f.write("\n".join(linhas) + "\n")

print(f"rssi_dados.txt gerado: {N_SCANS} scans x {len(REDES)} redes.")
