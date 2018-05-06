package com.student.practice.done.practiceGraph.typeX;

import java.util.*;

public class TopologicalSortOfNodeList {

    public static void main(String[] args) {
        TopologicalSortOfNodeList app = new TopologicalSortOfNodeList();
        int c = 1;
        ManyNode node1 = app.new ManyNode(c, c++);
        ManyNode node2 = app.new ManyNode(c, c++);
        ManyNode node3 = app.new ManyNode(c, c++);
        ManyNode node4 = app.new ManyNode(c, c++);
        ManyNode node5 = app.new ManyNode(c, c++);
        ManyNode node6 = app.new ManyNode(c, c++);
        ManyNode node7 = app.new ManyNode(c, c++);
        ManyNode node8 = app.new ManyNode(c, c++);
        node1.setNodeList(Arrays.asList(node3));
        node2.setNodeList(Arrays.asList(node3, node4));
        node3.setNodeList(Arrays.asList(node5));
        node4.setNodeList(Arrays.asList(node6));
        node5.setNodeList(Arrays.asList(node6, node8));
        node6.setNodeList(Arrays.asList(node7));
        List<ManyNode> unsortedNodeList = Collections.unmodifiableList(Arrays.asList(node1, node2, node3, node4, node5, node6, node7, node8));
        app.getPrintSortTopological(unsortedNodeList);
    }

    private void getPrintSortTopological(List<ManyNode> unsortedNodeList) {
        Stack<ManyNode> sortedNodeStack = new Stack<>();
        TreeSet<ManyNode> visitedNodeSet = new TreeSet<>();
        sortTopological(unsortedNodeList, sortedNodeStack, visitedNodeSet);
        sortedNodeStack.stream().forEach(node -> System.out.println(node.getKey()));
    }

    private void sortTopological(List<ManyNode> unsortedNodeList, Stack<ManyNode> sortedNodeStack, TreeSet<ManyNode> visitedNodeSet) {
        //
        if (unsortedNodeList == null || unsortedNodeList.size() == 0) {
            return;
        }
        // loop and select an unvisited node,
        // visit and add the selected node into the visited set
        // recursively sort (visit and push) the child node list of the selected node
        // push the selected node into the sorted stack
        for (ManyNode selectedNode : unsortedNodeList) {
            if (!visitedNodeSet.contains(selectedNode)) {
                visitedNodeSet.add(selectedNode);
                sortTopological(selectedNode.getNodeList(), sortedNodeStack, visitedNodeSet);
                sortedNodeStack.push(selectedNode);
            }
        }
        // visited set and sorted stack has been modified
    }

    private class ManyNode implements Comparable<ManyNode> {

        private int key;
        private int value;
        private List<ManyNode> nodeList;

        ManyNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        int getKey() {
            return key;
        }

        void setKey(int key) {
            this.key = key;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        List<ManyNode> getNodeList() {
            return nodeList;
        }

        void setNodeList(List<ManyNode> nodeList) {
            this.nodeList = nodeList;
        }

        @Override
        public int compareTo(ManyNode that) {
            return getKey() - that.getKey();
        }

    }

}
