package com.student.practice.done.practiceDP.typeC;

public class EggDropping {

    public static void main(String[] args) {
        EggDropping app = new EggDropping();
        System.out.println("for attempt required = " + app.getAttemptDP2(2, 6)[2][6]);
    }

    private int[][] getAttemptDP2(int eggCount, int floorCount) {

        // egg = number of eggs to drop; floor = number of floors to decide; then
        // attemptDP[egg][floor] is the minimum attempt required in worst case
        // to find the floor from which the dropped egg will break or to conclude that the dropped egg will not break at all
        int[][] attemptDP = new int[eggCount + 1][floorCount + 1];

        // row : floor = 0
        for (int egg = 0; egg <= eggCount; egg++) {
            attemptDP[egg][0] = 0;
        }

        // column : egg = 0
        for (int floor = 1; floor <= floorCount; floor++) {
            attemptDP[0][floor] = 0;
        }

        // row : floor = 1
        for (int egg = 1; egg <= eggCount; egg++) {
            attemptDP[egg][1] = 1;
        }

        // column : egg = 1
        for (int floor = 2; floor <= floorCount; floor++) {
            attemptDP[1][floor] = floor;
        }

        //
        for (int egg = 2; egg <= eggCount; egg++) {
            for (int floor = 2; floor <= floorCount; floor++) {

                int attempt = Integer.MAX_VALUE;
                for (int temp = 1; temp <= floor; temp++) {
                    // if broken from floor = temp
                    int brokenFromCurrentFloor = 1 + attemptDP[egg - 1][temp - 1];
                    // if not broken from floor = temp
                    int notBrokenFromCurrentFloor = 1 + attemptDP[egg][floor - temp];
                    // minimum attempt in worst(ie maximum of broken or not broken) case
                    attempt = Integer.min(attempt, Integer.max(brokenFromCurrentFloor, notBrokenFromCurrentFloor));
                }
                attemptDP[egg][floor] = attempt;

            }
        }

        return attemptDP;
    }

}
