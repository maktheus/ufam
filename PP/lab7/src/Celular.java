package br.edu.ufam.icomp.lab_encapsulamento;

import br.edu.ufam.icomp.lab_encapsulamento.Posicao;
import br.edu.ufam.icomp.lab_encapsulamento.Localizavel;
import java.util.Random;

public class Celular implements Localizavel {
    private static final int MIN_COD_PAIS = 1;
    private static final int MAX_COD_PAIS = 1999;
    private static final int MIN_COD_AREA = 10;
    private static final int MAX_COD_AREA = 99;
    private static final int MIN_NUMERO = 10000000;
    private static final int MAX_NUMERO = 999999999;

    private int codPais;
    private int codArea;
    private int numero;

    public Celular(int codPais, int codArea, int numero) {
        this.codPais = validateRange(codPais, MIN_COD_PAIS, MAX_COD_PAIS);
        this.codArea = validateRange(codArea, MIN_COD_AREA, MAX_COD_AREA);
        this.numero = validateRange(numero, MIN_NUMERO, MAX_NUMERO);
    }

    public int getCodPais() {
        return codPais;
    }

    public int getCodArea() {
        return codArea;
    }

    public int getNumero() {
        return numero;
    }

    public final void setCodPais(int codPais) {
        this.codPais = validateRange(codPais, MIN_COD_PAIS, MAX_COD_PAIS);
    }

    public final void setCodArea(int codArea) {
        this.codArea = validateRange(codArea, MIN_COD_AREA, MAX_COD_AREA);
    }

    public final void setNumero(int numero) {
        this.numero = validateRange(numero, MIN_NUMERO, MAX_NUMERO);
    }

    public Posicao getPosicao() {
        double[] coordenadas = createCoordinates();
        return new Posicao(coordenadas[0], coordenadas[1], coordenadas[2]);
    }

    public double getErroLocalizacao() {
        return 50.0;
    }

    private double[] createCoordinates() {
        Random r = new Random();
        double latitude = generateRandomInRange(-3.16, -2.96);
        double longitude = generateRandomInRange(-60.12, -59.82);
        double altitude = generateRandomInRange(15.0, 100.0);
        return new double[]{latitude, longitude, altitude};
    }

    private int validateRange(int value, int min, int max) {
        return (value >= min && value <= max) ? value : -1;
    }

    private double generateRandomInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
