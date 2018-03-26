package com.student.practice.practiceDP.typeD;

public class Palindrome {

    public static void main(String[] args) {
        Palindrome app = new Palindrome();
        int[] arr = new int[]{2, 1, 2, 0, 0, 2, 0, 0};
        app.getPrintAllPalindrome3(arr);

    }

    private void getPrintAllPalindrome3(int[] arr) {

        boolean[][] palindromeDP = getPalindromeDP2(arr);

        for (int length = 0; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                if (palindromeDP[start][last]) {
                    print1(arr, start, last);
                }
            }
        }

    }

    private void print1(int[] arr, int startIndex, int lastIndex) {
        for (int currentIndex = startIndex; currentIndex <= lastIndex; currentIndex++) {
            System.out.print(arr[currentIndex] + ", ");
        }
        System.out.println();
    }

    private boolean[][] getPalindromeDP2(int[] arr) {

        boolean[][] palindromeDP = new boolean[arr.length][arr.length];

        // length = 0
        for (int length = 0; length <= 0; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = true;
            }
        }

        // length = 1
        for (int length = 1; length <= 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = (arr[start] == arr[last]);
            }
        }

        // length >= 2
        for (int length = 2; length <= arr.length - 1; length++) {
            for (int start = 0, last = start + length; last <= arr.length - 1; start++, last++) {
                palindromeDP[start][last] = (arr[start] == arr[last]) && palindromeDP[start + 1][last - 1];
            }
        }

        return palindromeDP;
    }
}
