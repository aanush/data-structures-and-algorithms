package com.student.practice.done.practiceDP.typeB;

public class MaximumSumIncreasingSubsequence {

    public static void main(String[] args) {
        MaximumSumIncreasingSubsequence app = new MaximumSumIncreasingSubsequence();
        int arr[] = {4, 6, 1, 3, 8, 4, 6};
        SumAndPreviousIndex[] lisDP = app.getMaximumSumIncreasingSubsequenceDP2(arr);
        app.getPrintMSISFromMSISDP1(arr, lisDP);
    }

    private void getPrintMSISFromMSISDP1(int[] arr, SumAndPreviousIndex[] msisDP) {

        int msisIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (msisDP[current].getSum() > msisDP[msisIndex].getSum()) {
                msisIndex = current;
            }
        }

        while (msisIndex > SumAndPreviousIndex.NO_INDEX) {
            System.out.println(arr[msisIndex]);
            msisIndex = msisDP[msisIndex].getPreviousIndex();
        }

    }

    private SumAndPreviousIndex[] getMaximumSumIncreasingSubsequenceDP2(int[] arr) {

        SumAndPreviousIndex[] msisDP = new SumAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            msisDP[current] = new SumAndPreviousIndex(arr[current], SumAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (arr[previous] < arr[current] && arr[current] + msisDP[previous].getSum() > msisDP[current].getSum()) {
                    msisDP[current] = new SumAndPreviousIndex(arr[current] + msisDP[previous].getSum(), previous);
                }

            }
        }

        return msisDP;
    }

    private class SumAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private final int sum;
        private final int previousIndex;

        SumAndPreviousIndex(int sum, int previousIndex) {
            this.sum = sum;
            this.previousIndex = previousIndex;
        }

        int getSum() {
            return sum;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
