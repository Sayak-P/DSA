class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;
        
        // Find the index of 0
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        int ans = Integer.MAX_VALUE;

        // Check clockwise direction (step +1)
        boolean ok1 = true;
        for (int i = 0; i < n; i++) {
            if (nums[(zero + i) % n] != i) {
                ok1 = false;
                break;
            }
        }
        if (ok1) {
            ans = Math.min(ans, zero);         // Rotate directly
            ans = Math.min(ans, n - zero + 2); // Reverse, rotate, then reverse back
        }

        // Check counter-clockwise direction (step -1)
        boolean ok2 = true;
        for (int i = 0; i < n; i++) {
            if (nums[(zero - i % n + n) % n] != i) {
                ok2 = false;
                break;
            }
        }
        if (ok2) {
            ans = Math.min(ans, zero + 2);     // Rotate, then reverse
            ans = Math.min(ans, n - zero);     // Reverse, then rotate
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}