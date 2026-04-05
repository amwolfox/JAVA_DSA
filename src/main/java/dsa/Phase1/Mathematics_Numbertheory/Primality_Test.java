package dsa.Phase1.Mathematics_Numbertheory;

import java.util.Scanner;

public class Primality_Test {

    /**
     * Optimized Primality Test using Trial Division and 6k ± 1 Optimization.
     * Complexity: O(√N) Time | O(1) Space
     */
    public static boolean isPrime(long n) {
        // 1. BASE CASE ELIMINATION (O(1))
        // Numbers <= 1 are not prime by definition.
        // 2 and 3 are the only primes that don't follow the 6k ± 1 pattern.
        if (n <= 1) return false;
        if (n <= 3) return true;

        // 2. THE "TWO-THIRDS" RULE (O(1))
        // By checking divisibility by 2 and 3 upfront, we instantly
        // eliminate ~66.6% of all integers (all evens and multiples of 3).
        // This allows our main loop to "jump" over these non-candidates.
        if (n % 2 == 0 || n % 3 == 0) return false;

        // 3. THE SQUARE ROOT PROPERTY (O(√N))
        // Mathematical Fact: If 'n' has a divisor 'd', then at least one
        // divisor must be <= √n. Thus, we only search up to i * i <= n.
        // PERFORMANCE NOTE: 'i * i <= n' is faster than 'i <= Math.sqrt(n)'
        // because it avoids expensive floating-point CPU instructions.

        // 4. THE 6k ± 1 JUMP
        // Every prime number > 3 is of the form 6k ± 1 (e.g., 5, 7, 11, 13...).
        // We start 'i' at 5 (6*1 - 1) and increment by 6.
        // Inside, we check 'i' (6k-1) and 'i+2' (6k+1).
        // This effectively "skips" 2, 3, 4, and 6 in every block of 6 numbers.
        for (long i = 5; i * i <= n; i += 6) {
            // If 'n' is divisible by either candidate in the 6k ± 1 pair, it's not prime.
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        // 5. If no divisors were found up to √n, 'n' is mathematically guaranteed to be prime.
        return true;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter a number for which u want to test a prime number: ");
        long n = scn.nextLong();
        System.out.printf("is %d a Prime number?: %s", n, isPrime(n));
    }
}
/*
LeetCode 204: Count Primes (This introduces the Sieve, but try the O(sqrt n) approach first to see why it fails for large ranges!).
LeetCode 2523: Closest Prime Numbers in Range (Requires efficient primality checks).
LeetCode 762: Prime Number of Set Bits in Binary Representation (A mix of Bit Manipulation and Primality).
 */