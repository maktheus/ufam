public class Processador{
    String marca;
    String modelo;
    double velocidade;
    int numNucleos;

    public Processador(){
        this.marca = "Intel";
        this.modelo = "i5";
        this.velocidade = 2.5;
        this.numNucleos = 4;
    }

    public Processador(String marca, String modelo, double velocidade, int numNucleos){
        this.marca = marca;
        this.modelo = modelo;
        this.velocidade = velocidade;
        this.numNucleos = numNucleos;
    }

    public double getVelocidadeParalela(){
        return this.velocidade * this.numNucleos;
    }

    public String getDescricao(){
        // Processador: marca=Intel, modelo=i7, velocidade=3.2GHz, numNucleos=8, velocidadeParalela=25.6GHz.
        return "Processador: marca=" + this.marca + ", modelo=" + this.modelo + ", velocidade=" + this.velocidade + "GHz, numNucleos=" + this.numNucleos + ", velocidadeParalela=" + this.getVelocidadeParalela() + "GHz.";
    }
}

