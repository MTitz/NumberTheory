/* Program Micropuzzle50.java to solve micropuzzle 50 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle50
{
    private static boolean[] q11, q63, q64, q65;

    static {
        q11 = new boolean[11];
        q63 = new boolean[63];
        q64 = new boolean[64];
        q65 = new boolean[65];
        for (int k = 0; k < 11; ++k) q11[k] = false;
        for (int k = 0; k <= 5; ++k) q11[(k * k) % 11] = true;
        for (int k = 0; k < 63; ++k) q63[k] = false;
        for (int k = 0; k <=31; ++k) q63[(k * k) % 63] = true;
        for (int k = 0; k < 64; ++k) q64[k] = false;
        for (int k = 0; k <=31; ++k) q64[(k * k) % 64] = true;
        for (int k = 0; k < 65; ++k) q65[k] = false;
        for (int k = 0; k <=32; ++k) q65[(k * k) % 65] = true;

    }

    public static long integerSqrt(long n)
    {
        if (n <= 0)
            return 0;
        long x, y;
        x = n;
        while (true) {
            y = (x + n / x) >> 1;
            if (y < x)
                x = y;
            else
                return x;
        }
    }

    public static long squareTest(long n)
    {
        if (n < 0)
            return -1;
        int t = (int)(n & 63);
        if (!q64[t])
            return -1;
        int r = (int)(n % 45045);
        if (!q63[r % 63])
            return -1;
        if (!q65[r % 65])
            return -1;
        if (!q11[r % 11])
            return -1;
        long q = integerSqrt(n);
        return q * q == n ? q : -1;
    }

    public static final long tetrahedralNumber(long n)
    {
        return (n * (n+1) * (n+2)) / 6;
    }

    public static void main(String[] args)
    {
        long nMax = 1000000;
        if (args.length == 1) {
            nMax = Long.parseLong(args[0]);
        } else if (args.length > 1) {
           System.err.println("Too many command line arguments, aborting.");
           return;
        }
        for (long n = 0; n <= nMax; ++n) {
            long tet = tetrahedralNumber(n);
            long q = squareTest(tet);
            if (q >= 0) {
                System.out.println("Found pyramid with " + n + " layers containing " +
                                   tet + " = " + q + "^2 balls.");
            }
        }
        System.out.println("Checked all tetrahedral pyramids with <= " + nMax + " layers.");
    }
}
