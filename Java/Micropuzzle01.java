/* Program Micropuzzle01.java to solve micropuzzle 1 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Pythagoras for beginners
*/

import static java.lang.Math.*;
import java.util.Set;
import java.util.TreeSet;

public class Micropuzzle01
{
    public static boolean isCube(long n)
    {
        final double eps = 1.0e-8;  // to prevent errors by rounding
        double x = n;
        long root = (long)floor(cbrt(x) + eps);
        return root * root * root == n;
    }


    private static class PythagoreanTriple implements Comparable<PythagoreanTriple>
    {
        public PythagoreanTriple(int a, int b, int c)
        {
            if (a < b) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
            this.c = c;
        }

        public int a() { return this.a; }
        public int b() { return this.b; }
        public int c() { return this.c; }

        @Override public int compareTo(PythagoreanTriple val)
        {
            if (this.c < val.c) return -1;
            if (this.c > val.c) return  1;
            if (this.b < val.b) return -1;
            if (this.b > val.b) return  1;
            return 0;
        }

        private int a;
        private int b;
        private int c;
    };


    /* Generates all Pythagorean triples (a,b,c) with a^2+b^2=c^2
       and perimeter a square and area a cube until a given limit */

    public static Set<PythagoreanTriple> searchSolutions(int limit)
    {
        Set<PythagoreanTriple> triples = new TreeSet<>();
        for (int m = 1; m * m < limit; ++m) {
            for (int n = (m & 1) + 1; n < m; n += 2) {
                if (NumberTheory.gcd(m, n) == 1) {
                    int aBase = m*m - n*n;
                    int bBase = 2 * m * n;
                    int cBase = m*m + n*n;
                    if (cBase <= limit) {
                        int lambda = 1;
                        while (lambda * cBase <= limit) {
                            int a = aBase * lambda;
                            int b = bBase * lambda;
                            int c = cBase * lambda;
                            long perimeter = (long)a + b + c;
                            if (NumberTheory.isSquare(perimeter)) {
                                long area = ((long)a * (long)b) / 2;
                                if (isCube(area)) {
                                    triples.add(new PythagoreanTriple(a, b, c));
                                }
                            }
                            ++lambda;
                        }
                    } else  {
                        break;
                    }
                }
            }
        }
        return triples;
    }

    static void usage()
    {
        System.out.println("usage: java Micropuzzle01 limit");
    }

    public static void main(String[] args)
    {
        if (args.length != 1) {
            usage();
            return;
        }

        if (args[0].equals("--help") || args[0].equals("-h")) {
            usage();
            return;
        }

        int limit = Integer.parseInt(args[0]);
        if (limit <= 0) {
            System.err.println("limit should be positive, aborting...");
            return;
        }
        if (limit > 1_000_000_000) {
            System.err.println("limit too big for integer calculation, aborting...");
            return;
        }

        System.out.println("All solutions with hypotenuse <= " + limit);
        System.out.println();

        Set<PythagoreanTriple> allTriples = searchSolutions(limit);

        for (PythagoreanTriple triple : allTriples) {
            int a = triple.a();
            int b = triple.b();
            int c = triple.c();
            long perimeter = (long)a + b + c;
            long area = ((long)a * (long)b) / 2;
            System.out.printf("%12d%12d%12d    %12d  %24d%n",
                a, b, c, perimeter, area);
        }
    }
}
