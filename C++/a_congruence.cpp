// Congruence 2^m = 2 (mod m)
// Note that only a few even values of m are solutions.
// By a theorem of Fermat all prime numbers solve it,
// therefore only composite solutions are printed out.

#include <iostream>
#include <iomanip>

#include "numbertheory.h"

using std::cout;
using std::endl;
using std::setw;

using namespace math;

unsigned long congruence(unsigned long m)
{
    return powerMod<unsigned long, unsigned long long>(2, m, m);
}

void print_solution(unsigned long m)
{
    cout << setw(10) << m;
    PrimeFactors primefactors(m);
    if (!primefactors.isPrime()) {
        cout << " = " << primefactors;
        if (even(m))
            cout << "  (even)";
    }
    cout << endl;
}

int main(void)
{
    //print_solution(2);
    for (unsigned long n = 3; n <= 4000000000UL; ++n) {
        if (!isPrime(n) && congruence(n) == 2) {
            print_solution(n);
        }
    }
}
