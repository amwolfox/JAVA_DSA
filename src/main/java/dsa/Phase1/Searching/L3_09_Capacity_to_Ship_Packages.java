package dsa.Phase1.Searching;

public class L3_09_Capacity_to_Ship_Packages {
	
	public static void main(String[] args) {
		int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int days = 5;
		
		int result = shipWithinDays(weights, days);
		System.out.println("The minimum ship capacity required is: " + result);
	}
	
	public static int shipWithinDays(int[] weights, int days) {
		int left = 0;
		int right = 0;
		
		for (int w : weights) {
			left = Math.max(left, w); // Min capacity must be at least the heaviest package
			right += w;               // Max capacity is the sum of all packages
		}
		
		while (left < right) {
			int mid = left + (right - left) / 2;
			
			// If we can ship with 'mid' capacity, try a smaller capacity (left side)
			if (canShip(weights, days, mid)) {
				right = mid;
			} else {
				// If we can't, we need more capacity (right side)
				left = mid + 1;
			}
		}
		return left;
	}
	
	// Helper function to check if a given capacity can finish the job in 'days'
	private static boolean canShip(int[] weights, int daysLimit, int capacity) {
		int daysUsed = 1;
		int currentLoad = 0;
		
		for (int w : weights) {
			if (currentLoad + w > capacity) {
				daysUsed++;     // Start a new day
				currentLoad = w; // Carry the package on the new day
			} else {
				currentLoad += w;
			}
		}
		
		return daysUsed <= daysLimit;
	}
}


/*
 * This problem is a classic application of **Binary Search on Answer**. It’s not about searching through the array, but searching for the ideal "capacity" value within a specific range.
 * 
 - --                                                                           *
 
 ## 🧠 The Logic: Deep Stress Analysis
 
 The "stress" in this problem comes from finding the sweet spot. 
 * If your ship capacity is too **small**, you’ll take too many days (exceeding $D$).
 * If your ship capacity is too **large**, you’re wasting resources, though you'll finish well within $D$ days.
 
 ### The Search Range
 To find the minimum capacity, we define our boundaries:
 1.  **Low ($L$):** The weight of the heaviest single package. Why? Because the ship must be able to carry at least the largest item, or it can never ship it.
 2.  **High ($R$):** The sum of all package weights. Why? Because in the "worst" case, you ship everything in exactly 1 day.
 
 We then pick a middle weight ($mid$) and check: *"Can we ship everything in $D$ days with this capacity?"* ---
 
 ## 🛠 Real-World Use Cases
 
 This algorithm isn't just for LeetCode; it’s vital in **Logistics and Resource Management**:
 
 * **Cloud Computing:** Distributing a set of tasks (packages) with specific execution times (weights) across a fixed number of servers (days) to minimize the maximum load on any single server.
 * **Manufacturing:** Planning assembly line batches where you need to finish a set of parts within a specific number of shifts.
 * **Data Streaming:** Allocating data chunks into fixed-sized packets or buffers to be transmitted over a network within a certain time window.
 
 ---
 
 ## 🔗 Practice Link
 You can test your implementation and see different variations of this problem here:
 * **LeetCode 1011:** [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)
 
 ### Complexity Analysis
 * **Time Complexity:** $O(n \cdot \log(\text{sum} - \text{max}))$, where $n$ is the number of packages. We binary search over the weight range and iterate through the array for each search step.
 * **Space Complexity:** $O(1)$, as we only use a few variables for the search.
 */
