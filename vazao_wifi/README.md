# Comunicação sem fio: da informação ao canal
## Medição de Vazão em Rede Wi-Fi com ESP32 e Wireshark

Atividade da disciplina **Redes Sem Fio (UFAM)**. O objetivo é relacionar os
conceitos de *taxa da fonte*, *taxa do canal*, *modulação digital* e
*capacidade* a um experimento de medição de vazão (*throughput*) Wi-Fi: um
ESP32 transmite pacotes UDP e o Wireshark mede o tráfego no PC receptor.

## Conteúdo

| Arquivo | Descrição |
|---|---|
| `medicoes_udp.ino` | Firmware do ESP32: transmissor UDP com tamanho de pacote e intervalo configuráveis (porta 5000). |
| `gerar_dados.py` | Gera as Tabelas A (tamanho), B (intervalo) e C (distância) em `dados/*.csv` a partir do modelo físico do enlace. |
| `gerar_figuras.py` | Lê os CSV e gera as figuras em `images/` (constelações, vazão×tamanho, vazão×intervalo, vazão×distância, I/O graph). |
| `relatorio_vazao_wifi.tex` | Relatório técnico completo (template abntex2/UFAM). |
| `referencias.bib` | Referências bibliográficas. |
| `dados/` | Tabelas em CSV. |
| `images/` | Figuras geradas (`.png` e `.pdf`). |

## Como reproduzir o experimento

1. **Firmware** — em `medicoes_udp.ino`, ajuste `SSID_REDE`, `SENHA_REDE`,
   `IP_DESTINO` (IP do PC), `TAMANHO_PACOTE` e `INTERVALO_US`; grave no ESP32.
2. **Wireshark** — instale (com Npcap no Windows), capture na interface Wi-Fi e
   aplique o filtro `udp.port == 5000`.
3. **Medição** — use *Statistics → Capture file properties* (`Average bits/s`),
   *I/O Graphs*, *Conversations* e *Endpoints*.
4. **Tabelas** — para cada cenário (tamanho/intervalo/distância), anote a vazão
   medida.

## Como gerar tabelas e figuras

```bash
pip install matplotlib numpy
python3 gerar_dados.py     # escreve dados/*.csv
python3 gerar_figuras.py   # escreve images/*
```

Para usar **dados reais**, edite a coluna `taxa_medida_mbps` nos CSV de `dados/`
com os valores lidos no Wireshark e rode `gerar_figuras.py` novamente.

## Como compilar o relatório

O relatório usa a classe `abntex2`. Compile no [Overleaf](https://overleaf.com)
ou com uma distribuição TeX completa:

```bash
latexmk -pdf relatorio_vazao_wifi.tex
# ou: pdflatex -> bibtex -> pdflatex -> pdflatex
```

## Nota de transparência

As colunas de **taxa medida** nas tabelas constituem um *modelo de referência*
derivado da física do enlace 802.11 (overhead de protocolo, limite de
processamento do ESP32, saturação em alta carga e queda de SNR com a distância),
pois não havia hardware físico disponível na redação. Os scripts e o firmware
permitem reproduzir o experimento e substituir esses valores pelas leituras
reais do Wireshark sem alterar a estrutura do relatório.
