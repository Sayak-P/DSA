import java.util.HashMap;

public class Solution {
    public int minOperations(int[] nums, int x) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        int target = totalSum - x;
        
        // If target is less than 0, it's impossible to reduce x to 0
        if (target < 0) {
            return -1;
        }
        
        // If target is 0, we need to remove all elements
        if (target == 0) {
            return nums.length;
        }
        
        // Use a sliding window / two pointers approach since all elements are positive
        int left = 0, currentSum = 0, maxLength = -1;
        
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];
            
            while (currentSum > target && left <= right) {
                currentSum -= nums[left];
                left++;
            }
            
            if (currentSum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }
        
        return maxLength == -1 ? -1 : nums.length - maxLength;
    }
}