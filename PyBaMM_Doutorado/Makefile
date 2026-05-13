LATEX = pdflatex
BIBTEX = bibtex
MAIN = tese_pybamm_ufam

all: pdf

pdf:
	$(LATEX) $(MAIN).tex
	$(LATEX) $(MAIN).tex
	$(LATEX) $(MAIN).tex

clean:
	rm -f *.aux *.bbl *.blg *.log *.out *.toc *.lof *.lot \
	       *.idx *.ilg *.ind *.acn *.acr *.alg *.glg *.glo \
	       *.gls *.ist *.nlo *.nls *.synctex.gz

distclean: clean
	rm -f $(MAIN).pdf

.PHONY: all pdf clean distclean
