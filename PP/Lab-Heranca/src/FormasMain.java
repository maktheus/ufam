package br.edu.icomp.ufam.lab_heranca;

public class FormasMain {
    public static void main(String[] args) {
        FormaGeometrica[] formas = new FormaGeometrica[3];
        formas[0] = new Circulo(32, 87, 6.0);
        formas[1] = new Retangulo(12, 65, 2.0, 7.0);
        formas[2] = new Quadrado(45, 39, 6.0);

        for (FormaGeometrica forma : formas) {
            System.out.println(forma);
        }
    }
}
