package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class MinimumSpanningTreeUsingDisjointSet1 {

    public static void main(String[] args) {
        MinimumSpanningTreeUsingDisjointSet1 app = new MinimumSpanningTreeUsingDisjointSet1();
        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(app.new Edge(1, 2, 3));
        edgeList.add(app.new Edge(2, 3, 1));
        edgeList.add(app.new Edge(3, 4, 1));
        edgeList.add(app.new Edge(4, 1, 1));
        edgeList.add(app.new Edge(4, 5, 6));
        edgeList.add(app.new Edge(5, 6, 2));
        edgeList.add(app.new Edge(6, 3, 4));
        edgeList.add(app.new Edge(2, 4, 3));
        edgeList.add(app.new Edge(3, 5, 5));
        Set<Edge> mst = app.getMinimumSpanningTree(edgeList);
    }

    private Set<Edge> getMinimumSpanningTree(List<Edge> edgeList) {
        Map<Integer, DisjointNode> vertexNodeMap = new HashMap<>();
        for (Edge edge : edgeList) {
            vertexNodeMap.putIfAbsent(edge.getTail(), new DisjointNode(edge.getTail()));
            vertexNodeMap.putIfAbsent(edge.getHead(), new DisjointNode(edge.getHead()));
        }
        Set<Edge> mst = new HashSet<>();
        edgeList.sort(Comparator.comparingInt(Edge::getCost));
        for (Edge edge : edgeList) {
            DisjointNode tailNode = vertexNodeMap.get(edge.getTail());
            DisjointNode headNode = vertexNodeMap.get(edge.getHead());
            if (!tailNode.joint(headNode)) {
                tailNode.union(headNode);
                mst.add(edge);
            }
        }
        return mst;
    }

    private class DisjointNode {

        private int value;
        private int count;
        private DisjointNode leader;

        DisjointNode(int value) {
            this.value = value;
            this.count = 1;
            this.leader = this;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        int getCount() {
            return count;
        }

        void setCount(int count) {
            this.count = count;
        }

        DisjointNode getLeader() {
            return leader;
        }

        void setLeader(DisjointNode leader) {
            this.leader = leader;
        }

        boolean joint(DisjointNode that) {
            return that != null && resetAndGetLeader() == that.resetAndGetLeader();
        }

        DisjointNode resetAndGetLeader() {
            if (getLeader() == null) {
                setLeader(this);
                return getLeader();
            }
            if (getLeader() == this) {
                return getLeader();
            }
            setLeader(getLeader().resetAndGetLeader());
            return getLeader();
        }

        void union(DisjointNode that) {
            if (that != null) {
                DisjointNode thisLeader = resetAndGetLeader();
                DisjointNode thatLeader = that.resetAndGetLeader();
                if (thisLeader != thatLeader) {
                    if (thisLeader.getCount() >= thatLeader.getCount()) {
                        thatLeader.setLeader(thisLeader);
                        thisLeader.setCount(thisLeader.getCount() + thatLeader.getCount());
                    } else {
                        thisLeader.setLeader(thatLeader);
                        thatLeader.setCount(thatLeader.getCount() + thisLeader.getCount());
                    }
                }
            }
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
