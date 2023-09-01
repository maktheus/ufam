package br.edu.ufam.icomp.lab_excecoes;

public class Caminho{
    Coordenada[] caminho;
    int tamanho;

    public Caminho(int maxTam){
        this.tamanho = maxTam;
    }

    public int tamanho(){
        return this.tamanho;
    }

    public void addCoordenada(Coordenada c){
        this.caminho[this.tamanho] = c;
        this.tamanho++;
    }

    
}