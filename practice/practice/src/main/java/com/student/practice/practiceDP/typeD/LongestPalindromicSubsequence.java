package com.student.practice.practiceDP.typeD;

import java.util.Stack;

public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        LongestPalindromicSubsequence app = new LongestPalindromicSubsequence();
        int[] arr = new int[]{1, 5, 2, 3, 9, 4, 3, 5, 2, 1};
        int[][] lpsLengthDP = app.getLPSLengthDP2(arr);
        app.getPrintLongestPalindromicSubsequenceFromLPSLengthDP1(arr, lpsLengthDP);
    }

    private void getPrintLongestPalindromicSubsequenceFromLPSLengthDP1(int[] arr, int[][] lpsLengthDP) {

        int start = 0;
        int last = arr.length - 1;
        Stack<Integer> stack = new Stack<>();
        while (start <= last) {
            // lpsLengthDP[start][last] == 2 + lpsLengthDP[start + 1][last - 1]
            // is not good to check because it will fail at start == last so
            // better check arr[start] == arr[last]
            if (arr[start] == arr[last]) {

                System.out.println(arr[start]);
                if (start != last) {
                    stack.push(arr[last]);
                }
                start = start + 1;
                last = last - 1;
                continue;
            }
            if (lpsLengthDP[start][last] == lpsLengthDP[start + 1][last]) {
                start = start + 1;
                continue;
            }
            if (lpsLengthDP[start][last] == lpsLengthDP[start][last - 1]) {
                last = last - 1;
                continue;
            }
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private int[][] getLPSLengthDP2(int[] arr) {


        int[][] lpsLengthDP = new int[arr.length][arr.length];

        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                lpsLengthDP[start][last] = 1;
            }
        }

        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                lpsLengthDP[start][last] = arr[start] == arr[last] ? 2 : 1;
            }
        }


        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                if (arr[start] == arr[last]) {
                    lpsLengthDP[start][last] = lpsLengthDP[start + 1][last - 1] + 2;
                } else {
                    lpsLengthDP[start][last] = Integer.max(lpsLengthDP[start + 1][last], lpsLengthDP[start][last - 1]);
                }
            }
        }

        return lpsLengthDP;
    }

}
