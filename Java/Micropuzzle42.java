/* Program Micropuzzle42.java to solve micropuzzle 42 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle42
{
    public static double[] solution(int n)
    {
        final double sqrt2 = Math.sqrt(2.0);
        double f1 = 3.0 + 2.0 * sqrt2;
        double f2 = 3.0 - 2.0 * sqrt2;
        double p1 = Math.pow(f1, n);
        double p2 = Math.pow(f2, n);
        return new double[] { (p1 - p2) / (4.0 * sqrt2), 0.25 * (p1 + p2) - 0.5 };
    }

    public static void main(String[] args)
    {
        for (int c = 1; c <= 16; ++c) {
            double[] sol = solution(c);
            System.out.printf("%20.0f %20.0f%n", sol[0], sol[1]);
        }
    }
}
