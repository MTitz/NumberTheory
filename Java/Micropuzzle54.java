/* Program Micropuzzle54.java to solve micropuzzle 54 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The ladder and the wall
*/

import static java.lang.Math.sqrt;

public class Micropuzzle54
{
    public static void main(String[] args)
    {
        double R = 1.0 + sqrt(26.0);
        double D = R * (R - 4.0);
        double x1 = 3.0 / 2.0 * (R - sqrt(D));
        double x2 = 3.0 / 2.0 * (R + sqrt(D));
        System.out.printf("x1 = %16.12f%n", x1);
        System.out.printf("x2 = %16.12f%n", x2);
        System.out.printf("%nDistance from top of the wall is %9.6f feet or %9.6f feet%n", 15.0-x1, 15.0-x2);
    }
}
