// RoverException: "Exceção geral do rover"
// RoverCoordenadaException: "Exceção geral de coordenada do rover"
// RoverCaminhoException: "Exceção geral de caminho do rover"
// CoordenadaNegativaException: "Coordenada com valor negativo"
// CoordenadaForaDosLimitesException: "Coordenada com valores fora dos limites"
// DigitoInvalidoException: "Digito da coordenada inválido"
// TamanhoMaximoExcedidoException: "Quantidade máxima de coordenadas excedida"
// DistanciaEntrePontosExcedidaException: "Distância máxima entre duas coordenadas vizinhas excedida"
package br.edu.ufam.icomp.lab_excecoes;

public class RoverException extends Exception {

    public RoverException(String message) {
        super(message);
    }

    public RoverException() {
        super("Exceção geral do rover");
    }
}