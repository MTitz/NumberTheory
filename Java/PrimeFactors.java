import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

public class PrimeFactors
{
    public static class Factor
    {
        public Factor(long base, int exponent)
        {
            this.base = base;
            this.exponent = exponent;
        }

        public long base()
        {
            return this.base;
        }

        public int exponent()
        {
            return this.exponent;
        }

        @Override
        public String toString()
        {
            StringBuilder str = new StringBuilder();
            str.append(base);
            if (exponent >= 2) {
                str.append('^');
                str.append(exponent);
            }
            return str.toString();
        }

        final private long base;
        final private int exponent;
    }


    public PrimeFactors(long number)
    {
        this.number = number;
        if (number == 0) {
            sign = 0;
            factor = Collections.emptyList();
        } else {
            sign = number > 0 ? 1 : -1;
            factor = factorize(sign * number);
        }
    }

    public long getNumber()
    {
        return number;
    }

    public List<Factor> getFactors()
    {
        return factor;
    }

    public int nFactors()
    {
        return factor.size();
    }

    public Factor getFactor(int i)
    {
        return factor.get(i);
    }

    public boolean isPrime()
    {
        return factor.size() == 1 && factor.get(0).exponent() == 1;
    }

    public List<Factor> factorize(long n)
    {
        if (n == 0 || n == 1)
            return Collections.emptyList();
        if (n < 0)
            return factorize(-n);
        List<Factor> factors = new LinkedList<>();
        long limit = NumberTheory.integerSqrt(n);
        final int nPrimes = NumberTheory.primes.length;
        for (int i = 0; i < nPrimes; ++i) {
            int p = NumberTheory.primes[i];
            int e = 0;
            while (n % p == 0) {
                n /= p;
                e += 1;
            }
            if (e > 0) {
                factors.add(new Factor(p, e));
                if (n == 1)
                    return factors;
                limit = NumberTheory.integerSqrt(n);
            }
            if (p > limit) {
                if (n > 1)
                    factors.add(new Factor(n, 1));
                return factors;
            }
        }
        if (n > 1) {
            int p = NumberTheory.primes[nPrimes - 1];
            int offset = p % 6 == 1 ? 4 : 2;        /* p == 1 or 5 mod 6 */
            while (n > 1) {
                p += offset;
                offset = 6 - offset;
                int e = 0;
                while (n % p == 0) {
                    n /= p;
                    e += 1;
                }
                if (e > 0) {
                    factors.add(new Factor(p, e));
                    if (n == 1)
                        return factors;
                    limit = NumberTheory.integerSqrt(n);
                }
                if (p > limit) {
                    factors.add(new Factor(n, 1));
                    return factors;
                }
            }
        }
        return factors;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        if (sign != 1 || factor.isEmpty()) {
            str.append(sign);
        }
        boolean printOperator = sign != 1;
        for (Factor f : factor) {
            if (printOperator) {
                str.append('*');
            }
            str.append(f);
            printOperator = true;
        }
        return str.toString();
    }

    final private long number;
    final private int sign;
    final private List<Factor> factor;
}
