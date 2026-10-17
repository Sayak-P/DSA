import java.util.Arrays;

public class Solution {

    // Helper function to calculate the sum of digits of a number
    private int getDigitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    // Helper class to store the number and its original index
    private static class Element implements Comparable<Element> {
        int val;
        int index;

        Element(int val, int index) {
            this.val = val;
            this.index = index;
        }

        @Override
        public int compareTo(Element other) {
            // Compare by digit sum first
            // (Note: we call getDigitSum via outer class or static, let's make it static or self-contained)
            return Integer.compare(this.val, other.val);
        }
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        Element[] arr = new Element[n];
        
        for (int i = 0; i < n; i++) {
            arr[i] = new Element(nums[i], i);
        }

        // Sort based on digit sum, then by value
        Arrays.sort(arr, (a, b) -> {
            int sumA = getDigitSum(a.val);
            int sumB = getDigitSum(b.val);
            if (sumA != sumB) {
                return Integer.compare(sumA, sumB);
            }
            return Integer.compare(a.val, b.val);
        });

        // Find minimum swaps using cycle decomposition
        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            // If already visited or already in the correct position, continue
            if (visited[i] || arr[i].index == i) {
                continue;
            }

            // Traverse the cycle
            int cycleSize = 0;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                j = arr[j].index;
                cycleSize++;
            }

            if (cycleSize > 1) {
                swaps += (cycleSize - 1);
            }
        }

        return swaps;
    }
}