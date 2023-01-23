/* [Peter Giblin] Primes and Programming, Computing exercise 4.5 (a) */

using System;

class SqrtExpression
{
    static void Main()
    {
        for (int n = 1; n <= 100; ++n) {
            int tmp = (int)Math.Floor(Math.Sqrt(n)) + 1;
            int d = tmp * tmp - n;
            Console.WriteLine("{0,4}  {1,4}", n, d);
        }
    }
}
