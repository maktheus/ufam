package urnaeletronica.BackEnd.Models;

public class Candidate  extends Voter{
    
    private String candidateNumber;

    public Candidate(String name, String cpf,   String etitulo, String candidateNumber) {
        super(name, cpf, etitulo);
        this.candidateNumber = candidateNumber;
    }

    public String candidateNumber() {
        return this.candidateNumber;
    }

}
