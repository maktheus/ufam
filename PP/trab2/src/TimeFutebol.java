import java.util.*;

public class TimeFutebol {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Integer> golsTime = new ArrayList<>();
        while (true) {
            int gol = sc.nextInt();
            if (gol == -1) {
                break;
            }
            golsTime.add(gol);
        }

        List<Integer> golsAdversarios = new ArrayList<>();
        while (true) {
            int gol = sc.nextInt();
            if (gol == -1) {
                break;
            }
            golsAdversarios.add(gol);
        }

        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;

        for (int i = 0; i < golsTime.size(); i++) {
            int golsTimeAtual = golsTime.get(i);
            int golsAdversariosAtual = golsAdversarios.get(i);

            if (golsTimeAtual > golsAdversariosAtual) {
                vitorias++;
            } else if (golsTimeAtual == golsAdversariosAtual) {
                empates++;
            } else {
                derrotas++;
            }
        }

        System.out.println(vitorias + " " + empates + " " + derrotas);
    }
}
