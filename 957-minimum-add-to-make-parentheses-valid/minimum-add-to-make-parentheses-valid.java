class Solution {
    public int minAddToMakeValid(String s) {
        int openNeeded = 0;
        int insertionsNeeded = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                openNeeded++;
            } else if (c == ')') {
                if (openNeeded > 0) {
                    openNeeded--;
                } else {
                    insertionsNeeded++;
                }
            }
        }

        return insertionsNeeded + openNeeded;
    }
}