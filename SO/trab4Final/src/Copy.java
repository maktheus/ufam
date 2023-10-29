import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Copy {
        public static void execute(String[] tokens, String currentDirectory) {
        if (tokens.length == 3) {
            String sourceFile = tokens[1];
            String destinationFile = tokens[2];
            
            Path sourcePath = Path.of(currentDirectory, sourceFile);
            Path destinationPath = Path.of(currentDirectory, destinationFile);
            
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied successfully.");
            } catch (IOException e) {
                System.err.println("Failed to copy the file: " + e.getMessage());
            }
        } else {
            System.err.println("Usage: copy <sourceFile> <destinationFile>");
        }
    }
}
