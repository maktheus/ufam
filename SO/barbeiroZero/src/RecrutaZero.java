import java.util.concurrent.*;

import javax.management.loading.PrivateClassLoader;

public class RecrutaZero implements Runnable {

    private Semaphore cortandoCabelo;
    private static boolean corteFinalizados = false;

    private static int quantidadeDeAtendimentosCabos = 0;
    private static int quantidadeDeAtendimentosOficiais = 0;
    private static int quantidadeDeAtendimentosSargentos = 0;
    private static int quantidadeDeAtendimentosTotal = 0;

    private static int tempoMedioDeCorteCabos = 0;
    private static int tempoMedioDeCorteOficiais = 0;
    private static int tempoMedioDeCorteSargentos = 0;
    private static int tempoMedioDeCorteTotal = 0;

    private int tempoExtraAposDescanso = 0;

    private static int tempoMedioDeEsperaTotal = 0;
    private static int tempoMedioDeEsperaOficial = 0;
    private static int tempoMedioDeEsperaSargento = 0;
    private static int tempoMedioDeEsperaCabo = 0;

    public RecrutaZero(Semaphore cortandoCabelo) {
        this.cortandoCabelo = cortandoCabelo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                cortandoCabelo.acquire();
                if (Barbearia.filaOutside == false && Barbearia.getSizeOfAllFilas() == 0) {
                    System.out.println("Não há mais clientes para serem atendidos pelo Recruta Zero");
                    corteFinalizados = true;
                    break;
                }

                Cliente cliente = Barbearia.obterCliente();

                if (cliente != null) {
                    if( tempoExtraAposDescanso > 0){ 
                        tempoMedioDeEsperaTotal +=  tempoExtraAposDescanso;
                        System.out.println(Barbearia.getTempoMedioDeEsperaSaida());
                        System.out.println(cliente.getEntradaFilaTimestamp());
                        System.out.println("\nTempo médio de espera total: " + tempoMedioDeEsperaTotal);
                        // print categoria
                        
                        System.out.println(cliente.getCategoria());
                        if (cliente.getCategoria() == 1) {
                            tempoMedioDeEsperaOficial += tempoExtraAposDescanso;
                        } else if (cliente.getCategoria() == 2) {
                            tempoMedioDeEsperaSargento += tempoExtraAposDescanso;
                        } else if (cliente.getCategoria() == 3) {
                            tempoMedioDeEsperaCabo += tempoExtraAposDescanso;
                        }
                    }
                    

                    if(cliente.getTempoServico() + tempoExtraAposDescanso > SargentoTainha.getTempoDeDescanso()){
                        tempoExtraAposDescanso += cliente.getTempoServico() - SargentoTainha.getTempoDeDescanso();
                    }else{
                        tempoExtraAposDescanso = 0;
                    }

                    

                    Barbearia.setTempoMedioDeEsperaSaida(cliente.getTempoServico());

                    quantidadeDeAtendimentosTotal++;
                    tempoMedioDeCorteTotal += cliente.getTempoServico();
                    if (cliente.getCategoria() == 1) {
                        quantidadeDeAtendimentosOficiais++;
                        tempoMedioDeCorteOficiais += cliente.getTempoServico();
                    } else if (cliente.getCategoria() == 2) {
                        quantidadeDeAtendimentosSargentos++;
                        tempoMedioDeCorteSargentos += cliente.getTempoServico();
                    } else if (cliente.getCategoria() == 3) {
                        quantidadeDeAtendimentosCabos++;
                        tempoMedioDeCorteCabos += cliente.getTempoServico();
                    }

                    Thread.sleep(cliente.getTempoServico());

                    Barbearia.removeFromFila(cliente.getCategoria());
                    System.out.println("Recruta Zero está cortando o cabelo de um " + cliente);
                    System.out.println("Recruta Zero terminou de cortar o cabelo de um " + cliente);
                }
                cortandoCabelo.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getTempoMedioDeEsperaTotal() {
        return tempoMedioDeEsperaTotal;
    }

    public static synchronized int getTempoMedioDeEsperaOficial() {
        return tempoMedioDeEsperaOficial;
    }

    public static synchronized int getTempoMedioDeEsperaSargento() {
        return tempoMedioDeEsperaSargento;
    }

    public static synchronized int getTempoMedioDeEsperaCabo() {
        return tempoMedioDeEsperaCabo;
    }

    public static synchronized int getQuantidadeDeAtendimentosOficiais() {
        return quantidadeDeAtendimentosOficiais;
    }

    public static synchronized int getQuantidadeDeAtendimentosSargentos() {
        return quantidadeDeAtendimentosSargentos;
    }

    public static synchronized int getQuantidadeDeAtendimentosCabos() {
        return quantidadeDeAtendimentosCabos;
    }

    public static synchronized int getQuantidadeDeAtendimentosTotal() {
        return quantidadeDeAtendimentosTotal;
    }

    public static synchronized int getTempoMedioDeCorteCabos() {
        return tempoMedioDeCorteCabos;
    }

    public static synchronized int getTempoMedioDeCorteOficiais() {
        return tempoMedioDeCorteOficiais;
    }

    public static synchronized int getTempoMedioDeCorteSargentos() {
        return tempoMedioDeCorteSargentos;
    }

    public static synchronized int getTempoMedioDeCorteTotal() {
        return tempoMedioDeCorteTotal;
    }

    public static synchronized boolean getCorteFinalizados() {
        return corteFinalizados;
    }
}
