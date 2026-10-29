import java.util.*;

class Solution {
    private int n, timer;
    private int[] parent, depth, size, heavy, head, pos, value;
    private List<Integer>[] adj;
    private int[] seg;

    public List<Boolean> palindromePath(int n, int[][] edges, String s,
                                        String[] queries) {
        this.n = n;

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];
        value = new int[n];

        Arrays.fill(heavy, -1);

        // Build parent, depth, and traversal order.
        int[] order = new int[n];
        int count = 0;
        int[] stack = new int[n];
        int top = 0;

        stack[top++] = 0;
        parent[0] = -1;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int v : adj[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        // Calculate subtree sizes and heavy children.
        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            size[u] = 1;
            int maxSize = 0;

            for (int v : adj[u]) {
                if (v == parent[u]) {
                    continue;
                }

                size[u] += size[v];

                if (size[v] > maxSize) {
                    maxSize = size[v];
                    heavy[u] = v;
                }
            }
        }

        // Assign Heavy-Light Decomposition positions.
        timer = 0;

        int[] nodes = new int[n];
        int[] heads = new int[n];

        top = 0;
        nodes[top] = 0;
        heads[top++] = 0;

        while (top > 0) {
            top--;

            int u = nodes[top];
            int h = heads[top];

            while (u != -1) {
                head[u] = h;
                pos[u] = timer;
                value[timer++] = 1 << (s.charAt(u) - 'a');

                for (int v : adj[u]) {
                    if (v == parent[u] || v == heavy[u]) {
                        continue;
                    }

                    nodes[top] = v;
                    heads[top++] = v;
                }

                u = heavy[u];
            }
        }

        // Build segment tree.
        seg = new int[4 * n];
        build(1, 0, n - 1);

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                value[pos[u]] = 1 << (c - 'a');

                update(1, 0, n - 1, pos[u], value[pos[u]]);
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int mask = 0;

                // XOR the segments until both nodes share a chain.
                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int temp = u;
                        u = v;
                        v = temp;
                    }

                    mask ^= query(
                        1, 0, n - 1, pos[head[u]], pos[u]
                    );

                    u = parent[head[u]];
                }

                // Process the final segment.
                int left = Math.min(pos[u], pos[v]);
                int right = Math.max(pos[u], pos[v]);

                mask ^= query(1, 0, n - 1, left, right);

                // At most one character may have an odd frequency.
                result.add(Integer.bitCount(mask) <= 1);
            }
        }

        return result;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            seg[node] = value[l];
            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            seg[node] = val;
            return;
        }

        int mid = l + (r - l) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, val);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, val);
        }

        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    private int query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return seg[node];
        }

        int mid = l + (r - l) / 2;
        int ans = 0;

        if (ql <= mid) {
            ans ^= query(node * 2, l, mid, ql, qr);
        }

        if (qr > mid) {
            ans ^= query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        return ans;
    }
}

