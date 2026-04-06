package dsa.Phase1.Mathematics_Numbertheory;

public class L2_11_Excel_Column_Title {
    public static void main(String[] args) {
        L2_11_Excel_Column_Title sol = new L2_11_Excel_Column_Title();
        System.out.println(sol.convertToTitle(1));   // "A"
        System.out.println(sol.convertToTitle(28));  // "AB"
        System.out.println(sol.convertToTitle(701)); // "ZY"
    }

    /**
     * Converts a column number to its Excel title equivalent.
     * Complexity: O(log26 N) Time | O(log26 N) Space (for the result string)
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();

        while (columnNumber > 0) {
            // 1. THE CRITICAL ADJUSTMENT
            // Subtract 1 to shift from 1-indexed (1-26) to 0-indexed (0-25).
            // This handles the 'Z' case where 26 % 26 would have been 0.
            columnNumber--;

            // 2. EXTRACT THE "DIGIT"
            // Get the remainder after dividing by 26.
            int remainder = columnNumber % 26;

            // 3. MAP TO CHAR
            // 'A' is 65 in ASCII. So 0 becomes 'A', 25 becomes 'Z'.
            char c = (char) ('A' + remainder);
            sb.append(c);

            // 4. REDUCE THE NUMBER
            columnNumber /= 26;
        }

        // 5. REVERSE THE STRING
        // Since we extracted digits from right to left (units, then tens...),
        // we must reverse to get the correct order (e.g., 'AZ' instead of 'ZA').
        return sb.reverse().toString();
    }
}
/*
Converting a number to an Excel column title (e.g., $1 \rightarrow A$, $27 \rightarrow AA$) is a classic Base-Conversion problem. In a Deep Stress interview, the "trap" is that Excel isn't a standard Base-26 system (0-25). It is a 1-indexed system (1-26).
This shift from 0-based to 1-based logic causes an "Off-by-One" error if you aren't careful, especially when dealing with the letter 'Z'.

1. The "Heat Stress" Logic: 1-Based Base-26
In a normal Base-10 system, we use digits 0–9. In Base-26, we use A–Z.
* Standard Base-26: $0=A, 1=B \dots 25=Z$.
* Excel Base-26: $1=A, 2=B \dots 26=Z$.
The Problem: When you have $26$, a normal modulo (26 % 26) gives 0. But in Excel, 26 should be Z, not 0 followed by a carry.
The Fix: Before every step, we subtract 1 from the number. This aligns the 1–26 range to a 0–25 range, making the modulo operator work perfectly with the ASCII values of 'A' through 'Z'.

3. Deep Stress Breakdown
A. Why columnNumber--?
Let's trace $N=26$:
* Without --: $26 \% 26 = 0$. (char)('A' + 0) is 'A'. Then $26 / 26 = 1$. Next loop gives another 'A'. Result: AA. Wrong.
* With --: $26 - 1 = 25$. $25 \% 26 = 25$. (char)('A' + 25) is 'Z'. Then $25 / 26 = 0$. Loop ends. Result: Z. Correct.
B. Time Complexity: $O(\log_{26} N)$
Just like binary conversion is $O(\log_2 N)$, this is $O(\log_{26} N)$. For the maximum integer value ($2 \times 10^9$), the loop only runs about 7 times.
C. StringBuilder vs. String
In Java (and Node.js), strings are immutable. Using sb.append() is more memory-efficient than str = str + char, which creates a new string object in every iteration.

4. Real-World Use Cases
1. Spreadsheet Software Engine: Obviously, if you were building a clone of Google Sheets or Excel, this is the core logic for the UI headers.
2. Unique ID Generation (Shortlinks): Services like Bitly or YouTube video IDs use Base-62 (A-Z, a-z, 0-9). The logic is identical, just with a larger divisor (62 instead of 26) and a slightly more complex character mapping.
3. Coordinate Systems: In some CAD or mapping software, large grids use alphabetical labeling for one axis and numeric for the other.
4. License Plate / Serial Number Logic: Systems that increment strings (like AAA001 to AAA002... to AAB001) use this base-overflow logic.

5. Practice Links
* LeetCode 168: Excel Sheet Column Title: (The problem we just solved).
* LeetCode 171: Excel Sheet Column Number: (The reverse! Convert "AB" to 28. Very good for understanding polynomial expansion).
* LeetCode 13: Roman to Integer: Another variation of non-standard base conversion.
 */