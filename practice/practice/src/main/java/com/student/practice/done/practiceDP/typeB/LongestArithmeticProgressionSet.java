package com.student.practice.done.practiceDP.typeB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LongestArithmeticProgressionSet {

    public static void main(String[] args) {
        LongestArithmeticProgressionSet app = new LongestArithmeticProgressionSet();
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 15, 16, 19, 20, 22, 25, 30, 35, 40, 45, 50};
        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(list, new Random());
        arr = list.stream().mapToInt(value -> value).toArray();
        app.getPrintLongestArithmeticProgressionSet(arr);
    }

    private void getPrintLongestArithmeticProgressionSet(int[] arr) {

        // minimum length of arithmetic progression set could be 2
        if (arr == null || arr.length <= 1) {
            return;
        }

        // sort ascending and don't filter out 0
        int[] sorted = Arrays.stream(arr)
                .sorted()
                .toArray();

        // for number from index = 0 to index = arr.length - 1
        // apLengthDP[x][y] + 1 is the length of the longest ap set
        // which must include arr[x] as the second last number and
        // which must include arr[y] as the last number
        int[][] apLengthDP = getLongestArithmeticProgressionSetDP2(sorted);

        int len = 1;
        int dif = 0;
        int num = sorted[0];
        for (int x = 0; x <= sorted.length - 1; x++) {
            for (int y = x + 1; y <= sorted.length - 1; y++) {
                if (apLengthDP[x][y] + 1 > len) {
                    len = apLengthDP[x][y] + 1;
                    dif = sorted[y] - sorted[x];
                    num = sorted[y];
                }
            }
        }

        if (len >= 2) {
            while (len >= 1) {
                System.out.println(num);
                num = num - dif;
                len = len - 1;
            }
        }
    }

    private int[][] getLongestArithmeticProgressionSetDP2(int[] arr) {

        // arr is sorted ascending and 0 is not filtered out

        // for number from index = 0 to index = arr.length - 1
        // apLengthDP[x][y] + 1 is the length of the longest ap set
        // which must include arr[x] as the second last number and
        // which must include arr[y] as the last number
        int[][] apLengthDP = new int[arr.length][arr.length];

        // column : x == 0; second last number is arr[0]
        for (int x = 0; x <= 0; x++) {
            for (int y = x + 1; y <= arr.length - 1; y++) {
                apLengthDP[x][y] = 1;
            }
        }

        // column : x >= 1;
        // try for all x : arr[x] as the second last number of the set
        // try for all y : arr[y] as the last number of the set (y > x)
        for (int x = 1; x <= arr.length - 2; x++) {
            int y = x + 1;
            int w = x - 1;
            // try for all w : arr[w], arr[x], arr[y] in ap
            while (w >= 0 && y <= arr.length - 1) {
                // arr[x], arr[y] in ap
                apLengthDP[x][y] = 1;
                if (arr[w] + arr[y] == arr[x] + arr[x]) {
                    // arr[w], arr[x], arr[y] in ap
                    // length of ap set with arr[x] as second last and arr[y] as last
                    // is equal to one more than the
                    // length of ap set with arr[w] as second last and arr[x] as last
                    // which has already been computed
                    apLengthDP[x][y] = apLengthDP[w][x] + 1;
                    y = y + 1;
                    w = w - 1;
                } else if (arr[w] + arr[y] > arr[x] + arr[x]) {
                    // as arr is sorted ascending look for w - 1
                    w = w - 1;
                } else if (arr[w] + arr[y] < arr[x] + arr[x]) {
                    // as arr is sorted ascending look for y + 1
                    y = y + 1;
                }
            }
            while (y <= arr.length - 1) {
                // arr[x], arr[y] in ap
                apLengthDP[x][y] = 1;
                y = y + 1;
            }
        }

        return apLengthDP;
    }

}
