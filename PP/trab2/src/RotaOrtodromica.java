import java.util.Scanner;

public class RotaOrtodromica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        double lat1 = Math.toRadians(scanner.nextDouble());

        
        double lon1 = Math.toRadians(scanner.nextDouble());

                double lat2 = Math.toRadians(scanner.nextDouble());

        
        double lon2 = Math.toRadians(scanner.nextDouble());

        double distancia = calcularDistancia(lat1, lon1, lat2, lon2);
        System.out.printf("A distancia entre os pontos (%.6f, %.6f) e (%.6f, %.6f) e de %.2f km%n",
                Math.toDegrees(lat1), Math.toDegrees(lon1), Math.toDegrees(lat2), Math.toDegrees(lon2), distancia);

        scanner.close();
    }

    private static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double raioTerra = 6371.0; // raio medio da Terra em quilômetros

        // Fórmula de Haversine para calcular a distancia entre dois pontos na superfície da Terra
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return raioTerra * c;
    }
}
