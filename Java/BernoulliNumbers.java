public class BernoulliNumbers
{
    public static BigFraction[] calculateBernoulliNumbers(int n)
    {
        if (n <= 2)
            throw new IllegalArgumentException();
        BigFraction[] B = new BigFraction[n+1];
        B[0] = new BigFraction(1, 1);
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
            System.out.printf("B[%2s] = %s%n", i, B[i]);
        }
    }

    public static void main(String[] args)
    {
        BigFraction[] bern = calculateBernoulliNumbers(30);
        printTable(bern);
    }
}
