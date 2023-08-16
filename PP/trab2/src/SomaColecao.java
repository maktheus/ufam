import java.util.Scanner;

public class SomaColecao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int valor;
        int soma = 0;

        do {
            valor = scanner.nextInt();
            if (valor != -1) {
                soma += valor;
            }
        } while (valor != -1);

        scanner.close();
        System.out.println( soma);
    }
}
