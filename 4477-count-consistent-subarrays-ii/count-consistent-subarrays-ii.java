
import java.util.*;

class Solution {
    public long countConsistent(int[] nums) {
        int n = nums.length;
        int B = 20;

        int[] prevSmaller = new int[n];
        int[] nextSmaller = new int[n];

        int[] stack = new int[n];
        int top = -1;

        // Previous strictly smaller element
        for (int i = 0; i < n; i++) {
            while (top >= 0 && nums[stack[top]] >= nums[i]) {
                top--;
            }

            prevSmaller[i] = (top == -1) ? -1 : stack[top];
            stack[++top] = i;
        }

        // Next smaller or equal element
        top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] > nums[i]) {
                top--;
            }

            nextSmaller[i] = (top == -1) ? n : stack[top];
            stack[++top] = i;
        }

        // Nearest position on the left where each bit is zero
        int[][] leftZero = new int[B][n];
        int[] lastZero = new int[B];

        Arrays.fill(lastZero, -1);

        for (int i = 0; i < n; i++) {
            for (int b = 0; b < B; b++) {
                if ((nums[i] & (1 << b)) == 0) {
                    lastZero[b] = i;
                }

                leftZero[b][i] = lastZero[b];
            }
        }

        // Nearest position on the right where each bit is zero
        int[][] rightZero = new int[B][n];
        int[] nextZero = new int[B];

        Arrays.fill(nextZero, n);

        for (int i = n - 1; i >= 0; i--) {
            for (int b = 0; b < B; b++) {
                if ((nums[i] & (1 << b)) == 0) {
                    nextZero[b] = i;
                }

                rightZero[b][i] = nextZero[b];
            }
        }

        long answer = 0;

        for (int i = 0; i < n; i++) {
            int left = prevSmaller[i];
            int right = nextSmaller[i];

            // Find nearest position that breaks the AND condition
            for (int b = 0; b < B; b++) {
                if ((nums[i] & (1 << b)) != 0) {
                    left = Math.max(left, leftZero[b][i]);
                    right = Math.min(right, rightZero[b][i]);
                }
            }

            long leftChoices = i - left;
            long rightChoices = right - i;

            answer += leftChoices * rightChoices;
        }

        return answer;
    }
}
