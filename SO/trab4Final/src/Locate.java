import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Locate {
    public static void execute(String[] tokens, String currentDirectory) {
        if (tokens.length >= 2) {
            boolean caseInsensitive = false;
            String searchTerm = tokens[1];

            if (tokens.length == 3 && tokens[1].equals("-i")) {
                caseInsensitive = true;
                searchTerm = tokens[2];
            }

            locateFiles(searchTerm, caseInsensitive, currentDirectory);
        } else {
            System.err.println("Usage: locate [-i] <searchTerm>");
        }
    }

    private static void locateFiles(String searchTerm, boolean caseInsensitive, String currentDirectory) {
        try {
            File currentDir = new File(currentDirectory);
            List<File> foundFiles = new ArrayList<>();
            searchFiles(currentDir, searchTerm, caseInsensitive, foundFiles);

            for (File file : foundFiles) {
                System.out.println(file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to locate files: " + e.getMessage());
        }
    }

    private static void searchFiles(File directory, String searchTerm, boolean caseInsensitive, List<File> foundFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (caseInsensitive) {
                        fileName = fileName.toLowerCase();
                        searchTerm = searchTerm.toLowerCase();
                    }
                    if (fileName.contains(searchTerm)) {
                        foundFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    
                    searchFiles(file, searchTerm, caseInsensitive, foundFiles);
                }
            }
        }
    }
}
