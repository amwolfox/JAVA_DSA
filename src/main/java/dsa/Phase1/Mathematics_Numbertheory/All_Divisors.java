package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class All_Divisors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number to get all divisors::");
        int n = scanner.nextInt();
        printAllDivisors(n);
    }

    /**
     * Finds and prints all divisors of N in O(√N) time.
     */
    public static void printAllDivisors(int n) {
        // List to store divisors if you need them sorted later
        List<Integer> divisors = new ArrayList<>();

        // 1. THE SQUARE ROOT BOUNDARY (O(√N))
        // We only loop up to √n because factors come in pairs.
        for (int i = 1; i * i <= n; i++) {

            // 2. CHECK DIVISIBILITY
            if (n % i == 0) {
                // 'i' is a divisor
                divisors.add(i);

                // 3. FIND THE PARTNER
                // If 'i' is a divisor, then 'n/i' is its pair.
                // We must check that we don't add the same number twice
                // (this happens if 'n' is a perfect square, like 6*6=36).
                if (i != n / i) {
                    divisors.add(n / i);
                }
            }
        }

        // 4. SORTING (Optional)
        // Since we find 1 and 36 at the same time, the list won't be sorted.
        // Sorting takes O(D log D) where D is the number of divisors.
        Collections.sort(divisors);

        System.out.println("Divisors of " + n + ": " + divisors);
    }
}

/*
Finding all divisors of a number is the natural follow-up to the Primality Test. In a **Deep Stress** interview, the "trap" is to simply print the numbers as you find them.

If you loop from $1$ to $N$, the complexity is $O(N)$. By using the **Square Root Property**, we can find every divisor in $O(\sqrt{N})$ time.

---

### 1. The "Heat Stress" Logic: Pair Symmetry
Every divisor has a "partner." If $d$ is a divisor of $n$, then $n/d$ is also a divisor.

**The Mathematical Fact:**
In any pair of divisors $(d, n/d)$, one must be $\le \sqrt{n}$ and the other must be $\ge \sqrt{n}$.
* Example for $N = 36$ ($\sqrt{36} = 6$):
    * (1, 36)
    * (2, 18)
    * (3, 12)
    * (4, 9)
    * (6, 6) $\leftarrow$ The square root "middle point."

**The Conclusion:** You only need to search up to $\sqrt{n}$. Every time you find a divisor $i$, you have automatically found its partner $n/i$.
        ---

### 3. Deep Stress Breakdown
#### A. Why the `i != n / i` check?
If $n = 25$, when $i = 5$, the partner $n/i$ is also $5$. Without this check, your code would output `[1, 25, 5, 5]`. In interviews, failing to handle **Perfect Squares** is a common way to lose points.

#### B. Sorting Complexity
The number of divisors ($D$) is much smaller than $N$. For example, $10^9$ has at most 1,344 divisors. Sorting them is nearly instantaneous ($O(D \log D)$), but the **search** remains $O(\sqrt{N})$.

#### C. Performance vs. Primality
While the Primality Test can skip multiples of 2 and 3 (the $6k \pm 1$ trick), the "All Divisors" algorithm **cannot**. You must check every number $i$ because even if $i$ is even, it might be a divisor of an even $N$.

---

        ### 4. Comparison Table

| Property | Primality Test | All Divisors |
        | :--- | :--- | :--- |
        | **Goal** | Return `true`/`false`. | Return a list/set of all factors. |
        | **Complexity** | $O(\sqrt{N})$ (can be optimized with $6k \pm 1$). | $O(\sqrt{N})$ (must check all $i$). |
        | **Key Edge Case** | Numbers $\le 3$. | **Perfect Squares** ($i = n/i$). |
        | **Output Order** | N/A. | Unsorted (requires `Collections.sort`). |

        ---

        ### Practice Task

        **Try this LeetCode challenge:**
        * **LeetCode 504: Base 7** (Uses similar remainder logic).
        * **LeetCode 2427: Number of Common Factors** (Apply this logic to two numbers and find the intersection).

Should we move on to the **Sieve of Eratosthenes**, or do you want to see how this logic applies to **GCD (Greatest Common Divisor)**?
 */