package br.edu.ufam.icomp.lab_excecoes;
// CoordenadaNegativaException: "Coordenada com valor negativo"
// CoordenadaForaDosLimitesException: "Coordenada com valores fora dos limites"
// DigitoInvalidoException: "Digito da coordenada inválido"
// TamanhoMaximoExcedidoException: "Quantidade máxima de coordenadas excedida"
// DistanciaEntrePontosExcedidaException: "Distância máxima entre duas coordenadas vizinhas excedida"

public class CoordenadaNegativaException extends RoverCoordenadaException {
    public CoordenadaNegativaException(String message) {
        super(message);
    }

    public CoordenadaNegativaException() {
        super("Coordenada com valor negativo");
    }

}