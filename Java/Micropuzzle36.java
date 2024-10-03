/* Program Micropuzzle36.java to solve micropuzzle 36 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   More cubes and squares
   (uses divisors function of my NumberTheory library)
*/

public class Micropuzzle36
{
    public static boolean isCube(long n)
    {
        final double eps = 1.0e-4;
        double x = n;
        long root = (long)Math.floor(Math.cbrt(x) + eps);
        return root * root * root == n;
    }

    public static void main(String[] args)
    {
        final long TwoM = 2000000;
        long[] div = NumberTheory.divisors(TwoM);
        for (long d : div) {
            if (isCube(d * d + TwoM)) {
                System.out.println("Solution: factor = " + d);
            }
        }
    }
}
