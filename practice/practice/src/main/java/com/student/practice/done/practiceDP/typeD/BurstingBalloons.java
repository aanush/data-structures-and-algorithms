package com.student.practice.done.practiceDP.typeD;

public class BurstingBalloons {

    public static void main(String[] args) {
        BurstingBalloons app = new BurstingBalloons();
        int[] arr = new int[]{3, 1, 5, 8};
        Burst[][] burstDP = app.getBurstDP3(arr);
        app.getPrintBurstingBalloonsFromBurstDP1(arr, burstDP);
    }

    private void getPrintBurstingBalloonsFromBurstDP1(int[] arr, Burst[][] burstDP) {
        getPrintBurstingBalloonsRecursive(arr, burstDP, 0, arr.length - 1);
    }

    private void getPrintBurstingBalloonsRecursive(int[] arr, Burst[][] burstDP, int start, int last) {
        if (0 <= start && start <= last && last <= arr.length - 1) {
            int lastBurstingBalloonIndex = burstDP[start][last].getLastBurstingBalloonIndex();
            if (start <= lastBurstingBalloonIndex && lastBurstingBalloonIndex <= last) {
                System.out.println(arr[lastBurstingBalloonIndex]);
                // lastBurstingBalloonIndex should be the last among balloon index belongs to [start, last]
                // lastBurstingBalloonIndex separates the bursting balloon into
                // two independent subArray [start, lastBurstingBalloonIndex - 1] and [lastBurstingBalloonIndex + 1, last]
                // now wrt lastBurstingBalloonIndex there could be two secondLastBurstingBalloonIndex
                // one such secondLastBurstingBalloonIndex belongs to [start, lastBurstingBalloonIndex + 1]
                // one such secondLastBurstingBalloonIndex belongs to [lastBurstingBalloonIndex + 1, last]
                getPrintBurstingBalloonsRecursive(arr, burstDP, start, lastBurstingBalloonIndex - 1);
                getPrintBurstingBalloonsRecursive(arr, burstDP, lastBurstingBalloonIndex + 1, last);
            }
        }
    }

    private Burst[][] getBurstDP3(int[] arr) {

        Burst[][] burstDP = new Burst[arr.length][arr.length];

        // length == 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                // start == lastBurstingBalloonIndex
                int value = arr[start];
                if (start - 1 >= 0) {
                    // start - 1 has not bursted and must contribute to the product
                    value = value * arr[start - 1];
                }
                if (start + 1 <= arr.length - 1) {
                    // start + 1 has not bursted and must contribute to the product
                    value = value * arr[start + 1];
                }
                burstDP[start][last] = new Burst(start, value);
            }
        }

        // 1 <= length <= arr.length - 1
        for (int length = 1; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                int maximumValue = -1;
                int lastBurstingBalloonIndex = -1;

                // try all balloonIndex belongs to [start, last]
                // and find the balloonIndex which must be last to burst to maximize the burst value for [start, last]
                // if tempLastBurstingBalloonIndex is balloonIndex last to burst it means
                // balloonIndex belongs to [start, tempLastBurstingBalloonIndex - 1] has already bursted
                // balloonIndex belongs to [tempLastBurstingBalloonIndex + 1, last] has already bursted
                // balloonIndex start - 1 has not bursted and is at immediate left to tempLastBurstingBalloonIndex now and must contribute to the product
                // balloonIndex last + 1 has not bursted and is at immediate right to tempLastBurstingBalloonIndex now  and must contribute to the product
                for (int tempLastBurstingBalloonIndex = start; tempLastBurstingBalloonIndex <= last; tempLastBurstingBalloonIndex++) {

                    // contribution of current bursting balloon
                    int tempValue = arr[tempLastBurstingBalloonIndex];
                    if (start - 1 >= 0) {
                        // start - 1 has not bursted and must contribute to the product
                        // immediate left now among non bursted balloons
                        tempValue = tempValue * arr[start - 1];
                    }
                    if (last + 1 <= arr.length - 1) {
                        // last + 1 has not bursted and must contribute to the product
                        // immediate right now among non bursted balloons
                        tempValue = tempValue * arr[last + 1];
                    }

                    // contribution of already bursted balloons belongs to [start, tempLastBurstingBalloonIndex - 1]
                    if (tempLastBurstingBalloonIndex - 1 >= start) {
                        tempValue = tempValue + burstDP[start][tempLastBurstingBalloonIndex - 1].getMaximumValue();
                    }

                    // contribution of already bursted balloons belongs to [tempLastBurstingBalloonIndex + 1, last]
                    if (tempLastBurstingBalloonIndex + 1 <= last) {
                        tempValue = tempValue + burstDP[tempLastBurstingBalloonIndex + 1][last].getMaximumValue();
                    }

                    // if maximum
                    if (tempValue > maximumValue) {
                        maximumValue = tempValue;
                        lastBurstingBalloonIndex = tempLastBurstingBalloonIndex;
                    }
                }

                burstDP[start][last] = new Burst(lastBurstingBalloonIndex, maximumValue);
            }
        }

        return burstDP;
    }

    private class Burst {

        private final int lastBurstingBalloonIndex;
        private final int maximumValue;

        Burst(int lastBurstingBalloonIndex, int maximumValue) {
            this.lastBurstingBalloonIndex = lastBurstingBalloonIndex;
            this.maximumValue = maximumValue;
        }

        int getLastBurstingBalloonIndex() {
            return lastBurstingBalloonIndex;
        }

        int getMaximumValue() {
            return maximumValue;
        }

    }

}
