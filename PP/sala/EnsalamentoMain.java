
public class EnsalamentoMain {
    public static void main(String[] args) {
        Ensalamento ensalamento = new Ensalamento();

        Sala sala1 = new Sala(6, 101, 50, true);
        Sala sala2 = new Sala(6, 102, 40, true);
        Sala sala3 = new Sala(7, 201, 30, true);
        Sala sala4 = new Sala(7, 202, 35, true);
        Sala sala5 = new Sala(8, 301, 30, true);
        Sala sala6 = new Sala(8, 302, 65, true);

        ensalamento.addSala(sala5);
        ensalamento.addSala(sala6);
        ensalamento.addSala(sala1);
        ensalamento.addSala(sala2);
        ensalamento.addSala(sala3);
        ensalamento.addSala(sala4);
        
        // HORAS = { 8, 10, 12, 14, 16, 18, 20 };

        Turma turma1 = new Turma("Algoritmos e Estrutura de Dados I", "Edleno Silva", 60, true);
        turma1.addHorario(8); //
        turma1.addHorario(10); 
        turma1.addHorario(12); 

        Turma turma2 = new Turma("Programação Orientada a Objetos", "Ana Souza", 45, true);
        turma2.addHorario(2); 
        turma2.addHorario(16); 
        turma2.addHorario(18); 

        Turma turma3 = new Turma("Banco de Dados", "Carlos Rodrigues", 30, false);
        turma3.addHorario(16);
        turma3.addHorario(18);
        turma3.addHorario(20);

        Turma turma4 = new Turma("Engenharia de Software", "Maria Santos", 30, false);
        turma4.addHorario(8);
        turma4.addHorario(10);
        turma4.addHorario(12);

        Turma turma5 = new Turma("Sistemas Operacionais", "João Silva", 30, false);
        turma5.addHorario(8);
        turma5.addHorario(10);
        turma5.addHorario(12);

        Turma turma6 = new Turma("Redes de Computadores", "José Santos", 30, false);
        turma6.addHorario(8);
        turma6.addHorario(10);

        Turma turma7 = new Turma("Inteligência Artificial", "Ana Souza", 30, false);
        turma7.addHorario(8);
        turma7.addHorario(20);

        Turma turma8 = new Turma("Sistemas Distribuídos", "Carlos Rodrigues", 30, false);
        turma8.addHorario(8);
        turma8.addHorario(10);
        turma8.addHorario(12);
        

        Turma turma9 = new Turma("Programação para Web", "Maria Santos", 30, false);
        turma9.addHorario(8);
        turma9.addHorario(10);
        turma9.addHorario(12);
        turma9.addHorario(14);
        turma9.addHorario(16);
        turma9.addHorario(18);
        turma9.addHorario(20);

        Turma turma10 = new Turma("Tópicos Especiais em Computação I", "João Silva", 30, false);
        turma10.addHorario(8);


        Turma turma11 = new Turma("Tópicos Especiais em Computação II", "José Santos", 30, false);
         turma10.addHorario(18);
            turma10.addHorario(20);

        Turma turma12 = new Turma("Tópicos Especiais em Computação III", "Ana Souza", 30, false);
        turma10.addHorario(16);


        ensalamento.addTurma(turma4);
        ensalamento.addTurma(turma5);
        ensalamento.addTurma(turma6);
        ensalamento.addTurma(turma7);
        ensalamento.addTurma(turma8);
        ensalamento.addTurma(turma9);
        ensalamento.addTurma(turma10);
        ensalamento.addTurma(turma11);
        ensalamento.addTurma(turma12);

        
        ensalamento.addTurma(turma1);
        ensalamento.addTurma(turma2);
        ensalamento.addTurma(turma3);

        ensalamento.alocarTodas();

        // System.out.println(ensalamento.relatorioTurmasPorSala());
        System.out.println(ensalamento.relatorioResumoEnsalamento());
    }
}
