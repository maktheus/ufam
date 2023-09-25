import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Barbearia {
    public static boolean filaOutside = true;

    static ConcurrentLinkedQueue<Cliente> filaOficiais = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Cliente> filaSargentos = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Cliente> filaCabos = new ConcurrentLinkedQueue<>();
   

    static int tempoDeAtendimentoCadeira1 = 0;
    static int tempoDeAtendimentoCadeira2 = 0;
    static int tempoDeAtendimentoCadeira3 = 0;
   

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
    public static synchronized int getSizeOfAllFilas() {
        return filaOficiais.size() + filaSargentos.size() + filaCabos.size();   
    }



    // Adicionar cliente à fila correspondente
    public static void addToFila(Cliente cliente) {
        switch (cliente.getCategoria()) {
            case 1: // Oficiais
                if (getSizeOfAllFilas() < 20)
                    filaOficiais.offer(cliente);
                break;
            case 2: // Sargentos
                if (getSizeOfAllFilas() < 20)
                    filaSargentos.offer(cliente);
                break;
            case 3: // Cabos
                if (getSizeOfAllFilas() < 20)
                    filaCabos.offer(cliente);
                break;
        }
    }

    public static synchronized void removeFromFila(int categoria) {
        switch (categoria) {
            case 1:
                filaOficiais.poll();
                break;
            case 2:
                filaSargentos.poll();
                break;
            case 3:
                filaCabos.poll();
                break;
        }
    }

    public static synchronized Cliente obterCliente() {
        // Verifica as filas em ordem de prioridade
        if (!filaOficiais.isEmpty())
            return filaOficiais.peek();
        if (!filaSargentos.isEmpty())
            return filaSargentos.peek();
        if (!filaCabos.isEmpty())
            return filaCabos.peek();
        return null; // Nenhum cliente para ser atendido
    }

    public static synchronized boolean isFilaEmpty() {
        return filaOficiais.isEmpty() && filaSargentos.isEmpty() && filaCabos.isEmpty();
    }
}
