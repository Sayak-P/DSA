class Solution {
    public boolean checkString(String s) {
        boolean seenB = false;

        for (char ch : s.toCharArray()) {
            if (ch == 'b') {
                seenB = true;
            } else if (seenB) {
                return false;
            }
        }

        return true;
    }
}