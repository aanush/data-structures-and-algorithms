package com.student.practice.practiceDP.typeB;

public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        LongestIncreasingSubsequence app = new LongestIncreasingSubsequence();
        int[] arr = {23, 10, 22, 5, 33, 8, 9, 21, 50, 41, 60, 80, 99, 22, 23, 24, 25, 26, 27};
        LengthAndPreviousIndex[] lisDP = app.getLongestIncreasingSubsequenceDP2(arr);
        app.getPrintLISFromLISDP1(arr, lisDP);
    }

    private void getPrintLISFromLISDP1(int[] arr, LengthAndPreviousIndex[] lisDP) {

        int lisIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (lisDP[current].getLength() > lisDP[lisIndex].getLength()) {
                lisIndex = current;
            }
        }

        while (lisIndex > LengthAndPreviousIndex.NO_INDEX) {
            System.out.println(arr[lisIndex]);
            lisIndex = lisDP[lisIndex].getPreviousIndex();
        }

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

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
