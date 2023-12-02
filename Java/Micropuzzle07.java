/* Program Micropuzzle07.java to solve micropuzzle 7 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   More perfect squares
*/

import java.util.SortedSet;
import java.util.TreeSet;

public class Micropuzzle07
{
    public static final int gcd(int m, int n)
    {
        while (n != 0) {
            int r = n;
            n = m % n;
            m = r;
        }
        return m;
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
            int thisA = this.a + this.b;
            int valA  = val.a  + val.b;
            if (thisA < valA) return -1;
            if (thisA > valA) return  1;
            return 0;
        }

        private final int a;
        private final int b;
        private final int c;
    };


    /* Generates all Pythagorean triples (a,b,c) with a^2+b^2=c^2
       until a given limit. */
    public static SortedSet<PythagoreanTriple> generateTriples(int limit, boolean primitiveOnly)
    {
        SortedSet<PythagoreanTriple> triples = new TreeSet<>();
        for (int m = 1; m * m < limit; ++m) {
            for (int n = (m & 1) + 1; n < m; n += 2) {
                if (gcd(m, n) == 1) {
                    int a = m*m - n*n;
                    int b = 2 * m * n;
                    int c = m*m + n*n;
                    if (c <= limit) {
                        if (primitiveOnly) {
                            triples.add(new PythagoreanTriple(a, b, c));
                        } else {
                            int k = 1;
                            while (k * c <= limit) {
                                triples.add(new PythagoreanTriple(k * a, k * b, k * c));
                                ++k;
                            }
                        }
                    } else {
                        break;  // abort inner for loop
                    }
                }
            }
        }
        return triples;
    }

    private static void usage()
    {
        System.out.println("usage: java Micropuzzle07 [-a|--all] [-p|--primitive] limit ...");
        System.out.println("  calculates the solutions of micropuzzle 7 <= limit^2");
        System.out.println("    -a  or  --all         calculate all solutions");
        System.out.println("    -p  or  --primitive   calculate only primitive solutions (default)");
        System.out.println("    limit                 a positive integer");
    }

    public static void main(String[] args)
    {
        boolean primitiveTriplesOnly = true;

        if (args.length < 1) {
            usage();
            return;
        }
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("--help") || args[i].equals("-h")) {
                usage();
                return;
            } else if (args[i].equals("--primitive") || args[i].equals("-p")) {
                primitiveTriplesOnly = true;
            } else if (args[i].equals("--all") || args[i].equals("-a")) {
                primitiveTriplesOnly = false;
            } else {
                int limit;
                try {
                    limit = Integer.parseInt(args[i]);
                }
                catch (NumberFormatException e) {
                    System.err.println("limit must be a positive integer, aborting...");
                    return;
                }
                if (limit <= 0) {
                    System.err.println("limit should be positive, aborting...");
                    return;
                }
                if (limit > 1_000_000_000) {
                    System.err.println("limit too big for integer calculation, aborting...");
                    return;
                }

                SortedSet<PythagoreanTriple> triples = generateTriples(limit, primitiveTriplesOnly);

                System.out.println();
                System.out.print("All ");
                if (primitiveTriplesOnly) {
                    System.out.print("primitive ");
                }
                System.out.println("solutions with B <= " + limit + "^2");
                System.out.println();

                for (PythagoreanTriple triple : triples) {
                    int tripleA, tripleB;
                    if (triple.a() > triple.b()) {
                        tripleA = triple.a();
                        tripleB = triple.b();
                    } else {
                        tripleA = triple.b();
                        tripleB = triple.a();
                    }
                    long a = tripleA - tripleB;
                    long b = triple.c();
                    long c = tripleA + tripleB;

                    System.out.printf("%9d =%5d^2, %9d =%5d^2, %9d =%5d^2  with difference %9d%n",
                            a * a, a, b * b, b, c * c, c, b * b - a * a);
                }
            }
        }
    }
}
