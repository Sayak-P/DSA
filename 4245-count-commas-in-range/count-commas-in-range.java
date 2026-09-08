class Solution {
    
    public int countCommas(int n) {
     int count = 0;
        int base = 1000;
        
        // Count numbers that contribute at least 1 comma, then 2 commas, etc.
        while (n >= base) {
            count += (n - base + 1);
            
            // Prevent overflow if n is extremely large
            if (Long.MAX_VALUE / 1000 < base) {
                break;
            }
            base *= 1000;
        }
        
        return count;
    }   
}