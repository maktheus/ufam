import java.util.Random;
import java.util.concurrent.*;

public class RecrutaZero implements Runnable {
    private Semaphore cortandoCabelo;
    private Random random = new Random();

    public RecrutaZero(Semaphore cortandoCabelo){
        this.cortandoCabelo = cortandoCabelo;
    }

    @Override
    public void run(){
        try {
            cortandoCabelo.acquire();  // Adquire o semáforo antes de começar o corte de cabelo

            int categoria = obterCategoriaCliente();  // Obtém a categoria do próximo cliente
            int tempoCorte = obterTempoCorte(categoria);  // Obtém o tempo de corte com base na categoria

            if (categoria != -1) {  // Se houver um cliente para ser atendido
                System.out.println("Recruta Zero está cortando o cabelo de um " + getCategoriaNome(categoria) + " por " + tempoCorte + " segundos.");
                Thread.sleep(tempoCorte * 1000);  // Simula o corte de cabelo
                Barbearia.removeFromFila(categoria);  // Remove o cliente da fila
            } else {
                System.out.println("Nenhum cliente para atender no momento.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cortandoCabelo.release();  // Libera o semáforo depois de terminar o corte de cabelo
        }
    }

    public static void runRecrutaZero(Semaphore cortandoCabelo){
        Thread recrutaZero = new Thread(new RecrutaZero(cortandoCabelo));
        recrutaZero.start();
    }

    private int obterCategoriaCliente() {
        // Verifica as filas em ordem de prioridade
        if (!Barbearia.filaOficiais.isEmpty()) return 0;
        if (!Barbearia.filaSargentos.isEmpty()) return 1;
        if (!Barbearia.filaCabos.isEmpty()) return 2;
        return -1;  // Nenhum cliente para ser atendido
    }

    private int obterTempoCorte(int categoria) {
        switch (categoria) {
            case 0: return random.nextInt(3) + 4;  // Oficial: 4 a 6 segundos
            case 1: return random.nextInt(3) + 2;  // Sargento: 2 a 4 segundos
            case 2: return random.nextInt(3) + 1;  // Cabo: 1 a 3 segundos
            default: return 0;
        }
    }

    private String getCategoriaNome(int categoria) {
        switch (categoria) {
            case 0: return "oficial";
            case 1: return "sargento";
            case 2: return "cabo";
            default: return "desconhecido";
        }
    }
}
