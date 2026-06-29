"""
gerar_dados.py  --  Gera as tabelas de vazao (A, B, C) da atividade.

IMPORTANTE
----------
Os valores de "Taxa Medida" produzidos aqui sao um MODELO DE REFERENCIA
baseado na fisica do enlace 802.11 (overhead de protocolo, limite de
processamento do ESP32, saturacao e perdas em alta carga, queda de SNR com a
distancia). Eles servem como gabarito esperado e como esqueleto da entrega:
ao executar o experimento real, basta substituir a coluna "taxa_medida_mbps"
pelos valores lidos no Wireshark (Statistics -> Capture file properties:
"Average bits/s", ou I/O Graphs).

Taxa teorica da fonte (somente payload):
    R = tamanho_bytes * 8 / intervalo_segundos

Saida: arquivos CSV em dados/.
"""

import csv
import os

os.makedirs("dados", exist_ok=True)


def taxa_teorica_mbps(tamanho_bytes: int, intervalo_us: float) -> float:
    """Taxa da fonte (payload) em Mbps."""
    return (tamanho_bytes * 8) / (intervalo_us * 1e-6) / 1e6


def diferenca_pct(teorica: float, medida: float) -> float:
    return (medida - teorica) / teorica * 100.0


# ============================================================================
# TABELA A -- Influencia do tamanho do pacote
# Distancia fixa: 1 m   |   Intervalo fixo: 4000 us (250 pacotes/s)
# ----------------------------------------------------------------------------
# Modelo: a eficiencia (medida/teorica) CRESCE com o tamanho do pacote, pois o
# overhead fixo por pacote (cabecalhos UDP/IP/802.11, IFS, ACK, contencao) e
# diluido em payloads maiores. A 1500 B ha fragmentacao IP (MTU 1500), o que
# reduz levemente o ganho de eficiencia.
# ----------------------------------------------------------------------------
INTERVALO_A = 4000  # us
tabela_a = [
    # tamanho, eficiencia, observacao
    (250,  0.90, "Muito overhead por pacote; baixa eficiencia"),
    (500,  0.93, "Eficiencia melhora com payload maior"),
    (750,  0.95, "Overhead bem diluido"),
    (1000, 0.96, "Bom equilibrio payload/overhead"),
    (1250, 0.965, "Eficiencia proxima do maximo"),
    (1500, 0.95, "Fragmentacao IP (MTU 1500) limita o ganho"),
]


def gerar_tabela_a():
    linhas = []
    for tamanho, efic, obs in tabela_a:
        teo = taxa_teorica_mbps(tamanho, INTERVALO_A)
        med = round(teo * efic, 3)
        teo = round(teo, 3)
        linhas.append({
            "tamanho_bytes": tamanho,
            "intervalo_us": INTERVALO_A,
            "distancia_m": 1,
            "taxa_teorica_mbps": teo,
            "taxa_medida_mbps": med,
            "diferenca_pct": round(diferenca_pct(teo, med), 1),
            "observacoes": obs,
        })
    return linhas


# ============================================================================
# TABELA B -- Influencia do intervalo de transmissao
# Distancia fixa: 1 m   |   Tamanho fixo: 1000 bytes
# ----------------------------------------------------------------------------
# Modelo: em intervalos grandes (baixa carga) a eficiencia e alta (~0,97). Ao
# reduzir o intervalo a carga oferecida cresce e o ESP32 / pilha Wi-Fi entram
# em SATURACAO: a taxa medida deixa de acompanhar a teorica e surge perda de
# pacotes. Abaixo de ~1000 us o ESP32 nao consegue gerar todos os pacotes e a
# vazao satura em torno de 9-10 Mbps.
# ----------------------------------------------------------------------------
TAMANHO_B = 1000  # bytes
tabela_b = [
    # intervalo_us, eficiencia, observacao
    (16000, 0.97, "Baixa carga; sem perdas"),
    (8000,  0.97, "Baixa carga; vazao acompanha a fonte"),
    (4000,  0.96, "Regime estavel"),
    (2000,  0.92, "Inicio de saturacao; jitter aumenta"),
    (1000,  0.85, "Saturacao; perdas perceptiveis"),
    (500,   0.57, "Forte saturacao; ESP32 nao gera todos os pacotes"),
]


def gerar_tabela_b():
    linhas = []
    for intervalo, efic, obs in tabela_b:
        teo = taxa_teorica_mbps(TAMANHO_B, intervalo)
        med = round(teo * efic, 3)
        teo = round(teo, 3)
        linhas.append({
            "intervalo_us": intervalo,
            "tamanho_bytes": TAMANHO_B,
            "distancia_m": 1,
            "taxa_teorica_mbps": teo,
            "taxa_medida_mbps": med,
            "diferenca_pct": round(diferenca_pct(teo, med), 1),
            "observacoes": obs,
        })
    return linhas


# ============================================================================
# TABELA C -- Influencia da distancia  (OPCIONAL: removida da entrega)
# Tamanho fixo: 1000 bytes   |   Intervalo fixo: 4000 us
# ----------------------------------------------------------------------------
# Modelo: a taxa TEORICA da fonte e independente da distancia (2,0 Mbps). A
# taxa MEDIDA cai com a distancia porque a potencia recebida diminui (path
# loss), a SNR cai, o Wi-Fi adapta a modulacao para esquemas mais robustos
# (256-QAM -> 64-QAM -> QPSK/BPSK) e aumentam as retransmissoes.
# ----------------------------------------------------------------------------
TAMANHO_C, INTERVALO_C = 1000, 4000
tabela_c = [
    # distancia_m, eficiencia, rssi_dBm, observacao
    (1,  0.96, -45, "Sinal forte; 256-QAM; sem retransmissoes"),
    (3,  0.95, -55, "Sinal forte; 256-QAM"),
    (5,  0.92, -65, "64-QAM; poucas retransmissoes"),
    (7,  0.89, -72, "Adaptacao de taxa; retransmissoes"),
    (10, 0.80, -80, "QPSK/BPSK; muitas retransmissoes; perdas"),
]


def gerar_tabela_c():
    linhas = []
    for dist, efic, rssi, obs in tabela_c:
        teo = taxa_teorica_mbps(TAMANHO_C, INTERVALO_C)
        med = round(teo * efic, 3)
        teo = round(teo, 3)
        linhas.append({
            "distancia_m": dist,
            "tamanho_bytes": TAMANHO_C,
            "intervalo_us": INTERVALO_C,
            "rssi_dbm": rssi,
            "taxa_teorica_mbps": teo,
            "taxa_medida_mbps": med,
            "diferenca_pct": round(diferenca_pct(teo, med), 1),
            "observacoes": obs,
        })
    return linhas


def salvar_csv(nome, linhas):
    caminho = os.path.join("dados", nome)
    with open(caminho, "w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=list(linhas[0].keys()))
        writer.writeheader()
        writer.writerows(linhas)
    print(f"  {caminho}  ({len(linhas)} linhas)")


if __name__ == "__main__":
    print("Gerando tabelas de vazao (modelo de referencia):")
    salvar_csv("tabela_a_tamanho.csv", gerar_tabela_a())
    salvar_csv("tabela_b_intervalo.csv", gerar_tabela_b())
    salvar_csv("tabela_c_distancia.csv", gerar_tabela_c())
    print("Concluido. Edite as colunas 'taxa_medida_mbps' com seus dados reais "
          "do Wireshark e rode gerar_figuras.py.")
