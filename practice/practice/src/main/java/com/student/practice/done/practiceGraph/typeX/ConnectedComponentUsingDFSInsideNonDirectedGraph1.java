package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class ConnectedComponentUsingDFSInsideNonDirectedGraph1 {

    public static void main(String[] args) {
        ConnectedComponentUsingDFSInsideNonDirectedGraph1 app = new ConnectedComponentUsingDFSInsideNonDirectedGraph1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 1));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(4, 5));
        edgeSet.add(app.new Edge(5, 6));
        edgeSet.add(app.new Edge(6, 4));
        edgeSet.add(app.new Edge(7, 8));
        edgeSet.add(app.new Edge(7, 9));
        Set<Set<Integer>> componentSet = app.getComponentSet(edgeSet);
    }

    private Set<Set<Integer>> getComponentSet(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = getAdjacencyMap(edgeSet);
        return getComponentSet(adjacencyMap);
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

    private Set<Set<Integer>> getComponentSet(Map<Integer, Set<Integer>> adjacencyMap) {
        Set<Set<Integer>> componentSet = new HashSet<>();
        Set<Integer> dfsFinishedSet = new HashSet<>();
        for (Integer vertex : adjacencyMap.keySet()) {
            if (!dfsFinishedSet.contains(vertex)) {
                Set<Integer> visitedSet = new HashSet<>();
                dfsAndFinish(vertex, adjacencyMap, visitedSet);
                dfsFinishedSet.addAll(visitedSet);
                componentSet.add(visitedSet);
            }
        }
        return componentSet;
    }

    private void dfsAndFinish(Integer vertex, Map<Integer, Set<Integer>> adjacencyMap, Set<Integer> visitedSet) {
        if (!visitedSet.contains(vertex)) {
            visitedSet.add(vertex);
            for (Integer adjacent : adjacencyMap.get(vertex)) {
                dfsAndFinish(adjacent, adjacencyMap, visitedSet);
            }
        }
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
