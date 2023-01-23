#include <cmath>
#include <cstdlib>

namespace math {

template<typename F, typename I = std::size_t>
F harmonic_series(I n)
{
    const F one { 1 };
    F sum { 0 };
    for (I i = n; i > 0; --i) {
        sum += one / static_cast<F>(i);
    }
    return sum;
}

extern const double A[];
extern size_t n_A;
extern const double euler;

template<typename I>
double harmonic_upper(I n, I n_iter)
{
    if (n_iter > n_A)
        n_iter = n_A;
    double result = euler + log(n) + 0.5/n;
    double prod = n;
    for (I k = 2; k <= n_iter; ++k) {
        prod *= n+k-1;
        result -= A[k] / prod;
    }
    return result;
}

extern const long double A_l[];
extern size_t n_A_l;
extern const long double euler_l;

template<typename I>
long double harmonic_upper_ld(I n, I n_iter)
{
    if (n_iter > n_A_l)
        n_iter = n_A_l;
    long double result = euler_l + logl(n) + 0.5L/n;
    long double prod = n;
    for (I k = 2; k <= n_iter; ++k) {
        prod *= n+k-1;
        result -= A_l[k] / prod;
    }
    return result;
}

template<typename I>
double harmonic(I n)
{
    if (n <= 10000)
        return harmonic_series<double>(n);
    I n_iter = 10;
    if (n > 100000)
        n_iter = 3;
    if (n > 1000000)
        n_iter = 2;
    return harmonic_upper(n, n_iter);
}

template<typename I>
long double harmonic_ld(I n)
{
    if (n <= 10000)
        return harmonic_series<long double>(n);
    I n_iter = 10;
    if (n > 100000)
        n_iter = 3;
    if (n > 1000000)
        n_iter = 2;
    return harmonic_upper_ld(n, n_iter);
}

} /* end of namespace math */
