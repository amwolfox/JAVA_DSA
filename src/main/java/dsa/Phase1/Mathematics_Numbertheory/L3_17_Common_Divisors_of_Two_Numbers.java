package dsa.Phase1.Mathematics_Numbertheory;

public class L3_17_Common_Divisors_of_Two_Numbers {
    public static void main(String[] args) {
        int x = 12, y = 18;
        int gcd = getGCD(x, y);
        System.out.println("Common divisors of " + x + " and " + y + " is: " + countDivisors(gcd));
    }

    /**
     * EUCLIDEAN ALGORITHM (Iterative)
     * Why Iterative? It avoids StackOverflow for extremely deep recursions.
     * Complexity: O(log(min(a, b))) Time | O(1) Space
     */
    public static int getGCD(int a, int b) {
        while (b != 0) {
            // The core logic: Replace (a, b) with (b, a % b)
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a; // When b becomes 0, 'a' holds the GCD
    }

    /**
     * Finds the count of common divisors in O(log(min(A, B)) + sqrt(GCD))
     */
    public static int countDivisors(int x) {
        // 2. Count divisors of g in O(sqrt(g))
        int count = 0;
        for (int i = 1; i * i <= x; i++) {
            if (x % i == 0) {
                // If divisors are different (e.g., 2 and 3 for 6)
                if (i * i != x) {
                    count += 2;
                } else {
                    // If it's a perfect square (e.g., 4*4 for 16)
                    count += 1;
                }
            }
        }
        return count;
    }
}
/*
To solve this in Deep Stress mode, we don't just find divisors of $A$ and then divisors of $B$. We use the Greatest Common Divisor (GCD).
The core mathematical insight is this: Any number that divides both $A$ and $B$ must also divide their GCD.Therefore, the problem of "Common Divisors of $A$ and $B$" is reduced to "Finding the total number of divisors of $GCD(A, B)$."

1. The "Heat Stress" Logic: The GCD Shortcut
Instead of checking every number up to the smaller of $A$ and $B$ ($O(min(A, B))$), we do this:
1. Calculate $G = GCD(A, B)$ using the Euclidean Algorithm ($O(\log(min(A, B)))$).
2. Count Divisors of $G$ using the $O(\sqrt{G})$ approach we learned earlier.
Example: $A=12, B=18$
* $GCD(12, 18) = 6$.
* Divisors of 6 are: 1, 2, 3, 6.
* Count = 4. (These are the only numbers that divide both 12 and 18).

3. Deep Stress Breakdown
A. Why the GCD?
If $d$ divides $A$ and $d$ divides $B$, then $d$ must divide any linear combination of $A$ and $B$, including their greatest common divisor. By finding the divisors of the GCD, we are guaranteed to find every single common factor.
B. Time Complexity
* GCD: $O(\log(\min(A, B)))$.
* Divisor Counting: $O(\sqrt{GCD(A, B)})$.
* This is vastly superior to the brute force $O(N)$ approach. Even if $A$ and $B$ are $10^9$, this runs in roughly 31,000 operations.
C. Edge Cases
* $A$ or $B$ is 1: The only common divisor is 1. The GCD will be 1, and the count will be 1. Correct.
* $A = B$: The GCD is $A$. We just count all divisors of $A$. Correct.

4. Real-World Use Cases
1. Resource Allocation: If you have 100 CPUs and 150 Storage Units, and you want to divide them into equal-sized "clusters" where each cluster has the same number of CPUs and Storage, the possible number of clusters you can create is any common divisor of 100 and 150.
2. Frontend Layouts: In a grid-based UI (like a Node.js dashboard you might build), if you have two different container widths and you want to find a "tile size" that fits perfectly into both without gaps, you look for common divisors.
3. Data Synchronization: In distributed systems, if two different services have different "heartbeat" intervals ($A$ms and $B$ ms), the common divisors represent the intervals where both services will sync up simultaneously.
4. Graphics/Resolution: Finding compatible aspect ratios or pixel-density scales that work across two different screen resolutions.

5. Practice Links
* LeetCode 1979: Find Greatest Common Divisor of Array: A starting point for GCD logic.
* LeetCode 2427: Number of Common Factors: This is the exact problem we just solved.
* LeetCode 1071: Greatest Common Divisor of Strings: A brilliant "Deep Stress" twist where the same logic is applied to text strings instead of numbers.
 */