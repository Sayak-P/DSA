import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final int MOD = 1_000_000_007;

    long[] sum;
    long[] sqSum;
    long[] lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        sqSum = new long[4 * n];
        lazy = new long[4 * n];

        Map<Integer, Integer> lastSeen = new HashMap<>();
        long totalSum = 0;

        for (int i = 0; i < n; i++) {
            int lastIdx = lastSeen.getOrDefault(nums[i], -1);
            // Update the range [lastIdx + 1, i] by adding 1
            update(1, 0, n - 1, lastIdx + 1, i, 1);
            lastSeen.put(nums[i], i);

            // The root (range [0, n-1]) stores the total sum of squares of all subarrays ending at or before i
            totalSum = (totalSum + sqSum[1]) % MOD;
        }

        return (int) totalSum;
    }

    private void push(int node, int l, int r) {
        if (lazy[node] != 0) {
            long v = lazy[node];
            int mid = (l + r) / 2;
            
            apply(2 * node, l, mid, v);
            apply(2 * node + 1, mid + 1, r, v);
            
            lazy[node] = 0;
        }
    }

    private void apply(int node, int l, int r, long v) {
        long len = (r - l + 1);
        // new_sqSum = sqSum + 2 * v * sum + len * v^2
        long newSqSum = (sqSum[node] + 2 * v * sum[node] + len * v * v) % MOD;
        // new_sum = sum + len * v
        long newSum = (sum[node] + len * v) % MOD;

        sqSum[node] = newSqSum;
        sum[node] = newSum;
        lazy[node] = (lazy[node] + v) % MOD;
    }

    private void update(int node, int l, int r, int ql, int qr, long v) {
        if (ql > r || qr < l) {
            return;
        }
        if (ql <= l && r <= qr) {
            apply(node, l, r, v);
            return;
        }
        push(node, l, r);
        int mid = (l + r) / 2;
        update(2 * node, l, mid, ql, qr, v);
        update(2 * node + 1, mid + 1, r, ql, qr, v);
        
        sum[node] = (sum[2 * node] + sum[2 * node + 1]) % MOD;
        sqSum[node] = (sqSum[2 * node] + sqSum[2 * node + 1]) % MOD;
    }
}
