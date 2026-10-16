# PDI — Projeto 7: Filtragem no domínio da frequência

Porte para **Python** do projeto (originalmente em MATLAB) de filtragem no
domínio da frequência: filtros **Butterworth** passa-baixa/passa-alta, **filtro
de alta ênfase (HEF)** e **equalização de histograma**.

## Requisitos
- Python 3.9+
- `make`

As dependências (numpy, matplotlib, pillow) são instaladas automaticamente num
ambiente virtual pelo `make`.

## Como rodar

```bash
make run
```

Isso cria o `.venv`, instala as dependências e gera todas as figuras em
`imgs_result/`.

### Outros alvos
| Comando | O que faz |
|---|---|
| `make run` | Gera todas as figuras (partes a, b e c) — modo headless |
| `make show` | Roda abrindo as janelas (precisa de display gráfico) |
| `make part-a` / `part-b` / `part-c` | Roda apenas uma parte |
| `make clean` | Remove as figuras geradas |
| `make distclean` | Remove figuras e o `.venv` |
| `make help` | Lista os alvos |

Sem `make`, dá para rodar direto:

```bash
pip install -r requirements.txt
python frequency_filters.py            # todas as partes
python frequency_filters.py --part a   # só a parte (a)
python frequency_filters.py --show     # abre janelas
```

## Entradas / Saídas
- **Entrada:** `imgs_orig/woman.tif`, `imgs_orig/chestXray.tif`
- **Saída:** `imgs_result/part_*.png`

## O que cada parte faz
- **(a)** `woman.tif`: espectro de Fourier e filtros Butterworth PB/PA (2D e 3D).
- **(b)** Filtro de alta ênfase `H = a + b·H_PA` (visualização 2D/3D e demonstração).
- **(c)** `chestXray.tif`: HEF seguido de equalização de histograma, com comparação
  de espectros e histogramas.
