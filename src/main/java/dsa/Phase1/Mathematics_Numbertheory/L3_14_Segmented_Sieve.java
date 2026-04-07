package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L3_14_Segmented_Sieve {

    /**
     * main method to execute the Segmented Sieve.
     * Use case: Finding primes between 1,000,000,000 and 1,000,050,000
     */
    public static void main(String[] args) {
        L3_14_Segmented_Sieve solver = new L3_14_Segmented_Sieve();

        // DEEP STRESS CASE: Large Range far from zero
        long L = 1000000000L; // 10^9
        long R = 1000000500L; // A small window of 500 numbers

        System.out.println("Searching for primes between " + L + " and " + R + "...");

        long startTime = System.currentTimeMillis();
        List<Long> primes = solver.getPrimesInRange(L, R);
        long endTime = System.currentTimeMillis();

        // Print the results
        System.out.println("Found " + primes.size() + " primes:");
        System.out.println(primes);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

    public List<Long> getPrimesInRange(long L, long R) {
        // Step 1: Precompute primes up to sqrt(R)
        int limit = (int) Math.sqrt(R);
        List<Integer> basePrimes = simpleSieve(limit);

        // Step 2: Initialize the segment array
        // isPrime[0] represents L, isPrime[i] represents L + i
        boolean[] isPrime = new boolean[(int) (R - L + 1)];
        Arrays.fill(isPrime, true);

        // Edge case: 1 is never prime
        if (L == 1) isPrime[0] = false;

        // Step 3: Mark multiples of basePrimes in the [L, R] range
        for (int p : basePrimes) {
            // Find the first multiple of p >= L
            long firstMultiple = (L / p) * p;
            if (firstMultiple < L) {
                firstMultiple += p;
            }

            // If the first multiple is the prime itself, move to the next multiple
            if (firstMultiple == p) {
                firstMultiple += p;
            }

            // Mark all multiples of p in the segment
            for (long j = firstMultiple; j <= R; j += p) {
                isPrime[(int) (j - L)] = false;
            }
        }

        // Step 4: Collect all remaining 'true' indices as primes
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                result.add(L + i);
            }
        }
        return result;
    }

    // Standard Sieve for precomputing small primes
    private List<Integer> simpleSieve(int n) {
        boolean[] temp = new boolean[n + 1];
        Arrays.fill(temp, true);
        temp[0] = temp[1] = false;
        for (int p = 2; p * p <= n; p++) {
            if (temp[p]) {
                for (int i = p * p; i <= n; i += p) {
                    temp[i] = false;
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (temp[i]) primes.add(i);
        }
        return primes;
    }
}