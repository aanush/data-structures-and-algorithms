package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class DetectCycleUsingDFSInsideDirectedGraph1 {

    public static void main(String[] args) {
        DetectCycleUsingDFSInsideDirectedGraph1 app = new DetectCycleUsingDFSInsideDirectedGraph1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2));
        edgeSet.add(app.new Edge(1, 3));
        edgeSet.add(app.new Edge(1, 4));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(4, 5));
        edgeSet.add(app.new Edge(5, 6));
        edgeSet.add(app.new Edge(4, 6));
        Map<Integer, Integer> cycleMap = app.cycle(edgeSet);
        boolean cycle = !cycleMap.isEmpty();
    }

    private Map<Integer, Integer> cycle(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = getAdjacencyMap(edgeSet);
        return cycle(adjacencyMap);
    }

    private Map<Integer, Set<Integer>> getAdjacencyMap(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            adjacencyMap.putIfAbsent(edge.getTail(), new HashSet<>());
            adjacencyMap.get(edge.getTail()).add(edge.getHead());
        }
        return adjacencyMap;
    }

    private Map<Integer, Integer> cycle(Map<Integer, Set<Integer>> adjacencyMap) {
        Set<Integer> dfsFinishedSet = new HashSet<>();
        for (Integer vertex : adjacencyMap.keySet()) {
            if (!dfsFinishedSet.contains(vertex)) {
                Map<Integer, Integer> visitedMap = new HashMap<>();
                boolean cycle = dfsAndDetectCycle(vertex, null, adjacencyMap, visitedMap, dfsFinishedSet);
                if (cycle) {
                    return visitedMap;
                }
            }
        }
        return Collections.emptyMap();
    }

    private boolean dfsAndDetectCycle(Integer vertex, Integer previous, Map<Integer, Set<Integer>> adjacencyMap, Map<Integer, Integer> visitedMap, Set<Integer> dfsFinishedSet) {
        if (visitedMap.containsKey(vertex)) {
            visitedMap.put(vertex, previous);
            return true;
        } else {
            visitedMap.put(vertex, previous);
            Set<Integer> adjacencySet = adjacencyMap.get(vertex);
            if (adjacencySet != null) {
                for (Integer adjacent : adjacencySet) {
                    boolean cycle = dfsAndDetectCycle(adjacent, vertex, adjacencyMap, visitedMap, dfsFinishedSet);
                    if (cycle) {
                        return true;
                    }
                }
            }
            visitedMap.remove(vertex);
            dfsFinishedSet.add(vertex);
            return false;
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
