package br.edu.ufam.icomp.lab_excecoes;
// CoordenadaNegativaException: "Coordenada com valor negativo"
// CoordenadaForaDosLimitesException: "Coordenada com valores fora dos limites"
// DigitoInvalidoException: "Digito da coordenada inválido"
// TamanhoMaximoExcedidoException: "Quantidade máxima de coordenadas excedida"
// DistanciaEntrePontosExcedidaException: "Distância máxima entre duas coordenadas vizinhas excedida"

public class TamanhoMaximoExcedidoException extends RoverCaminhoException {
    public TamanhoMaximoExcedidoException(String message) {
        super(message);
    }

    public TamanhoMaximoExcedidoException() {
        super("Quantidade máxima de coordenadas excedida");
    }

}