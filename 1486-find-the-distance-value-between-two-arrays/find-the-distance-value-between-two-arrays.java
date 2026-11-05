import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int count = 0;
        for (int x : arr1) {
            // find the first index in arr2 with value >= x
            int lo = 0, hi = arr2.length;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (arr2[mid] < x) lo = mid + 1;
                else hi = mid;
            }
            boolean nearRight = lo < arr2.length && arr2[lo] - x <= d;
            boolean nearLeft = lo > 0 && x - arr2[lo - 1] <= d;
            if (!nearRight && !nearLeft) count++;
        }
        return count;
    }
}