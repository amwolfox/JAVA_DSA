package dsa.Phase1.Searching;

public class L2_04_Search_in_Rotated_Sorted_Array {
    public static void main(String[] args) {
        L2_04_Search_in_Rotated_Sorted_Array rs = new L2_04_Search_in_Rotated_Sorted_Array();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Index of 0: " + rs.search(nums, 0)); // 4
    }

    /**
     * Searches for target in a rotated sorted array in O(log N).
     * Complexity: O(log N) Time | O(1) Space
     */
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) return mid;

            // 1. IDENTIFY THE SORTED HALF
            // Case A: Left side is sorted
            if (nums[low] <= nums[mid]) {
                // Check if target is within the sorted left range
                if (target >= nums[low] && target < nums[mid]) {
                    high = mid - 1; // Target is in the left
                } else {
                    low = mid + 1;  // Target is in the right
                }
            }
            // Case B: Right side must be sorted
            else {
                // Check if target is within the sorted right range
                if (target > nums[mid] && target <= nums[high]) {
                    low = mid + 1;  // Target is in the right
                } else {
                    high = mid - 1; // Target is in the left
                }
            }
        }
        return -1;
    }
}
/*
Searching in a Rotated Sorted Array is a classic "Deep Stress" interview favorite because it tests if you truly understand the property of Binary Search: You don't need the whole array to be sorted; you only need to identify which half is sorted.
When an array like [0,1,2,4,5,6,7] is rotated, it becomes something like [4,5,6,7,0,1,2]. It’s now composed of two sorted "segments."

1. The "Heat Stress" Logic: The Sorted Half Rule
In a normal binary search, you compare target with mid. Here, that's not enough. You must first figure out which side of mid is sorted.
* Look at the left side (low to mid): If nums[low] <= nums[mid], the left side is perfectly sorted.
* Look at the right side (mid to high): If the left isn't sorted, the right side must be sorted.
Once you know which side is sorted, you check if your target falls within that sorted range. If it does, you go there. If it doesn't, you go to the "messy" side.


3. Deep Stress Breakdown
A. The nums[low] <= nums[mid] condition
Why <=? Because if the array has only two elements (e.g., [3, 1]), low and mid will be the same index. We need to treat that as a "sorted" single-element range.
B. Why is this still $O(\log N)$?
Even though we added extra if statements, we are still discarding exactly half of the remaining array in every single step. We never look at both sides.
C. The "Duplicate" Trap (LeetCode 81)
If the array has duplicates (e.g., [1, 0, 1, 1, 1]), this logic fails because nums[low], nums[mid], and nums[high] might all be the same. In that case, you have to shrink the search space by doing low++ and high--, which makes the worst-case $O(N)$.

4. Real-World Use Cases
1. Circular Buffers: In Node.js stream processing or low-level systems, data is often stored in a circular queue. When you read from it, the data appears "rotated." Finding a specific timestamp or ID requires this logic.
2. Clock-Time Systems: Searching for an event in a list that wraps around (e.g., a schedule that starts at 10 PM and ends at 4 AM).
3. Load Balancing: In distributed systems, consistent hashing often places servers in a logical "ring." Finding the correct server for a key involves searching in a structure that is effectively a rotated sorted list of hash values.
4. Game Logs: If a game only stores the last 1000 events and overwrites the oldest ones, the "start" of the log moves, creating a rotated sorted list of timestamps.

5. Practice Links
* LeetCode 33: Search in Rotated Sorted Array: The classic problem.
* LeetCode 81: Search in Rotated Sorted Array II: The version with duplicates (Deep Stress alert!).
* LeetCode 153: Find Minimum in Rotated Sorted Array: Finding the "pivot" point.
 */