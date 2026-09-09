import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class Solution {
    public int minMoves(String[] classroom, int energy) {
       int m = classroom.length;
        int n = classroom[0].length();
        
        char[][] grid = new char[m][n];
        int[][] litterIdx = new int[m][n];
        for (int[] row : litterIdx) {
            Arrays.fill(row, -1);
        }
        
        int startR = -1, startC = -1;
        int litterCount = 0;
        
        // Parse the grid, locate starting position and index the litter items
        for (int i = 0; i < m; i++) {
            grid[i] = classroom[i].toCharArray();
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startR = i;
                    startC = j;
                } else if (grid[i][j] == 'L') {
                    litterIdx[i][j] = litterCount++;
                }
            }
        }
        
        // If there's no litter at all, 0 moves are required
        if (litterCount == 0) return 0;
        
        // All bits set to 1 for the total number of litters
        int targetMask = (1 << litterCount) - 1;
        
        // maxEnergy[row][col][mask] stores the max energy we had when reaching this state
        int[][][] maxEnergy = new int[m][n][1 << litterCount];
        for (int[][] mat : maxEnergy) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        
        // Queue stores arrays of {row, col, mask, current_energy}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, 0, energy});
        maxEnergy[startR][startC][0] = energy;
        
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        
        int moves = 0;
        
        // Standard BFS loop
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1], mask = curr[2], e = curr[3];
                
                // If energy is 0, we can't step outwards from here.
                if (e == 0) continue;
                
                for (int d = 0; d < 4; d++) {
                    int nr = r + dRow[d];
                    int nc = c + dCol[d];
                    
                    // Check bounds
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                        if (grid[nr][nc] == 'X') continue; // Blocked by obstacle
                        
                        int ne = e - 1;
                        
                        // Handle Reset Area
                        if (grid[nr][nc] == 'R') {
                            ne = energy;
                        }
                        
                        int nmask = mask;
                        
                        // Handle Litter Collection
                        if (grid[nr][nc] == 'L') {
                            nmask |= (1 << litterIdx[nr][nc]);
                        }
                        
                        // If all litter is collected, return the move count
                        if (nmask == targetMask) {
                            return moves + 1;
                        }
                        
                        // Only add to the queue if we reached this state with more energy than before
                        if (ne > maxEnergy[nr][nc][nmask]) {
                            maxEnergy[nr][nc][nmask] = ne;
                            queue.offer(new int[]{nr, nc, nmask, ne});
                        }
                    }
                }
            }
            moves++;
        }
        
        // Exhausted all paths without collecting all litter
        return -1; 
    }
}