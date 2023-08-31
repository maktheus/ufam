package br.edu.ufam.icomp.lab_excecoes;
import br.edu.ufam.icomp.lab_excecoes.RoverCoordenadaException;


public class Coordenada {
    private int posX;
    private int posY;
    private int digito;

    public Coordenada(int posX, int posY, int digito) throws CoordenadaNegativaException, CoordenadaForaDosLimitesException, DigitoInvalidoException {
        // DigitoInvalidoException: disparada quando o resto da divisão da soma das coordenadas (posX + posY) por 10 for diferente do dígito (que deverá estar entre 0 e 9).
   
       
        if (posX < 0 || posY < 0) {
            throw new CoordenadaNegativaException();
        }
        if (posX > 30000 || posY > 30000) {
            throw new CoordenadaForaDosLimitesException();
        }

         if (digito != (posX + posY) % 10) {
            throw new DigitoInvalidoException();
        }
        this.posX = posX;
        this.posY = posY;
        this.digito = digito;
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getDigito() {
        return digito;
    }

    // distancia(Coordenada coordenada): double

    public double distancia(Coordenada coordenada) {
        return Math.sqrt(Math.pow(coordenada.getPosX() - this.posX, 2) + Math.pow(coordenada.getPosY() - this.posY, 2));
    }

    // Já o método toString deverá imprimir as coordenadas de acordo com o exemplo abaixo:
// 32, 67
    @Override
    public String toString() {
        return posX + ", " + posY;
    }
    
}
