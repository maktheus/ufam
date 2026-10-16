"""
coleta_dados.py
-------------------------------------------------------------------------------
Captura as leituras de RSSI enviadas pelo ESP32 (firmware envia_rssi.ino) pela
porta serial e organiza as amostras por distancia do ponto de acesso.

Procedimento de coleta:
  1. Posicione o ESP32 a ~1,0 m do AP e rode o script informando a distancia.
  2. O script coleta N (=30) amostras de RSSI e grava em medicoes_rssi.csv.
  3. Afaste-se ~0,5 m e repita, ate >= 3,0 m (ou o limite do ambiente).

Saida: medicoes_rssi.csv com colunas [distancia_m, amostra, rssi_dbm].

Disciplina: Redes Sem Fio - PPGEEC/UFAM
-------------------------------------------------------------------------------
"""

import csv
import os
import sys
import time

import serial  # pip install pyserial

PORTA_SERIAL = "/dev/ttyUSB0"   # Windows: "COM6"; Linux: "/dev/ttyUSB0"
BAUDRATE = 115200
N_AMOSTRAS = 30                 # amostras por distancia
ARQUIVO_CSV = "medicoes_rssi.csv"


def ler_rssi(ser):
    """Le uma linha da serial e retorna o RSSI (int) ou None se invalida."""
    linha = ser.readline().decode("utf-8", errors="ignore").strip()
    if not linha or linha.startswith("#"):
        return None
    try:
        valor = int(linha)
    except ValueError:
        return None
    # filtra leituras fora da faixa fisica plausivel
    if -100 <= valor <= -10:
        return valor
    return None


def coletar(distancia, ser):
    """Coleta N_AMOSTRAS leituras validas de RSSI para a distancia dada."""
    print(f"\n>> Coletando {N_AMOSTRAS} amostras a {distancia:.1f} m do AP...")
    amostras = []
    while len(amostras) < N_AMOSTRAS:
        rssi = ler_rssi(ser)
        if rssi is not None:
            amostras.append(rssi)
            print(f"   [{len(amostras):02d}/{N_AMOSTRAS}] RSSI = {rssi} dBm")
    media = sum(amostras) / len(amostras)
    print(f"   Media a {distancia:.1f} m: {media:.2f} dBm")
    return amostras


def salvar(distancia, amostras):
    """Acrescenta as amostras ao CSV (cria cabecalho se necessario)."""
    novo = not os.path.exists(ARQUIVO_CSV)
    with open(ARQUIVO_CSV, "a", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        if novo:
            w.writerow(["distancia_m", "amostra", "rssi_dbm"])
        for k, r in enumerate(amostras, start=1):
            w.writerow([f"{distancia:.1f}", k, r])
    print(f"   Gravado em {ARQUIVO_CSV}.")


def main():
    try:
        ser = serial.Serial(PORTA_SERIAL, BAUDRATE, timeout=2)
    except serial.SerialException as e:
        print(f"Erro ao abrir {PORTA_SERIAL}: {e}")
        sys.exit(1)

    time.sleep(2)  # aguarda o ESP32 reiniciar/estabilizar
    print(f"Conectado a {PORTA_SERIAL}. Pressione Ctrl+C para encerrar.")

    try:
        while True:
            entrada = input("\nDistancia do AP em metros (ENTER vazio p/ sair): ")
            if entrada.strip() == "":
                break
            distancia = float(entrada.replace(",", "."))
            amostras = coletar(distancia, ser)
            salvar(distancia, amostras)
    except KeyboardInterrupt:
        print("\nColeta encerrada pelo usuario.")
    finally:
        ser.close()
        print("Porta serial fechada.")


if __name__ == "__main__":
    main()
