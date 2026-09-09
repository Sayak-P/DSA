import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        
      // Map to store the number and its index
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // If the map contains the complement, we found our pair
            if (numMap.containsKey(complement)) {
                return new int[] { numMap.get(complement), i };
            }
            
            // Otherwise, store the current number and its index
            numMap.put(nums[i], i);
        }
        
        // Return an empty array if no solution is found 
        // (though the problem guarantees one valid answer)
        return new int[] {};  
    }
}