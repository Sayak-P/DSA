
class Solution {
    public long maxDifference(int[] nums) {
        int n = nums.length;

        long[][] dp = new long[n + 2][2];

        // 0 = Alice's turn, 1 = Bob's turn

        for (int i = n - 1; i >= 0; i--) {
            for (int turn = 0; turn < 2; turn++) {

                long best = (turn == 0)
                        ? Long.MIN_VALUE
                        : Long.MAX_VALUE;

                // Take one element
                long sum1 = nums[i];

                int next1 = (sum1 % 3 == 0)
                        ? 1 - turn : turn;

                long val1 = (turn == 0 ? sum1 : -sum1)
                        + dp[i + 1][next1];

                if (turn == 0)
                    best = Math.max(best, val1);
                else
                    best = Math.min(best, val1);

                // Take two elements
                if (i + 1 < n) {
                    long sum2 = (long) nums[i] + nums[i + 1];

                    int next2 = (sum2 % 3 == 0)
                            ? 1 - turn : turn;

                    long val2 = (turn == 0 ? sum2 : -sum2)
                            + dp[i + 2][next2];

                    if (turn == 0)
                        best = Math.max(best, val2);
                    else
                        best = Math.min(best, val2);
                }

                dp[i][turn] = best;
            }
        }

        return dp[0][0];
    }
}
