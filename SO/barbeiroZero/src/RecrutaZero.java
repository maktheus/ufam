import java.util.concurrent.*;

public class RecrutaZero implements Runnable {

    private Semaphore cortandoCabelo;
    private static boolean corteFinalizados = false;

    private static int quantidadeDeAtendimentosCabos = 0;
    private static int quantidadeDeAtendimentosOficiais = 0;
    private static int quantidadeDeAtendimentosSargentos = 0;
    private static int quantidadeDeAtendimentosTotal = 0;



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
                    quantidadeDeAtendimentosTotal++;
                    if(cliente.getCategoria() == 1){
                        quantidadeDeAtendimentosOficiais++;
                    }else if(cliente.getCategoria() == 2){
                        quantidadeDeAtendimentosSargentos++;
                    }else if(cliente.getCategoria() == 3){
                        quantidadeDeAtendimentosCabos++;
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

    public static synchronized boolean getCorteFinalizados() {
        return corteFinalizados;
    }
}
