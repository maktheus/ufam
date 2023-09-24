class Cliente {
    private int categoria;
    private int tempoServico;

    public Cliente(int categoria, int tempoServico) {
        this.categoria = categoria;
        this.tempoServico = tempoServico;
    }

    // ... getters, setters e toString() 

    public int getCategoria() {
        return categoria;
    }

    public int getTempoServico() {
        return tempoServico;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "categoria=" + categoria +
                ", tempoServico=" + tempoServico +
                '}';
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setTempoServico(int tempoServico) {
        this.tempoServico = tempoServico;
    }
}