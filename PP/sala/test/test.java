package test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test {

    // Definir a matriz de relacionamento entre salas e turmas
    static int[][][] matrizRelacionamento = {
        {{1, 0, 1}, {1, 1, 1}, {0, 1, 0}},
        {{1, 0, 1}, {0, 0, 0}, {0, 1, 0}},
        {{1, 0, 1}, {1, 1, 1}, {0, 1, 0}}
    };

    // Função de avaliação - quantas aulas podem ser alocadas
    static int avaliar(int[] individuo) {
        int total = 0;
        boolean[] salasUtilizadas = new boolean[individuo.length];
        
        for (int t = 0; t < individuo.length; t++) {
            int s = individuo[t];
            if (matrizRelacionamento[t][s][0] == 1 && !salasUtilizadas[s]) {
                total++;
                salasUtilizadas[s] = true;
            }
        }
        
        return total;
    }

    public static void main(String[] args) {
        int numTurmas = matrizRelacionamento.length;
        int numSalas = matrizRelacionamento[0].length;

        // Parâmetros do algoritmo genético
        int tamanhoPopulacao = 100;
        int numGeracoes = 1000;
        double taxaMutacao = 0.1;

        Random random = new Random();

        // Inicialização da população
        List<int[]> populacao = new ArrayList<>();
        for (int i = 0; i < tamanhoPopulacao; i++) {
            int[] individuo = new int[numTurmas];
            for (int j = 0; j < numTurmas; j++) {
                individuo[j] = random.nextInt(numSalas);
            }
            populacao.add(individuo);
        }

        // Algoritmo genético
        for (int geracao = 0; geracao < numGeracoes; geracao++) {
            populacao.sort((indiv1, indiv2) -> Integer.compare(avaliar(indiv2), avaliar(indiv1)));

            // Seleção dos pais (elitismo)
            List<int[]> pais = new ArrayList<>(populacao.subList(0, tamanhoPopulacao / 2));

            // Cruzamento
            List<int[]> filhos = new ArrayList<>();
            for (int i = 0; i < tamanhoPopulacao / 2; i++) {
                int[] pai1 = pais.get(random.nextInt(pais.size()));
                int[] pai2 = pais.get(random.nextInt(pais.size()));
                int pontoCorte = random.nextInt(numTurmas - 1) + 1;
                int[] filho = new int[numTurmas];
                System.arraycopy(pai1, 0, filho, 0, pontoCorte);
                System.arraycopy(pai2, pontoCorte, filho, pontoCorte, numTurmas - pontoCorte);
                filhos.add(filho);
            }

            // Mutação
            for (int[] filho : filhos) {
                if (random.nextDouble() < taxaMutacao) {
                    int turma = random.nextInt(numTurmas);
                    int novaSala = random.nextInt(numSalas);
                    filho[turma] = novaSala;
                }
            }

            // Nova população
            populacao = new ArrayList<>(pais);
            populacao.addAll(filhos);
        }

        // Encontrar o melhor indivíduo
        int[] melhorIndividuo = populacao.get(0);

        // Imprimir a alocação
        for (int turma = 0; turma < melhorIndividuo.length; turma++) {
            System.out.println("Turma " + (turma + 1) + " -> Sala " + (char)('A' + melhorIndividuo[turma]));
        }
    }
}
