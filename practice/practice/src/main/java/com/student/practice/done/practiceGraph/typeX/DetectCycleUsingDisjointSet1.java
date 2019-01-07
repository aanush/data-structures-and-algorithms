package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class DetectCycleUsingDisjointSet1 {

    public static void main(String[] args) {
        DetectCycleUsingDisjointSet1 app = new DetectCycleUsingDisjointSet1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(3, 4));
        edgeSet.add(app.new Edge(4, 5));
        edgeSet.add(app.new Edge(5, 2));
        edgeSet.add(app.new Edge(6, 1));
        boolean cycle = app.cycle(edgeSet);
    }

    private boolean cycle(Set<Edge> edgeSet) {
        Map<Integer, DisjointNode> vertexNodeMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            vertexNodeMap.putIfAbsent(edge.getTail(), new DisjointNode(edge.getTail()));
            vertexNodeMap.putIfAbsent(edge.getHead(), new DisjointNode(edge.getHead()));
        }
        boolean cycle = false;
        for (Edge edge : edgeSet) {
            DisjointNode tailNode = vertexNodeMap.get(edge.getTail());
            DisjointNode headNode = vertexNodeMap.get(edge.getHead());
            if (tailNode != headNode) {
                cycle = tailNode != headNode && tailNode.joint(headNode);
                if (cycle) {
                    break;
                } else {
                    tailNode.union(headNode);
                }
            }
        }
        return cycle;
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

        Edge(int tail, int head) {
            this.tail = tail;
            this.head = head;
        }

        int getTail() {
            return tail;
        }

        int getHead() {
            return head;
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
            return getTail() == that.getTail() && getHead() == that.getHead();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTail(), getHead());
        }

    }

}
