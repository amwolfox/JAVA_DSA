package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L2_08_Prime_Factorization {
    private static final int MAX = 1000001;
    private static int[] spf = new int[MAX];

    // Static block to precompute SPF once when the class loads
    static {
        precomputeSPF();
    }

    private static void precomputeSPF() {
        // Initialize SPF for every number as itself
        for (int i = 2; i < MAX; i++) spf[i] = i;

        // Modified Sieve: For every prime 'p',
        // mark its multiples with 'p' if they don't have an SPF yet.
        for (int p = 2; p * p < MAX; p++) {
            if (spf[p] == p) { // 'p' is prime
                for (int i = p * p; i < MAX; i += p) {
                    // Only update if it's the first time reaching this number
                    if (spf[i] == i) {
                        spf[i] = p;
                    }
                }
            }
        }
    }

    /**
     * Factorizes N in O(log N) using the precomputed SPF array.
     */
    public static List<Integer> getPrimeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        while (n > 1) {
            factors.add(spf[n]);
            n /= spf[n]; // Move to the next factor instantly
        }
        return factors;
    }

    public static void main(String[] args) {
        System.out.println("Prime Factors of 122: " + getPrimeFactors(122)); // [2, 61]
        System.out.println("Prime Factors of 100: " + getPrimeFactors(100)); // [2, 2, 5, 5]
    }
}
/*
In a Deep Stress interview, the standard way to find prime factors is $O(\sqrt{N})$. But what if the interviewer says, "I have 100,000 numbers, and I need the prime factorization of each one instantly"?
The $O(\sqrt{N})$ approach will fail. To handle multiple queries, we use a modified Sieve of Eratosthenes to precompute the Smallest Prime Factor (SPF) for every number. This reduces the factorization of any number $N$ to $O(\log N)$ time.

1. The "Heat Stress" Logic: The SPF Sieve
In a normal Sieve, we mark numbers as true or false. In an SPF Sieve, we store the smallest prime that divides each number.
* SPF[6] = 2
* SPF[9] = 3
* SPF[25] = 5
The Factorization Trick:
Once you have the SPF array, you don't need to "search" for factors. You just look at SPF[N], divide $N$ by it, and repeat until you reach 1.
Example: $N = 12$
1. SPF[12] is 2. (Factor: 2, $12/2 = 6$)
2. SPF[6] is 2. (Factor: 2, $6/2 = 3$)
3. SPF[3] is 3. (Factor: 3, $3/3 = 1$) Result: $2 \times 2 \times 3$

3. Deep Stress Breakdown
A. Time Complexity
* Pre-computation: $O(N \log \log N)$ (Standard Sieve speed).
* Query Time: $O(\log N)$ per number. (Because a number $N$ can have at most $\log_2 N$ prime factors).
* Total for $Q$ queries: $O(N \log \log N + Q \log N)$.
B. Space Complexity
* $O(N)$ for the spf[] array. For $10^6$ integers, this is $\approx 4$ MB.
C. Why is it $O(\log N)$?
The smallest prime factor is at least 2. Dividing a number by at least 2 repeatedly is a logarithmic process. This is the fastest possible way to factorize a number in an interview.

4. Real-World Use Cases
1. Distributed Computing (Data Sharding): In big data, you often need to distribute tasks across $P$ processors. Using prime factors helps in creating consistent hashing algorithms that minimize data movement when the number of processors changes.
2. Signal Processing: Fast Fourier Transforms (FFT)—used in audio and image compression—rely heavily on the prime factorization of the signal's length to split the problem into smaller pieces.
3. Finance & Security: Identifying the "DNA" of a number through its prime factors is used in detecting patterns in stock market fluctuations or identifying malicious packet sizes in network security.

5. Practice Links
1. LeetCode 952: Largest Component Size by Common Factor: (Hard!) You must use SPF to factorize numbers quickly or you will TLE.
2. LeetCode 2507: Smallest Value After Replacing with Sum of Prime Factors: A direct application of factorization.
3. LeetCode 2521: Distinct Prime Factors of Product of Array: Use SPF to find distinct factors across a whole array.

Summary Pro-Tip
Since you follow a health-conscious diet, think of Prime Factorization like a Nutritional Label. A number is the "product," and its prime factors are the "raw ingredients." The SPF Sieve is like a Barcode Scanner—instead of guessing the ingredients, you scan the number and get the full breakdown instantly.
Ready for Concept 9: Binary Exponentiation (pow(x, n))? This is the core of modular arithmetic and public-key cryptography!

 */