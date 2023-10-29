import java.io.File;

public class Mkdir {
        public static void execute(String[] tokens, String currentDirectory) {
        if (tokens.length == 2) {
            String directoryName = tokens[1];
            createDirectory(directoryName, currentDirectory);
        } else {
            System.err.println("Usage: mkdir <directoryName>");
        }
    }

    private static void createDirectory(String directoryName, String currentDirectory) {
        try {
            File newDirectory = new File(currentDirectory, directoryName);

            if (!newDirectory.exists()) {
                if (newDirectory.mkdir()) {
                    System.out.println("Directory created successfully.");
                } else {
                    System.err.println("Failed to create the directory.");
                }
            } else {
                System.err.println("Directory already exists: " + directoryName);
            }
        } catch (Exception e) {
            System.err.println("Failed to create the directory: " + e.getMessage());
        }
    }
}
