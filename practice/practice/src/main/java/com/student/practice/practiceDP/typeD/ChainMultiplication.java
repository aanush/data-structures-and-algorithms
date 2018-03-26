package com.student.practice.practiceDP.typeD;

public class ChainMultiplication {

    public static void main(String[] args) {
        ChainMultiplication app = new ChainMultiplication();
        int[] arr = new int[]{40, 20, 30, 10, 30};
        app.getPrintChainMultiplicationOrderAndMinimumStep(arr);
    }

    private void getPrintChainMultiplicationOrderAndMinimumStep(int[] arr) {
        Chain[][] arrChain = getChain(arr);
        getPrintChainMultiplicationOrder(arr, arrChain, 0, arr.length - 2);
        System.out.println("minimum step = " + arrChain[0][arr.length - 2].getMinimumStep());
    }

    private void getPrintChainMultiplicationOrder(int[] arr, Chain[][] arrChain, int startIndex, int lastIndex) {
        int pivot = arrChain[startIndex][lastIndex].getPivotIndex();
        if (pivot < 0) {
            return;
        }
        getPrintChainMultiplicationOrder(arr, arrChain, startIndex, pivot);
        getPrintChainMultiplicationOrder(arr, arrChain, pivot + 1, lastIndex);
        print(arr[startIndex], arr[pivot + 1], arr[pivot + 1], arr[lastIndex + 1]);
    }

    private void print(int rowCountM1, int columnCountN1, int rowCountM2, int columnCountN2) {
        System.out.print("(" + rowCountM1 + "cross" + columnCountN1 + ")" + " multiplied to ");
        System.out.print("(" + rowCountM2 + "cross" + columnCountN2 + ")" + " results ");
        System.out.print("(" + rowCountM1 + "cross" + columnCountN2 + ")" + " in step = ");
        System.out.print(rowCountM1 * columnCountN1 * columnCountN2);//System.out.print(rowCountM1 * rowCountM2 * columnCountN2);
        System.out.println();
    }

    private Chain[][] getChain(int[] arr) {

        final int maxLength = arr.length - 1;
        Chain[][] arrChain = new Chain[maxLength][maxLength];

        for (int current = 0; current <= maxLength - 1; current++) {
            arrChain[current][current] = new Chain(0, arr[current], arr[current + 1], -1);
        }

        for (int length = 1; length <= maxLength - 1; length++) {
            for (int startIndex = 0, lastIndex = startIndex + length; lastIndex <= maxLength - 1; startIndex++, lastIndex++) {
                int minimumStep = Integer.MAX_VALUE;
                Chain chain = null;
                for (int pivotIndex = startIndex; pivotIndex + 1 <= lastIndex; pivotIndex++) {
                    int m = arrChain[startIndex][pivotIndex].getRowCountM();
                    int k = arrChain[startIndex][pivotIndex].getColumnCountN();//== arrChain[pivotIndex + 1][lastIndex].getRowCountM()
                    int n = arrChain[pivotIndex + 1][lastIndex].getColumnCountN();
                    int currentStep = (m * k * n) + arrChain[startIndex][pivotIndex].getMinimumStep() + arrChain[pivotIndex + 1][lastIndex].getMinimumStep();
                    if (currentStep <= minimumStep) {
                        minimumStep = currentStep;
                        chain = new Chain(minimumStep, m, n, pivotIndex);
                    }
                }
                arrChain[startIndex][lastIndex] = chain;
            }
        }

        return arrChain;
    }

    private class Chain {
        private final int minimumStep;
        private final int rowCountM;
        private final int columnCountN;
        private final int pivotIndex;

        Chain(int minimumStep, int rowCountM, int columnCountN, int pivotIndex) {
            this.minimumStep = minimumStep;
            this.rowCountM = rowCountM;
            this.columnCountN = columnCountN;
            this.pivotIndex = pivotIndex;
        }

        int getMinimumStep() {
            return minimumStep;
        }

        int getRowCountM() {
            return rowCountM;
        }

        int getColumnCountN() {
            return columnCountN;
        }

        int getPivotIndex() {
            return pivotIndex;
        }
    }

}
