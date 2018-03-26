package com.student.practice.practiceDP.typeE;

import java.util.Stack;

public class StockBuySellMaxProfit {

    public static void main(String[] args) {
        StockBuySellMaxProfit app = new StockBuySellMaxProfit();
        int[] arrDayPrice = new int[]{2, 5, 7, 1, 4, 3, 1, 3};
        int maxBuySellCount = 3;
        int[][] buySellDP2 = app.getBuySellDP2(arrDayPrice, maxBuySellCount);
        int[][] buySellDP3 = app.getBuySellDP3(arrDayPrice, maxBuySellCount);
        app.getPrintBuySellMaxProfitFromBuySellDP(arrDayPrice, maxBuySellCount, buySellDP2);
        System.out.println();
        app.getPrintBuySellMaxProfitFromBuySellDP(arrDayPrice, maxBuySellCount, buySellDP3);
    }

    private void getPrintBuySellMaxProfitFromBuySellDP(int[] arrDayPrice, int maxBuySellCount, int[][] buySellDP) {

        Stack<Integer> buyingDayIndexStack = new Stack<>();
        Stack<Integer> sellingDayIndexStack = new Stack<>();

        int dayIndex = arrDayPrice.length - 1;
        int bsCount = maxBuySellCount;

        while (dayIndex >= 0 && bsCount >= 0) {
            //
            if (buySellDP[dayIndex][bsCount] != buySellDP[dayIndex - 1][bsCount]) {
                // sold on day : dayIndex
                sellingDayIndexStack.push(dayIndex);
                int totalProfit = buySellDP[dayIndex][bsCount];
                int sp = arrDayPrice[dayIndex];
                dayIndex = dayIndex - 1;
                bsCount = bsCount - 1;
                while (dayIndex >= 0 && bsCount >= 0) {
                    int cp = arrDayPrice[dayIndex];
                    int previousProfit = buySellDP[dayIndex][bsCount];
                    if (sp - cp + previousProfit == totalProfit) {
                        // bought on day : dayIndex
                        buyingDayIndexStack.push(dayIndex);
                        dayIndex = dayIndex - 1;
                        break;
                    } else {
                        // not bought on day : dayIndex
                        dayIndex = dayIndex - 1;
                    }
                }
                //
            } else {
                // not sold on day : dayIndex
                dayIndex = dayIndex - 1;
            }
            //
        }

        int maximumProfit = 0;
        System.out.println(sellingDayIndexStack.size() == buyingDayIndexStack.size());
        while (!sellingDayIndexStack.isEmpty() && !buyingDayIndexStack.isEmpty()) {
            int buyingDayIndex = buyingDayIndexStack.pop();
            int sellingDayIndex = sellingDayIndexStack.pop();
            maximumProfit = maximumProfit + arrDayPrice[sellingDayIndex] - arrDayPrice[buyingDayIndex];
            System.out.println("buy at dayIndex = " + buyingDayIndex + " for -" + arrDayPrice[buyingDayIndex]);
            System.out.println("sell at dayIndex = " + sellingDayIndex + " for +" + arrDayPrice[sellingDayIndex]);
        }
        System.out.println("maximumProfit = " + maximumProfit);
    }

    private int[][] getBuySellDP2(int[] arrDayPrice, int maxBuySellCount) {

        int[][] buySellDP = new int[arrDayPrice.length][maxBuySellCount + 1];

        // dayIndex == 0; bsCount == 0;
        buySellDP[0][0] = 0;

        // bsCount == 0
        for (int dayIndex = 1; dayIndex <= arrDayPrice.length - 1; dayIndex++) {
            buySellDP[dayIndex][0] = 0;
        }

        // dayIndex == 0
        for (int bsCount = 1; bsCount <= maxBuySellCount; bsCount++) {
            buySellDP[0][bsCount] = 0;
        }

        // i could sell some best day before today but not today
        // or
        // i could sell today
        // if i sell today; i could buy yesterday or i could buy some best day before yesterday

        for (int bsCount = 1; bsCount <= maxBuySellCount; bsCount++) {

            //if i sell today and buy some best day before today
            int maxProfitMinusCP = buySellDP[0][bsCount - 1] - arrDayPrice[0];

            for (int dayIndex = 1; dayIndex <= arrDayPrice.length - 1; dayIndex++) {

                // if i sell today; i could buy yesterday or i could buy some best day before yesterday
                // if i sell today and buy yesterday
                int nowProfitMinusCP = buySellDP[dayIndex - 1][bsCount - 1] - arrDayPrice[dayIndex - 1];
                // if i sell today then : should i buy yesterday ? or should i buy some best day before yesterday
                if (nowProfitMinusCP > maxProfitMinusCP) {
                    // if i sell today then yes :) i should buy yesterday
                    maxProfitMinusCP = nowProfitMinusCP;
                }

                // if i sell today
                int doBuySellTodayMaximumProfit = maxProfitMinusCP + arrDayPrice[dayIndex];
                // if i don't sell today
                int noBuySellTodayMaximumProfit = buySellDP[dayIndex - 1][bsCount];

                // should i sell today ?
                // should i not sell today ?
                if (doBuySellTodayMaximumProfit > noBuySellTodayMaximumProfit) {
                    // i should sell today
                    buySellDP[dayIndex][bsCount] = doBuySellTodayMaximumProfit;
                } else {
                    // i should not sell today
                    buySellDP[dayIndex][bsCount] = noBuySellTodayMaximumProfit;
                }
                //
            }
            //
        }

        return buySellDP;
    }

    private int[][] getBuySellDP3(int[] arrDayPrice, int maxBuySellCount) {

        int[][] buySellDP = new int[arrDayPrice.length][maxBuySellCount + 1];

        // column : dayIndex == 0; row : bsCount == 0;
        buySellDP[0][0] = 0;

        // column : dayIndex == 0;
        for (int bsCount = 1; bsCount <= maxBuySellCount; bsCount++) {
            buySellDP[0][bsCount] = 0;
        }

        // row : bsCount == 0;
        for (int dayIndex = 1; dayIndex <= arrDayPrice.length - 1; dayIndex++) {
            buySellDP[dayIndex][0] = 0;
        }

        for (int bsCount = 1; bsCount <= maxBuySellCount; bsCount++) {
            for (int dayIndex = 1; dayIndex <= arrDayPrice.length - 1; dayIndex++) {
                //
                int doBuySellTodayMaximumProfit = 0;
                for (int tempDayIndex = 0; tempDayIndex <= dayIndex - 1; tempDayIndex++) {
                    int sp = arrDayPrice[dayIndex];
                    int cp = arrDayPrice[tempDayIndex];
                    int previousProfit = buySellDP[tempDayIndex][bsCount - 1];
                    int tempMaximumProfit = sp - cp + previousProfit;
                    if (tempMaximumProfit > doBuySellTodayMaximumProfit) {
                        doBuySellTodayMaximumProfit = tempMaximumProfit;
                    }
                }

                int noBuySellTodayMaximumProfit = buySellDP[dayIndex - 1][bsCount];

                if (doBuySellTodayMaximumProfit > noBuySellTodayMaximumProfit) {
                    buySellDP[dayIndex][bsCount] = doBuySellTodayMaximumProfit;
                } else {
                    buySellDP[dayIndex][bsCount] = buySellDP[dayIndex - 1][bsCount];
                }
                //
            }
        }

        return buySellDP;
    }

}
