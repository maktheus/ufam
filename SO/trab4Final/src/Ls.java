import java.io.File;

public class Ls {
        public static void execute(String currentDirectory) {
        File currentDir = new File(currentDirectory);
        String[] files = currentDir.list();

        if (files != null) {
            for (String file : files) {
                System.out.println(file);
            }
        } else {
            System.err.println("Failed to list files in the current directory.");
        }
    }
}
