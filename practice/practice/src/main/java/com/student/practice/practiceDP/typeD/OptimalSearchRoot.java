package com.student.practice.practiceDP.typeD;

public class OptimalSearchRoot {

    public static void main(String[] args) {
        OptimalSearchRoot app = new OptimalSearchRoot();
        int[] arr = new int[]{4, 2, 6, 3};
        int[][] sumDP = app.getSumDP(arr);
        OptimalSearch[][] searchDP = app.getOptimalSearchDP(arr, sumDP);
        app.getPrintOptimalSearchRoot(searchDP, 0, arr.length - 1, 0, 0);
    }

    private void getPrintOptimalSearchRoot(OptimalSearch[][] searchDP, int start, int last, int x, int y) {
        int root = searchDP[start][last].getRootIndex();
        System.out.println("root = " + root + ": (x, y) = (" + x + "," + y + ")");
        if (root - 1 >= start) {
            getPrintOptimalSearchRoot(searchDP, start, root - 1, x - 1, y + 1);
        }
        if (root + 1 <= last) {
            getPrintOptimalSearchRoot(searchDP, root + 1, last, x + 1, y + 1);
        }
    }

    private OptimalSearch[][] getOptimalSearchDP(int[] arr, int[][] sumDP) {
        //
        OptimalSearch[][] searchDP = new OptimalSearch[arr.length][arr.length];

        // length == 0;
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                searchDP[start][last] = new OptimalSearch(sumDP[start][last], start);
            }
        }

        // length == 1;
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                if (arr[start] > arr[last]) {
                    searchDP[start][last] = new OptimalSearch(sumDP[start][last] + arr[last], start);
                } else {
                    searchDP[start][last] = new OptimalSearch(sumDP[start][last] + arr[start], last);
                }
            }
        }

        // length >= 1;
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                //
                int cost = Integer.MAX_VALUE;
                int rootIndex = -1;
                // now try all index belongs to [start, last] as root index and get the optimal cost and root index
                // try start as root index
                int cost1 = sumDP[start][last] + searchDP[start + 1][last].getCost();
                if (cost1 < cost) {
                    cost = cost1;
                    rootIndex = start;
                }
                // try last as root index
                int cost2 = sumDP[start][last] + searchDP[start][last - 1].getCost();
                if (cost2 < cost) {
                    cost = cost2;
                    rootIndex = last;
                }
                // try temp as root index
                for (int temp = start + 1; temp <= last - 1; temp++) {
                    int tempCost = sumDP[start][last] + searchDP[start][temp - 1].getCost() + searchDP[temp + 1][last].getCost();
                    if (tempCost < cost) {
                        cost = tempCost;
                        rootIndex = temp;
                    }
                }
                //
                searchDP[start][last] = new OptimalSearch(cost, rootIndex);
            }
        }

        return searchDP;
    }

    private int[][] getSumDP(int[] arr) {
        //
        int[][] sumDP = new int[arr.length][arr.length];

        // length == 0;
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                sumDP[start][last] = arr[start];
            }
        }

        // length == 1;
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                sumDP[start][last] = arr[start] + arr[last];
            }
        }

        // length >= 1;
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                sumDP[start][last] = arr[start] + sumDP[start + 1][last];
            }
        }

        return sumDP;
    }

    private class OptimalSearch {

        private final int cost;
        private final int rootIndex;

        OptimalSearch(int cost, int rootIndex) {
            this.cost = cost;
            this.rootIndex = rootIndex;
        }

        public int getCost() {
            return cost;
        }

        public int getRootIndex() {
            return rootIndex;
        }

    }

}
