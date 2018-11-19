package com.student.practice.done.practiceGraph.typeX;

import java.math.BigInteger;

public class FloydWarshallAllPairShortestPath1 {

    public static void main(String[] args) {
        FloydWarshallAllPairShortestPath1 app = new FloydWarshallAllPairShortestPath1();
        Integer[][] mat = new Integer[4][4];
        Integer oo = null;
        mat[0] = new Integer[]{0, 3, 6, 15};
        mat[1] = new Integer[]{oo, 0, -2, oo};
        mat[2] = new Integer[]{oo, oo, 0, 2};
        mat[3] = new Integer[]{1, oo, oo, 0};
        CostAndPreviousIndex[][] shortestPathDP = app.getShortestPathDP(mat);
    }

    // cost(x)(y)(k) = cost of shortest path from vertex x to vertex y
    // prev(x)(y)(k) = vertex immediate before vertex y in the shortest path from vertex x to vertex y
    // such that the intermediate vertex could be selected (if) only from vertex 0 to vertex k
    // now there are two case
    // case1: if vertex k is selected as an intermediate vertex
    // case2: if vertex k is not selected as an intermediate vertex
    // if(cost(x)(k)(k-1) + cost(k)(y)(k-1) < cost(x)(y)(k-1)) {
    //    // vertex k is selected as an intermediate vertex
    //    cost(x)(y)(k) = cost(x)(k)(k-1) + cost(k)(y)(k-1);
    //    prev(x)(y)(k) = prev(k)(y)(k-1);
    // } else {
    //    // vertex k is not selected as an intermediate vertex
    //    cost(x)(y)(k) = cost(x)(y)(k-1);
    //    prev(x)(y)(k) = prev(x)(y)(k-1);
    // }

    private CostAndPreviousIndex[][] getShortestPathDP(Integer[][] mat) {

        CostAndPreviousIndex[][] shortestPathDP = new CostAndPreviousIndex[mat.length][mat.length];

        for (int x = 0; x <= mat.length - 1; x++) {
            for (int y = 0; y <= mat.length - 1; y++) {
                shortestPathDP[x][y] = new CostAndPreviousIndex(mat[x][y], x);
            }
        }

        for (int k = 0; k <= mat.length - 1; k++) {
            for (int x = 0; x <= mat.length - 1; x++) {
                for (int y = 0; y <= mat.length - 1; y++) {
                    Integer costXK = shortestPathDP[x][k].getCost();
                    Integer costKY = shortestPathDP[k][y].getCost();
                    Integer costXY = shortestPathDP[x][y].getCost();
                    Integer previousIndexKY = shortestPathDP[k][y].getPreviousIndex();
                    if (shorter(costXK, costKY, costXY)) {
                        shortestPathDP[x][y].setCost(costXK + costKY);
                        shortestPathDP[x][y].setPreviousIndex(previousIndexKY);
                    }
                }
            }
        }

        return shortestPathDP;
    }

    private boolean shorter(Integer c1, Integer c2, Integer c3) {
        if (c1 == null) {
            return false;
        }
        if (c2 == null) {
            return false;
        }
        if (c3 == null) {
            return true;
        }
        BigInteger v1 = BigInteger.valueOf(c1);
        BigInteger v2 = BigInteger.valueOf(c2);
        BigInteger v3 = BigInteger.valueOf(c3);
        return v1.add(v2).subtract(v3).signum() == -1;
    }

    private class CostAndPreviousIndex {

        private Integer cost;
        private Integer previousIndex;

        CostAndPreviousIndex(Integer cost, Integer previousIndex) {
            this.cost = cost;
            this.previousIndex = previousIndex;
        }

        Integer getCost() {
            return cost;
        }

        void setCost(Integer cost) {
            this.cost = cost;
        }

        Integer getPreviousIndex() {
            return previousIndex;
        }

        void setPreviousIndex(Integer previousIndex) {
            this.previousIndex = previousIndex;
        }

    }

}
