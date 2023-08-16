import java.util.Scanner;

public class VolumeCombustivel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double alturaTotal = scanner.nextDouble();

        double nivelCombustivel = scanner.nextDouble();

        double raioBojos = scanner.nextDouble();
        scanner.close();

        double volume = calcularVolumeCombustivel(alturaTotal, nivelCombustivel, raioBojos);
        if (volume >= 0) {
            System.out.printf("%.3f", volume);
        } else {
            System.out.println("-1.000");
        }
    }

    public static double calcularVolumeCombustivel(double alturaTotal, double nivelCombustivel, double raioBojos) {
        if (alturaTotal >= 0 && nivelCombustivel >= 0 && raioBojos >= 0) {
            double alturaCilindro = alturaTotal - (2 * raioBojos);
            if (nivelCombustivel >= 0 && nivelCombustivel <= alturaCilindro) {
                double volumeCilindro = calcularVolumeCilindro(raioBojos, alturaCilindro);
                double volumeEsfera = calcularVolumeEsfera(raioBojos);
                double volumeCombustivel = volumeCilindro + volumeEsfera;
                double alturaNivel = alturaCilindro - nivelCombustivel;

                if (alturaNivel >= 0 && alturaNivel <= raioBojos) {
                    double volumeCalota = calcularVolumeCalotaEsferica(raioBojos, alturaNivel);
                    return volumeCombustivel - volumeCalota;
                } else if (alturaNivel > raioBojos && alturaNivel <= alturaCilindro) {
                    double volumeParcialCalota = calcularVolumeCalotaEsferica(raioBojos, raioBojos);
                    double volumeCilindroCalota = calcularVolumeCilindro(raioBojos, alturaNivel - raioBojos);
                    return volumeCombustivel - volumeParcialCalota - volumeCilindroCalota;
                } else {
                    return -1.000; // Nível de combustível maior que a altura total do tanque
                }
            } else {
                return -1.000; // Nível de combustível inválido
            }
        } else {
            return -1.000; // Valores inválidos inseridos
        }
    }

    public static double calcularVolumeEsfera(double raio) {
        return (4.0 / 3.0) * Math.PI * Math.pow(raio, 3);
    }

    public static double calcularVolumeCalotaEsferica(double raio, double altura) {
        return (1.0 / 3.0) * Math.PI * Math.pow(altura, 2) * (3 * raio - altura);
    }

    public static double calcularVolumeCilindro(double raio, double altura) {
        return Math.PI * Math.pow(raio, 2) * altura;
    }
}
