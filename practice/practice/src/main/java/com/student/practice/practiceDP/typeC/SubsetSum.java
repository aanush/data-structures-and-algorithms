package com.student.practice.practiceDP.typeC;

import java.util.*;

// find one subset with subsetSum : s1
// find two subset with subsetSum : s1 = s2; s1 + s2 = totalSum; s1 = s2 so find subset with subsetSum = totalSum/2
// find two subset with subsetSum : s1 - s2 = diff; s1 + s2 = totalSum; so find subset with subsetSum = s1 = (totalSum + diff)/2 and s2 = (totalSum - diff)/2
// todo
// find one subset with subsetSum : closest to s1
// closest to s1; complete subsetFound[arr.length - 1][s1 + maximum entry] and find the subsetSum closest to s1

public class SubsetSum {

    public static void main(String[] args) {
        SubsetSum app = new SubsetSum();
        int[] arr = {-1, -1, -3, -5, -12, 9, 10, 20};//{1, -3, -4, 5, 6, 7};
        int sum = 8;//2;
        //app.getPrintSubsetPositive(arr, sum);
        app.getPrintSubset(arr, sum);
    }

//////////////////////////////////////////////////////////////////////////////
////arr has non negative numbers only/////////////////////////////////////////

    void getPrintSubsetPositive(int[] arr, int sum) {

        boolean[][] subsetFound = getSubsetFoundPositive(arr, sum);
        Set<Integer> subset = getSubsetPositiveFromSubsetFound(arr, sum, subsetFound);

        subset.forEach(i -> System.out.println(arr[i]));
    }

    private Set<Integer> getSubsetPositiveFromSubsetFound(int[] arr, int sum, boolean[][] subsetFound) {

        Set<Integer> subset = new HashSet<>();

        int current = arr.length - 1;
        int s = sum;

        while (subsetFound[current][s]) {
            if (current == 0) {
                if (s != 0 && arr[current] == s) {
                    subset.add(current);
                    s = s - arr[current];
                }
                break;
            }
            // must include current only if subsetFound is true for current and false for current - 1
            if (!subsetFound[current - 1][s]) {
                subset.add(current);
                s = s - arr[current];
            }
            current = current - 1;
        }

        return subset;
    }

    private boolean[][] getSubsetFoundPositive(int[] arr, int sum) {

        // subsetFound[current][s] == true
        // means that from index = 0, to index = current, there is a subset with sum = s
        boolean[][] subsetFound = new boolean[arr.length][sum + 1];

        // complete the row for sum : s = 0
        // from index = 0, to index = current, sum = 0
        for (int current = 0; current <= arr.length - 1; current++) {
            subsetFound[current][0] = true;
        }

        // complete the column for index : current = 0
        // from index = 0, to index = 0, sum = s
        // for (int s = 1; s <= sum; s++) {
        //     subsetFound[0][s] = arr[0] == s;
        // }
        if (arr[0] <= sum) {
            subsetFound[0][arr[0]] = true;
        }

        for (int current = 1; current <= arr.length - 1; current++) {
            for (int s = 1; s <= sum; s++) {

                // excluding arr[current]
                // from index = 0, to index = current - 1, could i find a subset with sum = s
                boolean excludingCurrent = subsetFound[current - 1][s];

                // including arr[current]
                // could i include and if i could include then
                // from index = 0, to index = current - 1, could i find a subset with sum = s - arr[current]
                boolean includingCurrent = s - arr[current] >= 0 && subsetFound[current - 1][s - arr[current]];

                // excluding arr[current] or including arr[current]
                // from index = 0, to index = current, could i find a subset with sum = s
                subsetFound[current][s] = excludingCurrent || includingCurrent;

            }
        }

        return subsetFound;
    }

////////////////////////////////////////////////////////////////////////////
////arr has multiple negative and positive numbers//////////////////////////

    private void getPrintSubset(int[] arr, int sum) {

        int[] arrPositive = Arrays.stream(arr).filter(value -> value > 0).toArray();
        int[] arrNegative = Arrays.stream(arr).filter(value -> value < 0).map(value -> -value).toArray();
        int positiveSum = Arrays.stream(arrPositive).sum();
        int negativeSum = Arrays.stream(arrNegative).sum();

        Integer[] arrSumP = null;
        boolean[][] subsetFoundPositive = null;
        if (arrPositive.length - 1 >= 0) {
            subsetFoundPositive = getSubsetFoundPositive(arrPositive, positiveSum);
            if (sum > 0 && subsetFoundPositive[subsetFoundPositive.length - 1][sum]) {
                getSubsetPositiveFromSubsetFound(arrPositive, sum, subsetFoundPositive).stream().map(index -> arrPositive[index]).forEach(entry -> System.out.println(+entry));
                return;
            }

            List<Integer> positiveSumList = new ArrayList<>();
            for (int s = 1; s <= positiveSum; s++) {
                if (subsetFoundPositive[subsetFoundPositive.length - 1][s]) {
                    positiveSumList.add(s);
                }
            }
            arrSumP = positiveSumList.toArray(new Integer[positiveSumList.size()]);
        }

        Integer[] arrSumN = null;
        boolean[][] subsetFoundNegative = null;
        if (arrNegative.length - 1 >= 0) {
            subsetFoundNegative = getSubsetFoundPositive(arrNegative, negativeSum);
            if (-sum > 0 && subsetFoundNegative[subsetFoundNegative.length - 1][-sum]) {
                getSubsetPositiveFromSubsetFound(arrNegative, -sum, subsetFoundNegative).stream().map(index -> arrNegative[index]).forEach(entry -> System.out.println(-entry));
                return;
            }

            List<Integer> negativeSumList = new ArrayList<>();
            for (int s = 1; s <= negativeSum; s++) {
                if (subsetFoundNegative[subsetFoundNegative.length - 1][s]) {
                    negativeSumList.add(s);
                }
            }
            arrSumN = negativeSumList.toArray(new Integer[negativeSumList.size()]);
        }

        Arrays.sort(arrSumP, (a, b) -> b - a);
        Arrays.sort(arrSumN, (a, b) -> b - a);

        int p = 0;
        int n = 0;

        while (p <= arrSumP.length - 1 && n <= arrSumN.length - 1) {
            if (arrSumP[p] - arrSumN[n] == sum) {
                break;
            }
            if (arrSumP[p] - arrSumN[n] >= sum) {
                p++;
                continue;
            }
            if (arrSumP[p] - arrSumN[n] <= sum) {
                n++;
                continue;
            }
        }

        if (arrSumP[p] - arrSumN[n] == sum) {
            getSubsetPositiveFromSubsetFound(arrPositive, arrSumP[p], subsetFoundPositive).stream().map(index -> arrPositive[index]).forEach(entry -> System.out.println(+entry));
            getSubsetPositiveFromSubsetFound(arrNegative, arrSumN[n], subsetFoundNegative).stream().map(index -> arrNegative[index]).forEach(entry -> System.out.println(-entry));
        }
    }

///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

    void print(int[] arr, int sum, boolean[][] subsetFound) {
        System.out.println();
        System.out.println();
        for (int s = 0; s <= sum; s++) {
            for (int current = 0; current <= arr.length - 1; current++) {
                boolean found = subsetFound[current][s];
                if (found) {
                    System.out.print("current = " + current + ", sum = " + s + " = " + found + "           ");
                } else {
                    System.out.print("current = " + current + ", sum = " + s + " = " + found + "          ");
                }

            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

}


