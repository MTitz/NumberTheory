/* Program Micropuzzle34c.java to solve micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   An unusual number
*/

public class Micropuzzle34c
{
    public static long power(long base, int exponent)
    {
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) != 0)
                result *= base;
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    public static long number(final int[] digits)
    {
        final int base = 10;
        if (digits == null)
            return 0;
        long n = 0;
        for (int i = digits.length-1; i >= 0; --i) {
            n *= base;
            n += digits[i];
        }
        return n;
    }

    public static void search(final int nDigits)
    {
        if (nDigits <= 3)
             return;
        search(nDigits, 4, 78, 21);
        search(nDigits, 9, 89, 10);
    }

    private static void search(final int nDigits, long a, long dLow, long dHigh)
    {
        final long nHigh = power(10, nDigits-4);
        int[] digits = new int[nDigits];
        for (long dMiddle = 0; dMiddle < nHigh; ++dMiddle) {
            long d = 100 * (nHigh * dHigh + dMiddle) + dLow;
            long p = d;
            for (int i = 0; i < nDigits; ++i) {
                digits[nDigits-1-i] = (int)(p % 10);
                p /= 10;
            }
            p = number(digits);
            if (d * a == p) {
                System.out.println("Solution: " + d + " * " + a + " = " + p);
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
                if (nDigits >= 2 && nDigits <= 18) {
                    search(nDigits);
                }
            }
        }
    }
}
