/* Program Micropuzzle05.java to solve micropuzzle 5 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Digital dexterity
*/

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class Micropuzzle05
{
    // multiplicative order of a modulo m
    // a and m must have no common divisor
    public static int ord(int a, int m)
    {
        int r = 1;
        long power = a % m;
        while (power != 1) {
            ++r;
            power = (power * a) % m;
        }
        return r;
    }

    public static BigInteger leastSolution(int base, int d0)
    {
        if (d0 < 2 || d0 >= base) {
            System.err.println("Digit d0 must be in the range of 2.." + (base-1));
            return BigInteger.ZERO;
        }
        int m = base * d0 - 1;
        int n = ord(base, m);
        BigInteger bigFactor = BigInteger.valueOf(base).pow(n).subtract(BigInteger.ONE);
        return bigFactor.divide(BigInteger.valueOf(m)).multiply(BigInteger.valueOf(d0));
    }

    public static void main(String[] args)
    {
        int base = 10;
        if (args.length > 0) {
            base = Integer.parseInt(args[0]);
            if (base < 2 || args.length >= 2) {
                System.err.println("Command line error");
                return;
            }
            System.out.println("Results for base " + base);
            System.out.println();
        }
        for (int digit = 2; digit < base; ++digit) {
            BigInteger originalNumber = leastSolution(base, digit);
            System.out.println("digit " + digit + ": " + NumberFormat.getNumberInstance(Locale.US).format(originalNumber));
        }
    }
}
