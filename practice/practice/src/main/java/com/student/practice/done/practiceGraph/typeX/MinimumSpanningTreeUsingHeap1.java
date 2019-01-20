package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class MinimumSpanningTreeUsingHeap1 {

    public static void main(String[] args) {
        MinimumSpanningTreeUsingHeap1 app = new MinimumSpanningTreeUsingHeap1();
        Set<Edge> edgeSet = new HashSet<>();
        edgeSet.add(app.new Edge(1, 2, 3));
        edgeSet.add(app.new Edge(2, 3, 1));
        edgeSet.add(app.new Edge(3, 4, 1));
        edgeSet.add(app.new Edge(4, 1, 1));
        edgeSet.add(app.new Edge(4, 5, 6));
        edgeSet.add(app.new Edge(5, 6, 2));
        edgeSet.add(app.new Edge(6, 3, 4));
        edgeSet.add(app.new Edge(2, 4, 3));
        edgeSet.add(app.new Edge(3, 5, 5));
        Set<Edge> mst = app.getMinimumSpanningTree(edgeSet);
    }

    private Set<Edge> getMinimumSpanningTree(Set<Edge> edgeSet) {
        Map<Integer, Set<Edge>> adjacencyMap = getAdjacencyMap(edgeSet);
        return getMinimumSpanningTree(adjacencyMap);
    }

    private Set<Edge> getMinimumSpanningTree(Map<Integer, Set<Edge>> adjacencyMap) {
        Set<Edge> mst = new HashSet<>();
        Map<Integer, Edge> vertexEdgeMap = new HashMap<>();
        MinimumBinaryHeap heap = new MinimumBinaryHeap(adjacencyMap.keySet().size());
        for (Integer vertex : adjacencyMap.keySet()) {
            heap.put(new KeyAndValue(vertex, Integer.MAX_VALUE));
        }
        while (!heap.isEmpty()) {
            KeyAndValue rootKeyAndValue = heap.deleteRootValue();
            Integer vertex = rootKeyAndValue.getKey();
            Edge edge = vertexEdgeMap.get(vertex);
            mst.add(edge);
            for (Edge adjacent : adjacencyMap.get(vertex)) {
                if (adjacent.getTail() != vertex) {
                    if (heap.contains(adjacent.getTail()) && heap.get(adjacent.getTail()).getValue() > adjacent.getCost()) {
                        heap.put(new KeyAndValue(adjacent.getTail(), adjacent.getCost()));
                        vertexEdgeMap.put(adjacent.getTail(), adjacent);
                    }
                }
                if (adjacent.getHead() != vertex) {
                    if (heap.contains(adjacent.getHead()) && heap.get(adjacent.getHead()).getValue() > adjacent.getCost()) {
                        heap.put(new KeyAndValue(adjacent.getHead(), adjacent.getCost()));
                        vertexEdgeMap.put(adjacent.getHead(), adjacent);
                    }
                }
            }
        }
        return mst;
    }

    private Map<Integer, Set<Edge>> getAdjacencyMap(Set<Edge> edgeSet) {
        Map<Integer, Set<Edge>> adjacencyMap = new HashMap<>();
        for (Edge edge : edgeSet) {
            adjacencyMap.putIfAbsent(edge.getTail(), new HashSet<>());
            adjacencyMap.putIfAbsent(edge.getHead(), new HashSet<>());
            adjacencyMap.get(edge.getTail()).add(edge);
            adjacencyMap.get(edge.getHead()).add(edge);
        }
        return adjacencyMap;
    }

    private class MinimumBinaryHeap {

        private final int length;
        private final Map<Integer, Integer> map;
        private KeyAndValue[] arr;
        private int nextIndex;

        MinimumBinaryHeap(int length) {
            if (length <= 0) {
                throw new IllegalArgumentException();
            }
            this.length = length;
            this.map = new HashMap<>();
            this.arr = new KeyAndValue[length];
            this.nextIndex = 0;
            Arrays.fill(arr, nextIndex, arr.length, null);
        }

        boolean isEmpty() {
            return nextIndex == 0;
        }

        boolean contains(Integer key) {
            return map.containsKey(key);
        }

        KeyAndValue get(Integer key) {
            return Optional.ofNullable(key)
                    .map(map::get)
                    .map(index -> arr[index])
                    .orElse(null);
        }

        KeyAndValue deleteRootValue() {
            reduceLength();
            nextIndex--;
            KeyAndValue top = arr[0];
            KeyAndValue bottom = arr[nextIndex];
            map.remove(top.getKey());
            map.put(bottom.getKey(), 0);
            arr[0] = bottom;
            arr[nextIndex] = null;
            heapifyBelow(0);
            return top;
        }

        void put(KeyAndValue keyAndValue) {
            if (Objects.nonNull(keyAndValue) && Objects.nonNull(keyAndValue.getKey())) {
                update(keyAndValue);
                insert(keyAndValue);
            }
        }

        private void update(KeyAndValue keyAndValue) {
            if (map.containsKey(keyAndValue.getKey())) {
                int index = map.get(keyAndValue.getKey());
                arr[index] = keyAndValue;
                heapifyAbove(index);
                heapifyBelow(index);
            }
        }

        private void insert(KeyAndValue keyAndValue) {
            if (!map.containsKey(keyAndValue.getKey())) {
                ensureLength();
                map.put(keyAndValue.getKey(), nextIndex);
                arr[nextIndex] = keyAndValue;
                heapifyAbove(nextIndex);
                nextIndex++;
            }
        }

        private void heapifyAbove(int currentIndex) {
            if (currentIndex > 0) {
                int topIndex = getTopIndex(currentIndex);
                if (arr[topIndex].getValue() > arr[currentIndex].getValue()) {
                    KeyAndValue current = arr[currentIndex];
                    KeyAndValue top = arr[topIndex];
                    map.put(top.getKey(), currentIndex);
                    arr[currentIndex] = top;
                    map.put(current.getKey(), topIndex);
                    arr[topIndex] = current;
                    heapifyAbove(topIndex);
                }
            }
        }

        private void heapifyBelow(int currentIndex) {
            int leftIndex = getLeftIndex(currentIndex);
            int rightIndex = getRightIndex(currentIndex);
            if (leftIndex <= nextIndex - 1) {
                int altIndex = leftIndex;
                if (rightIndex <= nextIndex - 1) {
                    if (arr[leftIndex].getValue() < arr[rightIndex].getValue()) {
                        altIndex = leftIndex;
                    } else {
                        altIndex = rightIndex;
                    }
                }
                if (arr[currentIndex].getValue() > arr[altIndex].getValue()) {
                    KeyAndValue current = arr[currentIndex];
                    KeyAndValue alt = arr[altIndex];
                    map.put(alt.getKey(), currentIndex);
                    arr[currentIndex] = alt;
                    map.put(current.getKey(), altIndex);
                    arr[altIndex] = current;
                    heapifyBelow(altIndex);
                }
            }
        }

        private void ensureLength() {
            if (nextIndex == arr.length) {
                arr = Arrays.copyOf(arr, 2 * arr.length);
                Arrays.fill(arr, nextIndex, arr.length, null);
            }
        }

        private void reduceLength() {
            if (length <= nextIndex && 4 * nextIndex <= arr.length) {
                arr = Arrays.copyOf(arr, arr.length / 2);
                Arrays.fill(arr, nextIndex, arr.length, null);
            }
        }

        private int getLeftIndex(int currentIndex) {
            return (2 * currentIndex) + 1;
        }

        private int getRightIndex(int currentIndex) {
            return (2 * currentIndex) + 2;
        }

        private int getTopIndex(int currentIndex) {
            return (currentIndex - 1) / 2;
        }

    }

    private class KeyAndValue {

        private final int key;
        private final int value;

        KeyAndValue(int key, int value) {
            this.key = key;
            this.value = value;
        }

        int getKey() {
            return key;
        }

        int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            KeyAndValue that = (KeyAndValue) obj;
            return getKey() == that.getKey() && getValue() == that.getValue();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }

    }

    private class Edge {

        private final int tail;
        private final int head;
        private final int cost;

        Edge(int tail, int head, int cost) {
            this.tail = Integer.min(tail, head);
            this.head = Integer.max(tail, head);
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
