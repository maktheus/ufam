package br.edu.ufam.icomp.lab_encapsulamento;

import java.util.Random;

import br.edu.ufam.icomp.lab_encapsulamento.Posicao;

public class CarroLuxuoso extends Carro implements Localizavel{
    private static final int MIN_COD_PAIS = 1;
    private static final int MAX_COD_PAIS = 1999;
    private static final int MIN_COD_AREA = 10;
    private static final int MAX_COD_AREA = 99;
    private static final int MIN_NUMERO = 10000000;
    private static final int MAX_NUMERO = 999999999;

    public CarroLuxuoso(String placa){
        super(placa);
    }

    public Posicao getPosicao() {
        double[] coordenadas = createCoordinates();
        return new Posicao(coordenadas[0], coordenadas[1], coordenadas[2]);
    }

        private double[] createCoordinates() {
        Random r = new Random();
        double latitude = generateRandomInRange(-3.16, -2.96);
        double longitude = generateRandomInRange(-60.12, -59.82);
        double altitude = generateRandomInRange(15.0, 100.0);
        return new double[]{latitude, longitude, altitude};
    }


    private double generateRandomInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public double getErroLocalizacao() {
        return 15.0;
    }
}
