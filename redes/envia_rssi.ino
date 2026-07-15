/*
 * envia_rssi.ino
 * ---------------------------------------------------------------------------
 * Conecta o ESP32 a um ponto de acesso WiFi e envia, pela porta serial,
 * a leitura de RSSI (Received Signal Strength Indicator) do AP conectado
 * a cada 200 ms. Cada linha contem apenas o valor inteiro de RSSI em dBm,
 * facilitando a captura pelo script Python "coleta_dados.py".
 *
 * Disciplina: Redes Sem Fio - PPGEEC/UFAM
 * Plataforma: ESP32-WROOM-32 (Espressif), Arduino IDE 2.x
 * ---------------------------------------------------------------------------
 */

#include "WiFi.h"

// -- Credenciais do ponto de acesso sob medicao ------------------------------
const char* SSID_AP  = "RSSI_LAB_2G";     // ajuste para o SSID do seu AP
const char* SENHA_AP = "senha_do_ap";     // ajuste para a senha do seu AP

// Intervalo entre leituras (ms)
const unsigned long INTERVALO_MS = 200;

void conectarWiFi() {
  Serial.printf("Conectando a %s ...\n", SSID_AP);
  WiFi.mode(WIFI_STA);
  WiFi.begin(SSID_AP, SENHA_AP);

  unsigned long inicio = millis();
  while (WiFi.status() != WL_CONNECTED) {
    delay(300);
    Serial.print(".");
    // tenta reconectar caso demore demais
    if (millis() - inicio > 15000) {
      Serial.println("\nFalha na conexao. Tentando novamente...");
      WiFi.disconnect();
      delay(500);
      WiFi.begin(SSID_AP, SENHA_AP);
      inicio = millis();
    }
  }
  Serial.println();
  Serial.printf("Conectado! IP: %s | BSSID: %s\n",
                WiFi.localIP().toString().c_str(),
                WiFi.BSSIDstr().c_str());
}

void setup() {
  Serial.begin(115200);
  delay(200);
  conectarWiFi();
  Serial.println("# Iniciando envio de RSSI (dBm)...");
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    conectarWiFi();
  }

  long rssi = WiFi.RSSI();   // RSSI do AP atualmente conectado (dBm)

  // Envia apenas o numero inteiro, uma leitura por linha.
  Serial.println(rssi);

  delay(INTERVALO_MS);
}
