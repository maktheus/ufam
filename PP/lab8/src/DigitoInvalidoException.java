package br.edu.ufam.icomp.lab_excecoes;
// CoordenadaNegativaException: "Coordenada com valor negativo"
// CoordenadaForaDosLimitesException: "Coordenada com valores fora dos limites"
// DigitoInvalidoException: "Digito da coordenada inválido"
// TamanhoMaximoExcedidoException: "Quantidade máxima de coordenadas excedida"
// DistanciaEntrePontosExcedidaException: "Distância máxima entre duas coordenadas vizinhas excedida"

import br.edu.ufam.icomp.lab_excecoes.RoverCoordenadaException;

public class DigitoInvalidoException extends RoverCoordenadaException {
    public DigitoInvalidoException(String message) {
        super(message);
    }

    public DigitoInvalidoException() {
        super("Digito da coordenada inválido");
    }

}