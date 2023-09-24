import java.util.*;
import java.util.concurrent.*;

public class TenenteEscovinha implements Runnable {
    private long tempoInicial;
    private long periodoDeSimulacao;  // tempo total da simulação em milissegundos
    private long intervaloDeColeta = 3000;  // intervalo de coleta de dados em milissegundos

    public TenenteEscovinha(long periodoDeSimulacao) {
        this.tempoInicial = System.currentTimeMillis();
        this.periodoDeSimulacao = periodoDeSimulacao;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() - tempoInicial < periodoDeSimulacao) {
            try {
                // Coletar dados a cada 3 segundos
                Thread.sleep(intervaloDeColeta);
                // ... coleta de dados da Barbearia, por exemplo:
                Barbearia.porcentagemOcupacaoPorCategoria();
                Barbearia.tempoMedioAtendimentoPorCategoria();
                // etc.
            } catch (InterruptedException e) {
                // Tratar interrupção
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Elaborar o relatório ao final do período de simulação
        elaborarRelatorio();
    }

    public void elaborarRelatorio() {
        // Exemplo de como elaborar um relatório
        Map<Integer, Double> porcentagemOcupacao = Barbearia.porcentagemOcupacaoPorCategoria();
        Map<Integer, Double> tempoMedioAtendimento = Barbearia.tempoMedioAtendimentoPorCategoria();
        Map<Integer, Double> tempoMedioEspera = Barbearia.tempoMedioEsperaPorCategoria();
        Map<Integer, Integer> numeroAtendimentos = Barbearia.numeroAtendimentosPorCategoria();
        
        System.out.println("Relatório de atendimento");
        System.out.println("Categoria\t% Ocupação\tTempo médio de atendimento\tTempo médio de espera\tNúmero de atendimentos");
        for (int categoria = 0; categoria < 5; categoria++) {
            System.out.println(categoria + "\t" + porcentagemOcupacao.get(categoria) + "\t" + tempoMedioAtendimento.get(categoria) + "\t" + tempoMedioEspera.get(categoria) + "\t" + numeroAtendimentos.get(categoria));
        }

        System.out.println("Comprimento médio da fila: " + Barbearia.comprimentoMedioFila());

        System.out.println("Número de clientes atendidos: " + Barbearia.totalClientes());
        
    }

    public static void runTenenteEscovinha(long periodoDeSimulacao){
        Thread tenenteThread = new Thread(new TenenteEscovinha(periodoDeSimulacao));
        tenenteThread.start();
    }
}
