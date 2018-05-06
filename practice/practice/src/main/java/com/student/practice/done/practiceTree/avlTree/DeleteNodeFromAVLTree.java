package com.student.practice.done.practiceTree.avlTree;

import java.util.Stack;

public class DeleteNodeFromAVLTree {

    public static void main(String[] args) {
        DeleteNodeFromAVLTree app = new DeleteNodeFromAVLTree();
        int h = 12;
        Stack<Integer> stack = new Stack<>();
        AVLNode root = null;
        for (int c = 1; c < (int) Math.pow(2, h); c++) {
            root = app.insert(root, c);
            stack.push(c);
        }
        System.out.println("size = " + stack.size() + ", height = " + root.getHeight());
        for (int c = 1; c < (int) Math.pow(2, h - 1) + 1; c++) {
            root = app.delete(root, stack.pop());
        }
        System.out.println("size = " + stack.size() + ", height = " + root.getHeight());
    }

    private AVLNode insert(AVLNode root, int value) {

        if (root == null) {
            return new AVLNode(value);
        }

        // make sure duplicate node is not inserted
        if (value == root.getValue()) {
            return root;
        }

        // during recursion :
        // height of left node and right node is self updated before
        // height of root is self updated
        if (value < root.getValue()) {
            root.setLeftNode(insert(root.getLeftNode(), value));
        }
        if (value > root.getValue()) {
            root.setRightNode(insert(root.getRightNode(), value));
        }

        // during rotation :
        // height of old root is self updated before
        // height of new root is self updated
        if (unbalanceLeftMinusRight(root) >= 2) {
            if (unbalanceLeftMinusRight(root.getLeftNode()) < 0) {
                root.setLeftNode(rotateLeft(root.getLeftNode()));
            }
            root = rotateRight(root);
        }
        if (unbalanceLeftMinusRight(root) <= -2) {
            if (unbalanceLeftMinusRight(root.getRightNode()) > 0) {
                root.setRightNode(rotateRight(root.getRightNode()));
            }
            root = rotateLeft(root);
        }

        return root;
    }

    private AVLNode delete(AVLNode root, int value) {

        if (root == null) {
            return null;
        }

        // during recursion :
        // height of left node and right node is self updated before
        // height of root is self updated
        if (value == root.getValue()) {
            if (root.getLeftNode() == null && root.getRightNode() == null) {
                return null;
            }
            if (root.getLeftNode() == null) {
                return root.getRightNode();
            }
            if (root.getRightNode() == null) {
                return root.getLeftNode();
            }
            AVLNode pred = root.getLeftNode();
            while (pred.getRightNode() != null) {
                pred = pred.getRightNode();
            }
            root.setValue(pred.getValue());
            pred.setValue(value);
            root.setLeftNode(delete(root.getLeftNode(), value));
        }
        if (value < root.getValue()) {
            root.setLeftNode(delete(root.getLeftNode(), value));
        }
        if (value > root.getValue()) {
            root.setRightNode(delete(root.getRightNode(), value));
        }

        // during rotation :
        // height of old root is self updated before
        // height of new root is self updated
        if (unbalanceLeftMinusRight(root) >= 2) {
            if (unbalanceLeftMinusRight(root.getLeftNode()) < 0) {
                root.setLeftNode(rotateLeft(root.getLeftNode()));
            }
            root = rotateRight(root);
        }
        if (unbalanceLeftMinusRight(root) <= -2) {
            if (unbalanceLeftMinusRight(root.getRightNode()) > 0) {
                root.setRightNode(rotateRight(root.getRightNode()));
            }
            root = rotateLeft(root);
        }

        return root;
    }

    private AVLNode rotateLeft(AVLNode root) {
        if (root.getRightNode() == null) {
            return root;
        }
        AVLNode newRoot = root.getRightNode();
        // height of old root is self updated
        root.setRightNode(newRoot.getLeftNode());
        // height of new root is self updated
        newRoot.setLeftNode(root);
        return newRoot;
    }

    private AVLNode rotateRight(AVLNode root) {
        if (root.getLeftNode() == null) {
            return root;
        }
        AVLNode newRoot = root.getLeftNode();
        // height of old root is self updated
        root.setLeftNode(newRoot.getRightNode());
        // height of new root is self updated
        newRoot.setRightNode(root);
        return newRoot;
    }

    private int unbalanceLeftMinusRight(AVLNode root) {
        int l = root.getLeftNode() != null ? root.getLeftNode().getHeight() : 0;
        int r = root.getRightNode() != null ? root.getRightNode().getHeight() : 0;
        return l - r;
    }

    private class AVLNode {

        private int value;
        private AVLNode leftNode;
        private AVLNode rightNode;
        private int height;

        AVLNode(int value) {
            this.value = value;
            this.height = 1;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        AVLNode getLeftNode() {
            return leftNode;
        }

        void setLeftNode(AVLNode leftNode) {
            this.leftNode = leftNode;
            // make sure height of "this" node is self updated
            setHeight();
        }

        AVLNode getRightNode() {
            return rightNode;
        }

        void setRightNode(AVLNode rightNode) {
            this.rightNode = rightNode;
            // make sure height of "this" node is self updated
            setHeight();
        }

        int getHeight() {
            return height;
        }

        void setHeight() {
            this.height = 1 + Integer.max(getLeftNode() != null ? getLeftNode().getHeight() : 0, getRightNode() != null ? getRightNode().getHeight() : 0);
        }

    }

}
