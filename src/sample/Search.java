package sample;

public class Search {

    public int straightSearch(String line, String subline){

        char[] text = line.toCharArray();
        char[] pattern = subline.toCharArray();

        int lineCount       =  text.length;
        int sublineCount    =  pattern.length;

        if(lineCount == 0 || sublineCount == 0){
            return -1;
        }
        int res = -1;

        for (int i = 0; i < lineCount - sublineCount + 1; i++) {
            for (int j = 0; j < sublineCount; j++) {
                if(pattern[j] != text[i+j]){
                    break;
                }else if(j == sublineCount - 1){
                    res = i;
                    break;
                }
            }
        }
        return res;
    }

    public static int[] prefixFunction(String s) {
        int[] p = new int[s.length()];
        int k = 0;
        for (int i = 1; i < s.length(); i++) {
            while (k > 0 && s.charAt(k) != s.charAt(i))
                k = p[k - 1];
            if (s.charAt(k) == s.charAt(i))
                ++k;
            p[i] = k;
        }
        return p;
    }

    public static int kmpMatcher(String s, String pattern) {
        int m = pattern.length();
        if (m == 0)
            return 0;
        int[] p = prefixFunction(pattern);
        for (int i = 0, k = 0; i < s.length(); i++)
            for (; ; k = p[k - 1]) {
                if (pattern.charAt(k) == s.charAt(i)) {
                    if (++k == m)
                        return i + 1 - m;
                    break;
                }
                if (k == 0)
                    break;
            }
        return -1;
    }

}

