import java.util.Scanner;

public class AreaTriangulo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Lê os lados do triângulo
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();
        
        // Verifica se as entradas correspondem a um triângulo válido
        if (a + b > c && a + c > b && b + c > a) {
            // Calcula o semi-perímetro
            double s = (a + b + c) / 2.0;
            
            // Calcula a área usando a fórmula de Heron
            double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
            
            // Imprime a área com duas casas decimais
            System.out.printf("%.2f\n", area);
        } else {
            System.out.println("Triangulo invalido");
        }
        
        sc.close();
    }
}
