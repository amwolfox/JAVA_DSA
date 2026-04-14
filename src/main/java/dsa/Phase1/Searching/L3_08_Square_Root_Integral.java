package dsa.Phase1.Searching;

public class L3_08_Square_Root_Integral {
    public static void main(String[] args) {
        L3_08_Square_Root_Integral sol = new L3_08_Square_Root_Integral();
        System.out.println(sol.mySqrt(8));  // Output: 2
        System.out.println(sol.mySqrt(16)); // Output: 4
    }

    /**
     * Calculates floor(sqrt(x)) in O(log x) time.
     * Complexity: O(log x) Time | O(1) Space
     */
    public int mySqrt(int x) {
        // 1. BASE CASES
        if (x == 0 || x == 1) return x;

        int low = 1, high = x, ans = 0;

        while (low <= high) {
            // Use (low + (high - low) / 2) to prevent (low + high) overflow
            int mid = low + (high - low) / 2;

            // 2. THE OVERFLOW TRAP
            // Comparing 'mid * mid <= x' can overflow an 'int'.
            // Deep Stress Trick: Use 'mid <= x / mid' instead.
            if (mid <= x / mid) {
                ans = mid;    // 'mid' is a potential floor
                low = mid + 1; // Try to find a larger value
            } else {
                high = mid - 1; // 'mid' is too large
            }
        }
        return ans;
    }
}
/*
Finding the floor of a square root—$\lfloor\sqrt{x}\rfloor$—without using the built-in sqrt() function is the "Gateway" problem into Numerical Binary Search. While a simple loop from 1 to $x$ is $O(x)$, using Binary Search reduces this to $O(\log x)$, which is fast enough to find the root of $2^{31}-1$ in just 31 steps.

1. The "Heat Stress" Logic: Searching the Number Line
The square root of $x$ is some number $k$ such that $k^2 \le x$. Since the numbers are sorted ($1, 2, 3, \dots, x$), we can binary search for the largest $k$.
The Strategy:
1. Range: The answer must be between $1$ and $x$ (actually $x/2$ for $x > 1$, but $x$ is safer).
2. Mid-Point: Check $mid = (low + high) / 2$.
3. The Comparison:
    * If $mid^2 == x$, you found the exact root.
    * If $mid^2 < x$, $mid$ could be the floor. Save it and search the right half to see if a larger $mid$ works.
    * If $mid^2 > x$, $mid$ is too big. Search the left half.

3. Deep Stress Breakdown
A. The Division Trick: mid <= x / mid
In an interview, if you write if (mid * mid <= x), you might fail. If $mid = 46341$, then $mid^2$ exceeds the maximum value of a 32-bit signed integer ($2,147,483,647$).
* Junior fix: Use (long) mid * mid.
* Senior/Deep Stress fix: Use mid <= x / mid. This is mathematically the same but physically impossible to overflow.
B. The "Search for Floor"
Unlike a standard Binary Search where you return as soon as you find the target, here we continue searching even after finding a $mid^2 < x$. We store the "best so far" in ans and keep pushing rightward to see if there is a closer value.
C. Time Complexity: $O(\log x)$
For $x = 2,147,483,647$, the loop runs at most 31 times. This is incredibly efficient compared to a linear scan.

4. Real-World Use Cases
1. Graphics & Game Engines: Calculating distances between points (Pythagorean theorem) often requires square roots. While GPUs have hardware for this, specialized kernels (like the famous "Fast Inverse Square Root" from Quake III) use bit-level tricks to find approximations.
2. Constraint Programming: When determining the bounds for a loop (like our $O(\sqrt{n})$ primality test!), you need to know the integral square root to set the upper limit.
3. Cryptography: Many algorithms (like the Baby-step Giant-step for discrete logs) require splitting a range into blocks of size $\sqrt{x}$.
4. Financial Modeling: Calculating volatility or standard deviation in risk management requires square roots. In high-frequency trading (HFT), you might implement custom math functions to avoid the overhead of standard libraries.

5. Practice Links
* LeetCode 69: Sqrt(x): (The exact problem).
* LeetCode 367: Valid Perfect Square: Uses the same logic but checks if $ans^2$ exactly equals $x$.
* LeetCode 1011: Capacity To Ship Packages Within D Days: An advanced version where you binary search for a "capacity" instead of a square root.
 */