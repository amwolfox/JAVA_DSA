package dsa.Phase1.Searching;

public class L2_07_Find_Peak_Element {
    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 3, 5, 6, 4};
        int peakIndex = findPeakElement(nums);
        System.out.println("Peak element found at index: " + peakIndex);
        System.out.println("Peak value is: " + nums[peakIndex]);
    }

    public static int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // Check if we are on a downward slope
            if (nums[mid] > nums[mid + 1]) {
                // You are in a descending part.
                // There might be a peak to the left (including mid).
                right = mid;
            } else {
                // You are in an ascending part (nums[mid] < nums[mid + 1]).
                // The peak must be to the right.
                left = mid + 1;
            }
        }

        // Left and Right will converge to the peak index
        return left;
    }
}
/*
Finding a "Peak Element" in an unsorted array feels like a trick question. Usually, $O(\log n)$ is reserved for sorted data, but here we are dealing with an unsorted mess.

The "Stress" (The Paradox)
Usually, to find a peak in an unsorted list, you’d think you have to look at every single number ($O(n)$). If the array is [1, 2, 1, 3, 5, 6, 4], peaks are 2 and 6.
The Stress: How do you discard half of the array when there is no global order?
The Insight: You don't need the array to be sorted; you just need to follow the slope.
* If you are standing on a mountain and the path to your right goes up, there must be a peak somewhere to your right (even if it's the very last element).
* If the path to your right goes down, then the peak is either where you are standing or somewhere to your left.
By checking only the middle element and its immediate neighbor, we can decide which "slope" to follow, effectively using Binary Search on an unsorted space.

Real-World Use Cases
Where do we look for "local maximums" in unsorted data?
1. Topographic Mapping: Drone sensors or satellites scanning terrain to find mountain peaks or high-altitude points without scanning every square inch of a mountain range.
2. Signal Processing: Finding "spikes" in a noisy radio frequency or heart rate monitor (ECG). You don't need the absolute highest spike, just a valid local peak to trigger an alert.
3. Stock Market Analysis: Identifying local "highs" in volatile, unsorted daily trading data to identify potential resistance levels.
4. Game AI: In "Pathfinding," an AI might look for a local high point on a map to gain a line-of-sight advantage over an opponent.

Complexity & Practice
Metric	Complexity
Time Complexity	$O(\log n)$
Space Complexity	$O(1)$
Practice on LeetCode
* Find Peak Element (Medium): LeetCode #162
* Peak Index in a Mountain Array (Easy): LeetCode #852 (A simpler version where the array is guaranteed to go up then down).
Wait, here's a brain teaser: What happens if the array is strictly increasing, like [1, 2, 3, 4, 5]?
In this logic, 5 is considered the peak because the problem treats nums[-1] and nums[n] as $-\infty$. Does that make sense?
 */