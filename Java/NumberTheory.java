import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongBiFunction;


public class NumberTheory
{
    public static final int bitlength(int n)
    {
        if (n < 0) n = -n;
        int bits = 0;
        while (n != 0) {
            ++bits;
            n >>= 1;
        }
        return bits;
    }

    public static final int bitlength(long n)
    {
        if (n < 0) n = -n;
        int bits = 0;
        while (n != 0) {
            ++bits;
            n >>= 1;
        }
        return bits;
    }

    public static final int digitSum(int n)
    {
        final int base = 10;
        int sum = 0;
        while (n != 0) {
            sum += n % base;
            n /= base;
        }
        return sum;
    }

    public static final int digitSum(int n, int base)
    {
        int sum = 0;
        while (n != 0) {
            sum += n % base;
            n /= base;
        }
        return sum;
    }

    public static final int digitSum(long n, int base)
    {
        int sum = 0;
        while (n != 0) {
            sum += (int)(n % base);
            n /= base;
        }
        return sum;
    }

    public static final int digitSum(long n)
    {
        return digitSum(n, 10);
    }

    public static final boolean even(int n)
    {
        return (n & 1) == 0;
    }

    public static final boolean even(long n)
    {
        return (n & 1) == 0;
    }

    public static final boolean odd(int n)
    {
        return (n & 1) == 1;
    }

    public static final boolean odd(long n)
    {
        return (n & 1) == 1;
    }

    public static final int power(int base, int exponent)
    {
        int result = 1;
        while (exponent != 0) {
            if ((exponent & 1) != 0)
                result *= base;
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    public static final long power(long base, int exponent)
    {
        long result = 1;
        while (exponent != 0) {
            if ((exponent & 1) != 0)
                result *= base;
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    public static final BigInteger power(BigInteger base, int exponent)
    {
        return base.pow(exponent);
    }

    public static final int powerMod(int a, int n, int m)
    {
        if (n == 0) {
            return 1;
        } else if (n % 2 == 0) {
            long t = powerMod(a, n/2, m);
            return (int)((t * t) % m);
        } else {
            long t = powerMod(a, n-1, m);
            return (int)((a * t) % m);
        }
    }

    @Deprecated
    public static final long powerMod(long a, long n, long m)
    {
        if (n == 0) {
            return 1;
        } else if (n % 2 == 0) {
            long t = powerMod(a, n/2, m);
            return (t * t) % m;
        } else {
            long t = powerMod(a, n-1, m);
            return (a * t) % m;
        }
    }

    public static final BigInteger powerMod(BigInteger a, BigInteger n, BigInteger m)
    {
        return a.modPow(n, m);
    }

    // From Project Euler Problem 188
    //    The hyperexponentiation of a number
    //    https://projecteuler.net/problem=188
    public static final int hyperExpMod(int a, int k, int m)
    {
        if (k == 1) {
            return a % m;
        } else {
           return powerMod(a, hyperExpMod(a, k-1, m), m);
        }
    }

    public static final int primefactorInFactorial(final int number, final int p)
    {
        if (number < 0)
            throw new IllegalArgumentException("first argument of primefactorInFactorial is negative");
        if (p < 2)
            throw new IllegalArgumentException("second argument of primefactorInFactorial is too small");
        int n = number;
        int sum = 0;
        while (n >= p) {
            n /= p;
            sum += n;
        }
        return sum;
    }

    public static final int gcd(int x, int y)
    {
        while (y != 0) {
            int tmp = y;
            y = x % y;
            x = tmp;
        }
        return Math.abs(x);
    }

    public static final long gcd(long x, long y)
    {
        while (y != 0) {
            long tmp = y;
            y = x % y;
            x = tmp;
        }
        return Math.abs(x);
    }

    public static final int gcd(int[] u)
    {
        int n = u.length;
        int d = u[0];
        int k = 1;
        while (d != 1 && k < n) {
            d = gcd(u[k], d);
            ++k;
        }
        return d;
    }

    public static final long gcd(long[] u)
    {
        int n = u.length;
        long d = u[0];
        int k = 1;
        while (d != 1 && k < n) {
            d = gcd(u[k], d);
            ++k;
        }
        return d;
    }

    public static final long repeatedLongFunction(ToLongBiFunction<Long, Long> f, long[] u)
    {
        long v = u[0];
        for (int k = 1; k < u.length; ++k) {
            v = f.applyAsLong(v, u[k]);
        }
        return v;
    }

    public static final long lcm(long x, long y)
    {
        if (x == 0 || y == 0)
           return 0;
        return Math.abs((x / gcd(x, y)) * y);
    }

    public static final long lcm(long[] u)
    {
        return repeatedLongFunction((a, b) -> lcm(a,b), u);
    }

    public static class ExtendedEuclidResult {
        public long d;
        public long u;
        public long v;
    }

    // input: non-negative integers a and b
    // output: triple (u,v,d) such that au+bv=d and d=(a,b)
    // see [Cohen], Algorithm 1.3.6
    public static ExtendedEuclidResult extendedEuclid(long a, long b)
    {
        ExtendedEuclidResult result = new ExtendedEuclidResult();
        result.d = a;
        result.u = 1;
        if (b == 0) {
            result.v = 0;
        } else {
            long v1 = 0;
            long v3 = b;
            while (v3 != 0) {
                long q =  result.d / v3;
                long t3 = result.d % v3;
                long t1 = result.u - q*v1;
                result.u = v1;
                result.d = v3;
                v1 = t1;
                v3 = t3;
            }
            result.v = (result.d - a*result.u) / b;
        }
        return result;
    }

    public static long modInverse(long a, long m)
    {
        ExtendedEuclidResult result = extendedEuclid(a, m);
        if (result.d != 1)
            throw new IllegalArgumentException("inverse does not exist");
        return result.u >= 0 ? result.u : result.u + m;
    }


    public static class SimpleLinearCongruence
    {
        public SimpleLinearCongruence()
        {
            this.b = 0;
            this.m = 0;
        }

        public SimpleLinearCongruence(long b, long m)
        {
            this.b = b;
            this.m = m;
        }

        public long b()
        {
            return this.b;
        }

        public long m()
        {
            return this.m;
        }

        private final long b;
        private final long m;
    }

    public static class LinearCongruence
    {
        public LinearCongruence()
        {
            this.a = 0;
            this.b = 0;
            this.m = 0;
        }

        public LinearCongruence(long a, long b, long m)
        {
            this.a = a;
            this.b = b;
            this.m = m;
        }

        public long a()
        {
            return this.a;
        }

        public long b()
        {
            return this.b;
        }

        public long m()
        {
            return this.m;
        }

        private final long a;
        private final long b;
        private final long m;
    }

    public static final SimpleLinearCongruence solve(LinearCongruence congruence)
    {
        long a = congruence.a();
        long b = congruence.b();
        long m = congruence.m();
        long d = gcd(a, m);
        if (b % d == 0) {
            a /= d;
            b /= d;
            m /= d;

            try {
                b *= modInverse(a, m);
            }
            catch (RuntimeException re) {
                System.err.println("Error in solve: " + re);
                return new SimpleLinearCongruence(0, 0);
            }

            b %= m;
            if (b < 0)
                b += m;

            return new SimpleLinearCongruence(b, m);
        } else {
            return new SimpleLinearCongruence(0, 0);
        }
    }

    public static final SimpleLinearCongruence solve(SimpleLinearCongruence c1,
                                                     SimpleLinearCongruence c2)
    {
        long m1 = c1.m();
        long m2 = c2.m();
        long b1 = c1.b();
        long b2 = c2.b();
        long d = gcd(m1, m2);
        if ((b2 - b1) % d == 0) {
            long t1 = solve(new LinearCongruence(m1 / d, (b2 - b1) / d, m2 / d)).b();
            return new SimpleLinearCongruence(b1 + t1 * m1, lcm(m1, m2));
        } else {
            return new SimpleLinearCongruence(0, 0);
        }
    }

    public static final SimpleLinearCongruence solve(SimpleLinearCongruence[] congruence)
    {
        if (congruence == null || congruence.length == 0)
            return new SimpleLinearCongruence(0, 0);
        SimpleLinearCongruence c = congruence[0];
        for (int i = 1; i < congruence.length; ++i) {
            c = solve(c, congruence[i]);
        }
        return c;
    }

    public static final SimpleLinearCongruence solve(LinearCongruence[] c)
    {
        boolean solvable = true;
        SimpleLinearCongruence[] simpleC = new SimpleLinearCongruence[c.length];
        for (int i = 0; i < c.length; ++i) {
            simpleC[i] = solve(c[i]);
            if (simpleC[i].m() == 0) {
                solvable = false;
                break;
            }
        }
        SimpleLinearCongruence solution = solvable
                    ? solve(simpleC)
                    : new SimpleLinearCongruence(0, 0);
        simpleC = null;
        return solution;
    }


    public static final int jacobi(long a, long b)
    {
        final int[] tab2 = new int[]{0, 1, 0, -1, 0, -1, 0, 1};
        if (b == 0)
            return (a == 1 || a == -1) ? 1 : 0;
        if ((a & 1) == 0 && (b & 1) == 0)
            return 0;
        int v = 0;
        while ((b & 1) == 0) {
            v += 1;
            b /= 2;
        }
        int k = (v & 1) == 0 ? 1 : tab2[(int)(a & 7)];
        if (b < 0) {
            b = -b;
            if (a < 0)
                k = -k;
        }

        while (true) {
            // Here b is odd and b > 0.
            if (a == 0)
                return b > 1 ? 0 : k;
            v = 0;
            while ((a & 1) == 0) {
                v += 1;
                a /= 2;
            }
            if ((v & 1) != 0)
                k *= tab2[(int)(b & 7)];
            if ((a & b & 2) != 0)
                k = -k;
            long r = a >= 0 ? a : -a;
            a = b % r;
            b = r;
        }
    }

    public static long[] divisors(PrimeFactors primefactors)
    {
        final int n = primefactors.nFactors();
        if (n == 0) {
            return new long[] {1};
        }

        long[] div = new long[(int)sigma0(primefactors)];
        int index = 0;
        int[] a = new int[n+1];
        int[] m = new int[n+1];
        for (int i = 1; i <= n; ++i) {
            m[i] = primefactors.factors(i-1).exponent();
        }

        for (int i = 0; i <= n; ++i) {
            a[i] = 0;
        }
        m[0] = 2;

        while (true) {
            long d = 1;
            for (int i = 1; i <= n; ++i) {
                d *= power(primefactors.factors(i-1).base(), a[i]);
            }
            div[index++] = d;

            int j = n;
            while (a[j] == m[j]) {
                a[j] = 0;
               --j;
            }
            if (j == 0)
                break;
            ++a[j];
        }

        Arrays.sort(div);
        return div;
    }

    public static long[] smallDivisors(PrimeFactors primefactors)
    {
        final int n = primefactors.nFactors();
        if (n == 0) {
            return new long[] {1};
        }

        long[] div = new long[((int)sigma0(primefactors)+1)/2];
        long limit = integerSqrt(primefactors.getNumber());

        int index = 0;
        int[] a = new int[n+1];
        int[] m = new int[n+1];
        for (int i = 1; i <= n; ++i) {
            m[i] = primefactors.factors(i-1).exponent();
        }

        for (int i = 0; i <= n; ++i) {
            a[i] = 0;
        }
        m[0] = 2;

        while (true) {
            long d = 1;
            for (int i = 1; i <= n; ++i) {
                d *= power(primefactors.factors(i-1).base(), a[i]);
            }
            if (d <= limit)
                div[index++] = d;

            int j = n;
            while (a[j] == m[j]) {
                a[j] = 0;
               --j;
            }
            if (j == 0)
                break;
            ++a[j];
        }

        Arrays.sort(div);
        return div;
    }

    public static long[] divisors(long n)
    {
        PrimeFactors factors = n < 0 ? new PrimeFactors(-n) : new PrimeFactors(n);
        return divisors(factors);
    }

    public static long[] smallDivisors(long n)
    {
        PrimeFactors factors = n < 0 ? new PrimeFactors(-n) : new PrimeFactors(n);
        return smallDivisors(factors);
    }

    public static long additiveFunction(ToLongBiFunction<Long, Integer> factorF, PrimeFactors primefactors)
    {
        long result = 0;
        final int nFactors = primefactors.nFactors();
        for (int i = 0; i < nFactors; ++i) {
            PrimeFactors.Factor factor = primefactors.factors(i);
            result += factorF.applyAsLong(factor.base(), factor.exponent());
        }
        return result;
    }

    public static long multiplicativeFunction(ToLongBiFunction<Long, Integer> factorF, PrimeFactors primefactors)
    {
        long result = 1;
        final int nFactors = primefactors.nFactors();
        for (int i = 0; i < nFactors; ++i) {
            PrimeFactors.Factor factor = primefactors.factors(i);
            result *= factorF.applyAsLong(factor.base(), factor.exponent());
        }
        return result;
    }

    private static long factorF_sigma1(long base, int exponent)
    {
        long sum = 1;
        for (int i = 1; i <= exponent; ++i) {
            sum *= base;
            sum += 1;
        }
        return sum;
    }

    public static long omega(PrimeFactors primefactors)
    {
        return additiveFunction((base, exponent) -> 1, primefactors);
    }

    public static long factorCount(PrimeFactors primefactors)
    {
        return additiveFunction((base, exponent) -> exponent, primefactors);
    }

    public static long sigma0(PrimeFactors primefactors)
    {
        return multiplicativeFunction((base, exponent) -> exponent + 1, primefactors);
    }

    public static long sigma1(PrimeFactors primefactors)
    {
        return multiplicativeFunction((base, exponent) -> factorF_sigma1(base, exponent), primefactors);
    }

    public static long phi(PrimeFactors primefactors)
    {
        return multiplicativeFunction((base, exponent) -> power(base, exponent-1) * (base-1), primefactors);
    }

    public static long moebius(PrimeFactors primefactors)
    {
        return multiplicativeFunction((base, exponent) -> exponent >= 2 ? 0 : -1, primefactors);
    }

    public static long radical(PrimeFactors primefactors)
    {
        return multiplicativeFunction((base, exponent) -> base, primefactors);
    }

    public static long omega(long n)
    {
        return omega(new PrimeFactors(n));
    }

    public static long factorCount(long n)
    {
        return factorCount(new PrimeFactors(n));
    }

    public static long sigma0(long n)
    {
        return sigma0(new PrimeFactors(n));
    }

    public static long sigma1(long n)
    {
        return sigma1(new PrimeFactors(n));
    }

    public static long aliquotSum(long n)
    {
        return sigma1(new PrimeFactors(n)) - n;
    }

    public static long phi(long n)
    {
        return phi(new PrimeFactors(n));
    }

    public static long moebius(long n)
    {
        return moebius(new PrimeFactors(n));
    }

    public static long radical(long n)
    {
        if (n == 0)
            throw new IllegalArgumentException("radical of an integer is not defined for number 0");
        return radical(new PrimeFactors(n));
    }

    public static final int integerSqrt(int n)
    {
        if (n <= 0)
            return 0;
        int x = n;
        while (true) {
            int y = (x + n / x) >> 1;
            if (y < x)
                x = y;
            else
                return x;
        }
    }

    public static final long integerSqrt(long n)
    {
        if (n <= 0)
            return 0;
        long x = n;
        while (true) {
            long y = (x + n / x) >> 1;
            if (y < x)
                x = y;
            else
                return x;
        }
    }

    public static final BigInteger integerSqrt(final BigInteger n)
    {
        if (n.signum() <= 0)
            return BigInteger.ZERO;
        BigInteger x = n;
        while (true) {
            BigInteger y = x.add(n.divide(x)).shiftRight(1);
            if (y.compareTo(x) < 0)
                x = y;
            else
                return x;
        }
    }


    // sqrtToContinuedFraction from [Forster], Kapitel 21 Kettenbrüche

    public static List<Long> sqrtToContinuedFraction(long n)
    {
        List<Long> cfrac = new ArrayList<>();
        long w = NumberTheory.integerSqrt(n);
        long q = n - w * w;
        if (q == 0) {
            cfrac.add(w);
            return cfrac;
        }
        long w2 = 2 * w;
        long a = w;
        long m = w;
        long q0 = 1;
        cfrac.add(a);
        while (a != w2) {
            a = (m + w) / q;
            cfrac.add(a);
            long m1 = a * q - m;
            long q1 = q0 + a * (m - m1);
            m = m1;
            q0 = q;
            q = q1;
        }
        return cfrac;
    }

    // pell4 and pell from [Forster], Kapitel 25 Die Pell'sche Gleichung

    static final long[] pell4(long d)
    {
        if (d == 5)
            return new long[] {1, 1, -4};
        long w = integerSqrt(d); long q = d - w * w;
        long q0 = 1; long m = w;
        long u0 = 1; long u = m; long v0 = 0; long v = 1;
        int sign = -1;
        while (q != 4 && q != 1) {
            long a = (m + w) / q;
            long m1 = a * q - m; long q1 = q0 + a * (m - m1);
            long u1 = a * u + u0; long v1 = a * v + v0;
            m = m1; q0 = q; q = q1;
            u0 = u; u = u1; v0 = v; v = v1;
            sign = -sign;
        }
        return new long[] {u, v, sign * q};
    }

    static final long[] pell(long d)
    {
        long[] z = pell4(d);
        long u = z[0]; long v = z[1]; long c = z[2];
        if (c == 4 || c == -4) {
            long u2 = u * u;
            c /= 4;
            u = u * ((u2 - 3 * c) / 2);
            v = v * ((u2 - c) / 2);
        }
        if (c < 0) {
            v = 2 * u * v;
            u = 2 * u * u + 1;
        }
        return new long[] {u, v};
    }


    private static final boolean[] q11, q63, q64, q65;

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

    public static final int squareTest(int n)
    {
        if (n < 0)
            return -1;
        int t = n & 63;
        if (!q64[t])
            return -1;
        if (!q63[n % 63])
            return -1;
        if (!q65[n % 65])
            return -1;
        if (!q11[n % 11])
            return -1;
        int q = integerSqrt(n);
        return q * q == n ? q : -1;
    }

    public static final long squareTest(long n)
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

    public static final BigInteger squareTest(final BigInteger n)
    {
        final BigInteger MinusOne = BigInteger.valueOf(-1);
        if (n.signum() < 0)
            return MinusOne;
        int t = n.and(BigInteger.valueOf(63)).intValueExact();
        if (!q64[t])
            return MinusOne;
        int r = n.mod(BigInteger.valueOf(45045)).intValueExact();
        if (!q63[r % 63])
            return MinusOne;
        if (!q65[r % 65])
            return MinusOne;
        if (!q11[r % 11])
            return MinusOne;
        BigInteger q = integerSqrt(n);
        return q.multiply(q).equals(n) ? q : MinusOne;
    }

    public static final boolean isSquare(int n)
    {
        return squareTest(n) >= 0;
    }

    public static final boolean isSquare(long n)
    {
        return squareTest(n) >= 0;
    }

    public static final boolean isSquare(final BigInteger n)
    {
        return squareTest(n).signum() >= 0;
    }

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

    public static final int[] primes;
    static {
        int n = 1000000;
        //System.err.println("Sieving for primes until " + n);
        boolean[] sieve = eratosthenes(n);
        // count how many primes we actually have until n
        int count = 0;
        for (int i = 1; i <= n; ++i) {
            if (sieve[i]) ++count;
        }
        //System.err.println(" ... found " + count + " primes");
        // now creating array of primes with correct size and insert the primes
        primes = new int[count];
        int k = 0;
        for (int i = 1; i <= n; ++i) {
            if (sieve[i]) {
                primes[k++] = i;
            }
        }
        // give memory for sieve back for garbage collector
        sieve = null;
        if (k != count) {
            System.err.println("Error during initialization of prime array.");
        }
    }

    public static final boolean isPrime(long n)
    {
        if (n < 2)
            return false;
        if (n == 2 || n == 3)
            return true;
        long limit = integerSqrt(n);
        for (int i = 0; i < primes.length; ++i) {
            long p = primes[i];
            if (n % p == 0) {
                return false;
            } else if (p > limit) {
                return true;
            }
        }
        long p = primes[primes.length - 1];
        long offset = p % 6 == 1 ? 4 : 2;        /* p == 1 or 5 mod 6 */
        do {
            p += offset;
            offset = 6 - offset;
            if (n % p == 0) {
                return false;
            }
        } while (p <= limit);
        return true;
    }

    public static boolean isSquareFree(PrimeFactors primefactors)
    {
        int n = primefactors.nFactors();
        for (int i = 0; i < n; ++i) {
            if (primefactors.factors(i).exponent() >= 2)
                return false;
        }
        return true;
    }

    public static boolean isSquareFree(long n)
    {
        return isSquareFree(new PrimeFactors(n));
    }

    // multiplicative order of a modulo n
    private static long ord1(long a, long n)
    {
        // implementation directly to definition, but slow
        long r = 1;
        long power = a % n;
        while (power != 1) {
            ++r;
            power = (power * a) % n;
        }
        return r;
    }

    private static long ord2(long a, long p, int e)
    {
        long m = power(p, e);
        long t = m / p * (p - 1);
        long[] fac = divisors(t);
        for (int i = 0; i < fac.length; ++i) {
            if (powerMod(a, fac[i], m) == 1)
                return fac[i];
        }
        return 0;
    }

    // multiplicative order of a modulo n
    private static long ord2(long a, long n)
    {
        PrimeFactors pf = new PrimeFactors(n);
        long res = 1;
        int nFactors = pf.nFactors();
        for (int i = 0; i < nFactors; ++i) {
            res = lcm(res, ord2(a, pf.factors(i).base(), pf.factors(i).exponent()));
        }
        return res;
    }

    // multiplicative order of a modulo n
    public static long ord(long a, long n)
    {
        if (gcd(a, n) > 1)
            throw new RuntimeException("multiplicative order not defined");
        return n < 120 ? ord1(a, n) : ord2(a, n);
    }

    // check if n is primitive root modulo p
    // p must be prime number > 2
    public static boolean isPrimitiveRoot(long n, long p)
    {
        PrimeFactors pf = new PrimeFactors(p-1);
        int nFactors = pf.nFactors();
        for (int i = 0; i < nFactors; ++i) {
            long m = (p-1) / pf.factors(i).base();
            if (powerMod(n, m, p) == 1)
                return false;
        }
        return true;
    }

    // auxiliary function for repeated calls with fixed p
    // check if n is primitive root modulo p
    // p must be prime number > 2
    // pf must contain the prime factors of p-1
    public static boolean isPrimitiveRoot(long n, long p, PrimeFactors pf)
    {
        int nFactors = pf.nFactors();
        for (int i = 0; i < nFactors; ++i) {
            long m = (p-1) / pf.factors(i).base();
            if (powerMod(n, m, p) == 1)
                return false;
        }
        return true;
    }

    // find first primitive root of p
    // p must be prime number > 2
    public static long primitiveRootPrime(long p)
    {
        PrimeFactors pf = new PrimeFactors(p-1);
        long x = 2;
        while (x <= p-1) {
            if (isPrimitiveRoot(x, p, pf)) {
                return x;
            } else {
                ++x;
                if (isSquare(x))
                    ++x;
            }
        }
        throw new RuntimeException("argument must be an odd prime number");
    }

    // find first primitive root of n
    // returns 0 if no primitive root exists
    public static long primitiveRoot(long n)
    {
        if (n <= 0)
            return 0;
        PrimeFactors pf = new PrimeFactors(n);
        if (pf.nFactors() == 1) {
            if (pf.factors(0).base() == 2 && pf.factors(0).exponent() >= 3)
                return 0;
        } else if (pf.nFactors() == 2) {
            if (pf.factors(0).base() != 2 || pf.factors(0).exponent() >= 2)
                return 0;
        } else if (pf.nFactors() >= 3) {
            return 0;
        }
        long phiValue = phi(pf);
        for (long i = 1; i < n; ++i) {
            if (gcd(i, n) == 1) {
                long order = 1;
                long power = i % n;
                while (power != 1) {
                    ++order;
                    power = (power * i) % n;
                }
                if (order == phiValue)
                    return i;
            }
        }
        return 0;
    }

    // find all primitive roots of n
    public static long[] primitiveRoots(long n)
    {
        if (n <= 0)
            return new long[0];
        PrimeFactors pf = new PrimeFactors(n);
        if (pf.nFactors() == 1) {
            if (pf.factors(0).base() == 2 && pf.factors(0).exponent() >= 3)
                return new long[0];
        } else if (pf.nFactors() == 2) {
            if (pf.factors(0).base() != 2 || pf.factors(0).exponent() >= 2)
                return new long[0];
        } else if (pf.nFactors() >= 3) {
            return new long[0];
        }
        long phiValue = phi(pf);
        List<Long> roots = new ArrayList<>();
        for (long i = 1; i < n; ++i) {
            if (gcd(i, n) == 1) {
                long order = 1;
                long power = i % n;
                while (power != 1) {
                    ++order;
                    power = (power * i) % n;
                }
                if (order == phiValue) {
                    roots.add(i);
                    continue;
                }
            }
        }
        long[] result = new long[roots.size()];
        for (int i = 0; i < roots.size(); ++i)
            result[i] = roots.get(i);
        return result;
    }

    public static boolean isCarmichaelNumber(PrimeFactors primefactors)
    {
        final int n = primefactors.nFactors();
        if (n <= 2 || even(n))
            return false;  // must be odd and have at least 3 prime factors
        for (int i = 0; i < n; ++i) {
            if (primefactors.factors(i).exponent() >= 2)
                return false;  // not squarefree
            if ((primefactors.getNumber()-1) % (primefactors.factors(i).base()-1) != 0)
                return false;
        }
        return true;
    }

    public static boolean isCarmichaelNumber(long n)
    {
        return n >= 3 && odd(n) && isCarmichaelNumber(new PrimeFactors(n));
    }

    public static boolean isDeficientNumber(long n)
    {
        return aliquotSum(n) < n;
    }

    public static boolean isPerfectNumber(long n)
    {
        return aliquotSum(n) == n;
    }

    public static boolean isAbundantNumber(long n)
    {
        return aliquotSum(n) > n;
    }

    public static boolean isAmicablePair(long m, long n)
    {
        return aliquotSum(m) == n && aliquotSum(n) == m;
    }

    public static long firstNumberWithNDivisors(int N)
    {
        PrimeFactors nFactors = new PrimeFactors(N);
        if (nFactors.isPrime()) {
            // N = p with p prime number
            return power(2L, N-1);
        } else if (nFactors.nFactors() == 2
                        && nFactors.factors(0).exponent() == 1
                        && nFactors.factors(1).exponent() == 1) {
            // N = p*q with p < q prime numbers
            return power(2L, (int)nFactors.factors(1).base()-1) *
                   power(3L, (int)nFactors.factors(0).base()-1);
        }
        long n = 1;
        while (sigma0(n) != N) {
            ++n;
        }
        return n;
    }

    public static final int nSquaresFor(int n)
    {
        if (n < 0)
            return -1;
        if (squareTest(n) >= 0)
            return 1;
        PrimeFactors primeFactors = new PrimeFactors((long)n);
        boolean twoSquares = true;
        for (PrimeFactors.Factor factor : primeFactors.getFactors()) {
            if (factor.base() % 4 == 3 && factor.exponent() % 2 == 1) {
                twoSquares = false;
                break;
            }
        }
        if (twoSquares)
            return 2;
        int n2 = n;
        while (n2 % 4 == 0) {
            n2 /= 4;
        }
        return n2 % 8 == 7 ? 4 : 3;
    }
}
