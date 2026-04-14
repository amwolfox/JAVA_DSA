package dsa.Phase1.Searching;

public class L2_06_Search_in_an_Infinite_Array {
    public static void main(String[] args) {
        // Simulated "Infinite" Array (just a large sorted array)
        int[] infiniteArr = {3, 5, 7, 9, 10, 90, 100, 130, 140, 160, 170, 500, 600, 1000, 2000};
        int target = 10;

        int result = findRange(infiniteArr, target);

        if (result == -1) {
            System.out.println("Element not found.");
        } else {
            System.out.println("Element found at index: " + result);
        }
    }

    // Phase 1: Find the bounds exponentially
    static int findRange(int[] arr, int target) {
        int low = 0;
        int high = 1;

        // Keep doubling the high index until target is within [low, high]
        // Note: In a real 'infinite' array, we wouldn't check arr.length
        while (target > arr[high]) {
            int newLow = high + 1;
            // Double the range: newHigh = previousHigh + (size of box * 2)
            high = high + (high - low + 1) * 2;
            low = newLow;
        }

        return binarySearch(arr, target, low, high);
    }

    // Phase 2: Standard Binary Search
    static int binarySearch(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (target < arr[mid]) {
                high = mid - 1;
            } else if (target > arr[mid]) {
                low = mid + 1;
            } else {
                return mid; // Target found!
            }
        }
        return -1;
    }
}
/*
Searching in an infinite array (or an array of unknown size) is a classic "think outside the box" problem. Usually, Binary Search requires a low and a high, but here, the high is a mystery.

The "Stress" (The Problem)
Imagine you are looking for a house on a street that never ends. You know the houses are numbered in order ($1, 2, 3...$), but you have no idea where the street stops. If you try to access a house number that doesn't exist (e.g., arr[1000000]), the system might crash with an ArrayIndexOutOfBoundsException.
The Challenge:
Standard Binary Search is $O(\log n)$, but it requires a boundary. To find the boundary in an "infinite" space without checking every single index (which would be $O(n)$), we need a way to expand our search area exponentially.

The Solution: Exponential Search
Instead of moving one by one, we double our range every time. This is the "Inverse Binary Search" logic.
1. Start with a range of size 1 (low = 0, high = 1).
2. If the target is greater than arr[high], double the range: low = high and high = high * 2.
3. Repeat until target <= arr[high].
4. Once you have a bound, perform a standard Binary Search within that range.

Real-World Use Cases
1. Streaming Data: When processing a continuous stream of sorted data (like logs or stock prices arriving in real-time) where you don't know when the stream will end.
2. Large Files/Databases: Searching for a record in a massive sorted file where calculating the total number of lines (the size) is more expensive than the search itself.
3. Search Engines: Looking for a specific "Rank" or "Score" in a list of results where the total number of pages is dynamically generated or effectively "endless" to the user.

Complexity & Practice
* Time Complexity: $O(\log p)$, where $p$ is the position of the target. Finding the range takes $O(\log p)$ and searching within it takes $O(\log p)$.
* Space Complexity: $O(1)$.
Practice on LeetCode
Since LeetCode cannot actually provide an "infinite" array, they use a special interface ArrayReader.
* Search in a Sorted Array of Unknown Size (Premium): LeetCode #702
* Alternative: If you don't have Premium, try implementing the logic above on a very large array and simulate the "out of bounds" by catching exceptions or using a fixed limit.
 */