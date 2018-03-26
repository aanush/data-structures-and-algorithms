package com.student.practice.practiceDP.typeD;

public class GamePlanning {

    public static void main(String[] args) {
        GamePlanning app = new GamePlanning();
        int[] arr = new int[]{3, 9, 1, 2};
        Score score = app.getScoreDP2(arr)[0][arr.length - 1];
        System.out.println("beginner = " + score.getScoreAsBeginner() + ", follower = " + score.getScoreAsFollower());
    }

    private Score[][] getScoreDP2(int[] arr) {

        Score[][] scoreDP = new Score[arr.length][arr.length];

        // diagonal : length == 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                scoreDP[start][last] = new Score(arr[start], 0);
            }
        }

        // diagonal : length == 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                int max = Integer.max(arr[start], arr[last]);
                int min = Integer.min(arr[start], arr[last]);
                scoreDP[start][last] = new Score(max, min);
            }
        }

        // diagonal : length >= 2
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {

                // case 1 : if current beginner selected score at start index
                int beginnerScore1 = arr[start] + scoreDP[start + 1][last].getScoreAsFollower();
                int followerScore1 = scoreDP[start + 1][last].getScoreAsBeginner();

                // case 2 : if current beginner selected score at last index
                int beginnerScore2 = arr[last] + scoreDP[start][last - 1].getScoreAsFollower();
                int followerScore2 = scoreDP[start][last - 1].getScoreAsBeginner();

                // the best of case 1 and case 2
                if (beginnerScore1 >= beginnerScore2) {
                    scoreDP[start][last] = new Score(beginnerScore1, followerScore1);
                } else {
                    scoreDP[start][last] = new Score(beginnerScore2, followerScore2);
                }

            }
        }

        return scoreDP;
    }

    private class Score {
        private final int scoreAsBeginner;
        private final int scoreAsFollower;

        Score(int scoreAsBeginner, int scoreAsFollower) {
            this.scoreAsBeginner = scoreAsBeginner;
            this.scoreAsFollower = scoreAsFollower;
        }

        int getScoreAsBeginner() {
            return scoreAsBeginner;
        }

        int getScoreAsFollower() {
            return scoreAsFollower;
        }
    }
}
