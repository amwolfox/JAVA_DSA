package oops;

import java.util.Arrays;

public class compare {
    public static void main(String[] args) {
        // [ID, Score]
        int[][] students = {
                {3, 95}, // index 0
                {1, 88}, // index 1
                {2, 99}  // index 2
        };

        // Sort based on the SCORE (index 1)
        // 'a' and 'b' represent two rows (e.g., {3, 95} and {1, 88})

        Arrays.sort(students, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted by ID (Ascending):");
        for (int[] s : students) {
            System.out.println("ID: " + s[0] + " | Score: " + s[1]);
        }

        Arrays.sort(students, (a, b) -> Integer.compare(a[1], b[1]));
        System.out.println("Sorted by Score (Ascending):");
        for (int[] s : students) {
            System.out.println("ID: " + s[0] + " | Score: " + s[1]);
        }

        System.out.println("Sorted by ID (Descending):");
        Arrays.sort(students, (a, b) -> Integer.compare(b[0], a[0]));
        for (int[] s : students) {
            System.out.println("ID: " + s[0] + " | Score: " + s[1]);
        }

        System.out.println("Sorted by Score (Descending):");
        Arrays.sort(students, (a, b) -> Integer.compare(b[1], a[1]));
        for (int[] s : students) {
            System.out.println("ID: " + s[0] + " | Score: " + s[1]);
        }
    }
}
