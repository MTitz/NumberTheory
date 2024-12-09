/* Program Micropuzzle42.java to solve micropuzzle 42 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The numerate marathon runner
*/

public class Micropuzzle42
{
    private static void printSolutions(int solutions)
    {
        final double sqrt2 = Math.sqrt(2.0);
        int n = 2*solutions-1;
        long[] P = new long[n+1];
        long[] Q = new long[n+1];
        P[0] = 1;
        Q[0] = 1;
        P[1] = 3;
        Q[1] = 2;
        for (int k = 2; k <= n; ++k) {
            P[k] = 2 * P[k-1] + P[k-2];
            Q[k] = 2 * Q[k-1] + Q[k-2];
        }

        System.out.println(" No.     numerator    denominator         quotient         quotient-sqrt(2)");
        for (int k = 0; k <= n; ++k) {
            double p = P[k];
            double q = Q[k];
            System.out.printf("%3d %14d %14d  %19.15f %19.15f%n", k, P[k], Q[k], p/q, p/q - sqrt2);
        }
        System.out.println();
        System.out.println("  i    k         runner  total runners");
        for (int i = 1; i <= solutions; ++i) {
            int k = 2*i-1;
            long runner = Q[k] / 2;
            long total  = (P[k]-1) / 2;
            System.out.printf("%3d %4d %14d %14d%n", i, k, runner, total);
        }
    }

    public static void main(String[] args)
    {
        printSolutions(16);
    }
}
