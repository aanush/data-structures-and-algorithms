package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class DetectCycleUsingDFSInsideNonDirectedGraph1 {

    public static void main(String[] args) {
        DetectCycleUsingDFSInsideNonDirectedGraph1 app = new DetectCycleUsingDFSInsideNonDirectedGraph1();
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
        Map<Integer, Set<Integer>> adjacencyMap = getAdjacencyMap(edgeSet);
        return cycle(adjacencyMap);
    }

    private Map<Integer, Set<Integer>> getAdjacencyMap(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            adjacencyMap.putIfAbsent(edge.getTail(), new HashSet<>());
            adjacencyMap.putIfAbsent(edge.getHead(), new HashSet<>());
            adjacencyMap.get(edge.getTail()).add(edge.getHead());
            adjacencyMap.get(edge.getHead()).add(edge.getTail());
        }
        return adjacencyMap;
    }

    private boolean cycle(Map<Integer, Set<Integer>> adjacencyMap) {
        Set<Integer> dfsFinishedSet = new HashSet<>();
        for (Integer vertex : adjacencyMap.keySet()) {
            if (!dfsFinishedSet.contains(vertex)) {
                Set<Integer> visitedSet = new HashSet<>();
                boolean cycle = dfsAndDetectCycle(vertex, null, adjacencyMap, visitedSet);
                if (cycle) {
                    return true;
                }
                dfsFinishedSet.addAll(visitedSet);
            }
        }
        return false;
    }

    private boolean dfsAndDetectCycle(Integer vertex, Integer previous, Map<Integer, Set<Integer>> adjacencyMap, Set<Integer> visitedSet) {
        if (visitedSet.contains(vertex)) {
            return true;
        }
        visitedSet.add(vertex);
        for (Integer adjacent : adjacencyMap.get(vertex)) {
            if (!adjacent.equals(previous)) {
                boolean cycle = dfsAndDetectCycle(adjacent, vertex, adjacencyMap, visitedSet);
                if (cycle) {
                    return true;
                }
            }
        }
        return false;
    }

    private class Edge {

        private final int tail;
        private final int head;

        Edge(int tail, int head) {
            this.tail = Integer.min(tail, head);
            this.head = Integer.max(tail, head);
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
