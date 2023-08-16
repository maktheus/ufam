public class Disco {
    String marca;
    String tipo;
    double capacidade;
    int rpm;

    public Disco(){
        this.marca = "Seagate";
        this.tipo = "HDD";
        this.capacidade = 1;
        this.rpm = 7200;
    }

    public Disco(String marca, String tipo, double capacidade, int rpm){
        this.marca = marca;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.rpm = rpm;
    }

    public String getDescricao(){
        // Disco: marca=Western Digital, tipo=HDD, capacidade=4000.0GB, rpm=9600rpm.
        return "Disco: marca=" + this.marca + ", tipo=" + this.tipo + ", capacidade=" + this.capacidade + "GB, rpm=" + this.rpm + "rpm.";
    }
}
