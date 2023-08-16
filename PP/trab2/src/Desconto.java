import java.util.Scanner;

public class Desconto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        double precoCompra = scanner.nextDouble();

        double valorFinal = calcularValorComDesconto(precoCompra);
        System.out.printf("%.2f%n", valorFinal);

        scanner.close();
    }

    private static double calcularValorComDesconto(double precoCompra) {
        double valorDesconto = 0.05 * precoCompra;

        if (precoCompra >= 200.0) {
            return precoCompra - valorDesconto;
        } else {
            return precoCompra;
        }
    }
}
