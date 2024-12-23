#include <stdio.h>

unsigned long integerSqrt(unsigned long n)
{
    if (n == 0)
        return 0;
    unsigned long x, y;
    x = n;
    for (;;) {
        y = (x + n / x) >> 1;
        if (y < x)
            x = y;
        else
            return x;
    }
}

static int q11[11], q63[63], q64[64], q65[65];

void init_square_test()
{
    int k;

    /* Fill 11 */
    for (k = 0; k <= 10; ++k)
    {
        q11[k] = 0;
    }
    for (k = 0; k <= 5; ++k)
    {
        q11[(k*k) % 11] = 1;
    }

    /* Fill 63 */
    for (k = 0; k <= 62; ++k)
    {
        q63[k] = 0;
    }
    for (k = 0; k <= 31; ++k)
    {
        q63[(k*k) % 63] = 1;
    }


    /* Fill 64 */
    for (k = 0; k <= 63; ++k)
    {
        q64[k] = 0;
    }
    for (k = 0; k <= 31; ++k)
    {
        q64[(k*k) % 64] = 1;
    }

    /* Fill 65 */
    for (k = 0; k <= 64; ++k)
    {
        q65[k] = 0;
    }
    for (k = 0; k <= 32; ++k)
    {
        q65[(k*k) % 65] = 1;
    }
}

unsigned long square_test(unsigned long n)
{
    unsigned long t = n % 64;
    if (q64[t] == 0) return 0;
    unsigned long r = n % 45045;
    if (q63[r % 63] == 0) return 0;
    if (q65[r % 65] == 0) return 0;
    if (q11[r % 11] == 0) return 0;
    unsigned long q = integerSqrt(n);
    return n == q*q ? q : 0;
}

void test()
{
    init_square_test();
    for (unsigned int n = 0; n <= 10000; ++n)
        printf("Square test: %lu\n", square_test(n));
}

int main()
{
    init_square_test();

    unsigned long x, y, z;
    int found = 0;
    x = 0;
    while (!found) {
        ++x;
        fprintf(stderr, "Testing x = %lu\n", x);
        for (y = 2; y < x; ++y) {
            if (square_test(x-y) == 0 || square_test(x+y) == 0)
                continue;
            for (z = 1; z < y; ++z) {
                if (square_test(x-z) != 0 && square_test(x+z) != 0 && square_test(y-z) != 0 && square_test(y+z) != 0) {
                    printf("Found x = %lu, y = %lu, z = %lu\n", x, y, z);
                    printf("    with sum x+y+z = %lu\n", x + y + z);
                    found = 1;
                    break;
                }
            }
        }
    }
    return 0;
}
