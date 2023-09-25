import java.util.concurrent.*;

public class RecrutaZero implements Runnable {

    private Semaphore cortandoCabelo;
    private int totalDeClientes = 0;
    private int totalDeClientesCabos = 0;
    private int totalDeClientesSargentos = 0;
    private int totalDeClientesOficiais = 0;

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
                    break;
                }

                Cliente cliente = Barbearia.obterCliente();
                
                if (cliente != null) {
                    Barbearia.removeFromFila(cliente.getCategoria());
                    System.out.println("Recruta Zero está cortando o cabelo de um " + cliente);
                    Thread.sleep(cliente.getTempoServico());
                    System.out.println("Recruta Zero terminou de cortar o cabelo de um " + cliente);
                }
                cortandoCabelo.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

  

}
