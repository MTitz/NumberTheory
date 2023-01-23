/* [Peter Giblin] Primes and Programming, Computing exercise 4.5 (a) */

#include <math.h>
#include <stdio.h>

int main()
{
    for (int n = 1; n <= 100; ++n) {
        int tmp = (int)floor(sqrt(n)) + 1;
        int d = tmp * tmp - n;
        printf("%4d  %4d\n", n, d);
    }
}
