package br.edu.icomp.ufam.lab_heranca;

import br.edu.icomp.ufam.lab_heranca.FormaGeometrica;
import br.edu.icomp.ufam.lab_heranca.Retangulo;

public class Quadrado extends Retangulo {

    public Quadrado(int posX, int posY, double lado){
        super(posX, posY, lado, lado);
    }

    public String toString(){
        //Quadrado na posição (45, 39) com lado de 6.0cm (área=36.0cm2, perímetro=24.0cm)
        return "Quadrado na posição ("+this.posX+", "+this.posY+") com lado de "+this.altura+"cm (área="+this.getArea()+"cm2, perímetro="+this.getPerimetro()+"cm)";
    }
}
