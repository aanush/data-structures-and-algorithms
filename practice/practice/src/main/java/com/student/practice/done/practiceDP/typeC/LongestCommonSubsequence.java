package com.student.practice.done.practiceDP.typeC;

// todo
// check if longest common subset = longest common subsequence of sorted arr1 and sorted arr2
// check if longest common subset = intersection of sorted arr1 and sorted arr2
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        LongestCommonSubsequence app = new LongestCommonSubsequence();
        int[] arr1 = new int[]{18, 20, 22, 44, 66, 88, 92, 92, 95, 96};
        int[] arr2 = new int[]{18, 20, 22, 22, 22, 44, 66, 88, 95, 92, 96};
        int[][] lcsLengthDP = app.getLCSLengthDP2(arr1, arr2);
        app.getPrintLCSFromLCSLengthDP2(arr1, arr2, lcsLengthDP);
    }

    private void getPrintLCSFromLCSLengthDP2(int[] arr1, int[] arr2, int[][] lcsLengthDP) {


        int lcsLength = 0;
        int column = 0;
        int row = 0;

        for (int x = 0; x <= arr1.length - 1; x++) {
            for (int y = 0; y <= arr2.length - 1; y++) {
                if (lcsLengthDP[x][y] >= lcsLength) {
                    lcsLength = lcsLengthDP[x][y];
                    column = x;
                    row = y;
                }
            }
        }

        while (lcsLength > 0) {

            if (column == 0 || row == 0) {
                System.out.println(arr1[column] + " = " + arr2[row]);
                break;
            }

            if (arr1[column] == arr2[row]) {
                // must be
                // lcsLengthDP[column][row] == 1 = lcsLengthDP[column - 1][row - 1]
                System.out.println(arr1[column] + " = " + arr2[row]);
                lcsLength = lcsLength - 1;
                column = column - 1;
                row = row - 1;
                continue;
            }

            if (lcsLengthDP[column][row] == lcsLengthDP[column][row - 1]) {
                row = row - 1;
                continue;
            }

            if (lcsLengthDP[column][row] == lcsLengthDP[column - 1][row]) {
                column = column - 1;
                continue;
            }

        }

    }

    private int[][] getLCSLengthDP2(int[] arr1, int[] arr2) {

        int[][] lcsLengthDP = new int[arr1.length][arr2.length];

        // column x == 0 and row y == 0
        lcsLengthDP[0][0] = (arr1[0] == arr2[0] ? 1 : 0);

        // column x == 0
        for (int y = 1; y <= arr2.length - 1; y++) {
            lcsLengthDP[0][y] = (arr1[0] == arr2[y] ? 1 : 0);
        }

        //row y == 0
        for (int x = 1; x <= arr1.length - 1; x++) {
            lcsLengthDP[x][0] = (arr1[x] == arr2[0] ? 1 : 0);
        }

        for (int x = 1; x <= arr1.length - 1; x++) {
            for (int y = 1; y <= arr2.length - 1; y++) {
                lcsLengthDP[x][y] = (arr1[x] == arr2[y] ? 1 + lcsLengthDP[x - 1][y - 1] : Integer.max(lcsLengthDP[x][y - 1], lcsLengthDP[x - 1][y]));
            }
        }

        return lcsLengthDP;
    }

}
