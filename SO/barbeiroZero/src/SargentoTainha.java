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
                int numeroAleatorio = random.nextInt(3); // Gera um número aleatório de 0 a 2
                adicionarElemento.acquire(); // Aguarde para adicionar um elemento
                Barbearia.addToFila(numeroAleatorio); // Adicione o número à fila
                adicionarElemento.release(); // Libere o semáforo após a adição
                lerFila.release(); // Sinalize que há algo para ler na fila
                System.out.println("Sargento Tainha adicionou o número " + numeroAleatorio + " à fila");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

    public static void  runSargentoTainha(Semaphore adicionarElemento,Semaphore lerFila){
        Thread sargento1 = new Thread(new SargentoTainha( adicionarElemento, lerFila));
        sargento1.start();
        try {
            sargento1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
