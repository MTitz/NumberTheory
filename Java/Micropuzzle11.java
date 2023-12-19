/* Program Micropuzzle11.java to solve micropuzzle 11 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The not-so-perfect square
*/

import static java.lang.Math.*;

public class Micropuzzle11
{
    public static void main(String[] args)
    {
        long aMax = 1000;
        for (long a = 1; a <= aMax; ++a) {
            long aSquare = a * a;
            long dMax = round(floor(a / sqrt(5.0)));
            for (long d = 1; d <= dMax; ++d) {
                long dSquare = d * d;
                if (NumberTheory.isSquare(aSquare - 5 * dSquare) &&
                    NumberTheory.isSquare(aSquare + 5 * dSquare) &&
                    NumberTheory.gcd(a, d) == 1) {
                    long b = NumberTheory.integerSqrt(aSquare - 5 * dSquare);
                    long c = NumberTheory.integerSqrt(aSquare + 5 * dSquare);
                    System.out.println("Solution: (" + b + "/" + d + ")^2" +
                                               ", (" + a + "/" + d + ")^2" +
                                               ", (" + c + "/" + d + ")^2");
                }
            }
        }
    }
}
