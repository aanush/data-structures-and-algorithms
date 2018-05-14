package com.student.practice.done.practiceDP.typeX;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DecodeWaysOneCipherManyValue {

    public static void main(String[] args) {
        DecodeWaysOneCipherManyValue app = new DecodeWaysOneCipherManyValue();
        Set<String> allCipher = new HashSet<>();
        allCipher.add("1");
        allCipher.add("2");
        allCipher.add("3");
        allCipher.add("4");
        allCipher.add("5");
        allCipher.add("6");
        allCipher.add("7");
        allCipher.add("8");
        allCipher.add("9");
        allCipher.add("10");
        allCipher.add("11");
        allCipher.add("12");
        allCipher.add("13");
        allCipher.add("14");
        allCipher.add("15");
        allCipher.add("16");
        allCipher.add("17");
        allCipher.add("18");
        allCipher.add("19");
        allCipher.add("20");
        allCipher.add("21");
        allCipher.add("22");
        allCipher.add("23");
        allCipher.add("24");
        allCipher.add("25");
        allCipher.add("26");
        int ways = app.getDecodeWaysCount("********", allCipher);
        System.out.println(ways);//123775776
    }

    private int getDecodeWaysCount(String info, Set<String> allCipher) {

        if (info == null || info.length() == 0) {
            return 0;
        }

        // sort on length and parseInt value
        SortedSet<String> sortedAllCipher = new TreeSet<>(this::getCompareCipher);
        sortedAllCipher.addAll(allCipher);

        if (info.length() == 1) {
            return getCipherWays(0, 0, info, sortedAllCipher);
        }

        int cipherMinLength = Integer.max(sortedAllCipher.first().length(), 1);
        int cipherMaxLength = Integer.min(sortedAllCipher.last().length(), info.length());

        int[] waysDP = getDecodeWaysDP(info, sortedAllCipher, cipherMinLength, cipherMaxLength);

        return waysDP[info.length() - 1];
    }

    private int[] getDecodeWaysDP(String info, SortedSet<String> sortedAllCipher, int cipherMinLength, int cipherMaxLength) {

        int[] waysDP = new int[info.length()];

        waysDP[0] = getCipherWays(0, 0, info, sortedAllCipher);
        waysDP[1] = getCipherWays(1, 1, info, sortedAllCipher) * waysDP[0] + getCipherWays(0, 1, info, sortedAllCipher);

        for (int x = 2; x <= info.length() - 1; x++) {
            int ways = 0;
            for (int len = cipherMinLength, w = x - len + 1; len <= Integer.min(cipherMaxLength, x + 1); len++, w--) {
                int waysWToX = getCipherWays(w, x, info, sortedAllCipher);
                int ways0ToWMinus1 = (w - 1 >= 0) ? waysDP[w - 1] : 1;
                ways = ways + (waysWToX * ways0ToWMinus1);
            }
            waysDP[x] = ways;
        }

        return waysDP;
    }

    private int getCipherWays(int w, int x, String info, SortedSet<String> sortedAllCipher) {

        String cipher = getCipher(info, w, x);

        if ("*".equals(cipher)) {
            return 9;
        }

        if ("1*".equals(cipher)) {
            return 9;
        }
        if ("2*".equals(cipher)) {
            return 6;
        }

        if ("*0".equals(cipher)) {
            return 2;
        }
        if ("*1".equals(cipher)) {
            return 2;
        }
        if ("*2".equals(cipher)) {
            return 2;
        }
        if ("*3".equals(cipher)) {
            return 2;
        }
        if ("*4".equals(cipher)) {
            return 2;
        }
        if ("*5".equals(cipher)) {
            return 2;
        }
        if ("*6".equals(cipher)) {
            return 2;
        }
        if ("*7".equals(cipher)) {
            return 1;
        }
        if ("*8".equals(cipher)) {
            return 1;
        }
        if ("*9".equals(cipher)) {
            return 1;
        }

        if ("**".equals(cipher)) {
            return 15;
        }

        if (cipher.contains("*")) {
            return 0;
        }

        return sortedAllCipher.contains(cipher) ? 1 : 0;
    }

    private String getCipher(String info, int start, int last) {
        // beginIndex is inclusive but endIndex is exclusive in method substring
        return info.substring(start, last + 1);
    }

    private int getCompareCipher(String cipher1, String cipher2) {
        int compare = cipher1.length() - cipher2.length();
        if (compare == 0) {
            compare = Integer.parseInt(cipher1) - Integer.parseInt(cipher2);
        }
        return compare;
    }

    // study more
    // https://leetcode.com/problems/decode-ways-ii/description/

}
