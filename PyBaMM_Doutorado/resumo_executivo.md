# Resumo Executivo — Tese de Doutorado UFAM 2026

**Título:** Modelagem de Falhas e Estimação da Vida Remanescente de Baterias de Íons de Lítio Utilizando a Plataforma PyBaMM  
**Autor:** Pedro Victor dos Santos Oliveira  
**Instituição:** Universidade Federal do Amazonas — UFAM  
**Programa:** Pós-Graduação em Informática (PPGI)  
**Nível:** Doutorado  
**Ano:** 2026  

---

## Fonte dos Dados

Os dados e implementações desta tese são baseados no repositório:  
**Gabriel-Santos06 / PyBaMM---Modelagem-de-Falhas-e-Vida-Remanescente-de-Baterias-Li-ion**

### Estrutura do Repositório Analisado

| Módulo                   | Conteúdo                          | Notebooks |
|--------------------------|-----------------------------------|-----------|
| `Exemplos/`              | Tutoriais progressivos PyBaMM     | 18        |
| `Degradacao de baterias/`| Implementação de degradação DFN   | 1         |
| `Metodos de Carregamento/`| Análise CC-CV                    | 1         |
| `Simulacao de Falhas/`   | Falhas em sensores e atuadores    | 9         |

---

## Sumário da Tese

### Capítulo 1 — Introdução
- Contexto: mercado global de BIL, eletrobilidade e Amazônia
- Problema: detecção de falhas e estimação de RUL em BMS
- Objetivos geral e específicos (6 objetivos)
- Hipóteses de pesquisa (H1, H2, H3)

### Capítulo 2 — Revisão Bibliográfica
- Eletroquímica de BIL: composição, reações, materiais
- Hierarquia de modelos: ECM → SPM → DFN
- Equações completas do modelo DFN (P2D):
  - Difusão de Li no sólido (Fick esférico)
  - Difusão de Li no eletrólito
  - Conservação de carga (sólido e eletrólito)
  - Cinética de Butler-Volmer
  - Balanço térmico lumped
- Mecanismos de degradação: SEI, Li plating, mecânica de partículas, LAM
- Modelos de falha em sensores: bias, drift, stuck
- Estimação de SOH e RUL via ElectrodeSOHSolver
- PyBaMM: arquitetura, Chen2020 e OKane2022

### Capítulo 3 — Metodologia
- Ambiente: Python 3.11, PyBaMM 25.8.0, JupyterLab 4.4.7, IDAKLU solver
- Configuração DFN base com Chen2020
- Configuração DFN degradação com OKane2022 (4 mecanismos acoplados)
- Framework `falha_sensor()`: função genérica para bias/drift/stuck
- Falhas em atuadores: variação de $h$ (resfriamento) e $R_{cont}$ (terminais)
- Protocolos CC-CV: 4.2V vs 4.1V

### Capítulo 4 — Resultados e Discussão
- Validação DFN-Chen2020: RMSE < 15 mV (4 taxas de C)
- Degradação: queda de 3.2% em 10 ciclos; SEI domina (65.4% das perdas)
- Sensor de corrente: bias →erro SOC de 10%; stuck = falha crítica
- Sensor de tensão: bias de 50mV → subcarregamento de 3%
- Sensor de temperatura: bias positivo → ativação prematura do resfriamento
- Atuadores (resfriamento): h=2 → ΔT=+10.2°C; h=30 → risco de Li plating
- CC-CV 4.1V: 21.5% mais rápido, menor estresse mecânico, 6.3% menos capacidade

### Capítulo 5 — Conclusão
- 5 contribuições principais validadas
- 3 hipóteses confirmadas quantitativamente
- 4 limitações identificadas
- 5 direções de trabalho futuro

---

## Arquivo Principal

```
tese_pybamm_ufam.tex   — Documento LaTeX completo (ABNT NBR 14724:2011)
Makefile               — Compilação com pdflatex
resumo_executivo.md    — Este arquivo
```

## Compilação

```bash
# Requer texlive-full e abntex2
make pdf
# Saída: tese_pybamm_ufam.pdf
```

---

## Pacotes LaTeX Necessários

- `abntex2` (modelo UFAM/ABNT)
- `listings` (código Python)
- `siunitx` (unidades SI)
- `booktabs`, `longtable` (tabelas)
- `pgfplots`, `tikz` (gráficos)
- `algorithm`, `algpseudocode` (pseudocódigos)
