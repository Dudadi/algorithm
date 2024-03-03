package String;

class KMP {
    public int strStr(String haystack, String needle) {
        int[] next = getNext(needle);
        
        int i=0,j=0,m=haystack.length(), n=needle.length();
        while(i<m && j<n) {
            if(j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                j++;
                i++;
            } else {
                j = next[j];
            }
        }
        return j == n ? i-j : -1;
    }
    
    public int[] getNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n+1];
        next[0] = -1;
        for(int i=0,j=-1;i<n;) {
            if(j==-1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        for(int i=0;i<n;i++) {
            System.out.print(next[i] + " ");
        }
        return next;
    }
}
