import java.math.BigInteger;

public class Fibonacci
{
    //use the following line for Java version less than 9 and replace BigInteger.TWO with TWO below
    //static final BigInteger TWO = new BigInteger("2");

    public static int bitlength(int n)
    {
        if (n < 0) n = -n;
        int bits = 0;
        while (n != 0) {
            ++bits;
            n >>= 1;
        }
        return bits;
    }

    public static BigInteger fibonacci(int n)
    {
        if (n == 0)
            return BigInteger.ZERO;
        else if (n == 1)
            return BigInteger.ONE;
        else if (n < 0) {
            BigInteger f = fibonacci(-n);
            return n % 2 == 0 ? f.negate() : f;
        }
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;

        final int bitLength = bitlength(n);
        for (int k = bitLength-2; k >= 0; --k) {
            BigInteger x2 = x.multiply(x);
            x = x2.add(BigInteger.TWO.multiply(x).multiply(y));
            y = x2.add(y.multiply(y));
            if (((1 << k) & n) != 0) {
                BigInteger temp = x;
                x = x.add(y);
                y = temp;
            }
        }

        return x;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; ++i) {
            int n = Integer.parseInt(args[i]);
            System.out.println("fib(" + n + ") = " + fibonacci(n));
        }
    }
}
