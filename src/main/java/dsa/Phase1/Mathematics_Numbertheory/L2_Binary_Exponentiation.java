package dsa.Phase1.Mathematics_Numbertheory;

public class L2_Binary_Exponentiation {
    public static void main(String[] args) {
        L2_Binary_Exponentiation sol = new L2_Binary_Exponentiation();
        System.out.println(sol.myPow(2.0, 10)); // 1024.0
    }

    /**
     * Implements pow(x, n) in O(log n) time.
     * Logic: Iterative Binary Exponentiation
     */
    public double myPow(double x, int n) {
        // 1. THE OVERFLOW TRAP
        // If n is Integer.MIN_VALUE, making it positive (+2147483648)
        // overflows an 'int'. We use 'long' to stay safe.
        long nn = n;
        if (nn < 0) {
            nn = -nn;
            x = 1 / x; // x^-n is (1/x)^n
        }

        double ans = 1.0;

        // 2. THE LOGARITHMIC LOOP (Binary Logic)
        // We look at the bits of the exponent 'nn'.
        while (nn > 0) {
            // A.) If the current bit is 1 (exponent is odd), [if current bit is 0, skip it, as we don't need this in our solution ]
            if (nn == 1) { //similar to nn & 1 as it extracts last bit.
                ans = ans * x;
            }
            // B.) Square the base for the next bit
            x = x * x;
            // C.) Shift to the next bit (divide exponent by 2)
            nn = nn / 2; //similar to nn >> 1
        }
        return ans;
    }
}
/*
Binary Exponentiation, also known as Exponentiation by Squaring, is a "Deep Stress" algorithm because it turns a linear task—multiplying $x$ by itself $n$ times ($O(n)$)—into a logarithmic task ($O(\log n)$).
In modern computing, calculating $x^{1,000,000,000}$ would take a full second with a standard loop. With Binary Exponentiation, it takes about 30 operations.

1. The "Heat Stress" Logic: Divide and Conquer
The core idea is to use the properties of exponents to reduce the number of multiplications:
* If $n$ is even: $x^n = (x^{n/2})^2$
* If $n$ is odd: $x^n = x \cdot (x^{(n-1)/2})^2$
Example: $2^{10}$
1. Instead of $2 \times 2 \times 2...$ (10 times), we see $2^{10} = (2^5)^2$.
2. We calculate $2^5 = 2 \cdot (2^2)^2$.
3. We calculate $2^2 = (2^1)^2$. Every step cuts the exponent in half.

3. Deep Stress Breakdown
A. Why is it $O(\log n)$?
In every iteration, we divide the exponent nn by 2. This is exactly how many bits are in a binary number. A 32-bit integer will never take more than 32 iterations, regardless of how large the value of $n$ is.
B. Modular Exponentiation (The Most Common Use)
In DSA and Cryptography, we usually need $(x^n) \pmod m$. The logic is the same, but we apply % m at every multiplication to keep the numbers from exploding.
* ans = (ans * x) % m;
* x = (x * x) % m;
C. The Bitwise View
If $n = 13$, its binary is 1101.
$$x^{13} = x^8 \cdot x^4 \cdot x^1$$
Our loop checks the bits of $n$ from right to left. If a bit is 1, we multiply the current "squared" base into our answer.

4. Real-World Use Cases
1. Cryptography (RSA): This is the soul of RSA encryption. To encrypt a message, the computer calculates $M^e \pmod n$ where $e$ and $n$ are massive numbers (hundreds of digits). Without Binary Exponentiation, the internet would be too slow to use securely.
2. Fibonacci (Log N): You can find the $10^{18}$-th Fibonacci number in $O(\log n)$ by using Matrix Exponentiation, which uses this exact binary logic on matrices instead of single numbers.
3. Graph Theory: Finding the number of paths of length $k$ in a graph involves raising the Adjacency Matrix to the power of $k$.
4. Game Physics: Calculating decay, compound interest, or exponential growth in simulation engines (like determining how quickly a "threat level" rises in a strategy game).

5. Practice Links
* LeetCode 50: Pow(x, n): The classic implementation.
* LeetCode 372: Super Pow: Handling exponents so large they are passed as an array.
* LeetCode 1922: Count Good Numbers: Requires Modular Exponentiation.
 */