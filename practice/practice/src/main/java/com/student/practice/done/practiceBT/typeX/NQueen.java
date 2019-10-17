package com.student.practice.done.practiceBT.typeX;

import java.util.Arrays;
import java.util.function.Consumer;

public class NQueen {

    public static void main(String[] args) {
        NQueen app = new NQueen();
        Consumer<Integer[]> consumer = ans -> System.out.println(Arrays.toString(ans));
        app.place(4, consumer);
    }

    private void place(int n, Consumer<Integer[]> consumer) {
        Integer[] yrr = new Integer[n];
        for (int x = 0; x <= yrr.length - 1; x++) {
            place(yrr, x, consumer);
        }
    }

    private void place(Integer[] yrr, int x, Consumer<Integer[]> consumer) {
        if (x == yrr.length) {
            consumer.accept(yrr);
        } else {
            for (int y = 0; y <= yrr.length - 1; y++) {
                if (!attack(yrr, x, y)) {
                    Integer yrrx = yrr[x];
                    yrr[x] = y;
                    x++;
                    place(yrr, x, consumer);
                    x--;
                    yrr[x] = yrrx;
                }
            }
        }
    }

    private boolean attack(Integer[] yrr, int x2, int y2) {
        for (int x1 = 0; x1 <= x2 - 1; x1++) {
            if (yrr[x1] != null && attack(x1, yrr[x1], x2, y2)) {
                return true;
            }
        }
        return false;
    }

    private boolean attack(int x1, int y1, int x2, int y2) {
        return x1 == x2 || y1 == y2 || x1 - y1 == x2 - y2 || x1 + y1 == x2 + y2;
    }

}
