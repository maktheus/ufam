import java.util.Scanner;

public class TipoTriangulo {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Lê as medidas dos três lados do triângulo
        //System.out.print("Digite a medida do primeiro lado: ");
        double lado1 = scanner.nextDouble();

        //System.out.print("Digite a medida do segundo lado: ");
        double lado2 = scanner.nextDouble();

        //System.out.print("Digite a medida do terceiro lado: ");
        double lado3 = scanner.nextDouble();

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        // Verifica se pelo menos um dos lados é negativo ou se não é possível formar um triângulo
        if (lado1 <= 0 || lado2 <= 0 || lado3 <= 0 || !ehTriangulo(lado1, lado2, lado3)) {
            System.out.println("invalido");
        } else if (lado1 == lado2 && lado2 == lado3) { // Triângulo equilátero
            System.out.println("equilatero");
        } else if (lado1 == lado2 || lado1 == lado3 || lado2 == lado3) { // Triângulo isósceles
            System.out.println("isosceles");
        } else { // Triângulo escaleno
            System.out.println("escaleno");
        }
    }

    // Verifica se é possível formar um triângulo com as medidas dos lados fornecidas
    private static boolean ehTriangulo(double lado1, double lado2, double lado3) {
        return (lado1 + lado2 > lado3) && (lado1 + lado3 > lado2) && (lado2 + lado3 > lado1);
    }
}
