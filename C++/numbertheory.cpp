#include "numbertheory.h"

namespace math {

const int primes[] = {
#ifdef PRIMETABLE_SMALL
#include "primetable_small.h"
#elif defined PRIMETABLE_LARGE
#include "primetable_large.h"
#else
#include "primetable_medium.h"
#endif
};

std::size_t n_primes = sizeof(primes) / sizeof(int);


const bool q11[] = { true, true, false, true, true, true, false, false, false, true, false };
const bool q63[] = {
    true,  true,  false, false, true,  false, false, true,
    false, true,  false, false, false, false, false, false,
    true,  false, true,  false, false, false, true,  false,
    false, true,  false, false, true,  false, false, false,
    false, false, false, false, true,  true,  false, false,
    false, false, false, true,  false, false,  true, false,
    false, true,  false, false, false, false, false, false,
    false, false, true,  false, false, false, false };
const bool q64[] = {
    true,  true,  false, false, true,  false, false, false,
    false, true,  false, false, false, false, false, false,
    true,  true,  false, false, false, false, false, false,
    false, true,  false, false, false, false, false, false,
    false, true,  false, false, true,  false, false, false,
    false, true,  false, false, false, false, false, false,
    false, true,  false, false, false, false, false, false,
    false, true,  false, false, false, false, false, false };
const bool q65[] = {
    true,  true,  false, false, true,  false, false, false,
    false, true,  true,  false, false, false, true,  false,
    true,  false, false, false, false, false, false, false,
    false, true,  true,  false, false, true,  true,  false,
    false, false, false, true,  true,  false, false, true,
    true,  false, false, false, false, false, false, false,
    false, true,  false, true,  false, false, false, true,
    true,  false, false, false, false, true,  false, false,
    true };


long long powerMod(long long a, long long n, long long m)
{
    if (n == 0) {
        return 1;
    } else {
        HeadsAlgorithm<long long> *ha = new HeadsAlgorithm<long long>(m);
        auto result = powerMod<long long>(a, n, ha);
        delete ha;
        return result;
    }
}

unsigned long hyperExpMod(unsigned long a, unsigned long k, unsigned long m)
{
    return k == 1
        ? a % m
        : powerMod<unsigned long, unsigned long long>(a, hyperExpMod(a, k-1, m), m);
}

} /* end of namespace math */
