package com.student.practice.practiceDP.typeD;

import java.util.HashSet;
import java.util.Set;

public class PalindromePartitionMinCount {


    public static void main(String[] args) {
        PalindromePartitionMinCount app = new PalindromePartitionMinCount();
        //int[] arr = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,     4, 4, 2, 2, 2, 4, 4,    4};
        int[] arr = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 2, 2, 2, 4, 4, 2};
        Set<Integer>[] partitionIndexSetDP2 = app.getPartitionIndexSetDP2(arr);
        System.out.println(partitionIndexSetDP2[partitionIndexSetDP2.length - 1]);
        Set<Integer>[][] partitionIndexSetDP3 = app.getPartitionIndexSetDP3(arr);
        System.out.println(partitionIndexSetDP3[0][partitionIndexSetDP3.length - 1]);
    }

    private Set<Integer>[] getPartitionIndexSetDP2(int[] arr) {

        boolean[][] palindromeDP = getPalindromeDP2(arr);

        // partitionIndexSetDP[last] is a set of partition index
        // for an array with start index = 0 and last index = last
        Set<Integer>[] partitionIndexSetDP = new HashSet[arr.length];

        partitionIndexSetDP[0] = new HashSet<>();

        partitionIndexSetDP[1] = new HashSet<>();
        if (!palindromeDP[0][1]) {
            partitionIndexSetDP[1].add(0);
        }

        for (int last = 2; last <= arr.length - 1; last++) {
            Set<Integer> partitionSet = new HashSet<>();
            if (!palindromeDP[0][last]) {
                int minPartitionCount = last;
                int newPartitionIndex = last - 1;
                for (int temp = 0; temp <= last - 1; temp++) {
                    if (palindromeDP[temp + 1][last] && minPartitionCount >= 1 + partitionIndexSetDP[temp].size()) {
                        minPartitionCount = 1 + partitionIndexSetDP[temp].size();
                        newPartitionIndex = temp;
                    }
                }
                partitionSet.addAll(partitionIndexSetDP[newPartitionIndex]);
                partitionSet.add(newPartitionIndex);
            }
            partitionIndexSetDP[last] = partitionSet;
        }

        return partitionIndexSetDP;
    }

    private Set<Integer>[][] getPartitionIndexSetDP3(int[] arr) {

        // partitionIndexSetDP[start][last] is a set of partition index
        // for an array with start index = start and last index = last
        Set<Integer>[][] partitionIndexSetDP = new HashSet[arr.length][arr.length];
        boolean[][] palindromeDP = getPalindromeDP2(arr);

        // length = 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                partitionIndexSetDP[start][last] = new HashSet<>();
            }
        }

        // length = 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                Set<Integer> partitionIndexSet = new HashSet<>();
                if (!palindromeDP[start][last]) {
                    partitionIndexSet.add(start);
                }
                partitionIndexSetDP[start][last] = partitionIndexSet;
            }
        }

        // length >= 2
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                Set<Integer> partitionIndexSet = new HashSet<>();
                if (!palindromeDP[start][last]) {
                    int minPartitionCount = length;
                    int newPartitionIndex = last - 1;
                    for (int temp = start; temp <= last - 1; temp++) {
                        if (minPartitionCount >= 1 + partitionIndexSetDP[start][temp].size() + partitionIndexSetDP[temp + 1][last].size()) {
                            minPartitionCount = 1 + partitionIndexSetDP[start][temp].size() + partitionIndexSetDP[temp + 1][last].size();
                            newPartitionIndex = temp;
                        }
                    }
                    partitionIndexSet.addAll(partitionIndexSetDP[start][newPartitionIndex]);
                    partitionIndexSet.addAll(partitionIndexSetDP[newPartitionIndex + 1][last]);
                    partitionIndexSet.add(newPartitionIndex);
                }
                partitionIndexSetDP[start][last] = partitionIndexSet;
            }

        }

        return partitionIndexSetDP;

    }

    private boolean[][] getPalindromeDP2(int[] arr) {

        boolean[][] palindromeDP = new boolean[arr.length][arr.length];

        // length = 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = true;
            }
        }

        // length = 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = (arr[start] == arr[last]);
            }
        }

        // length >= 2
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = (arr[start] == arr[last]) && palindromeDP[start + 1][last - 1];
            }
        }

        return palindromeDP;
    }

}
