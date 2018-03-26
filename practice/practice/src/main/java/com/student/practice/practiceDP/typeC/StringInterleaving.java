package com.student.practice.practiceDP.typeC;

public class StringInterleaving {

    public static void main(String[] args) {
        StringInterleaving app = new StringInterleaving();
        String[] arr1 = new String[]{"a", "a", "b"};
        String[] arr2 = new String[]{"a", "x", "y"};
        String[] arr3 = new String[]{"a", "a", "x", "a", "b", "y"};
        boolean[][] interleavingDP = app.getInterleavingDP2(arr1, arr2, arr3);
        System.out.println(interleavingDP[arr1.length][arr2.length]);
    }

    private boolean[][] getInterleavingDP2(String[] arr1, String[] arr2, String[] arr3) {

        boolean[][] interleavingDP = new boolean[arr1.length + 1][arr2.length + 1];

        interleavingDP[0][0] = true;

        // y == 0
        for (int x = 1; x - 1 <= arr1.length - 1; x++) {
            interleavingDP[x][0] = arr1[x - 1].equals(arr3[x - 1]) && interleavingDP[x - 1][0];
        }

        // x == 0
        for (int y = 1; y - 1 <= arr2.length - 1; y++) {
            interleavingDP[0][y] = arr2[y - 1].equals(arr3[y - 1]) && interleavingDP[0][y - 1];
        }

        for (int x = 1; x - 1 <= arr1.length - 1; x++) {
            for (int y = 1; y - 1 <= arr2.length - 1; y++) {
                boolean interleaved1st = arr1[x - 1].equals(arr3[x + y - 1]) && interleavingDP[x - 1][y];
                boolean interleaved2nd = arr2[y - 1].equals(arr3[x + y - 1]) && interleavingDP[x][y - 1];
                interleavingDP[x][y] = interleaved1st || interleaved2nd;
            }
        }

        return interleavingDP;
    }

}
