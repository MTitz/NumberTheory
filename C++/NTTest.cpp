// Literature
//  [Andrews] George E. Andrews, "Number Theory", Dover, 1994
//  [BachShallit] Bach, Shallit, "Algorithmic Number Theory", MIT-Press, 1996
//  [Clessa] J. J. Clessa, "Math and Logic Puzzles for PC Enthusiasts", Dover, 1996
//  [Giblin] Peter Giblin, "Primes and Programming", Cambridge University Press, 1993
//  [Nathanson] Melvyn B. Nathanson, "Elementary Methods in Number Theory", Springer, 2000
//  [Scheid] Harald Scheid, "Zahlentheorie", 3. Auflage, Spektrum Akademischer Verlag, 2003
//  [Yan] Song Y. Yan, "Number Theory for Computing", 2nd edition, Springer, 1998


#include <algorithm>
#include <cmath>
#include <exception>
#include <iomanip>
#include <iostream>
#include <utility>
#include <vector>

#include "numbertheory.h"
#include "showSequence.h"

using std::cout;
using std::endl;
using std::setw;

using namespace math;

// Maximal exponents for factorization of a^n-1, a^n+1 for a = 2 and a = 10
const int MaxExponent2  = 63;
const int MaxExponent10 = 12;

const std::size_t Amicable_Numbers_Limit = 2500000U;

template<typename T> void bitlengthTest(T n)
{
    cout << "bitlength(" << n << ") = " << bitlength(n) << endl;
}

static void bitlengthTestcases()
{
    bitlengthTest(1U);
    bitlengthTest(2U);
    bitlengthTest(3U);
    bitlengthTest(4U);
    bitlengthTest(1023);
    bitlengthTest(1024);
    bitlengthTest(1025);
    bitlengthTest(0);
    bitlengthTest(-1023);
    bitlengthTest(-1024);
    bitlengthTest((1<<24)-1);
    bitlengthTest(1<<24);
    bitlengthTest(9223372036854775807ULL);
    bitlengthTest(9223372036854775808ULL);
    bitlengthTest(18446744073709551615ULL);
}

template<typename T> void sum_of_digits_test(T n)
{
    cout << "sum_of_digits(" << n << ") = " << sum_of_digits(n) << endl;
}

template<typename T> void sum_of_digits_test(T n, int base)
{
    cout << "base " << base  << " sum_of_digits(" << n << ") = " << sum_of_digits(n, base) << endl;
}

static void sum_of_digits_testcases()
{
    sum_of_digits_test(0);
    sum_of_digits_test(1);
    sum_of_digits_test(10);
    sum_of_digits_test(12);
    sum_of_digits_test(144);
    sum_of_digits_test(1024);
    sum_of_digits_test(1024, 2);
    sum_of_digits_test(1024, 4);
    sum_of_digits_test(1024, 16);
    sum_of_digits_test(999999999);
    sum_of_digits_test(999999999, 2);
    sum_of_digits_test(123456789123456789ULL, 10);
    sum_of_digits_test(123456789123456789ULL,  2);
}

static void oddEvenTestcases()
{
    for (int i : {-2, -1, 0, 1, 2, 15, 16, 79, 80, 81, 82, 83, 84}) {
        cout << i << " is ";
        if (odd(i))
            cout << "odd";
        if (even(i))
            cout << "even";
        cout << endl;
    }
}

static void powTestcases()
{
    for (unsigned int i = 0; i <= 4; ++i) {
        cout << "0^" << i << " = " << math::pow(0, i) << endl;
    }
    cout << endl;
    for (unsigned int i = 0; i <= 4; ++i) {
        cout << "1^" << i << " = " << math::pow(1, i) << endl;
    }
    cout << endl;
    for (unsigned int i = 0; i <= 24; ++i) {
        cout << "2^" << i << " = " << math::pow(2, i) << endl;
    }
    cout << endl;
    for (unsigned int i = 0; i <= 6; ++i) {
        cout << "5^" << i << " = " << math::pow(5, i) << endl;
    }
}

template<typename T> void squareTestTest(const T& n)
{
    cout << "squareTest(" << n << ") = " << squareTest(n) << endl;
}

static void squareTestTestcases()
{
    for (int i = -4; i <= 100; ++i) {
        squareTestTest(i);
    }
    squareTestTest(193);
    squareTestTest(585);
}

template<typename T> void isSquareTest(const T& n)
{
    cout << "  Is " << n << " a square: " << isSquare(n) << endl;
}

static void isSquareTestcases()
{
    cout << std::boolalpha;
    cout << "Signed case:" << endl;
    for (int i = -4; i <= 9; ++i) {
        isSquareTest(i);
    }
    isSquareTest(      193L);
    isSquareTest(      585L);
    isSquareTest( 99999999L);
    isSquareTest(100000000L);
    isSquareTest(100000001L);

    cout << "Unsigned case:" << endl;
    for (unsigned long ui = 0UL; ui <= 9UL; ++ui) {
        isSquareTest(ui);
    }
}

static void isPowerOfTestcases()
{
    cout << std::boolalpha;
    cout << "Is 120 a power of  5: " << isPowerOf(120,  5) << endl;
    cout << "Is 125 a power of  5: " << isPowerOf(125,  5) << endl;
    cout << "Is 120 a power of 12: " << isPowerOf(120, 12) << endl;
    cout << "Is 144 a power of 12: " << isPowerOf(144, 12) << endl;
    cout << "Is 125 a power of -5: " << isPowerOf(125, -5) << endl;
    cout << "Is   0 a power of  0: " << isPowerOf(  0,  0) << endl;
    cout << "Is   0 a power of  1: " << isPowerOf(  0,  1) << endl;
    cout << "Is   1 a power of  0: " << isPowerOf(  1,  0) << endl;
    cout << "Is   1 a power of  1: " << isPowerOf(  1,  1) << endl;
    cout << "Is   1 a power of -1: " << isPowerOf(  1, -1) << endl;
    cout << "Is  -1 a power of  1: " << isPowerOf( -1,  1) << endl;
    cout << "Is  -1 a power of -1: " << isPowerOf( -1, -1) << endl;
    cout << "Is   0 a power of  3: " << isPowerOf(  0,  3) << endl;
}

static void powerModTestcases()
{
    cout << "powerMod(2,   10, 10000000) = " << powerMod(2ULL,   10ULL, 1000000ULL) << endl;
    cout << "powerMod(2,  100, 10000000) = " << powerMod(2ULL,  100ULL, 1000000ULL) << endl;
    cout << "powerMod(2, 1000, 10000000) = " << powerMod(2ULL, 1000ULL, 1000000ULL) << endl;
    cout << endl;
    cout << "powerMod(2,   10, 10000000) = " << powerMod<int, long long int>(2,   10, 1000000) << endl;
    cout << "powerMod(2,  100, 10000000) = " << powerMod<int, long long int>(2,  100, 1000000) << endl;
    cout << "powerMod(2, 1000, 10000000) = " << powerMod<int, long long int>(2, 1000, 1000000) << endl;
    cout << endl;

    // [Giblin], chapter 4.2
    cout << "powerMod(7, 50, 11) = " << powerMod(7, 50, 11) << endl;
    // [Nathanson], chapter 2.5, exercise 1 (page 71)
    cout << "powerMod(3, 512, 1024) = " << powerMod(3, 512, 1024) << endl;
    // [Nathanson], chapter 2.5, exercise 2 (page 71)
    cout << "powerMod(7, 51, 144) = " << powerMod(7, 51, 144) << endl;
    // [Nathanson], chapter 2.5, exercise 3 (page 71)
    cout << "powerMod(2, 10^8, 31) = " << powerMod(2UL, 100000000UL, 31UL) << endl;
    // [Nathanson], chapter 2.6, example on page 74
    cout << "powerMod(2, 850, 851) = " << powerMod(2, 850, 851) << endl;
    // [Nathanson], chapter 2.6, example on page 75
    cout << "powerMod(7, 340, 341) = " << powerMod(7, 340, 341) << endl;
}

static void hyperExpModTestcases()
{
    cout << "hyperExpMod(3, 2, 10^8) = " << hyperExpMod(3, 2, 100000000) << endl;
    cout << "hyperExpMod(9, 3, 10^8) = " << hyperExpMod(9, 3, 100000000) << endl;
    cout << "hyperExpMod(1777, 1855, 10^8) = " << hyperExpMod(1777, 1855, 100000000) << endl;
}

template<typename T> void primeInFactorialTest(const T& n, const T& p)
{
    cout << "In " << n << "! has the prime factor " << p
         << " the exponent " << primefactor_in_factorial(n, p) << endl;
}

static void primeInFactorialTestcases()
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
    cout << endl;
    try {
        (void)primefactor_in_factorial(-1, 2);
    }
    catch (std::invalid_argument&) {
        cout << "Got expected exception for primeInFactorialTest(-1, 2)" << endl;
    }
    try {
        (void)primefactor_in_factorial(4, 1);
    }
    catch (std::invalid_argument&) {
        cout << "Got expected exception for primeInFactorialTest(4, 1)" << endl;
    }
    cout << endl;
    for (int n = 0; n <= 30; ++n) {
        cout << setw(3) << n << "! ends with " << setw(1) << primefactor_in_factorial(n, 5) << " zeros." << endl;
    }
    for (int n : {40, 50, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000}) {
        cout << " " << n << "! ends with " << primefactor_in_factorial(n, 5) << " zeros." << endl;
    }
}

template<typename T> void lcmTest(const T& a, const T& b)
{
    cout << "lcm(" << a << ", " << b << ") = " << lcm(a, b) << endl;
}

template<typename T> void lcmTest(const T& a, const T& b, const T& c)
{
    long param[] = {a, b, c};
    cout << "lcm(" << a << ", " << b << ", " << c << ") = " << lcm(3, param) << endl;
}

static void lcmTestcases()
{
    lcmTest(0, 0);
    lcmTest(12, 0);
    lcmTest(0, 20);
    lcmTest(12, 20);
    lcmTest(24U, 36U);
    lcmTest(10, 12, 16);
}

template<typename T> void extendedEuclidTest(const T& a, const T& b)
{
    auto r = extendedEuclid(a, b);
    cout << "extendedEuclid(" << a << ", " << b << "): d = " << r.d <<  ", u = " << r.u << ", v = " << r.v << endl;
}

static void extendedEuclidTestcases()
{
    extendedEuclidTest(12,  0);
    extendedEuclidTest(12, 20);
    extendedEuclidTest(12, 21);
    extendedEuclidTest(574, 252); // [Nathanson], page 19
}

template<typename T> void modInverseTable(T m)
{
    cout << "Modulus " << m << ':' << endl;
    for (T i = 1; i < m; ++i) {
        try {
            int inv = modInverse(i, m);
            cout << '\t' << i << '\t' << inv << endl;
        }
        catch (std::exception&) {
        }
    }
}

template<typename T> void modInverseTest(T a, T m)
{
    T inv = modInverse(a, m);
    cout << "The inverse of " << a << " mod " << m << " is " << inv << endl;
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

    cout << endl;
    // [Andrews] section 5-1, exercise 3 (page 61)
    modInverseTest(2, 5);
    modInverseTest(7, 9);
    modInverseTest(12, 17);

    // [Yan] example 1.6.14 (1) (pages 121, 122)
    modInverseTest(154, 801);
}


const SimpleLinearCongruence<int> exercise1[] =
   { SimpleLinearCongruence<int>(3, 5),
     SimpleLinearCongruence<int>(2, 6)};

// Sun-Tsu (cited in [Scheid])
const SimpleLinearCongruence<int> exercise2[] =
   { SimpleLinearCongruence<int>(2, 3),
     SimpleLinearCongruence<int>(3, 5),
     SimpleLinearCongruence<int>(2, 7)};

// [Yan] exercise 1.6.1 (page 132)
const SimpleLinearCongruence<int> exercise3[] =
   { SimpleLinearCongruence<int>(2, 7),
     SimpleLinearCongruence<int>(7, 9),
     SimpleLinearCongruence<int>(3, 4)};

const LinearCongruence<int> exercise4[] =
   { LinearCongruence<int>(3,  1,  5),
     LinearCongruence<int>(4,  6, 14),
     LinearCongruence<int>(5, 11,  3)};

const LinearCongruence<int> exercise5[] =
   { LinearCongruence<int>(4,  2,  6),
     LinearCongruence<int>(3,  5,  7),
     LinearCongruence<int>(2,  4, 11)};

// [Scheid] Kapitel 4.1 (Seite 199)
const SimpleLinearCongruence<int> exercise6[] =
   { SimpleLinearCongruence<int>(-1, 3),
     SimpleLinearCongruence<int>(-1, 4),
     SimpleLinearCongruence<int>(-1, 5),
     SimpleLinearCongruence<int>(-1, 6)};

// [Scheid] Kapitel 4.1 (Seite 199)
const LinearCongruence<int> exercise6b[] =
   { LinearCongruence<int>(1, -1, 3),
     LinearCongruence<int>(1, -1, 4),
     LinearCongruence<int>(1, -1, 5),
     LinearCongruence<int>(1, -1, 6)};

const SimpleLinearCongruence<int> exercise7[] =
   { SimpleLinearCongruence<int>(1, 2),
     SimpleLinearCongruence<int>(1, 3),
     SimpleLinearCongruence<int>(1, 4),
     SimpleLinearCongruence<int>(1, 5),
     SimpleLinearCongruence<int>(1, 6),
     SimpleLinearCongruence<int>(0, 7)};

// from [Scheid] Kapitel IV.12, Aufgabe 6b (Seite 264)
const LinearCongruence<int> exercise7a[] =
   { LinearCongruence<int>(5, 2, 12),
     LinearCongruence<int>(7, 0, 15)};

const LinearCongruence<int> exercise7b[] =
   { LinearCongruence<int>(5, 2, 12),
     LinearCongruence<int>(7, 1, 15)};

const LinearCongruence<int> exercise7c[] =
   { LinearCongruence<int>(5, 2, 12),
     LinearCongruence<int>(7, 2, 15)};

const SimpleLinearCongruence<int> exercise8[] =
   { SimpleLinearCongruence<int>(1, 2),
     SimpleLinearCongruence<int>(2, 4)};

const SimpleLinearCongruence<int> exercise9a[] =
   { SimpleLinearCongruence<int>(1,  6),
     SimpleLinearCongruence<int>(1, 10),
     SimpleLinearCongruence<int>(1, 15)};

const SimpleLinearCongruence<int> exercise9b[] =
   { SimpleLinearCongruence<int>(1,  6),
     SimpleLinearCongruence<int>(1, 10),
     SimpleLinearCongruence<int>(6, 15)};

const SimpleLinearCongruence<int> exercise9c[] =
   { SimpleLinearCongruence<int>( 1,  6),
     SimpleLinearCongruence<int>( 1, 10),
     SimpleLinearCongruence<int>(11, 15)};

const SimpleLinearCongruence<long long int> exercise10[] =
   { SimpleLinearCongruence<long long int>(1,  2),
     SimpleLinearCongruence<long long int>(1,  3),
     SimpleLinearCongruence<long long int>(1,  4),
     SimpleLinearCongruence<long long int>(1,  5),
     SimpleLinearCongruence<long long int>(1,  6),
     SimpleLinearCongruence<long long int>(1,  7),
     SimpleLinearCongruence<long long int>(1,  8),
     SimpleLinearCongruence<long long int>(1,  9),
     SimpleLinearCongruence<long long int>(1, 10),
     SimpleLinearCongruence<long long int>(1, 11),
     SimpleLinearCongruence<long long int>(1, 12),
     SimpleLinearCongruence<long long int>(1, 13),
     SimpleLinearCongruence<long long int>(1, 14),
     SimpleLinearCongruence<long long int>(1, 15),
     SimpleLinearCongruence<long long int>(1, 16),
     SimpleLinearCongruence<long long int>(1, 17),
     SimpleLinearCongruence<long long int>(1, 18),
     SimpleLinearCongruence<long long int>(1, 19),
     SimpleLinearCongruence<long long int>(1, 20),
     SimpleLinearCongruence<long long int>(1, 21),
     SimpleLinearCongruence<long long int>(1, 22),
     SimpleLinearCongruence<long long int>(1, 23),
     SimpleLinearCongruence<long long int>(1, 24),
     SimpleLinearCongruence<long long int>(1, 25),
     SimpleLinearCongruence<long long int>(1, 26),
     SimpleLinearCongruence<long long int>(1, 27),
     SimpleLinearCongruence<long long int>(1, 28),
     SimpleLinearCongruence<long long int>(1, 29),
     SimpleLinearCongruence<long long int>(1, 30),
     SimpleLinearCongruence<long long int>(0, 31)};

const SimpleLinearCongruence<long long int> exercise11[] =
   { SimpleLinearCongruence<long long int>(0,  2),
     SimpleLinearCongruence<long long int>(0,  3),
     SimpleLinearCongruence<long long int>(0,  4),
     SimpleLinearCongruence<long long int>(0,  5),
     SimpleLinearCongruence<long long int>(0,  6),
     SimpleLinearCongruence<long long int>(0,  7),
     SimpleLinearCongruence<long long int>(0,  8),
     SimpleLinearCongruence<long long int>(0,  9),
     SimpleLinearCongruence<long long int>(0, 10),
     SimpleLinearCongruence<long long int>(0, 11),
     SimpleLinearCongruence<long long int>(0, 12),
     SimpleLinearCongruence<long long int>(0, 13),
     SimpleLinearCongruence<long long int>(0, 14),
     SimpleLinearCongruence<long long int>(0, 15),
     SimpleLinearCongruence<long long int>(0, 16),
     SimpleLinearCongruence<long long int>(0, 17),
     SimpleLinearCongruence<long long int>(0, 18),
     SimpleLinearCongruence<long long int>(0, 19),
     SimpleLinearCongruence<long long int>(0, 20),
     SimpleLinearCongruence<long long int>(0, 21),
     SimpleLinearCongruence<long long int>(0, 22),
     SimpleLinearCongruence<long long int>(0, 23),
     SimpleLinearCongruence<long long int>(0, 24),
     SimpleLinearCongruence<long long int>(0, 25),
     SimpleLinearCongruence<long long int>(0, 26),
     SimpleLinearCongruence<long long int>(0, 27),
     SimpleLinearCongruence<long long int>(0, 28),
     SimpleLinearCongruence<long long int>(0, 29),
     SimpleLinearCongruence<long long int>(0, 30),
     SimpleLinearCongruence<long long int>(1, 31)};

// [Clessa], Micropuzzle 39
const SimpleLinearCongruence<long> micropuzzle39[] =
   { SimpleLinearCongruence<long>( 3,  4),
     SimpleLinearCongruence<long>( 1,  5),
     SimpleLinearCongruence<long>( 2,  7),
     SimpleLinearCongruence<long>( 2, 11),
     SimpleLinearCongruence<long>(12, 17)};

const LinearCongruence<int> unsolvable_test[] =
   { LinearCongruence<int>(0, 1, 2),
     LinearCongruence<int>(1, 1, 1)};

template<typename T> void linearCongruence(T a, T b, T m)
{
    cout << "The congruence " << a << " * x = " << b << " mod " << m;
    LinearCongruence<T> congruence(a, b, m);
    SimpleLinearCongruence<T> solution = solve(congruence);
    if (solution.m() == 0)
        cout << "  has no solution" << endl;
    else
        cout << "  has the solution  x = " << solution.b()
             << " mod " << solution.m() << endl;
}

template<typename T>
void simpleLinearCongruence(const SimpleLinearCongruence<T> congruence[], int n)
{
    cout << endl;
    cout << "The problem" << endl;
    for (int i = 0; i < n; ++i) {
        cout << "x = " << congruence[i].b()
             << " mod " << congruence[i].m() << endl;
    }
    SimpleLinearCongruence<T> solution = solve(congruence, n);
    if (solution.m() == 0)
        cout << "  has no solution" << endl;
    else
        cout << "  has the solution  x = " << solution.b()
             << " mod " << solution.m() << endl;
}

template<typename T>
void linearCongruence(const LinearCongruence<T> congruence[], int n)
{
    cout << endl;
    cout << "The problem" << endl;
    for (int i = 0; i < n; ++i) {
        cout << congruence[i].a() << " * x = " << congruence[i].b()
             << " mod " << congruence[i].m() << endl;
    }
    SimpleLinearCongruence<T> solution = solve(congruence, n);
    if (solution.m() == 0)
        cout << "  has no solution" << endl;
    else
        cout << "  has the solution  x = " << solution.b()
             << " mod " << solution.m() << endl;
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
    linearCongruence(3L, 11L, 2275L);  // [Andrews], section 5-3, example 5-6
    linearCongruence(1193L, 367L, 31500L);  // [Scheid], Kapitel IV.1, Beispiel 3 (Seite 98)
    linearCongruence(154L, 11L, 803L);
    linearCongruence(154L, 22L, 803L);  // check [Yan] example 1.6.15 (page 124)
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

    simpleLinearCongruence(exercise1, 2);
    simpleLinearCongruence(exercise2, 3);
    simpleLinearCongruence(exercise3, 3);
    linearCongruence(exercise4, 3);
    linearCongruence(exercise5, 3);
    simpleLinearCongruence(exercise6, 4);
    linearCongruence(exercise6b, 4);
    simpleLinearCongruence(exercise7, 6);
    linearCongruence(exercise7a, 2);
    linearCongruence(exercise7b, 2);
    linearCongruence(exercise7c, 2);
    simpleLinearCongruence(exercise8, 2);
    simpleLinearCongruence(exercise9a, 3);
    simpleLinearCongruence(exercise9b, 3);
    simpleLinearCongruence(exercise9c, 3);
    simpleLinearCongruence(exercise10,
        sizeof(exercise10) / sizeof(SimpleLinearCongruence<long long int>));
    simpleLinearCongruence(exercise11,
        sizeof(exercise11) / sizeof(SimpleLinearCongruence<long long int>));
    simpleLinearCongruence(micropuzzle39,
        sizeof(micropuzzle39) / sizeof(SimpleLinearCongruence<long>));
    linearCongruence(unsolvable_test, 2);
}


static void pell4Tests()
{
    for (long long d : {11, 13, 17, 19, 61, 67, 101, 103, 107, 109, 211, 223}) {
        auto result = pell4(d);
        cout << setw(3) << d << ": (" << result[0] << ", " << result[1] << ", " << result[2] << ")" << endl;
    }
}

static void pellEquationTestcases()
{
    cout << "Testing Pell's equation routines" << endl << endl;
    pell4Tests();
    cout << endl;
    for (long long d = 2; d < 100; ++d) {
        if (isSquareFree(d)) {
            auto result = pell(d);
            cout << setw(3) << d << ": (" << result[0] << ", " << result[1] << ")" << endl;
        }
    }
    for (long long d : {101, 103, 107, 109, 211, 223}) {
        auto result = pell(d);
        cout << setw(3) << d << ": (" << result[0] << ", " << result[1] << ")" << endl;
    }
}


template<typename T> void intSqrtTest(T n)
{
    cout << "integerSqrt(" << n << ") = " << integerSqrt(n) << endl;
}

static void intSqrtTestcases()
{
    intSqrtTest(-1);
    intSqrtTest(0);
    intSqrtTest(1);
    intSqrtTest(15);
    intSqrtTest(16);
    intSqrtTest(17);
    intSqrtTest(123456789UL);
    intSqrtTest(4123456789UL);
    intSqrtTest(123456789123456789ULL);
}

template<typename T> void sqrt_to_cfrac_Test(const T& n)
{
    cout << "cfrac sequence of sqrt(" << n << "):  ";
    showSequence(sqrt_to_cfrac(n));
}

static void sqrt_to_cfrac_Testcases()
{
    for (int i = 1; i < 100; ++i) {
        sqrt_to_cfrac_Test(i);
    }
    sqrt_to_cfrac_Test(114);  // Example from https://en.wikipedia.org/wiki/Periodic_continued_fraction
    sqrt_to_cfrac_Test(709);
    sqrt_to_cfrac_Test(710);
}

static void jacobiSymbolTestcases()
{
    for (int i = -2; i <= 3; ++i)
        cout << "jacobi(" << i << ", 0) = " << jacobi(i, 0) << endl;
    for (int i = 1; i <= 6; ++i)
        cout << "legendre(" << i  << ", 7) = " << jacobi(i,7) << endl;
    cout << endl;
    cout << "legendre(33, 83) = " << jacobi(33, 83) << endl;
    cout << "legendre(46, 997) = " << jacobi(46, 997) << endl;
    cout << "legendre(286, 563) = " << jacobi(286, 563) << endl;
    cout << "jacobi(1009, 2307) = " << jacobi(1009, 2307) << endl;
    cout << "jacobi(1009, -2307) = " << jacobi(1009, -2307) << endl;
    cout << "jacobi(-1009, -2307) = " << jacobi(-1009, -2307) << endl;
    cout << "jacobi(4, 4) = " << jacobi(4, 4) << endl;
    cout << "jacobi(127, 256) = " << jacobi(127, 256) << endl;
}

template<typename T> void isPrimeTest(T n)
{
    cout << "Is a prime " << n << ": " << isPrime(n) << endl;
}

template<typename T> void factorTest(T n)
{
    FactorizedNumber<T> f(n);
    cout << "factor(" << n << ") = " << f << endl;
}

static void factorTestcases()
{
    factorTest(-6);
    factorTest(-1);
    factorTest(0);
    factorTest(1);
    factorTest(2);
    factorTest(480);
    factorTest(51948);  // [Natanson] Section 1.4, exercise 1
    factorTest(-126619L);
    factorTest<long>(991*997);
    factorTest<long>(999983);
    factorTest<long>(1000000);
    factorTest<long>(1048576);
    factorTest<unsigned int>(0u);
    factorTest<unsigned int>(1729u);

    // Example from W. S. Jevons, 1874 cited in [BachShallit]:
    factorTest<long long>(8616460799LL);

    factorTest(195545750400L);

    // Project Euler, Problem 3
    factorTest(600851475143LL);

    // Testing signed and unsigned variant:
    factorTest<long long>(144403552893600LL);
    factorTest<unsigned long long>(144403552893600ULL);

    // Two twin primes as factors:
    factorTest<unsigned long long>(10030423103ULL);

    // Square of prime beyond internal prime table
    factorTest<unsigned long long>(1000003ULL * 1000003ULL);

    for (int offset = -1; offset <= 1; offset += 2) {
        cout << endl;
        unsigned long long P2 = 1ULL;
        unsigned int exponent = 0;
        for (int i = 1; i <= MaxExponent2; ++i) {
            P2 <<= 1;
            ++exponent;
            unsigned long long n = offset == 1 ? P2+1 : P2-1;
            PrimeFactors<unsigned long long>factors(n);
            cout << "factor(2^" << exponent << std::showpos << offset
                 << std::noshowpos << ") = " << factors << endl;
        }
    }

    for (int offset = -1; offset <= 1; offset += 2) {
        cout << endl;
        unsigned long long P10 = 1ULL;
        unsigned int exponent = 0;
        for (int i = 1; i <= MaxExponent10; ++i) {
            P10 *= 10ULL;
            ++exponent;
            unsigned long long n = offset == 1 ? P10+1 : P10-1;
            PrimeFactors<unsigned long long>factors(n);
            cout << "factor(10^" << exponent << std::showpos << offset
                 << std::noshowpos << ") = " << factors << endl;
        }
    }
}

template<typename T> void divisorTest(T n)
{
    cout << "Divisors of " << n << " are: ";
    showSequence(divisors(n));
    cout << "Small divisors of " << n << " are: ";
    showSequence(smallDivisors(n));
}

static void divisorTestcases()
{
    divisorTest(1);
    divisorTest(2);
    divisorTest(30);
    divisorTest(48U);
    divisorTest(81);
    divisorTest(120);
    divisorTest(997);
    divisorTest(1024);
    divisorTest(2520);
    divisorTest(195545750400ULL);
    divisorTest(-210);
}

static void radicalTestcases()
{
    cout << "rad(15) = " << radical(15) << endl;
    cout << "rad(30) = " << radical(30) << endl;
    cout << "rad(-45) = " << radical(-45) << endl;
    cout << "rad(72) = " << radical(72) << endl;
    cout << "rad(225) = " << radical(225) << endl;
    cout << "rad(81) = " << radical(81) << endl;
    cout << "rad(-1) = " << radical(-1) << endl;
    cout << "rad(195545750400) = " << radical(195545750400ULL) << endl;
    try {
        (void)radical(0);
    }
    catch (std::runtime_error&) {
        cout << "Got expected exception for rad(0)" << endl;
    }
}

static void isSquareFreeTestcases()
{
    for (int i : {0, 1, 2, 7, 8, 9, 10, 15, 16, 79, 80, 81}) {
        cout << "isSquareFree(" << i << ") = " << isSquareFree(i) << endl;
    }
}

template<typename T> void ordTest(const T& a, const T& n)
{
    cout << "ord_" << n << "(" << a << ") = " << ord(a, n) << endl;
}

static void ordTestcases()
{
    int n = 11;
    for (int i = 1; i <= 10; ++i) {
        ordTest(i, n);
    }
    ordTest(37U, 1000U);  // expect 100
    ordTest(54, 100001);  // expect 9090
    try {
        (void)ord(6, 12);
    }
    catch (std::runtime_error&) {
        cout << "Got expected exception for ord(6, 12)" << endl;
    }
}

template<typename T> void primitiveRootPrimeTest(const T& n)
{
    cout << "First primitive root mod " << n << " is: " << primitiveRootPrime(n) << endl;
}

template<typename T> void primitiveRootTest(const T& n)
{
    T root = primitiveRoot(n);
    if (root > 0) {
        cout << "First primitive root mod " << n << " is: " << root << endl;
    }
}

static void primitiveRootTestcases()
{
    cout << "Primitive roots modulo a prime:" << endl << endl;
    for (int i = 1; i < 10; ++i) {
        primitiveRootPrimeTest(primes[i]);
    }
    primitiveRootPrimeTest(191);
    try {
        (void)primitiveRootPrime(2);
    }
    catch (std::runtime_error&) {
        cout << "Got expected exception for primitiveRootPrime(2)" << endl;
    }

    cout << endl;
    cout << "10 is a primitive root of these prime numbers:" << endl;
    int output_count = 0;
    for (std::size_t i = 0; i < n_primes; ++i) {
        auto prime = primes[i];
        if (prime == 2 || prime == 5)
            continue;
        if (isPrimitiveRoot(10, prime)) {
            cout << setw(8) << prime;
            ++output_count;
            if (output_count % 10 == 0)
                cout << endl;
            if (output_count >= 200)
                break;
        }
    }
    cout << endl;
    cout << "Primitive roots modulo a general integer:" << endl << endl;
    for (int i = 1; i <= 200; ++i) {
        primitiveRootTest(i);
    }
    cout << endl;
    for (int i = 1; i <= 200; ++i) {
        auto roots = primitiveRoots(i);
        if (roots.size() > 0) {
            cout << setw(4) << i << "   " << setw(4) << roots.size() << "    ";
            showSequence(roots);
        }
    }
    auto root_m1 = primitiveRoot(-1);
    auto roots_m1 = primitiveRoots(-1);
    if (root_m1 > 0 || roots_m1.size() > 0) {
        cout << "Error with primitive root calculation for -1" << endl;
    }
}

static void isCarmichaelNumberTestcases()
{
    for (int i : {0, 1, 2, 7, 8, 9, 10, 15, 16, 79, 80, 81, 231, 315, 560, 561, 562, 1105, 1729, 7429}) {
        cout << "isCarmichaelNumber(" << i << ") = " << isCarmichaelNumber(i) << endl;
    }
}

static void testEratosthenes(std::size_t limit)
{
    std::vector<bool> era = eratosthenes(limit);
    int lf_count = 0;
    for (std::size_t i = 1; i <= limit; ++i) {
        if (era[i]) {
            cout << setw(6) << i;
            ++lf_count;
            if (lf_count == 10) {
                cout << endl;
                lf_count = 0;
            }
        }
    }
    cout << endl;
}

static int printPrimeTable(int limit)
{
    int count = 0;
    int width = static_cast<int>(std::ceil(std::log10(limit))) + 1;
    for (int i = 2; i <= limit; ++i) {
        if (isPrime(i)) {
            if (count % 10 == 0)
                cout << endl;
            ++count;
            cout << setw(width) << i;
        }
    }
    cout << endl;
    return count;
}

static void findPerfectNumbers()
{
    int count = 0;
    for (std::size_t i = 0; i < n_primes; ++i) {
        int p = primes[i];
        if (p >= 32)
            break;
        unsigned long long factor1 = pow(2ULL, (unsigned int)p) - 1ULL;
        if (isPrime(factor1)) {
            unsigned long long factor2 = pow(2ULL, static_cast<unsigned int>(p-1));
            cout << "Perfect number " << ++count << ":  " << setw(2) << p
                 << "  " << setw(10) << factor1
                 << "  " << setw(20) << factor1 * factor2 << endl;
        }
    }
}

/*** Currently unused, old version

void findAmicableNumbers_old(unsigned int max)
{
    unsigned int *aliquotSumTable = new unsigned int[max];
    for (unsigned int i = 1; i <= max; ++i) {
        aliquotSumTable[i] = aliquotSum(i);
    }
    for (unsigned int n = 1; n <= max; ++n) {
        for (unsigned int m = 1; m < n; ++m) {
            if (aliquotSumTable[m] == n && aliquotSumTable[n] == m) {
                cout << "Found amicable number pair: "
                     << setw(9) << m << setw(9) << n << endl;
            }
        }
    }
    delete[] aliquotSumTable;
}

***/

static void findAmicableNumbers(std::size_t max)
{
    using entry_type = std::size_t;
    using table_entry = typename std::pair<entry_type, entry_type>;
    cout << "Amicable pairs and their sums" << endl << endl;
    std::vector<table_entry> divisorSumTable(max);
    for (entry_type i = 1; i <= max; ++i) {
        divisorSumTable[i-1] = std::make_pair(i, sigma1(i));
    }
    std::sort(divisorSumTable.begin(), divisorSumTable.end(),
              [](const table_entry& t1, const table_entry& t2)
                  { return t1.second < t2.second || (t1.second ==  t2.second && t1.first < t2.first); });
    entry_type lowerIndex = 0;
    while (lowerIndex < max) {
        entry_type sum = divisorSumTable[lowerIndex].second;
        entry_type upperIndex = lowerIndex;
        while (upperIndex+1 < max && sum == divisorSumTable[upperIndex+1].second) {
            ++upperIndex;
        }

        if (lowerIndex < upperIndex) {
            for (entry_type i = lowerIndex; i <= upperIndex-1; ++i) {
                for (entry_type j = i+1; j <= upperIndex; ++j) {
                    if (divisorSumTable[i].first + divisorSumTable[j].first == sum) {
                        entry_type n = divisorSumTable[i].first;
                        entry_type m = divisorSumTable[j].first;
                        if (n > m) {
                            std::swap(n, m);
                        }
                        cout << setw(10) << n << setw(10) << m
                             << setw(12) << sum << endl;
                    }
                }
            }
        }

        lowerIndex = upperIndex + 1;
    }
}

int main(void)
{
    bitlengthTestcases();

    cout << endl;
    sum_of_digits_testcases();

    cout << endl;
    oddEvenTestcases();

    cout << endl;
    powTestcases();

    cout << endl;
    squareTestTestcases();

    cout << endl;
    isSquareTestcases();

    cout << endl;
    isPowerOfTestcases();

    cout << endl;
    powerModTestcases();

    cout << endl;
    hyperExpModTestcases();

    cout << endl;
    primeInFactorialTestcases();

    cout << endl;
    lcmTestcases();

    cout << endl;
    extendedEuclidTestcases();

    cout << endl;
    modInverseTestcases();

    cout << endl;
    linearCongruenceTestcases();

    cout << endl;
    pellEquationTestcases();

    cout << endl;
    intSqrtTestcases();

    cout << endl;
    sqrt_to_cfrac_Testcases();

    cout << endl;
    jacobiSymbolTestcases();

    cout << endl;
    factorTestcases();

    cout << endl;
    divisorTestcases();

    cout << endl;
    radicalTestcases();

    cout << endl;
    isSquareFreeTestcases();

    cout << endl;
    ordTestcases();

    cout << endl;
    primitiveRootTestcases();

    cout << endl;
    isCarmichaelNumberTestcases();

    cout << endl;
    cout << "sigma0(18480) = " << sigma0(18480) << endl;
    cout << "sigma1(18480) = " << sigma1(18480) << endl;
    cout << "sigma0(195545750400) = " << sigma0(195545750400LL) << endl;
    cout << "sigma1(195545750400) = " << sigma1(195545750400LL) << endl;

    cout << endl;
    cout << "phi(10) = " << phi(10) << endl;
    cout << "phi(6993) = " << phi(6993) << endl;  // [Natanson] Section 2.3, exercise 1
    cout << "phi(18480) = " << phi(18480) << endl;
    cout << "phi(18483) = " << phi(18483) << endl;
    cout << "phi(999983) = " << phi(999983) << endl;
    cout << "phi(1000000) = " << phi(1000000) << endl;
    cout << "phi(1048576) = " << phi(1048576) << endl;

    cout << endl << std::boolalpha;
    cout << "Is perfect number " << 5 << ": " << isPerfectNumber(5) << endl;
    cout << "Is perfect number " << 6 << ": " << isPerfectNumber(6) << endl;
    cout << "Is perfect number " << 7 << ": " << isPerfectNumber(7) << endl;
    cout << "Is abundant number " << 945 << ": "
         << isAbundantNumber(945) << endl;
    cout << "Is abundant number " << 5391411025 << ": "
         << isAbundantNumber(5391411025) << endl;
    cout << "Is amicable pair  " << 220 << " " << 284 << ": "
         << isAmicablePair(220, 284) << endl;

    cout << endl;
    testEratosthenes(10000);

    cout << endl << std::boolalpha;
    for (int i = -2; i <= 5; ++i) {
        cout << "Is a prime " << setw(2) << i << ": " << isPrime(i) << endl;
    }
    isPrimeTest(2147483647L);
    isPrimeTest(1000000000039ULL);
    isPrimeTest(1000003ULL * 1000003ULL);
    int count = printPrimeTable(100000);
    cout << endl << "(total " << count << " numbers in the table)" << endl;

    cout << endl;
    findPerfectNumbers();
    cout << endl;
    findAmicableNumbers(Amicable_Numbers_Limit);

    cout << endl;
    cout << "n, sigma0(n), sigma1(n), phi(n), moebius(n), omega(n), factor count and prime factors"
         << endl << endl;
    for (int i = 1; i <= 1000; ++i) {
        PrimeFactors<int> f(i);
        cout << setw(5) << i << setw(4) << sigma0(f) << setw(6) << sigma1(f)
             << setw(6) << phi(f) <<setw(4) << moebius(f)
             << setw(5) << omega(f) << setw(3) << factorCount(f)
             << "   " << f << endl;
    }

    cout << endl << endl << "First number with given number of divisors:" << endl << endl;
    for (unsigned int i = 1; i <= 36; ++i)
    {
        auto firstNumber = firstNumberWithNDivisors(i);
        PrimeFactors<decltype(firstNumber)> factors(firstNumber);
        cout << setw(3) << i << setw(16) << firstNumber << " = " << factors << endl;
    }

    cout << endl;
    for (int n = -2; n <= 32; ++n) {
        int nSquares = nSquaresSum(n);
        if (nSquares == -1)
            continue;
        cout << n << " is sum of " << nSquares << (nSquares == 1 ? " square." : " squares.") << endl;
    }

    return 0;
}
