import java.util.*;

public class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int remL = 0, remR = 0;
        
        // Step 1: Calculate the minimum number of misplaced '(' and ')'
        for (char c : s.toCharArray()) {
            if (c == '(') {
                remL++;
            } else if (c == ')') {
                if (remL > 0) {
                    remL--;
                } else {
                    remR++;
                }
            }
        }
        
        Set<String> result = new HashSet<>();
        backtrack(s, 0, 0, remL, remR, new StringBuilder(), result);
        return new ArrayList<>(result);
    }
    
    private void backtrack(String s, int index, int open, int remL, int remR, StringBuilder sb, Set<String> result) {
        if (index == s.length()) {
            if (remL == 0 && remR == 0 && open == 0) {
                result.add(sb.toString());
            }
            return;
        }
        
        char c = s.charAt(index);
        int len = sb.length();
        
        if (c == '(') {
            // Option 1: Remove '(' if we still have extra left parentheses to remove
            if (remL > 0) {
                backtrack(s, index + 1, open, remL - 1, remR, sb, result);
            }
            // Option 2: Keep '('
            sb.append(c);
            backtrack(s, index + 1, open + 1, remL, remR, sb, result);
            sb.setLength(len); // Backtrack
        } else if (c == ')') {
            // Option 1: Remove ')' if we still have extra right parentheses to remove
            if (remR > 0) {
                backtrack(s, index + 1, open, remL, remR - 1, sb, result);
            }
            // Option 2: Keep ')' only if there is a matching open '('
            if (open > 0) {
                sb.append(c);
                backtrack(s, index + 1, open - 1, remL, remR, sb, result);
                sb.setLength(len); // Backtrack
            }
        } else {
            // Non-parenthesis characters are always kept
            sb.append(c);
            backtrack(s, index + 1, open, remL, remR, sb, result);
            sb.setLength(len); // Backtrack
        }
    }
}