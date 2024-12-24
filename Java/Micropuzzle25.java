/* Program Micropuzzle25.java to solve micropuzzle 25 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Fieldcraft
*/

import static java.lang.Math.sqrt;
import java.util.Random;

public class Micropuzzle25
{
    public static double time(double u, double v)
    {
       double diff1 = v - u;
       double diff2 = 6 - v;
       return 40.0 * sqrt(u * u + 1.0)
            + 20.0 * sqrt(diff1 * diff1 + 4.0)
            + 10.0 * sqrt(diff2 * diff2 + 9.0);
    }

    public static void main(String[] args)
    {
        long n = 1000000;
        if (args.length == 1) {
            n = Long.parseLong(args[0]);
        } else if (args.length > 1) {
           System.err.println("Too many command line arguments, aborting.");
           return;
        }
        double uMin = 6.0;
        double vMin = 6.0;
        double tMin = time(uMin, vMin);
        Random random = new Random();
        for (long i = 1; i <= n; ++i) {
            double u = 6.0 * random.nextDouble();
            double v = 6.0 * random.nextDouble();
            if (u > v) {
                double t = v;
                v = u;
                u = t;
            }
            double tm = time(u, v);
            if (tm < tMin) {
                System.out.printf("%12.6f %12.6f  ->  %12.9f  (step %d)%n",
                        100.0 * u, 100.0 * v, tm, i);
                uMin = u;
                vMin = v;
                tMin = tm;
            }
        }
    }
}
