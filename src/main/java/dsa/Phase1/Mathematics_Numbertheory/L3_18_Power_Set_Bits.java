package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L3_18_Power_Set_Bits {
    public static void main(String[] args) {
        L3_18_Power_Set_Bits sol = new L3_18_Power_Set_Bits();
        int[] nums = {1, 2, 3, 5};
        List<List<Integer>> ans;
        ans = sol.subsets(nums);
        System.out.println(ans);
        System.out.println("Total count of subset is: " + ans.size());
    }

    /**
     * Generates all subsets in O(N * 2^N) time.
     * Complexity: O(2^N) space to store the result.
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        // 1. THE TOTAL COUNT
        // 1 << n is the same as 2^n.
        // For n=3, limit is 8 (0 to 7).
        int totalSubsets = 1 << n;

        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> currentSubset = new ArrayList<>();

            // 2. CHECK EACH BIT
            // For each number 'i', check which bits are '1'
            for (int j = 0; j < n; j++) {
                // If the j-th bit of 'i' is set
                if ((i & (1 << j)) != 0) {
                    currentSubset.add(nums[j]);
                }
            }
            result.add(currentSubset);
        }
        return result;
    }
}
/*
Generating all subsets of a set (the Power Set) is where Binary logic meets Combinatorics. In a Deep Stressinterview, if you're asked to find every possible combination of $N$ items, you don't need complex recursion. You just need to count from $0$ to $2^N - 1$.
The core insight: A subset is just a sequence of "Yes" or "No" decisions. For each element, do you include it ($1$) or exclude it ($0$)?

1. The "Heat Stress" Logic: Mapping Bits to Elements
If you have a set {A, B, C}, there are $2^3 = 8$ subsets. If we look at the binary representation of numbers from $0$ to $7$, we see every possible combination:
Number	Binary	Subset
0	000	{ } (Empty)
1	001	{C}
2	010	{B}
3	011	{B, C}
4	100	{A}
5	101	{A, C}
6	110	{A, B}
7	111	{A, B, C}

3. Deep Stress Breakdown
A. Why is this better than Recursion?
While backtracking/recursion is common, the bitwise approach is Iterative.
* Predictable Memory: No risk of StackOverflowError for deep sets.
* State Representation: You can represent any subset as a single integer (a "bitmask"). This is crucial for advanced problems like Dynamic Programming on Trees or TSP (Traveling Salesman Problem).
B. The (i & (1 << j)) Trick
This is the standard way to check if the $j$-th bit of $i$ is set.
* 1 << j creates a number with only the $j$-th bit as $1$ (e.g., if $j=2$, it creates 0100).
* The & (AND) operation returns non-zero only if $i$ also has a $1$ at that exact position.
C. Constraint Warning
Because $2^N$ grows exponentially, this approach is only feasible for $N \le 20$. If $N=30$, you have $1$ billion subsets. If $N=64$, you've exceeded the number of atoms in the universe.

4. Real-World Use Cases
1. Feature Selection in Machine Learning: If you have 10 parameters and want to find the best combination of features to train a model, you are essentially testing every subset of the Power Set.
2. E-commerce Filters: If a user selects filters (Size: M, Color: Red, Brand: Nike), the system checks which "subset" of attributes the product matches.
3. Password Cracking (Brute Force): Generating all combinations of characters to find a match.
4. Portfolio Optimization: In Finance (relevant to your interest in ETFs), if you have 5 stocks, you might analyze the risk/reward of all possible subsets (mini-portfolios) you could build from them.

5. Practice Links
* LeetCode 78: Subsets: (The exact problem we solved).
* LeetCode 90: Subsets II: (Handles duplicate elements—requires sorting and skipping bits).
* LeetCode 1601: Maximum Number of Achievable Transfer Requests: A "Hard" problem where bitmasking all subsets is the most elegant solution.

DRY RUN:
To understand i & (1 << j), think of it as a "Spotlight" or a "Checkmark." It is the most efficient way to ask a computer: "Is the $j$-th bit of this number turned on?"
Let's do a Deep Stress dry run with $i = 6$ and $j = 2$.

1. The Components
* $i = 6$: In binary, this is 110.
* $j = 2$: We want to check the bit at the 2nd position (remember, we count from 0, starting from the right).

2. Step 1: The Left Shift (1 << j)
We take the number 1 (binary 001) and shift it to the left $j$ times.
* $j = 0 \rightarrow 001$
* $j = 1 \rightarrow 010$
* $j = 2 \rightarrow 100$
The result of (1 << 2) is 100. This is our Mask. It has a 1 only at the position we care about.

3. Step 2: The Bitwise AND &
Now we compare our number $i$ (110) with our mask (100) using the AND gate. An AND gate only returns 1 if bothbits are 1.
Plaintext

    1 1 0  (i = 6)
  & 1 0 0  (mask = 1 << 2)
  ---------
    1 0 0  (Result = 4)

4. Step 3: The Boolean Check
The code says if ((i & (1 << j)) != 0).
* Our result is 4.
* Is 4 != 0? Yes.
* Conclusion: The 2nd bit of 6 is ON. (We include the 2nd element of the array in our subset).

5. Let's try a "Fail" Case ($i=6, j=0$)
* $i = 6$ (110)
* $j = 0$ (1 << 0 is 001)

    1 1 0  (i = 6)
  & 0 0 1  (mask = 1 << 0)
  ---------
    0 0 0  (Result = 0)
* Is 0 != 0? No.
* Conclusion: The 0-th bit of 6 is OFF. (We skip the 0-th element).

Why this is "Deep Stress" Professionalism
1. Speed: Bitwise operations happen directly on the CPU registers. They are much faster than calling Math.pow(2, j)or using string manipulation.
2. Memory: In your Node.js/TypeScript work, if you need to store 32 different "True/False" settings for a user, you could use an array of 32 booleans (which takes a lot of memory) or one single integer using this bitmasking logic.

 */