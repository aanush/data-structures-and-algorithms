package com.student.practice.practiceArray;

import java.util.Stack;

public class MaximumAreaRectangleInHistogram {

    public static void main(String[] args) {
        MaximumAreaRectangleInHistogram app = new MaximumAreaRectangleInHistogram();
        int[] histogram = new int[]{6, 2, 5, 4, 5, 1, 7, 6, 5, 4, 2, 1, 1, 1, 1, 1};
        app.getMaximumAreaRectangleInHistogram1(histogram);
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
