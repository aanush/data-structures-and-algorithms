package com.student.practice.done.practiceDP.typeD;

import java.util.function.BiFunction;

public class BooleanParenthesization {

    public static void main(String[] args) {
        BooleanParenthesization app = new BooleanParenthesization();
        boolean[] arr = new boolean[]{true, true, false, true};
        Parenthesizer or = (a, b) -> a || b;
        Parenthesizer and = (a, b) -> a && b;
        Parenthesizer xor = (a, b) -> a ^ b;
        Parenthesizer[] parenthesizer = new Parenthesizer[]{or, and, xor};
        Parenthesization[][] parenthesizationDP = app.getParenthesizationDP(arr, parenthesizer);
        System.out.println(" waysT = " + parenthesizationDP[0][arr.length - 1].getWaysT());
        System.out.println(" waysF = " + parenthesizationDP[0][arr.length - 1].getWaysF());
    }

    private Parenthesization[][] getParenthesizationDP(boolean[] arr, Parenthesizer[] parenthesizer) {

        // arr : with start index = start; and with last index = last
        // parenthesizationDP[start][last].getWaysT() = possible ways to parenthesize the arr to get true
        // parenthesizationDP[start][last].getWaysF() = possible ways to parenthesize the arr to get false
        Parenthesization[][] parenthesizationDP = new Parenthesization[arr.length][arr.length];

        // length == 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                parenthesizationDP[start][last] = arr[start] ? new Parenthesization(1, 0) : new Parenthesization(0, 1);
            }
        }

        // length == 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                parenthesizationDP[start][last] = parenthesizer[start].apply(arr[start], arr[last]) ? new Parenthesization(1, 0) : new Parenthesization(0, 1);
            }
        }

        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                int waysT = 0;
                int waysF = 0;

                for (int pivot = start; pivot <= last - 1; pivot++) {

                    if (parenthesizer[pivot].apply(true, true)) {
                        waysT = waysT + parenthesizationDP[start][pivot].getWaysT() * parenthesizationDP[pivot + 1][last].getWaysT();
                    } else {
                        waysF = waysF + parenthesizationDP[start][pivot].getWaysT() * parenthesizationDP[pivot + 1][last].getWaysT();
                    }

                    if (parenthesizer[pivot].apply(true, false)) {
                        waysT = waysT + parenthesizationDP[start][pivot].getWaysT() * parenthesizationDP[pivot + 1][last].getWaysF();
                    } else {
                        waysF = waysF + parenthesizationDP[start][pivot].getWaysT() * parenthesizationDP[pivot + 1][last].getWaysF();
                    }


                    if (parenthesizer[pivot].apply(false, true)) {
                        waysT = waysT + parenthesizationDP[start][pivot].getWaysF() * parenthesizationDP[pivot + 1][last].getWaysT();
                    } else {
                        waysF = waysF + parenthesizationDP[start][pivot].getWaysF() * parenthesizationDP[pivot + 1][last].getWaysT();
                    }

                    if (parenthesizer[pivot].apply(false, false)) {
                        waysT = waysT + parenthesizationDP[start][pivot].getWaysF() * parenthesizationDP[pivot + 1][last].getWaysF();
                    } else {
                        waysF = waysF + parenthesizationDP[start][pivot].getWaysF() * parenthesizationDP[pivot + 1][last].getWaysF();
                    }

                }

                parenthesizationDP[start][last] = new Parenthesization(waysT, waysF);
            }
        }

        return parenthesizationDP;
    }

    private interface Parenthesizer extends BiFunction<Boolean, Boolean, Boolean> {
    }

    private class Parenthesization {

        private final int waysT;
        private final int waysF;

        Parenthesization(int waysT, int waysF) {
            this.waysT = waysT;
            this.waysF = waysF;
        }

        int getWaysT() {
            return waysT;
        }

        int getWaysF() {
            return waysF;
        }

    }

}
