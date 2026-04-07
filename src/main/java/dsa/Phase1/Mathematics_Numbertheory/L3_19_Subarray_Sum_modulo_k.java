package dsa.Phase1.Mathematics_Numbertheory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class L3_19_Subarray_Sum_modulo_k {
    public static void main(String[] args) {
        L3_19_Subarray_Sum_modulo_k sol = new L3_19_Subarray_Sum_modulo_k();
        int[] nums = {3, 1, 1, 2, 5}; // N=5
        int k = 5;
        System.out.println(Arrays.toString(sol.findSubarray(nums, k)));
    }

    /**
     * Finds a subarray whose sum is divisible by N in O(N) time.
     * Complexity: O(N) Time | O(N) Space
     */
    public int[] findSubarray(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();

        // Base case
        map.put(0, -1);

        long currentSum = 0;
        for (int i = 0; i < n; i++) {
            currentSum += nums[i];

            // Use k instead of n
            int rem = (int) ((currentSum % k + k) % k);

            if (map.containsKey(rem)) {
                int startIndex = map.get(rem) + 1;
                return Arrays.copyOfRange(nums, startIndex, i + 1);
            }
            map.put(rem, i);
        }
        return new int[0]; // no such subarray
    }
}