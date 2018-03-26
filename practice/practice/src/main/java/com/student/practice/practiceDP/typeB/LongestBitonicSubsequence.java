package com.student.practice.practiceDP.typeB;

public class LongestBitonicSubsequence {

    public static void main(String[] args) {
        LongestBitonicSubsequence app = new LongestBitonicSubsequence();
        int[] arr = {5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 1, 2};
        int[] rev = {2, 1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5};
        app.getPrintLBSLength(arr);
    }

    private void getPrintLBSLength(int[] arr) {

        int[] rev = new int[arr.length];
        for (int current = 0; current <= arr.length - 1; current++) {
            rev[arr.length - 1 - current] = arr[current];
        }

        LengthAndPreviousIndex[] arrDP = getLongestIncreasingSubsequenceDP2(arr);
        LengthAndPreviousIndex[] tmpDP = getLongestIncreasingSubsequenceDP2(rev);

        LengthAndPreviousIndex[] revDP = new LengthAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            revDP[arr.length - 1 - current] = tmpDP[current];
        }

        int lbsIndex = 0;
        for (int current = 0; current <= arr.length - 1; current++) {
            if (arrDP[current].getLength() + revDP[current].getLength() - 1 >= arrDP[lbsIndex].getLength() + revDP[lbsIndex].getLength() - 1) {
                lbsIndex = current;
            }
        }
        int lbsIndexLength = arrDP[lbsIndex].getLength() + revDP[lbsIndex].getLength() - 1;

        System.out.println(lbsIndexLength);
    }

    private LengthAndPreviousIndex[] getLongestIncreasingSubsequenceDP2(int[] arr) {

        LengthAndPreviousIndex[] lisDP = new LengthAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            lisDP[current] = new LengthAndPreviousIndex(1, LengthAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (arr[previous] < arr[current] && 1 + lisDP[previous].getLength() > lisDP[current].getLength()) {
                    lisDP[current] = new LengthAndPreviousIndex(1 + lisDP[previous].getLength(), previous);
                }

            }
        }

        return lisDP;
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

        public int getPreviousIndex() {
            return previousIndex;
        }

    }

}
