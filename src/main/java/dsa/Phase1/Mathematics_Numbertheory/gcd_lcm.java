package dsa.Phase1.Mathematics_Numbertheory;

public class gcd_lcm {
    public static void main(String[] args) {
        long a = 48, b = 18;
        System.out.println("GCD: " + getGCD(a, b)); // Output: 6
        System.out.println("LCM: " + getLCM(a, b)); // Output: 144
    }

    /**
     * EUCLIDEAN ALGORITHM (Iterative)
     * Why Iterative? It avoids StackOverflow for extremely deep recursions.
     * Complexity: O(log(min(a, b))) Time | O(1) Space
     */
    public static long getGCD(long a, long b) {
        while (b != 0) {
            // The core logic: Replace (a, b) with (b, a % b)
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a; // When b becomes 0, 'a' holds the GCD
    }

    /**
     * LCM FORMULA
     * Formula: (a * b) / GCD(a, b)
     * STRESS TIP: Always divide BEFORE multiplying to prevent Integer Overflow.
     */
    public static long getLCM(long a, long b) {
        if (a == 0 || b == 0) return 0;

        // Correct way: (a / GCD) * b
        // Incorrect way: (a * b) / GCD -> This might overflow 'long' before dividing!
        return (a / getGCD(a, b)) * b;
    }
}

/*
The Greatest Common Divisor (GCD) and Least Common Multiple (LCM) are the "building blocks" of all rational math in DSA. While you can find the GCD by checking all factors (brute force), the Euclidean Algorithm is a "Deep Stress" masterpiece that reduces the problem to $O(\log(\min(A, B)))$.
In any technical interview, if you don't use the Euclidean Algorithm for GCD, you are essentially leaving performance on the table.

The "Heat Stress" Logic: The Euclidean Principle
The Euclidean algorithm is based on a simple but profound observation:

The GCD of two numbers does not change if the larger number is replaced by its difference with the smaller number.

The Optimization (Modulo):
Instead of subtracting repeatedly (which is slow), we use the remainder (modulo).
For any two numbers $a$ and $b$ (where $a > b$):
$$\text{GCD}(a, b) = \text{GCD}(b, a \pmod b)$$
Why it stops?
We keep repeating this until the remainder becomes 0. The last non-zero number is our GCD.

Deep Stress Breakdown
A. The "Overflow" Trap
When calculating LCM, if $a = 10^9$ and $b = 10^9$, then $a \times b = 10^{18}$. This is very close to the limit of a Java long ($9 \times 10^{18}$). If the numbers were slightly larger, $a \times b$ would overflow and give a negative result.
* The Fix: Since $GCD(a, b)$ is a divisor of $a$, you should do (a / gcd) * b. This keeps the numbers smaller during calculation.
B. Time Complexity $O(\log(\min(A, B)))$
The Euclidean algorithm is incredibly fast. Even for numbers with 100 digits, it finds the GCD in a few hundred steps. It is mathematically proven that the number of steps is related to the Fibonacci sequence, making it one of the most efficient algorithms in history.
C. The "Negative Number" Case
In Java, % can return a negative value if the input is negative. In a professional setting, always use Math.abs(a) and Math.abs(b) before starting the GCD process.

4. Practice Links
Mastering GCD is essential for problems involving fractions, grids, or periodic patterns:
1. LeetCode 1979: Find Greatest Common Divisor of Array (The basic implementation).
2. LeetCode 1201: Ugly Number III (Advanced use of LCM and Binary Search).
3. LeetCode 365: Water and Jug Problem (A classic brain teaser solved purely by GCD properties).
4. LeetCode 914: X of a Kind in a Deck of Cards (Checking if a common GCD $> 1$ exists for all frequencies).

Summary Pro-Tip
Since you're a Chess strategist, think of GCD like finding the "Common Rhythm" between two pieces. If one piece moves 48 squares and another moves 18, the largest "tile size" they both share is 6.
Does the "Divide before Multiply" trick for LCM make sense as a way to protect your code from crashing on large numbers?

 */