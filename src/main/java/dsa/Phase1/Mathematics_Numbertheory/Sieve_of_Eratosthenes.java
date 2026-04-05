package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class Sieve_of_Eratosthenes {
    public static void main(String[] args) {
        int n = 50;
        boolean[] primes = sieve(n);

        System.out.println("Primes up to " + n + ":");
        for (int i = 2; i <= n; i++) {
            if (primes[i]) System.out.print(i + " ");
        }
    }

    /**
     * Sieve of Eratosthenes Implementation
     * Complexity: O(N log log N) Time | O(N) Space
     */
    public static boolean[] sieve(int n) {
        // 1. INITIALIZATION (O(N) Space)
        // We create a boolean array where index 'i' represents the number 'i'.
        // By default, boolean arrays in Java are initialized to 'false'.
        // We will treat 'true' as PRIME and 'false' as COMPOSITE.
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);

        // 0 and 1 are not prime by definition.
        isPrime[0] = isPrime[1] = false;

        // 2. THE MARKING ENGINE (O(N log log N))
        // We only need to check up to √n to mark all multiples.
        for (int i = 2; i * i <= n; i++) {

            // If isPrime[p] is still true, then 'p' is a prime.
            if (isPrime[i]) {

                // MARK MULTIPLES of 'p' starting from p*p.
                // We skip smaller multiples (like p*2) because they were
                // already handled by smaller primes.
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        return isPrime;
    }
}

/*
The Sieve of Eratosthenes is the ultimate "Heat Stress" algorithm for prime numbers. While the Primality Test is used to check one number, the Sieve is used when you need to know all primes in a range (e.g., up to 1 million).
Instead of dividing to see if a number is prime, the Sieve works by multiplication/marking. It’s the difference between checking every individual person's ID (Primality Test) vs. just marking everyone who is clearly over 21 at the door (Sieve).

The "Deep Stress" Logic: The Elimination Method
The algorithm assumes every number is prime at the start. Then, starting from the first prime (2), it "strikes out" all of its multiples ($4, 6, 8...$) because a multiple of a prime cannot be prime itself.


The Optimization Rules:
1. Start at $p^2$: When marking multiples of a prime $p$, you don't need to start at $2p, 3p,$ or $4p$. Why? Because those smaller multiples would have already been marked by smaller primes (2 and 3). You start marking at $p \times p$.
2. Stop at $\sqrt{N}$: Just like the Primality Test, once $p \times p > N$, there are no more multiples left to mark. Every number remaining "unmarked" is guaranteed to be prime.  
Complexity Analysis: Why is it so fast?
* Time Complexity: $O(N \log (\log N))$. This is practically linear! For $N = 10^7$, this algorithm finishes in less than a second.
* Space Complexity: $O(N)$. You must store the boolean array. This is the "Stress" trade-off: you trade Memory to get incredible Speed.
The "Internal" View:
If $N = 100$:
* When $p=2$: You mark $4, 6, 8, 10...$ (50 operations).
* When $p=3$: You mark $9, 12, 15, 18...$ (33 operations).
* When $p=5$: You mark $25, 30, 35, 40...$ (20 operations).
* By the time $p=11$, $11 \times 11 = 121$, which is $> 100$. Stop.

4. Comparison: Trial Division vs. Sieve
Feature	Trial Division (√N)	Sieve of Eratosthenes
Best Use Case	Checking one specific number.	Finding all primes in a range.
Time	$O(\sqrt{N})$	$O(N \log \log N)$
Space	$O(1)$	$O(N)$ (requires an array)
Philosophy	Checking by division.	Eliminating by multiplication.

5. Practice Link
* LeetCode 204: Count Primes: This is the direct application of the Sieve. If you try $O(\sqrt{N})$ on this, you will get TLE (Time Limit Exceeded). You must use the Sieve.

 */