/* Program Micropuzzle34b.java to investigate micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   An unusual number
*/

public class Micropuzzle34b
{
    public static void main(String[] args)
    {
        final int B = 10;
        for (int a = 2; a < B; ++a) {
            for (int d0 = 1; d0 < B; ++d0) {
                int dH0   = (d0 * a) % B;
                int carry = (d0 * a) / B;
                for (int d1 = 0; d1 < B; ++d1) {
                    int dH1 = (d1 * a + carry) % B;
                    int dHigh = B * dH0 + dH1;
                    int dLow = B * d0 + d1;
                    String classification = "";
                    if (dH0 == 0)
                        classification = "First digit zero";
                    else if (dHigh * a >= dLow + 1)
                        classification = "Product too large";
                    else if ((dHigh + 1) * a < dLow)
                        classification = "Product too small";
                    System.out.printf("a = %d, dLow = %02d:  dHigh = %02d  %s%n",
                            a, dLow, dHigh, classification);
                }
            }
        }
    }
}
