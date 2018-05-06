package com.student.practice.done.practiceDP.typeE;

public class TextJustification {

    public static void main(String[] args) {
        TextJustification app = new TextJustification();
//        int[] arr = new int[]{6, 3, 5, 2, 4};
        int[] arr = new int[]{4, 2, 2, 7, 2, 4, 14};
        int width = 16;
        int[][] textSumDP = app.getTextSumDP2(arr);
        int[][] blankSquareDP = app.getBlankSquareDP2(arr, width, textSumDP);
        Justification[] justificationDP = app.getJustificationDP2(arr, blankSquareDP);
        app.getPrintJustification(justificationDP);
    }

    private void getPrintJustification(Justification[] justificationDP) {

        int pivot1 = justificationDP.length - 1;
        int pivot2 = justificationDP[justificationDP.length - 1].getPivotIndex();
        while (pivot2 != -1) {
            System.out.println("text from index including " + (pivot2 + 1) + " to index including " + pivot1 + " will go together");
            pivot1 = pivot2;
            pivot2 = justificationDP[pivot2].getPivotIndex();
        }
    }

    private Justification[] getJustificationDP2(int[] arr, int[][] blankSquareDP) {

        // Justification[last].getBlanKSquareSum = minimized sum of (square of (number of blanks at each line))
        // for start index = 0 and last index = last
        // Justification[last].getPivotIndex is the index of last breakingPivot
        Justification[] justificationDP = new Justification[arr.length];

        justificationDP[0] = new Justification(blankSquareDP[0][0], -1);

        // start = 0;
        for (int last = 1; last <= arr.length - 1; last++) {

            if (blankSquareDP[0][last] != -1) {
                // all can go in one line
                // no breakingPivot means pivot = -1
                justificationDP[last] = new Justification(blankSquareDP[0][last], -1);
            } else {
                // all can not go in one line
                // must break but where ? try all from 0 to last - 1
                // and break there at the minimum of all possible blankSquareSum
                int minBlankSquareSum = Integer.MAX_VALUE;
                int pivot = 0;
                // trying all pivot from 0 to last - 1
                for (int temp = 0; temp <= last - 1; temp++) {
                    // check if temp + 1 to last can go in one line or not
                    if (blankSquareDP[temp + 1][last] != -1) {
                        // tempBlankSquareSum or
                        // justificationDP[temp].getBlankSquareSum() + blankSquareDP[temp + 1][last]
                        // is the blankSquareSum from 0 to last if
                        // 0 to temp goes into already justified line (already minimized)
                        // and temp + 1 to last goes into next line
                        // already justified blankSquareSum from o to temp added to blankSquare value for temp + 1 to last
                        int tempBlankSquareSum = justificationDP[temp].getBlankSquareSum() + blankSquareDP[temp + 1][last];
                        if (tempBlankSquareSum < minBlankSquareSum) {
                            minBlankSquareSum = tempBlankSquareSum;
                            pivot = temp;
                        }
                    }
                }
                // after trying all possible pivot we have selected the pivot with minimum blankSquareSum
                justificationDP[last] = new Justification(minBlankSquareSum, pivot);
            }
        }

        return justificationDP;
    }

    private int[][] getBlankSquareDP2(int[] arr, int width, int[][] textSumDP) {

        int[][] blankSquareDP = new int[arr.length][arr.length];

        for (int length = 0; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                int blank = width - textSumDP[start][last];
                blankSquareDP[start][last] = blank >= 0 ? blank * blank : -1;
            }
        }

        return blankSquareDP;
    }

    private int[][] getTextSumDP2(int[] arr) {

        int[][] textSumDP = new int[arr.length][arr.length];

        // length == 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                textSumDP[start][last] = arr[start];
            }
        }

        // length == 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                textSumDP[start][last] = arr[start] + 1 + arr[last];
            }
        }

        // length == 1
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                textSumDP[start][last] = textSumDP[start][last - 1] + 1 + arr[last];
            }
        }

        return textSumDP;
    }

    private class Justification {

        private final int blankSquareSum;
        private final int pivotIndex;

        Justification(int blankSquareSum, int pivotIndex) {
            this.blankSquareSum = blankSquareSum;
            this.pivotIndex = pivotIndex;
        }

        int getBlankSquareSum() {
            return blankSquareSum;
        }

        int getPivotIndex() {
            return pivotIndex;
        }

    }

}
