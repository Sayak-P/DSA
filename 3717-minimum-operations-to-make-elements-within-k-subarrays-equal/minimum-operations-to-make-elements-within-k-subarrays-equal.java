
import java.util.*;

class Solution {

    static class Median {
        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();

        long sumLow = 0, sumHigh = 0;
        int sizeLow = 0, sizeHigh = 0;

        void remove(TreeMap<Integer, Integer> map, int v) {
            int count = map.get(v);
            if (count == 1) map.remove(v);
            else map.put(v, count - 1);
        }

        void add(int v) {
            if (sizeLow == 0 || v <= low.lastKey()) {
                low.put(v, low.getOrDefault(v, 0) + 1);
                sumLow += v;
                sizeLow++;
            } else {
                high.put(v, high.getOrDefault(v, 0) + 1);
                sumHigh += v;
                sizeHigh++;
            }
            balance();
        }

        void erase(int v) {
            if (low.containsKey(v)) {
                remove(low, v);
                sumLow -= v;
                sizeLow--;
            } else {
                remove(high, v);
                sumHigh -= v;
                sizeHigh--;
            }
            balance();
        }

        void balance() {
            while (sizeLow > sizeHigh + 1) {
                int v = low.lastKey();
                remove(low, v);
                sumLow -= v;
                sizeLow--;

                high.put(v, high.getOrDefault(v, 0) + 1);
                sumHigh += v;
                sizeHigh++;
            }

            while (sizeLow < sizeHigh) {
                int v = high.firstKey();
                remove(high, v);
                sumHigh -= v;
                sizeHigh--;

                low.put(v, low.getOrDefault(v, 0) + 1);
                sumLow += v;
                sizeLow++;
            }
        }

        long getCost() {
            long median = low.lastKey();

            return median * sizeLow - sumLow
                 + sumHigh - median * sizeHigh;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];
        Median median = new Median();

        for (int i = 0; i < x; i++) {
            median.add(nums[i]);
        }

        cost[0] = median.getCost();

        for (int i = x; i < n; i++) {
            median.erase(nums[i - x]);
            median.add(nums[i]);
            cost[i - x + 1] = median.getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
