/* [Peter Giblin] Primes and Programming, Computing exercise 4.5 (a) */

import static java.lang.Math.*;

public class SqrtExpression
{
    public static void main(String[] args)
    {
        for (int n = 1; n <= 100; ++n) {
            int tmp = (int)floor(sqrt(n)) + 1;
            int d = tmp * tmp - n;
            System.out.printf("%4d  %4d%n", n, d);
        }
    }
}
