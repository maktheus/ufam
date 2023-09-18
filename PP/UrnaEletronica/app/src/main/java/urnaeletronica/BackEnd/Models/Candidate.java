package urnaeletronica.BackEnd.Models;

public class Candidate {
    
    private String candidateNumber;
    private String etitulo;


    public Candidate( String etitulo, String candidateNumber) {
        this.etitulo = etitulo;        
        this.candidateNumber = candidateNumber;
    }

    public String etitulo() {
        return this.etitulo;
    }

    public String candidateNumber() {
        return this.candidateNumber;
    }

}
