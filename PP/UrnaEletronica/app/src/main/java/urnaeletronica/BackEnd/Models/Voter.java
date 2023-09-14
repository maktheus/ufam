package urnaeletronica.BackEnd.Models;

public  class Voter   {
    private String name;
    private String cpf;
    private String etitulo;

    public  Voter(String name, String cpf, String etitulo) {
        this.name = name;
        this.cpf = cpf;
        this.etitulo = etitulo;
    }

    public String getName() {
        return this.name;
    }

    public String getCpf() {
        return this.cpf;
    }

  
    public String getEtitulo() {
        return this.etitulo;
    }

    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", cpf='" + getCpf() +  "'" +
            ", etitulo='" + getEtitulo() +  "'" +

            "}";
    }
   
}
