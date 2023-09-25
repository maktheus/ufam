import java.util.Random;
import java.util.concurrent.*;

public class SargentoTainha implements Runnable {
    private Semaphore adicionarElemento;
    private Semaphore lerFila;
    private Random random = new Random();

    

    public SargentoTainha(Semaphore adicionarElemento, Semaphore lerFila) {
        this.adicionarElemento = adicionarElemento;
        this.lerFila = lerFila;
    }

    @Override
    public void run() {

        try {
            adicionarElemento.acquire(); // Aguarde para adicionar um elemento
            Cliente cliente = generateClienteAleatorio(); // Gere um cliente aleatório

            if (cliente.getCategoria() != 0) { // Se não for uma pausa
                Barbearia.addToFila(cliente); // Adicione o cliente à fila
                System.out.println("Sargento Tainha adicionou " + cliente + " à fila");
            } else {
                System.out.println("Pausa, ninguém na fila");
            }

            adicionarElemento.release(); // Libere o semáforo após a adição
            lerFila.release(); // Sinalize que há algo para ler na fila

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int gerarCategoriaAleatoria() {
        return random.nextInt(4); // Retorna um número aleatório entre 0 e 3
    }

    private int gerarTempoServicoAleatorio(int type) {
        // Cada corte de cabelo pode durar entre 4 e 6 segundos no caso de um
        // oficial, de 2 a 4 segundos no caso de um sargento e de 1 a 3 segundos no caso
        // de um cabo. A
        // prioridade de atendimento será sempre: oficiais – sargentos – cabos.

        switch (type) {
            case 0:
                return random.nextInt(3) + 1;
            case 1:
                return random.nextInt(3) + 2;
            case 2:
                return random.nextInt(3) + 4;
            default:
                return 0;
        }
    }

    private Cliente generateClienteAleatorio() {
        int categoria = gerarCategoriaAleatoria();
        if(categoria == 0) {
            return new Cliente(categoria, 0);
        }
        int tempoServico = gerarTempoServicoAleatorio(categoria);
        return new Cliente(categoria, tempoServico);
    }

    public static void runSargentoTainha(Semaphore adicionarElemento, Semaphore lerFila) {
        Thread sargento1 = new Thread(new SargentoTainha(adicionarElemento, lerFila));
        sargento1.start();
    }
}
