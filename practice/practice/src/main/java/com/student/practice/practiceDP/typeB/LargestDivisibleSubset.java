package com.student.practice.practiceDP.typeB;

import java.util.Arrays;

public class LargestDivisibleSubset {

    public static void main(String[] args) {
        LargestDivisibleSubset app = new LargestDivisibleSubset();
        int[] arr = new int[]{1, 16, 7, 8, 4};
        app.getPrintLDSFromLargestDivisibleSubset2(arr);
    }

    private void getPrintLDSFromLargestDivisibleSubset2(int[] arr) {

        Arrays.sort(arr);
        LengthAndPreviousIndex[] ldsDP = getLargestDivisibleSubsequenceDP2(arr);
        getPrintLDSFromLargestDivisibleSubsequenceDP1(arr, ldsDP);

    }

    private void getPrintLDSFromLargestDivisibleSubsequenceDP1(int[] arr, LengthAndPreviousIndex[] ldsDP) {

        int ldsIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (ldsDP[current].getLength() > ldsDP[ldsIndex].getLength()) {
                ldsIndex = current;
            }
        }

        while (ldsIndex > LengthAndPreviousIndex.NO_INDEX) {
            System.out.println(arr[ldsIndex]);
            ldsIndex = ldsDP[ldsIndex].getPreviousIndex();
        }

    }

    private LengthAndPreviousIndex[] getLargestDivisibleSubsequenceDP2(int[] arr) {

        LengthAndPreviousIndex[] ldsDP = new LengthAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            ldsDP[current] = new LengthAndPreviousIndex(1, LengthAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (arr[previous] != 0 && arr[current] % arr[previous] == 0) {
                    if (ldsDP[previous].getLength() + 1 > ldsDP[current].getLength()) {
                        ldsDP[current] = new LengthAndPreviousIndex(ldsDP[previous].getLength() + 1, previous);
                    }
                }

            }
        }

        return ldsDP;
    }

    private class LengthAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private final int length;
        private final int previousIndex;

        LengthAndPreviousIndex(int length, int previousIndex) {
            this.length = length;
            this.previousIndex = previousIndex;
        }

        int getLength() {
            return length;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
