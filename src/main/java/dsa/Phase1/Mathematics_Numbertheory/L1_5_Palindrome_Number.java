package dsa.Phase1.Mathematics_Numbertheory;

public class L1_5_Palindrome_Number {
    public static void main(String[] args) {
        System.out.println(isPalindrome(121));  // true
        System.out.println(isPalindrome(-121)); // false
        System.out.println(isPalindrome(10));   // false
    }

    /**
     * Checks if a number is a palindrome without String conversion.
     * Complexity: O(log10(N)) Time | O(1) Space
     */
    public static boolean isPalindrome(int x) {
        // 1. NEGATIVE NUMBER RULE
        // Any negative number is NOT a palindrome (e.g., -121 reversed is 121-)
        // Also, any number ending in 0 (except 0 itself) cannot be a palindrome.
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int original = x;
        long reversed = 0; // Use 'long' to prevent overflow during reversal

        // 2. MATHEMATICAL REVERSAL
        while (x > 0) {
            int lastDigit = x % 10;
            reversed = (reversed * 10) + lastDigit;
            x = x / 10;
        }

        // 3. FINAL COMPARISON
        // If the reversed long exceeds Integer.MAX_VALUE, it won't equal 'original' anyway.
        return original == (int) reversed;
    }
}

/*
A Palindrome Number is a number that reads the same backward as forward (e.g., 121, 1331). In a Deep Stressinterview, the "trap" is using Integer.toString() and reversing the string. While that works, it uses $O(N)$ extra spaceand is considered a "junior" solution.
The professional way is to use Modulo (%) and Division (/) to reverse the digits mathematically.

1. The "Heat Stress" Logic: Mathematical Reversal
To reverse a number without a string, you "pop" the last digit using % 10 and "push" it into a new variable.
The Algorithm:
1. Extract the last digit: digit = n % 10
2. Build the reversed number: reversed = (reversed * 10) + digit
3. Remove the last digit from original: n = n / 10
Example: $n = 121$
* Step 1: digit = 1, rev = (0*10)+1 = 1, n = 12
* Step 2: digit = 2, rev = (1*10)+2 = 12, n = 1
* Step 3: digit = 1, rev = (12*10)+1 = 121, n = 0
* Compare: Is original == reversed? Yes $\rightarrow$ Palindrome.

3. Deep Stress Breakdown
A. Why the x % 10 == 0 check?
If a number ends in zero (like 120), its reverse would start with zero (021), which isn't possible for an integer. The only exception is the number 0 itself. Adding this check saves CPU cycles.
B. The Overflow Trap
In Java, an int goes up to 2,147,483,647. If you try to reverse 1,534,236,469, the result exceeds the limit.
* The "Pro" Fix: Use a long for the reversed variable to handle the intermediate calculation safely.
C. Time Complexity: $O(\log_{10}(N))$
The number of iterations is equal to the number of digits in $N$. Since $N$ has approximately $\log_{10}(N)$ digits, the complexity is logarithmic. For a 10-digit number, the loop only runs 10 times.

4. Real-World Use Cases
Why do we care about palindromes in 2026?
1. Data Integrity (Checksums): In some low-level protocols, numbers are designed to be "symmetric" so that if bits are flipped during transmission, the error is easily detected.
2. Cryptographic Hashing: Certain hashing algorithms use palindromic properties to ensure that data transformed forward and backward maintains specific mathematical traits.
3. DNA Sequencing: In Bioinformatics, "Palindromic Sequences" in DNA (where the sequence matches its reverse complement) are sites where restriction enzymes cut the DNA. This is vital for genetic engineering.
4. Game State Storage: In strategy games (like Chess analysis), symmetric board states can be hashed into palindromic-style integers to save space in "Transposition Tables."

5. Practice Links
* LeetCode 9: Palindrome Number: This is the exact problem. Try to solve it using the "reverse only half the number" optimization for extra stress!
* LeetCode 234: Palindrome Linked List: A much harder version! It combines Linked Lists with the palindrome concept.
* LeetCode 5: Longest Palindromic Substring: (String-based, but uses the same logic of expanding from the center).
 */