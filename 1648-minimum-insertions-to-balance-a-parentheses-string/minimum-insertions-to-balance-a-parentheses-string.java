class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int needed = 0; // Tracks required ')'

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // If needed is odd, we have an isolated ')' that must be completed
                if (needed % 2 != 0) {
                    insertions++; // Insert ')'
                    needed--;     // Now it's paired
                }
                needed += 2; // Each '(' needs '))'
            } else { // c == ')'
                needed--;
                // If needed is -1, we found a ')' without a matching '('
                if (needed < 0) {
                    insertions++; // Insert '('
                    needed += 2;  // The inserted '(' needs '))', and we just consumed one ')'
                }
            }
        }

        return insertions + needed;
    }
}