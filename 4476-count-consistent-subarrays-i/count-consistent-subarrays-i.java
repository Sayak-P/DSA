
class Solution {
    public int countConsistent(int[] nums) {

        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {

            int and = nums[i];
            int min = nums[i];

            for (int j = i; j < n; j++) {

                and &= nums[j];
                min = Math.min(min, nums[j]);

                if (and == min) {
                    count++;
                }
            }
        }

        return count;
    }
}
