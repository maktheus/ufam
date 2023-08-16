import java.util.*;

public class PontoReta {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double x = sc.nextDouble();
        double y = sc.nextDouble();

        if (Math.abs(2 * x + y - 3) < 1e-6) {
            System.out.println("Ponto (" + x + ", " + y + ") pertence a reta 2x + y = 3.");
        } else {
            System.out.println("Ponto (" + x + ", " + y + ") nao pertence a reta 2x + y = 3.");
        }

        sc.close();
    }
}
