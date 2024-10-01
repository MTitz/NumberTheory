/* Program Micropuzzle34a.java to investigate micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   An unusual number
*/

public class Micropuzzle34a
{
    public static void main(String[] args)
    {
        final int B = 10;
        for (int a = 2; a < B; ++a) {
            for (int d0 = 1; d0 < B; ++d0) {
                int dHigh = d0 * a % B;
                String classification = "";
                if (dHigh == 0)
                    classification = "First digit zero";
                else if (dHigh * a >= d0 + 1)
                    classification = "Product too large";
                else if ((dHigh + 1) * a < d0)
                    classification = "Product too small";
                System.out.printf("a =%2d, d0 =%2d:  dH =%2d  %s%n", a, d0, dHigh, classification);
            }
        }
    }
}
