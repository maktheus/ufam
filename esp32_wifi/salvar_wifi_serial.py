import serial
import time

porta_serial = "COM6"   # ajuste para a porta correta (ex: COM3, /dev/ttyUSB0)
baudrate = 115200

try:
    ser = serial.Serial(porta_serial, baudrate, timeout=1)
    time.sleep(2)

    print(f"Conectado a {porta_serial}. Capturando dados RSSI...")

    with open("rssi_dados.txt", "w", encoding="utf-8") as arquivo:
        while True:
            linha = ser.readline().decode("utf-8", errors="ignore").strip()
            if linha:
                print(linha)
                arquivo.write(linha + "\n")

except serial.SerialException as e:
    print(f"Erro ao acessar {porta_serial}: {e}")
except KeyboardInterrupt:
    print("\nCaptura encerrada pelo usuario.")
except Exception as e:
    print(f"Erro inesperado: {e}")
