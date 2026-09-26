class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        // Map to store knowledge for O(1) lookups
        Map<String, String> map = new HashMap<>();
        for (List<String> list : knowledge) {
            map.put(list.get(0), list.get(1));
        }
        
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        int i = 0;
        
        while (i < n) {
            char c = s.charAt(i);
            if (c == '(') {
                int j = i + 1;
                // Find the closing bracket
                while (s.charAt(j) != ')') {
                    j++;
                }
                // Extract the key and look it up in the map
                String key = s.substring(i + 1, j);
                sb.append(map.getOrDefault(key, "?"));
                
                // Move the pointer past the closing bracket
                i = j + 1;
            } else {
                sb.append(c);
                i++;
            }
        }
        
        return sb.toString();
    }
}