Number Theory is the "hidden engine" of competitive programming.
In a Deep Stress scenario, you aren't just calculating numbers; you are optimizing $O(N)$ approaches into $O(\sqrt{N})$
or even $O(\log N)$ by using mathematical properties.

---

## 1. The Core "Heat Stress" Concepts

### A. Primality & The $\sqrt{N}$ Rule

The most basic mistake is checking primality by looping from $2$ to $N-1$.
**The Stress Logic:** If a number $n$ has a divisor $d$ greater than $\sqrt{n}$, it must also have a divisor $n/d$
smaller than $\sqrt{n}$. Therefore, you only ever need to check up to $\sqrt{n}$.

### B. Sieve of Eratosthenes

If you need to find all primes up to $10^6$, checking each number individually is too slow ($O(N\sqrt{N})$). The Sieve
marks multiples of primes as "not prime," achieving a massive speedup of $O(N \log(\log N))$.

### C. Euclidean Algorithm (GCD & LCM)

Finding the Greatest Common Divisor using the remainder ($a \% b$) is the foundation of many "grid" or "pattern"
problems.
$$\text{GCD}(a, b) = \text{GCD}(b, a \% b)$$

* **LCM Formula:** $(a \times b) / \text{GCD}(a, b)$

### D. Modular Arithmetic

In DSA, results often exceed the `long` limit ($2^{63}-1$). You are often asked to return the answer "
Modulo $10^9 + 7$".

* $(A + B) \% M = (A \% M + B \% M) \% M$
* $(A \times B) \% M = (A \% M \times B \% M) \% M$
* **Warning:** Division is different! You need the **Modular Multiplicative Inverse** (Fermat's Little Theorem).

### E. Fast Exponentiation (Binary Exponentiation)

Calculating $2^{1000}$ normally takes 1000 steps. Binary exponentiation breaks the power into bits, reducing it
to $\log(1000) \approx 10$ steps. This is critical for DP and Matrix Exponentiation.

---

## 2. Top 20 Interview Questions (DSA Perspective)

These are categorized from "Warm-up" to "Hard Optimization."

Prompt:
EXPLAIN THIS IN DEEP STRESS AND SOLUTION with comments TOO AND SOME LEETCODE LINK TO PRACTISE, and also explain the use
case where it can be used

### Level: Basic (The Foundation)

- [x] **1. Primality_Test:** Check if $N$ is prime in $O(\sqrt{N})$.
- [x] **2. All_Divisors:** Print all divisors of $N$ in $O(\sqrt{N})$.
- [x] **3. Sieve_of_Eratosthenes:** Find all primes up to $N$.
- [x] **4. GCD_LCM:** Implement the Euclidean algorithm.
- [x] **5. Palindrome_Number:** Reverse an integer without using strings (to avoid extra space).

### Level: Intermediate (Pattern Recognition)

- [x] **6. Trailing_Zeroes_in_Factorial:** How many zeroes at the end of $N!$? (Hint: Count factors of 5).
- [x] **7. Count_Primes_in_Range:** Use Sieve to answer multiple queries efficiently.
- [x] **8. Prime_Factorization:** Find all prime factors of $N$ using a precomputed Sieve.
- [x] **9. Binary_Exponentiation:** Implement `pow(x, n)` in $O(\log n)$.
- [x] **10. Modular_Inverse:** Find $x$ such that $(a \times x) \% m = 1$.
- [x] **11. Excel_Column_Title:** Convert column number to title (Base-26 logic).
- [x] **12. Square_Root_Integral:** Find $\lfloor\sqrt{x}\rfloor$ without using `sqrt()` (Binary Search on math).
- [x] **13. Factorial_of_a_Large_Number:** Calculate the factorial of a large number.

### Level: Advanced (Mathematical Strategy)

- [x] **14. Segmented_Sieve:** Find primes in a range $[L, R]$ where $R$ is very large ($10^{12}$) but $R-L$ is small.
- [ ] **15. Nth_Fibonacci (Log N):** Use Matrix Exponentiation to find huge Fibonacci numbers.
- [ ] **16. Chinese Remainder Theorem:** Solving a system of simultaneous congruences.
- [ ] **17. Common Divisors of Two Numbers:** Find the count of numbers that divide both $A$ and $B$.
- [ ] **18. Power Set / Subsets:** Use Bit Manipulation to generate all $2^N$ subsets (Math meets Bits).
- [ ] **19. Pigeonhole Principle Problems:** E.g., "Find a subarray whose sum is divisible by $N$."
- [ ] **20. Catalan Numbers:** Used in counting BSTs or valid bracket sequences.
- [ ] **21. Lucas Theorem:** Calculating $^nC_r \% p$ when $p$ is a small prime.

---