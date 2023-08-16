public class Computador {
    String fabricante;
    Processador processador;
    Memoria memoria;
    Disco disco;

    public Computador(){
        this.fabricante = "Dell";
        this.processador = new Processador();
        this.memoria = new Memoria();
        this.disco = new Disco();
    }

    public Computador(String fabricante, Processador processador, Memoria memoria, Disco disco){
        this.fabricante = fabricante;
        this.processador = processador;
        this.memoria = memoria;
        this.disco = disco;
    }

    public String getDescricao(){
        //Computador da fabricante Dell. Processador: marca=Intel, modelo=i7, velocidade=3.2GHz, numNucleos=8, velocidadeParalela=25.6GHz. Memoria: marca=Kingston, tipo=DDR4, tamanho=8.0GB, velocidade=3.2GHz, numPentes=4, tamanhoTotal=32.0GB, velocidadeParalela=12.8GHz. Disco: marca=Western Digital, tipo=HDD, capacidade=4000.0GB, rpm=9600rpm.

        return "Computador da fabricante " + this.fabricante + ". " + this.processador.getDescricao() + " " + this.memoria.getDescricao() + " " + this.disco.getDescricao();
    }
}
