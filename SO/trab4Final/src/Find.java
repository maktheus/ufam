import java.io.File;

public class Find {
        public static void execute(String[] tokens, String currentDirectory) {
        if (tokens.length == 2 || tokens.length == 3) {
            String directory = (tokens.length == 3) ? tokens[1] : currentDirectory;
            String fileName = (tokens.length == 3) ? tokens[2] : tokens[1];
            
            findFiles(directory, fileName);
        } else {
            System.err.println("Usage: find [<directory>] <fileName>");
        }
    }

    private static void findFiles(String directory, String fileName) {
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            System.err.println("Invalid directory: " + directory);
            return;
        }

        searchFiles(dir, fileName);
    }

    private static void searchFiles(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    
                    searchFiles(file, fileName);
                } else if (file.getName().equals(fileName)) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }
}
