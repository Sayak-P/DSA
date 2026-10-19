import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        // Step 1: Sort the array in non-decreasing order
        Arrays.sort(nums);
        
        // Step 2: Collect all indices where nums[i] == target
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                result.add(i);
            }
        }
        
        // Step 3: Return the sorted list of indices
        return result;
    }
}
