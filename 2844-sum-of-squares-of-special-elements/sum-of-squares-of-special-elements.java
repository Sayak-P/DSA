class Solution {
    public int sumOfSquares(int[] nums) {
        int n = nums.length;
        int sum = 0;
        
        // Iterate through 1-indexed positions
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                // Since Java arrays are 0-indexed, nums[i] corresponds to nums[i - 1]
                int val = nums[i - 1];
                sum += val * val;
            }
        }
        
        return sum;
    }
}