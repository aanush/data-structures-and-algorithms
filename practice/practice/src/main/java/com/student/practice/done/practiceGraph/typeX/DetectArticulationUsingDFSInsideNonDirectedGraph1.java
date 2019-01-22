package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class DetectArticulationUsingDFSInsideNonDirectedGraph1 {

    public static void main(String[] args) {
        DetectArticulationUsingDFSInsideNonDirectedGraph1 app = new DetectArticulationUsingDFSInsideNonDirectedGraph1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2));
        edgeSet.add(app.new Edge(1, 3));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(3, 4));
        edgeSet.add(app.new Edge(4, 5));
        edgeSet.add(app.new Edge(5, 6));
        edgeSet.add(app.new Edge(5, 7));
        edgeSet.add(app.new Edge(6, 7));
        edgeSet.add(app.new Edge(6, 8));
        edgeSet.add(app.new Edge(11, 12));
        edgeSet.add(app.new Edge(12, 13));
        edgeSet.add(app.new Edge(13, 14));
        edgeSet.add(app.new Edge(11, 13));
        Set<Set<Integer>> allArticulationSet = app.getAllArticulationSet(edgeSet);
    }

    private Set<Set<Integer>> getAllArticulationSet(Set<Edge> edgeSet) {
        Map<Integer, Set<Integer>> adjacencyMap = getAdjacencyMap(edgeSet);
        return getAllArticulationSet(adjacencyMap);
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

    private Set<Set<Integer>> getAllArticulationSet(Map<Integer, Set<Integer>> adjacencyMap) {
        Set<Set<Integer>> allArticulationSet = new HashSet<>();
        Set<Integer> dfsFinishedSet = new HashSet<>();
        Clock clock = new Clock(0);
        for (Integer vertex : adjacencyMap.keySet()) {
            if (!dfsFinishedSet.contains(vertex)) {
                Set<Integer> articulationSet = new HashSet<>();
                Map<Integer, int[]> visitedMap = new HashMap<>();
                dfsAndDetectArticulation(vertex, null, adjacencyMap, visitedMap, articulationSet, clock);
                dfsFinishedSet.addAll(visitedMap.keySet());
                allArticulationSet.add(articulationSet);
            }
        }
        return allArticulationSet;
    }

    private void dfsAndDetectArticulation(Integer vertex, Integer previous, Map<Integer, Set<Integer>> adjacencyMap, Map<Integer, int[]> visitedMap, Set<Integer> articulationSet, Clock clock) {
        if (!visitedMap.containsKey(vertex)) {
            int visit = clock.getNextValue();
            int lower = visit;
            int count = 0;
            visitedMap.put(vertex, new int[]{visit, lower, count});
            int[] arr = visitedMap.get(previous);
            if (arr != null) {
                arr[2] = arr[2] + 1;
            }
            for (Integer adjacent : adjacencyMap.get(vertex)) {
                if (!adjacent.equals(previous)) {
                    dfsAndDetectArticulation(adjacent, vertex, adjacencyMap, visitedMap, articulationSet, clock);
                    int[] vert = visitedMap.get(vertex);
                    int[] adjc = visitedMap.get(adjacent);
                    int[] prev = visitedMap.get(previous);
                    boolean articulation = articulation(vert, adjc, prev);
                    if (articulation) {
                        articulationSet.add(vertex);
                    }
                    vert[1] = Integer.min(vert[1], adjc[1]);
                }
            }
        }
    }

    private boolean articulation(int[] vert, int[] adjc, int[] prev) {
        return prev == null ? vert[2] >= 2 : vert[0] <= adjc[1];
    }

    private class Clock {

        private int value;

        Clock(int value) {
            this.value = value;
        }

        int getNextValue() {
            value = value + 1;
            return value;
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
