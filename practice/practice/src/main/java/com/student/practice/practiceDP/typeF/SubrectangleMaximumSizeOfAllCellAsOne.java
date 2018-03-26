package com.student.practice.practiceDP.typeF;

import java.util.Stack;

public class SubrectangleMaximumSizeOfAllCellAsOne {

    public static void main(String[] args) {
        SubrectangleMaximumSizeOfAllCellAsOne app = new SubrectangleMaximumSizeOfAllCellAsOne();
        int[] column1 = new int[]{1, 1, 0, 0};
        int[] column2 = new int[]{0, 0, 1, 0};
        int[] column3 = new int[]{0, 1, 1, 1};
        int[] column4 = new int[]{1, 1, 1, 1};
        int[] column5 = new int[]{1, 0, 1, 1};
        int[] column6 = new int[]{1, 1, 1, 1};
        int[][] rect0 = new int[][]{column1, column2, column3, column4, column5, column6};
        app.getPrintSubrectangleMaximumSizeOfAllCellAsOne2(rect0);
    }

    private void getPrintSubrectangleMaximumSizeOfAllCellAsOne2(int[][] rect) {
        int x1Max = -1;
        int x2Max = -1;
        int y1Max = -1;
        int y2Max = -1;
        int maximumArea = 0;

        int[] histogram = new int[rect[0].length];

        for (int x = 0; x <= rect.length - 1; x++) {

            for (int y = 0; y <= histogram.length - 1; y++) {
                histogram[y] = rect[x][y] == 1 ? histogram[y] + 1 : 0;
            }

            MaximumAreaRectangle maximumAreaRectangle = getMaximumAreaRectangleInHistogram1(histogram);
            if (maximumAreaRectangle.getArea() > maximumArea) {
                maximumArea = maximumAreaRectangle.getArea();
                y1Max = maximumAreaRectangle.getStartIndex();
                y2Max = maximumAreaRectangle.getLastIndex();
                int length = y2Max - y1Max + 1;
                int height = maximumArea / length;
                x2Max = x;
                x1Max = x2Max - height + 1;
            }

        }

        System.out.println("maximum sum = " + maximumArea);
        System.out.println("x1Max = " + x1Max);
        System.out.println("x2Max = " + x2Max);
        System.out.println("y1Max = " + y1Max);
        System.out.println("y2Max = " + y2Max);
    }

    private MaximumAreaRectangle getMaximumAreaRectangleInHistogram1(int[] histogram) {
        Stack<Integer> indexStack = new Stack<>();
        indexStack.push(0);
        int maximumArea = histogram[0];
        int startIndex = 0;
        int lastIndex = 0;
        int upstairsIndex = 1;

        while (upstairsIndex <= histogram.length - 1) {
            while (upstairsIndex <= histogram.length - 1 && (indexStack.isEmpty() || histogram[indexStack.peek()] <= histogram[upstairsIndex])) {
                indexStack.push(upstairsIndex);
                upstairsIndex = upstairsIndex + 1;
            }
            while (!indexStack.isEmpty() && (upstairsIndex > histogram.length - 1 || histogram[indexStack.peek()] > histogram[upstairsIndex])) {
                int heightIndex = indexStack.pop();
                int height = histogram[heightIndex];
                int downstairsIndex = indexStack.isEmpty() ? 0 : indexStack.peek() + 1;
                int length = upstairsIndex - downstairsIndex;
                int area = height * length;
                if (area > maximumArea) {
                    maximumArea = area;
                    startIndex = downstairsIndex;
                    lastIndex = upstairsIndex - 1;
                }
            }
        }

        return new MaximumAreaRectangle(maximumArea, startIndex, lastIndex);
    }

    private class MaximumAreaRectangle {

        private final int area;
        private final int startIndex;
        private final int lastIndex;

        MaximumAreaRectangle(int area, int startIndex, int lastIndex) {
            this.area = area;
            this.startIndex = startIndex;
            this.lastIndex = lastIndex;
        }

        int getArea() {
            return area;
        }

        int getStartIndex() {
            return startIndex;
        }

        int getLastIndex() {
            return lastIndex;
        }

    }

}
