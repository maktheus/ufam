
public class EnsalamentoMain {
    public static void main(String[] args) {
        Ensalamento ensalamento = new Ensalamento();

        Sala sala1 = new Sala(6, 101, 50, true);
        Sala sala2 = new Sala(6, 102, 40, true);
        Sala sala3 = new Sala(7, 201, 30, true);
        Sala sala4 = new Sala(7, 202, 35, true);
        Sala sala5 = new Sala(8, 301, 30, true);
        Sala sala6 = new Sala(8, 302, 35, true);

        ensalamento.addSala(sala5);
        ensalamento.addSala(sala6);
        ensalamento.addSala(sala1);
        ensalamento.addSala(sala2);
        ensalamento.addSala(sala3);
        ensalamento.addSala(sala4);

        Turma turma1 = new Turma("Algoritmos e Estrutura de Dados I", "Edleno Silva", 60, true);
        turma1.addHorario(1); // segunda 8hs
        turma1.addHorario(15); // quarta 10hs
        turma1.addHorario(29); // sexta 12hs

        Turma turma2 = new Turma("Programação Orientada a Objetos", "Ana Souza", 45, true);
        turma2.addHorario(2); // segunda 10hs
        turma2.addHorario(16); // quarta 12hs
        turma2.addHorario(30); // sexta 14hs

        Turma turma3 = new Turma("Banco de Dados", "Carlos Rodrigues", 30, false);
        turma3.addHorario(3); // segunda 12hs
        turma3.addHorario(17); // quarta 14hs
        turma3.addHorario(31); // sexta 16hs

        Turma turma4 = new Turma("Engenharia de Software", "Maria Santos", 30, false);
        turma4.addHorario(4); // segunda 14hs
        turma4.addHorario(18); // quarta 16hs
        turma4.addHorario(32); // sexta 18hs

        Turma turma5 = new Turma("Sistemas Operacionais", "João Silva", 30, false);
        turma5.addHorario(5); // segunda 16hs
        turma5.addHorario(19); // quarta 18hs

        Turma turma6 = new Turma("Redes de Computadores", "José Santos", 30, false);
        turma6.addHorario(6); // segunda 18hs
        turma6.addHorario(20); // quarta 20hs

        Turma turma7 = new Turma("Inteligência Artificial", "Ana Souza", 30, false);
        turma7.addHorario(7); // segunda 20hs

        Turma turma8 = new Turma("Sistemas Distribuídos", "Carlos Rodrigues", 30, false);
        turma8.addHorario(8); // terça 8hs
        turma8.addHorario(21); // quinta 10hs
        

        Turma turma9 = new Turma("Programação para Web", "Maria Santos", 30, false);
        turma9.addHorario(9); // terça 10hs

        Turma turma10 = new Turma("Tópicos Especiais em Computação I", "João Silva", 30, false);
        turma10.addHorario(10); // terça 12hs
        turma10.addHorario(22); // quinta 14hs
        turma10.addHorario(33); // sexta 20hs

        Turma turma11 = new Turma("Tópicos Especiais em Computação II", "José Santos", 30, false);
        turma11.addHorario(11); // terça 14hs
        turma11.addHorario(23); // quinta 16hs

        Turma turma12 = new Turma("Tópicos Especiais em Computação III", "Ana Souza", 30, false);
        turma12.addHorario(12); // terça 16hs
        turma12.addHorario(24); // quinta 18hs


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

        System.out.println(ensalamento.relatorioTurmasPorSala());
        System.out.println(ensalamento.relatorioResumoEnsalamento());
    }
}
