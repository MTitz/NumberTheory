/* Program Micropuzzle34.java to solve micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   An unusual number
*/

public class Micropuzzle34
{
    public static int power(int base, int exponent)
    {
        int result = 1;
        while (exponent != 0)
        {
            if ((exponent & 1) != 0)
                result *= base;
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    public static int number(final int[] digits)
    {
        final int base = 10;
        if (digits == null)
            return 0;
        int n = 0;
        for (int i = digits.length-1; i >= 0; --i) {
            n *= base;
            n += digits[i];
        }
        return n;
    }

    public static void search(final int nDigits)
    {
        final int nLow = power(10, nDigits-1);
        final int nHigh = 10 * nLow;
        int[] digits = new int[nDigits];
        for (int a = 2; a <= 9; ++a) {
            if (a == 5)
                continue;
            int dLimit = nHigh / a;
            for (int d = nLow; d <= dLimit; ++d) {
                if (d % 10 == 0)
                    continue;
                int n = d;
                for (int i = 0; i < nDigits; ++i) {
                    digits[nDigits-1-i] = n % 10;
                    n /= 10;
                }
                n = number(digits);
                if (d * a == n) {
                    System.out.println("Solution: " + d + " * " + a + " = " + n);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            search(6);  // the original problem as stated in the book
        } else {
            for (int i = 0; i < args.length; ++i) {
                int nDigits = Integer.parseInt(args[i]);
                if (nDigits >= 1 && nDigits <= 9) {
                    search(nDigits);
                }
            }
        }
    }
}
