import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Remove {
        public static void execute(String[] tokens, String currentDirectory) {
        if (tokens.length == 2) {
            String fileToRemove = tokens[1];

            
            Path filePath = Path.of(currentDirectory, fileToRemove);

            try {
                
                Files.delete(filePath);
                System.out.println("File removed successfully.");
            } catch (IOException e) {
                System.err.println("Failed to remove the file: " + e.getMessage());
            }
        } else {
            System.err.println("Usage: remove <file>");
        }
    }
}
