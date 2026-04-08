package dsa.Phase1.Searching;

public class L1_02_Lower_Bound_Upper_Bound {
    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 3, 3, 3, 4, 5, 5};
        int target = 3;

        //Lower Bound
        System.out.println("Lower Bound (at index): " + findFirst(nums, target));

        //Upper Bound
        System.out.println("Upper Bound (at index): " + findLast(nums, target));

        //Count the frequency of the number in a sorted duplicated elements array. O(log n)
        System.out.println("Count of " + target + " is: " + (findLast(nums, target) - findFirst(nums, target) + 1));

    }

    /**
     * Finds the first occurrence of target in O(log N)
     */
    public static int findFirst(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int first = -1; // Default if not found

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                first = mid;      // Record potential answer
                high = mid - 1;   // Keep looking LEFT for earlier match
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return first;
    }

    /**
     * Finds the last occurrence of target in O(log N)
     */
    public static int findLast(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int last = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                last = mid;       // Record potential answer
                low = mid + 1;    // Keep looking RIGHT for later match
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return last;
    }
}
/*
In a Deep Stress interview, this is the "Standard Binary Search Trap." While the standard algorithm returns any index where the target is found, Lower and Upper Bounds are about finding the boundaries of a range.
If you have [1, 2, 2, 2, 3], a standard search might return index 2. But the Lower Bound is index 1 (the first 2), and the Upper Bound is index 3 (the last 2).

1. The "Heat Stress" Logic: Don't Stop at the Match
The secret to finding the first or last occurrence is simple: When you find the target, don't return. Instead, "record" the current index as a potential candidate and keep searching in the direction of the boundary.
* To find the First Occurrence: If nums[mid] == target, save mid and move the high pointer to mid - 1 to see if there's an even earlier match.
* To find the Last Occurrence: If nums[mid] == target, save mid and move the low pointer to mid + 1 to see if there's a later match.

3. Deep Stress Breakdown
A. Total Count of an Element
If an interviewer asks: "How many times does '2' appear in this sorted array?" Don't use a loop! That's $O(N)$. Use the bounds:
Count = LastOccurrenceIndex - FirstOccurrenceIndex + 1. This is $O(\log N)$.
B. Why not just Linear Search from the middle?
If the array is [2, 2, 2, ..., 2] (a million 2s), and you find the middle one then loop left/right, your complexity becomes $O(N)$. Staying inside the Binary Search logic ensures you stay at $O(\log N)$.
C. The "Insertion Point"
In C++ std::lower_bound or Java's Arrays.binarySearch, the return value often tells you where the element would be inserted to keep the array sorted. This is the foundation of the Search Insert Position problem.

4. Real-World Use Cases
1. Log Analysis: In your Node.js backend, if you have millions of logs sorted by timestamp, and you want to find all logs between 10:00 AM and 10:05 AM, you use Lower Bound for 10:00 and Upper Bound for 10:05.
2. E-commerce Pricing: Finding all products in a price range ($50 to $100) in a database where products are indexed by price.
3. Stock Market Data: Finding the first time a stock price hit a certain threshold in a historical CSV file.
4. Dictionary Suggestions: Finding the range of all words that start with the prefix "comp" (like "computer", "compare").

5. Practice Links
* LeetCode 34: Find First and Last Position of Element in Sorted Array: The perfect practice for this exact code.
* LeetCode 278: First Bad Version: A great conceptual use of "First Occurrence" logic.
 */