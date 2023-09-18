package urnaeletronica.BackEnd.Models;

public class Voto {

    private String etitulo;
    private String candidate;

    public Voto( String etitulo, String candidate) {
        this.etitulo = etitulo;
        this.candidate = candidate;
    }

    
    public String getEtitulo() {
        return this.etitulo;
    }

    public String getCandidate() {
        return this.candidate;
    }

    public String toString() {
        return "{" +
            ", etitulo='" + getEtitulo() + "'" +
            ", candidate='" + getCandidate() + "'" +
            "}";
    }
    
}
