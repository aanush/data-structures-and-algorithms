package com.student.practice.practiceArray;

public class MinimumDistanceBetweenTwoNumberInArray {

    //https://www.youtube.com/watch?v=hoceGcqQczM&index=4&list=PLqM7alHXFySEQDk2MDfbwEdjd2svVJH9p
    public static void main(String[] args) {
        MinimumDistanceBetweenTwoNumberInArray app = new MinimumDistanceBetweenTwoNumberInArray();
        int[] arr = new int[]{8, 0, 0, 0, 8, 1, 8, 0, 1, 1, 4, 0, 0, 0, 8, 0, 0, 44, 0, 81, 0, 0, 8, 0, 0, 4};
        int[] min = app.getMinimumDistance(arr, 4, 8); //[17, 19]
    }

    private int[] getMinimumDistance(int[] arr, int a, int b) {
        final int noIndex = -1;

        int current = 0;
        int matched = noIndex;
        while (current <= arr.length - 1) {
            if (arr[current] == a || arr[current] == b) {
                matched = current;
                break;
            }
            current++;
        }

        if (matched == noIndex) {
            return null;
        }

        if (current == arr.length - 1) {
            return null;
        }

        current++;

        int start = 0;
        int end = arr.length;

        while (current <= arr.length - 1) {
            if (arr[current] == a || arr[current] == b) {
                if (arr[current] != arr[matched] && end - start > current - matched) {
                    start = matched;
                    end = current;
                }
                matched = current;
            }
            current++;
        }

        if (end - start >= arr.length - 0) {
            return null;
        }

        return new int[]{start, end};
    }

}
