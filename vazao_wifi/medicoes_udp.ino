/* ============================================================================
 *  medicoes_udp.ino  --  Transmissor de pacotes UDP para medicao de vazao
 *  Disciplina: Redes Sem Fio / Comunicacao Sem Fio (UFAM)
 *
 *  O ESP32 conecta-se a uma rede Wi-Fi e dispara pacotes UDP em rajada
 *  continua para um IP de destino (o PC rodando o Wireshark). O tamanho do
 *  pacote e o intervalo entre transmissoes sao configuraveis, permitindo
 *  reproduzir as Tabelas A (tamanho) e B (intervalo) da atividade.
 *
 *  Como medir no PC (Wireshark):
 *    - capturar na interface Wi-Fi
 *    - aplicar o filtro de exibicao:  udp.port == 5000
 *    - Statistics -> Capture file properties / I/O Graphs / Conversations
 *
 *  O byte 0 de cada pacote carrega um contador de sequencia (modulo 256),
 *  util para estimar perdas de pacotes na analise.
 * ========================================================================== */

#include <WiFi.h>
#include <WiFiUdp.h>

// ----------------------------------------------------------------------------
// 1) PARAMETROS DE REDE  (ajuste para o seu ambiente)
// ----------------------------------------------------------------------------
const char* SSID_REDE   = "MINHA_REDE_WIFI";     // nome (SSID) da rede
const char* SENHA_REDE  = "MINHA_SENHA";         // senha da rede

// IP do PC que executa o Wireshark e a porta de destino.
// Descubra o IP do PC com "ipconfig" (Windows) ou "ip addr" (Linux).
IPAddress IP_DESTINO(192, 168, 0, 100);
const uint16_t PORTA_DESTINO = 5000;             // casa com udp.port == 5000

// ----------------------------------------------------------------------------
// 2) PARAMETROS DO EXPERIMENTO  (varie estes valores entre as medicoes)
// ----------------------------------------------------------------------------
//  - TAMANHO_PACOTE: carga util (payload) UDP, em bytes  -> Tabela A
//  - INTERVALO_US  : tempo entre o inicio de pacotes, em microssegundos -> Tabela B
//
//  Taxa teorica da fonte (payload):  R = TAMANHO_PACOTE * 8 / (INTERVALO_US * 1e-6)
//  Ex.: 1000 bytes a cada 4000 us  ->  1000*8 / 0,004 = 2.000.000 bps = 2,0 Mbps
const uint16_t TAMANHO_PACOTE = 1000;            // 250..1500 bytes
const uint32_t INTERVALO_US   = 4000;            // 500..16000 us

// ----------------------------------------------------------------------------
// Objetos globais
// ----------------------------------------------------------------------------
WiFiUDP udp;
uint8_t  buffer[1500];          // payload maximo desta atividade
uint8_t  seq = 0;               // contador de sequencia (byte 0 do pacote)
uint32_t proximaTx = 0;         // instante (us) da proxima transmissao
uint32_t totalPacotes = 0;
uint32_t ultimoLog = 0;

void conectarWiFi() {
  Serial.printf("Conectando a \"%s\" ...\n", SSID_REDE);
  WiFi.mode(WIFI_STA);
  WiFi.begin(SSID_REDE, SENHA_REDE);
  while (WiFi.status() != WL_CONNECTED) {
    delay(300);
    Serial.print('.');
  }
  Serial.println();
  Serial.printf("Conectado. IP do ESP32: %s\n", WiFi.localIP().toString().c_str());
  Serial.printf("Enviando UDP para %s:%u\n",
                IP_DESTINO.toString().c_str(), PORTA_DESTINO);
  Serial.printf("Pacote = %u bytes | Intervalo = %u us | RSSI = %d dBm\n",
                TAMANHO_PACOTE, INTERVALO_US, WiFi.RSSI());

  // Taxa teorica esperada da fonte (apenas payload)
  double taxa = (double)TAMANHO_PACOTE * 8.0 / (INTERVALO_US * 1e-6);
  Serial.printf("Taxa teorica da fonte: %.3f Mbps (%.0f pps)\n",
                taxa / 1e6, 1e6 / INTERVALO_US);
}

void setup() {
  Serial.begin(115200);
  delay(200);

  // Preenche o payload com um padrao conhecido (facilita inspecao no Wireshark)
  for (uint16_t i = 0; i < sizeof(buffer); i++) buffer[i] = (uint8_t)('A' + (i % 26));

  conectarWiFi();
  proximaTx = micros();
}

void loop() {
  // Reconecta automaticamente caso a rede caia
  if (WiFi.status() != WL_CONNECTED) {
    conectarWiFi();
    proximaTx = micros();
  }

  uint32_t agora = micros();
  if ((int32_t)(agora - proximaTx) >= 0) {
    buffer[0] = seq++;                                   // marca de sequencia
    udp.beginPacket(IP_DESTINO, PORTA_DESTINO);
    udp.write(buffer, TAMANHO_PACOTE);
    udp.endPacket();

    totalPacotes++;
    proximaTx += INTERVALO_US;                           // agenda proxima TX

    // Se o ESP32 nao consegue acompanhar (saturacao), realinha o agendamento
    if ((int32_t)(micros() - proximaTx) > (int32_t)INTERVALO_US) {
      proximaTx = micros();
    }
  }

  // Log periodico no monitor serial (1 vez por segundo)
  if (millis() - ultimoLog >= 1000) {
    ultimoLog = millis();
    Serial.printf("Pacotes enviados: %lu | RSSI: %d dBm\n",
                  (unsigned long)totalPacotes, WiFi.RSSI());
  }
}
