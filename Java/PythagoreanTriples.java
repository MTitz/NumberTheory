/* Program PythagoreanTriples to calculate all Pythagorean
   triples a*a + b*b = c*c for integers up to a given limit.

   Compilation:  javac PythagoreanTriples.java
   Execution:    java PythagoreanTriples [-a|--all] [-p|--primitive] limit ...
      example:   java PythagoreanTriples -a 100 -p 200
*/

import java.util.SortedSet;
import java.util.TreeSet;

public class PythagoreanTriples
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
            if (this.b < val.b) return -1;
            if (this.b > val.b) return  1;
            return 0;
        }

        @Override
        public String toString() {
            return String.format("PythagoreanTriple(a=%d, b=%d, c=%d)", a, b, c);
        }

        private int a;
        private int b;
        private int c;
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
        System.out.println("usage: java PythagoreanTriples [-a|--all] [-p|--primitive] limit ...");
        System.out.println("  calculates the Pythagorean triples with hypotenuse <= limit");
        System.out.println("    -a  or  --all         calculate all triples");
        System.out.println("    -p  or  --primitive   calculate only primitive triples (default)");
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
                System.out.println("Pythagorean triples with hypotenuse <= " + limit);
                System.out.println();

                for (PythagoreanTriple triple : triples) {
                    System.out.printf("%10d%10d%10d%n", triple.a(), triple.b(), triple.c());
                }
            }
        }
    }
}
