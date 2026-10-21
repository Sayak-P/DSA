import java.util.Arrays;

class Solution {
    public int minOperations(String s) {
        int n = s.length();
        
        // If length is 1, 0 operations needed
        if (n <= 1) {
            return 0;
        }
        
        // Check if already sorted
        boolean isSorted = true;
        char mn = s.charAt(0);
        char mx = s.charAt(0);
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c < mn) mn = c;
            if (c > mx) mx = c;
            if (i > 0 && s.charAt(i) < s.charAt(i - 1)) {
                isSorted = false;
            }
        }
        
        if (isSorted) {
            return 0;
        }
        
        // If length is 2 and not sorted, it's impossible
        if (n == 2) {
            return -1;
        }
        
        // If the minimum character is at the beginning or the maximum character is at the end
        if (s.charAt(0) == mn || s.charAt(n - 1) == mx) {
            return 1;
        }
        
        // Check if min or max appears anywhere in the interior proper substring
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == mn || s.charAt(i) == mx) {
                return 2;
            }
        }
        
        // Otherwise, it takes 3 operations
        return 3;
    }
}