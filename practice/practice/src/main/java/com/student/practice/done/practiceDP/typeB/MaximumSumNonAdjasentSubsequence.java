package com.student.practice.done.practiceDP.typeB;

public class MaximumSumNonAdjasentSubsequence {

    public static void main(String[] args) {
        MaximumSumNonAdjasentSubsequence app = new MaximumSumNonAdjasentSubsequence();
        int arr[] = {2, 6, 3, 1, 7};
        SumAndPreviousIndex[] msnasDP = app.getMaximumSumNonAdjasentSubsequenceDP2(arr);
        app.getPrintMSNASFromMSNASDP1(arr, msnasDP);
    }

    private void getPrintMSNASFromMSNASDP1(int[] arr, SumAndPreviousIndex[] msnasDP) {

        int msnasIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (msnasDP[current].getSum() > msnasDP[msnasIndex].getSum()) {
                msnasIndex = current;
            }
        }

        while (msnasIndex > SumAndPreviousIndex.NO_INDEX) {
            System.out.println(arr[msnasIndex]);
            msnasIndex = msnasDP[msnasIndex].getPreviousIndex();
        }
    }

    private SumAndPreviousIndex[] getMaximumSumNonAdjasentSubsequenceDP2(int[] arr) {

        SumAndPreviousIndex[] msnasDP = new SumAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            msnasDP[current] = new SumAndPreviousIndex(arr[current], SumAndPreviousIndex.NO_INDEX);
        }

        for (int current = 2; current <= arr.length - 1; current++) {
            for (int previous = 0; previous <= current - 2; previous++) {

                if (arr[current] + msnasDP[previous].getSum() > msnasDP[current].getSum()) {
                    msnasDP[current] = new SumAndPreviousIndex(arr[current] + msnasDP[previous].getSum(), previous);
                }

            }
        }

        return msnasDP;
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
