package dsa.Phase1.Mathematics_Numbertheory;

public class L3_15_Nth_Fibonacci {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        L3_15_Nth_Fibonacci sol = new L3_15_Nth_Fibonacci();
        System.out.println("100th Fibonacci: " + sol.fib(100));
    }

    /**
     * Finds Nth Fibonacci in O(log N) using Matrix Exponentiation.
     */
    public long fib(long n) {
        if (n <= 1) return n;

        // The transformation matrix T
        long[][] T = {{1, 1}, {1, 0}};

        // Raise matrix T to the power of (n-1)
        long[][] result = matrixPower(T, n - 1);

        // The answer is the top-left element (result[0][0])
        // since F1 = 1 and F0 = 0.
        return result[0][0];
    }

    // Binary Exponentiation applied to Matrices
    private long[][] matrixPower(long[][] A, long p) {
        long[][] res = {{1, 0}, {0, 1}}; // Identity Matrix (like '1' for numbers)
        while (p > 0) {
            if (p % 2 == 1) res = multiply(res, A);
            A = multiply(A, A);
            p /= 2;
        }
        return res;
    }

    // Standard 2x2 Matrix Multiplication with Modulo
    private long[][] multiply(long[][] A, long[][] B) {
        long[][] C = new long[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return C;
    }
}
/*
Finding the $n$-th Fibonacci number in $O(\log n)$ is the ultimate "Deep Stress" flex. While a simple loop is $O(n)$, and recursion is $O(2^n)$, Matrix Exponentiation allows you to find the $10^{18}$-th Fibonacci number in about 60 steps.
It works by combining linear algebra with the Binary Exponentiation logic we learned earlier.

1. The "Heat Stress" Logic: The Magic Matrix
We can represent the Fibonacci transition as a matrix multiplication. If we have the pair $(F_n, F_{n-1})$, how do we get to the next pair $(F_{n+1}, F_n)$?
The relationship is:
* $F_{n+1} = 1 \cdot F_n + 1 \cdot F_{n-1}$
* $F_n = 1 \cdot F_n + 0 \cdot F_{n-1}$
In matrix form, this is:
$$\begin{pmatrix} F_{n+1} \\ F_n \end{pmatrix} = \begin{pmatrix} 1 & 1 \\ 1 & 0 \end{pmatrix} \begin{pmatrix} F_n \\ F_{n-1} \end{pmatrix}$$
If you want the $n$-th number, you just raise that transformation matrix to the power of $(n-1)$:
$$\begin{pmatrix} F_n \\ F_{n-1} \end{pmatrix} = \begin{pmatrix} 1 & 1 \\ 1 & 0 \end{pmatrix}^{n-1} \begin{pmatrix} F_1 \\ F_0 \end{pmatrix}$$

3. Deep Stress Breakdown
A. Why $O(\log n)$?
Just like $2^{10}$ can be calculated as $((2^2)^2)^2 \dots$, a matrix can be squared. Each squaring operation doubles the "distance" we jump in the Fibonacci sequence. To reach $10^{18}$, we only need to square the matrix about 60 times.
B. The Identity Matrix
In normal math, $x^0 = 1$. In matrix math, the "1" is the Identity Matrix ($I = \begin{pmatrix} 1 & 0 \\ 0 & 1 \end{pmatrix}$). Multiplying any matrix by $I$ leaves it unchanged. We start our res with this.
C. Handling the Modulo
Fibonacci numbers grow exponentially. The 100th Fibonacci number is already too big for a long. In interviews, they almost always ask for the answer modulo $10^9+7$. We apply the modulo inside the matrix multiplication to keep the numbers safe.

multiply: A[i k] * B[k j]
4. Real-World Use Cases
1. Linear Recurrence Relations: Any sequence where the next term is a linear combination of previous terms (like $a_n = 3a_{n-1} + 5a_{n-2}$) can be solved using this method.
2. Graph Theory (Paths of length K): If you have an adjacency matrix $A$ of a graph, $A^k$ tells you the number of paths of length $k$ between any two nodes. Matrix exponentiation finds this for massive $k$ in $O(\log k)$.
3. Population Modeling: Scientists use "Leslie Matrices" to predict population growth over hundreds of generations. Matrix exponentiation lets them jump straight to the year 3000.
4. Computer Graphics: Complex transformations (rotation, scaling, translation) are represented as matrices. Applying the same transformation $N$ times is just matrix exponentiation.

5. Practice Links
* LeetCode 509: Fibonacci Number: (Start here, though $N$ is small enough for $O(N)$).
* LeetCode 70: Climbing Stairs: This is secretly a Fibonacci problem!
* LeetCode 1220: Count Vowels Permutation: A "Hard" problem that is solved beautifully using Matrix Exponentiation.
 */