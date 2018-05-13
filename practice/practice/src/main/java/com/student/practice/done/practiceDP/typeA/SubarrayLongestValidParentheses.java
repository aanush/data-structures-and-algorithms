package com.student.practice.done.practiceDP.typeA;

public class SubarrayLongestValidParentheses {

    public static void main(String[] args) {
        SubarrayLongestValidParentheses app = new SubarrayLongestValidParentheses();
        String s = "))()())((()((())()()))(";
        app.getPrintLongestValidParentheses(s);
    }

    public void getPrintLongestValidParentheses(String s) {
        if (s.length() <= 1) {
            return;
        }
        String[] arr = s.split("");
        int[] lengthDP = getLongestValidParenthesesLengthDP(arr);
        int max = 0;
        for (int x = 0; x <= arr.length - 1; x++) {
            if (lengthDP[x] > lengthDP[max]) {
                max = x;
            }
        }
        for (int x = max - lengthDP[max] + 1; x <= max; x++) {
            System.out.print(arr[x]);
        }
    }

    private int[] getLongestValidParenthesesLengthDP(String[] arr) {

        final String opening = "(";
        final String closing = ")";

        // lengthDP[x] is the length of the longest valid parenthesis that includes arr[x] as the last one
        int[] lengthDP = new int[arr.length];

        // lengthDP[0] = 0;
        lengthDP[1] = arr[0].equals(opening) && arr[1].equals(closing) ? 2 : 0;

        for (int x = 2; x <= arr.length - 1; x++) {
            // parenthesis that includes opening as the last one
            // could not be a valid parenthesis
            // if (arr[x].equals(opening)) {
            //     lengthDP[x] = 0;
            // }
            //
            // parenthesis that includes closing as the last one
            // could be a valid parenthesis
            // if there is a corresponding opening such that
            // the parenthesis between the opening and the closing is a valid parenthesis
            if (arr[x].equals(closing)) {
                if (arr[x - 1].equals(opening)) {
                    lengthDP[x] = lengthDP[x - 2] + 2;
                }
                if (arr[x - 1].equals(closing)) {
                    int w = x - 1 - lengthDP[x - 1];
                    // if (w < 0) {
                    //     lengthDP[x] = 0;
                    // }
                    if (w == 0) {
                        lengthDP[x] = arr[w].equals(opening) ? lengthDP[x - 1] + 2 : 0;
                    }
                    if (w >= 1) {
                        lengthDP[x] = arr[w].equals(opening) ? lengthDP[x - 1] + 2 + lengthDP[w - 1] : 0;
                    }
                }
            }
            //
        }

        return lengthDP;
    }

    // study more
    // https://leetcode.com/problems/longest-valid-parentheses/description/

}
