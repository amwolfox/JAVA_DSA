package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L2_Count_Primes_in_Range {
    // Define the maximum limit based on problem constraints (e.g., 10^6)
    private static final int MAX = 1000000;
    private static int[] prefixSum = new int[MAX + 1];

    static {
        precompute();
    }

    private static void precompute() {
        boolean[] isPrime = new boolean[MAX + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        // 1. Standard Sieve to mark primes
        for (int p = 2; p * p <= MAX; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= MAX; i += p)
                    isPrime[i] = false;
            }
        }

        // 2. Build Prefix Sum Array
        // prefixSum[i] stores count of primes from 0 to i
        int currentCount = 0;
        for (int i = 0; i <= MAX; i++) {
            if (isPrime[i]) {
                currentCount++;
            }
            prefixSum[i] = currentCount;
        }
    }

    /**
     * Answers range query in O(1) time.
     */
    public static int countPrimesInRange(int L, int R) {
        if (L <= 0) return prefixSum[R];
        return prefixSum[R] - prefixSum[L - 1];
    }

    public static void main(String[] args) {
        // Example: Count primes between 10 and 20 (11, 13, 17, 19)
        System.out.println("Primes in [10, 20]: " + countPrimesInRange(10, 20)); // Output: 4
    }
}
/*
In a Deep Stress interview, the challenge isn't just finding primes—it's doing it repeatedly and fast. If you are asked to find how many primes exist between $L$ and $R$ for 100,000 different queries, running a standard Sieve or Primality test every time will result in a Time Limit Exceeded (TLE) error.
The solution is a two-step process: Sieve of Eratosthenes followed by a Prefix Sum.

1. The "Heat Stress" Logic: Pre-computation
Instead of calculating from scratch for every query $[L, R]$, we do the heavy lifting once:
1. Generate Primes: Use the Sieve to mark all primes up to the maximum possible $R$ (usually $10^6$ in competitive programming).
2. Prefix Sum Array: Create an array count[] where count[i] stores the total number of primes from $0$ to $i$.
    * If $i$ is prime: count[i] = count[i-1] + 1
    * If $i$ is not prime: count[i] = count[i-1]
3. The Query: To find primes in $[L, R]$, the answer is simply: count[R] - count[L-1].
This reduces each query from $O(N \log \log N)$ to $O(1)$ constant time.

3. Deep Stress Breakdown
A. Time Complexity
* Pre-computation: $O(N \log \log N)$ where $N$ is the max range.
* Query Time: $O(1)$ per query.
* Total for Q queries: $O(N \log \log N + Q)$.
B. Space Complexity
* $O(N)$ to store the isPrime boolean array and the prefixSum integer array. In Java, an int[1,000,000] takes about 4MB of heap memory—well within limits.
C. The "L-1" Edge Case
When calculating the range $[L, R]$, we subtract prefixSum[L-1]. If $L=0$, $L-1$ is $-1$, which causes an ArrayIndexOutOfBoundsException. Always handle $L=0$ or use 1-based indexing carefully.

4. Real-World Use Cases
1. Cryptography (Key Generation): When generating RSA keys, systems often need to pick a random prime within a specific bit-length range (e.g., between $2^{1023}$ and $2^{1024}$). While they don't use a simple Sieve for such huge numbers, the logic of range selection is identical.
2. Database Indexing: In distributed databases, data might be "sharded" (split) based on prime number intervals to ensure an even distribution of load (avoiding "hotspots").
3. Network Routing: Some load-balancing algorithms use prime-number-based hashing to distribute traffic across a range of servers. Knowing the density of primes in a range helps in predicting hash collisions.

5. Practice Links
* LeetCode 204: Count Primes: (The basic version, but the logic here is the foundation).
* LeetCode 2523: Closest Prime Numbers in Range: Requires finding primes in a range $[left, right]$ and then finding the pair with the smallest difference.
* Codeforces / Competitive Sites: Look for "Range Prime Query" problems—they are common in Div. 2 rounds.
 */