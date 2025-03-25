// In this dp approach, taking a 2D dp table and storing whether the subproblem string matches the subproblem pattern or not. If the
// char at p matches the char at s, they both cancel each other and remaining is the subproblem that we have already solved and the 
// ans to it lies in the diagonal place. If the chars dont match, simply false. If there is *, then we have 0-case and 1-case. The 
// ans to 0-case lies right above, and ans to 1-case lies on the left. If it is first column then we only have access to 0-case, there
// if no left value. Let's take pattern on row side and string on column side.

// Time Complexity : O(m*n)
// Space Complexity : O(m*n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Dp solution:
class Solution {
    public boolean isMatch(String s, String p) {
        // Base case
        if (s.equals(p)) {
            return true;
        }
        if (p == null || p.length() == 0) {
            return false;
        }
        // Pattern on row side and s on column side
        int m = p.length();
        int n = s.length();
        // Declare a dp matrix
        boolean[][] dp = new boolean[m + 1][n + 1];
        // The first cell, that is ans to empty string matches empty string, mark it
        // true
        dp[0][0] = true;
        // Loop from second row because first row will be all false, because on pattern
        // side we have empty string, and we cannot match anything to the empty string
        for (int i = 1; i < m + 1; i++) {
            // Start from first column
            for (int j = 0; j < n + 1; j++) {
                // Check if it is not a star
                if (p.charAt(i - 1) != '*') {
                    // Either it is char or ?, in both case if the char matches
                    if (j > 0 && (p.charAt(i - 1) == s.charAt(j - 1) || p.charAt(i - 1) == '?')) {
                        // Ans lies in diagonal, else the boolean matrix will by default have false
                        // values
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
                // Else it is a '*'
                else {
                    // 0-case always available
                    dp[i][j] = dp[i - 1][j];
                    // And if j>0
                    if (j > 0) {
                        // Do or between 0-case and 1-case
                        dp[i][j] = dp[i][j] || dp[i][j - 1];
                    }
                }
            }
        }
        // Final ans in last cell
        return dp[m][n];
    }
}

// In this two pointers approach, taking two pointers one at start of p and one
// at start of s. Also, take two variables sStar and pStar
// which will hold the index when we encountered a star in pattern, so that
// later if we dont find ans with 0-case, we can go back and
// try the 1-case.

// Time Complexity : O(min(m,n))
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// Two pointers solution:
class Solution {
    public boolean isMatch(String s, String p) {
        // Base case
        if (s.equals(p)) {
            return true;
        }
        if (p == null || p.length() == 0) {
            return false;
        }
        // pointers
        int sp = 0;
        int pp = 0;
        int sStar = -1;
        int pStar = -1;
        // Loop till s.length
        while (sp < s.length()) {
            // Check if the index pp is not out of bounds and if the both chars are equal
            if ((pp < p.length()) && (s.charAt(sp) == p.charAt(pp) || p.charAt(pp) == '?')) {
                // Simply increment both pointers
                sp++;
                pp++;
            }
            // Else if it is a '*'
            else if ((pp < p.length()) && p.charAt(pp) == '*') {
                // Record the indexes in pstar and sstar
                pStar = pp;
                sStar = sp;
                // increment on pp, indicating 0-case, that is we take empty string in place of
                // *
                pp++;
            }
            // At this point, the chars are not equal so we will come here, check if pstar
            // is still -1 means we dont have any * to go back
            else if (pStar == -1) {
                // So return false
                return false;
            }
            // Else if we have *
            else {
                // Go back
                sp = sStar;
                pp = pStar;
                // Increment both indication 1-case, we are expanding * to char at sp
                pp++;
                sp++;
                // Record the new position of sp in sstar
                sStar = sp;
            }
        }
        // After coming out if still there are some chars in p, then check if they are
        // *, then simply move forward, taking that as empty string, else return false
        while (pp < p.length()) {
            if (p.charAt(pp) != '*') {
                return false;
            }
            pp++;
        }
        // If false is not returned at any place, return true
        return true;
    }
}