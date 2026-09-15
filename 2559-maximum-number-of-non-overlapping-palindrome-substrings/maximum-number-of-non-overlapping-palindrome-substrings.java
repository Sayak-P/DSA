import java.util.*;

class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        // Step 1: Precompute all palindrome substrings using Dynamic Programming
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (len <= 2) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
            }
        }
        
        // Step 2: Collect all valid palindrome intervals [start, end] of length >= k
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + k - 1; j < n; j++) {
                if (dp[i][j]) {
                    intervals.add(new int[]{i, j});
                }
            }
        }
        
        // Step 3: Sort intervals by their end time ascending (Greedy strategy)
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));
        
        // Step 4: Greedily select non-overlapping intervals
        int count = 0;
        int lastEnd = -1;
        for (int[] interval : intervals) {
            if (interval[0] > lastEnd) {
                count++;
                lastEnd = interval[1]; // Update the end position of the last picked palindrome
            }
        }
        
        return count;
    }
}