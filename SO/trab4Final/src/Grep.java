import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Grep {
     public static void execute(String[] tokens) {
        if (tokens.length == 3) {
            String pattern = tokens[1];
            String filename = tokens[2];
            grepFile(pattern, filename);
        } else {
            System.err.println("Usage: grep <pattern> <filename>");
        }
    }

    private static void grepFile(String pattern, String filename) {
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            
            while ((line = reader.readLine()) != null) {
                if (line.contains(pattern)) {
                    System.out.println(line);
                }
            }
            reader.close(); 
        } catch (IOException e) {
            System.err.println("Failed to grep the file: " + e.getMessage());
        }
    }
}
