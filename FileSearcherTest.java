import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSearcherTest {

    private File testDirectory;
    private FileSearcher caseSensitiveSearcher;
    private FileSearcher caseInsensitiveSearcher;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory structure for testing
        testDirectory = Files.createTempDirectory("testDir").toFile();

        // Create test files and directories
        File subDir1 = new File(testDirectory, "subDir1");
        File subDir2 = new File(testDirectory, "subDir2");
        subDir1.mkdir();
        subDir2.mkdir();

        new File(testDirectory, "testFile.txt").createNewFile();
        new File(subDir1, "testFile.txt").createNewFile();
        new File(subDir2, "anotherFile.txt").createNewFile();
        new File(subDir2, "TESTFILE.txt").createNewFile(); // For case-insensitive test

        // Initialize searchers
        caseSensitiveSearcher = new FileSearcher(true);
        caseInsensitiveSearcher = new FileSearcher(false);
    }

    @Test
    void testFileFoundInRootDirectory() {
        List<String> result = caseSensitiveSearcher.searchFiles(testDirectory, "testFile.txt");
        assertEquals(1, result.size());
        assertTrue(result.get(0).endsWith("testFile.txt"));
    }

    @Test
    void testFileFoundInSubdirectories() {
        List<String> result = caseSensitiveSearcher.searchFiles(testDirectory, "testFile.txt");
        assertEquals(2, result.size()); // Expecting two files with the same name in different directories
    }

    @Test
    void testFileNotFound() {
        List<String> result = caseSensitiveSearcher.searchFiles(testDirectory, "nonExistentFile.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    void testCaseInsensitiveSearch() {
        List<String> result = caseInsensitiveSearcher.searchFiles(testDirectory, "testfile.txt");
        assertEquals(3, result.size()); // Should find all variations of "testFile.txt" regardless of case
    }

    @Test
    void testCaseSensitiveSearch() {
        List<String> result = caseSensitiveSearcher.searchFiles(testDirectory, "TESTFILE.txt");
        assertEquals(1, result.size()); // Should only find the exact case match
    }

    @Test
    void testInvalidDirectory() {
        File invalidDirectory = new File("nonexistentDirectory");
        List<String> result = caseSensitiveSearcher.searchFiles(invalidDirectory, "testFile.txt");
        assertTrue(result.isEmpty());
    }
}
