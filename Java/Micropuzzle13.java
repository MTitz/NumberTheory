/* Program Micropuzzle13.java to solve micropuzzle 13 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

// Searching for solutions of
//   a + b + c + d = 1422
//   a * b * c * d = 1422 * 200^3
// in positive integers a, b, c, d,
// without loss of generality a >= b >= c >= d
// (Therefore number a must contribute at least one fourth of the sum.)
//
// Speeding up by only taking divisors of 1422 * 200^3 as candidates
// would allow an even faster program.

public class Micropuzzle13
{
    public static void main(String[] args)
    {
        final long TOTAL = 2 * 711;
        for (long a = TOTAL/4; a <= TOTAL; ++a) {
            for (long b = 1; b <= a; ++b) {
                long sum = a + b;
                if (sum >= TOTAL-2)
                   break;
                for (long c = 1; c <= b; ++c) {
                    long d = TOTAL - sum - c;
                    if (d >= 1 && d <= c && a * b * c * d == TOTAL * 200 * 200 * 200) {
                        System.out.printf("%6.3f %6.3f %6.3f %6.3f%n", a / 200.0, b / 200.0, c / 200.0, d / 200.0);
                    }
                }
            }
        }
    }
}
