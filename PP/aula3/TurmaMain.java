public class TurmaMain {
    public static void main(String[] args) {
        Professor professor = new Professor("Dr.", "Hubert J. Farnsworth", 2208);
        Turma turma = new Turma("Iniciação Tecnológica e Científica", 2016, 1, professor);

        Aluno aluno1 = new Aluno("Emmett L. Brown", 7714, 1994);
        Aluno aluno2 = new Aluno("Egon Spengler", 5907, 1996);
        Aluno aluno3 = new Aluno("Peter Weyland", 7734, 1997);

        turma.addAluno(aluno1);
        turma.addAluno(aluno2);
        turma.addAluno(aluno3);

        System.out.println(turma.getDescricao());
        System.out.println("Média de idade: " + turma.getMediaIdade());
    }
    
}
