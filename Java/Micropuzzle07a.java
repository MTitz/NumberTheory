/* Program Micropuzzle07a.java to solve micropuzzle 7 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   More perfect squares
*/

import java.util.Set;
import java.util.HashSet;

public class Micropuzzle07a
{
    public static final int gcd(int x, int y)
    {
        while (y != 0) {
            int tmp = y;
            y = x % y;
            x = tmp;
        }
        return Math.abs(x);
    }

    public static final int integerSqrt(int n)
    {
        if (n <= 0)
            return 0;
        int x = n;
        while (true) {
            int y = (x + n / x) >> 1;
            if (y < x)
                x = y;
            else
                return x;
        }
    }

    public static void main(String[] args)
    {
        final int nMax = 2500;
        Set<Integer> squares = new HashSet<>();
        for (int n = 1; n <= nMax; n += 2) {
            squares.add(n * n);
        }

        for (int a = 1; a < nMax; a += 2) {
            final int aSquare = a * a;
            final int deltaLimit = (nMax * nMax - aSquare) / 2;
            int delta = 0;
            while ((delta += 4) <= deltaLimit) {
                if (gcd(a, delta) > 1) continue;  // allow only 'primitive' solutions
                if (squares.contains(aSquare + delta) && squares.contains(aSquare + 2*delta)) {
                    int b = integerSqrt(aSquare +   delta);
                    int c = integerSqrt(aSquare + 2*delta);
                    System.out.printf("%9d =%5d^2, %9d =%5d^2, %9d =%5d^2  with difference %9d%n",
                            aSquare, a, b * b, b, c * c, c, delta);
                }
            }
        }
    }
}
