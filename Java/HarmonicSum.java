import java.math.BigInteger;

public class HarmonicSum
{
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
        BigFraction sum = BigFraction.ZERO;
        for (int n = 1; n <= limit; ++n) {
            sum = sum.add(new BigFraction(BigInteger.ONE, BigInteger.valueOf(n)));
            System.out.println("n = " + n + ": " + sum);
        }
    }
}
