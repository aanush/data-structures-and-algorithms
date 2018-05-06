package com.student.practice.done.practiceDP.typeC;

public class SubsquareMaximumSizeOfAllCellAsOne {

    public static void main(String[] args) {
        SubsquareMaximumSizeOfAllCellAsOne app = new SubsquareMaximumSizeOfAllCellAsOne();
        int[] column1 = new int[]{0, 1, 0, 1};
        int[] column2 = new int[]{0, 0, 1, 0};
        int[] column3 = new int[]{1, 1, 1, 1};
        int[] column4 = new int[]{1, 1, 1, 1};
        int[] column5 = new int[]{1, 1, 1, 1};
        int[][] rect0 = new int[][]{column1, column2, column3, column4, column5};
        int[][] contiguousDP = app.getContiguousCountDP2(rect0);
        app.getPrintSubsquareMaximumSizeOfAllCellAsOne2(rect0, contiguousDP);
    }

    private void getPrintSubsquareMaximumSizeOfAllCellAsOne2(int[][] rect, int[][] contiguousDP) {
        int xMax = -1;
        int yMax = -1;
        int sizeMax = 0;

        for (int x = rect.length - 1; x >= 0; x--) {
            for (int y = rect[x].length - 1; y >= 0; y--) {
                if (contiguousDP[x][y] > sizeMax) {
                    sizeMax = contiguousDP[x][y];
                    xMax = x;
                    yMax = y;
                }
            }
        }

        System.out.println("xMax = " + xMax);
        System.out.println("yMax = " + yMax);
        System.out.println("sizeMax = " + sizeMax);
    }

    private int[][] getContiguousCountDP2(int[][] rect) {

        int[][] contiguousCountDP = new int[rect.length][rect[0].length];

        // x == 0 and y == 0
        contiguousCountDP[0][0] = rect[0][0];

        // y == 0
        for (int x = 1; x <= rect.length - 1; x++) {
            contiguousCountDP[x][0] = rect[x][0];
        }

        // x == 0
        for (int y = 1; y <= rect[0].length - 1; y++) {
            contiguousCountDP[0][y] = rect[0][y];
        }

        for (int x = 1; x <= rect.length - 1; x++) {
            for (int y = 1; y <= rect[x].length - 1; y++) {

                if (rect[x][y] == 1) {
                    contiguousCountDP[x][y] = 1 + Integer.min(contiguousCountDP[x - 1][y - 1], Integer.min(contiguousCountDP[x][y - 1], contiguousCountDP[x - 1][y]));
                } else {
                    contiguousCountDP[x][y] = 0;
                }

            }
        }

        return contiguousCountDP;
    }

}
