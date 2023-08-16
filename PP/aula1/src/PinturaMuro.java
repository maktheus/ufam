import java.util.Scanner;

public class PinturaMuro {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Lê o valor cobrado por metro quadrado do pintor
        float valorPorMetroQuadrado = scanner.nextFloat();

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        // Dimensões do muro
        float comprimento = 12.0f;
        float altura = 3.0f;

        // Cálculo da área do muro
        float areaMuro = comprimento * altura;

        // Cálculo do custo total da pintura
        float custoTotal = valorPorMetroQuadrado * areaMuro + 100.0f;

        // Imprime o resultado formatado com uma casa decimal
        System.out.printf("%.1f\n", custoTotal);
    }
}
