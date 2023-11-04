import java.util.concurrent.*;

public class Barbearia {
    public static boolean filaOutside = true;

    static ConcurrentLinkedQueue<Cliente> filaOficiais = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Cliente> filaSargentos = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Cliente> filaCabos = new ConcurrentLinkedQueue<>();


    private static int quantidadeDeAtendimentosCabos = 0;
    private static int quantidadeDeAtendimentosOficiais = 0;
    private static int quantidadeDeAtendimentosSargentos = 0;
    private static int quantidadeDeAtendimentosTotal = 0;
    private static int quantidadeDeAtendimentosPausa = 0;
    
    private static int tempoMedioDeEsperaEntrada = 0;
    private static int tempoMedioDeEsperaSaida = 0;
    
    public static synchronized int getTempoMedioDeEsperaEntrada() {
        return tempoMedioDeEsperaEntrada;
    }


    public static synchronized void setTempoMedioDeEsperaEntrada(int tempoMedioDeEsperaEntrada) {
        Barbearia.tempoMedioDeEsperaEntrada += tempoMedioDeEsperaEntrada;
    }

    public static synchronized int getTempoMedioDeEsperaSaida() {
        return tempoMedioDeEsperaSaida;
    }

    public static synchronized void setTempoMedioDeEsperaSaida(int tempoMedioDeEsperaSaida) {
        Barbearia.tempoMedioDeEsperaSaida += tempoMedioDeEsperaSaida;
    }


    public static void printFila() {
        System.out.println("Fila Oficiais: " + filaOficiais);
        System.out.println("Fila Sargentos: " + filaSargentos);
        System.out.println("Fila Cabos: " + filaCabos);
    }

    // get the size of the fila unifieds
    public static synchronized int getSizeOfAllFilas() {
        return filaOficiais.size() + filaSargentos.size() + filaCabos.size();   
    }

    public static synchronized int getSizeOfOficiasFila() {
        return filaOficiais.size();
    }

    public static synchronized int getSizeOfSargentosFila() {
        return filaSargentos.size();
    }

    public static synchronized int getSizeOfCabosFila() {
        return filaCabos.size();
    }



    // Adicionar cliente à fila correspondente
    public static void addToFila(Cliente cliente) {
        quantidadeDeAtendimentosTotal++;
        switch (cliente.getCategoria()) {
            case 0: // Pausa
                quantidadeDeAtendimentosPausa++;
                break;
            case 1: // Oficiais
                if (getSizeOfAllFilas() < 20){

                    quantidadeDeAtendimentosOficiais++;
                    filaOficiais.offer(cliente);
                }
                break;
            case 2: // Sargentos
                if (getSizeOfAllFilas() < 20){
                    quantidadeDeAtendimentosSargentos++;
                    filaSargentos.offer(cliente);
                }
                break;
            case 3: // Cabos
                if (getSizeOfAllFilas() < 20){
                    quantidadeDeAtendimentosCabos++;
                    filaCabos.offer(cliente);
                }
                break;
        }
    }

    public static synchronized int getQuantidadeDeAtendimentosTotal() {
        return quantidadeDeAtendimentosTotal;
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

    public static synchronized int getQuantidadeDeAtendimentosPausa() {
        return quantidadeDeAtendimentosPausa;
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
