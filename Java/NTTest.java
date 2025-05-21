// Literature
//  [Andrews] George E. Andrews, "Number Theory", Dover, 1994
//  [BachShallit] Bach, Shallit, "Algorithmic Number Theory", 2nd edition, MIT-Press, 1996
//  [Clessa] J. J. Clessa, "Math and Logic Puzzles for PC Enthusiasts", Dover, 1996
//  [Giblin] Peter Giblin, "Primes and Programming", Cambridge University Press, 1993
//  [Knuth_2] Donald E. Knuth, "The Art of Computer Programming v. 2. Seminumerical Algorithms", 3rd edition, Addison-Wesley, 1997
//  [Nathanson] Melvyn B. Nathanson, "Elementary Methods in Number Theory", Springer, 2000
//  [Rosen] Kenneth H. Rosen (editor-in-chief), "Handbook of discrete and combinatorial mathematics", CRC Press, 2000
//  [Scheid] Harald Scheid, "Zahlentheorie", 3. Auflage, Spektrum Akademischer Verlag, 2003
//  [Yan] Song Y. Yan, "Number Theory for Computing", 2nd edition, Springer, 1998


import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class NTTest
{
    // Maximal exponents for factorization of a^n-1, a^n+1 for a = 2 and a = 10
    static final int MaxExponent2  = 48;
    static final int MaxExponent10 = 12;

    static final int AmicableNumbersLimit = 2500000;

    private static void printArray(long[] a)
    {
        for (int i = 0; i < a.length; ++i) {
            if (i > 0)
                System.out.print(' ');
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static <T> void printList(List<T> list)
    {
        for (int i = 0; i < list.size(); ++i) {
            if (i > 0)
                System.out.print(' ');
            System.out.print(list.get(i));
        }
        System.out.println();
    }

    private static void intBitlengthTest(int n)
    {
        System.out.println("bitlength(" + n + ") = " + NumberTheory.bitlength(n));
    }

    private static void bitlengthTestcases()
    {
        intBitlengthTest(1);
        intBitlengthTest(2);
        intBitlengthTest(3);
        intBitlengthTest(4);
        intBitlengthTest(1023);
        intBitlengthTest(1024);
        intBitlengthTest(1025);
        intBitlengthTest(0);
        intBitlengthTest(-1023);
        intBitlengthTest(-1024);
        intBitlengthTest((1<<24)-1);
        intBitlengthTest(1<<24);
        System.out.println("bitlength(" + Long.MAX_VALUE + ") = " + NumberTheory.bitlength(Long.MAX_VALUE));
    }

    private static void digitSumTest(int n)
    {
        System.out.println("sum_of_digits(" + n + ") = " + NumberTheory.digitSum(n));
    }

    private static void digitSumTest(int n, int base)
    {
        System.out.println("base " + base + " sum_of_digits(" + n + ") = " + NumberTheory.digitSum(n, base));
    }

    private static void digitSumTestLong(long n, int base)
    {
        System.out.println("base " + base + " sum_of_digits(" + n + ") = " + NumberTheory.digitSum(n, base));
    }

    private static void digitSumTestcases()
    {
        digitSumTest(0);
        digitSumTest(1);
        digitSumTest(10);
        digitSumTest(12);
        digitSumTest(144);
        digitSumTest(1024);
        digitSumTest(1024, 2);
        digitSumTest(1024, 4);
        digitSumTest(1024, 16);
        digitSumTest(999999999);
        digitSumTest(999999999, 2);
        digitSumTestLong(123456789123456789L, 10);
        digitSumTestLong(123456789123456789L,  2);
    }

    private static void oddEvenTestcases()
    {
        for (int i : new int[] {-2, -1, 0, 1, 2, 15, 16, 79, 80, 81, 82, 83, 84}) {
            System.out.print("" + i + " is ");
            if (NumberTheory.odd(i))
                System.out.print("odd");
            if (NumberTheory.even(i))
                System.out.print("even");
            System.out.println();
        }
    }

    private static void powTest(int base, int maxExponent)
    {
        for (int i = 0; i <= maxExponent; ++i) {
            System.out.println(base + "^" + i + " = " + NumberTheory.power(base, i));
        }
    }

    private static void powTestcases()
    {
        powTest(0, 4);
        System.out.println();
        powTest(1, 4);
        System.out.println();
        powTest(2, 24);
        System.out.println();
        powTest(3, 12);
        System.out.println();
        powTest(4, 8);
        System.out.println();
        powTest(5, 6);
    }


    private static void squareTestTest(int n)
    {
        System.out.println("squareTest(" + n + ") = " + NumberTheory.squareTest(n));
    }

    private static void squareTestTestcases()
    {
        for (int i = -4; i <= 100; ++i) {
            squareTestTest(i);
        }
        squareTestTest(193);
        squareTestTest(585);
    }

    private static void isSquareTest(long n)
    {
        System.out.println("  Is " + n + " a square: " + (NumberTheory.squareTest(n) >= 0));
    }

    private static void isSquareTestcases()
    {
        System.out.println("Signed case:");
        for (int i = -4; i <= 9; ++i) {
            isSquareTest(i);
        }
        isSquareTest(      193L);
        isSquareTest(      585L);
        isSquareTest( 99999999L);
        isSquareTest(100000000L);
        isSquareTest(100000001L);

        System.out.println("Unsigned case:");
        System.out.println("  N/A");
    }

    private static void ordTest(long a, long n)
    {
        System.out.println("ord_" + n + "(" + a + ") = " + NumberTheory.ord(a, n));
    }

    private static void ordTestcases()
    {
        int n = 11;
        for (int i = 1; i <= 10; ++i) {
            ordTest(i, n);
        }
        ordTest(7, 31);       // expect 15, [Scheid] page 143
        ordTest(37,   1000);  // expect 100
        ordTest(54, 100001);  // expect 9090
        ordTest(997, 10000);
        ordTest(5040, 5041);
        try {
            NumberTheory.ord(6, 12);
        }
        catch (RuntimeException re) {
            System.out.println("Got expected exception for ord(6, 12)");
        }
    }

    private static void powerModFermatTest(long p)
    {
        System.out.println("powerMod(2, " + (p-1) + ", " + p + ") = " + NumberTheory.powerMod(2L, p-1, p));
    }

    private static void powerModTestcases()
    {
        System.out.println("powerMod(2,   10, 10000000) = " + NumberTheory.powerMod(2,   10, 1000000));
        System.out.println("powerMod(2,  100, 10000000) = " + NumberTheory.powerMod(2,  100, 1000000));
        System.out.println("powerMod(2, 1000, 10000000) = " + NumberTheory.powerMod(2, 1000, 1000000));
        System.out.println();
        System.out.println("powerMod(2,   10, 10000000) = " + NumberTheory.powerMod(2L,   10L, 1000000L));
        System.out.println("powerMod(2,  100, 10000000) = " + NumberTheory.powerMod(2L,  100L, 1000000L));
        System.out.println("powerMod(2, 1000, 10000000) = " + NumberTheory.powerMod(2L, 1000L, 1000000L));
        System.out.println();

        // [Giblin], chapter 4.2
        System.out.println("powerMod(7, 50, 11) = " + NumberTheory.powerMod(7, 50, 11));
        // [Nathanson], chapter 2.5, exercise 1 (page 71)
        System.out.println("powerMod(3, 512, 1024) = " + NumberTheory.powerMod(3, 512, 1024));
        // [Nathanson], chapter 2.5, exercise 2 (page 71)
        System.out.println("powerMod(7, 51, 144) = " + NumberTheory.powerMod(7, 51, 144));
        // [Nathanson], chapter 2.5, exercise 3 (page 71)
        System.out.println("powerMod(2, 10^8, 31) = " + NumberTheory.powerMod(2, 100000000, 31));
        // [Nathanson], chapter 2.6, example on page 74
        System.out.println("powerMod(2, 850, 851) = " + NumberTheory.powerMod(2, 850, 851));
        // [Nathanson], chapter 2.6, example on page 75
        System.out.println("powerMod(7, 340, 341) = " + NumberTheory.powerMod(7, 340, 341));

        // [Giblin], chapter 4.3
        powerModFermatTest(1000000007L);
        powerModFermatTest(10000000019L);
        powerModFermatTest(100000000003L);
        powerModFermatTest(1000000000039L);
        powerModFermatTest(10000000000037L);
        powerModFermatTest(100000000000031L);
        powerModFermatTest(1000000000000037L);
    }

    private static void hyperExpModTestcases()
    {
        System.out.println("hyperExpMod(3, 2, 10^8) = " + NumberTheory.hyperExpMod(3, 2, 100000000));
        System.out.println("hyperExpMod(9, 3, 10^8) = " + NumberTheory.hyperExpMod(9, 3, 100000000));
        System.out.printf("hyperExpMod(1777, 1855, 10^8)  = %16d%n", NumberTheory.hyperExpMod(1777, 1855, 100000000));
        System.out.printf("hyperExpMod(1777, 1855, 10^16) = %16d%n", NumberTheory.hyperExpMod(1777L, 1855L, 10000000000000000L));
    }

    private static void primeInFactorialTest(int n, int p)
    {
        System.out.println("In " + n + "! has the prime factor " + p +
                " the exponent " + NumberTheory.primefactorInFactorial(n, p));
    }

    private static void primeInFactorialTestcases()
    {
        primeInFactorialTest(4, 2);
        primeInFactorialTest(4, 3);
        primeInFactorialTest(4, 5);
        // Example for 10! compare [Nathanson], page 29
        primeInFactorialTest(10, 2);
        primeInFactorialTest(10, 3);
        primeInFactorialTest(10, 5);
        primeInFactorialTest(10, 7);
        // Example for 100! compare [Giblin], page 27
        primeInFactorialTest(100, 2);
        primeInFactorialTest(100, 3);
        primeInFactorialTest(100, 5);
        primeInFactorialTest(1000, 2);
        primeInFactorialTest(1000, 3);
        primeInFactorialTest(1000, 5);
        primeInFactorialTest(1000, 997);
        System.out.println();
        try {
            NumberTheory.primefactorInFactorial(-1, 2);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Got expected exception for primeInFactorialTest(-1, 2)");
        }
        try {
            NumberTheory.primefactorInFactorial(4, 1);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Got expected exception for primeInFactorialTest(4, 1)");
        }
        System.out.println();
        for (int n = 0; n <= 30; ++n) {
            System.out.printf("%3d! ends with %1d zeros.%n",
                    n, NumberTheory.primefactorInFactorial(n, 5));
        }
        for (int n : new int[] {40, 50, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000}) {
            System.out.printf(" %d! ends with %d zeros.%n",
                    n, NumberTheory.primefactorInFactorial(n, 5));
        }
    }

    private static void gcdTest(long a, long b)
    {
        System.out.println("gcd(" + a + ", " + b + ") = " + NumberTheory.gcd(a, b));
    }

    private static void gcdTest(long a, long b, long c)
    {
        System.out.println("gcd(" + a + ", " + b + ", " + c + ") = " + NumberTheory.gcd(a, b, c));
    }

    private static void gcdTestcases()
    {
        gcdTest(0, 0);           // compare [Knuth_2], chapter 4.5.2 (1)
        gcdTest(12, 0);          // compare [Knuth_2], chapter 4.5.2 (4)
        gcdTest(-4, 0);          // compare [Knuth_2], chapter 4.5.2 (4)
        gcdTest(0, 20);
        gcdTest(12, 20);
        gcdTest(20, 12);         // compare [Knuth_2], chapter 4.5.2 (2)
        gcdTest(-12,20);         // compare [Knuth_2], chapter 4.5.2 (3)
        gcdTest(12, 21);
        gcdTest(24, 25);
        gcdTest(24, 36);
        gcdTest(25, 36);
        gcdTest(2520, 18480);
        gcdTest(10, 12, 16);
        gcdTest(39, 102, 75);    // [Andrews], chapter 2-4, exercise 11 (page 29)
        gcdTest(935, 1122);      // [Nathanson], chapter 1.2, exercise 1 (page 14)
        gcdTest(168, 252, 294);  // [Nathanson], chapter 1.2, exercise 2 (page 14)
        gcdTest(40902, 24140);   // [Knuth_2], chapter 4.5.2, example
    }

    private static void lcmTest(long a, long b)
    {
        System.out.println("lcm(" + a + ", " + b + ") = " + NumberTheory.lcm(a, b));
    }

    private static void lcmTest(long a, long b, long c)
    {
        System.out.println("lcm(" + a + ", " + b + ", " + c + ") = " + NumberTheory.lcm(a, b, c));
    }

    private static void lcmTestcases()
    {
        lcmTest(0, 0);
        lcmTest(12, 0);
        lcmTest(0, 20);
        lcmTest(12, 20);
        lcmTest(12, 21);
        lcmTest(24, 25);
        lcmTest(24, 36);
        lcmTest(25, 36);
        lcmTest(2520, 18480);
        lcmTest(10, 12, 16);
        lcmTest(39, 102, 75);    // [Andrews], chapter 2-4, exercise 11 (page 29)
    }

    private static void extendedEuclidTest(long a, long b)
    {
        NumberTheory.ExtendedEuclidResult r = NumberTheory.extendedEuclid(a, b);
        System.out.println("extendedEuclid(" + a + ", " + b + "): d = " + r.d + ", u = " + r.u + ", v = " + r.v);
    }

    private static void extendedEuclidTestcases()
    {
        extendedEuclidTest(12,  0);
        extendedEuclidTest(12, 20);
        extendedEuclidTest(12, 21);
        extendedEuclidTest(574, 252); // [Nathanson], page 19
    }

    static void modInverseTable(long m)
    {
        System.out.println("Modulus " + m + ':');
        for (long i = 1; i < m; ++i) {
            try {
                long inv = NumberTheory.modInverse(i, m);
                System.out.println("\t" + i + "\t" + inv);
            }
            catch (RuntimeException e) {
            }
        }
    }

    static void modInverseTest(long a, long m)
    {
        long inv = NumberTheory.modInverse(a, m);
        System.out.println("The inverse of " + a + " mod " + m + " is " + inv);
    }

    static void modInverseTestcases()
    {
        modInverseTable(2);
        modInverseTable(3);
        modInverseTable(4);
        modInverseTable(5);
        modInverseTable(6);
        modInverseTable(7);
        modInverseTable(11);
        modInverseTable(12);
        modInverseTable(13);
        modInverseTable(14);
        modInverseTable(21);  // [Yan] example 1.6.12 (pages 120, 121)

        System.out.println();
        // [Andrews] section 5-1, exercise 3 (page 61)
        modInverseTest(2, 5);
        modInverseTest(7, 9);
        modInverseTest(12, 17);

        // [Yan] example 1.6.14 (1) (pages 121, 122)
        modInverseTest(154, 801);
    }

    static NumberTheory.SimpleLinearCongruence[] exercise1 =
        { new NumberTheory.SimpleLinearCongruence(3, 5),
          new NumberTheory.SimpleLinearCongruence(2, 6)};

    // Sun-Tsu (cited in [Scheid])
    static NumberTheory.SimpleLinearCongruence[] exercise2 =
        { new NumberTheory.SimpleLinearCongruence(2, 3),
          new NumberTheory.SimpleLinearCongruence(3, 5),
          new NumberTheory.SimpleLinearCongruence(2, 7)};

    // [Yan] exercise 1.6.1 (page 132)
    static NumberTheory.SimpleLinearCongruence[] exercise3 =
        { new NumberTheory.SimpleLinearCongruence(2, 7),
          new NumberTheory.SimpleLinearCongruence(7, 9),
          new NumberTheory.SimpleLinearCongruence(3, 4)};

    static NumberTheory.LinearCongruence[] exercise4 =
       { new NumberTheory.LinearCongruence(3,  1,  5),
         new NumberTheory.LinearCongruence(4,  6, 14),
         new NumberTheory.LinearCongruence(5, 11,  3)};

    static NumberTheory.LinearCongruence[] exercise5 =
       { new NumberTheory.LinearCongruence(4,  2,  6),
         new NumberTheory.LinearCongruence(3,  5,  7),
         new NumberTheory.LinearCongruence(2,  4, 11)};

    // [Scheid] Kapitel 4.1 (Seite 199)
    static NumberTheory.SimpleLinearCongruence[] exercise6 =
       { new NumberTheory.SimpleLinearCongruence(-1, 3),
         new NumberTheory.SimpleLinearCongruence(-1, 4),
         new NumberTheory.SimpleLinearCongruence(-1, 5),
         new NumberTheory.SimpleLinearCongruence(-1, 6)};

    // [Scheid] Kapitel 4.1 (Seite 199)
    static NumberTheory.LinearCongruence[] exercise6b =
       { new NumberTheory.LinearCongruence(1, -1, 3),
         new NumberTheory.LinearCongruence(1, -1, 4),
         new NumberTheory.LinearCongruence(1, -1, 5),
         new NumberTheory.LinearCongruence(1, -1, 6)};

    static NumberTheory.SimpleLinearCongruence[] exercise7 =
       { new NumberTheory.SimpleLinearCongruence(1, 2),
         new NumberTheory.SimpleLinearCongruence(1, 3),
         new NumberTheory.SimpleLinearCongruence(1, 4),
         new NumberTheory.SimpleLinearCongruence(1, 5),
         new NumberTheory.SimpleLinearCongruence(1, 6),
         new NumberTheory.SimpleLinearCongruence(0, 7)};

    // from [Scheid] Kapitel IV.12, Aufgabe 6b (Seite 264)
    static NumberTheory.LinearCongruence[] exercise7a =
       { new NumberTheory.LinearCongruence(5, 2, 12),
         new NumberTheory.LinearCongruence(7, 0, 15)};

    static NumberTheory.LinearCongruence[] exercise7b =
       { new NumberTheory.LinearCongruence(5, 2, 12),
         new NumberTheory.LinearCongruence(7, 1, 15)};

    static NumberTheory.LinearCongruence[] exercise7c =
       { new NumberTheory.LinearCongruence(5, 2, 12),
         new NumberTheory.LinearCongruence(7, 2, 15)};

    static NumberTheory.SimpleLinearCongruence[] exercise8 =
       { new NumberTheory.SimpleLinearCongruence(1, 2),
         new NumberTheory.SimpleLinearCongruence(2, 4)};

    static NumberTheory.SimpleLinearCongruence[] exercise9a =
       { new NumberTheory.SimpleLinearCongruence(1,  6),
         new NumberTheory.SimpleLinearCongruence(1, 10),
         new NumberTheory.SimpleLinearCongruence(1, 15)};

    static NumberTheory.SimpleLinearCongruence[] exercise9b =
       { new NumberTheory.SimpleLinearCongruence(1,  6),
         new NumberTheory.SimpleLinearCongruence(1, 10),
         new NumberTheory.SimpleLinearCongruence(6, 15)};

    static NumberTheory.SimpleLinearCongruence[] exercise9c =
       { new NumberTheory.SimpleLinearCongruence( 1,  6),
         new NumberTheory.SimpleLinearCongruence( 1, 10),
         new NumberTheory.SimpleLinearCongruence(11, 15)};

    static NumberTheory.SimpleLinearCongruence[] exercise10 =
       { new NumberTheory.SimpleLinearCongruence(1,  2),
         new NumberTheory.SimpleLinearCongruence(1,  3),
         new NumberTheory.SimpleLinearCongruence(1,  4),
         new NumberTheory.SimpleLinearCongruence(1,  5),
         new NumberTheory.SimpleLinearCongruence(1,  6),
         new NumberTheory.SimpleLinearCongruence(1,  7),
         new NumberTheory.SimpleLinearCongruence(1,  8),
         new NumberTheory.SimpleLinearCongruence(1,  9),
         new NumberTheory.SimpleLinearCongruence(1, 10),
         new NumberTheory.SimpleLinearCongruence(1, 11),
         new NumberTheory.SimpleLinearCongruence(1, 12),
         new NumberTheory.SimpleLinearCongruence(1, 13),
         new NumberTheory.SimpleLinearCongruence(1, 14),
         new NumberTheory.SimpleLinearCongruence(1, 15),
         new NumberTheory.SimpleLinearCongruence(1, 16),
         new NumberTheory.SimpleLinearCongruence(1, 17),
         new NumberTheory.SimpleLinearCongruence(1, 18),
         new NumberTheory.SimpleLinearCongruence(1, 19),
         new NumberTheory.SimpleLinearCongruence(1, 20),
         new NumberTheory.SimpleLinearCongruence(1, 21),
         new NumberTheory.SimpleLinearCongruence(1, 22),
         new NumberTheory.SimpleLinearCongruence(1, 23),
         new NumberTheory.SimpleLinearCongruence(1, 24),
         new NumberTheory.SimpleLinearCongruence(1, 25),
         new NumberTheory.SimpleLinearCongruence(1, 26),
         new NumberTheory.SimpleLinearCongruence(1, 27),
         new NumberTheory.SimpleLinearCongruence(1, 28),
         new NumberTheory.SimpleLinearCongruence(1, 29),
         new NumberTheory.SimpleLinearCongruence(1, 30),
         new NumberTheory.SimpleLinearCongruence(0, 31)};

    static NumberTheory.SimpleLinearCongruence[] exercise11 =
       { new NumberTheory.SimpleLinearCongruence(0,  2),
         new NumberTheory.SimpleLinearCongruence(0,  3),
         new NumberTheory.SimpleLinearCongruence(0,  4),
         new NumberTheory.SimpleLinearCongruence(0,  5),
         new NumberTheory.SimpleLinearCongruence(0,  6),
         new NumberTheory.SimpleLinearCongruence(0,  7),
         new NumberTheory.SimpleLinearCongruence(0,  8),
         new NumberTheory.SimpleLinearCongruence(0,  9),
         new NumberTheory.SimpleLinearCongruence(0, 10),
         new NumberTheory.SimpleLinearCongruence(0, 11),
         new NumberTheory.SimpleLinearCongruence(0, 12),
         new NumberTheory.SimpleLinearCongruence(0, 13),
         new NumberTheory.SimpleLinearCongruence(0, 14),
         new NumberTheory.SimpleLinearCongruence(0, 15),
         new NumberTheory.SimpleLinearCongruence(0, 16),
         new NumberTheory.SimpleLinearCongruence(0, 17),
         new NumberTheory.SimpleLinearCongruence(0, 18),
         new NumberTheory.SimpleLinearCongruence(0, 19),
         new NumberTheory.SimpleLinearCongruence(0, 20),
         new NumberTheory.SimpleLinearCongruence(0, 21),
         new NumberTheory.SimpleLinearCongruence(0, 22),
         new NumberTheory.SimpleLinearCongruence(0, 23),
         new NumberTheory.SimpleLinearCongruence(0, 24),
         new NumberTheory.SimpleLinearCongruence(0, 25),
         new NumberTheory.SimpleLinearCongruence(0, 26),
         new NumberTheory.SimpleLinearCongruence(0, 27),
         new NumberTheory.SimpleLinearCongruence(0, 28),
         new NumberTheory.SimpleLinearCongruence(0, 29),
         new NumberTheory.SimpleLinearCongruence(0, 30),
         new NumberTheory.SimpleLinearCongruence(1, 31)};

    // [Clessa], Micropuzzle 39
    static NumberTheory.SimpleLinearCongruence[] micropuzzle39 =
       { new NumberTheory.SimpleLinearCongruence( 3,  4),
         new NumberTheory.SimpleLinearCongruence( 1,  5),
         new NumberTheory.SimpleLinearCongruence( 2,  7),
         new NumberTheory.SimpleLinearCongruence( 2, 11),
         new NumberTheory.SimpleLinearCongruence(12, 17)};

    static NumberTheory.LinearCongruence[] unsolvableTest =
        { new NumberTheory.LinearCongruence(0, 1, 2),
          new NumberTheory.LinearCongruence(1, 1, 1)};

    static void linearCongruence(long a, long b, long m)
    {
        System.out.print("The congruence " + a + " * x = " + b + " mod " + m);
        NumberTheory.LinearCongruence congruence = new NumberTheory.LinearCongruence(a, b, m);
        NumberTheory.SimpleLinearCongruence solution = NumberTheory.solve(congruence);
        if (solution.m() == 0)
            System.out.println("  has no solution");
        else
            System.out.println("  has the solution  x = " + solution.b()
                 + " mod " + solution.m());
    }

    static void simpleLinearCongruence(NumberTheory.SimpleLinearCongruence[] congruence)
    {
        System.out.println();
        System.out.println("The problem");
        for (int i = 0; i < congruence.length; ++i) {
            System.out.println("x = " + congruence[i].b() + " mod " + congruence[i].m());
        }
        NumberTheory.SimpleLinearCongruence solution = NumberTheory.solve(congruence);
        if (solution.m() == 0)
            System.out.println("  has no solution");
        else
            System.out.println("  has the solution  x = " + solution.b()
                 + " mod " + solution.m());
    }

    static void linearCongruence(NumberTheory.LinearCongruence congruence[])
    {
        System.out.println();
        System.out.println("The problem");
        for (int i = 0; i < congruence.length; ++i) {
            System.out.println("" + congruence[i].a() + " * x = " + congruence[i].b() + " mod " + congruence[i].m());
        }
        NumberTheory.SimpleLinearCongruence solution = NumberTheory.solve(congruence);
        if (solution.m() == 0)
            System.out.println("  has no solution");
        else
            System.out.println("  has the solution  x = " + solution.b()
                 + " mod " + solution.m());
}

    static void linearCongruenceTestcases()
    {
        linearCongruence(1, 1, 1);
        linearCongruence(1, 1, 2);
        linearCongruence(0, 1, 2);
        linearCongruence(0, 0, 2);
        linearCongruence(3, 10, 12);
        linearCongruence(20, 15, 135);  // [Yan] example 1.6.18 (page 129, 130)
        linearCongruence(2, 10, 11);
        linearCongruence(10, 10, 11);
        linearCongruence(3, 11, 2275);  // [Andrews], section 5-3, example 5-6
        linearCongruence(1193, 367, 31500);  // [Scheid], Kapitel IV.1, Beispiel 3 (Seite 98)
        linearCongruence(154, 11, 803);
        linearCongruence(154, 22, 803);  // check [Yan] example 1.6.15 (page 124)
        linearCongruence( 4,  9, 11);  // [Nathanson], chapter 2.2, exercise 1 (page 56)
        linearCongruence(12,  3, 45);  // [Nathanson], chapter 2.2, exercise 2 (page 56)
        linearCongruence(28, 35, 42);  // [Nathanson], chapter 2.2, exercise 3 (page 56)
        linearCongruence(10, 3, 27);  // [Giblin}, chapter 3.2, exercise 2.2 (a)
        linearCongruence(35, 14, 182);  // [Giblin}, chapter 3.2, exercise 2.2 (a)

        // [Andrews], section 5-1, exercise 2 (a)-(f) (page 61)
        linearCongruence(99, 100, 101);
        linearCongruence(400898, 22, 400900);
        linearCongruence(27, 1, 51);
        linearCongruence(99, 100, 102);
        linearCongruence(30, 42, 49);
        linearCongruence(81, 57, 117);

        simpleLinearCongruence(exercise1);
        simpleLinearCongruence(exercise2);
        simpleLinearCongruence(exercise3);
        linearCongruence(exercise4);
        linearCongruence(exercise5);
        simpleLinearCongruence(exercise6);
        linearCongruence(exercise6b);
        simpleLinearCongruence(exercise7);
        linearCongruence(exercise7a);
        linearCongruence(exercise7b);
        linearCongruence(exercise7c);
        simpleLinearCongruence(exercise8);
        simpleLinearCongruence(exercise9a);
        simpleLinearCongruence(exercise9b);
        simpleLinearCongruence(exercise9c);
        simpleLinearCongruence(exercise10);
        simpleLinearCongruence(exercise11);
        simpleLinearCongruence(micropuzzle39);
        linearCongruence(unsolvableTest);
    }

    private static void pell4Tests()
    {
        for (long d : new long[] {11, 13, 17, 19, 61, 67, 101, 103, 107, 109, 211, 223}) {
            long[] result = NumberTheory.pell4(d);
            System.out.printf("%3d: (%d, %d, %d)%n", d, result[0], result[1], result[2]);
        }
    }

    static void pellEquationTestcases()
    {
        System.out.println("Testing Pell's equation routines");
        System.out.println();
        pell4Tests();
        System.out.println();
        for (long d = 2; d < 100; ++d) {
            if (NumberTheory.isSquareFree(d)) {
                long[] result = NumberTheory.pell(d);
                System.out.printf("%3d: (%d, %d)%n", d, result[0], result[1]);
            }
        }
        for (long d : new long[] {101, 103, 107, 109, 211, 223}) {
            long[] result = NumberTheory.pell(d);
            System.out.printf("%3d: (%d, %d)%n", d, result[0], result[1]);
       }
    }

    private static void intSqrtTest(int n)
    {
        System.out.println("integerSqrt(" + n + ") = " + NumberTheory.integerSqrt(n));
    }

    private static void intSqrtTest(long n)
    {
        System.out.println("integerSqrt(" + n + ") = " + NumberTheory.integerSqrt(n));
    }

    private static void intSqrtTestcases()
    {
        intSqrtTest(-1);
        intSqrtTest(0);
        intSqrtTest(1);
        intSqrtTest(15);
        intSqrtTest(16);
        intSqrtTest(17);
        intSqrtTest(123456789L);
        intSqrtTest(4123456789L);
        intSqrtTest(123456789123456789L);
    }

    private static void sqrtToContinuedFractionTest(long n)
    {
        System.out.print("cfrac sequence of sqrt(" + n + "):  ");
        List<Long> cfrac = NumberTheory.sqrtToContinuedFraction(n);
        printList(cfrac);
    }

    private static void sqrtToContinuedFractionTestcases()
    {
        for (int i = 1; i < 100; ++i) {
            sqrtToContinuedFractionTest(i);
        }
        sqrtToContinuedFractionTest(114);  // Example from https://en.wikipedia.org/wiki/Periodic_continued_fraction
        sqrtToContinuedFractionTest(709);
        sqrtToContinuedFractionTest(710);
    }

    private static void jacobiSymbolTestcases()
    {
        for (int i = -2; i <= 3; ++i)
            System.out.println("jacobi(" + i + ", 0) = " + NumberTheory.jacobi(i, 0));
        for (int i = 1; i <= 6; ++i)
            System.out.println("legendre(" + i  + ", 7) = " + NumberTheory.jacobi(i,7));
        System.out.println();
        System.out.println("legendre(33, 83) = " + NumberTheory.jacobi(33, 83));
        System.out.println("legendre(46, 997) = " + NumberTheory.jacobi(46, 997));
        System.out.println("legendre(286, 563) = " + NumberTheory.jacobi(286, 563));
        System.out.println("jacobi(1009, 2307) = " + NumberTheory.jacobi(1009, 2307));
        System.out.println("jacobi(1009, -2307) = " + NumberTheory.jacobi(1009, -2307));
        System.out.println("jacobi(-1009, -2307) = " + NumberTheory.jacobi(-1009, -2307));
        System.out.println("jacobi(4, 4) = " + NumberTheory.jacobi(4, 4));
        System.out.println("jacobi(127, 256) = " + NumberTheory.jacobi(127, 256));
    }

    private static void isPrimeTest(long n)
    {
        System.out.println("Is a prime " + n + ": " + NumberTheory.isPrime(n));
    }

    private static void MillerRabinTest(BigInteger n)
    {
        boolean result = NumberTheory.MillerRabin(n);
        System.out.printf("%24d is %s%n", n, result ? "probably prime" : "composite");
    }

    private static void MillerRabinTest(long n)
    {
        MillerRabinTest(BigInteger.valueOf(n));
    }

    private static void MillerRabinTestcases()
    {
        System.out.println("Testing Miller-Rabin method:");
        for (long n = 25; n <= 100; n += 2) {
            MillerRabinTest(n);
        }
        MillerRabinTest(NumberTheory.power(BigInteger.TWO, 61).subtract(BigInteger.ONE));
        MillerRabinTest(NumberTheory.power(BigInteger.TWO, 67).subtract(BigInteger.ONE));
        MillerRabinTest(NumberTheory.power(BigInteger.TWO, 89).subtract(BigInteger.ONE));
    }

    private static void pollardRhoTest(long n, long maxIterations)
    {
        long d = NumberTheory.pollardRho(n, maxIterations);
        if (d <= 0) {
            System.out.printf("%24d: No factor found after %d iterations%n", n, maxIterations);
        } else {
            System.out.printf("%24d: factor %d found%n", n, d);
        }
    }

    private static void pollardRhoTest(long n)
    {
        long d = NumberTheory.pollardRho(n);
        if (d < 0) {
            System.out.printf("%24d: No factor found after %d iterations%n", n, -d);
        } else {
            System.out.printf("%24d: factor %d found%n", n, d);
        }
    }

    private static void pollardRhoTest(BigInteger n, long maxIterations)
    {
        BigInteger d = NumberTheory.pollardRho(n, maxIterations);
        if (d.signum() < 0) {
            System.out.printf("%24d: No factor found after %d iterations%n", n, d.negate());
        } else {
            System.out.printf("%24d: factor %d found%n", n, d);
        }
    }

    private static void pollardRhoTest(BigInteger n)
    {
        final long MAX_POLLARD_RHO_ITERATIONS = 32000;
        pollardRhoTest(n, MAX_POLLARD_RHO_ITERATIONS);
    }

    private static void pollardRhoTestcases()
    {
        System.out.println("Testing Pollard Rho method:");
        pollardRhoTest(   1,   100);
        pollardRhoTest( 997,   100);
        pollardRhoTest( 403, 40000);
        pollardRhoTest(1000, 40000);
        pollardRhoTest(1387, 40000);
        pollardRhoTest(4294967297L);

        // Example from W. S. Jevons, 1874 cited in [BachShallit]:
        pollardRhoTest(8616460799L, 1000000L);

        // Product of two twin primes
        pollardRhoTest(10030423103L);

        // Project Euler, Problem 3
        pollardRhoTest(600851475143L);

        // Compare [Scheid] page 28, challenge from a letter by Mersenne to Fermat
        pollardRhoTest(100895598169L, 1000000L);

        pollardRhoTest(195545750400L);

        System.out.println();
        System.out.println("Testing Pollard Rho BigInteger method:");
        pollardRhoTest(BigInteger.valueOf(   1),   100);
        pollardRhoTest(BigInteger.valueOf( 997),   100);
        pollardRhoTest(BigInteger.valueOf( 403), 40000);
        pollardRhoTest(BigInteger.valueOf(1000), 40000);
        pollardRhoTest(BigInteger.valueOf(1387), 40000);
        pollardRhoTest(BigInteger.valueOf(4294967297L));
        pollardRhoTest(BigInteger.valueOf(8616460799L));
        pollardRhoTest(BigInteger.valueOf(10030423103L));
        pollardRhoTest(BigInteger.valueOf(195545750400L));
        pollardRhoTest(NumberTheory.power(BigInteger.TWO, 67).subtract(BigInteger.ONE), 100000000L);
    }

    private static void factorTest(long n)
    {
        PrimeFactors primeFactors = new PrimeFactors(n);
        System.out.println("factor(" + n + ") = " + primeFactors);
    }

    private static void factorTestcases()
    {
        factorTest(-6);
        factorTest(-1);
        factorTest(0);
        factorTest(1);
        factorTest(2);
        factorTest(480);
        factorTest(51948);  // [Nathanson] Section 1.4, exercise 1
        factorTest(-126619);
        factorTest(991*997);
        factorTest(999983);
        factorTest(1000000);
        factorTest(1048576);
        factorTest(1729);

        // Example from W. S. Jevons, 1874 cited in [BachShallit]:
        factorTest(8616460799L);

        factorTest(195545750400L);

        // Project Euler, Problem 3
        factorTest(600851475143L);

        factorTest(144403552893600L);

        // Two twin primes as factors:
        factorTest(10030423103L);

        // Square of prime beyond internal prime table
        factorTest(1000003L * 1000003L);

        // Compare [Scheid] page 28, challenge from a letter by Mersenne to Fermat
        factorTest(100895598169L);

        for (int offset = -1; offset <= 1; offset += 2) {
            System.out.println();
            long p2 = 1;
            int exponent = 0;
            for (int i = 1; i <= MaxExponent2; ++i) {
                p2 <<= 1;
                ++exponent;
                long n = offset == 1 ? p2+1 : p2-1;
                PrimeFactors factors = new PrimeFactors(n);
                System.out.println("factor(2^" + exponent + (offset == 1 ? "+" : "") + offset + ") = " + factors);
            }
        }

        for (int offset = -1; offset <= 1; offset += 2) {
            System.out.println();
            long p10 = 1;
            int exponent = 0;
            for (int i = 1; i <= MaxExponent10; ++i) {
                p10 *= 10;
                ++exponent;
                long n = offset == 1 ? p10+1 : p10-1;
                PrimeFactors factors = new PrimeFactors(n);
                System.out.println("factor(10^" + exponent + (offset == 1 ? "+" : "") + offset + ") = " + factors);
            }
        }
    }

    private static void divisorTest(long n)
    {
        System.out.print("Divisors of " + n + " are: ");
        printArray(NumberTheory.divisors(n));
        System.out.print("Small divisors of " + n + " are: ");
        printArray(NumberTheory.smallDivisors(n));
    }

    private static void divisorTestcases()
    {
        divisorTest(1);
        divisorTest(2);
        divisorTest(3);
        divisorTest(4);
        divisorTest(30);
        divisorTest(48);
        divisorTest(81);
        divisorTest(120);
        divisorTest(997);
        divisorTest(1024);
        divisorTest(2520);
        divisorTest(2000000);
        divisorTest(195545750400L);
        divisorTest(-210);
    }

    private static void radicalTestcases()
    {
        System.out.println("rad(15) = " + NumberTheory.radical(15));
        System.out.println("rad(30) = " + NumberTheory.radical(30));
        System.out.println("rad(-45) = " + NumberTheory.radical(-45));
        System.out.println("rad(72) = " + NumberTheory.radical(72));
        System.out.println("rad(225) = " + NumberTheory.radical(225));
        System.out.println("rad(81) = " + NumberTheory.radical(81));
        System.out.println("rad(-1) = " + NumberTheory.radical(-1));
        System.out.println("rad(195545750400) = " + NumberTheory.radical(195545750400L));
        try {
            NumberTheory.radical(0);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Got expected exception for rad(0)");
        }
    }

    private static void isSquareFreeTestcases()
    {
        for (int i : new int[] {0, 1, 2, 7, 8, 9, 10, 15, 16, 79, 80, 81}) {
            System.out.println("isSquareFree(" + i + ") = " + NumberTheory.isSquareFree(i));
        }
    }

    private static void primitiveRootPrimeTest(long n)
    {
        System.out.println("First primitive root mod " + n + " is: " + NumberTheory.primitiveRootPrime(n));
    }

    private static void primitiveRootTest(long n)
    {
        long root = NumberTheory.primitiveRoot(n);
        if (root > 0) {
            System.out.println("First primitive root mod " + n + " is: " + root);
        }
    }

    private static void primitiveRootTestcases()
    {
        System.out.println("Primitive roots modulo a prime:");
        System.out.println();
        for (int i = 1; i < 10; ++i) {
            primitiveRootPrimeTest(NumberTheory.primes[i]);
        }
        primitiveRootPrimeTest(191);
        try {
            NumberTheory.primitiveRootPrime(2);
        }
        catch (RuntimeException re) {
            System.out.println("Got expected exception for primitiveRootPrime(2)");
        }

        System.out.println();
        System.out.println("10 is a primitive root of these prime numbers:");
        int output_count = 0;
        for (int i = 0; i < NumberTheory.primes.length; ++i) {
            int prime = NumberTheory.primes[i];
            if (prime == 2 || prime == 5)
                continue;
            if (NumberTheory.isPrimitiveRoot(10, prime)) {
                System.out.printf("%8d", prime);
                ++output_count;
                if (output_count % 10 == 0)
                    System.out.println();
                if (output_count >= 200)
                    break;
            }
        }
        System.out.println();
        System.out.println("Primitive roots modulo a general integer:");
        System.out.println();
        for (int i = 1; i <= 200; ++i) {
            primitiveRootTest(i);
        }
        System.out.println();
        for (int i = 1; i <= 200; ++i) {
            long[] roots = NumberTheory.primitiveRoots(i);
            if (roots.length > 0) {
                System.out.printf("%4d   %4d    ", i, roots.length);
                printArray(roots);
            }
        }
        long root_m1 = NumberTheory.primitiveRoot(-1);
        long[] roots_m1 = NumberTheory.primitiveRoots(-1);
        if (root_m1 > 0 || roots_m1.length > 0) {
            System.out.println("Error with primitive root calculation for -1");
        }
    }

    private static void isCarmichaelNumberTestcases()
    {
        for (int i : new int[] {0, 1, 2, 7, 8, 9, 10, 15, 16, 79, 80, 81, 231, 315, 560, 561, 562, 1105, 1729, 7429}) {
            System.out.println("isCarmichaelNumber(" + i + ") = " + NumberTheory.isCarmichaelNumber(i));
        }
    }

    private static void testEratosthenes(int limit)
    {
        boolean[] era = NumberTheory.eratosthenes(limit);
        int lfCount = 0;
        for (int i = 1; i <= limit; ++i) {
            if (era[i]) {
                System.out.printf("%6d", i);
                ++lfCount;
                if (lfCount == 10) {
                    System.out.println();
                    lfCount = 0;
                }
            }
        }
        System.out.println();
    }

    private static int printPrimeTable(int limit)
    {
        final int width = (int)(Math.ceil(Math.log10(limit))) + 1;
        final String fmt = "%" + width + "d";
        int count = 0;
        for (int i = 2; i <= limit; ++i) {
            if (NumberTheory.isPrime(i)) {
                if (count % 10 == 0)
                    System.out.println();
                ++count;
                System.out.printf(fmt, i);
            }
        }
        System.out.println();
        return count;
    }

    public static void findPerfectNumbers()
    {
        int count = 0;
        for (int i = 0; i < NumberTheory.primes.length; ++i) {
            int p = NumberTheory.primes[i];
            if (p >= 32)
                break;
            long factor1 = NumberTheory.power(2L, p) - 1L;
            if (NumberTheory.isPrime(factor1)) {
                long factor2 = NumberTheory.power(2L, p-1);
                System.out.printf("Perfect number %d:  %2d  %10d  %20d%n", ++count, p, factor1, factor1 * factor2);
            }
        }
    }

    public static void findAmicableNumbers(int max)
    {
        System.out.println("Amicable pairs and their sums");
        System.out.println();
        List<Pair<Integer, Integer>> divisorSumTable = new Vector<>(max);
        for (int i = 1; i <= max; ++i) {
            divisorSumTable.add(new Pair<Integer, Integer>(i, (int)NumberTheory.sigma1(i)));
        }
        Collections.sort(divisorSumTable,
                         (e1, e2) -> e1.getSecond() == e2.getSecond()
                             ? e1.getFirst()  - e2.getFirst()
                             : e1.getSecond() - e2.getSecond());
        int lowerIndex = 0;
        while (lowerIndex < max) {
            int sum = divisorSumTable.get(lowerIndex).getSecond();
            int upperIndex = lowerIndex;
            while (upperIndex+1 < max && sum == divisorSumTable.get(upperIndex+1).getSecond()) {
                ++upperIndex;
            }

            if (lowerIndex < upperIndex) {
                for (int i = lowerIndex; i <= upperIndex-1; ++i) {
                    for (int j = i+1; j <= upperIndex; ++j) {
                        if (divisorSumTable.get(i).getFirst() + divisorSumTable.get(j).getFirst() == sum) {
                            int n = divisorSumTable.get(i).getFirst();
                            int m = divisorSumTable.get(j).getFirst();
                            if (n > m) {
                                int t = n; n = m; m = t;
                            }
                            System.out.printf("%10d%10d%12d%n", n, m, sum);
                        }
                    }
                }
            }

            lowerIndex = upperIndex + 1;
        }
    }

    public static void nSquaresForTestcases()
    {
        for (int n = -2; n <= 32; ++n) {
            int nSquares = NumberTheory.nSquaresFor(n);
            if (nSquares == -1)
                continue;
            System.out.println("" + n + " is sum of " + nSquares + (nSquares == 1 ? " square." : " squares."));
        }

        // Compare [Rosen] Section 4.8.5 Fact 5
        final int nMax = 100;
        System.out.printf("%nPositive integers less than %d that are not the sum of three squares are%n", nMax);
        for (int n = 1; n <= nMax; ++n) {
            if (NumberTheory.nSquaresFor(n) == 4)
                System.out.printf(" %d", n);
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        NumberTheory.randomSource = new Random(27);

        bitlengthTestcases();

        System.out.println();
        digitSumTestcases();

        System.out.println();
        oddEvenTestcases();

        System.out.println();
        powTestcases();

        System.out.println();
        squareTestTestcases();

        System.out.println();
        isSquareTestcases();

        System.out.println();
        powerModTestcases();

        System.out.println();
        hyperExpModTestcases();

        System.out.println();
        primeInFactorialTestcases();

        System.out.println();
        gcdTestcases();

        System.out.println();
        lcmTestcases();

        System.out.println();
        extendedEuclidTestcases();

        System.out.println();
        modInverseTestcases();

        System.out.println();
        linearCongruenceTestcases();

        System.out.println();
        pellEquationTestcases();

        System.out.println();
        intSqrtTestcases();

        System.out.println();
        sqrtToContinuedFractionTestcases();

        System.out.println();
        jacobiSymbolTestcases();

        System.out.println();
        MillerRabinTestcases();

        System.out.println();
        pollardRhoTestcases();

        System.out.println();
        factorTestcases();

        System.out.println();
        divisorTestcases();

        System.out.println();
        radicalTestcases();

        System.out.println();
        isSquareFreeTestcases();

        System.out.println();
        ordTestcases();

        System.out.println();
        primitiveRootTestcases();

        System.out.println();
        isCarmichaelNumberTestcases();

        System.out.println();
        System.out.println("sigma0(18480) = " + NumberTheory.sigma0(18480));
        System.out.println("sigma1(18480) = " + NumberTheory.sigma1(18480));
        System.out.println("sigma0(195545750400) = " + NumberTheory.sigma0(195545750400L));
        System.out.println("sigma1(195545750400) = " + NumberTheory.sigma1(195545750400L));

        System.out.println();
        for (long n : new long[] {10, 6993, 18480, 18483, 999983, 1000000, 1048576}) {
            System.out.println("phi(" + n + ") = " + NumberTheory.phi(n));
        }

        System.out.println();
        System.out.println("Is perfect number 5: " + NumberTheory.isPerfectNumber(5L));
        System.out.println("Is perfect number 6: " + NumberTheory.isPerfectNumber(6L));
        System.out.println("Is perfect number 7: " + NumberTheory.isPerfectNumber(7L));
        System.out.println("Is abundant number 945: " + NumberTheory.isAbundantNumber(945L));
        System.out.println("Is abundant number 5391411025: " + NumberTheory.isAbundantNumber(5391411025L));
        System.out.printf("Is amicable pair  %d %d: %s%n", 220, 284, NumberTheory.isAmicablePair(220, 284));

        System.out.println();
        testEratosthenes(10000);

        System.out.println();
        for (int i = -2; i <= 5; ++i) {
            System.out.printf("Is a prime %2d: %s%n", i, NumberTheory.isPrime(i));
        }
        isPrimeTest(2147483647L);
        isPrimeTest(1000000000039L);
        isPrimeTest(1000003L * 1000003L);

        int count = printPrimeTable(100000);
        System.out.println();
        System.out.println("(total " + count + " numbers in the table)");

        System.out.println();
        findPerfectNumbers();
        System.out.println();
        findAmicableNumbers(AmicableNumbersLimit);

        System.out.println();
        System.out.println("n, sigma0(n), sigma1(n), phi(n), moebius(n), omega(n), factor count and prime factors");
        System.out.println();
        for (int i = 1; i <= 1000; ++i) {
            PrimeFactors f = new PrimeFactors(i);
            System.out.printf("%5d%4d%6d%6d%4d%5d%3d   %s%n",
                    i,
                    NumberTheory.sigma0(f),
                    NumberTheory.sigma1(f),
                    NumberTheory.phi(f),
                    NumberTheory.moebius(f),
                    NumberTheory.omega(f),
                    NumberTheory.factorCount(f),
                    f);
        }

        System.out.printf("%n%nFirst number with given number of divisors:%n%n");
        for (int i = 1; i <= 36; ++i) {
            long firstNumber = NumberTheory.firstNumberWithNDivisors(i);
            PrimeFactors factors = new PrimeFactors(firstNumber);
            System.out.printf("%3d%16d = %s%n", i, firstNumber, factors);
        }

        System.out.println();
        nSquaresForTestcases();
    }
}
