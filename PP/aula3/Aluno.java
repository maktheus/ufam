import java.util.Calendar;

public class Aluno{

    String nome;
    int matricula;
    int anoNascimento;

    public Aluno(){
        this.nome = "";
        this.matricula = 0;
        this.anoNascimento = 0;
    }

    public Aluno(String nome, int matricula, int anoNascimento){
        this.nome = nome;
        this.matricula = matricula;
        this.anoNascimento = anoNascimento;
    }

    
    /** 
     * @return int
     */
    public int getIdade(){
        Calendar cal = Calendar.getInstance();
        int anoAtual = cal.get(Calendar.YEAR);
        return anoAtual - this.anoNascimento;
    }

    public String getDescricao(){
        //Emmett L. Brown (mat=7714, idade=22)
        return this.nome + " (mat=" + this.matricula + ", idade=" + this.getIdade() + ")";
    }
}