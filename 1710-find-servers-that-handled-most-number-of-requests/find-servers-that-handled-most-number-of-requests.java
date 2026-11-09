import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        
        // Priority queue stores int[]: {free_time, server_id}
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] requestCount = new int[k];
        int maxCount = 0;
        
        for (int i = 0; i < arrival.length; i++) {
            int currTime = arrival[i];
            
            // Free up servers whose tasks have finished by the current arrival time
            while (!busy.isEmpty() && busy.peek()[0] <= currTime) {
                available.add(busy.poll()[1]);
            }
            
            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }
            
            // Find the preferred server using ceiling to get the first available >= i % k
            int start = i % k;
            Integer server = available.ceiling(start);
            if (server == null) {
                // Wrap around to the beginning of the server list
                server = available.first();
            }
            
            // Assign request to the chosen server
            available.remove(server);
            requestCount[server]++;
            maxCount = Math.max(maxCount, requestCount[server]);
            
            busy.offer(new int[]{currTime + load[i], server});
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxCount) {
                result.add(i);
            }
        }
        
        return result;
    }
}