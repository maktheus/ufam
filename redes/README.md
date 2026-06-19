# Medição e Análise de RSSI (Wi-Fi) — ESP32 × Modelo Log-Distance

Atividade prática da disciplina **Redes Sem Fio** (PPGEEC/UFAM). Coleta de
amostras de RSSI de uma rede Wi-Fi em ambiente real e comparação com o modelo
teórico de propagação *log-distance*.

**Integrantes:** Álison de Oliveira Venâncio · Ayrton Finicelli Lemes ·
Matheus Serrão Uchôa · Victor Ronald

## Estrutura / Entregáveis

| Arquivo | Descrição |
|---|---|
| `envia_rssi.ino` | Firmware ESP32: conecta ao AP e envia o RSSI pela serial |
| `coleta_dados.py` | Coleta serial: 30 amostras por distância → `medicoes_rssi.csv` |
| `grafico_valor_medio.py` | **Parte 2** — gráfico distância × RSSI médio (dados reais) |
| `log_distancia.py` | **Parte 3** — modelo log-distance (30 amostras simuladas/distância) |
| `grafico_comparativo.py` | **Parte 4** — gráfico comparativo (dados reais × curva teórica) |
| `medicoes_rssi.csv` | Medições brutas (9 distâncias × 30 amostras = 270 leituras) |
| `medias_rssi.csv` | Resumo por distância (média, desvio, mín, máx) |
| `medicoes_simuladas.csv` | Amostras simuladas pelo modelo log-distance |
| `images/comparativo.png` | Gráfico comparativo (entregável principal) |
| `relatorio_rssi.tex` / `.pdf` | Relatório técnico completo |
| `referencias.bib` | Referências bibliográficas |

## Parâmetros do modelo (Parte 3)

- Potência de transmissão do AP: `Pt = 20 dBm` (≈ 100 mW EIRP, roteador 2,4 GHz)
- Distância de referência: `d0 = 1 m`
- Expoente de perda: `n = 3.0` (faixa 2,3–3,5, ambiente interno)
- Desvio-padrão de *shadowing*: `σ = 4 dB` (faixa 3–5 dB)

## Como reproduzir

```bash
pip install pyserial numpy matplotlib

# 1) Carregar envia_rssi.ino no ESP32 (Arduino IDE) e coletar:
python coleta_dados.py            # gera medicoes_rssi.csv

# 2) Gráficos e análise:
python grafico_valor_medio.py     # Parte 2  -> images/rssi_medio_real.png
python log_distancia.py           # Parte 3  -> images/rssi_medio_teorico.png
python grafico_comparativo.py     # Parte 4  -> images/comparativo.png

# 3) Relatório (TeX Live com abntex2 + siunitx):
pdflatex relatorio_rssi.tex && bibtex relatorio_rssi && \
  pdflatex relatorio_rssi.tex && pdflatex relatorio_rssi.tex
```

## Principais resultados

- 270 medições reais (1,0 m a 5,0 m, passos de 0,5 m).
- RSSI a 1 m ≈ **−40 dBm**; a 5 m ≈ **−62,5 dBm**.
- Expoente de perda efetivo (ajuste sobre dados reais): **n ≈ 3,23**.
- Aderência teoria × prática: **RMSE = 1,89 dB**, **MAE = 1,61 dB**.
- Todos os pontos reais permanecem dentro da faixa teórica ±σ.
