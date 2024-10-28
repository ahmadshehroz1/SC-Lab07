import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    private boolean caseSensitive;

    public FileSearcher(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public List<String> searchFiles(File directory, String fileName) {
        List<String> foundFiles = new ArrayList<>();
        searchRecursive(directory, fileName, foundFiles);
        return foundFiles;
    }

    private void searchRecursive(File directory, String fileName, List<String> foundFiles) {
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory: " + directory.getAbsolutePath());
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchRecursive(file, fileName, foundFiles);
            } else {
                System.out.println("Checking file: " + file.getName());
                if (matches(file.getName(), fileName)) {
                    System.out.println("Match found: " + file.getAbsolutePath());
                    foundFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    private boolean matches(String fileName1, String fileName2) {
        return caseSensitive ? fileName1.equals(fileName2) : fileName1.equalsIgnoreCase(fileName2);
    }

    public static void main(String[] args) {
        // Sample main method if you want to run the FileSearcher independently
        if (args.length < 2) {
            System.out.println("Usage: java FileSearcher <directory-path> <file-name> [-i]");
            System.out.println("Use -i for case-insensitive search.");
            return;
        }

        String directoryPath = args[0];
        String fileName = args[1];
        boolean caseSensitive = args.length < 3 || !args[2].equalsIgnoreCase("-i");

        FileSearcher fileSearcher = new FileSearcher(caseSensitive);
        List<String> results = fileSearcher.searchFiles(new File(directoryPath), fileName);

        if (results.isEmpty()) {
            System.out.println("File not found.");
        } else {
            System.out.println("File found at the following locations:");
            for (String path : results) {
                System.out.println(path);
            }
            System.out.println("Total occurrences: " + results.size());
        }
    }
}
