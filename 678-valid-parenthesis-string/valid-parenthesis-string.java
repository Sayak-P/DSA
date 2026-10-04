class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0; // Minimum possible open parentheses
        int maxOpen = 0; // Maximum possible open parentheses

        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else { // c == '*'
                minOpen--; // Treat '*' as ')'
                maxOpen++; // Treat '*' as '('
            }

            // If maxOpen is negative, there are too many ')' to ever be balanced
            if (maxOpen < 0) {
                return false;
            }
            
            // minOpen cannot drop below 0. If it does, we simply treat one of the
            // earlier '*' as an empty string instead of a ')' to fix it.
            if (minOpen < 0) {
                minOpen = 0;
            }
        }

        // It is valid if we can reach exactly 0 open parentheses at the end
        return minOpen == 0;
    }
}