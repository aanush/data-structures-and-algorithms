package com.student.practice.done.practiceDP.typeA;

public class SubarrayMaximumSum {

    public static void main(String[] args) {
        SubarrayMaximumSum app = new SubarrayMaximumSum();
        int[] arr = new int[]{-2, -3, 4, -1, -2, 1, 5, -3};
        SumAndStartIndex[] maximumSumSubarrayDP = app.getMaximumSumSubarrayDP1(arr);
        app.getPrintSubarrayFromMaximumSumSubarrayDP1(arr, maximumSumSubarrayDP);
    }

    private void getPrintSubarrayFromMaximumSumSubarrayDP1(int[] arr, SumAndStartIndex[] maximumSumSubarrayDP) {

        int lastIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (maximumSumSubarrayDP[current].getSum() > maximumSumSubarrayDP[lastIndex].getSum()) {
                lastIndex = current;
            }
        }

        int startIndex = maximumSumSubarrayDP[lastIndex].getStartIndex();

        for (int current = startIndex; current <= lastIndex; current++) {
            System.out.println(arr[current]);
        }

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
