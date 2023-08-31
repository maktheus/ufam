package br.edu.ufam.icomp.lab_excecoes;

public class Caminho{
    private Coordenada[] caminho;
    private int tamanho;

    public Caminho(int maxTam){
        this.caminho = new Coordenada[maxTam];
        this.tamanho = 0;
    }

    public int tamanho(){
        return this.tamanho;
    }

    public void addCoordenada(Coordenada c) throws TamanhoMaximoExcedidoException, DistanciaEntrePontosExcedidaException{
        //         TamanhoMaximoExcedidoException: disparada ao tentar adicionar mais uma coordenada, mas o tamanho máximo foi atingido.
        // DistanciaEntrePontosExcedidaException: disparada quando a distância entre o último ponto adicionado e o ponto atual é maior que 15m.
        if(this.tamanho == this.caminho.length){
            throw new TamanhoMaximoExcedidoException();
        }
        
        if(this.tamanho > 0){
            if(this.caminho[this.tamanho-1].distancia(c) > 15){
                throw new DistanciaEntrePontosExcedidaException();
            }
        }
        this.caminho[this.tamanho] = c;
        this.tamanho++;

    }

    public void reset(){
        this.tamanho = 0;
    }

    public String toString(){
//         Dados do caminho:
//   - Quantidade de pontos: 6
//   - Pontos:
//     -> 32, 30
//     -> 35, 40
//     -> 38, 30
//     -> 30, 36
//     -> 40, 36
//     -> 33, 31


        String s = "Dados do caminho:\n";
        s += " - Quantidade de pontos: " + this.tamanho + "\n";
        s += " - Pontos:\n";
        for(int i = 0; i < this.tamanho; i++){
            s += "   -> " + this.caminho[i].toString() + "\n";
        }
        return s;
    } 

    
}