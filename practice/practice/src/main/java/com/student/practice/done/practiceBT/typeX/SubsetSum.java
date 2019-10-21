package com.student.practice.done.practiceBT.typeX;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SubsetSum {

    public static void main(String[] args) {
        SubsetSum app = new SubsetSum();
        int[] arr = {5, 7, 8, 10, 12, 13, 15, 18};
        int sum = 30;
        Consumer<boolean[]> consumer = bbb -> System.out.println(Arrays.toString(IntStream.range(0, arr.length).filter(i -> bbb[i]).map(i -> arr[i]).toArray()));
        app.choose(arr, sum, consumer);
    }

    private void choose(int[] arr, int sum, Consumer<boolean[]> consumer) {
        boolean[] choice = new boolean[arr.length];
        choose(choice, 0, 0, arr, sum, consumer);
    }

    private void choose(boolean[] choice, int x, int s, int[] arr, int sum, Consumer<boolean[]> consumer) {
        // now we will make choice to choose or not to choose arr[x], s = chosen sum up to index x - 1
        if (x == arr.length) {
            // all choice made
            if (s == sum) {
                consumer.accept(choice);
            }
        } else {
            // current node
            boolean bool = choice[x];
            // going to child 1 : choosing arr[x]
            choice[x] = true;
            s = s + arr[x];
            x++;
            // dfs at child 1
            choose(choice, x, s, arr, sum, consumer);
            // coming back to current node from child 1
            x--;
            s = s - arr[x];
            choice[x] = bool;
            // going to child 2 : not choosing arr[x]
            choice[x] = false;
            x++;
            choose(choice, x, s, arr, sum, consumer);
            // coming back to current node from child 2
            x--;
            choice[x] = bool;
        }
    }

}
