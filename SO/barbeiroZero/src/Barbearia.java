import java.util.concurrent.*;

public class Barbearia {
    static ConcurrentLinkedQueue<Integer> fila = new ConcurrentLinkedQueue<>();

    public static void printFila() {
        System.out.println(fila);
    }

    public static boolean isPossibleToAddToFila() {
        if (fila.size() < 20) {
            return true;
        } else {
            return false;
        }
    }

    // add to fila
    public static void addToFila(int numeroAleatorio) {
        if (!isPossibleToAddToFila()) {
            System.out.println("Fila cheia");
            return;
        }
        fila.offer(numeroAleatorio);
    }

    // remove from fila
    public static void removeFromFila() {
        if (isEmpty()) {
            System.out.println("Fila vazia");
            return;
        }
        fila.poll();
    }

    // Verificar maior prioridade (0 a 2)
    public static int getMaiorPrioridade() {
        int maiorPrioridade = Integer.MIN_VALUE;

        for (int elemento : fila) {
            if (elemento > maiorPrioridade) {
                maiorPrioridade = elemento;
            }
        }

        return maiorPrioridade;
    }

    // Verificar menor prioridade (0 a 2)
    public static int getMenorPrioridade() {
        int menorPrioridade = Integer.MAX_VALUE;

        for (int elemento : fila) {
            if (elemento < menorPrioridade) {
                menorPrioridade = elemento;
            }
        }

        return menorPrioridade;
    }

    // verificar se a fila esta vazia
    public static boolean isEmpty() {
        if (fila.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static int removeMaiorPrioridade() {
        if (isEmpty()) {
            System.out.println("Fila vazia");
            return -1;
        }

        int elementoRemover = getMaiorPrioridade();
        fila.remove(elementoRemover);
        
        return elementoRemover;
    }

    // Remover o elemento com menor prioridade
    public static int removeMenorPrioridade() {
        if (isEmpty()) {
            System.out.println("Fila vazia");
            return -1;
        }

        int elementoRemover = getMenorPrioridade();
        fila.remove(elementoRemover);

        return elementoRemover;
    }

}
