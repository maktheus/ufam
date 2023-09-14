package urnaeletronica.BackEnd.Models;

public class Candidate  extends Voter{
    
    private String idForCandidate;

    public Candidate(String name, String cpf,   String etitulo, String idForCandidate) {
        super(name, cpf, etitulo);
        this.idForCandidate = idForCandidate;
    }

    public String getIdForCandidate() {
        return this.idForCandidate;
    }

}
