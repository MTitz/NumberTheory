/* Program Micropuzzle41.java to solve micropuzzle 41 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/


public class Micropuzzle41
{
    public static void main(String[] args)
    {
        final int nMax = 8999;
        final boolean[] prime = NumberTheory.eratosthenes(nMax);
        for (int n = 1; n <= nMax; n += 2) {
            if (prime[n]) {
                //System.out.println("" + n + " is a prime.");
                continue;
            }
            boolean foundRepresentation = false;  // as sum of prime and twice a square
            int j = 3;
            while (j < n) {
                if (prime[j]) {
                    int r = NumberTheory.squareTest((n - j) / 2);
                    if (r > 0) {
                        //System.out.println("" + n + " = " + j + " + 2 * " + r + "^2");
                        foundRepresentation = true;
                        break;
                    }
                }
                j += 2;
            }
            if (!foundRepresentation) {
                System.out.println("No representation: " + n);
            }
        }
    }
}
