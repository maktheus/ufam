import java.util.Random;
import java.util.concurrent.*;

public class SargentoTainha implements Runnable {
    private Semaphore adicionarElemento;
    private Semaphore lerFila;
    private Random random = new Random();
    private long periodoDeCochilo;  // O período de cochilo em milissegundos

    public SargentoTainha(Semaphore adicionarElemento, Semaphore lerFila, long periodoDeCochilo) {
        this.adicionarElemento = adicionarElemento;
        this.lerFila = lerFila;
        this.periodoDeCochilo = periodoDeCochilo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {  // gera 1000 registros
            try {
                int categoria = gerarCategoriaAleatoria();
                int tempoServico = gerarTempoServicoAleatorio();

                adicionarElemento.acquire();  // Aguarde para adicionar um elemento

                if (categoria != 0) {  // Se não for uma pausa
                    Cliente cliente = new Cliente(categoria, tempoServico);
                    Barbearia.addToFila(cliente);  // Adicione o cliente à fila
                    System.out.println("Sargento Tainha adicionou " + cliente + " à fila");
                } else {
                    System.out.println("Pausa, ninguém na fila");
                }

                adicionarElemento.release();  // Libere o semáforo após a adição
                lerFila.release();  // Sinalize que há algo para ler na fila

                Thread.sleep((random.nextInt(5) + 1) * 1000);  // Cochilo aleatório entre 1 e 5 segundos

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    private int gerarCategoriaAleatoria() {
        return random.nextInt(4);  // Retorna um número aleatório entre 0 e 3
    }

    private int gerarTempoServicoAleatorio() {
        return random.nextInt(60) + 1;  // Retorna um número aleatório entre 1 e 60
    }


    public static void runSargentoTainha(Semaphore adicionarElemento, Semaphore lerFila, long periodoDeCochilo) {
        Thread sargento1 = new Thread(new SargentoTainha(adicionarElemento, lerFila, periodoDeCochilo));
        sargento1.start();
    }
}
