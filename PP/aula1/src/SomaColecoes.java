import java.util.Scanner;

public class SomaColecoes {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        int somaColecao = 0; // Variável para armazenar a soma de cada coleção

        // Loop principal para ler as coleções e calcular a soma
        while (true) {
            int num;
            int somaAtual = 0; // Variável para armazenar a soma da coleção atual

            // Loop para ler os valores de cada coleção
            while (true) {
                //System.out.print("Digite um valor (ou -1 para encerrar a coleção): ");
                num = scanner.nextInt();

                if (num == -1) {
                    break; // Fim da coleção atual, sair do loop interno
                }

                somaAtual += num;
            }

            if (somaAtual == 0) {
                break; // Fim do programa, coleção vazia, sair do loop principal
            }

            somaColecao += somaAtual;
            System.out.println(somaAtual); // Imprime a soma da coleção atual
        }

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        //System.out.println(somaColecao); // Imprime a soma total de todas as coleções
    }
}
