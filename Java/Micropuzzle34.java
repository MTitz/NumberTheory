/* Program Micropuzzle34.java to solve micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   An unusual number
*/

public class Micropuzzle34
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
        if (nDigits <= 1)
             return;
        final long nHigh = power(10, nDigits-2);
        int[] digits = new int[nDigits];
        for (int a = 2; a <= 9; ++a) {
            for (int d0 = 1; d0 <= 9; ++d0) {
                int dn = (d0 * a) % 10;
                if (dn * a >= 10 || dn == 0 || dn * a >= d0 + 1 || (dn + 1) * a <= d0) {
                    //System.err.println("No solution for a = " + a + ", d0 = " + d0 + ", dn = " + dn);
                    continue;
                }
                //System.err.println("Searching a = " + a + ", d0 = " + d0 + ", dn = " + dn);
                final long dLimit = 10 * nHigh * (d0 + 1) / a;
                for (long dMiddle = 0; dMiddle < nHigh; ++dMiddle) {
                    long d = 10 * nHigh * dn + 10 * dMiddle + d0;
                    if (d >= dLimit) {
                        //System.err.printf("  limit reached for %d%n", d);
                        break;
                    }
                    long n = d;
                    for (int i = 0; i < nDigits; ++i) {
                        digits[nDigits-1-i] = (int)(n % 10);
                        n /= 10;
                    }
                    n = number(digits);
                    if (d * a == n) {
                        System.out.println("Solution: " + d + " * " + a + " = " + n);
                    }
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
                if (nDigits >= 2 && nDigits <= 18) {
                    search(nDigits);
                }
            }
        }
    }
}
