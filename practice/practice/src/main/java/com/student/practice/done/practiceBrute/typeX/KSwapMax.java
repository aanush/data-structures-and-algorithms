package com.student.practice.done.practiceBrute.typeX;

import java.util.Arrays;

public class KSwapMax {

    public static void main(String[] args) {
        KSwapMax app = new KSwapMax();
        int[] arr = {0, 9, 8, 7, 1, 2, 3, 4, 5, 6};
        int k = 4;
        long max = app.swapk(arr, k);
        System.out.println(max);
    }

    private long swapk(int[] arr, int k) {
        long max = val(arr);
        for (int x = 0; x <= arr.length - 1; x++) {
            long cur = swapk(arr, x, k, max);
            if (cur > max) {
                max = cur;
            }
        }
        return max;
    }

    private long swapk(int[] arr, int x, int k, long num) {
        long max = num;
        if (k > 0) {
            for (int y = x; y <= arr.length - 1; y++) {
                if (arr[x] < arr[y]) {
                    // going from node to child node
                    swap1(arr, x, y);
                    long cur = val(arr);
                    if (cur > max) {
                        max = cur;
                    }
                    // dfs at child node
                    cur = swapk(arr, x + 1, k - 1, max);
                    if (cur > max) {
                        max = cur;
                    }
                    // going to node from child node
                    swap1(arr, y, x);
                }
            }
        }
        return max;
    }

    private void swap1(int[] arr, int x, int y) {
        int arrx = arr[x];
        int arry = arr[y];
        arr[x] = arry;
        arr[y] = arrx;
    }

    private long val(int[] arr) {
        return Arrays.stream(arr).mapToLong(a -> a).reduce((a, b) -> (10 * a) + b).orElse(0);
    }

}
