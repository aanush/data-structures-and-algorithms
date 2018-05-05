package com.student.practice.practiceDP.typeB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LongestGeometricProgressionSet {

    public static void main(String[] args) {
        LongestGeometricProgressionSet app = new LongestGeometricProgressionSet();
        int[] arr = new int[]{0, 1, 1, 1, 0, 0, 3, 9, 27, 2, 4, 0, 81, 8, 16, 0, 243, 32, 64, 729, 2187, 0, 128, 256, 0, 512};
        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(list, new Random());
        arr = list.stream().mapToInt(value -> value).toArray();
        app.getPrintLongestGeometricProgressionSet(arr);
    }

    private void getPrintLongestGeometricProgressionSet(int[] arr) {

        // minimum length of geometric progression set could be 2
        if (arr == null || arr.length <= 1) {
            return;
        }

        // sort ascending and filter out 0
        int[] sorted = Arrays.stream(arr)
                .sorted()
                .filter(value -> value != 0)
                .toArray();

        // for number from index = 0 to index = arr.length - 1
        // gpLengthDP[x][y] + 1 is the length of the longest gp set
        // which must include arr[x] as the second last number and
        // which must include arr[y] as the last number
        int[][] gpLengthDP = getLongestGeometricProgressionSetDP2(sorted);

        int len = 1;
        int rto = 0;
        int num = sorted[0];
        for (int x = 0; x <= sorted.length - 1; x++) {
            for (int y = x + 1; y <= sorted.length - 1; y++) {
                if (gpLengthDP[x][y] + 1 > len) {
                    len = gpLengthDP[x][y] + 1;
                    rto = sorted[y] / sorted[x];
                    num = sorted[y];
                }
            }
        }

        if (len >= 2 && rto != 0) {
            while (len >= 1) {
                System.out.println(num);
                num = num / rto;
                len = len - 1;
            }
        }
    }

    private int[][] getLongestGeometricProgressionSetDP2(int[] arr) {

        // arr is sorted ascending and 0 is filtered out

        // for number from index = 0 to index = arr.length - 1
        // gpLengthDP[x][y] + 1 is the length of the longest gp set
        // which must include arr[x] as the second last number and
        // which must include arr[y] as the last number
        int[][] gpLengthDP = new int[arr.length][arr.length];

        // column : x == 0; second last number is arr[0]
        for (int x = 0; x <= 0; x++) {
            for (int y = x + 1; y <= arr.length - 1; y++) {
                if (arr[y] % arr[x] == 0) {
                    gpLengthDP[x][y] = 1;
                }
            }
        }

        // column : x >= 1;
        // try for all x : arr[x] as the second last number of the set
        // try for all y : arr[y] as the last number of the set (y > x)
        for (int x = 1; x <= arr.length - 2; x++) {
            int y = x + 1;
            int w = x - 1;
            // try for all w : arr[w], arr[x], arr[y] in gp
            while (w >= 0 && y <= arr.length - 1) {
                if (arr[y] % arr[x] == 0) {
                    // arr[x], arr[y] in gp
                    gpLengthDP[x][y] = 1;
                    if (arr[w] * arr[y] == arr[x] * arr[x]) {
                        // arr[w], arr[x], arr[y] in gp
                        // length of gp set with arr[x] as second last and arr[y] as last
                        // is equal to one more than the
                        // length of gp set with arr[w] as second last and arr[x] as last
                        // which has already been computed
                        gpLengthDP[x][y] = gpLengthDP[w][x] + 1;
                        y = y + 1;
                        w = w - 1;
                    } else if (arr[w] * arr[y] > arr[x] * arr[x]) {
                        // as arr is sorted ascending look for w - 1
                        w = w - 1;
                    } else if (arr[w] * arr[y] < arr[x] * arr[x]) {
                        // as arr is sorted ascending look for y + 1
                        y = y + 1;
                    }
                } else {
                    // arr[x], arr[y] not in gp
                    y = y + 1;
                }
            }
            while (y <= arr.length - 1) {
                if (arr[y] % arr[x] == 0) {
                    // arr[x], arr[y] in gp
                    gpLengthDP[x][y] = 1;
                }
                y = y + 1;
            }
        }

        return gpLengthDP;
    }

}
