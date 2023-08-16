import java.util.Scanner;
import java.lang.Math;

public class RaizQuadrada {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double numero = scanner.nextDouble();
        double raiz = Math.sqrt(numero);
        System.out.printf("%.3f\n", raiz);
        //close the scanner
        scanner.close();
        
    }
}
