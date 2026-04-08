package dsa.Phase1.Searching;

public class L1_01_Binary_Search_Implementation {
    public static void main(String[] args) {
        L1_01_Binary_Search_Implementation bs = new L1_01_Binary_Search_Implementation();
        int[] data = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("Index of 23: " + bs.search(data, 23)); // 5
    }

    /**
     * Finds the index of target in a sorted array.
     * Complexity: O(log N) Time | O(1) Space
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            // 1. THE OVERFLOW TRAP
            // Don't use (low + high) / 2. If low and high are large,
            // their sum can exceed the 'int' limit.
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid; // Found it!
            } else if (nums[mid] < target) {
                // 2. DISCARD LEFT HALF
                low = mid + 1;
            } else {
                // 3. DISCARD RIGHT HALF
                high = mid - 1;
            }
        }

        // 4. NOT FOUND
        return -1;
    }
}
/*
In the world of Deep Stress interviews, Binary Search is the bread and butter of optimization. If you are given a sorted range, and you aren't using Binary Search, you're leaving performance on the table.
The core idea is simple: Check the middle. If the middle isn't what you want, you can instantly discard half the search space.

1. The "Heat Stress" Logic: Iterative vs. Recursive
While both approaches have the same Time Complexity ($O(\log N)$), they differ significantly in how they handle memory.
Feature	Iterative	Recursive
Space Complexity	$O(1)$ (Constant)	$O(\log N)$ (Stack Space)
Memory Risk	None. It uses the same variables.	StackOverflowError if the range is massive.
Performance	Generally faster (no function call overhead).	Slightly slower due to function call overhead.
Deep Stress Insight: In a production environment (like a Node.js backend processing millions of requests), you always prefer Iterative. Every recursive call adds a "frame" to the call stack. For a very large $N$, the iterative version uses practically zero extra memory, while the recursive one grows with the depth of the search.

3. Deep Stress Breakdown
A. The low <= high Condition
If you use low < high, you might miss the element if it's the very last one remaining in the search. Always use <= when you want to check every single possibility.
B. The "Mid" Calculation
This is a classic "Senior Developer" check.
* Junior: mid = (low + high) / 2;
* Senior: mid = low + (high - low) / 2; In Java, int maxes out at about 2.1 billion. If low and high are both 1.5 billion, the "Junior" version results in a negative number because of integer overflow. The "Senior" version is safe.
C. Discarding the Middle
Since we already checked nums[mid] and it wasn't the target, we move to mid + 1 or mid - 1. Never include mid in the next range, or you might get stuck in an infinite loop.

4. Real-World Use Cases
1. Database Indexing: When you query a database for a specific user_id, the database uses a B-Tree index, which is essentially a high-powered Binary Search.
2. Version Control (Git Bisect): If your code breaks, you don't check every commit. You use git bisect to Binary Search through your commit history to find the exact commit that introduced the bug.
3. Dictionary/Autocompletion: Finding a word in a sorted list of thousands of words.
4. Libraries & Compilers: Finding the correct function address in a symbol table during code execution.

5. Practice Links
* LeetCode 704: Binary Search: The standard implementation.
* LeetCode 35: Search Insert Position: A great twist where you return the index where the element should be.
 */