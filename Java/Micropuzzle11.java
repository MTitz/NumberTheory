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
        for (long a = 1; a < aMax; ++a) {
            long a_2 = a * a;
            long dMax = round(floor(a / sqrt(5.0)));
            for (long d = 1; d < dMax; ++d) {
                long d_2 = d * d;
                if (NumberTheory.isSquare(a_2 - 5 * d_2) &&
                    NumberTheory.isSquare(a_2 + 5 * d_2) &&
                    NumberTheory.gcd(a, d) == 1) {
                    long b = NumberTheory.integerSqrt(a_2 - 5 * d_2);
                    long c = NumberTheory.integerSqrt(a_2 + 5 * d_2);
                    System.out.println("Solution: (" + b + "/" + d + ")^2" +
                                               ", (" + a + "/" + d + ")^2" +
                                               ", (" + c + "/" + d + ")^2");
                   
                }
            }
        }
    }
}
