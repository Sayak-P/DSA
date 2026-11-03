import java.util.Arrays;

public class Solution {
    public int minMoves(int[] nums) {
        // Find the maximum element in the array
        int max = nums[0];
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }
        
        // Calculate the total moves required to make all elements equal to max
        int totalMoves = 0;
        for (int num : nums) {
            totalMoves += (max - num);
        }
        
        return totalMoves;
    }
}