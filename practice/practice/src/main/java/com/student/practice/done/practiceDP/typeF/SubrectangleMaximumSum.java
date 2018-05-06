package com.student.practice.done.practiceDP.typeF;

public class SubrectangleMaximumSum {

    public static void main(String[] args) {
        SubrectangleMaximumSum app = new SubrectangleMaximumSum();
        int[] column1 = new int[]{+2, +0, +2, -3};
        int[] column2 = new int[]{+1, +6, -2, +3};
        int[] column3 = new int[]{-3, +3, -1, +1};
        int[] column4 = new int[]{-4, +4, +4, +0};
        int[] column5 = new int[]{+5, +1, -5, +3};
        int[][] rect0 = new int[][]{column1, column2, column3, column4, column5};
        // we should rotate the rectangle if column > row as complexity order is column * column * row
        // think why ? there should be at least one positive entry
        // if not shift all the entry by adding a positive number to all entry
        app.getPrintSubrectangleMaximumSum3(rect0);
    }

    private void getPrintSubrectangleMaximumSum3(int[][] rect) {
        int x1Max = 0;
        int x2Max = 0;
        int y1Max = 0;
        int y2Max = 0;
        int maximumSum = 0;

        for (int x1 = 0; x1 <= rect.length - 1; x1++) {
            // re initialize working array
            int[] arr = new int[rect[x1].length];

            for (int x2 = x1; x2 <= rect.length - 1; x2++) {
                // current column index = x2 : the right column index
                int[] arrTemp = rect[x2];
                for (int y = 0; y <= arr.length - 1; y++) {
                    // add current column array to working array : arr
                    arr[y] = arr[y] + arrTemp[y];
                }

                // get maximum sum subarray of the working array : arr
                SumAndStartIndex[] maximumSumSubarrayDP = getMaximumSumSubarrayDP1(arr);
                int[] sl = getStartAndLastIndexOfMaximumSumSubarray1(arr, maximumSumSubarrayDP);
                int y1 = sl[0];
                int y2 = sl[1];
                int currentSum = maximumSumSubarrayDP[y2].getSum();
                if (currentSum > maximumSum) {
                    // update maximum sum subrectangle sum and corner index
                    maximumSum = currentSum;
                    x1Max = x1;
                    x2Max = x2;
                    y1Max = y1;
                    y2Max = y2;
                }
            }
            //
        }

        System.out.println("maximum sum = " + maximumSum);
        System.out.println("x1Max = " + x1Max);
        System.out.println("x2Max = " + x2Max);
        System.out.println("y1Max = " + y1Max);
        System.out.println("y2Max = " + y2Max);
    }

    private int[] getStartAndLastIndexOfMaximumSumSubarray1(int[] arr, SumAndStartIndex[] maximumSumSubarrayDP) {

        int lastIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (maximumSumSubarrayDP[current].getSum() > maximumSumSubarrayDP[lastIndex].getSum()) {
                lastIndex = current;
            }
        }

        int startIndex = maximumSumSubarrayDP[lastIndex].getStartIndex();

        return new int[]{startIndex, lastIndex};
    }

    private SumAndStartIndex[] getMaximumSumSubarrayDP1(int[] arr) {

        SumAndStartIndex[] maximumSumSubarrayDP = new SumAndStartIndex[arr.length];

        maximumSumSubarrayDP[0] = new SumAndStartIndex(arr[0], 0);

        for (int current = 1; current <= arr.length - 1; current++) {
            if (maximumSumSubarrayDP[current - 1].getSum() > 0) {
                maximumSumSubarrayDP[current] = new SumAndStartIndex(maximumSumSubarrayDP[current - 1].getSum() + arr[current], maximumSumSubarrayDP[current - 1].getStartIndex());
            } else {
                maximumSumSubarrayDP[current] = new SumAndStartIndex(arr[current], current);
            }
        }

        return maximumSumSubarrayDP;
    }

    private class SumAndStartIndex {

        private final int sum;
        private final int startIndex;

        SumAndStartIndex(int sum, int startIndex) {
            this.sum = sum;
            this.startIndex = startIndex;
        }

        int getSum() {
            return sum;
        }

        int getStartIndex() {
            return startIndex;
        }

    }

}
