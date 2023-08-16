public class Professor {
    String titulacao;
    String nome;
    int matricula;

    public Professor() {
        this.titulacao = "";
        this.nome = "";
        this.matricula = 0;
    }

    public Professor(String titulacao, String nome, int matricula) {
        this.titulacao = titulacao;
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getDescricao() {
        //Prof. Dr. Hubert J. Farnsworth - mat 2208
        return "Prof. " + this.titulacao + " " + this.nome + " - mat " + this.matricula;
    }
}
