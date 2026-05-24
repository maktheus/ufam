"""
analisar_rssi.py
Le rssi_dados.txt capturado pelo salvar_wifi_serial.py e responde
automaticamente as quest?es b, c e d do exercicio:
  b) Quantas redes foram identificadas?
  c) SSID, BSSID e canal de cada rede
  d) Maior e menor RSSI de cada rede
"""

import re
from collections import defaultdict

ARQUIVO = "rssi_dados.txt"
LOCAL_COLETA = "Apartamento - sala"   # (a) resposta manual

# === parse =============================================
# formato: [i] SSID: x | BSSID: x | Canal: x | RSSI: x dBm
PADRAO = re.compile(
    r"\[\d+\]\s+SSID:\s+(.+?)\s*\|\s+BSSID:\s+([0-9A-Fa-f:]{17})"
    r"\s*\|\s+Canal:\s*(\d+)\s*\|\s+RSSI:\s*(-?\d+)\s*dBm"
)

redes: dict[str, dict] = {}   # chave = BSSID

with open(ARQUIVO, encoding="utf-8") as f:
    for linha in f:
        m = PADRAO.search(linha)
        if not m:
            continue
        ssid  = m.group(1).strip()
        bssid = m.group(2)
        canal = int(m.group(3))
        rssi  = int(m.group(4))
        if bssid not in redes:
            redes[bssid] = {"ssid": ssid, "canal": canal, "rssi": []}
        redes[bssid]["rssi"].append(rssi)

# === saidas ============================================
SEP = "=" * 70

print(SEP)
print("  ANALISE DE REDES WiFi - ESP32 RSSI Scanner")
print(SEP)

print(f"\n(a) Local da coleta: {LOCAL_COLETA}\n")

print(f"(b) Numero de redes identificadas: {len(redes)}\n")

print("(c) SSID, BSSID e canal de cada rede:")
print(f"    {'#':<4} {'SSID':<32} {'BSSID':<20} {'Canal':>5}")
print("    " + "-" * 65)
for idx, (bssid, info) in enumerate(redes.items(), 1):
    print(f"    {idx:<4} {info['ssid']:<32} {bssid:<20} {info['canal']:>5}")

print("\n(d) Maior e menor RSSI por rede:")
print(f"    {'#':<4} {'SSID':<32} {'max':>7} {'med':>7} {'min':>7} {'N':>4}")
print("    " + "-" * 75)
for idx, (bssid, info) in enumerate(redes.items(), 1):
    leituras = info["rssi"]
    mx=max(leituras); mn=min(leituras); md=round(sum(leituras)/len(leituras),1)
    print(f"    {idx:<4} {info['ssid']:<32} {mx:>7} {md:>7} {mn:>7} {len(leituras):>4}")

print(f"\n{SEP}")
