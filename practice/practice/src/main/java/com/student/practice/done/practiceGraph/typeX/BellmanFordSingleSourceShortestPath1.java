package com.student.practice.done.practiceGraph.typeX;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BellmanFordSingleSourceShortestPath1 {

    public static void main(String[] args) {
        BellmanFordSingleSourceShortestPath1 app = new BellmanFordSingleSourceShortestPath1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(0, 1, 4));
        edgeSet.add(app.new Edge(0, 2, 5));
        edgeSet.add(app.new Edge(0, 3, 8));
        edgeSet.add(app.new Edge(1, 2, -3));
        edgeSet.add(app.new Edge(2, 4, 4));
        edgeSet.add(app.new Edge(3, 4, 2));
        edgeSet.add(app.new Edge(4, 3, 1));
        CostAndPreviousIndex[] shortestPathDP1 = app.getShortestPathDP(edgeSet, 0, 5);
        Set<Edge> edgeSetNegativeCycle = new HashSet<>();
        edgeSetNegativeCycle.add(app.new Edge(0, 1, 1));
        edgeSetNegativeCycle.add(app.new Edge(1, 2, 3));
        edgeSetNegativeCycle.add(app.new Edge(2, 3, 2));
        edgeSetNegativeCycle.add(app.new Edge(3, 1, -6));
        CostAndPreviousIndex[] shortestPathDP2 = app.getShortestPathDP(edgeSetNegativeCycle, 0, 4);
        System.out.println();
    }

    private CostAndPreviousIndex[] getShortestPathDP(Set<Edge> edgeSet, int source, int verticesCount) {

        CostAndPreviousIndex[] shortestPathDP = new CostAndPreviousIndex[verticesCount];
        for (int i = 0; i <= verticesCount - 1; i++) {
            shortestPathDP[i] = new CostAndPreviousIndex(Integer.MAX_VALUE, CostAndPreviousIndex.NO_INDEX);
        }
        shortestPathDP[source].setCost(0);

        boolean negativeCycle = false;

        for (int i = 0; i <= verticesCount - 1; i++) {
            for (Edge edge : edgeSet) {
                //
                if (correct(edge.getTail(), edge.getHead(), source, 0, shortestPathDP.length - 1)) {
                    int c1 = shortestPathDP[edge.getTail()].getCost();
                    int c2 = edge.getCost();
                    int c3 = shortestPathDP[edge.getHead()].getCost();
                    if (shorter(c1, c2, c3)) {
                        if (i != verticesCount - 1) {
                            shortestPathDP[edge.getHead()].setCost(c1 + c2);
                            shortestPathDP[edge.getHead()].setPreviousIndex(edge.getTail());
                        } else {
                            negativeCycle = true;
                            break;
                        }
                    }
                }
                //
            }
        }

        if (negativeCycle) {
            shortestPathDP = null;
        }

        return shortestPathDP;
    }

    private boolean correct(int tail, int head, int source, int min, int max) {
        return (head != source) && (min <= tail && tail <= max) && (min <= head && head <= max);
    }

    private boolean shorter(int c1, int c2, int c3) {
        BigInteger v1 = BigInteger.valueOf(c1);
        BigInteger v2 = BigInteger.valueOf(c2);
        BigInteger v3 = BigInteger.valueOf(c3);
        return v1.add(v2).subtract(v3).signum() == -1;
    }

    private class CostAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private int cost;
        private int previousIndex;

        CostAndPreviousIndex(int cost, int previousIndex) {
            this.cost = cost;
            this.previousIndex = previousIndex;
        }

        int getCost() {
            return cost;
        }

        void setCost(int cost) {
            this.cost = cost;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

        void setPreviousIndex(int previousIndex) {
            this.previousIndex = previousIndex;
        }

    }

    private class Edge {

        private final int tail;
        private final int head;
        private final int cost;

        Edge(int tail, int head, int cost) {
            this.tail = tail;
            this.head = head;
            this.cost = cost;
        }

        int getTail() {
            return tail;
        }

        int getHead() {
            return head;
        }

        int getCost() {
            return cost;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Edge that = (Edge) obj;
            return getTail() == that.getTail() && getHead() == that.getHead() && getCost() == that.getCost();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTail(), getHead(), getCost());
        }

    }

}
