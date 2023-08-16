public class Memoria {
    String marca;
    String tipo;
    double tamanho;
    double velocidade;
    int numPentes;

    public Memoria(){
        this.marca = "Kingston";
        this.tipo = "DDR4";
        this.tamanho = 8;
        this.velocidade = 2400;
        this.numPentes = 1;
    }

    public Memoria(String marca, String tipo, double tamanho, double velocidade, int numPentes){
        this.marca = marca;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.velocidade = velocidade;
        this.numPentes = numPentes;
    }

    public double getTamanhoTotal(){
        return this.tamanho * this.numPentes;
    }

    public double getVelocidadeParalela(){
        return this.velocidade * this.numPentes;
    }

    public String getDescricao(){
//Memoria: marca=Kingston, tipo=DDR4, tamanho=8.0GB, velocidade=3.2GHz, numPentes=4, tamanhoTotal=32.0GB, velocidadeParalela=12.8GHz.
        return "Memoria: marca=" + this.marca + ", tipo=" + this.tipo + ", tamanho=" + this.tamanho + "GB, velocidade=" + this.velocidade + "GHz, numPentes=" + this.numPentes + ", tamanhoTotal=" + this.getTamanhoTotal() + "GB, velocidadeParalela=" + this.getVelocidadeParalela() + "GHz.";
    }

}
