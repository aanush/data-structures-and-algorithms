package com.student.practice.done.practiceDP.typeB;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BoxStacking {

    public static void main(String[] args) {
        BoxStacking app = new BoxStacking();
        int[] lbh421 = new int[]{4, 2, 1};//{4, 4, 4};
        int[] lbh532 = new int[]{5, 3, 2};//{1, 2, 3};
        int[][] arrLBH = new int[][]{lbh421, lbh532};
        app.getPrintBoxStacking(arrLBH);
    }

    private void getPrintBoxStacking(int[][] arrLBH) {
        Box[] arrBox = getBoxArrayOfPossibleOrientation(arrLBH);
        HeightAndPreviousIndex[] boxStackingDP = getBoxStackingDP2(arrBox);
        getPrintBoxStackingFromBoxStackingDP1(arrBox, boxStackingDP);
    }

    private void getPrintBoxStackingFromBoxStackingDP1(Box[] arrBox, HeightAndPreviousIndex[] boxStackingDP) {

        int bsIndex = 0;

        for (int current = 0; current <= arrBox.length - 1; current++) {
            if (boxStackingDP[current].getHeight() > boxStackingDP[bsIndex].getHeight()) {
                bsIndex = current;
            }
        }

        while (bsIndex > HeightAndPreviousIndex.NO_INDEX) {
            System.out.println("length = " + arrBox[bsIndex].getLength() + ", breadth = " + arrBox[bsIndex].getBreadth() + ", height = " + arrBox[bsIndex].getHeight());
            bsIndex = boxStackingDP[bsIndex].getPreviousIndex();
        }
    }

    private HeightAndPreviousIndex[] getBoxStackingDP2(Box[] arrBox) {

        Arrays.sort(arrBox, (box1, box2) -> box2.getLength() * box2.getBreadth() - box1.getLength() * box1.getBreadth());

        HeightAndPreviousIndex[] boxStackingDP = new HeightAndPreviousIndex[arrBox.length];

        for (int current = 0; current <= arrBox.length - 1; current++) {
            boxStackingDP[current] = new HeightAndPreviousIndex(arrBox[current].getHeight(), HeightAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arrBox.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (arrBox[previous].getLength() > arrBox[current].getLength() && arrBox[previous].getBreadth() > arrBox[current].getBreadth()) {
                    if (boxStackingDP[previous].getHeight() + arrBox[current].getHeight() > boxStackingDP[current].getHeight()) {
                        boxStackingDP[current] = new HeightAndPreviousIndex(boxStackingDP[previous].getHeight() + arrBox[current].getHeight(), previous);
                    }
                }

            }
        }

        return boxStackingDP;
    }

    private Box[] getBoxArrayOfPossibleOrientation(int[][] arrLBH) {

        Set<Box> boxSet = new HashSet<>();
        for (int[] lbh : arrLBH) {
            boxSet.add(new Box(lbh[0], lbh[2], lbh[1]));
            boxSet.add(new Box(lbh[1], lbh[2], lbh[0]));
            boxSet.add(new Box(lbh[2], lbh[1], lbh[0]));
        }

        return boxSet.toArray(new Box[boxSet.size()]);
    }

    private class Box {

        private final int height;
        private final int length;
        private final int breadth;

        Box(int height, int length, int breadth) {
            this.height = height;
            this.length = length >= breadth ? length : breadth;
            this.breadth = length >= breadth ? breadth : length;
        }

        int getHeight() {
            return height;
        }

        int getLength() {
            return length;
        }

        int getBreadth() {
            return breadth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Box that = (Box) o;
            return getHeight() == that.getHeight() && getLength() == that.getLength() && getBreadth() == that.getBreadth();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getHeight(), getLength(), getBreadth());
        }

    }

    private class HeightAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private final int height;
        private final int previousIndex;

        HeightAndPreviousIndex(int height, int previousIndex) {
            this.height = height;
            this.previousIndex = previousIndex;
        }

        int getHeight() {
            return height;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
