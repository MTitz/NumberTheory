// Project Euler Problem 127 abc-hits

#include <iostream>
#include "numbertheory.h"

using std::cout, std::endl;

void searchAbcHits(int cmax)
{
    int count = 0, sum = 0;
    for (int c = 1; c < cmax; ++c) {
        for (int b = (c+1)/2; b < c; ++b) {
            if (math::gcd(b,c) != 1) continue;
            int a = c-b;
            if (math::gcd(a,b) != 1) continue;
            if (math::gcd(a,c) != 1) continue;
            if (math::radical((unsigned long long)a*b*c) < c) {
                ++count;
                sum += c;
                cout << "a=" << a << ", b=" <<  b << ", c=" << c << ", radical: " << math::radical((unsigned long long)a*b*c) << endl;
            }
        }
    }
    cout << "Count = " << count << endl;
    cout << "Sum   = " << sum   << endl;
}

int main()
{
    int cmax = 120*1000;
    searchAbcHits(cmax);
}
