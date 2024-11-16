package com.codemaster.io;
public class PalindromicSubstrings {
    private static Boolean[][] dp;

    public static void main(String[] args) {
        String str = "abcba";
        System.out.println("Total Palindromic Substrings: " + countPalindromicSubstrings(str));
    }

    public static int countPalindromicSubstrings(String str) {
        int n = str.length();
        dp = new Boolean[n][n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(str, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPalindrome(String str, int start, int end) {
        if (start >= end) return true; // Base case: single character or empty substring
        if (dp[start][end] != null) return dp[start][end]; // Use memoized result

        if(str.charAt(start) != str.charAt(end)) dp[start][end] = false;
        else dp[start][end] = isPalindrome(str, start + 1, end - 1);

        return dp[start][end];
    }
}
