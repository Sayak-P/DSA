
import java.util.*;

class Solution {
    public int maxScore(int[] parent, int[] op, int[] score) {

        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] order = new int[n];
        int[] stack = new int[n];

        int top = 0, size = 0;
        stack[top++] = 0;

        while (top > 0) {
            int node = stack[--top];
            order[size++] = node;

            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        long[] dp = new long[n];

        for (int i = size - 1; i >= 0; i--) {

            int node = order[i];

            long sum = 0;
            long minChild = Long.MAX_VALUE;

            for (int child : tree[node]) {
                sum += dp[child];
                minChild = Math.min(minChild, dp[child]);
            }

            if (tree[node].isEmpty()) {
                dp[node] = score[node];
            }
            else if (op[node] == 1) {
                dp[node] = score[node] + sum - minChild;
            }
            else {
                dp[node] = score[node] + sum - Math.min(0L, minChild);
            }
        }

        return (int) dp[0];
    }
}
