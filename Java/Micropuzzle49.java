/* Program Micropuzzle49.java to solve micropuzzle 49 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

import java.util.Set;
import java.util.HashSet;

public class Micropuzzle49
{
    public static void main(String[] args)
    {
        int nMax = 15000;
        Set<Integer> squares = new HashSet<>();
        for (int n = 1; n <= nMax; ++n) {

            squares.add(n * n);
        }
        int b2 = -1;
        for (int a4 = 1; a4 <= 200; ++a4) {
            if (squares.contains(a4 + 1)
             && squares.contains(3 * a4 + 1)
             && squares.contains(8 * a4 + 1)) {
                System.out.println("Solution a4 = " + a4);
                b2 = a4;
            }
        }
        for (int b4 = 1; b4 < nMax * nMax / 190; ++b4) {
            if (squares.contains(8 * b4 + 1)
             && squares.contains(b2 * b4 + 1)
             && squares.contains(190 * b4 + 1)) {
                System.out.println("Solution b4 = " + b4);
            }
        }
    }
}
