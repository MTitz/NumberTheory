/* Program Micropuzzle42a.java to solve micropuzzle 42 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The numerate marathon runner
*/

public class Micropuzzle42a
{
    public static void main(String[] args)
    {
        for (long n = 1; n <= 100000; ++n) {
            long potentialSquare = n * (n+1) / 2;
            long m = Math.round(Math.sqrt(potentialSquare));
            if (m * m == potentialSquare) {
                System.out.println("Runner " + m + " out of total " + n + " runners.");
            }
        }
    }
}
