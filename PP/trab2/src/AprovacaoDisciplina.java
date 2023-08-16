import java.util.*;

public class AprovacaoDisciplina {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Double> notas = new ArrayList<>();
        while (true) {
            double nota = sc.nextDouble();
            if (nota == -1) {
                break;
            }
            notas.add(nota);
        }

        List<Integer> frequencias = new ArrayList<>();
        while (true) {
            int frequencia = sc.nextInt();
            if (frequencia == -1) {
                break;
            }
            frequencias.add(frequencia);
        }

        int cargaHoraria = sc.nextInt();

        int aprovados = 0;
        int reprovadosNota = 0;
        int reprovadosFrequencia = 0;

        for (int i = 0; i < notas.size(); i++) {
            double nota = notas.get(i);
            int frequencia = frequencias.get(i);

            if (frequencia < cargaHoraria * 0.75) {
                reprovadosFrequencia++;
            } else if (nota < 5.0) {
                reprovadosNota++;
            } else {
                aprovados++;
            }
        }

        System.out.println(aprovados + " " + reprovadosNota + " " + reprovadosFrequencia);
    }
}
