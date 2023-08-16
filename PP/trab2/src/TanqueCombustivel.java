import java.util.Scanner;

public class TanqueCombustivel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double raio = scanner.nextDouble();

        double alturaAr = scanner.nextDouble();

        int opcao = scanner.nextInt();

        double volume = calcularVolumeTanque(raio, alturaAr, opcao);
        System.out.printf("%.4f%n", volume);

        scanner.close();
    }

    private static double calcularVolumeTanque(double raio, double alturaAr, int opcao) {
        double volumeTotal = (4.0 / 3.0) * Math.PI * Math.pow(raio, 3);
        double volumeCalota = calcularVolumeCalota(raio, alturaAr);

        if (opcao == 1) {
            return volumeCalota;
        } else if (opcao == 2) {
            return volumeTotal - volumeCalota;
        } else {
            System.out.println("Opção inválida.");
            return 0.0;
        }
    }

    private static double calcularVolumeCalota(double raio, double alturaAr) {
        return (1.0 / 3.0) * Math.PI * Math.pow(alturaAr, 2) * (3 * raio - alturaAr);
    }
}
