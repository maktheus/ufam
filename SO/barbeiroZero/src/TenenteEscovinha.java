
public class TenenteEscovinha implements Runnable {
        private final int timeOfSleep = 3;
        private int quantidadeDeVerificacoes = 0;

        private int comprimentoMedioDasFilas = 0;

        private int porcetagemMediaDeOcupacaoDasCadeirasTotal = 0;
        private int porcetagemMediaDeOcupacaoDasCadeirasOficiais = 0;
        private int porcetagemMediaDeOcupacaoDasCadeirasSargentos = 0;
        private int porcetagemMediaDeOcupacaoDasCadeirasCabos = 0;

        @Override
        public void run() {
                while (true) {
                        if (SargentoTainha.getStarted() == true) {
                                try {
                                        System.out.println("Tenente Escovinha está verificando a fila");
                                        this.comprimentoMedioDasFilas += Barbearia.getSizeOfAllFilas();

                                        this.quantidadeDeVerificacoes++;

                                        this.porcetagemMediaDeOcupacaoDasCadeirasTotal += (Barbearia.getSizeOfAllFilas()
                                                        * 100) / 20;
                                        this.porcetagemMediaDeOcupacaoDasCadeirasOficiais += (Barbearia
                                                        .getSizeOfOficiasFila() * 100) / 20;
                                                        
                                                        System.out.println("Barbearia.getSizeOfSargentosFila(): " + Barbearia.getSizeOfSargentosFila());
                                        this.porcetagemMediaDeOcupacaoDasCadeirasSargentos += (Barbearia
                                                        .getSizeOfSargentosFila() * 100) / 20;
                                        this.porcetagemMediaDeOcupacaoDasCadeirasCabos += (Barbearia
                                                        .getSizeOfCabosFila() * 100) / 20;

                                        Thread.sleep(timeOfSleep);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                } finally {
                                        if (RecrutaZero.getCorteFinalizados()) {
                                                System.out.println(
                                                                "Não há mais clientes para serem atendidos pelo Tenente Escovinha");
                                                break;
                                        }
                                }
                        }
                }

                System.out.println(this.printRelatorio());
        }

        public String printRelatorio() {
                // ● [X]Estado de ocupação da(s) cadeira(s) (% por categoria e livre)
                // ● [X]Comprimento médio das filas
                // ● [X]Tempo médio de atendimento por categoria
                // ● []Tempo médio de espera por categoria
                // ● [X]Número de atendimentos por categoria
                // ● [X]Número total de clientes por categoria (oficiais, sargentos, cabos e
                // pausa)

                String Barrinha = "------------------------------------------------------------------------------------------------------------------------\n";

                String ocupacaoCadeiras = "Estado de ocupação da(s) cadeira(s) (% por categoria e livre)\n"
                                + "Oficiais: "
                                + (double) this.porcetagemMediaDeOcupacaoDasCadeirasOficiais
                                                / (double) this.quantidadeDeVerificacoes
                                + "% "
                                + "\nSargentos: "
                                + (double) this.porcetagemMediaDeOcupacaoDasCadeirasSargentos
                                                / (double) this.quantidadeDeVerificacoes
                                + "% "
                                + "\nCabos: "
                                + (double) this.porcetagemMediaDeOcupacaoDasCadeirasCabos
                                                / (double) this.quantidadeDeVerificacoes
                                + "% "
                                + "\nTotal: "
                                + (double) this.porcetagemMediaDeOcupacaoDasCadeirasTotal
                                                / (double) this.quantidadeDeVerificacoes
                                + "% "
                                + "\n";
                String comprimentoMedioDasFilas = "Comprimento médio das filas: "
                                + (double) this.comprimentoMedioDasFilas / (double) this.quantidadeDeVerificacoes
                                + "\n";
                String tempoMedioDeAtendimentoPorCategoria = "Tempo médio de atendimento por categoria\n" + "Oficiais: "
                                + (double) RecrutaZero.getTempoMedioDeCorteOficiais()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosOficiais()
                                + "\nSargentos: "
                                + (double) RecrutaZero.getTempoMedioDeCorteSargentos()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosSargentos()
                                + "\nCabos: "
                                + (double) RecrutaZero.getTempoMedioDeCorteCabos()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosCabos()
                                + "\n" + "Total: " + (double) RecrutaZero.getTempoMedioDeCorteTotal()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosTotal()
                                + "\n";
                String tempoMedioDeEsperaPorCategoria = "Tempo médio de espera por categoria\n"
                                + "\nOficiais: "
                                + (double) RecrutaZero.getTempoMedioDeEsperaOficial()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosOficiais()
                                + "\nSargentos: "
                                + (double) RecrutaZero.getTempoMedioDeEsperaSargento()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosSargentos()
                                + "\nCabos: "
                                + (double) RecrutaZero.getTempoMedioDeEsperaCabo()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosCabos()
                                + "\nTotal: "
                                + (double) RecrutaZero.getTempoMedioDeEsperaTotal()
                                                / (double) Barbearia.getQuantidadeDeAtendimentosTotal()
                                + "\n";
                String numeroDeAtendimentosPorCategoria = "Número de atendimentos por categoria\n" + "Oficiais: "
                                + RecrutaZero.getQuantidadeDeAtendimentosOficiais() + "\nSargentos: "
                                + RecrutaZero.getQuantidadeDeAtendimentosSargentos() + "\nCabos: "
                                + RecrutaZero.getQuantidadeDeAtendimentosCabos() + "\n" + "Total: "
                                + RecrutaZero.getQuantidadeDeAtendimentosTotal() + "\n";
                String numeroTotalDeClientesPorCategoria = "Número total de clientes por categoria (oficiais, sargentos, cabos e pausa)\n"
                                + "Oficiais: " + Barbearia.getQuantidadeDeAtendimentosOficiais() + "\nSargentos: "
                                + Barbearia.getQuantidadeDeAtendimentosSargentos() + "\nCabos: "
                                + Barbearia.getQuantidadeDeAtendimentosCabos() + "\n" + "Pausa: "
                                + Barbearia.getQuantidadeDeAtendimentosPausa() + "\n";

                // ocupacao cadeiras
                return Barrinha + ocupacaoCadeiras + comprimentoMedioDasFilas + tempoMedioDeAtendimentoPorCategoria
                                + tempoMedioDeEsperaPorCategoria + numeroDeAtendimentosPorCategoria
                                + numeroTotalDeClientesPorCategoria + Barrinha;
        }
}
