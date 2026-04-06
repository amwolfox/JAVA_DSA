package dsa.Phase1.Mathematics_Numbertheory;

import java.util.*;

public class L2_Factorial_of_a_Large_Number {
    public static void main(String[] args) {
        L2_Factorial_of_a_Large_Number sol = new L2_Factorial_of_a_Large_Number();
        System.out.println("100! = " + sol.factorial(100));
    }

    /**
     * Calculates N! for large N and returns it as a String.
     * Complexity: O(N^2) Time | O(Digits) Space
     */
    public String factorial(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1); // Start with 1! = 1

        // 1. OUTER LOOP: Multiply by each number from 2 to N
        for (int x = 2; x <= n; x++) {
            multiply(res, x);
        }

        // 2. FORMAT OUTPUT: The digits are stored in reverse (units first)
        StringBuilder sb = new StringBuilder();
        for (int i = res.size() - 1; i >= 0; i--) {
            sb.append(res.get(i));
        }
        return sb.toString();
    }

    private void multiply(ArrayList<Integer> res, int x) {
        int carry = 0;

        // 3. INNER LOOP: Multiply each digit by x
        for (int i = 0; i < res.size(); i++) {
            int prod = res.get(i) * x + carry;
            res.set(i, prod % 10); // Store the unit digit
            carry = prod / 10;     // Pass the rest as carry
        }

        // 4. THE GROWTH: If there's a carry left, add new digits
        while (carry != 0) {
            res.add(carry % 10);
            carry = carry / 10;
        }
    }
}
/*
Calculating the factorial of a large number like $100!$ is a "Deep Stress" challenge because the result is astronomically large. For perspective, $20!$ is the largest factorial that fits in a 64-bit long. $100!$ has 158 digits.
Since no primitive data type can hold this, we must simulate Manual Multiplication (the way you did it in school) using an Array or a List.

1. The "Heat Stress" Logic: Manual Carry Multiplication
We treat an array as a "storage container" where each index holds a single digit of the massive number.
The Strategy:
1. Initialize: Start with an array containing [1] (representing the number 1).
2. Multiply: For every number from $2$ to $N$, multiply each digit in our array by that number.
3. Handle the Carry: If a digit becomes greater than 9, calculate the carry and pass it to the next index (the next power of 10).
4. Grow: As the number gets bigger, the array size increases.

3. Deep Stress Breakdown
A. Why store in Reverse?
We store the digits in reverse order (index 0 is the ones place, index 1 is the tens place) because it makes it easier to grow the array. When you have a carry, you just add() to the end of the list. If we stored it normally, we’d have to shift every element to the right, making it $O(N^3)$.
B. Time Complexity: $O(N^2)$
The number of digits in $N!$ is approximately $O(N \log N)$. Since we multiply each digit by every number from $1$to $N$, the overall complexity is roughly $O(N^2)$. For $N=1000$, this is about 1 million operations—very fast for a modern CPU.
C. The Java "Shortcut": BigInteger
In a real-world project, you would use java.math.BigInteger. It handles all this array logic under the hood using even more optimized algorithms like Karatsuba Multiplication.
BigInteger fact = BigInteger.ONE; for(int i=2; i<=n; i++) fact = fact.multiply(BigInteger.valueOf(i));

4. Real-World Use Cases
1. Cryptography: Public key systems (like RSA) use numbers with hundreds of digits. They perform multiplications and exponentiations on these "BigInts" using the same carry logic.
2. Scientific Computing: Calculating the number of possible states in a biological system or a physics simulation often results in values that exceed $2^{64}$.
3. Combinatorics in Gaming: In strategy games (like Chess or Go), calculating the total possible move combinations ($N!$ paths) requires high-precision math.
4. Financial Auditing: In high-value banking, calculating compound interest over very long periods or across massive datasets might require precision beyond standard double types to avoid "rounding errors."

5. Practice Links
* GeeksForGeeks: Factorial of a Large Number: (Standard practice for this logic).
* LeetCode 43: Multiply Strings: A very similar problem where you multiply two large numbers represented as strings.
* LeetCode 2: Add Two Numbers: Uses the same "carry" logic with Linked Lists.
 */