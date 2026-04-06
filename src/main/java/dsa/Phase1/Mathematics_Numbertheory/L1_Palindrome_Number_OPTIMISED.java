package dsa.Phase1.Mathematics_Numbertheory;

public class L1_Palindrome_Number_OPTIMISED {
    public static void main(String[] args) {
        int n = 1221;
        System.out.println("Is " + n + " a Palindrome: " + isPalindrome(n));
    }

    public static boolean isPalindrome(int x) {
        // 1. QUICK ELIMINATION (Edge Cases)
        // Negative numbers are not palindromes.
        // If the last digit is 0, the first digit must be 0 (only 0 itself fits this).
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversedHalf = 0;

        // 2. REVERSE HALF PROCESS
        // We stop when the reversed part meets or passes the remaining part.
        // Example x = 1221:
        // Iteration 1: reversedHalf = 1, x = 122
        // Iteration 2: reversedHalf = 12, x = 12 -> STOP (reversedHalf == x)
        while (x > reversedHalf) {
            reversedHalf = (reversedHalf * 10) + (x % 10);
            x /= 10;
        }

        // 3. FINAL COMPARISON
        // Case A (Even length): x == reversedHalf (e.g., 12 == 12)
        // Case B (Odd length): x == reversedHalf / 10 (e.g., x is 1, reversedHalf is 12, we drop the '2')
        return x == reversedHalf || x == reversedHalf / 10;
    }
}
/*
The "Reverse Only Half" approach is the ultimate Deep Stress optimization for this problem.
In the previous method, we reversed the entire number. But mathematically, if a number is a palindrome, the first half must be the same as the reverse of the second half (e.g., in 1221, the first half 12 is the same as the reverse of the second half 21 $\rightarrow$ 12).
1. The "Heat Stress" Logic: Why half is better?
1. Prevents Overflow: By only reversing half, the number never grows large enough to exceed the Integer.MAX_VALUE. You don't even need a long!
2. Performance: You cut the number of iterations in half ($O(\log_{10} N / 2)$).
3. The "Stop" Condition: How do we know we've reached the middle? When the reversedNumber becomes greater than or equal to the remaining originalNumber.

3. Deep Stress Breakdown
A. Handling Odd Length Numbers
If $x = 12321$, at the end of the loop:
* x will be 12
* reversedHalf will be 123. The middle digit (3) doesn't matter for the palindrome property. By doing reversedHalf / 10, we get rid of that middle digit and compare 12 == 12.
B. The Termination Logic
The condition while (x > reversedHalf) is brilliant because it works regardless of the number's length. As digits move from x to reversedHalf, they eventually "cross" in the middle.
C. Memory Efficiency
This is $O(1)$ space. Unlike the string method, we aren't creating a new StringBuilder or an array of characters. This is "bare metal" integer manipulation, exactly what you'd want in a high-performance Data Engineering pipeline.

4. Summary Table
Feature	Full Reversal	Half Reversal (Optimized)
Overflow Risk	High (Requires long)	Zero (Safe with int)
Iterations	All digits	$\approx 1/2$ the digits
Middle Digit	Handled naturally	Handled via / 10
Logic Type	Simple	Strategic (Interview Gold)

 */