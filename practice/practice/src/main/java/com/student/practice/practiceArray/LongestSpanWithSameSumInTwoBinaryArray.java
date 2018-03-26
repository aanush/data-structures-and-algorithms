package com.student.practice.practiceArray;

import java.util.Arrays;

public class LongestSpanWithSameSumInTwoBinaryArray {

    //https://www.youtube.com/watch?v=xtfj4-r_Ahs&list=PLqM7alHXFySEQDk2MDfbwEdjd2svVJH9p&index=1
    public static void main(String[] args) {
        LongestSpanWithSameSumInTwoBinaryArray app = new LongestSpanWithSameSumInTwoBinaryArray();
        int[] arr1 = new int[]{1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1};
        int[] arr2 = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0};
        int[] span = app.getSpan(arr1, arr2); // [1, 7]
    }

    private int[] getSpan(int[] arr1, int[] arr2) {
        final int length = Integer.min(arr1.length, arr2.length);

        final int noIndex = -1;
        int[] arrIndex = new int[2 * length + 1];
        Arrays.fill(arrIndex, noIndex);

        int spanStart = noIndex;
        int spanEnd = noIndex;
        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i <= length - 1; i++) {
            sum1 = sum1 + arr1[i];
            sum2 = sum2 + arr2[i];

            if (sum1 == sum2) {
                spanStart = 0;
                spanEnd = i;
            } else {
                int diff = sum1 - sum2 + length;
                //Assert.that(0 <= diff && diff <= 2*length, "Broken");

                if (arrIndex[diff] == noIndex) {
                    arrIndex[diff] = i;
                } else {
                    if (i - (arrIndex[diff] + 1) > spanEnd - spanStart) {
                        spanStart = arrIndex[diff] + 1;
                        spanEnd = i;
                    }
                }
            }
        }

        return new int[]{spanStart, spanEnd};
    }

}
