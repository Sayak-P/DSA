class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int[] answer = new int[seq.length()];
        int depth = 0;
        
        for (int i = 0; i < seq.length(); i++) {
            char ch = seq.charAt(i);
            if (ch == '(') {
                depth++;
                // Assign based on the parity of the current depth
                answer[i] = depth % 2;
            } else {
                answer[i] = depth % 2;
                depth--;
            }
        }
        
        return answer;
    }
}