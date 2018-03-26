package com.student.practice.practiceDP.typeC;

public class DiceThrowSumPossibleWays {

    public static void main(String[] args) {
        DiceThrowSumPossibleWays app = new DiceThrowSumPossibleWays();
        int[] dice = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int throwCount = 5;
        int sum = 26;
        int[][] possibleWaysDP = app.getPossibleWaysDP2(dice, throwCount, sum);
        System.out.println(possibleWaysDP[throwCount - 1][sum]);
    }

    private int[][] getPossibleWaysDP2(int[] dice, int throwCount, int sum) {

        int[][] possibleWaysDP = new int[throwCount][sum + 1];

        // s == 0
        for (int throwCountIndex = 0; throwCountIndex <= throwCount - 1; throwCountIndex++) {
            possibleWaysDP[throwCountIndex][0] = 0;
        }

        // throwCountIndex == 0
        for (int s = 1; s <= sum; s++) {
            possibleWaysDP[0][s] = dice[0] <= s && s <= dice[dice.length - 1] ? 1 : 0;
        }

        for (int throwCountIndex = 1; throwCountIndex <= throwCount - 1; throwCountIndex++) {
            for (int s = 1; s <= sum; s++) {

                int ways = 0;
                for (int temp = 0; temp <= dice.length - 1; temp++) {
                    if (0 <= s - dice[temp] && s - dice[temp] <= sum) {
                        ways = ways + possibleWaysDP[throwCountIndex - 1][s - dice[temp]];
                    }

                }
                possibleWaysDP[throwCountIndex][s] = ways;

            }
        }

        return possibleWaysDP;
    }

}
