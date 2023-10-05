/* Calculates a list of Bernoulli numbers as fractions using BigFraction class.
   See Shanjie Zhang, Jianming Jin, "Computation of Special Functions",
   formulas (1.1.4) and (1.1.5) and FORTRAN-77 subroutine BERNOA.
*/

public class BernoulliNumbers
{
    public static BigFraction[] calculateBernoulliNumbers(int n)
    {
        if (n < 1)
            throw new IllegalArgumentException();
        BigFraction[] B = new BigFraction[n+1];
        B[0] = BigFraction.ONE;
        B[1] = new BigFraction(-1, 2);
        for (int m = 2; m <= n; ++m) {
            BigFraction sum = B[0].multiply(new BigFraction(1, m+1)).add(B[1]).negate();
            for (int k = 2; k < m; ++k) {
                BigFraction r = BigFraction.ONE;
                for (int j = 2; j <= k; ++j) {
                    r = r.multiply(new BigFraction(j+m-k, j));
                }
                sum = sum.subtract(r.multiply(B[k]));
            }
            B[m] = sum;
        }
        return B;
    }

    public static void printTable(BigFraction[] B)
    {
        if (B == null)
            return;
        for (int i = 0; i < B.length; ++i) {
            if (B[i].compareTo(BigFraction.ZERO) != 0)
                System.out.printf("B[%2d] = %s%n", i, B[i]);
        }
    }

    public static void main(String[] args)
    {
        int limit = 30;
        if (args.length > 0) {
            limit = Integer.parseInt(args[0]);
            if (args.length >= 2) {
                System.err.println("Command line error");
                return;
            }
        }
        BigFraction[] bern = calculateBernoulliNumbers(limit);
        printTable(bern);
    }
}
