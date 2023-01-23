/* Find divisibility rules */

public class DivRules
{
    public static void usage()
    {
        System.out.println("Call:");
        System.out.println("    java DivRules limit");
        System.out.println("    java DivRules base limit");
        System.out.println("  (base and limit have to be integers >= 2)");
    }

    public static boolean isPrime(int n)
    {
        if (n < 2)
            return false;
        if (n == 2)
            return true;
        if (n % 2 == 0)
            return false;
        int limit = (int)Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static int nextPrime(int n)
    {
        if (n <= 2)
            return 2;
        if ((n & 1) == 0)
            ++n;
        while (!isPrime(n))
            n += 2;
        return n;
    }

    public static void divisibilityTests(int base, int limit)
    {
        System.out.println("Divisibility rules in base " + base + " number system for primes <= " + limit);
        System.out.println();
        int p = 2;
        while (p <= limit) {
            if (base % p == 0) {
                /* last digit rule */
                System.out.printf("rule for %4d: last digit of", p);
                for (int i = 0; i < base; ++i) {
                    if (i % p == 0) {
                        System.out.printf(" %d", i);
                    }
                }
            } else {
                int power = 1;        /* power modulo p */
                int indexM1 = -1;
                int indexP1 = -1;
                for (int n = 1; n <= p-1; ++n) {
                    /* p divides n**(p-1)-1, so limit p-1 is sufficient */
                    power *= base;
                    power %= p;
                    if ((power+1) % p == 0) {
                        indexP1 = n;
                    }
                    if ((power-1) % p == 0) {
                        indexM1 = n;
                        break;
                    }
                }
                System.out.printf("rule for %4d: S%d", p, indexM1);
                if (indexP1 != -1)
                    System.out.printf(" or A%d", indexP1);
            }
            System.out.println();

            p = nextPrime(++p);
        }
    }

    public static void main(String[] args)
    {
        int base = 10;
        int limit = 1000;
        if (args.length == 1) {
            limit = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            base  = Integer.parseInt(args[0]);
            limit = Integer.parseInt(args[1]);
        } else {
            usage();
            return;
        }
        if (base >= 2 && limit >= 2) {
            divisibilityTests(base, limit);
        } else {
            usage();
        }
    }
}
