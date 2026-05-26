#include "WiFi.h"

void setup() {
  Serial.begin(115200);  // Inicia a comunicação serial
  WiFi.mode(WIFI_STA);   // Define o ESP32 no modo Station
  WiFi.disconnect();     // Desconecta de redes anteriores
  delay(100);
}

void loop() {
  Serial.println("Escaneando redes WiFi...");

  int numRedes = WiFi.scanNetworks(); // Escaneia redes disponíveis

  if (numRedes > 0) {
    Serial.print("RSSI da primeira rede encontrada: ");
    Serial.print(WiFi.RSSI(0));
    Serial.println(" dBm");
  } else {
    Serial.println("Nenhuma rede WiFi encontrada.");
  }

  delay(5000);  // Aguarda 5 segundos antes do próximo escaneamento
}
