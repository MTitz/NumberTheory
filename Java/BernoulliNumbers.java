/* Calculates a list of Bernoulli numbers as fractions using BigFraction class.
   See Shanjie Zhang, Jianming Jin, "Computation of Special Functions",
   formulas (1.1.4) and (1.1.5) and FORTRAN-77 subroutine BERNOA.
*/

import java.text.DecimalFormat;

public class BernoulliNumbers
{
    public static BigFraction[] calculateBernoulliNumbers(int n)
    {
        if (n < 1)
            throw new IllegalArgumentException("need n>=2 for table of Bernoulli numbers");
        BigFraction[] B = new BigFraction[n+1];
        B[0] = BigFraction.ONE;
        B[1] = new BigFraction(-1, 2);
        for (int m = 3; m <= n; m += 2) {
            B[m] = BigFraction.ZERO;
        }
        for (int m = 2; m <= n; m += 2) {
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

    public static void printTable(final BigFraction[] B)
    {
        if (B == null)
            return;
        for (int i = 0; i < B.length; ++i) {
            if (B[i].compareTo(BigFraction.ZERO) != 0)
                System.out.printf("B[%3d] = %s%n", i, B[i]);
        }
        System.out.println();
        DecimalFormat df1 = new DecimalFormat("###,###,###,###,###,##0.0000000000000000000000000000000000000000");
        DecimalFormat df2 = new DecimalFormat("0.000000000000000000000000000000000000000000000000000000000000000E000");
        DecimalFormat df3 = new DecimalFormat("0.00000000000000000000000000000000000000000000000000000000000000E0000");
        DecimalFormat df = df1;
        for (int i = 0; i < B.length; ++i) {
            // change output format to exponential notation beginning with index 48
            // until index 636 three digits of the exponent are sufficient
            if (i == 48)
                df = B.length <= 636 ? df2 : df3;
            if (B[i].compareTo(BigFraction.ZERO) != 0)
                System.out.printf("B[%3d] = %70s%n", i, df.format(B[i].bigDecimalValue(40)));
        }
    }

    public static void main(String[] args)
    {
        int limit = 30;  // Just a rather arbitrary default value
        if (args.length > 0) {
            limit = Integer.parseInt(args[0]);
            if (args.length >= 2) {
                System.err.println("Expected only upper limit for index of Bernoulli numbers.");
                return;
            }
        }
        if (limit > 4184) {
            System.err.println("Index too high for output format, and program would be too slow.");
        } else if (limit <= 1) {
            System.err.println("Index too low for any reasonable calculation.");
        } else {
            BigFraction[] bern = calculateBernoulliNumbers(limit);
            printTable(bern);
        }
    }
}
