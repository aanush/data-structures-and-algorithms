package com.student.practice.practiceDP.typeC;

import java.util.ArrayList;
import java.util.List;

//todo
//coin change with limited supply
//todo
//coin change with negative coins

public class CoinChangeMinCount {

    public static void main(String[] args) {
        CoinChangeMinCount app = new CoinChangeMinCount();
        int[] arr = new int[]{1, 7, 11, 13, 19};
        int sum = 250;
        app.getPrintCoinList(arr, sum);
    }

    private void getPrintCoinList(int[] arr, int sum) {

        int[][] minCountOfCoin = getMinCountOfCoin(arr, sum);
        List<Integer> coinList = getCoinListFromMinCountOfCoin(arr, sum, minCountOfCoin);

        coinList.stream().map(index -> arr[index]).forEach(entry -> System.out.println(+entry));
    }

    private List<Integer> getCoinListFromMinCountOfCoin(int[] arr, int sum, int[][] minCountOfCoin) {

        List<Integer> coinList = new ArrayList<>();

        int current = arr.length - 1;
        int s = sum;
        while (minCountOfCoin[current][s] > 0) {
            if (s - arr[current] >= 0 && minCountOfCoin[current][s] == 1 + minCountOfCoin[current][s - arr[current]]) {
                coinList.add(current);
                s = s - arr[current];
            } else {
                if (current == 0) {
                    break;
                } else {
                    current = current - 1;
                }
            }
        }

        return coinList;
    }

    private int[][] getMinCountOfCoin(int[] arr, int sum) {

        //for coins from index = 0 to index = current
        //minimum number of coins required to get a sum = s
        //is equal to minDenomination[current][s]
        int[][] minCountOfCoin = new int[arr.length][sum + 1];

        final int noCount = -1;

        // row s = 0
        for (int current = 0; current <= arr.length - 1; current++) {
            minCountOfCoin[current][0] = 0;
        }

        // column current = 0
        for (int s = 1; s <= sum; s++) {
            if (s % arr[0] == 0) {
                minCountOfCoin[0][s] = s / arr[0];
            } else {
                minCountOfCoin[0][s] = noCount;
            }
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int s = 1; s <= sum; s++) {
                int excludingCurrent = minCountOfCoin[current - 1][s];
                int includingCurrent = noCount;
                if (s - arr[current] >= 0) {
                    if (minCountOfCoin[current][s - arr[current]] != noCount) {
                        includingCurrent = 1 + minCountOfCoin[current][s - arr[current]];
                    }
                }
                if (excludingCurrent != noCount && includingCurrent != noCount) {
                    minCountOfCoin[current][s] = Integer.min(excludingCurrent, includingCurrent);
                } else {
                    minCountOfCoin[current][s] = Integer.sum(excludingCurrent, includingCurrent) - noCount;
                }
            }
        }

        return minCountOfCoin;
    }

}

