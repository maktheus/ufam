import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Barbearia {
    static ConcurrentLinkedQueue<Integer> filaOficiais = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Integer> filaSargentos = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Integer> filaCabos = new ConcurrentLinkedQueue<>();

    private static final Semaphore semaforo = new Semaphore(1);
    static Map<Integer, Integer> atendimentosPorCategoria = new HashMap<>();
    static Map<Integer, Integer> tempoAtendimentoPorCategoria = new HashMap<>();
    static Map<Integer, Integer> tempoEsperaPorCategoria = new HashMap<>();
    static int totalClientes = 0;

    public static void printFila() {
        System.out.println("Fila Oficiais: " + filaOficiais);
        System.out.println("Fila Sargentos: " + filaSargentos);
        System.out.println("Fila Cabos: " + filaCabos);
    }

    // get the size of the fila unifieds
    public static int getSizeOfAllFilas() {
        return filaOficiais.size() + filaSargentos.size() + filaCabos.size();   
    }



    // Adicionar cliente à fila correspondente
    public static void addToFila(Cliente cliente) {
        switch (cliente.getCategoria()) {
            case 0: // Oficiais
                if (getSizeOfAllFilas() < 20)
                    filaOficiais.offer(cliente.getTempoServico());
                break;
            case 1: // Sargentos
                if (getSizeOfAllFilas() < 20)
                    filaSargentos.offer(cliente.getTempoServico());
                break;
            case 2: // Cabos
                if (getSizeOfAllFilas() < 20)
                    filaCabos.offer(cliente.getTempoServico());
                break;
        }
    }

    // Remover cliente da fila correspondente
    public static int removeFromFila(int categoria) {
        int clienteAtendido = -1; // valor padrão para caso a fila esteja vazia
        try {
            semaforo.acquire();
            switch (categoria) {
                case 0:
                    clienteAtendido = filaOficiais.poll() != null ? filaOficiais.poll() : -1;
                    break;
                case 1:
                    clienteAtendido = filaSargentos.poll() != null ? filaSargentos.poll() : -1;
                    break;
                case 2:
                    clienteAtendido = filaCabos.poll() != null ? filaCabos.poll() : -1;
                    break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaforo.release();
        }
        return clienteAtendido;
    }

    
    //isEmpty
    public static boolean isEmpty() {
        return getSizeOfAllFilas() == 0;
    }

    public static int removeMaiorPrioridade() {
        int clienteAtendido = -1; // valor padrão para caso a fila esteja vazia
        try {
            semaforo.acquire();
            if (filaOficiais.size() > 0) {
                clienteAtendido = filaOficiais.poll();
            } else if (filaSargentos.size() > 0) {
                clienteAtendido = filaSargentos.poll();
            } else if (filaCabos.size() > 0) {
                clienteAtendido = filaCabos.poll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaforo.release();
        }
        return clienteAtendido;        
    }

    public static int totalClientes(){
        return totalClientes;
    }

    public static int porcentagemDeOcupacao() {
        return (getSizeOfAllFilas() * 100) / 20;
    }

    // Método para obter o comprimento médio das filas
    public static synchronized double comprimentoMedioFila() {
        return (double) getSizeOfAllFilas() / 20;
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
