public class Solution {
    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // dp[i][j] = number of unique paths to reach empty cell (i, j)
        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) continue;
                if (i == m - 1 && j == n - 1) continue;

                // 1. Try moving right from (i, j)
                if (j + 1 < n) {
                    int[] landing = getLandingCell(grid, i, j + 1, 0); // 0 = moving right
                    if (landing != null) {
                        dp[landing[0]][landing[1]] = (dp[landing[0]][landing[1]] + dp[i][j]) % MOD;
                    }
                }

                // 2. Try moving down from (i, j)
                if (i + 1 < m) {
                    int[] landing = getLandingCell(grid, i + 1, j, 1); // 1 = moving down
                    if (landing != null) {
                        dp[landing[0]][landing[1]] = (dp[landing[0]][landing[1]] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }

    // Simulates the reflection chain when attempting to enter cell (r, c) with direction `dir`
    // dir: 0 = moving right, 1 = moving down
    private int[] getLandingCell(int[][] grid, int r, int c, int dir) {
        int m = grid.length;
        int n = grid[0].length;

        while (r >= 0 && r < m && c >= 0 && c < n) {
            if (grid[r][c] == 0) {
                // Successfully landed on an empty cell
                return new int[]{r, c};
            } else {
                // Hit a mirror at (r, c)
                // If entered moving right (dir == 0), turn down (dir = 1), move to (r+1, c)
                // If entered moving down (dir == 1), turn right (dir = 0), move to (r, c+1)
                if (dir == 0) {
                    dir = 1;
                    r += 1;
                } else {
                    dir = 0;
                    c += 1;
                }
            }
        }
        // Out of bounds
        return null;
    }
}