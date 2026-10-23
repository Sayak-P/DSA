class Solution {
    public int sortPermutation(int[] nums) {
        int ans = -1; // Initialize with all bits set to 1
        
        for (int i = 0; i < nums.length; ++i) {
            if (i != nums[i]) {
                ans &= nums[i]; // Accumulate bitwise AND for all misplaced elements
            }
        }
        
        // If ans remains -1 (array is already sorted), return 0
        return Math.max(ans, 0);
    }
}