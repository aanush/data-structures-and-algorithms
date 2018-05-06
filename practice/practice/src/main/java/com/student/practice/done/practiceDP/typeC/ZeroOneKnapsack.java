package com.student.practice.done.practiceDP.typeC;

import java.util.HashSet;
import java.util.Set;

//todo
//zero one knapsack with negative price

public class ZeroOneKnapsack {

    public static void main(String[] args) {
        ZeroOneKnapsack app = new ZeroOneKnapsack();
        Thing thing1 = app.new Thing(2, 1);
        Thing thing2 = app.new Thing(3, 3);
        Thing thing3 = app.new Thing(4, 4);
        Thing thing4 = app.new Thing(5, 4);
        Thing thing5 = app.new Thing(6, 5);
        Thing thing6 = app.new Thing(7, 5);
        Thing[] arr = new Thing[]{thing1, thing2, thing3, thing4, thing5, thing6};
        int capital = 9;
        app.getPrintThingSet(arr, capital);
    }

    private void getPrintThingSet(Thing[] arr, int capital) {

        int[][] maxValue = getMaxValue(arr, capital);
        Set<Integer> thingSet = getThingSetFromMaxValue(arr, capital, maxValue);

        thingSet.stream().map(index -> arr[index]).forEach(thing -> System.out.println(thing.toString()));
    }

    private Set<Integer> getThingSetFromMaxValue(Thing[] arr, int capital, int[][] maxValue) {

        Set<Integer> thingSet = new HashSet<>();

        int current = arr.length - 1;
        int cap = capital;

        while (maxValue[current][cap] > 0) {
            if (current == 0) {
                if (cap - arr[current].getPrice() >= 0) {
                    thingSet.add(current);
                    cap = cap - arr[current].getPrice();
                }
                break;
            }
            if (maxValue[current][cap] > maxValue[current - 1][cap]) {
                thingSet.add(current);
                cap = cap - arr[current].getPrice();
            }
            current = current - 1;
        }

        return thingSet;
    }

    private int[][] getMaxValue(Thing[] arr, int capital) {

        // from thing index = 0 to thing index = current;
        // and given capital = cap
        // maximum value that could be found = maxValue[current][cap]
        int[][] maxValue = new int[arr.length][capital + 1];

        // cap = 0
        for (int current = 0; current <= arr.length - 1; current++) {
            maxValue[current][0] = 0;
        }

        // current = 0
        for (int cap = 1; cap <= arr[0].getPrice() - 1; cap++) {
            maxValue[0][cap] = 0;
        }
        for (int cap = arr[0].getPrice(); cap <= capital; cap++) {
            maxValue[0][cap] = arr[0].getValue();
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int cap = 1; cap <= capital; cap++) {
                int excludingCurrent = maxValue[current - 1][cap];
                int includingCurrent = 0;
                if (cap - arr[current].getPrice() >= 0) {
                    includingCurrent = arr[current].getValue() + maxValue[current - 1][cap - arr[current].getPrice()];
                }
                maxValue[current][cap] = Integer.max(excludingCurrent, includingCurrent);
            }
        }

        return maxValue;
    }

    private class Thing {

        private final int value;
        private final int price;

        Thing(int value, int price) {
            this.value = value;
            this.price = price;
        }

        int getValue() {
            return value;
        }

        int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Thing{" +
                    "value=" + value +
                    ", price=" + price +
                    '}';
        }
    }

}
