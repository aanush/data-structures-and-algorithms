package com.student.practice.done.practiceDP.typeC;

public class RegexMatching {

    public static void main(String[] args) {
        RegexMatching app = new RegexMatching();
        String[] alphabet = "".split("");
        String[] regex = ".".split("");
        boolean match = app.getMatch(alphabet, regex);
        System.out.println(match);
    }

    private boolean getMatch(String[] alphabet, String[] pattern) {
        if (alphabet.length == 1 && alphabet[0].equals("")) {
            alphabet = new String[]{};
        }
        if (pattern.length == 1 && pattern[0].equals("")) {
            pattern = new String[]{};
        }
        boolean[][] matchingDP = getRegexMatchingDP(alphabet, pattern);
        return matchingDP[alphabet.length][pattern.length];
    }

    private boolean[][] getRegexMatchingDP(String[] alphabet, String[] pattern) {

        boolean[][] matchingDP = new boolean[alphabet.length + 1][pattern.length + 1];

        // row : y == 0; no pattern
        // column : x == 0; no alphabet
        matchingDP[0][0] = true;

        // row : y == 0; no pattern
        // and one or more alphabet
        for (int x = 1; x <= alphabet.length; x++) {
            matchingDP[x][0] = false;
        }

        if (1 <= pattern.length) {
            // row : y == 1; one pattern : pattern[0]
            // column : x == 0; no alphabet
            matchingDP[0][1] = pattern[0].equals("*");
            // sure ? // todo

            // row : y == 1; one pattern : pattern[0]
            // and one or more alphabet
            for (int x = 1; x <= alphabet.length; x++) {
                matchingDP[x][1] = false;
                if (pattern[0].equals("*")) {
                    matchingDP[x][1] = false;
                    // sure ? // todo
                }
                if (pattern[0].equals(".")) {
                    matchingDP[x][1] = matchingDP[x - 1][0];
                }
                if (pattern[0].equals(alphabet[x - 1])) {
                    matchingDP[x][1] = matchingDP[x - 1][0];
                }
            }
            //
        }

        if (2 <= pattern.length) {
            // row : y == 2 : two pattern : pattern[0] and pattern[1]
            // column : x == 0; no alphabet
            matchingDP[0][2] = pattern[1].equals("*");
        }

        // row : y >= 3 : three or more pattern : pattern[0], pattern[1], pattern[2] or more
        // column : x == 0; no alphabet
        for (int y = 3; y <= pattern.length; y++) {
            matchingDP[0][y] = pattern[y - 1].equals("*") && matchingDP[0][y - 2];
        }

        for (int x = 1; x <= alphabet.length; x++) {
            for (int y = 2; y <= pattern.length; y++) {
                //
                matchingDP[x][y] = false;
                if (pattern[y - 1].equals("*")) {
                    boolean matchesToZero = matchingDP[x][y - 2];
                    boolean matchesToMore = matchingDP[x - 1][y] && (pattern[y - 2].equals(".") || pattern[y - 2].equals(alphabet[x - 1]));
                    matchingDP[x][y] = matchesToZero || matchesToMore;
                }
                if (pattern[y - 1].equals(".")) {
                    matchingDP[x][y] = matchingDP[x - 1][y - 1];
                }
                if (pattern[y - 1].equals(alphabet[x - 1])) {
                    matchingDP[x][y] = matchingDP[x - 1][y - 1];
                }
                //
            }
        }

        return matchingDP;
    }

}
