import java.util.Scanner;

public class AsciiArt {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Lê o número de asteriscos na base
        int n = scanner.nextInt();

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        // Imprime a figura usando asteriscos
        for (int i = 0; i < n; i++) {
            // Print left half of the asterisks
            for (int j = 0; j < n - i; j++) {
                System.out.print("*");
            }

            // Print spaces in the middle
            for (int j = 0; j < 2 * i; j++) {
                System.out.print(" ");
            }

            // Print right half of the asterisks
            for (int j = 0; j < n - i; j++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }
}
