package com.student.practice.practiceDP.typeC;

import java.util.List;

public class CoinChangePossibleWays {

    public static void main(String[] args) {
        CoinChangePossibleWays app = new CoinChangePossibleWays();
        app.getPossibleWays(new int[]{1, 2, 3}, 5);
    }

    private void getPrintPossibleCoinChange(int[] arr, int sum) {
        //todo print possible ways
    }

    private List<List<Integer>> getPossibleCoinChangeFromPossibleWays(int[] arr, int sum, int[][] possibleWays) {
        return null;
    }

    private int[][] getPossibleWays(int[] arr, int sum) {

        int[][] possibleWays = new int[arr.length][sum + 1];

        final int noWays = 0;

        //complete the row for sum : s  = 0
        for (int current = 0; current <= arr.length - 1; current++) {
            possibleWays[current][0] = 1;
        }

        //complete the column for index : current = 0;
        for (int s = 1; s <= sum; s++) {
            if (s % arr[0] == 0) {
                possibleWays[0][s] = 1;
            } else {
                possibleWays[0][s] = noWays;
            }
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int s = 1; s <= sum; s++) {
                int excludingCurrent = possibleWays[current - 1][s];
                int includingCurrent = noWays;
                if (s - arr[current] >= 0) {
                    includingCurrent = possibleWays[current][s - arr[current]];
                }
                possibleWays[current][s] = excludingCurrent + includingCurrent;
            }
        }

        return possibleWays;
    }

}
