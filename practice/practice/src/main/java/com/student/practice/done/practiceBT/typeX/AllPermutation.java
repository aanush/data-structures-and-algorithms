package com.student.practice.done.practiceBT.typeX;

import java.util.Arrays;
import java.util.function.Consumer;

public class AllPermutation {

    public static void main(String[] args) {
        AllPermutation app = new AllPermutation();
        Integer[] arr = {1, 2, 3};
        Consumer<Integer[]> consumer = ans -> System.out.println(Arrays.toString(ans));
        app.permute(arr, 0, consumer);
    }

    private void permute(Integer[] arr, int currIndex, Consumer<Integer[]> consumer) {
        // current node = current state of (arr, currIndex)
        if (currIndex == arr.length - 1) {
            // current node is a leaf node
            consumer.accept(arr);
        } else {
            // current node is not a leaf node
            // one by one go to all child node of current node as
            // go to one child node, dfs, come back to current node, go to other child node
            for (int nextIndex = currIndex; nextIndex <= arr.length - 1; nextIndex++) {
                // current node = current state of (arr, currIndex)
                Integer next = arr[nextIndex];
                Integer curr = arr[currIndex];
                // change state of (arr, currIndex) => go to a child node from current node
                arr[currIndex] = next;
                arr[nextIndex] = curr;
                currIndex++;
                // child node = current state of (arr, currIndex)
                // dfs
                permute(arr, currIndex, consumer);
                // change back state of (arr, currIndex) => come back to current node from child node
                currIndex--;
                arr[currIndex] = curr;
                arr[nextIndex] = next;
                // current node = current state of (arr, currIndex)
            }
        }
        // current node = current state of (arr, currIndex)
    }

}
