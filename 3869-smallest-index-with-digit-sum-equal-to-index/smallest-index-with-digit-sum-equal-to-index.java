class Solution {
    public int smallestIndex(int[] nums) {
       for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            String s = String.valueOf(nums[i]);
            
            for (char c : s.toCharArray()) {
                sum += c - '0';
            }
            
            if (sum == i) {
                return i;
            }
        }
        return -1; 
    }
}