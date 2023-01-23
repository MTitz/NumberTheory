/* Program Micropuzzle32.java to solve micropuzzle 32 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A problem of prime factors
*/

import java.math.BigInteger;

public class Micropuzzle32
{
    private static int[] a;
    private static boolean[] isPrime;
    private static BigInteger maxProduct = BigInteger.ZERO;

    public static boolean[] eratosthenes(int n)
    {
        boolean[] a = new boolean[n+1];
        a[0] = a[1] = false;
        for (int i = 2; i <= n; ++i) {
            a[i] = true;
        }
        int p = 2;
        while (p * p <= n) {
            int j = p * p;
            while (j <= n) {
                a[j] = false;
                j += p;
            }

            // find next prime
            do {
                ++p;
            } while (!a[p]);
        }
        return a;
    }

    private static void recursivePrimePartition(int m, int B, int N)
    {
        if (m == 0) {
            BigInteger product = BigInteger.ONE;
            for (int i = 0; i < N; ++i) {
                product = product.multiply(BigInteger.valueOf(a[i]));
            }
            if (product.compareTo(maxProduct) > 0) {
                System.out.print("New maximum " + product + " for primefactors");
                maxProduct = product;
                for (int i = 0; i < N; ++i) {
                    System.out.print(" " + a[i]);
                }
                System.out.println();
            }
        } else {
            for (int i = 2; i <= Math.min(B, m); ++i) {
                if (isPrime[i]) {
                    a[N] = i;
                    recursivePrimePartition(m-i, i, N+1);
                }
            }
        }
    }

    public static void generatePrimePartitions(int m)
    {
        if (m < 0) {
            System.err.println("Cannot generate partitions of a negative number.");
            return;
        }
        a = null;
        a = new int[m];
        isPrime = eratosthenes(m);
        recursivePrimePartition(m, m, 0);
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            generatePrimePartitions(100);
        }
        for (int i = 0; i < args.length; ++i)
        {
            if ( i > 0)
                System.out.println();
            int n = Integer.parseInt(args[i]);
            generatePrimePartitions(n);
        }
    }
}
