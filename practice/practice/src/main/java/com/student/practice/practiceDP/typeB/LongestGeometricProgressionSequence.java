package com.student.practice.practiceDP.typeB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestGeometricProgressionSequence {

    public static void main(String[] args) {
        LongestGeometricProgressionSequence app = new LongestGeometricProgressionSequence();
        int[] arr = new int[]{0, 1, 1, 1, 0, 0, 3, 9, 27, 2, 4, 0, 81, 8, 16, 0, 243, 32, 64, 729, 2187, 0, 128, 256, 0, 512};
        app.getPrintLongestGeometricProgressionSequence(arr);
    }

    private void getPrintLongestGeometricProgressionSequence(int[] arr) {

        // minimum length of geometric progression sequence could be 2
        if (arr == null || arr.length <= 1) {
            return;
        }

        Integer[] ratio = getAllIntegerRatio(arr);
        Map<Integer, Integer> ratioMap = getRatioIndexMap(ratio);

        // for number from index = 0 to index = x
        // gpLengthDP[x][y] + 1 is the length of the longest gp sequence
        // which must include arr[x] as the last number and
        // which ratio = ratio[y]
        int[][] gpLengthDP = getLongestGeometricProgressionSequenceDP2(arr, ratioMap);

        int len = 1;
        int rto = 0;
        int num = arr[0];
        for (int x = 0; x <= gpLengthDP.length - 1; x++) {
            for (int y = 0; y <= gpLengthDP[x].length - 1; y++) {
                if (gpLengthDP[x][y] + 1 > len) {
                    len = gpLengthDP[x][y] + 1;
                    rto = ratio[y];
                    num = arr[x];
                }
            }
        }

        if (len >= 2 && rto != 0) {
            while (len >= 1) {
                System.out.println(num);
                num = num / rto;
                len = len - 1;
            }
        }
    }

    private int[][] getLongestGeometricProgressionSequenceDP2(int[] arr, Map<Integer, Integer> ratioMap) {

        // for number from index = 0 to index = x
        // gpLengthDP[x][y] + 1 is the length of the longest gp sequence
        // which must include arr[x] as the last number and
        // which ratio = ratio[y]
        int[][] gpLengthDP = new int[arr.length][ratioMap.size()];

        // by default;
        // gpLengthDP[x][y] = 0; ie..
        // gpLengthDP[x][y] + 1 = 1 = default length of the longest gp sequence

        // column : x >= 1; number from arr[0] to arr[x]
        // try for all x : arr[x] as the last number of the sequence
        for (int x = 1; x <= arr.length - 1; x++) {
            // try for all k : arr[k] as the second last number of the sequence
            for (int k = 0; k <= x - 1; k++) {
                if (arr[k] != 0 && arr[x] % arr[k] == 0) {
                    // arr[k] could be second last number with ratio : r = arr[x] / arr[k]
                    int r = arr[x] / arr[k];
                    // get index : y from ratio : r
                    int y = ratioMap.get(r);
                    gpLengthDP[x][y] = gpLengthDP[k][y] + 1;
                }
            }
        }

        return gpLengthDP;
    }

    private Integer[] getAllIntegerRatio(int[] arr) {
        List<Integer> allRatio = new ArrayList<>();
        for (int x = 1; x <= arr.length - 1; x++) {
            for (int k = 0; k <= x - 1; k++) {
                if (arr[k] != 0 && arr[x] % arr[k] == 0) {
                    allRatio.add(arr[x] / arr[k]);
                }
            }
        }
        return allRatio
                .stream()
                .sorted()
                .distinct()
                .toArray(Integer[]::new);
    }

    private Map<Integer, Integer> getRatioIndexMap(Integer[] ratio) {
        HashMap<Integer, Integer> ratioMap = new HashMap<>();
        for (int i = 0; i <= ratio.length - 1; i++) {
            ratioMap.put(ratio[i], i);
        }
        return ratioMap;
    }

}
