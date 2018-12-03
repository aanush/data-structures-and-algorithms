package com.student.practice.done.practiceHeap.typeX;

import java.util.Arrays;
import java.util.function.BiFunction;

public class BinaryHeapUsingArray {

    public static void main(String[] args) {
        BinaryHeapUsingArray app = new BinaryHeapUsingArray();
        BinaryHeapType type = BinaryHeapType.MAX_HEAP;
        BinaryHeap heap = app.new ArrayBinaryHeap(type, 1);
        for (int i = 1; i <= 15; i++) {
            heap.insert(i);
        }
        System.out.println();
        Arrays.stream(heap.values()).filter(a -> a != type.getGarbageValue()).forEach(a -> System.out.print(a + ", "));
        //15, 10, 14, 7, 9, 11, 13, 1, 4, 3, 8, 2, 6, 5, 12,
        for (int i = 1; i <= 4; i++) {
            heap.deleteExtreme();
        }
        System.out.println();
        Arrays.stream(heap.values()).filter(a -> a != type.getGarbageValue()).forEach(a -> System.out.print(a + ", "));
        //11, 10, 6, 7, 9, 2, 5, 1, 4, 3, 8,
        for (int i = 0; i <= 10; i++) {
            heap.update(i, 2 * heap.get(i));
        }
        System.out.println();
        Arrays.stream(heap.values()).filter(a -> a != type.getGarbageValue()).forEach(a -> System.out.print(a + ", "));
        //22, 20, 12, 14, 18, 4, 10, 2, 8, 6, 16,
        for (int i = 0; i <= 10; i++) {
            heap.update(i, heap.get(i) / 2);
        }
        System.out.println();
        Arrays.stream(heap.values()).filter(a -> a != type.getGarbageValue()).forEach(a -> System.out.print(a + ", "));
        //20, 16, 10, 8, 9, 2, 3, 1, 3, 3, 2,
        heap.delete(0);
        heap.delete(4);
        heap.delete(7);
        heap.delete(5);
        System.out.println();
        Arrays.stream(heap.values()).filter(a -> a != type.getGarbageValue()).forEach(a -> System.out.print(a + ", "));
        //16, 9, 10, 8, 2, 3, 3,
    }

    private enum BinaryHeapType {

        MIN_HEAP(Integer.MIN_VALUE, Integer.MAX_VALUE, (top, alt) -> top < alt, (less, more) -> less),
        MAX_HEAP(Integer.MAX_VALUE, Integer.MIN_VALUE, (top, alt) -> top > alt, (less, more) -> more);

        private final int extremeValue;
        private final int garbageValue;
        private final BiFunction<Integer, Integer, Boolean> testFunction;
        private final BiFunction<Integer, Integer, Integer> pickFunction;

        BinaryHeapType(int extremeValue, int garbageValue, BiFunction<Integer, Integer, Boolean> testFunction, BiFunction<Integer, Integer, Integer> pickFunction) {
            this.extremeValue = extremeValue;
            this.garbageValue = garbageValue;
            this.testFunction = testFunction;
            this.pickFunction = pickFunction;
        }

        int getExtremeValue() {
            return extremeValue;
        }

        int getGarbageValue() {
            return garbageValue;
        }

        boolean testValue(int top, int alt) {
            return testFunction.apply(top, alt);
        }

        int pickIndex(int less, int more) {
            return pickFunction.apply(less, more);
        }

    }

    private interface BinaryHeap {

        void clear();

        int[] values();

        boolean search(int value);

        int getExtreme();

        int get(int index);

        void insert(int value);

        void update(int index, int value);

        int delete(int index);

        int deleteExtreme();

    }

    private class ArrayBinaryHeap implements BinaryHeap {

        private final BinaryHeapType type;
        private final int length;
        private int[] arr;
        private int nextIndex;

        ArrayBinaryHeap(BinaryHeapType type, int length) {
            if (type == null || length <= 0) {
                throw new IllegalArgumentException();
            }
            this.arr = new int[length];
            this.nextIndex = 0;
            this.type = type;
            this.length = length;
            Arrays.fill(arr, nextIndex, arr.length, type.getGarbageValue());
        }

        @Override
        public void clear() {
            arr = new int[length];
            nextIndex = 0;
            Arrays.fill(arr, nextIndex, arr.length, type.getGarbageValue());
        }

        @Override
        public int[] values() {
            return Arrays.copyOf(arr, arr.length);
        }

        @Override
        public boolean search(int value) {
            return Arrays.stream(arr).anyMatch(v -> v == value);
        }

        @Override
        public int getExtreme() {
            return arr[0];
        }

        @Override
        public int get(int index) {
            return arr[index];
        }

        @Override
        public void insert(int value) {
            ensureLength();
            arr[nextIndex] = value;
            heapifyAbove(nextIndex);
            nextIndex++;
        }

        @Override
        public void update(int index, int value) {
            if (0 <= index && index <= nextIndex - 1) {
                arr[index] = value;
                heapifyAbove(index);
                heapifyBelow(index);
            }
        }

        @Override
        public int delete(int index) {
            update(index, type.getExtremeValue());
            return deleteExtreme();
        }

        @Override
        public int deleteExtreme() {
            reduceLength();
            int value = arr[0];
            nextIndex--;
            arr[0] = arr[nextIndex];
            arr[nextIndex] = type.getGarbageValue();
            heapifyBelow(0);
            return value;
        }

        private void heapifyAbove(int currentIndex) {
            if (currentIndex > 0) {
                int topIndex = getTopIndex(currentIndex);
                if (!type.testValue(arr[topIndex], arr[currentIndex])) {
                    int temp = arr[currentIndex];
                    arr[currentIndex] = arr[topIndex];
                    arr[topIndex] = temp;
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
                    if (arr[leftIndex] < arr[rightIndex]) {
                        altIndex = type.pickIndex(leftIndex, rightIndex);
                    } else {
                        altIndex = type.pickIndex(rightIndex, leftIndex);
                    }
                }
                if (!type.testValue(arr[currentIndex], arr[altIndex])) {
                    int temp = arr[altIndex];
                    arr[altIndex] = arr[currentIndex];
                    arr[currentIndex] = temp;
                    heapifyBelow(altIndex);
                }
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

        private void ensureLength() {
            if (nextIndex == arr.length) {
                arr = Arrays.copyOf(arr, 2 * arr.length);
                Arrays.fill(arr, nextIndex, arr.length, type.getGarbageValue());
            }
        }

        private void reduceLength() {
            if (length <= nextIndex && 4 * nextIndex <= arr.length) {
                arr = Arrays.copyOf(arr, arr.length / 2);
                Arrays.fill(arr, nextIndex, arr.length, type.getGarbageValue());
            }
        }

    }

}
