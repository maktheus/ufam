package br.edu.ufam.icomp.lab_excecoes;

public class RoverMain {
	public static void main(String[] args) {
		var caminho = new Caminho(3);
		
		try {
			caminho.addCoordenada(new Coordenada(2, 15, 7));
			caminho.addCoordenada(new Coordenada(3, 14, 7));
			caminho.addCoordenada(new Coordenada(4, 1, 7));
			caminho.addCoordenada(new Coordenada(2, 15, 7));
			caminho.addCoordenada(new Coordenada(2, 15, 7));
		} catch (TamanhoMaximoExcedidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DistanciaEntrePontosExcedidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoordenadaNegativaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoordenadaForaDosLimitesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DigitoInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}