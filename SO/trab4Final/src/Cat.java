import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cat {
        public static void execute(String[] tokens) {
        if (tokens.length == 2) {
            String filename = tokens[1];
            catFile(filename);
        } else {
            System.err.println("Usage: cat <filename>");
        }
    }

    private static void catFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed to read the file: " + e.getMessage());
        }
    }
}
