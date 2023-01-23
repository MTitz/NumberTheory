/* Program Micropuzzle06.java to solve micropuzzle 06 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A palindromic square
*/

public class Micropuzzle06
{
    public static int nDigits(int n)
    {
        return (int)Math.floor(Math.log10(n) + 1.0);
    }

    public static boolean checkPalindrome(int originalNumber)
    {
        int reverseNumber = 0;
        int n = originalNumber;
        while (n > 0) {
            int lastDigit = n % 10;
            reverseNumber = reverseNumber * 10 + lastDigit;
            n /= 10;
        }
        return originalNumber == reverseNumber;
    }

    public static void main(String[] args)
    {
        final int limit = (int)Math.floor(Math.sqrt(Integer.MAX_VALUE));
        boolean done = false;
        int n = 0;
        while (!done) {
            ++n;
            int square = n * n;
            boolean isPalindrome = checkPalindrome(square);
            if (isPalindrome) {
                int digits = nDigits(square);
                System.out.printf("%d^2 = %d (%d %s)%n", n, square, digits,
                        digits == 1 ? "digit" : "digits");
                if (digits % 2 == 0)
                    done = true;
            }
            if (n >= limit) break;  // to avoid integer overflow
        }
    }
}
