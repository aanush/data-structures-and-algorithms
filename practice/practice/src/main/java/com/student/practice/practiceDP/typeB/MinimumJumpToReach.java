package com.student.practice.practiceDP.typeB;

public class MinimumJumpToReach {

    public static void main(String[] args) {
        MinimumJumpToReach app = new MinimumJumpToReach();
        int[] arr = new int[]{2, 3, 1, 1, 2, 4, 2, 0, 1, 1};
        JumpAndPreviousIndex[] jumpDP = app.getJumpDP2(arr);
        app.getPrintMinimumJumpToReachFromJumpDP1(arr, jumpDP);
    }

    private void getPrintMinimumJumpToReachFromJumpDP1(int[] arr, JumpAndPreviousIndex[] jumpDP) {

        int jumpIndex = arr.length - 1;

        while (jumpIndex > JumpAndPreviousIndex.NO_INDEX) {
            System.out.println(jumpIndex);
            jumpIndex = jumpDP[jumpIndex].getPreviousIndex();
        }

    }

    private JumpAndPreviousIndex[] getJumpDP2(int[] arr) {

        JumpAndPreviousIndex[] jumpDP = new JumpAndPreviousIndex[arr.length];

        for (int current = 0; current <= arr.length - 1; current++) {
            jumpDP[current] = new JumpAndPreviousIndex(Integer.MAX_VALUE, JumpAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (previous + arr[previous] >= current) {
                    if (jumpDP[previous].getJump() + 1 < jumpDP[current].getJump()) {
                        jumpDP[current] = new JumpAndPreviousIndex(jumpDP[previous].getJump() + 1, previous);
                    }
                }

            }
        }

        return jumpDP;
    }

    private class JumpAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private final int jump;
        private final int previousIndex;

        JumpAndPreviousIndex(int jump, int previousIndex) {
            this.jump = jump;
            this.previousIndex = previousIndex;
        }

        int getJump() {
            return jump;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
