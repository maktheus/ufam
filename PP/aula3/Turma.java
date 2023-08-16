import java.util.ArrayList;

public class Turma {
    String disciplina;
    int ano;
    int semestre;
    Professor professor;
    ArrayList<Aluno> alunos;

    public Turma() {
        this.disciplina = "";
        this.ano = 0;
        this.semestre = 0;
        this.professor = new Professor();
        this.alunos = new ArrayList<Aluno>();
    }

    public Turma(String disciplina, int ano, int semestre, Professor professor) {
        this.disciplina = disciplina;
        this.ano = ano;
        this.semestre = semestre;
        this.professor = professor;
        this.alunos = new ArrayList<Aluno>();
    }

    public void addAluno(Aluno aluno) {
        //verify if aluno aredy with get aluno
        
        if (this.getAluno(aluno.matricula) != null) {
            return;
        }

        this.alunos.add(aluno);
    }

    public Aluno getAluno(int matricula) {
        for (int i = 0; i < this.alunos.size(); i++) {
            if (this.alunos.get(i).matricula == matricula) {
                return this.alunos.get(i);
            }
        }
        return null;
    }

    public double getMediaIdade() {
        double media = 0;
        for (int i = 0; i < this.alunos.size(); i++) {
            media += this.alunos.get(i).getIdade();
        }
        return media / this.alunos.size();
    }

    public String getDescricao() {

        //Turma Parapsicologia - 2010/2 (Prof. Dr. Henry Walton Jones Jr - mat 1982): [NL]   - Aluno 1: Egon Spengler (mat=4350, idade=29) [NL]   - Aluno 2: Peter Venkman (mat=4361, idade=33) [NL]   - Aluno 3: Raymond Stantz (mat=4372, idade=31)
        
        String descricao = "Turma " + this.disciplina + " - " + this.ano + "/" + this.semestre + " (Prof. " + this.professor.titulacao + " " + this.professor.nome + " - mat " + this.professor.matricula + "):\n";

        for (int i = 0; i < this.alunos.size(); i++) {
            //dont print [NL] on the last one

            if (i == this.alunos.size() - 1) {
                descricao += "  - Aluno " + (i + 1) + ": " + this.alunos.get(i).getDescricao();
            } 
            
            
            else {
                descricao += "  - Aluno " + (i + 1) + ": " + this.alunos.get(i).getDescricao() + "\n";
            }
        }

        return descricao;
    }
}
