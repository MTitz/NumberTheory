// Head's algorithm for multiplication, see section 4.3 in:
// [Giblin] Peter Giblin, "Primes and Programming", Cambridge University Press, 1993

import static java.lang.Math.*;

public class HeadsAlgorithm {
    public HeadsAlgorithm(long m)
    {
        this.m = m;
        capT = (long)floor(sqrt(m) + 0.5);
        t = capT * capT - m;
    }

    public long multiplyModM(long x, long y)
    {
        long a = x / capT;
        long b = x - a * capT;
        long c = y / capT;
        long d = y - c * capT;
        long z = (a * d + b * c) % m;
        long e = (a * c) / capT;
        long f = a * c - e * capT;
        long v = (z + e * t) % m;
        long g = v / capT;
        long h = v - g * capT;
        long j = (f + g) * t % m;
        long k = (j + b * d) % m;
        return (h * capT + k) % m;
    }

    private final long m;
    private final long capT;
    private final long t;
}
