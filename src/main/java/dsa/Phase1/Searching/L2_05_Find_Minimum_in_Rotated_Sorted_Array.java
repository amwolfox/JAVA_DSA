package dsa.Phase1.Searching;

public class L2_05_Find_Minimum_in_Rotated_Sorted_Array {
    public static void main(String[] args) {
        // Example rotated sorted array
        int[] nums = {4, 5, 6, 7, 0, 1, 2};

        int result = findMin(nums);
        System.out.println("The minimum element (pivot point) is: " + result);
    }

    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        // Base case: If the array is not rotated at all
        if (nums[left] <= nums[right]) {
            return nums[left];
        }

        while (left < right) {
            int mid = left + (right - left) / 2;

            // STRATEGY: Compare mid with the rightmost element
            // If mid is greater than right, the minimum must be on the right side
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            // If mid is less than or equal to right, the minimum is on the left side
            // We include mid because mid itself could be the minimum
            else {
                right = mid;
            }
        }

        // When left == right, we've converged on the minimum element
        return nums[left];
    }
}
/*
Finding the minimum element in a rotated sorted array is a classic challenge that tests your grasp of Binary Search.
While a simple linear scan works, it’s not efficient enough for large-scale systems.

The "Stress" (The Problem)
Imagine a sorted array: [1, 2, 3, 4, 5, 6, 7].
Now, rotate it at some pivot: [4, 5, 6, 7, 1, 2, 3].
The stress here is that the array is no longer fully sorted, which usually breaks a standard Binary Search. However, it remains inflection-point sorted. There is a specific point where the numbers suddenly drop from a high value to a low value (the minimum).
The Logic
1. In a normal sorted array, arr[start] < arr[end].
2. In a rotated array, if arr[mid] > arr[end], it means the "drop-off" (the pivot) must be to the right of mid.
3. If arr[mid] <= arr[end], the minimum is either at mid or to the left.

Real-World Use Cases
Why would you ever have a "rotated" sorted array? It's more common than you think:
* Circular Buffers/Queues: In low-level systems (like networking or OS kernels), data is often stored in a circular buffer. As new data comes in and old data is deleted, the "start" of the chronological data shifts, effectively creating a rotated sorted list.
* Log Rotation: System logs are often sorted by timestamp but are "rotated" into new files. Finding the earliest log entry in a current active file often requires this exact logic.
* Time-Series Databases: If a database partitions data by hour and "wraps around" a 24-hour cycle, the data might be sorted by time but "rotated" depending on when the query starts.

Complexity & Practice
Metric	Complexity
Time Complexity	$O(\log n)$
Space Complexity	$O(1)$
Practice Links
* Find Minimum in Rotated Sorted Array: LeetCode #153
* Search in Rotated Sorted Array: LeetCode #33
* Find Minimum (with Duplicates): LeetCode #154
 */