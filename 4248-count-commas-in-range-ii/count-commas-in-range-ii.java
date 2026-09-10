class Solution {
    public long countCommas(long n) {
      if (n < 1000) {
            return 0;
        }

        long totalCommas = 0;
        long len = String.valueOf(n).length();

        // Count commas for all lengths strictly less than len (starting from 4)
        for (int L = 4; L < len; L++) {
            long count = 9 * (long) Math.pow(10, L - 1);
            long commasPerNum = (L - 1) / 3;
            totalCommas += count * commasPerNum;
        }

        // Count commas for the numbers of the maximum length (from 10^(len-1) to n)
        long startOfMaxLen = (long) Math.pow(10, len - 1);
        long countMaxLen = n - startOfMaxLen + 1;
        long commasForMaxLen = (len - 1) / 3;
        totalCommas += countMaxLen * commasForMaxLen;

        return totalCommas;  
    }
}