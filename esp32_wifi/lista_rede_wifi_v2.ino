#include "WiFi.h"

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(100);
}

void loop() {
  Serial.println("Escaneando redes WiFi...");

  // true = mostrar redes ocultas; varredura 2.4 e 5 GHz
  int numRedes = WiFi.scanNetworks(false, true);

  if (numRedes == 0) {
    Serial.println("Nenhuma rede WiFi encontrada.");
  } else {
    Serial.printf("Redes encontradas: %d\n", numRedes);
    for (int i = 0; i < numRedes; i++) {
      Serial.printf(
        "[%d] SSID: %-32s | BSSID: %s | Canal: %2d | RSSI: %d dBm\n",
        i,
        WiFi.SSID(i).c_str(),
        WiFi.BSSIDstr(i).c_str(),
        WiFi.channel(i),
        WiFi.RSSI(i)
      );
    }
  }

  WiFi.scanDelete();   // libera memoria apos cada scan
  delay(5000);
}
