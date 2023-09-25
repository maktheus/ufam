
public class TenenteEscovinha implements Runnable {
   private final int timeOfSleep = 5;
   private int quantidadeDeVerificacoes= 0;
   private int tempoMedioDeEspera = 0;
   private int comprimentoMedioDasFilas = 0;


    @Override
    public void run() {
        while(true){

            try {
                System.out.println("Tenente Escovinha está verificando a fila");
                this.comprimentoMedioDasFilas += Barbearia.getSizeOfAllFilas();
                this.quantidadeDeVerificacoes++;
          
                Thread.sleep(timeOfSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                if(RecrutaZero.getCorteFinalizados()){
                    System.out.println("Não há mais clientes para serem atendidos pelo Tenente Escovinha");
                    break;
                }
            }
        }
       
        System.out.println(this.printRelatorio());
    }

    public String printRelatorio() {
        // ● []Estado de ocupação da(s) cadeira(s) (% por categoria e livre)
        // ● []Comprimento médio das filas 
        // ● []Tempo médio de atendimento por categoria
        // ● []Tempo médio de espera por categoria
        // ● [X]Número de atendimentos por categoria
        // ● [X]Número total de clientes por categoria (oficiais, sargentos, cabos e pausa)

        String ocupacaoCadeiras = "Estado de ocupação da(s) cadeira(s) (% por categoria e livre)\n";
        String comprimentoMedioDasFilas = "Comprimento médio das filas: " + (double)this.comprimentoMedioDasFilas/(double)this.quantidadeDeVerificacoes+ "\n";
        // String tempoMedioDeAtendimentoPorCategoria = "Tempo médio de atendimento por categoria\n"+ "Oficiais: " +  + "\nSargentos: " + this.quantidadeDeAtendimentosSargentos + "\nCabos: " + this.quantidadeDeAtendimentosCabos + "\n";
        String tempoMedioDeAtendimentoPorCategoria = "Tempo médio de atendimento por categoria\n";
        String tempoMedioDeEsperaPorCategoria = "Tempo médio de espera por categoria\n";
        String numeroDeAtendimentosPorCategoria = "Número de atendimentos por categoria\n"+  "Oficiais: " + RecrutaZero.getQuantidadeDeAtendimentosOficiais() + "\nSargentos: " + RecrutaZero.getQuantidadeDeAtendimentosSargentos() + "\nCabos: " + RecrutaZero.getQuantidadeDeAtendimentosCabos() + "\n" + "Total: " + RecrutaZero.getQuantidadeDeAtendimentosTotal() + "\n";
        String numeroTotalDeClientesPorCategoria = "Número total de clientes por categoria (oficiais, sargentos, cabos e pausa)\n"+ "Oficiais: " + Barbearia.getQuantidadeDeAtendimentosOficiais() + "\nSargentos: " + Barbearia.getQuantidadeDeAtendimentosSargentos() + "\nCabos: " + Barbearia.getQuantidadeDeAtendimentosCabos() + "\n" + "Pausa: " + Barbearia.getQuantidadeDeAtendimentosPausa() + "\n";

        
        // ocupacao cadeiras
        return ocupacaoCadeiras + comprimentoMedioDasFilas + tempoMedioDeAtendimentoPorCategoria + tempoMedioDeEsperaPorCategoria + numeroDeAtendimentosPorCategoria + numeroTotalDeClientesPorCategoria;
    }
}
