package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class TopologicalSortUsingDFSInsideDirectedGraph1 {

    public static void main(String[] args) {
        TopologicalSortUsingDFSInsideDirectedGraph1 app = new TopologicalSortUsingDFSInsideDirectedGraph1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 3));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(2, 4));
        edgeSet.add(app.new Edge(3, 5));
        edgeSet.add(app.new Edge(4, 6));
        edgeSet.add(app.new Edge(5, 6));
        edgeSet.add(app.new Edge(5, 8));
        edgeSet.add(app.new Edge(6, 7));
        Stack<Integer> sortedStack = app.sortTopological(edgeSet);
    }

    private Stack<Integer> sortTopological(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = getAdjacencyMap(edgeSet);
        return sortTopological(adjacencyMap);
    }

    private Map<Integer, Set<Integer>> getAdjacencyMap(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            adjacencyMap.putIfAbsent(edge.getTail(), new HashSet<>());
            adjacencyMap.get(edge.getTail()).add(edge.getHead());
        }
        return adjacencyMap;
    }

    private Stack<Integer> sortTopological(Map<Integer, Set<Integer>> adjacencyMap) {
        Set<Integer> visitedSet = new HashSet<>();
        Stack<Integer> dfsFinishStack = new Stack<>();
        for (Integer vertex : adjacencyMap.keySet()) {
            dfsAndFinish(vertex, adjacencyMap, visitedSet, dfsFinishStack);
        }
        return dfsFinishStack;
    }

    private void dfsAndFinish(Integer vertex, Map<Integer, Set<Integer>> adjacencyMap, Set<Integer> visitedSet, Stack<Integer> dfsFinishStack) {
        if (!visitedSet.contains(vertex)) {
            visitedSet.add(vertex);
            Set<Integer> adjacencySet = adjacencyMap.get(vertex);
            if (adjacencySet != null) {
                for (Integer adjacent : adjacencySet) {
                    dfsAndFinish(adjacent, adjacencyMap, visitedSet, dfsFinishStack);
                }
            }
            dfsFinishStack.add(vertex);
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
