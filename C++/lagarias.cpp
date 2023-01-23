#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <limits>
#include <locale>

#include "harmonic.h"
#include "numbertheory.h"

using namespace std;

#ifdef DEBUG_PARTITON
static void print_partition(const int *a, int m)
{
    for (int i = 0; i < m; ++i)
        std::cout << ' ' << a[i];
    std::cout << std::endl;
}
#endif

void partitions(int n, void (*f)(const int *a, int m))
{
    int *a = new int[(std::size_t)(n+1)];
    const int *pa = a + 1;

    a[0] = 0;
    int m = 1;

    for (;;) {
        a[m] = n;
        int q = m;
        if (n == 1)
            --q;

        for (;;) {
            f(pa, m);
            if (a[q] != 2)
                break;

            a[q--] = 1;
            a[++m] = 1;
        }

        if (q == 0) {
            delete[] a;
            return;
        }
        int x = a[q] - 1;
        a[q] = x;
        n = m - q + 1;
        m = q + 1;

        while (n > x) {
            a[m] = x;
            ++m;
            n -= x;
        }
    }
}


static double log_limit;
static double *log_primes;

static void testfct(const int *a, int m)
{
#ifdef DEBUG_PARTITON
    print_partition(a, m);
#endif
    unsigned long long number = 1ULL;
    double log_number = 0;
    for (int i = 0; i < m; ++i) {
        log_number += a[i] * log_primes[i];
    }
    if (log_number > log_limit)
        return;
    for (int i = 0; i < m; ++i) {
        number *= math::pow((unsigned long long)math::primes[i], (unsigned int)a[i]);
    }
    long double h = math::harmonic_ld(number);
    long double h_limit = h + expl(h) * logl(h);
    math::PrimeFactors<unsigned long long> factors(number);
    cout << setw(19) << number << setw(3) << math::factorCount(factors) << ' '
         << setw(19) << scientific << setprecision(12) << h_limit << ' '
         << setw(20) << math::sigma1(factors) << ' '
         << setw(8) << fixed << setprecision(3)
         << math::sigma1(factors) / h_limit
         << "   " << factors << endl;
}

int main(int argc, char *argv[])
{
    //locale loc("de_DE");
    //cout.imbue(loc);
    if (argc != 2) {
        printf("Aufruf: lagarias n\n");
    } else {
        int n = std::atoi(argv[1]);
        if (n <= 0) {
            return 1;
        }
        if ((std::size_t)n > math::n_primes) {
            cout << "Nicht genug Primzahlen gespeichert." << endl;
            return 1;
        }
        cout.precision(9);
        double d_limit = pow(2.0, n+1) - 1.0;
        double Hn_limit = exp(math::euler) * d_limit * log(log(d_limit));
        if (Hn_limit > (double)numeric_limits<unsigned long long>::max()) {
            cout << "Grenze " << Hn_limit << " zu groß." << endl;
            return 1;
        }
        cout << fixed;
        cout.precision(3);
        cout << showpoint;
        unsigned long long limit = (1ULL << (n+1)) - 1;
        //cout << "Grenze: " << limit << endl;
        log_limit = log(limit);
        log_primes = new double[math::n_primes];
        for (int i = 0; i < n; ++i) {
            log_primes[i] = log(math::primes[i]);
        }
        for (int i = 1; i <= n; ++i) {
            partitions(i, testfct);
        }
        delete[] log_primes;
    }
    return 0;
}
