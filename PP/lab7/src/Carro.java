package br.edu.ufam.icomp.lab_encapsulamento;

import java.util.Random;

public class Carro {
    protected String placa;

    public Carro(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        if (placa.length() == 7) {
            this.placa = placa;
        }
    }






}
