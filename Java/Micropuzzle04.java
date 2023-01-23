/* Program Micropuzzle04.java to solve micropuzzle 04 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A palindromic puzzle
*/

public class Micropuzzle04
{
    public static boolean checkPalindrome(int n, int totalDigits)
    {
        String str = Integer.toString(n);
        while (str.length() < totalDigits) {
            str = "0" + str;
        }
        StringBuilder sb = new StringBuilder(str);
        String reverse = sb.reverse().toString();
        return str.equals(reverse);
    }

    public static void printSolution(int miles, String names)
    {
        System.out.println("After " + miles + " miles: " + names);
    }

    public static void main(String[] args)
    {
        int miles = 0;
        int a = 6600;
        int b = 18981;
        int c = 5335;
        boolean abDone = false;
        boolean acDone = false;
        boolean bcDone = false;
        while (true) {
            ++miles;
            a = ++a % 1000000;
            b = ++b %  100000;
            c = ++c %   10000;
            boolean isA = checkPalindrome(a, 6);
            boolean isB = checkPalindrome(b, 5);
            boolean isC = checkPalindrome(c, 4);
            if (isA && isB && !abDone) {
                printSolution(miles, "Alan and Bert");
                abDone = true;
            }
            if (isA && isC && !acDone) {
                printSolution(miles, "Alan and Colin");
                acDone = true;
            }
            if (isB && isC && !bcDone) {
                printSolution(miles, "Bert and Colin");
                bcDone = true;
            }
            if (isA && isB && isC) {
                printSolution(miles, "all three");
                break;  // We are done.
            }
        }
    }
}
