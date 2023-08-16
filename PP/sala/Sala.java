public class Sala{
    int bloco;
    int sala;
    int capacidade;
    boolean acessivel;

    public Sala(){
        this.bloco = 0;
        this.sala = 0;
        this.capacidade = 0;
        this.acessivel = false;
    }

    
    public Sala(int bloco, int sala, int capacidade, boolean acessivel){
        this.bloco = bloco;
        this.sala = sala;
        this.capacidade = capacidade;
        this.acessivel = acessivel;
    }

    public String getDescricao(){
        //Bloco 6, Sala 101 (50 lugares, acessível)
        return "Bloco " + this.bloco + ", Sala " + this.sala + " (" + this.capacidade + " lugares, " + (this.acessivel ? "acessível" : "não acessível") + ")";
    }
}