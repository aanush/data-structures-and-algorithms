package com.student.practice.done.practiceHeap.typeX;

import java.util.*;
import java.util.function.BiFunction;

public class BinaryHeapUsingMapAndArray {

    public static void main(String[] args) {
        BinaryHeapUsingMapAndArray app = new BinaryHeapUsingMapAndArray();
        BinaryHeapType type = BinaryHeapType.MAX_HEAP;
        BinaryHeap heap = app.new MapAndArrayBinaryHeap(type, 1);

        for (int i = 1; i <= 15; i++) {
            heap.put(app.new KeyAndValue(String.valueOf(i), i));
        }
        print(heap);
        //15,10,14,7,9,11,13,1,4,3,8,2,6,5,12,
        //15,10,14,7,9,11,13,1,4,3,8,2,6,5,12,
        //15,10,14,7,9,11,13,1,4,3,8,2,6,5,12,
        //15,10,14,7,9,11,13,1,4,3,8,2,6,5,12,

        for (int i = 1; i <= 15; i++) {
            heap.put(app.new KeyAndValue(String.valueOf(i), 16 - i));
        }
        print(heap);
        //1,3,2,4,8,6,5,7,10,9,15,11,14,13,12,
        //1,3,2,4,8,6,5,7,10,9,15,11,14,13,12,
        //15,13,14,12,8,10,11,9,6,7,1,5,2,3,4,
        //15,13,14,12,8,10,11,9,6,7,1,5,2,3,4,

        for (int i = 1; i <= 15; i++) {
            heap.put(app.new KeyAndValue(String.valueOf(i), 16 - i));
        }
        print(heap);
        //1,3,2,4,8,6,5,7,10,9,15,11,14,13,12,
        //1,3,2,4,8,6,5,7,10,9,15,11,14,13,12,
        //15,13,14,12,8,10,11,9,6,7,1,5,2,3,4,
        //15,13,14,12,8,10,11,9,6,7,1,5,2,3,4,

        for (int i = 1; i <= 15; i++) {
            heap.put(app.new KeyAndValue(String.valueOf(i), i));
        }
        print(heap);
        //15,14,13,8,9,12,11,2,3,6,7,4,5,10,1,
        //15,14,13,8,9,12,11,2,3,6,7,4,5,10,1,
        //15,14,13,8,9,12,11,2,3,6,7,4,5,10,1,
        //15,14,13,8,9,12,11,2,3,6,7,4,5,10,1,

        heap.deleteRootValue();
        heap.deleteRootValue();
        heap.deleteRootValue();
        heap.deleteRootValue();
        heap.deleteRootValue();
        print(heap);
        //10,9,5,8,7,4,1,2,3,6,
        //10,9,5,8,7,4,1,2,3,6,
        //10,9,5,8,7,4,1,2,3,6,
        //10,9,5,8,7,4,1,2,3,6,

        heap.delete(String.valueOf(6));
        heap.delete(String.valueOf(2));
        heap.delete(String.valueOf(5));
        print(heap);
        //10,9,4,8,7,3,1,
        //10,9,4,8,7,3,1,
        //10,9,4,8,7,3,1,
        //10,9,4,8,7,3,1,
    }

    private static void print(BinaryHeap heap) {
        System.out.println();
        Arrays.stream(heap.values()).filter(Objects::nonNull).map(KeyAndValue::getKey).forEach(key -> System.out.print(key + ","));
        System.out.println();
        Arrays.stream(heap.values()).filter(Objects::nonNull).map(KeyAndValue::getKey).map(heap::get).map(KeyAndValue::getKey).forEach(key -> System.out.print(key + ","));
        System.out.println();
        Arrays.stream(heap.values()).filter(Objects::nonNull).map(KeyAndValue::getValue).forEach(value -> System.out.print(value + ","));
        System.out.println();
        Arrays.stream(heap.values()).filter(Objects::nonNull).map(KeyAndValue::getKey).map(heap::get).map(KeyAndValue::getValue).forEach(value -> System.out.print(value + ","));
        System.out.println();
    }

    private enum BinaryHeapType {

        MIN_HEAP(Integer.MIN_VALUE, (top, alt) -> top < alt, (less, more) -> less),
        MAX_HEAP(Integer.MAX_VALUE, (top, alt) -> top > alt, (less, more) -> more);

        private final int extremeValue;
        private final BiFunction<Integer, Integer, Boolean> testFunction;
        private final BiFunction<Integer, Integer, Integer> pickFunction;

        BinaryHeapType(int extremeValue, BiFunction<Integer, Integer, Boolean> testFunction, BiFunction<Integer, Integer, Integer> pickFunction) {
            this.extremeValue = extremeValue;
            this.testFunction = testFunction;
            this.pickFunction = pickFunction;
        }

        int getExtremeValue() {
            return extremeValue;
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

        KeyAndValue[] values();

        boolean contains(String key);

        KeyAndValue get(String key);

        KeyAndValue getRootValue();

        KeyAndValue delete(String key);

        KeyAndValue deleteRootValue();

        void put(KeyAndValue keyAndValue);

    }

    private class MapAndArrayBinaryHeap implements BinaryHeap {

        private final BinaryHeapType type;
        private final int length;
        private final Map<String, Integer> map;
        private KeyAndValue[] arr;
        private int nextIndex;

        MapAndArrayBinaryHeap(BinaryHeapType type, int length) {
            if (type == null || length <= 0) {
                throw new IllegalArgumentException();
            }
            this.type = type;
            this.length = length;
            this.map = new HashMap<>();
            this.arr = new KeyAndValue[length];
            this.nextIndex = 0;
            Arrays.fill(arr, nextIndex, arr.length, null);
        }

        @Override
        public void clear() {
            map.clear();
            arr = new KeyAndValue[length];
            nextIndex = 0;
            Arrays.fill(arr, nextIndex, arr.length, null);
        }

        @Override
        public KeyAndValue[] values() {
            return Arrays.copyOf(arr, arr.length);
        }

        @Override
        public boolean contains(String key) {
            return map.containsKey(key);
        }

        @Override
        public KeyAndValue get(String key) {
            return Optional.ofNullable(key)
                    .map(map::get)
                    .map(index -> arr[index])
                    .orElse(null);
        }

        @Override
        public KeyAndValue getRootValue() {
            return arr[0];
        }

        @Override
        public KeyAndValue delete(String key) {
            if (map.containsKey(key)) {
                int index = map.get(key);
                KeyAndValue keyAndValue = arr[index];
                update(new KeyAndValue(key, type.getExtremeValue()));
                deleteRootValue();
                return keyAndValue;
            } else {
                return null;
            }
        }

        @Override
        public KeyAndValue deleteRootValue() {
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

        @Override
        public void put(KeyAndValue keyAndValue) {
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
                if (!type.testValue(arr[topIndex].getValue(), arr[currentIndex].getValue())) {
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
                        altIndex = type.pickIndex(leftIndex, rightIndex);
                    } else {
                        altIndex = type.pickIndex(rightIndex, leftIndex);
                    }
                }
                if (!type.testValue(arr[currentIndex].getValue(), arr[altIndex].getValue())) {
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

        private final String key;
        private final int value;

        KeyAndValue(String key, int value) {
            this.key = key;
            this.value = value;
        }

        String getKey() {
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
            return getValue() == that.getValue() && Objects.equals(getKey(), that.getKey());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }

    }

}
