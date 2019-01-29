package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class FordFulkersonEdmondsKarpFlowUsingBFSInsideDirectedGraph1 {

    public static void main(String[] args) {
        FordFulkersonEdmondsKarpFlowUsingBFSInsideDirectedGraph1 app = new FordFulkersonEdmondsKarpFlowUsingBFSInsideDirectedGraph1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2, 3, 3));
        edgeSet.add(app.new Edge(1, 4, 3, 3));
        edgeSet.add(app.new Edge(2, 3, 4, 4));
        edgeSet.add(app.new Edge(3, 1, 3, 3));
        edgeSet.add(app.new Edge(3, 4, 1, 1));
        edgeSet.add(app.new Edge(3, 5, 2, 2));
        edgeSet.add(app.new Edge(4, 5, 2, 2));
        edgeSet.add(app.new Edge(4, 6, 6, 6));
        edgeSet.add(app.new Edge(5, 2, 1, 1));
        edgeSet.add(app.new Edge(5, 7, 1, 1));
        edgeSet.add(app.new Edge(6, 7, 9, 9));
        Set<FlowPath> flowPathSet = app.getFlowPathSet(1, 7, edgeSet);
    }

    private Set<FlowPath> getFlowPathSet(Integer source, Integer target, Set<Edge> edgeSet) {
        Set<Edge> moreEdgeSet = new HashSet<>();
        for (Edge edge : edgeSet) {
            moreEdgeSet.add(new Edge(edge.getTail(), edge.getHead(), edge.getMark(), edge.getRest()));
            moreEdgeSet.add(new Edge(edge.getHead(), edge.getTail(), edge.getMark(), 0));
        }
        Map<Integer, Map<Integer, Edge>> adjacencyMap = getAdjacencyMap(moreEdgeSet);
        return getFlowPathSet(source, target, adjacencyMap);
    }

    private Set<FlowPath> getFlowPathSet(Integer source, Integer target, Map<Integer, Map<Integer, Edge>> adjacencyMap) {
        Set<FlowPath> flowPathSet = new HashSet<>();
        FlowPath flowPath = getFlowPath(source, target, adjacencyMap);
        while (flowPath != null) {
            flowPathSet.add(flowPath);
            flowPath = getFlowPath(source, target, adjacencyMap);
        }
        return flowPathSet;
    }

    private FlowPath getFlowPath(Integer source, Integer target, Map<Integer, Map<Integer, Edge>> adjacencyMap) {
        Map<Integer, Integer> visitedMap = new HashMap<>();
        bfsAndDetectFlow(source, target, adjacencyMap, visitedMap);
        if (visitedMap.containsKey(target)) {
            Set<Edge> path = new HashSet<>();
            Integer head = target;
            Integer tail = visitedMap.get(target);
            while (tail != null) {
                Edge edge = adjacencyMap.get(tail).get(head);
                path.add(edge);
                head = tail;
                tail = visitedMap.get(tail);
            }
            int flow = path.stream().map(Edge::getRest).mapToInt(Integer::intValue).min().orElse(0);
            path.stream().forEach(edge -> edge.setRest(edge.getRest() - flow));
            path.stream().map(edge -> adjacencyMap.get(edge.getHead()).get(edge.getTail())).forEach(edge -> edge.setRest(edge.getRest() + flow));
            return new FlowPath(flow, path);
        } else {
            return null;
        }
    }

    private void bfsAndDetectFlow(Integer source, Integer target, Map<Integer, Map<Integer, Edge>> adjacencyMap, Map<Integer, Integer> visitedMap) {
        Queue<Integer> bfsQueue = new LinkedList<>();
        if (!visitedMap.containsKey(source)) {
            visitedMap.put(source, null);
            bfsQueue.offer(source);
        }
        while (!bfsQueue.isEmpty() && !visitedMap.containsKey(target)) {
            Integer current = bfsQueue.poll();
            for (Edge adjacent : adjacencyMap.get(current).values()) {
                if (adjacent.getRest() > 0) {
                    if (!visitedMap.containsKey(adjacent.getHead())) {
                        visitedMap.put(adjacent.getHead(), current);
                        bfsQueue.offer(adjacent.getHead());
                    }
                }
            }
        }
    }

    private Map<Integer, Map<Integer, Edge>> getAdjacencyMap(Set<Edge> edgeSet) {
        Map<Integer, Map<Integer, Edge>> adjacencyMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            adjacencyMap.putIfAbsent(edge.getTail(), new HashMap<>());
            adjacencyMap.get(edge.getTail()).put(edge.getHead(), edge);
        }
        return adjacencyMap;
    }

    private class FlowPath {

        private final int flow;
        private final Set<Edge> path;

        FlowPath(int flow, Set<Edge> path) {
            this.flow = flow;
            this.path = path;
        }

        int getFlow() {
            return flow;
        }

        Set<Edge> getPath() {
            return path;
        }

    }

    private class Edge {

        private final int tail;
        private final int head;
        private final int mark;
        private int rest;

        Edge(int tail, int head, int mark, int rest) {
            this.tail = tail;
            this.head = head;
            this.mark = mark;
            this.rest = rest;
        }

        int getTail() {
            return tail;
        }

        int getHead() {
            return head;
        }

        int getMark() {
            return mark;
        }

        int getRest() {
            return rest;
        }

        void setRest(int rest) {
            this.rest = rest;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Edge edge = (Edge) obj;
            return getTail() == edge.getTail() && getHead() == edge.getHead();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTail(), getHead());
        }

    }

}
