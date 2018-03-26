package com.student.practice.practiceDP.typeD;

import java.util.HashSet;
import java.util.Set;

public class WordBreakPartitionMinCount {

    public static void main(String[] args) {
        WordBreakPartitionMinCount app = new WordBreakPartitionMinCount();
        test2(app);
    }

    private static void test2(WordBreakPartitionMinCount app) {
        //i a man god
        int wordLength = 8;
        boolean[][] dictionary = new boolean[wordLength][wordLength];
        dictionary[0][0] = true;//i
        dictionary[1][1] = true;//a
        dictionary[3][3] = true;//a
        dictionary[6][6] = true;//o
        dictionary[1][2] = true;//am
        dictionary[3][4] = true;//an
        dictionary[5][6] = true;//go
        dictionary[2][4] = true;//man
        dictionary[5][7] = true;//god
        dictionary[2][6] = true;//mango
        app.getWordBreakDP3(dictionary, wordLength);
    }

    private static void test1(WordBreakPartitionMinCount app) {
        //iamace
        int wordLength = 6;
        boolean[][] dictionary = new boolean[wordLength][wordLength];
        dictionary[0][0] = true;
        dictionary[1][1] = true;
        dictionary[3][3] = true;
        dictionary[1][2] = true;
        dictionary[3][5] = true;
        app.getWordBreakDP3(dictionary, wordLength);
    }

    private Set<Integer>[][] getWordBreakDP3(boolean[][] dictionary, int wordLength) {

        Set<Integer>[][] partitionIndexSetDP = new HashSet[wordLength][wordLength];

        // length == 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= wordLength - 1; start++, last++) {
                if (dictionary[start][last]) {
                    partitionIndexSetDP[start][last] = new HashSet<>();
                }
            }
        }

        // length == 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= wordLength - 1; start++, last++) {
                if (dictionary[start][last] || (dictionary[start][start] && dictionary[last][last])) {
                    partitionIndexSetDP[start][last] = new HashSet<>();
                    if (!dictionary[start][last]) {
                        partitionIndexSetDP[start][last].add(start);
                    }
                }
            }
        }

        for (int length = 0; length <= wordLength - 1; length++) {
            for (int start = 0, last = start + length; last <= wordLength - 1; start++, last++) {

                if (dictionary[start][last]) {
                    partitionIndexSetDP[start][last] = new HashSet<>();
                } else {

                    int partitionIndexSetSize = -1;
                    int partitionIndex = -1;
                    for (int temp = start; temp <= last - 1; temp++) {
                        if (partitionIndexSetDP[start][temp] != null && partitionIndexSetDP[temp + 1][last] != null) {
                            if (partitionIndexSetSize == -1 || partitionIndexSetSize > 1 + partitionIndexSetDP[start][temp].size() + partitionIndexSetDP[temp + 1][last].size()) {
                                partitionIndexSetSize = 1 + partitionIndexSetDP[start][temp].size() + partitionIndexSetDP[temp + 1][last].size();
                                partitionIndex = temp;
                            }
                        }
                    }

                    if (partitionIndex != -1) {
                        Set<Integer> partitionIndexSet = new HashSet<>();
                        partitionIndexSet.addAll(partitionIndexSetDP[start][partitionIndex]);
                        partitionIndexSet.addAll(partitionIndexSetDP[partitionIndex + 1][last]);
                        partitionIndexSet.add(partitionIndex);
                        partitionIndexSetDP[start][last] = partitionIndexSet;
                    }

                }
            }
        }

        return partitionIndexSetDP;
    }

}
