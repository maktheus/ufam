package Scripts;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class GenerateEntrada {

    public static void main(String[] args) {
        Random random = new Random();

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter("entrada1.txt"))) {
            for (int i = 0; i < 1000; i++) {
                int categoria = random.nextInt(3)+1;
                switch (categoria) {
                    // case 0:
                    //     arquivo.write(String.format("%d %d%n", categoria, random.nextInt(3) + 1));
                    //     break;
                    case 1:
                        arquivo.write(String.format("%d %d%n", categoria, random.nextInt(3) + 4));
                        break;
                    case 2:
                        arquivo.write(String.format("%d %d%n", categoria, random.nextInt(3) + 2));
                        break;
                    case 3:
                        arquivo.write(String.format("%d %d%n", categoria, random.nextInt(3) + 1));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
