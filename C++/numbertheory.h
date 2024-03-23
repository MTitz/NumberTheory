// File: numbertheory.h
// Author: Martin Titz
//
// Literature
// [Cohen]      Henri Cohen, "A Course in Computational Algebraic Number Theory", Springer, 1996
// [Forster]    Otto Forster, "Algorithmische Zahlentheorie", vieweg, Braunschweig / Wiesbaden, 1996
// [Giblin]     Peter Giblin, "Primes and Programming", Cambridge University Press, 1993
// [Nathanson]  Melvyn B. Nathanson "Elementary Methods in Number Theory", Springer, 2000

#ifndef NUMBERTHEORY_H
#define NUMBERTHEORY_H

#include <algorithm> // sort
#include <array>
#include <cmath>
#include <cstdlib>
#include <functional> // multiplies
#include <iostream>
#include <limits>
#include <numeric> // accumulate
#include <sstream>
#include <stdexcept>
#include <type_traits>
#include <vector>

namespace math {

template<typename T>
unsigned int bitlength(const T& x)
{
    T var;
    if constexpr (std::is_signed<T>::value) {
        var = x >= 0 ? x : -x;
    } else {
        var = x;
    }
    unsigned int bits = 0;
    while (var >= 4096) {
        bits += 12;
        var >>= 12;
    }
    while (var != 0) {
        ++bits;
        var >>= 1;
    }
    return bits;
}

template<typename T>
int sum_of_digits(T n, int base = 10)
{
    int sum = 0;
    while (n != 0) {
        sum += (int)(n % base);
        n /= base;
    }
    return sum;
}

template<typename T>
inline bool even(const T& n)
{
    return (n & 1) == 0;
}

template<typename T>
inline bool odd(const T& n)
{
    return (n & 1) == 1;
}

template<typename T>
T pow(T base, unsigned int exponent)
{
    T result = 1;
    while (exponent) {
        if (exponent & 1)
            result *= base;
        exponent >>= 1;
        base *= base;
    }
    return result;
}

template<typename T, typename U = T>
T powerMod(T a, T n, T m)
{
    if (n == 0) {
        return 1;
    } else if (even(n)) {
        U t = powerMod<T,U>(a, n/2, m);
        return static_cast<T>((t * t) % m);
    } else {
        U t = powerMod<T,U>(a, n-1, m);
        return static_cast<T>((a * t) % m);
    }
}

// Head's algorithm for integer multiplication modulo m, see section 4.3 in
// Peter Giblin, "Primes and Programming", Cambridge University Press, 1993
template<typename T>
class HeadsAlgorithm
{
  public:
    HeadsAlgorithm(T m)
    {
        if constexpr (std::is_unsigned<T>::value) {
            throw std::logic_error("Head's algorithm requires signed integers");
        }
        if (m <= 0) {
            throw std::invalid_argument("m must be positive for Head's algorithm");
        }
        if (m >= std::numeric_limits<T>::max() / 4) {
            throw std::invalid_argument("m too large for Head's algorithm");
        }
        _m = m;
        _T = (long)std::floor(std::sqrt(m) + 0.5);
        _t = _T * _T - m;
    }

    T multiplyModM(T x, T y)
    {
        T a = x / _T;
        T b = x - a * _T;
        T c = y / _T;
        T d = y - c * _T;
        T z = (a * d + b * c) % _m;
        T e = (a * c) / _T;
        T f = a * c - e * _T;
        T v = (z + e * _t) % _m;
        if (v < 0) v += _m;
        T g = v / _T;
        T h = v - g * _T;
        T j = (f + g) * _t % _m;
        if (j < 0) j += _m;
        T k = (j + b * d) % _m;
        return (h * _T + k) % _m;
    }

  private:
    T _m;
    T _T;
    T _t;
};

template<typename T>
T powerMod(T a, T n, HeadsAlgorithm<T> *ha)
{
    if (n == 0) {
        return 1;
    } else if (n % 2 == 0) {
        T t = powerMod(a, n/2, ha);
        return ha->multiplyModM(t, t);
    } else {
        T t = powerMod(a, n-1, ha);
        return ha->multiplyModM(a, t);
    }
}

long long powerMod(long long a, long long n, long long m);


// From Project Euler Problem 188
//    The hyperexponentiation of a number
//    https://projecteuler.net/problem=188
unsigned long hyperExpMod(unsigned long a, unsigned long k, unsigned long m);


template<typename T>
T primefactor_in_factorial(const T& number, const T& p)
{
    if (number < 0)
        throw std::invalid_argument("first argument of primefactor_in_factorial is negative");
    if (p < 2)
        throw std::invalid_argument("second argument of primefactor_in_factorial is too small");
    T n = number;
    T sum = 0;
    while (n >= p) {
        n /= p;
        sum += n;
    }
    return sum;
}


template<typename T>
T gcd(T x, T y)
{
    while (y != 0) {
        T tmp = y;
        y = x % y;
        x = tmp;
    }
    if constexpr (std::is_signed<T>::value) {
        return std::abs(x);
    } else {
        return x;
    }
}

template<typename T>
inline T fast_lcm(T x, T y)
{
    return (x / gcd(x, y)) * y;
}

template<typename T>
T lcm(const T& x, const T& y)
{
    if (x == 0 || y == 0)
        return 0;
    if constexpr (std::is_signed<T>::value) {
        return std::abs(fast_lcm(x, y));
    } else {
        return (x / gcd(x, y)) * y;
    }
}

inline unsigned int lcm(unsigned int x, unsigned int y)
{
    return fast_lcm(x, y);
}

inline unsigned long int lcm(unsigned long int x, unsigned long int y)
{
    return fast_lcm(x, y);
}

inline unsigned long long int lcm(unsigned long long int x,
                                  unsigned long long int y)
{
    return fast_lcm(x, y);
}

template<typename T>
T repeated_function(T(*f)(const T&, const T&), int n, const T u[])
{
    T v = u[0];
    for (int k = 1; k < n; ++k) {
        v = f(v, u[k]);
    }
    return v;
}

template<typename T>
T gcd(int n, const T u[])
{
    T d = u[0];
    int k = 1;
    while (d != 1 && k < n) {
        d = gcd(u[k], d);
        ++k;
    }
    return d;
}

template<typename T>
inline T lcm(int n, const T u[])
{
    return repeated_function(lcm, n, u);
}


/*** Currently unused, work in progress

template<typename It, typename Func>
auto repeatedFunction(It begin, It end, Func func) -> decltype(*begin + *end)
{
    decltype(*begin + *end) value;
    if (begin != end) {
        value = *begin;
        ++begin;
        for (; begin != end; ++begin) {
            value = func(value, *begin);
        }
    } else {
        value = 1;
    }
    return value;
}

***/


template<typename T>
struct extendedEuclidResult
{
    T d;
    T u;
    T v;
};

// input: non-negative integers a and b
// output: triple (u,v,d) such that au+bv=d and d=(a,b)
// see [Cohen], Algorithm 1.3.6
template<typename T>
extendedEuclidResult<T> extendedEuclid(T a, T b)
{
    extendedEuclidResult<T> result;
    result.d = a;
    result.u = 1;
    if (b == 0) {
        result.v = 0;
    } else {
        T v1 = 0;
        T v3 = b;
        while (v3 != 0) {
            T q =  result.d / v3;
            T t3 = result.d % v3;
            T t1 = result.u - q*v1;
            result.u = v1;
            result.d = v3;
            v1 = t1;
            v3 = t3;
        }
        result.v = (result.d - a*result.u) / b;
    }
    return result;
}

template<typename T>
T modInverse(T a, T m)
{
    extendedEuclidResult<T> result = extendedEuclid(a, m);
    if (result.d != 1)
        throw std::runtime_error("inverse does not exist");
    return result.u >= 0 ? result.u : result.u + m;
}


template<typename T>
class SimpleLinearCongruence
{
    public:
        SimpleLinearCongruence() : _b(0), _m(0) {}
        SimpleLinearCongruence(T b, T m) : _b(b), _m(m) {}
        T b() const { return _b; }
        T m() const { return _m; }
    private:
        T _b;
        T _m;
};

template<typename T>
class LinearCongruence
{
    public:
        LinearCongruence(T a, T b, T m) : _a(a), _b(b), _m(m) {}
        T a() const { return _a; }
        T b() const { return _b; }
        T m() const { return _m; }
    private:
        T _a;
        T _b;
        T _m;
};

template<typename T>
SimpleLinearCongruence<T> solve(const LinearCongruence<T>& congruence)
{
    T a = congruence.a();
    T b = congruence.b();
    T m = congruence.m();
    T d = gcd(a, m);
    if (b % d == 0) {
        a /= d;
        b /= d;
        m /= d;

        try {
            b *= modInverse(a, m);
        }
        catch (std::runtime_error& re) {
            std::cerr << "Error in solve: " << re.what() << std::endl;
            return SimpleLinearCongruence<T>(0, 0);
        }

        b %= m;
        if (b < 0)
            b += m;

        return SimpleLinearCongruence<T>(b, m);
    } else {
        return SimpleLinearCongruence<T>(0, 0);
    }
}

template<typename T>
SimpleLinearCongruence<T> solve(const SimpleLinearCongruence<T>& c1,
                                const SimpleLinearCongruence<T>& c2)
{
    T m1 = c1.m();
    T m2 = c2.m();
    T b1 = c1.b();
    T b2 = c2.b();
    T d = gcd(m1, m2);
    if ((b2 - b1) % d == 0) {
        T t1 = solve(LinearCongruence<T>(m1 / d, (b2 - b1) / d, m2 / d)).b();
        return SimpleLinearCongruence<T>(b1 + t1 * m1, lcm(m1, m2));
    } else {
        return SimpleLinearCongruence<T>(0, 0);
    }
}

template<typename T>
SimpleLinearCongruence<T> solve(const SimpleLinearCongruence<T> c[], int n)
{
    return repeated_function<SimpleLinearCongruence<T>>(solve, n, c);
}

template<typename T>
SimpleLinearCongruence<T> solve(const LinearCongruence<T> c[], int n)
{
    bool solvable = true;
    SimpleLinearCongruence<T> *simpleC = new SimpleLinearCongruence<T>[(std::size_t)n];
    for (int i = 0; i < n; ++i) {
        simpleC[i] = solve(c[i]);
        if (simpleC[i].m() == 0) {
            solvable = false;
            break;
        }
    }
    SimpleLinearCongruence<T> solution = solvable
                ? solve(simpleC, n)
                : SimpleLinearCongruence<T>(0,0);
    delete[] simpleC;
    return solution;
}


template<typename T>
int jacobi(T a, T b)
{
    static const int tab2[] = {0, 1, 0, -1, 0, -1, 0, 1};
    if (b == 0)
        return (a == 1 || a == -1) ? 1 : 0;
    if ((a & 1) == 0 && (b & 1) == 0)
        return 0;
    int v = 0;
    while ((b & 1) == 0) {
        v += 1;
        b /= 2;
    }
    int k = (v & 1) == 0 ? 1 : tab2[a & 7];
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
        if (v & 1)
            k *= tab2[b & 7];
        if (a & b & 2)
            k = -k;
        T r = a >= 0 ? a : -a;
        a = b % r;
        b = r;
    }
}


template<typename T>
T integerSqrt(T n)
{
    if (n <= 0)
        return 0;
    T x = n;
    while (true) {
        T y = (x + n / x) >> 1;
        if (y < x)
            x = y;
        else
            return x;
    }
}


// sqrt_to_cfrac from [Forster], Kapitel 21 Kettenbrüche

template<typename T>
std::vector<T> sqrt_to_cfrac(const T& n)
{
    T w = integerSqrt(n);
    T q = n - w * w;
    if (q == 0) {
        std::vector<T> cfrac {w};
        return cfrac;
    }
    T w2 = 2 * w;
    T a = w;
    T m = w;
    T q0 = 1;
    std::vector<T> cfrac;
    cfrac.push_back(a);
    while (a != w2) {
        a = (m + w) / q;
        cfrac.push_back(a);
        T m1 = a * q - m;
        T q1 = q0 + a * (m - m1);
        m = m1;
        q0 = q;
        q = q1;
    }
    return cfrac;
}

// pell4 and pell from [Forster], Kapitel 25 Die Pell'sche Gleichung

template<typename T>
std::array<T, 3> pell4(const T& d)
{
    if (d == 5)
        return std::array<T, 3> {1, 1, -4};
    T w = integerSqrt(d); T q = d - w * w;
    T q0 = 1; T m = w;
    T u0 = 1; T u = m; T v0 = 0; T v = 1;
    int sign = -1;
    while (q != 4 && q != 1) {
        T a = (m + w) / q;
        T m1 = a * q - m; T q1 = q0 + a * (m - m1);
        T u1 = a * u + u0; T v1 = a * v + v0;
        m = m1; q0 = q; q = q1;
        u0 = u; u = u1; v0 = v; v = v1;
        sign = -sign;
    }
    return std::array<T, 3> {u, v, sign * q};
}

template<typename T>
std::array<T, 2> pell(const T& d)
{
    auto z = pell4(d);
    T u = z[0]; T v = z[1]; T c = z[2];
    if (c == 4 || c == -4) {
        T u2 = u * u;
        c /= 4;
        u = u * ((u2 - 3 * c) / 2);
        v = v * ((u2 - c) / 2);
    }
    if (c < 0) {
        v = 2 * u * v;
        u = 2 * u * u + 1;
    }
    return std::array<T, 2> {u, v};
}


extern const bool q11[], q63[], q64[], q65[];

template<typename T>
T squareTest(const T& n)
{
    if (n < 0)
        return -1;
    T t = n & 63;
    if (!q64[t])
        return -1;
    T r = n % 45045;
    if (!q63[r % 63])
        return -1;
    if (!q65[r % 65])
        return -1;
    if (!q11[r % 11])
        return -1;
    T q = integerSqrt(n);
    return q * q == n ? q : -1;
}

template<typename T>
bool isSquare(const T& n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n < 0) {
            return false;
        }
    }
    T t = n & 63;
    if (!q64[t])
        return false;
    T r = n % 45045;
    if (!q63[r % 63])
        return false;
    if (!q65[r % 65])
        return false;
    if (!q11[r % 11])
        return false;
    T i_sqrt  = integerSqrt(n);
    return i_sqrt * i_sqrt == n;
}

/* isPowerOf: Is there a number k, so that a^k = n
   0^0 = 1 assumed */
template<typename T>
bool isPowerOf(T a, T n)
{
    if (n == 0)
        return a == 0 || a == 1;
    if (n == 1)
        return a == 1;
    if (n == -1)
        return a == -1 || a == 1;
    if (a == 0)
        return false;
        // return n == 0 || n == 1  (but these cases already have been done)
    while (a % n == 0) {
        a /= n;
    }
    return a == 1;
}


template<typename I>
std::vector<bool> eratosthenes(I n)
{
    std::vector<bool> a(n+1);
    a[0] = a[1] = false;
    for (std::size_t i = 2; i <= (std::size_t)n; ++i) {
        a[i] = true;
    }
    I p = 2;
    while (p * p <= n) {
        I j = p * p;
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

extern const int primes[];
extern std::size_t n_primes;

template<typename T>
class Factor
{
    public:
        Factor(T base, unsigned int exponent)
                        : _base(base), _exponent(exponent)
        {
        }

        Factor(const Factor<T>& f) { _base = f._base; _exponent = f._exponent; }

        Factor& operator=(const Factor& f)
        {
            _base = f._base;
            _exponent = f._exponent;
            return *this;
        }

        T base() const { return _base; }
        unsigned int exponent() const { return _exponent; }
        std::string toString() const {
            std::stringstream ss;
            ss << _base;
            if (_exponent != 1)
                ss << '^' << _exponent;
            return ss.str();
        }

    private:
        T _base;
        unsigned int _exponent;
};

template<typename T>
std::ostream& operator<<(std::ostream& os, const Factor<T>& factor)
{
    os << factor.toString();
    return os;
}


template<typename T>
class PrimeFactors
{
    public:

        PrimeFactors()
        {
        }

        explicit PrimeFactors(T n)
        {
            if (n > 0) {
                factorize(n);
            }
        }

        PrimeFactors(T p, unsigned int n)
        {
            if (n > 0) factor.push_back(Factor<T>(p, n));
        }

        PrimeFactors(T p, unsigned int n, T q, unsigned int m)
        {
            if (n > 0) factor.push_back(Factor<T>(p, n));
            if (m > 0) factor.push_back(Factor<T>(q, m));
        }

        PrimeFactors(T p1, unsigned int e1, T p2, unsigned int e2, T p3, unsigned int e3)
        {
            if (e1 > 0) factor.push_back(Factor<T>(p1, e1));
            if (e2 > 0) factor.push_back(Factor<T>(p2, e2));
            if (e3 > 0) factor.push_back(Factor<T>(p3, e3));
        }

        PrimeFactors(T p1, unsigned int e1, T p2, unsigned int e2, T p3, unsigned int e3, T p4, unsigned int e4)
        {
            if (e1 > 0) factor.push_back(Factor<T>(p1, e1));
            if (e2 > 0) factor.push_back(Factor<T>(p2, e2));
            if (e3 > 0) factor.push_back(Factor<T>(p3, e3));
            if (e4 > 0) factor.push_back(Factor<T>(p4, e4));
        }

        std::vector<Factor<T>> getFactors() const { return factor; }
        typename std::vector<Factor<T>>::size_type nFactors() const { return factor.size(); }
        Factor<T> factors(typename std::vector<Factor<T>>::size_type i) const { return factor[i]; }
        bool isPrime() const { return factor.size() == 1 && factors(0).exponent() == 1; }

        std::string toString() const {
            std::stringstream ss;
            if (factor.empty())
                ss << 1;
            for (typename std::vector<Factor<T>>::const_iterator it =
                    factor.begin();
                    it != factor.end();
                    ++it) {
                if (it != factor.begin())
                    ss << '*';
                ss << *it;
            }
            return ss.str();
        }

        void factorize(T n)
        {
            T limit = integerSqrt(n);
            for (std::size_t i = 0; i < n_primes; ++i) {
                T p = (T)primes[i];
                unsigned int e = 0;
                while (n % p == 0) {
                    n /= p;
                    e += 1;
                }
                if (e > 0) {
                    factor.push_back(Factor<T>(p, e));
                    if (n == 1)
                        return;
                    limit = integerSqrt(n);
                }
                if (p > limit) {
                    if (n > 1)
                        factor.push_back(Factor<T>(n, 1));
                    return;
                }
            }
            if (n > 1) {
                T p = (T)primes[n_primes - 1];
                T offset = p % 6 == 1 ? 4 : 2;        /* p == 1 or 5 mod 6 */
                while (n > 1) {
                    p += offset;
                    offset = 6 - offset;
                    unsigned int e = 0;
                    while (n % p == 0) {
                        n /= p;
                        e += 1;
                    }
                    if (e > 0) {
                        factor.push_back(Factor<T>(p, e));
                        if (n == 1)
                            return;
                        limit = integerSqrt(n);
                    }
                    if (p > limit) {
                        factor.push_back(Factor<T>(n, 1));
                        return;
                    }
                }
            }
        }

    protected:
        std::vector<Factor<T>> factor;
};

template<typename T>
std::ostream& operator<<(std::ostream& os, const PrimeFactors<T>& primefactors)
{
    os << primefactors.toString();
    return os;
}


template<typename T>
class FactorizedNumber;
template<typename T>
std::ostream& operator<< (std::ostream&, const FactorizedNumber<T>&);

template<typename T>
class FactorizedNumber : public virtual PrimeFactors<T>
{
        friend std::ostream& operator<< <>(std::ostream&,
                                           const FactorizedNumber<T>&);
    public:
        explicit FactorizedNumber(T n) : number(n)
        {
            if (number == 0) {
                sign = 0;
            } else {
                if constexpr (std::is_signed<T>::value) {
                    sign = number > 0 ? 1 : -1;
                    this->factorize(number * sign);
                } else {
                    sign = 1;
                    this->factorize(number);
                }
            }
        }

        T getNumber() const { return number; }
        int getSign() const { return sign; }

    private:
        T number;
        int sign;
};

template<typename T>
std::ostream& operator<<(std::ostream& os, const FactorizedNumber<T>& factorizedNumber)
{
    int sign = factorizedNumber.getSign();
    if (sign != 1 || factorizedNumber.factor.empty())
        os << sign;
    for (typename std::vector<Factor<T>>::const_iterator it =
                factorizedNumber.factor.begin();
            it != factorizedNumber.factor.end();
            ++it) {
        if (it != factorizedNumber.factor.begin() || sign != 1)
            os << '*';
        os << *it;
    }
    return os;
}


template<typename T>
std::vector<T> divisors(const PrimeFactors<T>& primefactors)
{
    using iter_type = typename std::vector<Factor<T>>::size_type;
    const iter_type n = primefactors.nFactors();
    if (n == 0) {
        std::vector<T> div {1};
        return div;
    }

    std::vector<T> div(static_cast<iter_type>(sigma0(primefactors)));
    iter_type index = 0;
    unsigned int* a = new unsigned int[n+1];
    unsigned int* m = new unsigned int[n+1];
    for (iter_type i = 1; i <= n; ++i) {
        m[i] = primefactors.factors(i-1).exponent();
    }

    for (iter_type i = 0; i <= n; ++i) {
        a[i] = 0;
    }
    m[0] = 2;

    while (true) {
        T d = 1;
        for (iter_type i = 1; i <= n; ++i) {
            d *= math::pow(primefactors.factors(i-1).base(), a[i]);
        }
        div[index++] = d;

        iter_type j = n;
        while (a[j] == m[j]) {
            a[j] = 0;
            --j;
        }
        if (j == 0)
            break;
        ++a[j];
    }

    delete[] m;
    delete[] a;
    std::sort(div.begin(), div.end());
    return div;
}

template<typename T>
std::vector<T> divisors(const T& n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n < 0) {
            PrimeFactors<T> factors(-n);
            return divisors(factors);
        }
    }
    PrimeFactors<T> factors(n);
    return divisors(factors);
}

template<typename T>
std::vector<T> smallDivisors(const FactorizedNumber<T>& primefactors)
{
    using iter_type = typename std::vector<Factor<T>>::size_type;
    const iter_type n = primefactors.nFactors();
    if (n == 0) {
        std::vector<T> div {1};
        return div;
    }

    std::vector<T> div(static_cast<iter_type>((sigma0(primefactors)+1)/2));
    T limit;
    if constexpr (std::is_signed<T>::value) {
        limit = integerSqrt(primefactors.getNumber() >= 0 ? primefactors.getNumber() : -primefactors.getNumber());
    } else {
        limit = integerSqrt(primefactors.getNumber());
    }
    iter_type index = 0;
    unsigned int* a = new unsigned int[n+1];
    unsigned int* m = new unsigned int[n+1];
    for (iter_type i = 1; i <= n; ++i) {
        m[i] = primefactors.factors(i-1).exponent();
    }

    for (iter_type i = 0; i <= n; ++i) {
        a[i] = 0;
    }
    m[0] = 2;

    while (true) {
        T d = 1;
        for (iter_type i = 1; i <= n; ++i) {
            d *= math::pow(primefactors.factors(i-1).base(), a[i]);
        }
        if (d <= limit)
            div[index++] = d;

        iter_type j = n;
        while (a[j] == m[j]) {
            a[j] = 0;
            --j;
        }
        if (j == 0)
            break;
        ++a[j];
    }

    delete[] m;
    delete[] a;
    std::sort(div.begin(), div.end());
    return div;
}

template<typename T>
std::vector<T> smallDivisors(const T& n)
{
    FactorizedNumber<T> factors(n);
    return smallDivisors(factors);
}

template<typename T>
T radical(const PrimeFactors<T>& primefactors)
{
    std::vector<T> p(primefactors.nFactors());
    for (typename std::vector<T>::size_type i = 0; i < primefactors.nFactors(); ++i) {
        p[i] = primefactors.factors(i).base();
    }
    return std::accumulate(p.begin(), p.end(), static_cast<T>(1), std::multiplies<T>());
}

template<typename T>
T radical(const T& n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n < 0) {
            PrimeFactors<T> factors(-n);
            return radical(factors);
        }
    }
    if (n == 0)
        throw std::runtime_error("radical of an integer is not defined for number 0");
    PrimeFactors<T> factors(n);
    return radical(factors);
}

template<typename T>
T additiveFunction(T (*factorF)(T base, unsigned int exponent),
        const PrimeFactors<T>& primefactors)
{
    T result = 0;
    const typename std::vector<Factor<T>>::size_type nFactors = primefactors.nFactors();
    for (typename std::vector<Factor<T>>::size_type i = 0; i < nFactors; ++i) {
        Factor<T> factor = primefactors.factors(i);
        result += factorF(factor.base(), factor.exponent());
    }
    return result;
}

template<typename T>
T multiplicativeFunction(T (*factorF)(T base, unsigned int exponent),
        const PrimeFactors<T>& primefactors)
{
    T result = 1;
    const typename std::vector<Factor<T>>::size_type nFactors = primefactors.nFactors();
    for (typename std::vector<Factor<T>>::size_type i = 0; i < nFactors; ++i) {
        Factor<T> factor = primefactors.factors(i);
        result *= factorF(factor.base(), factor.exponent());
    }
    return result;
}

template<typename T>
T factorF_sigma0(T base, unsigned int exponent)
{
    return (T)exponent+1;
}

template<typename T>
T factorF_sigma1(T base, unsigned int exponent)
{
    T sum = 1;
    for (unsigned int i = 1; i <= exponent; ++i) {
        sum *= base;
        sum += 1;
    }
    return sum;
}

template<typename T>
T factorF_phi(T base, unsigned int exponent)
{
    return pow(base, exponent-1) * (base-1);
}

template<typename T>
T omega(const PrimeFactors<T>& primefactors)
{
    return additiveFunction<T>([](T base, unsigned int exponent) -> T { return 1; }, primefactors);
}

template<typename T>
T factorCount(const PrimeFactors<T>& primefactors)
{
    return additiveFunction<T>([](T base, unsigned int exponent) -> T { return exponent; }, primefactors);
}

template<typename T>
T sigma0(const PrimeFactors<T>& primefactors)
{
    return multiplicativeFunction<T>(factorF_sigma0, primefactors);
}

template<typename T>
T sigma1(const PrimeFactors<T>& primefactors)
{
    return multiplicativeFunction<T>(factorF_sigma1, primefactors);
}

template<typename T>
T phi(const PrimeFactors<T>& primefactors)
{
    return multiplicativeFunction<T>(factorF_phi, primefactors);
}

template<typename T>
T moebius(const PrimeFactors<T>& primefactors)
{
    return multiplicativeFunction<T>([](T base, unsigned int exponent) -> T { return exponent >= 2 ? 0 : -1; }, primefactors);
}


template<typename T>
T sigma0(const FactorizedNumber<T>& factorizedNumber)
{
    return multiplicativeFunction<T>(factorF_sigma0, (PrimeFactors<T>)factorizedNumber);
}


template<typename T>
T omega(T n)
{
    return omega(PrimeFactors<T>(n));
}

template<typename T>
T factorCount(T n)
{
    return factorCount(PrimeFactors<T>(n));
}

template<typename T>
T sigma0(T n)
{
    return sigma0(PrimeFactors<T>(n));
}

template<typename T>
T sigma1(T n)
{
    return sigma1(PrimeFactors<T>(n));
}

template<typename T>
T aliquotSum(T n)
{
    return sigma1(PrimeFactors<T>(n)) - n;
}

template<typename T>
T phi(T n)
{
    return phi(PrimeFactors<T>(n));
}

template<typename T>
T moebius(T n)
{
    return moebius(PrimeFactors<T>(n));
}

template<typename T>
bool isPrime(const T& n)
{
    if (n < 2)
        return false;
    if (n == 2 || n == 3)
        return true;
    T limit = integerSqrt(n);
    for (std::size_t i = 0; i < n_primes; ++i) {
        T p = (T)primes[i];
        if (n % p == 0) {
            return false;
        } else if (p > limit) {
            return true;
        }
    }
    T p = (T)primes[n_primes - 1];
    T offset = p % 6 == 1 ? 4 : 2;        /* p == 1 or 5 mod 6 */
    do {
        p += offset;
        offset = 6 - offset;
        if (n % p == 0) {
            return false;
        }
    } while (p <= limit);
    return true;
}

template<typename T>
bool isSquareFree(const PrimeFactors<T>& primefactors)
{
    auto n = primefactors.nFactors();
    for (auto i = decltype(n){0}; i < n; ++i) {
        if (primefactors.factors(i).exponent() >= 2)
            return false;
    }
    return true;
}

template<typename T>
bool isSquareFree(T n)
{
    return isSquareFree(PrimeFactors<T>(n));
}

// multiplicative order of a modulo n
template<typename T>
T ord1(const T& a, const T& n)
{
    if (gcd(a, n) > 1)
        throw std::runtime_error("multiplicative order not defined");
    // implementation directly to definition, but slow
    T r = 1;
    T power = a % n;
    while (power != 1) {
        ++r;
        power = (power * a) % n;
    }
    return r;
}

template<typename T>
T ord2(const T& a, const T& p, unsigned int e)
{
    T m = pow(p, e);
    T t = m / p * (p - 1);
    std::vector<T> fac = divisors(t);
    for (typename std::vector<T>::size_type i = 0; i < fac.size(); ++i) {
        if (powerMod(a, fac[i], m) == 1)
            return fac[i];
    }
    return 0;
}

// multiplicative order of a modulo n
template<typename T>
T ord2(const T& a, const T& n)
{
    if (gcd(a, n) > 1)
        throw std::runtime_error("multiplicative order not defined");
    PrimeFactors<T> pf(n);
    T res = 1;
    auto nFactors = pf.nFactors();
    for (auto i = decltype(nFactors){0}; i < nFactors; ++i) {
        res = lcm(res, ord2(a, pf.factors(i).base(), pf.factors(i).exponent()));
    }
    return res;
}

// multiplicative order of a modulo n
template<typename T>
T ord(const T& a, const T& n)
{
    return n < 120 ? ord1(a, n) : ord2(a, n);
}

// check if n is primitive root modulo p
// p must be prime number > 2
template<typename T>
bool isPrimitiveRoot(const T& n, const T& p)
{
    PrimeFactors<T> pf(p-1);
    auto nFactors = pf.nFactors();
    for (auto i = decltype(nFactors){0}; i < nFactors; ++i) {
        auto m = (p-1) / pf.factors(i).base();
        if (powerMod(n, m, p) == 1)
            return false;
    }
    return true;
}

// auxiliary function for repeated calls with fixed p
// check if n is primitive root modulo p
// p must be prime number > 2
// pf must contain the prime factors of p-1
template<typename T>
bool isPrimitiveRoot(const T& n, const T& p, const PrimeFactors<T>& pf)
{
    auto nFactors = pf.nFactors();
    for (auto i = decltype(nFactors){0}; i < nFactors; ++i) {
        auto m = (p-1) / pf.factors(i).base();
        if (powerMod(n, m, p) == 1)
            return false;
    }
    return true;
}

// find first primitive root of p
// p must be prime number > 2
template<typename T>
T primitiveRootPrime(const T& p)
{
    PrimeFactors<T> pf(p-1);
    T x {2};
    while (x <= p-1) {
        if (isPrimitiveRoot(x, p, pf)) {
            return x;
        } else {
            ++x;
            if (isSquare(x))
                ++x;
        }
    }
    throw std::runtime_error("argument must be an odd prime number");
}

// find first primitive root of n
// returns 0 if no primitive root exists
template<typename T>
T primitiveRoot(const T& n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n <= 0)
            return 0;
    }
    PrimeFactors<T> pf(n);
    if (pf.nFactors() == 1) {
        if (pf.factors(0).base() == 2 && pf.factors(0).exponent() >= 3)
            return 0;
    } else if (pf.nFactors() == 2) {
        if (pf.factors(0).base() != 2 || pf.factors(0).exponent() >= 2)
            return 0;
    } else if (pf.nFactors() >= 3) {
        return 0;
    }
    T phiValue = phi(pf);
    for (T i = 1; i < n; ++i) {
        if (gcd(i, n) == 1) {
            T order = 1;
            T power = i % n;
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
template<typename T>
std::vector<T> primitiveRoots(const T& n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n <= 0)
            return std::vector<T>();
    }
    PrimeFactors<T> pf(n);
    if (pf.nFactors() == 1) {
        if (pf.factors(0).base() == 2 && pf.factors(0).exponent() >= 3)
            return std::vector<T>();
    } else if (pf.nFactors() == 2) {
        if (pf.factors(0).base() != 2 || pf.factors(0).exponent() >= 2)
            return std::vector<T>();
    } else if (pf.nFactors() >= 3) {
        return std::vector<T>();
    }
    T phiValue = phi(pf);
    //T n_roots = phi(phiValue);
    //typename std::vector<T>::size_type nRoots = static_cast<typename std::vector<T>::size_type>(n_roots);
    std::vector<T> roots;
    for (T i = 1; i < n; ++i) {
        if (gcd(i, n) == 1) {
            T order = 1;
            T power = i % n;
            while (power != 1) {
                ++order;
                power = (power * i) % n;
            }
            if (order == phiValue) {
                roots.push_back(i);
                continue;
            }
        }
    }
    return roots;
}

template<typename T>
bool isCarmichaelNumber(const T&number, const PrimeFactors<T>& primefactors)
{
    auto n = primefactors.nFactors();
    if (n <= 2 || even(n))
        return false;  // must be odd and have at least 3 prime factors
    for (auto i = decltype(n){0}; i < n; ++i) {
        if (primefactors.factors(i).exponent() >= 2)
            return false;  // not squarefree
        if ((number-1) % (primefactors.factors(i).base()-1) != 0)
            return false;
    }
    return true;
}

template<typename T>
bool isCarmichaelNumber(T n)
{
    return n >= 3 && odd(n) && isCarmichaelNumber(n, PrimeFactors<T>(n));
}

template<typename T>
bool isDeficientNumber(T n)
{
    return aliquotSum(n) < n;
}

template<typename T>
bool isPerfectNumber(T n)
{
    return aliquotSum(n) == n;
}

template<typename T>
bool isAbundantNumber(T n)
{
    return aliquotSum(n) > n;
}

template<typename T>
bool isAmicablePair(T m, T n)
{
    return aliquotSum(m) == n && aliquotSum(n) == m;
}

template<typename T>
T firstNumberWithNDivisors(T N)
{
    constexpr T TWO   {2};
    constexpr T THREE {3};
    PrimeFactors<T> Nfactors(N);
    if (Nfactors.isPrime()) {
        // N = p with p prime number
        return pow(TWO, N-1);
    } else if (Nfactors.nFactors() == 2
                    && Nfactors.factors(0).exponent() == 1
                    && Nfactors.factors(1).exponent() == 1) {
        // N = p*q with p < q prime numbers
        return pow(TWO,   Nfactors.factors(1).base()-1) *
               pow(THREE, Nfactors.factors(0).base()-1);
    }
    T n = 1;
    while (sigma0(n) != N) {
        ++n;
    }
    return n;
}

template<typename T>
int nSquaresSum(T n)
{
    if constexpr (std::is_signed<T>::value) {
        if (n < 0)
            return -1;
    }
    T nSqrt = integerSqrt(n);
    if (nSqrt * nSqrt == n)
        return 1;
    PrimeFactors<T> factors(n);
    bool twoSquares = true;
    for (Factor<T> factor : factors.getFactors()) {
        if (factor.base() % 4 == 3 && factor.exponent() % 2 == 1) {
            twoSquares = false;
            break;
        }
    }
    if (twoSquares)
        return 2;
    T n2 = n;
    while (n2 % 4 == 0) {
        n2 /= 4;
    }
    return n2 % 8 == 7 ? 4 : 3;
}


} /* end of namespace math */

#endif
