package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L3_19_Pigeonhole_Principle {
    public static void main(String[] args) {
        L3_19_Pigeonhole_Principle sol = new L3_19_Pigeonhole_Principle();
        int[] nums = {3, 1, 4, 2, 5}; // N=5
        System.out.println(Arrays.toString(sol.findSubarray(nums)));
    }

    /**
     * Finds a subarray whose sum is divisible by N in O(N) time.
     * Complexity: O(N) Time | O(N) Space
     */
    public int[] findSubarray(int[] nums) {
        int n = nums.length;
        // map stores: remainder -> index where it first occurred
        Map<Integer, Integer> map = new HashMap<>();

        // 1. THE BASE CASE (Pigeon 0)
        // Remainder 0 at index -1 (before the array starts)
        map.put(0, -1);

        long currentSum = 0;
        for (int i = 0; i < n; i++) {
            currentSum += nums[i];

            // 2. CALCULATE REMAINDER
            // Use (sum % n + n) % n to handle negative numbers in Java
            int rem = (int) ((currentSum % n + n) % n);

            // 3. CHECK THE PIGEONHOLE
            if (map.containsKey(rem)) {
                // We found a match! The subarray is from map.get(rem) + 1 to i
                int startIndex = map.get(rem) + 1;
                return Arrays.copyOfRange(nums, startIndex, i + 1);
            }

            // 4. STORE NEW REMAINDER
            map.put(rem, i);
        }

        return new int[0]; // Logic guarantees we won't reach here if size is N
    }
}

/*
The Pigeonhole Principle (PHP) sounds simple: If you have 11 pigeons and 10 holes, at least one hole must contain at least two pigeons. In a Deep Stress interview, this simple logic is used to prove that a solution must exist in $O(N)$time, even when a brute-force approach would take $O(N^2)$. The most famous application is finding a subarray whose sum is divisible by $N$.

1. The "Heat Stress" Logic: Remainder Matching
If we calculate the Prefix Sum of an array and take the modulo $N$ for each sum, we get a sequence of remainders.
* The possible remainders when dividing by $N$ are $\{0, 1, 2, \dots, N-1\}$. (There are $N$ possible "holes").
* If we have an array of size $N$, and we include the initial sum of 0, we have $N+1$ prefix sums ("pigeons").
The PHP Guarantee: According to the Pigeonhole Principle, at least two of those $N+1$ prefix sums must have the same remainder.
The Magic Math: If $PrefixSum[i] \pmod N = R$ and $PrefixSum[j] \pmod N = R$, then the difference between them $(PrefixSum[j] - PrefixSum[i])$ must be exactly divisible by $N$. This difference represents the sum of the subarray between $i$ and $j$.

3. Deep Stress Breakdown
A. Handling Negative Numbers
In Java, -1 % 5 is -1, but we need the positive remainder 4 to match the pigeonholes. The formula (sum % n + n) % n is a standard Deep Stress trick to ensure your remainders are always in the range $[0, N-1]$.
B. The $O(N)$ Proof
Because there are only $N$ possible remainders, by the time you've processed the $N$-th element of the array (plus the initial sum of 0), you are mathematically certain to have found a duplicate remainder. This turns an $O(N^2)$ search into a single linear pass.
C. The Base Case map.put(0, -1)
This is the most common mistake. If the prefix sum itself is divisible by $N$ (remainder 0), we need to subtract "nothing" from it. Setting remainder 0 at index -1 allows the formula startIndex = map.get(rem) + 1 to correctly return index 0.

4. Real-World Use Cases
1. Data Load Balancing: If you have $N$ servers and a stream of tasks with varying weights, PHP helps prove that there's a contiguous block of tasks that perfectly balances a load factor.
2. Cryptography (Hash Collisions): The "Birthday Paradox" is a form of the Pigeonhole Principle. It explains why hash collisions are inevitable when the number of inputs exceeds the square root of the number of possible hash values.
3. Network Scheduling: Finding "time slots" where multiple periodic signals align.
4. Financial Pattern Matching: In your Personal Finance interests, this logic is used to find cyclical patterns in stock market data where the net change over a period is a multiple of a certain value (e.g., matching quarterly fiscal cycles).

5. Practice Links
* LeetCode 974: Subarray Sums Divisible by K: (The most direct application).
* LeetCode 523: Continuous Subarray Sum: (Finds if a subarray sum is a multiple of $k$ and length $\ge 2$).
* LeetCode 560: Subarray Sum Equals K: (Similar prefix sum + map logic, but looking for a specific sum instead of a multiple).
 */
