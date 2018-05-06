package com.student.practice.done.practiceDP.typeC;

public class EditDistance {

    public static void main(String[] args) {
        EditDistance app = new EditDistance();
        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6};
        int[] arr2 = new int[]{1, 9, 3, 5, 4};
        int[][] editDistanceDP = app.getEditDistanceDP(arr1, arr2);
    }

    private int[][] getEditDistanceDP(int[] arr1, int[] arr2) {

        int[][] editDistanceDP = new int[arr1.length + 1][arr2.length + 1];

        // x == 0 and y == 0
        editDistanceDP[0][0] = 0;
        // x == 0
        for (int y = 1; y <= arr2.length; y++) {
            editDistanceDP[0][y] = y;
        }
        // y == 0
        for (int x = 1; x <= arr1.length; x++) {
            editDistanceDP[x][0] = x;
        }

        for (int x = 1; x <= arr1.length; x++) {
            for (int y = 1; y <= arr2.length; y++) {

                if (arr1[x - 1] == arr2[y - 1]) {
                    editDistanceDP[x][y] = editDistanceDP[x - 1][y - 1];
                } else {
                    // replace
                    int a = editDistanceDP[x - 1][y - 1];
                    // delete from arr1 or insert into arr2
                    int b = editDistanceDP[x - 1][y];
                    // delete from arr2 or insert into arr1
                    int c = editDistanceDP[x][y - 1];
                    // minimum
                    editDistanceDP[x][y] = 1 + Integer.min(a, Integer.min(b, c));
                }

            }
        }

        return editDistanceDP;
    }

}
