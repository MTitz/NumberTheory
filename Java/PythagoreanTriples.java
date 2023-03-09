/* Program PythagoreanTriples to calculate all Pythagorean
   triples a*a + b*b = c*c for integers up to a given limit.
*/

import java.util.SortedSet;
import java.util.TreeSet;

public class PythagoreanTriples
{
    public static final int gcd(int m, int n)
    {
        while (n != 0) {
            int tmp = n;
            n = m % n;
            m = tmp;
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
                                triples.add(new PythagoreanTriple(a * k, b * k, c * k));
                                ++k;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return triples;
    }

    static void usage()
    {
        System.out.println("usage: java PythagoreanTriples [-a|--all] [-p|--primitive] limit");
        System.out.println("  calculates the Pythagorean triples with hypotenuse <= limit");
        System.out.println("    -a  or  --all         calculate all triples");
        System.out.println("    -p  or  --primitive   calculate only primitive triples (default)");
        System.out.println("    limit                 a positive integer");
    }

    public static void main(String[] args)
    {
        boolean primitiveTuplesOnly = true;

        if (args.length < 1) {
            usage();
            return;
        }
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("--help") || args[i].equals("-h")) {
                usage();
                return;
            } else if (args[i].equals("--primitive") || args[i].equals("-p")) {
                primitiveTuplesOnly = true;
            } else if (args[i].equals("--all") || args[i].equals("-a")) {
                primitiveTuplesOnly = false;
            } else {
                int limit = Integer.parseInt(args[i]);
                if (limit <= 0) {
                    System.err.println("limit should be positive, aborting...");
                    return;
                }
                if (limit > 1_000_000_000) {
                    System.err.println("limit too big for integer calculation, aborting...");
                    return;
                }

                System.out.print("All ");
                if (primitiveTuplesOnly) {
                    System.out.print("primitive ");
                }
                System.out.println("Pythagoran triples with hypotenuse <= " + limit);
                System.out.println();

                SortedSet<PythagoreanTriple> triples = generateTriples(limit, primitiveTuplesOnly);

                for (PythagoreanTriple triple : triples) {
                    System.out.printf("%9d%9d%9d%n", triple.a(), triple.b(), triple.c());
                }
            }
        }
    }
}
