/* Program Micropuzzle11.java to solve micropuzzle 11 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.*;

public class Micropuzzle11
{
    public static void main(String[] args)
    {
        long nMax = 1000;
        Set<Long> squares = new TreeSet<>();
        for (long n = 1; n <= nMax; ++n) {
            squares.add(n * n);
        }
        long dMax = round(floor(nMax / sqrt(10.0)));

        for (long a = 1; a < nMax; ++a) {
            long a_2 = a * a;
            for (long d = 1; d < dMax; ++d) {
                long d_2 = d * d;
                if (squares.contains(a_2 + 5 * d_2) && squares.contains(a_2 + 10 * d_2)) {
                    if (NumberTheory.gcd(a, d) == 1) {
                        long b = NumberTheory.integerSqrt(a_2 +  5 * d_2);
                        long c = NumberTheory.integerSqrt(a_2 + 10 * d_2);
                        System.out.println("Solution: (" + a + "/" + d + ")^2" +
                                                   ", (" + b + "/" + d + ")^2" +
                                                   ", (" + c + "/" + d + ")^2");
                    }
                }
            }
        }
    }
}
