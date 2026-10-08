# Evidencias experimentais - como cada figura foi obtida

Ambiente: GNU Radio 3.10.9.2 (apt, Ubuntu), Python 3.12, xvfb + openbox. Nada foi digitado a mao nos numeros de resumo.json.

## Arquivos .grc (grc/)
Gerados por `scripts/gen_grc.py` (YAML GRC 3.10) e validados com `grcc` (compilam sem erro; unico aviso: Throttle marcado "old/deprecated" no 3.10.9). Abrem no gnuradio-companion real (conferido sob xvfb).

## Dados (dados/)
Rota REAL: `scripts/captura/captura_<mod>.grc` (Random Source -> Chunks to Symbols -> Head 5000 -> Vector Sink, mesmos parametros/tabelas dos .grc da atividade, sem Throttle/GUI), compilado com grcc e executado por `scripts/captura.py`. 5000 simbolos por modulacao em `<mod>_iq.csv` (indice,I,Q). `resumo.json` calculado desses dados.
Obs.: Es do 16-QAM = 1,0048 e a media amostral de 5000 simbolos (teorico = 1).

## Figuras de fluxo (figuras/*_fluxo.png)
Captura de tela REAL do gnuradio-companion abrindo o .grc (xvfb, `scripts/grc_screenshot.sh`), recortada e montada em matplotlib com um painel de texto inferior contendo os parametros exatos lidos do .grc (porque o GRC trunca a Symbol Table no bloco). O painel de texto NAO e captura de tela. `extra_<mod>_grc_dialogo_symbol_table.png` = captura real do dialogo de propriedades do Chunks to Symbols.

## Figuras de constelacao e temporal (figuras/*_constelacao.png, *_temporal.png)
RECONSTRUIDAS com matplotlib (300 dpi, `scripts/figuras.py`) a partir dos CSV capturados do flowgraph real (rota acima). Nao sao capturas dos sinks Qt. Constelacao: 2000 simbolos sobrepostos + pontos distintos observados nos 5000. Temporal: primeiros 50 simbolos, I azul e Q laranja, steps-post, tempo em ms (1 simbolo = 0,03125 ms). Linhas de nivel tracejadas rotuladas.

## Capturas Qt REAIS (extra)
`extra_<mod>_qtgui_real.png`: janela real do flowgraph aula2_<mod>_MATHEUS (QT GUI Constellation Sink + QT GUI Time Sink) rodando sob xvfb, gravada com QWidget.grab() (`scripts/captura_qt.py`). Os dados dessa execucao sao uma sequencia aleatoria independente da usada nos CSV.
Obs.: nos sinks Qt os rotulos de eixo sao os padrao do GNU Radio (In-phase/Quadrature, Time (ms)); o Time Sink liga amostras com retas (nao degraus).
