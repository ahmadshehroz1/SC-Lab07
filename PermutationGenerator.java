import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PermutationGenerator {

    // Recursive function to generate permutations
    public static List<String> generatePermutations(String str, boolean allowDuplicates) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) {
            return result; // Return an empty list if the input is null or empty
        }
        if (allowDuplicates) {
            generatePermutationsWithDuplicates(str, "", result);
        } else {
            generatePermutationsWithoutDuplicates(str, "", new HashSet<>(), result);
        }
        return result;
    }

    // Helper function for generating permutations without removing duplicates
    private static void generatePermutationsWithDuplicates(String str, String prefix, List<String> result) {
        if (str.isEmpty()) {
            result.add(prefix);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            generatePermutationsWithDuplicates(
                    str.substring(0, i) + str.substring(i + 1),
                    prefix + str.charAt(i),
                    result);
        }
    }

    // Helper function for generating permutations and removing duplicates
    private static void generatePermutationsWithoutDuplicates(String str, String prefix, Set<String> seen, List<String> result) {
        if (str.isEmpty()) {
            if (!seen.contains(prefix)) {
                result.add(prefix);
                seen.add(prefix);
            }
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            generatePermutationsWithoutDuplicates(
                    str.substring(0, i) + str.substring(i + 1),
                    prefix + str.charAt(i),
                    seen,
                    result);
        }
    }

    // Iterative method to generate permutations (non-recursive)
    public static List<String> generatePermutationsIterative(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) {
            return result;
        }
        result.add(String.valueOf(str.charAt(0))); // Start with the first character

        for (int i = 1; i < str.length(); i++) {
            char current = str.charAt(i);
            List<String> newResult = new ArrayList<>();

            for (String s : result) {
                for (int j = 0; j <= s.length(); j++) {
                    newResult.add(s.substring(0, j) + current + s.substring(j));
                }
            }
            result = newResult;
        }
        return new ArrayList<>(new HashSet<>(result)); // Remove duplicates if present
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take input string from the user
        System.out.print("Enter a string to generate its permutations: ");
        String input = scanner.nextLine();

        // Error handling for empty string input
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Error: Input string cannot be empty. Please enter a valid string.");
            return;
        }

        System.out.print("Do you want to allow duplicate permutations (yes/no)? ");
        boolean allowDuplicates = scanner.nextLine().equalsIgnoreCase("yes");

        // Recursive solution
        System.out.println("\nGenerating permutations recursively...");
        long startRecursive = System.currentTimeMillis();
        List<String> recursivePermutations = generatePermutations(input, allowDuplicates);
        long endRecursive = System.currentTimeMillis();
        System.out.println("Permutations (Recursive): " + recursivePermutations);
        System.out.println("Recursive approach took " + (endRecursive - startRecursive) + " ms\n");

        // Iterative solution
        System.out.println("Generating permutations iteratively...");
        long startIterative = System.currentTimeMillis();
        List<String> iterativePermutations = generatePermutationsIterative(input);
        long endIterative = System.currentTimeMillis();
        System.out.println("Permutations (Iterative): " + iterativePermutations);
        System.out.println("Iterative approach took " + (endIterative - startIterative) + " ms\n");

        // Comparison
        System.out.println("Comparison:");
        System.out.println("Recursive permutations count: " + recursivePermutations.size());
        System.out.println("Iterative permutations count: " + iterativePermutations.size());

        scanner.close();
    }
}
