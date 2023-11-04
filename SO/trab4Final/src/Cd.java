import java.io.File;

public class Cd {
        public static String  execute(String[] tokens, String currentDirectory) {
        if (tokens.length > 1) {
            String newDir = tokens[1];
            if (newDir.equals("..")) {
                
                currentDirectory = getParentDirectory(currentDirectory);
            } else {
            
                File newDirFile = new File(currentDirectory, newDir);
                if (newDirFile.isDirectory()) {
                    currentDirectory = newDirFile.getAbsolutePath();
                } else {
                    System.err.println("Directory does not exist: " + newDir);
                }
            }
        } else {
            System.err.println("Usage: cd <directory>");
        }
        return currentDirectory;
    }

    private static String getParentDirectory(String currentDirectory) {
        File currentDirFile = new File(currentDirectory);
        String parent = currentDirFile.getParent();
        return (parent != null) ? parent : currentDirectory;
    }
}
