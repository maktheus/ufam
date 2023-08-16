import java.util.Scanner;

class ContaEnergia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int consumo = scanner.nextInt();
        char tipo = scanner.next().charAt(0);
        scanner.close();

        double preco = calcularPrecoEnergia(consumo, tipo);
        if (preco >= 0) {
            System.out.printf("%.2f", preco);
        } else {
            System.out.println("-1.00");
        }
    }

    public static double calcularPrecoEnergia(int consumo, char tipo) {
        if (tipo == 'R' || tipo == 'r') {
            if (consumo <= 500) {
                return consumo * 0.40;
            } else {
                return consumo * 0.65;
            }
        } else if (tipo == 'C' || tipo == 'c') {
            if (consumo <= 1000) {
                return consumo * 0.55;
            } else {
                return consumo * 0.60;
            }
        } else if (tipo == 'I' || tipo == 'i') {
            if (consumo <= 5000) {
                return consumo * 0.55;
            } else {
                return consumo * 0.60;
            }
        } else {
            return -1.00; // Valor inválido inserido
        }
    }
}
