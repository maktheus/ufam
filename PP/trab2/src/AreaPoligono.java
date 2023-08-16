import java.util.*;
import java.text.DecimalFormat;

public class AreaPoligono {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Double> xCoords = new ArrayList<>();
        while (true) {
            double x = sc.nextDouble();
            if (x == -1) {
                break;
            }
            xCoords.add(x);
        }

        List<Double> yCoords = new ArrayList<>();
        while (true) {
            double y = sc.nextDouble();
            if (y == -1) {
                break;
            }
            yCoords.add(y);
        }

        double area = 0.0;

        for (int i = 0; i < xCoords.size() - 1; i++) {
            area += (xCoords.get(i) * yCoords.get(i + 1)) - (xCoords.get(i + 1) * yCoords.get(i));
        }

        area = Math.abs(area) / 2.0;

        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println(df.format(area));
    }
}
