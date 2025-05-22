/* Program Micropuzzle36a.java to solve micropuzzle 36 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   More cubes and squares
   (version without using external library)
*/

public class Micropuzzle36a
{
    public static boolean isCube(long n)
    {
        long root = Math.round(Math.cbrt(n));
        return root * root * root == n;
    }

    public static void main(String[] args)
    {
        long factor2 = 1;
        for (int i = 0; i <= 7; ++i) {
            long factor5 = 1;
            for (int j = 0; j <= 6; ++j) {
                long d = factor2 * factor5;
                if (isCube(d * d + 2000000)) {
                    System.out.println("Solution: factor = " + d);
                }
                factor5 *= 5;
            }
            factor2 *= 2;
        }
    }
}
