// Head's algorithm for integer multiplication modulo m, see section 4.3 in
// Peter Giblin, "Primes and Programming", Cambridge University Press, 1993

import static java.lang.Math.*;

public class HeadsAlgorithm {
    public HeadsAlgorithm(long m)
    {
        if (m <= 0)
            throw new IllegalArgumentException("m must be positive for Head's algorithm");
        if (m >= Long.MAX_VALUE / 4)
            throw new IllegalArgumentException("m too large for Head's algorithm");
        this.m = m;
        this.T = (long)floor(sqrt(m) + 0.5);
        this.t = T * T - m;
    }

    public long multiplyModM(long x, long y)
    {
        long a = x / T;
        long b = x - a * T;
        long c = y / T;
        long d = y - c * T;
        long z = floorMod(a * d + b * c, m);
        long e = (a * c) / T;
        long f = a * c - e * T;
        long v = floorMod(z + e * t, m);
        long g = v / T;
        long h = v - g * T;
        long j = floorMod((f + g) * t, m);
        long k = floorMod(j + b * d, m);
        return floorMod(h * T + k, m);
    }

    private final long m;
    private final long T;
    private final long t;
}
