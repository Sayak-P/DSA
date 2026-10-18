import java.util.ArrayList;
import java.util.List;

class Solution {
    private static final int MOD = 1000000007;
    private long[][] combinations;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        combinations = new long[n + 1][n + 1];

        // Precompute binomial coefficients using Pascal's identity
        for (int i = 0; i <= n; i++) {
            combinations[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                combinations[i][j] = (combinations[i - 1][j - 1] + combinations[i - 1][j]) % MOD;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        // Subtract 1 because the problem asks for ways to *reorder* nums 
        // (excluding the original array itself).
        return (int) (countWays(list) - 1 + MOD) % MOD;
    }

    private long countWays(List<Integer> nums) {
        if (nums.size() <= 2) {
            return 1;
        }

        int root = nums.get(0);
        List<Integer> leftSubtree = new ArrayList<>();
        List<Integer> rightSubtree = new ArrayList<>();

        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < root) {
                leftSubtree.add(nums.get(i));
            } else {
                rightSubtree.add(nums.get(i));
            }
        }

        long leftWays = countWays(leftSubtree) % MOD;
        long rightWays = countWays(rightSubtree) % MOD;

        int leftSize = leftSubtree.size();
        int rightSize = rightSubtree.size();

        // Ways to interleave left and right elements * ways for left * ways for right
        long ways = combinations[leftSize + rightSize][leftSize];
        ways = (ways * leftWays) % MOD;
        ways = (ways * rightWays) % MOD;

        return ways;
    }
}