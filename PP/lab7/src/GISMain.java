package br.edu.ufam.icomp.lab_encapsulamento;


public class GISMain {

    public static void main(String[] args) {
        
        Celular celular = new Celular(55, 92, 999999999);
        System.out.println(celular.getPosicao());
        System.out.println(celular.getErroLocalizacao());
        
        Carro carro = new Carro("AAA-1234");
        System.out.println(carro.getPlaca());
        carro.setPlaca("AAA-12345");
        System.out.println(carro.getPlaca());

        //vetor de objetos localizaveis

        Localizavel[] localizaveis = new Localizavel[2];

        localizaveis[0] = celular;
        

        //carro luxuoso

        CarroLuxuoso carroLuxuoso = new CarroLuxuoso("AAA-1234");

        localizaveis[1] = carroLuxuoso;

        
    }
}
