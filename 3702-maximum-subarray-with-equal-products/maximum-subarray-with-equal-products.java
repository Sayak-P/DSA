import java.math.BigInteger;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 1;

        for (int i = 0; i < n; i++) {
            BigInteger prod = BigInteger.ONE;
            BigInteger lcm = BigInteger.ONE;
            int gcd = 0;

            for (int j = i; j < n; j++) {
                BigInteger num = BigInteger.valueOf(nums[j]);

                prod = prod.multiply(num);
                gcd = gcd(gcd, nums[j]);
                lcm = lcm(lcm, num);

                BigInteger rhs = lcm.multiply(BigInteger.valueOf(gcd));

                if (prod.equals(rhs)) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private BigInteger lcm(BigInteger a, BigInteger b) {
        return a.divide(a.gcd(b)).multiply(b);
    }
}