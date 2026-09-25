import java.util.*;

class Solution {
    private int index;
    private String expression;

    public List<String> braceExpansionII(String expression) {
        this.index = 0;
        this.expression = expression;
        // Using a TreeSet automatically sorts the strings and removes duplicates
        Set<String> resultSet = parseExpression();
        return new ArrayList<>(resultSet);
    }

    // Parses comma-separated expressions, effectively acting as the Union operator
    private Set<String> parseExpression() {
        Set<String> result = new TreeSet<>();
        result.addAll(parseTerm());
        
        while (index < expression.length() && expression.charAt(index) == ',') {
            index++; // Skip ','
            result.addAll(parseTerm());
        }
        return result;
    }

    // Parses adjacent factors, effectively acting as the Concatenation (Cartesian product) operator
    private Set<String> parseTerm() {
        Set<String> result = new TreeSet<>();
        result.add("");
        
        while (index < expression.length() && (expression.charAt(index) == '{' || Character.isLetter(expression.charAt(index)))) {
            Set<String> nextFactor = parseFactor();
            Set<String> nextResult = new TreeSet<>();
            
            for (String prefix : result) {
                for (String suffix : nextFactor) {
                    nextResult.add(prefix + suffix);
                }
            }
            result = nextResult;
        }
        return result;
    }

    // Parses either a bracketed group '{ ... }' or consecutive lowercase letters
    private Set<String> parseFactor() {
        if (expression.charAt(index) == '{') {
            index++; // Skip '{'
            Set<String> result = parseExpression();
            index++; // Skip '}'
            return result;
        } else {
            StringBuilder sb = new StringBuilder();
            while (index < expression.length() && Character.isLetter(expression.charAt(index))) {
                sb.append(expression.charAt(index));
                index++;
            }
            
            Set<String> result = new TreeSet<>();
            result.add(sb.toString());
            return result;
        }
    }
}