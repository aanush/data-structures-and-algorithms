package com.student.practice.done.practiceDP.typeC;

public class WildcardMatching {

    public static void main(String[] args) {
        WildcardMatching app = new WildcardMatching();
        String[] alphabet = "wildcard matching".split("");
        String[] wildcard = "w?*??card***m*ing".split("");
        boolean[][] matchingDP = app.getWildcardMatchingDP(alphabet, wildcard);
        System.out.println(matchingDP[alphabet.length][wildcard.length]);
    }

    private boolean[][] getWildcardMatchingDP(String[] alphabet, String[] wildcard) {

        boolean[][] matchingDP = new boolean[alphabet.length + 1][wildcard.length + 1];

        // row : y == 0; no wildcard
        // column : x == 0; no alphabet
        matchingDP[0][0] = true;

        // row : y == 0; no wildcard
        for (int x = 1; x <= alphabet.length; x++) {
            matchingDP[x][0] = false;
        }

        // column : x == 0; no alphabet
        for (int y = 1; y <= wildcard.length; y++) {
            matchingDP[0][y] = wildcard[y - 1].equals("*") && matchingDP[0][y - 1];
        }

        for (int x = 1; x <= alphabet.length; x++) {
            for (int y = 1; y <= wildcard.length; y++) {
                //
                matchingDP[x][y] = false;
                //
                if (wildcard[y - 1].equals("*")) {
                    boolean matchesToZero = matchingDP[x][y - 1];
                    boolean matchesToMore = matchingDP[x - 1][y];
                    matchingDP[x][y] = matchesToZero || matchesToMore;
                }
                //
                if (wildcard[y - 1].equals("?")) {
                    boolean matchesToExactlyOne = matchingDP[x - 1][y - 1];
                    matchingDP[x][y] = matchesToExactlyOne;
                }
                //
                if (wildcard[y - 1].equals(alphabet[x - 1])) {
                    boolean matchesToExactlyOne = matchingDP[x - 1][y - 1];
                    matchingDP[x][y] = matchesToExactlyOne;
                }
                //
            }
        }

        return matchingDP;
    }

}
