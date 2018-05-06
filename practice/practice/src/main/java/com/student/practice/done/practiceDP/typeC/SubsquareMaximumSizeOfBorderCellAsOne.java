package com.student.practice.done.practiceDP.typeC;

public class SubsquareMaximumSizeOfBorderCellAsOne {

    public static void main(String[] args) {
        SubsquareMaximumSizeOfBorderCellAsOne app = new SubsquareMaximumSizeOfBorderCellAsOne();
        int[] column1 = new int[]{0, 1, 1, 1, 0};
        int[] column2 = new int[]{0, 0, 0, 1, 0};
        int[] column3 = new int[]{0, 1, 1, 1, 1};
        int[] column4 = new int[]{0, 1, 0, 1, 1};
        int[] column5 = new int[]{1, 1, 1, 1, 1};
        int[][] rect0 = new int[][]{column1, column2, column3, column4, column5};
        ContiguousCount[][] contiguousCountDP = app.getContiguousCountDP2(rect0);
        app.getPrintSubsquareMaximumSizeOfBorderCellAsOneFromContiguousCountDP3(rect0, contiguousCountDP);
    }

    void getPrintSubsquareMaximumSizeOfBorderCellAsOneFromContiguousCountDP3(int[][] rect, ContiguousCount[][] contiguousCountDP) {
        int xMax = -1;
        int yMax = -1;
        int sizeMax = 0;

        for (int x = rect.length - 1; x >= 0; x--) {
            for (int y = rect[x].length - 1; y >= 0; y--) {
                int onX = contiguousCountDP[x][y].getOnX();
                int onY = contiguousCountDP[x][y].getOnY();
                int sizeNow = Integer.min(onX, onY);
                while (sizeNow > sizeMax) {
                    if (contiguousCountDP[x - sizeNow + 1][y].getOnY() >= sizeNow && contiguousCountDP[x][y - sizeNow + 1].getOnX() >= sizeNow) {
                        sizeMax = sizeNow;
                        xMax = x;
                        yMax = y;
                        break;
                    } else {
                        sizeNow = sizeNow - 1;
                    }
                }
                //
            }
        }

        System.out.println("xMax = " + xMax);
        System.out.println("yMax = " + yMax);
        System.out.println("sizeMax = " + sizeMax);
    }

    private ContiguousCount[][] getContiguousCountDP2(int[][] rect) {

        ContiguousCount[][] contiguousCountDP = new ContiguousCount[rect.length][rect[0].length];

        // row : x == 0; column : y == 0
        contiguousCountDP[0][0] = rect[0][0] == 1 ? new ContiguousCount(1, 1) : new ContiguousCount(0, 0);

        // row : y == 0
        for (int x = 1; x <= rect.length - 1; x++) {
            int onX = contiguousCountDP[x - 1][0].getOnX();
            int onY = 0;
            contiguousCountDP[x][0] = rect[x][0] == 1 ? new ContiguousCount(1 + onX, 1 + onY) : new ContiguousCount(0, 0);
        }

        // column : x == 0
        for (int y = 1; y <= rect[0].length - 1; y++) {
            int onX = 0;
            int onY = contiguousCountDP[0][y - 1].getOnY();
            contiguousCountDP[0][y] = rect[0][y] == 1 ? new ContiguousCount(1 + onX, 1 + onY) : new ContiguousCount(0, 0);
        }

        for (int x = 1; x <= rect.length - 1; x++) {
            for (int y = 1; y <= rect[0].length - 1; y++) {
                int onX = contiguousCountDP[x - 1][y].getOnX();
                int onY = contiguousCountDP[x][y - 1].getOnY();
                contiguousCountDP[x][y] = rect[x][y] == 1 ? new ContiguousCount(1 + onX, 1 + onY) : new ContiguousCount(0, 0);
            }
        }

        return contiguousCountDP;
    }

    private class ContiguousCount {

        private final int onX;
        private final int onY;

        ContiguousCount(int onX, int onY) {
            this.onX = onX;
            this.onY = onY;
        }

        int getOnX() {
            return onX;
        }

        int getOnY() {
            return onY;
        }

    }

}
