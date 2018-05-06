package com.student.practice.done.practiceDP.typeA;

public class SubarrayMaximumProduct {

    public static void main(String[] args) {
        SubarrayMaximumProduct app = new SubarrayMaximumProduct();
        int[] arr = new int[]{-2, -3, -4, -1, 2, 1, 5, -3};
        ProductAndStartIndex[] maximumSumSubarrayDP = app.getMaximumProductSubarrayDP1(arr);
        app.getPrintSubarrayFromMaximumProductSubarrayDP1(arr, maximumSumSubarrayDP);
    }

    private void getPrintSubarrayFromMaximumProductSubarrayDP1(int[] arr, ProductAndStartIndex[] maximumProductSubarrayDP) {

        int lastIndex = 0;

        for (int current = 0; current <= arr.length - 1; current++) {
            if (maximumProductSubarrayDP[current].getPositiveProduct() > maximumProductSubarrayDP[lastIndex].getPositiveProduct()) {
                lastIndex = current;
            }
        }

        int startIndex = maximumProductSubarrayDP[lastIndex].getPositiveProductStartIndex();

        for (int current = startIndex; current <= lastIndex; current++) {
            System.out.println(arr[current]);
        }

    }

    private ProductAndStartIndex[] getMaximumProductSubarrayDP1(int[] arr) {

        ProductAndStartIndex[] maximumProductSubarrayDP = new ProductAndStartIndex[arr.length];

        maximumProductSubarrayDP[0] = new ProductAndStartIndex(0, ProductAndStartIndex.NO_INDEX, 0, ProductAndStartIndex.NO_INDEX);
        if (arr[0] > 0) {
            maximumProductSubarrayDP[0] = new ProductAndStartIndex(arr[0], 0, 0, ProductAndStartIndex.NO_INDEX);
        }
        if (arr[0] < 0) {
            maximumProductSubarrayDP[0] = new ProductAndStartIndex(0, ProductAndStartIndex.NO_INDEX, arr[0], 0);
        }

        for (int current = 1; current <= arr.length - 1; current++) {

            int positiveProduct = 0;
            int positiveProductStartIndex = ProductAndStartIndex.NO_INDEX;
            int negativeProduct = 0;
            int negativeProductStartIndex = ProductAndStartIndex.NO_INDEX;

            if (arr[current] > positiveProduct) {
                positiveProduct = arr[current];
                positiveProductStartIndex = current;
            }
            if (maximumProductSubarrayDP[current - 1].getPositiveProduct() * arr[current] > positiveProduct) {
                positiveProduct = maximumProductSubarrayDP[current - 1].getPositiveProduct() * arr[current];
                positiveProductStartIndex = maximumProductSubarrayDP[current - 1].getPositiveProductStartIndex();
            }
            if (maximumProductSubarrayDP[current - 1].getNegativeProduct() * arr[current] > positiveProduct) {
                positiveProduct = maximumProductSubarrayDP[current - 1].getNegativeProduct() * arr[current];
                positiveProductStartIndex = maximumProductSubarrayDP[current - 1].getNegativeProductStartIndex();
            }

            if (arr[current] < negativeProduct) {
                negativeProduct = arr[current];
                negativeProductStartIndex = current;
            }
            if (maximumProductSubarrayDP[current - 1].getPositiveProduct() * arr[current] < negativeProduct) {
                negativeProduct = maximumProductSubarrayDP[current - 1].getPositiveProduct() * arr[current];
                negativeProductStartIndex = maximumProductSubarrayDP[current - 1].getPositiveProductStartIndex();
            }
            if (maximumProductSubarrayDP[current - 1].getNegativeProduct() * arr[current] < negativeProduct) {
                negativeProduct = maximumProductSubarrayDP[current - 1].getNegativeProduct() * arr[current];
                negativeProductStartIndex = maximumProductSubarrayDP[current - 1].getNegativeProductStartIndex();
            }

            maximumProductSubarrayDP[current] = new ProductAndStartIndex(positiveProduct, positiveProductStartIndex, negativeProduct, negativeProductStartIndex);
        }

        return maximumProductSubarrayDP;
    }

    private class ProductAndStartIndex {

        private static final int NO_INDEX = -1;

        private final int positiveProduct;
        private final int positiveProductStartIndex;
        private final int negativeProduct;
        private final int negativeProductStartIndex;

        ProductAndStartIndex(int positiveProduct, int positiveProductStartIndex, int negativeProduct, int negativeProductStartIndex) {
            this.positiveProduct = positiveProduct;
            this.positiveProductStartIndex = positiveProductStartIndex;
            this.negativeProduct = negativeProduct;
            this.negativeProductStartIndex = negativeProductStartIndex;
        }

        int getPositiveProduct() {
            return positiveProduct;
        }

        int getPositiveProductStartIndex() {
            return positiveProductStartIndex;
        }

        int getNegativeProduct() {
            return negativeProduct;
        }

        int getNegativeProductStartIndex() {
            return negativeProductStartIndex;
        }

    }

}
