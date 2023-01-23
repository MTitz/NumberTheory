/* Program Micropuzzle51.java to solve micropuzzle 51 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle51
{
    public static void main(String[] args)
    {
        Permutation permutation = new Permutation(9);
        while (permutation.hasNext()) {
            final int d = 3;
            int[] p = permutation.getPermutation();
            long x = 0;
            for (int i = 0; i < d; ++i) {
                x = 10 * x + p[i];
            }
            long y = 0;
            for (int i = d; i < 9; ++i) {
                y = 10 * y + p[i];
            }
            if (x * x == y) {
                System.out.println("Solution: " + x + "^2 = " + y);
            }

            permutation.next();
        }
    }
}
