# Makefile - PDI Projeto 7 (Filtragem no dominio da frequencia)
# Uso principal:  make run
#
# Cria um ambiente virtual, instala as dependencias e executa o script,
# gerando as figuras em imgs_result/.

PY      ?= python3
VENV    := .venv
BIN     := $(VENV)/bin
SCRIPT  := frequency_filters.py

.DEFAULT_GOAL := run

## venv: cria o ambiente virtual e instala as dependencias
$(VENV)/.stamp: requirements.txt
	$(PY) -m venv $(VENV)
	$(BIN)/pip install --upgrade pip
	$(BIN)/pip install -r requirements.txt
	@touch $(VENV)/.stamp

.PHONY: venv
venv: $(VENV)/.stamp

## run: gera todas as figuras (partes a, b e c) - headless
.PHONY: run
run: $(VENV)/.stamp
	$(BIN)/python $(SCRIPT)

## show: roda abrindo as janelas (precisa de display grafico)
.PHONY: show
show: $(VENV)/.stamp
	$(BIN)/python $(SCRIPT) --show

## part-a / part-b / part-c: roda apenas uma parte
.PHONY: part-a part-b part-c
part-a: $(VENV)/.stamp
	$(BIN)/python $(SCRIPT) --part a
part-b: $(VENV)/.stamp
	$(BIN)/python $(SCRIPT) --part b
part-c: $(VENV)/.stamp
	$(BIN)/python $(SCRIPT) --part c

## clean: remove as figuras geradas
.PHONY: clean
clean:
	rm -f imgs_result/part_*.png

## distclean: remove figuras e o ambiente virtual
.PHONY: distclean
distclean: clean
	rm -rf $(VENV)

## help: lista os alvos disponiveis
.PHONY: help
help:
	@grep -E '^## ' $(MAKEFILE_LIST) | sed 's/## /  /'
