import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Barbearia {
    static ConcurrentLinkedQueue<Integer> fila = new ConcurrentLinkedQueue<>();
    static Map<Integer, Integer> atendimentosPorCategoria = new HashMap<>();
    static Map<Integer, Integer> tempoAtendimentoPorCategoria = new HashMap<>();
    static Map<Integer, Integer> tempoEsperaPorCategoria = new HashMap<>();
    static int totalClientes = 0;

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

    public static int porcentagemDeOcupacao() {
        return (fila.size() * 100) / 20;
    }

    // Método para obter o comprimento médio das filas
    public static synchronized double comprimentoMedioFila() {
        return (double) fila.size() / 20;
    }

    // Método para adicionar informações sobre o atendimento
    public static synchronized void registrarAtendimento(int categoria, int tempoAtendimento, int tempoEspera) {
        atendimentosPorCategoria.merge(categoria, 1, Integer::sum);
        tempoAtendimentoPorCategoria.merge(categoria, tempoAtendimento, Integer::sum);
        tempoEsperaPorCategoria.merge(categoria, tempoEspera, Integer::sum);
        totalClientes++;
    }

    // Método para obter a porcentagem de ocupação por categoria
    public static synchronized Map<Integer, Double> porcentagemOcupacaoPorCategoria() {
        Map<Integer, Double> porcentagemPorCategoria = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : atendimentosPorCategoria.entrySet()) {
            porcentagemPorCategoria.put(entry.getKey(), (double) entry.getValue() / totalClientes * 100);
        }
        return porcentagemPorCategoria;
    }

    public static synchronized Map<Integer, Double> tempoMedioAtendimentoPorCategoria() {
        Map<Integer, Double> tempoMedioPorCategoria = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : tempoAtendimentoPorCategoria.entrySet()) {
            int categoria = entry.getKey();
            int totalTempo = entry.getValue();
            int totalAtendimentos = atendimentosPorCategoria.get(categoria);
            tempoMedioPorCategoria.put(categoria, (double) totalTempo / totalAtendimentos);
        }
        return tempoMedioPorCategoria;
    }

    // Método para obter o tempo médio de espera por categoria
    public static synchronized Map<Integer, Double> tempoMedioEsperaPorCategoria() {
        Map<Integer, Double> tempoMedioPorCategoria = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : tempoEsperaPorCategoria.entrySet()) {
            int categoria = entry.getKey();
            int totalTempo = entry.getValue();
            int totalAtendimentos = atendimentosPorCategoria.get(categoria);
            tempoMedioPorCategoria.put(categoria, (double) totalTempo / totalAtendimentos);
        }
        return tempoMedioPorCategoria;
    }

    // Método para obter o número de atendimentos por categoria
    public static synchronized Map<Integer, Integer> numeroAtendimentosPorCategoria() {
        return new HashMap<>(atendimentosPorCategoria);
    }
}
