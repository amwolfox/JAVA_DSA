package dsa.Phase1.Mathematics_Numbertheory;

public class Modular_Inverse {
    // A common large prime used in competitive programming
    private static final long MOD = 1000000007;

    /**
     * Finds (a ^ n) % m in O(log n)
     */
    public static long power(long a, long n, long m) { // Binary Exponentiation to calculate a^b
        long res = 1;
        a %= m;
        while (n > 0) {
            if (n % 2 == 1) res = (res * a) % m;
            a = (a * a) % m;
            n /= 2;
        }
        return res;
    }

    /**
     * Finds Modular Inverse of 'a' under modulo 'm'
     * Requirement: 'm' must be a prime number.
     */
    public static long modInverse(long a, long m) {
        // Using Fermat's Little Theorem: inv(a) = a^(m-2) % m
        return power(a, m - 2, m);
    }

    public static void main(String[] args) {
        long a = 3;
        long inv = modInverse(a, MOD);
        System.out.println("Modular Inverse of 3 is: " + inv);

        // Verification: (3 * inv) % MOD should be 1
        System.out.println("Verification (3 * inv) % MOD: " + (a * inv) % MOD);
    }
}
/*

USE TO PERFORM A/B, but what is both are very large number
so we do A * inv(B)
In a Deep Stress interview, Modular Inverse is the "Grand Finale" of Number Theory. In standard math, if you want to divide $A$ by $B$, you just do $A/B$. But in the world of Modular Arithmetic (where every result must be kept under a large prime like $10^9+7$), division does not exist.
Instead of dividing by $B$, we must multiply by the Modular Multiplicative Inverse of $B$.

1. The "Heat Stress" Logic: Fermat’s Little Theorem
The most common way to find a modular inverse (when $M$ is a prime number) is using Fermat's Little Theorem.
The Theorem:
If $M$ is prime, then for any integer $a$:
$$a^{M-1} \equiv 1 \pmod M$$
The "Trick" for Division:
If we divide both sides by $a$, we get:
$$a^{M-2} \equiv a^{-1} \pmod M$$
So, the modular inverse of $a$ is simply $a^{M-2} \pmod M$. To calculate this efficiently, we reuse our Binary Exponentiation ($O(\log M)$) algorithm!

3. Deep Stress Breakdown
A. When can you NOT use this?
Fermat's Little Theorem only works if $M$ is prime and $a$ is not a multiple of $M$. If $M$ is not prime (e.g., $M=10$), you must use the Extended Euclidean Algorithm, which is a more complex $O(\log (\min(a, m)))$approach.
B. Why is this better than Brute Force?
A brute force approach would check every $x$ from $1$ to $M-1$ to see if $(a \cdot x) \% M == 1$. If $M = 10^9+7$, a brute force loop would take years. Binary Exponentiation takes 30 operations.
C. The "Precision" Problem
In Data Engineering or Node.js backends, when calculating large probabilities or combinations, regular double types lose precision. We use modular arithmetic to keep the numbers as exact integers.

4. Real-World Use Cases
1. Combinatorics ($nCr \pmod M$): To calculate $\frac{n!}{r!(n-r)!} \pmod M$, you cannot just divide. You must calculate: $$(n! \cdot \text{inv}(r!) \cdot \text{inv}((n-r)!)) \pmod M$$ 
2. Cryptography (Public Key Exchange): RSA and Elliptic Curve Cryptography (ECC) rely on the difficulty of finding modular inverses in certain groups to keep data private.
3. Hashing: Rolling hashes (used in string matching like the Rabin-Karp algorithm) require modular inverse to "slide the window" by removing the contribution of the leftmost character.
4. Error Correction: Reed-Solomon codes (used in QR codes and satellite communication) use modular arithmetic to reconstruct missing data bits.

5. Practice Links
* LeetCode 1916: Count Ways to Build Rooms in an Ant Colony: (Hard!) Requires $nCr \pmod{10^9+7}$ and modular inverse.
* LeetCode 1622: Fancy Sequence: A complex problem that requires modular inverse to "undo" multiplication and addition operations in $O(1)$.
* LeetCode 2514: Count Anagrams: Uses permutations and modular inverse.
 */