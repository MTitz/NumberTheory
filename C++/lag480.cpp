#include <iostream>
#include <iomanip>
#include <locale>
#include <cmath>
#include "harmonic.h"
#include "numbertheory.h"
using namespace std;

int main(void)
{
    //locale loc("de_DE");
    //cout.imbue(loc);
    cout << fixed;
    cout.precision(12);
    cout << showpoint;
    for (int i = 1; i <= 480; ++i) {
        long double h = math::harmonic_series<long double>(i);
        cout << setw(6) << i << setw(21)
             << h << setw(21) << h + exp(h) * log(h)
             << setw(6) << math::sigma0(i)
             << setw(9) << math::sigma1(i) << endl;
    }
    return 0;
}
