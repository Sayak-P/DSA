import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        // Min-heap for free servers: sorted by weight, then by index
        // Each element is stored as an array: [weight, index]
        PriorityQueue<int[]> freeServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        // Min-heap for busy servers: sorted by free time, then weight, then index
        // Each element is stored as a long array: [freeTime, weight, index]
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Long.compare(a[0], b[0]);
            }
            if (a[1] != b[1]) {
                return Long.compare(a[1], b[1]);
            }
            return Long.compare(a[2], b[2]);
        });

        // Initialize all servers as free
        for (int i = 0; i < n; i++) {
            freeServers.offer(new int[] { servers[i], i });
        }

        long currentTime = 0;

        for (int j = 0; j < m; j++) {
            // Time cannot be less than the current task index j
            currentTime = Math.max(currentTime, j);

            // If there are no free servers, jump time to when the earliest server becomes free
            if (freeServers.isEmpty()) {
                currentTime = Math.max(currentTime, busyServers.peek()[0]);
            }

            // Move all servers that have finished their tasks by currentTime back to freeServers
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= currentTime) {
                long[] busy = busyServers.poll();
                freeServers.offer(new int[] { (int) busy[1], (int) busy[2] }); // [weight, index]
            }

            // Assign the current task to the best available free server
            int[] bestServer = freeServers.poll();
            int serverWeight = bestServer[0];
            int serverIndex = bestServer[1];

            ans[j] = serverIndex;

            // Push the server into the busy heap with its new release time
            long freeTime = currentTime + tasks[j];
            busyServers.offer(new long[] { freeTime, serverWeight, serverIndex });
        }

        return ans;
    }
}