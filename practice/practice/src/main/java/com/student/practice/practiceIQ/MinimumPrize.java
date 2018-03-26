package com.student.practice.practiceIQ;

class MinimumPrize {

    public static void main(String[] args) {
        MinimumPrize app = new MinimumPrize();
        int[] arrScore = new int[]{15, 15, 18, 16, 20, 28, 30, 14, 12, 10, 15, 13, 17, 16, 13};
        int[] arrPrize = app.getPrize(arrScore);
        for (int x = 0; x <= arrScore.length - 1; x++) {
            System.out.println("score " + arrScore[x] + " gets " + arrPrize[x] + " prize");
        }
    }

    public int[] getPrize(int[] arrScore) {

        int[] arrPrize = new int[arrScore.length];

        for (int x = 0; x <= arrPrize.length - 1; x++) {
            arrPrize[x] = 1;
        }

        for (int x = 1; x <= arrPrize.length - 1; x++) {
            if (arrScore[x] == arrScore[x - 1]) {
                arrPrize[x] = arrPrize[x - 1];
            }
            if (arrScore[x] > arrScore[x - 1]) {
                arrPrize[x] = arrPrize[x - 1] + 1;
            }
        }

        for (int x = arrPrize.length - 2; x >= 0; x--) {
            if (arrScore[x] == arrScore[x + 1]) {
                arrPrize[x] = arrPrize[x + 1];
            }
            if (arrScore[x] > arrScore[x + 1]) {
                arrPrize[x] = arrPrize[x + 1] + 1;
            }
        }

        return arrPrize;
    }

}