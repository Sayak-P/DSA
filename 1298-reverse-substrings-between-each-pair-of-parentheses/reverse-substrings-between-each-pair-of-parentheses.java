import java.util.Stack;

class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        // Step 1: Pair up the matching parentheses indices
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        
        // Step 2: Traverse the string jumping through pairs and reversing direction
        StringBuilder result = new StringBuilder();
        int i = 0;
        int direction = 1; // 1 means moving forward, -1 means moving backward
        
        while (i < n) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                // Teleport to the matching parenthesis and reverse the traversal direction
                i = pair[i];
                direction = -direction;
            } else {
                result.append(c);
            }
            i += direction;
        }
        
        return result.toString();
    }
}