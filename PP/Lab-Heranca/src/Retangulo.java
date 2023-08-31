package br.edu.icomp.ufam.lab_heranca;

import br.edu.icomp.ufam.lab_heranca.FormaGeometrica;

public class Retangulo extends FormaGeometrica{
    public double largura;
    public double altura;

    public Retangulo(int PosX, int PosY, double largura, double altura){
        super(PosX, PosY);
        this.largura = largura;
        this.altura = altura;
    }

    public double getArea(){
        return this.altura * this.largura;
    }

    public double getPerimetro(){
        return 2*( this.altura+this.largura);
    }

    public String toString(){
        //Retângulo na posição (12, 65) com largura de 2.0cm e altura de 7.0cm (área=14.0cm2, perímetro=18.0cm)

        return  "Retângulo na posição ("+this.posX+", "+this.posY+") com largura de "+this.largura+"cm e altura de "+this.altura+"cm (área="+this.getArea()+"cm2, perímetro="+this.getPerimetro()+"cm)";
    }
}
