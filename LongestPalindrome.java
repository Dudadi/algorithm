package String;

import java.util.Arrays;

public class LongestPalindrome {
    public static String longestPalindrome (String str) {
        int length = str.length();
        if(length <2) {
            return str;
        }
        int start = 0,end = 0;
        for(int i=0;i<length;i++) {
            int oddLength = centerPalindromeLength(str, i,i);
            int evenLength = centerPalindromeLength(str, i,i+1);
            if(oddLength > end - start +1) {
                start = i - oddLength/2;
                end = i + oddLength/2;
            }
            if(evenLength > end - start+1) {
                start = i-evenLength/2 +1;
                end = i+evenLength/2;
            }
        }
        return str.substring(start,end+1);
    }

    public static int centerPalindromeLength (String str, int start, int end) {

        while(start >=0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
            start--;
            end++;
        }
        return end-start+1;
    }

    public static String manacher(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('%');
        for(char ch: str.toCharArray()) {
            sb.append(ch);
            sb.append('%');
        }
        int maxCenter = 0, maxLength = 0, n = sb.length();
        int[] len = new int[n];
        for(int i=0, center=0, right = 0;i<n;i++) {
            if(i<right) {
                len[i] = Math.min(len[2*center-i], right-i);
            }
            while(i-len[i]-1>=0 && i+len[i]+1<n && sb.charAt(i-len[i]-1) == sb.charAt(i+len[i]+1)) {
                len[i]++;
            }
            if(i+len[i] > right) {
                right = i+len[i];
                center = i;
            }
            if(len[i] > maxLength) {
                maxLength = len[i];
                maxCenter = i;
            }
        }
        return str.substring((maxCenter-maxLength)/2, (maxCenter+maxLength)/2);
    }

    public static String longestPalindrome_dp (String str) {
        int length = str.length();
        int start=0,end =0,maxLength = 0;
        boolean[][] dp = new boolean[length][length];
        for(int i=0;i<length;i++) {
            dp[i][i] = true;
        }
        for(int j=0;j<length;j++) {
            for(int i=0;i<j;i++) {
                if(str.charAt(i) == str.charAt(j)) {
                    if(j <= i+2) {
                        dp[i][j] = true;
                    }else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
                if(dp[i][j] &&j-i+1 > maxLength) {
                    start = i;
                    end = j;
                    maxLength = j-i+1;
                }
            }
        }
        return str.substring(start,end+1);
    }

    public static void main(String[] args) {
            System.out.println("Hello world!");

            String[] strs = {"babad","cbbd","abcd", "abaxabaxabb", "abaxabaxabybaxabyb"};
            String[] res = {"bab", "bb","a", "baxabaxab", "baxabybaxab"};

            for(int i=0;i<strs.length;i++) {
                System.out.println("\nTest: " + i);
                System.out.println("数组: " + strs[i]);
                String ret = manacher(strs[i]);
                System.out.println("结果：" + ret);
                System.out.println("正确值：" + res[i]);
                System.out.println("是否相等： "+ (ret.equals(res[i])));
            }
    }
}
