import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    private static class Interval {
        int l, r, w, id;

        Interval(int l, int r, int w, int id) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.id = id;
        }
    }

    private static class State {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            arr[i] = new Interval(interval.get(0), interval.get(1), interval.get(2), i);
        }

        // Sort intervals by left endpoint l ascending
        Arrays.sort(arr, (a, b) -> Integer.compare(a.l, b.l));

        // Precompute next_idx using binary search for each interval
        int[] nextIdx = new int[n];
        for (int i = 0; i < n; i++) {
            nextIdx[i] = binarySearch(arr, arr[i].r);
        }

        // dp[i][k] stores the optimal choice from interval i..n-1 with up to k intervals left
        State[][] dp = new State[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0, new ArrayList<>());
            }
        }

        // Process dynamic programming backwards
        for (int i = n - 1; i >= 0; i--) {
            int nxt = nextIdx[i];
            int origId = arr[i].id;
            long w = arr[i].w;

            for (int k = 1; k <= 4; k++) {
                // Option 1: Skip current interval
                State skipState = dp[i + 1][k];

                // Option 2: Take current interval
                State nxtState = dp[nxt][k - 1];
                long takeWeight = w + nxtState.weight;
                List<Integer> takeIndices = new ArrayList<>(nxtState.indices);
                takeIndices.add(origId);
                Collections.sort(takeIndices);

                // Compare weight first, then lexicographical order of index list
                if (isBetter(takeWeight, takeIndices, skipState.weight, skipState.indices)) {
                    dp[i][k] = new State(takeWeight, takeIndices);
                } else {
                    dp[i][k] = skipState;
                }
            }
        }

        List<Integer> resultList = dp[0][4].indices;
        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    // Binary search for the first interval starting strictly after target (r)
    private int binarySearch(Interval[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (arr[mid].l > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // Tie-breaker: true if take option is strictly better than skip option
    private boolean isBetter(long takeWeight, List<Integer> takeIndices, long skipWeight, List<Integer> skipIndices) {
        if (takeWeight != skipWeight) {
            return takeWeight > skipWeight;
        }
        int minLen = Math.min(takeIndices.size(), skipIndices.size());
        for (int i = 0; i < minLen; i++) {
            int cmp = Integer.compare(takeIndices.get(i), skipIndices.get(i));
            if (cmp != 0) {
                return cmp < 0;
            }
        }
        return takeIndices.size() < skipIndices.size();
    }
}