import java.util.*;

public class DiaSemana {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<List<Integer>> horasTrabalhadas = new ArrayList<>();
        
        while (true) {
            List<Integer> linha = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                int num = sc.nextInt();
                if (num == -1) {
                    break;
                }
                linha.add(num);
            }
            if (linha.size() < 7) {
                break;
            }
            horasTrabalhadas.add(linha);
        }
        
        int[] totalHoras = new int[7];
        for (List<Integer> linha : horasTrabalhadas) {
            for (int i = 0; i < 7; i++) {
                totalHoras[i] += linha.get(i);
            }
        }
        
        int maxHoras = Arrays.stream(totalHoras).max().getAsInt();
        for (int i = 0; i < 7; i++) {
            if (totalHoras[i] == maxHoras) {
                System.out.println(i + 1);
            }
        }
    }
}
