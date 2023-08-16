import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Turma {
    String nome;
    String professor;
    int numAlunos;
    boolean acessivel;
    ArrayList<Integer> horarios = new ArrayList<Integer>();
    private static final String[] DIAS_SEMANA = { "segunda", "terça", "quarta", "quinta", "sexta" };
    private static final int[] HORAS = { 8, 10, 12, 14, 16, 18, 20 };

    private Map<Integer, String> horarioDataTabela;

    public Turma() {
        this.nome = "";
        this.professor = "";
        this.numAlunos = 0;
        this.acessivel = false;
        this.horarios = new ArrayList<Integer>();
        horarioDataTabela = new HashMap<>();
        int contador = 0;
        for (int dia = 0; dia < DIAS_SEMANA.length; dia++) {
            for (int hora : HORAS) {
                horarioDataTabela.put(++contador, DIAS_SEMANA[dia] + " " + hora + "hs");
            }
        }
    }

    public Turma(String nome, String professor, int numAlunos, boolean acessivel) {
        this.nome = nome;
        this.professor = professor;
        this.numAlunos = numAlunos;
        this.acessivel = acessivel;
        horarioDataTabela = new HashMap<>();
        int contador = 0;
        for (int dia = 0; dia < DIAS_SEMANA.length; dia++) {
            for (int hora : HORAS) {
                horarioDataTabela.put(++contador, DIAS_SEMANA[dia] + " " + hora + "hs");
            }
        }
    }

    public void addHorario(int horario) {
        this.horarios.add(horario);
    }

    

    public String getHorariosString() {
        StringBuilder sb = new StringBuilder();
        for (int horario : horarios) {
            sb.append(horarioDataTabela.get(horario)).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);  // Remover a vírgula e o espaço no final
        }
        return sb.toString();
    }

    public String getDescricao(){
        //Turma: Algoritmos e Estrutura de Dados I
        // Professor: Edleno Silva
        // Número de Alunos: 60
        // Horário: segunda 8hs, quarta 8hs, sexta 8hs
        // Acessível: sim

        StringBuilder sb = new StringBuilder();
        sb.append("Turma: ").append(this.nome).append("\n");
        sb.append("Professor: ").append(this.professor).append("\n");
        sb.append("Número de Alunos: ").append(this.numAlunos).append("\n");
        sb.append("Horário: ").append(this.getHorariosString()).append("\n");
        sb.append("Acessível: ").append(this.acessivel ? "sim" : "não").append("\n");
        return sb.toString();
    }

}