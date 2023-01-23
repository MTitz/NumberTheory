/* Program Micropuzzle22.java to solve micropuzzle 22 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle22
{
    public static long expression(long x, long y)
    {
        long s = x + y;
        long d = x - y;
        long p = x * y;
        double q = (double)x / (double)y;
        double e = p * d - q * s;
        return (long)Math.round(e);
    }

    public static void main(String[] args)
    {
        System.out.println("Sample from exercise: " + expression(1234, 56));
        System.out.println();

        long xMax = 0, yMax = 0;
        long eMax = -1;

        Permutation permutation = new Permutation(6);
        while (permutation.hasNext()) {
            /* go through different separators for the two numbers
               index indicates beginning of second number */
            int[] p = permutation.getPermutation();
            for (int d = 1; d < 6; ++d) {
                long x = 0;
                for (int i = 0; i < d; ++i) {
                    x = 10 * x + p[i];
                }
                long y = 0;
                for (int i = d; i < 6; ++i) {
                    y = 10 * y + p[i];
                }
                //System.out.println("d = " + d + ", x = " + x + ", y = " + y);
                long e = expression(x, y);
                if (e > eMax) {
                    xMax = x;
                    yMax = y;
                    eMax = e;
                }
            }
            permutation.next();
        }

        System.out.println("Maximum at x = " + xMax + ", y = " + yMax +
                    " with E = " + eMax + " attained.");
    }
}
