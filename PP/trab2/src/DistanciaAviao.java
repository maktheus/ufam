import java.util.Scanner;

public class DistanciaAviao {

    public static void main(String[] args) {
        new DistanciaAviao().operation();
    }

    public void operation() {

        Scanner input = new Scanner(System.in);
        int index = 0, flightTime = 0;
        int[][] flightTimes = {{0, 2, 11, 6, 15, 11, 1}, {2, 0, 7, 12, 4, 2, 15}, {11, 7, 0, 11, 8, 3, 13}, {6, 12, 11, 0, 10, 2, 1}, {15, 4, 8, 10, 0, 5, 13}, {11, 2, 3, 2, 5, 0, 14}, {1, 15, 13, 1, 13, 14, 0}};
        int[] flights = new int[100];
        String flight;

        while (true) {
            flight = input.next();
            if (flight.equals("-1")) {
                break;
            }
            flights[index] = Integer.parseInt(String.valueOf(flight.charAt(0)));
            index++;
        }

        // 2 4 3 5

        for (int i = 0; i < index - 1; i++) {
            flightTime += flightTimes[flights[i] - 1][flights[i + 1] - 1];
        }
        System.out.println(flightTime);
    }
}