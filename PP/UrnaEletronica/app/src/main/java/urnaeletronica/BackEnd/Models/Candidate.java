package urnaeletronica.BackEnd.Models;

public class Candidate  {
    
    private String candidateNumber;
    private String etitulo;


    public Candidate( String etitulo, String candidateNumber) {
        this.etitulo = etitulo;        
        this.candidateNumber = candidateNumber;
    }

    public String getEtitulo() {
        return this.etitulo;
    }

    public String getIdForCandidate() {
        return this.candidateNumber;
    }
   
    public String toString() {
        return "Candidate Number: " + this.candidateNumber + "\n" +
               "E-Titulo: " + this.etitulo + "\n";
    }
}
