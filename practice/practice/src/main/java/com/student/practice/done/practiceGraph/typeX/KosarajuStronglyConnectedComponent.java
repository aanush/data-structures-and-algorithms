package com.student.practice.done.practiceGraph.typeX;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class KosarajuStronglyConnectedComponent {

    public static void main(String[] args) {
        KosarajuStronglyConnectedComponent app = new KosarajuStronglyConnectedComponent();
        Set<Edge> edgeSet = new HashSet<>();
        Set<Integer> vertexSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2));
        edgeSet.add(app.new Edge(1, 5));
        edgeSet.add(app.new Edge(1, 8));
        edgeSet.add(app.new Edge(2, 3));
        edgeSet.add(app.new Edge(2, 7));
        edgeSet.add(app.new Edge(2, 9));
        edgeSet.add(app.new Edge(3, 1));
        edgeSet.add(app.new Edge(3, 2));
        edgeSet.add(app.new Edge(3, 4));
        edgeSet.add(app.new Edge(3, 6));
        edgeSet.add(app.new Edge(4, 5));
        edgeSet.add(app.new Edge(5, 2));
        edgeSet.add(app.new Edge(6, 4));
        edgeSet.add(app.new Edge(8, 9));
        edgeSet.add(app.new Edge(9, 8));
        vertexSet.add(1);
        vertexSet.add(2);
        vertexSet.add(3);
        vertexSet.add(4);
        vertexSet.add(5);
        vertexSet.add(6);
        vertexSet.add(7);
        vertexSet.add(8);
        vertexSet.add(9);
        Set<Set<Integer>> componentSet = app.getAllStronglyConnectedComponent(edgeSet, vertexSet);
        componentSet.forEach(component -> {
            component.forEach(System.out::println);
            System.out.println();
        });
    }

    private Set<Set<Integer>> getAllStronglyConnectedComponent(Set<Edge> edgeSet, Set<Integer> vertexSet) {
        Deque<Integer> dfsFinishedStack = getDFSFinishedStack(edgeSet, vertexSet);
        return getComponentSet(edgeSet, dfsFinishedStack);
    }

    private Deque<Integer> getDFSFinishedStack(Set<Edge> edgeSet, Set<Integer> vertexSet) {
        Map<Integer, Set<Edge>> adjacencyMap = edgeSet.stream().collect(Collectors.groupingBy(Edge::getTail, Collectors.toSet()));
        Set<Integer> visitedSet = new HashSet<>(vertexSet.size());
        Deque<Integer> dfsFinishedStack = new ArrayDeque<>(vertexSet.size());
        vertexSet.forEach(vertex -> consumeAfterDFS(vertex, adjacencyMap, visitedSet, dfsFinishedStack::push));
        return dfsFinishedStack;
    }

    private Set<Set<Integer>> getComponentSet(Set<Edge> edgeSet, Deque<Integer> dfsFinishedStack) {
        Map<Integer, Set<Edge>> reverseAdjacencyMap = edgeSet.stream().map(edge -> new Edge(edge.getHead(), edge.getTail())).collect(Collectors.groupingBy(Edge::getTail, Collectors.toSet()));
        Set<Integer> visitedSet = new HashSet<>(dfsFinishedStack.size());
        Set<Set<Integer>> componentSet = new HashSet<>();
        Set<Integer> component = new HashSet<>();
        while (!dfsFinishedStack.isEmpty()) {
            Integer vertex = dfsFinishedStack.pop();
            consumeAfterDFS(vertex, reverseAdjacencyMap, visitedSet, component::add);
            if (!component.isEmpty()) {
                componentSet.add(component);
                component = new HashSet<>();
            }
        }
        return componentSet;
    }

    private void consumeAfterDFS(Integer vertex, Map<Integer, Set<Edge>> adjacencyMap, Set<Integer> visitedSet, Consumer<Integer> consumer) {
        if (!visitedSet.contains(vertex)) {
            visitedSet.add(vertex);
            Optional.ofNullable(adjacencyMap.get(vertex))
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(Edge::getHead)
                    .forEach(head -> consumeAfterDFS(head, adjacencyMap, visitedSet, consumer));
            consumer.accept(vertex);
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
