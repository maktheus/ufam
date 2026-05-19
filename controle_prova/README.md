# Controle de Sistemas — Guia de Resolução para Prova

> **Aulas cobertas:** lec10 a lec19  
> **Livro de referência:** Franklin, Powell & Emami-Naeini — *Feedback Control of Dynamic Systems* (FPE), Caps. 5 e 6  
> **Configuração padrão:** malha unitária R → (+) → K·L(s) → Y

---

## Mapa dos Tópicos

| Aula | Tópico | Método |
|------|--------|--------|
| lec10 | Introdução ao Root Locus | Lugar das raízes |
| lec11 | Regras A–F para esboçar RL | Lugar das raízes |
| lec12 | Compensação Lead (≈ PD) | RL + Frequência |
| lec13 | Compensação Lag (≈ PI) + intro Bode | RL + Frequência |
| lec14 | Diagramas de Bode (3 tipos de fatores) | Frequência |
| lec15 | Estabilidade por resposta em frequência; GM e PM | Frequência |
| lec16 | Projeto Lead/PI via Bode | Frequência |
| lec17 | Projeto Lag/PD/PID via Bode | Frequência |
| lec18 | Critério de Nyquist | Frequência |
| lec19 | Nyquist — mais exemplos; GM e PM no plot Nyquist | Frequência |

---

## BLOCO 1 — ROOT LOCUS (lec10–lec13)

### Conceito Central

Para a malha fechada com função de transferência de malha aberta `K·L(s)`:

```
Polos de malha fechada ⟺ raízes de:  a(s) + K·b(s) = 0
```

O **Root Locus** é o conjunto de todos os `s ∈ ℂ` que satisfazem a **condição de fase**:

```
∠L(s) = 180°  (L(s) é real e negativo)
```

para K variando de 0 a +∞.

---

### As 6 Regras para Esboço do Root Locus (lec10–lec11)

#### Regra A — Número de ramos
```
#ramos = n = grau do denominador de L(s)
```

#### Regra B — Pontos de partida (K → 0)
```
Ramos partem dos POLOS de malha aberta (s = p₁, p₂, ..., pₙ)
```

#### Regra C — Pontos de chegada (K → ∞)
```
Ramos chegam nos ZEROS de malha aberta (s = z₁, ..., zₘ)
Os (n − m) ramos restantes vão para ∞ pelas assíntotas
```

#### Regra D — Lugar real
```
Um ponto s₀ real está no RL se e somente se:
  #(polos à direita de s₀) − #(zeros à direita de s₀) é ÍMPAR
```
> **Dica de prova:** conte os polos e zeros à direita do ponto testado. Se a contagem for ímpar → o ponto está no RL.

#### Regra E — Assíntotas (ramos para ∞)
```
Ângulos das assíntotas:  θₖ = (180° + 360°·k) / (n − m),   k = 0, 1, ..., (n−m−1)

Centro das assíntotas:   σ_c = (Σpolos − Σzeros) / (n − m)
```

#### Regra F — Cruzamentos com o eixo jω
```
Substitua s = jω na equação característica a(jω) + K·b(jω) = 0
Separe partes real e imaginária → resolva para ω e K
```
> Alternativa: use o **Critério de Routh** para encontrar K crítico de estabilidade.

---

### Compensação Dinâmica via Root Locus (lec11–lec13)

#### Formato geral do compensador
```
D(s) = K · (s + z) / (s + p)        K, z, p > 0
```

| Tipo | Condição | Efeito | Análogo |
|------|----------|--------|---------|
| **Lead** | z < p | Melhora velocidade e amortecimento (adiciona fase) | ≈ PD |
| **Lag**  | z > p | Melhora rastreamento em regime permanente | ≈ PI |

#### Lead — Metodologia (lec12)
1. Identificar onde queremos os polos dominantes de MF (especificações de `ζ`, `ωₙ`, `ts`, `%OS`)
2. Calcular a fase que falta: `Δφ = 180° − ∠L(s*)`   onde `s*` é o polo desejado
3. Posicionar zero `z` e polo `p` do lead para fornecer `Δφ`
4. Calcular K usando a **condição de magnitude**: `K = |a(s*)| / |b(s*)|`

#### Lag — Metodologia (lec13)
1. Projetar primeiro como se fosse PI puro para cumprir estabilidade e rastreamento
2. Substituir o integrador por `(s + z)/(s + p)` com `p ≪ z` (tipicamente `p/z ≈ 0.1`)
3. Verificar erro em regime: `e(∞) = 1 / (1 + Kz/p)` — escolher `Kz/p` grande
4. Checar estabilidade: condições `K > 1 − p` e `Kz > p`

#### Relação Lead/Lag com fase
```
∠[(jω + z)/(jω + p)] = arctan(ω/z) − arctan(ω/p)
  Lead (z < p): resultado POSITIVO  → adiciona fase
  Lag  (z > p): resultado NEGATIVO  → reduz fase
```

---

## BLOCO 2 — RESPOSTA EM FREQUÊNCIA / BODE (lec14–lec17)

### Forma de Bode

Reescreva `KG(s)` colocando o **termo constante de cada fator igual a 1**:

```
KG(s) = K₀ · (jωτ₁ + 1)(jωτ₂ + 1)...
             ─────────────────────────────
             (jω)ⁿ · (jωτₐ + 1)(jωτᵦ + 1)...
```

O ganho DC (`K₀`) é calculado agrupando todas as constantes na frente.

---

### Os 3 Tipos de Fatores (lec14)

#### Tipo 1 — `K₀·(jω)ⁿ`
```
|K₀·(jω)ⁿ| (dB) = 20·log|K₀| + 20n·log(ω)   → reta de inclinação 20n dB/dec
∠K₀·(jω)ⁿ       = n × 90°                      → fase constante
```

| n | Inclinação magnitude | Fase |
|---|----------------------|------|
| +1 | +20 dB/dec | +90° |
|  0 | 0 dB/dec   | 0°   |
| −1 | −20 dB/dec | −90° |
| −2 | −40 dB/dec | −180°|

#### Tipo 2 — `(jωτ + 1)^±1`  (zero/polo real de primeira ordem)
```
Frequência de quebra:  ωb = 1/τ

Para um ZERO (+1):
  ω ≪ ωb → magnitude ≈ 0 dB,   fase ≈ 0°
  ω ≫ ωb → magnitude sobe +20 dB/dec,   fase → +90°

Para um POLO (−1):
  ω ≪ ωb → magnitude ≈ 0 dB,   fase ≈ 0°
  ω ≫ ωb → magnitude desce −20 dB/dec,   fase → −90°
```

> **Regra de ouro para esboço:** a magnitude muda de inclinação na frequência de quebra. A fase varia suavemente de 0° a ±90° ao longo de uma década antes e depois de ωb.

#### Tipo 3 — Polo/zero complexo conjugado `[(jω/ωₙ)² + 2ζ(jω/ωₙ) + 1]^±1`
```
Frequência natural: ωₙ
Amortecimento:      ζ

Para POLO complexo (−1):
  ω ≪ ωₙ → 0 dB,   fase ≈ 0°
  ω ≫ ωₙ → −40 dB/dec,   fase → −180°
  Pico de ressonância em ωr ≈ ωₙ√(1 − 2ζ²),  Mr = 1/(2ζ√(1−ζ²))
```

---

### Construção do Bode por Superposição (lec14)

**Passo a passo:**

1. Colocar `KG(jω)` na **forma de Bode** (fatorar, normalizar constantes)
2. Plotar o **ganho DC** `K₀` como linha horizontal
3. Para cada **polo/zero real** em `ω = 1/τ`:
   - Polo: dobrar a inclinação em **−20 dB/dec** a partir de ωb
   - Zero: dobrar a inclinação em **+20 dB/dec** a partir de ωb
4. Para cada **polo/zero complexo** em `ω = ωₙ`:
   - Polo: mudar inclinação em **−40 dB/dec** a partir de ωₙ
5. Somar as fases de todos os fatores individualmente

---

### Margens de Ganho e Fase (lec15)

#### Definições
```
Frequência de crossover de ganho (ωc):  |KG(jωc)| = 1   (0 dB)
Frequência de crossover de fase (ωφ):   ∠KG(jωφ) = −180°

Margem de Fase (PM):   PM = 180° + ∠KG(jωc)        [queremos PM > 0°]
Margem de Ganho (GM):  GM = 1/|KG(jωφ)|  (em dB: −20·log|KG(jωφ)|)  [queremos GM > 1]
```

#### Interpretação
```
PM > 0° e GM > 1  →  sistema em MF ESTÁVEL
PM ≤ 0° ou GM ≤ 1 →  sistema em MF INSTÁVEL
```

#### PM e amortecimento (2ª ordem)
```
PM ≈ 100·ζ   (aproximação útil)
ζ ≈ PM / 100

Regra prática: PM > 45° garante boa resposta transitória (ζ > 0.45)
```

---

### Relação Ganho-Fase de Bode (lec16–lec17)

Para sistemas **mínimos de fase** (sem zeros/polos no RHP):

```
Fase(ωc) ≈ Inclinação da magnitude(ωc) × 90°
```

| Inclinação em ωc | Fase em ωc | PM | Qualidade |
|-----------------|-----------|-----|-----------|
| −1 (−20 dB/dec) | ≈ −90° | ≈ 90° | Excelente |
| −2 (−40 dB/dec) | ≈ −180° | ≈ 0° | Crítico (instabilidade) |

> **Regra de ouro para projeto:** garanta inclinação **−1** na frequência de crossover ωc.

---

### Projeto por Resposta em Frequência (lec16–lec17)

#### Lead Compensator — Procedimento (lec16)
```
D(s) = K · (s + z)/(s + p),   z < p
```
1. Escolher **K** para atingir a largura de banda desejada (sem o lead)
2. Calcular PM atual → determinar quanto de fase adicionar: `Δφ = PM_desejada − PM_atual + margem`
3. Máxima fase do lead: `φmax = arcsin((p − z)/(p + z))`
4. Posicionar `ωmax = √(zp)` em ωc → calcular `z` e `p`
5. Verificar e iterar

#### Lag Compensator — Procedimento (lec17)
```
D(s) = K · (s + z)/(s + p),   z > p   (atenuação DC = z/p)
```
1. Projetar ganho K para PM desejado **sem** o lag
2. Calcular quanto de ganho DC o lag precisa adicionar: razão `z/p`
3. Posicionar frequência de quebra do lag bem abaixo de ωc (tipicamente `p = ωc/10`)
4. Verificar que o lag não degrade a PM (posicione lag longe de ωc)

#### PID via Lead+Lag
```
PID(s) ≈ Lead(s) × Lag(s) = K · (s+z₁)/(s+p₁) · (s+z₂)/(s+p₂)
  Lead: corrige transitório (PM, velocidade)
  Lag:  corrige regime permanente (erro zero)
```

---

## BLOCO 3 — CRITÉRIO DE NYQUIST (lec18–lec19)

### Plot de Nyquist

```
Plot de Nyquist de H(s): Im[H(jω)] vs. Re[H(jω)],   ω ∈ (−∞, +∞)
```

- É o **mapeamento** do eixo imaginário `jω` sob `H: ℂ → ℂ`
- Para `ω < 0`: imagem é o **conjugado espelhado** de `ω > 0`
- Para sistemas estritamente próprios: `H(j∞) → 0`

---

### O Teorema de Nyquist (1928) (lec18–lec19)

```
N = Z − P

onde:
  N = número de CIRCUITOS de −1/K pelo plot de Nyquist de G(s)  (CCW = positivo)
  Z = número de POLOS de MF no RHP (zeros de 1 + KG(s))
  P = número de POLOS de MA no RHP (de G(s))
```

**Critério de Estabilidade de Nyquist:**

```
Sistema em MF estável  ⟺  N = −P

(O plot de Nyquist de G(s) circula o ponto −1/K exatamente P vezes no sentido anti-horário)
```

---

### Workflow — Como Aplicar o Critério (lec19)

```
1. Determinar P (# polos de G(s) no RHP) — normalmente vem dado
2. Traçar o plot de Nyquist de G(jω) para ω ∈ [0, +∞)
   • Espelhar para ω < 0 (conjugado)
   • Se G(s) tiver polo na origem: contornar com semicírculo infinitesimal no RHP
3. Localizar o ponto −1/K no eixo real
4. Contar N = número de circuitos do ponto −1/K (CCW positivo)
5. Calcular Z = N + P
6. Verificar: Z = 0 → MF estável;  Z > 0 → Z polos instáveis de MF
```

#### Polo na origem de G(s)
```
Se G(s) tem polo em s = 0 (integrador):
  Percorrer semicírculo de raio r → 0 em s = r·e^(jθ), θ de −90° a +90°
  → Corresponde a arco de raio GRANDE (→ ∞) no plot de Nyquist
  → Sentido: horário se polo simples, duplo horário 2x, etc.
```

---

### GM e PM no Plot de Nyquist (lec19)

```
Fase de crossover ωφ: onde o plot cruza o EIXO REAL negativo
  GM = distância de −1/K até o ponto de cruzamento:
  GM = 1/|G(jωφ)|

Ganho de crossover ωc: onde o plot tem módulo = 1/K (|G(jωc)| = 1/K)
  PM = ângulo entre o ponto G(jωc) e o eixo real negativo
```

---

## Tabela-Resumo: Metodologias e Quando Usar

| Situação | Use | Técnica |
|----------|-----|---------|
| Especificação de polos dominantes (ζ, ωₙ) | Root Locus | Condição de fase + magnitude |
| Verificar estabilidade com parâmetro K | Root Locus | Cruzamento com jω (Regra F) |
| Rastreamento em regime (tipo do sistema) | RL + Lag | Adicionar polo/zero perto da origem |
| Especificação de PM e largura de banda | Bode | Lead/Lag no domínio de frequência |
| Dados experimentais (sem modelo) | Bode/Nyquist | Frequência response medida |
| Verificar estabilidade de MF sem Routh | Nyquist | Contar circuitos ao ponto −1/K |
| Sistema com atraso de transporte | Nyquist/Bode | PM se degrada com atraso |

---

## Fórmulas Rápidas para Prova

### Root Locus
```
• Ângulo das assíntotas:     θ = (180° + 360°k) / (n − m)
• Centro das assíntotas:     σ = (Σpᵢ − Σzᵢ) / (n − m)
• Condição de fase (teste):  Σ∠(s−zᵢ) − Σ∠(s−pᵢ) = ±180°
• Condição de magnitude:     K = |a(s*)| / |b(s*)|
• Ganho de cruzamento:       K_crítico via Routh sobre a(s) + Kb(s) = 0
```

### Bode
```
• Magnitude em dB:  M(dB) = 20·log₁₀|KG(jω)|
• Polo real 1ª ordem (1/(jωτ+1)):   quebra em ω=1/τ,  −20 dB/dec após
• Zero real 1ª ordem (jωτ+1):       quebra em ω=1/τ,  +20 dB/dec após
• Polo complexo 2ª ordem:           quebra em ω=ωₙ,   −40 dB/dec após
• PM = 180° + ∠KG(jωc)             (ωc: |KG|=1)
• GM = −20·log|KG(jωφ)| dB         (ωφ: ∠KG=−180°)
```

### Nyquist
```
• N = Z − P   →   Z = N + P   (Z=0 para MF estável)
• N: circuitos CCW ao ponto −1/K (CCW positivo, CW negativo)
• P: # polos de G(s) no RHP aberto
• Polo em origem: contornar com arco infinitesimal → arco de raio → ∞ no plot
```

### Relações PM ↔ Resposta Transitória (2ª ordem)
```
• ζ ≈ PM / 100                (PM em graus)
• %OS = exp(−πζ / √(1−ζ²)) × 100
• ts ≈ 4 / (ζωₙ)              (critério 2%)
• tr ≈ 1.8 / ωₙ
```

---

## Arquivos de Aula

| Arquivo | Conteúdo |
|---------|----------|
| `lec10.pdf` | Intro Root Locus; equação característica; qualificação de estabilidade |
| `lec11.pdf` | Regras A–F para esboço; intro compensação dinâmica |
| `lec12.pdf` | Compensador Lead; PD aproximado; exemplos |
| `lec13.pdf` | Compensador Lag; PI aproximado; intro Bode |
| `lec14.pdf` | Forma de Bode; 3 tipos de fatores; construção de plots |
| `lec15.pdf` | Estabilidade por frequência; GM; PM; relação com Root Locus |
| `lec16.pdf` | Projeto via Bode: Lead/PI; relação ganho-fase; largura de banda |
| `lec17.pdf` | Projeto via Bode: Lag/PD/PID; procedimento completo |
| `lec18.pdf` | Critério de Nyquist; plot Nyquist como mapeamento |
| `lec19.pdf` | Nyquist — mais exemplos; GM e PM no plot Nyquist |
