package com.student.practice.done.practiceBT.typeX;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class AllCombination {

    public static void main(String[] args) {
        AllCombination app = new AllCombination();
        int[] arr = {1, 2, 3};
        Consumer<boolean[]> consumer = choice -> System.out.println(Arrays.toString(IntStream.range(0, arr.length).filter(i -> choice[i]).map(i -> arr[i]).toArray()));
        app.choose(arr.length, consumer);
    }

    private void choose(int n, Consumer<boolean[]> consumer) {
        boolean[] choice = new boolean[n];
        choose(choice, 0, consumer);
    }

    private void choose(boolean[] choice, int x, Consumer<boolean[]> consumer) {
        // now we will make choice to choose x or not to choose x
        if (x == choice.length) {
            // all choice made
            consumer.accept(choice);
        } else {
            // current node
            boolean bool = choice[x];
            // going to child node 1 : choosing x
            choice[x] = true;
            x++;
            // dfs at child node 1
            choose(choice, x, consumer);
            // coming back to current node from child node 1
            x--;
            choice[x] = bool;
            // going to child node 2 : not choosing x
            choice[x] = false;
            x++;
            // dfs at child node 2
            choose(choice, x, consumer);
            // coming back to current node from child node 2
            x--;
            choice[x] = bool;
            // current node
        }
    }

}
