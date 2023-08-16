import java.util.*;
import java.text.DecimalFormat;

public class MediaColecao {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double sum = 0.0;
        int count = 0;

        while (true) {
            double num = sc.nextDouble();
            if (num == -1) {
                break;
            }
            sum += num;
            count++;
        }

        if (count > 0) {
            double average = sum / count;
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println(df.format(average));
        }
        sc.close();
    }
}
