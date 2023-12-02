/* Program Micropuzzle07.java to solve micropuzzle 7 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

import java.util.Set;
import java.util.HashSet;

public class Micropuzzle07
{
    public static void main(String[] args)
    {
        long nMax = 2500;
        Set<Long> squares = new HashSet<>();
        for (long n = 1; n <= nMax; n += 2) {
            squares.add(n * n);
        }

        for (long a = 1; a < nMax; a += 2) {
            long a_2 = a * a;
            long deltaLimit = (nMax * nMax - a_2) / 2;
            long delta = 0;
            while ((delta += 4) <= deltaLimit) {
                if (NumberTheory.gcd(a, delta) > 1) continue;  // allow only 'primitive' solutions
                if (squares.contains(a_2 + delta) && squares.contains(a_2 + 2*delta)) {
                    long b = NumberTheory.integerSqrt(a_2 +   delta);
                    long c = NumberTheory.integerSqrt(a_2 + 2*delta);
                    System.out.printf("%9d =%5d^2, %9d =%5d^2, %9d =%5d^2  with difference %9d%n",
                            a_2, a, b * b, b, c * c, c, delta);
                }
            }
        }
    }
}
