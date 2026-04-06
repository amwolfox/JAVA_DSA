package dsa.Phase1.Mathematics_Numbertheory;

public class L2_06_Trailing_Zeroes_in_Factorial {
    public static void main(String[] args) {
        System.out.println(trailingZeroes(25)); // Output: 6
    }

    /**
     * Calculates trailing zeroes in N! in O(log5 N) time.
     * Complexity: O(log N) Time | O(1) Space
     */
    public static int trailingZeroes(int n) {
        int count = 0;

        // 1. LOGARITHMIC LOOP
        // We divide n by 5, then 25, then 125...
        // We stop when n / i becomes 0.
        // We use a long for 'i' to prevent overflow if n is near Integer.MAX_VALUE.
        for (long i = 5; n / i >= 1; i *= 5) {
            count += (int) (n / i);
        }

        return count;
    }
}
/*
This problem is a "Deep Stress" classic because it tests if you can move from a brute force mindset (calculating the factorial) to a mathematical logic mindset.
Calculating $100!$ is impossible for a long variable (it has 158 digits). To find the trailing zeroes, we don't need the actual number; we only need to understand how a "zero" is born.

1. The "Heat Stress" Logic: The $2 \times 5$ Rule
A trailing zero is created every time we multiply by 10.
The prime factors of 10 are 2 and 5.
$$\text{Trailing Zeroes} = \text{Number of pairs of } (2 \times 5)$$
The Observation:
In any factorial (like $1 \times 2 \times 3 \times 4 \times 5...$), multiples of 2 appear much more frequently than multiples of 5.
* Every 2nd number is a multiple of 2.
* Only every 5th number is a multiple of 5.
The Strategy: Since 2s are abundant, the number of 5s is the "limiting factor." To find the zeroes, we simply count how many times the number 5 appears as a factor in the sequence.

2. The "Deep Stress" Catch: Powers of 5
You can't just do $N / 5$. Why? Because numbers like 25, 125, and 625 contain multiple 5s.
* $25 = 5 \times 5$ (Contributes two 5s)
* $125 = 5 \times 5 \times 5$ (Contributes three 5s)
To count them all:
1. Count how many numbers are divisible by 5 ($\lfloor N/5 \rfloor$).
2. Count how many are divisible by 25 ($\lfloor N/25 \rfloor$) to get that "extra" 5.
3. Count how many are divisible by 125... and so on.


4. Deep Stress Breakdown
A. Why the Loop condition n / i >= 1?
As $i$ grows ($5, 25, 125...$), eventually $i$ will be larger than $N$. At that point, $N/i$ becomes $0$, meaning there are no more factors of 5 to count.
B. Example: $N = 25$
* $25 / 5 = 5$ (Numbers: 5, 10, 15, 20, 25) i.e we have to count many numbers have 5 as a digit.
* $25 / 25 = 1$ (The number 25 provides one extra factor of 5)
* Total: $5 + 1 = \mathbf{6}$ zeroes.
C. Time Complexity: $O(\log_5 N)$
Since we multiply the divisor by 5 in every step, the loop runs very few times. Even for $N = 2,147,483,647$, the loop only runs 13 times. This is incredibly efficient compared to calculating a factorial.

5. Real-World Use Cases
1. Combinatorics & Probability: In large scale simulations (like calculating the number of ways to arrange a deck of cards or chess positions), we often deal with huge factorials. Knowing the "scale" (number of zeroes) helps in precision management.
2. Precision Loss Prevention: In Data Engineering, when multiplying large datasets, trailing zeroes indicate how much "precision" is just empty space. This helps in optimizing storage and floating-point calculations.
3. BigInt Optimization: Libraries like java.math.BigInteger use similar logic to optimize how they store and print large numbers in base-10.
4. Resource Estimation: In distributed systems, if an algorithm is $O(N!)$, knowing the trailing zeroes helps estimate the "magnitude" of the task.

6. Practice Links
* LeetCode 172: Factorial Trailing Zeroes: This is the exact problem.
* LeetCode 793: Preimage Size of Factorial Zeroes Function: (Hard Version) Given the number of zeroes, how many $N$ values produce that count?

 */