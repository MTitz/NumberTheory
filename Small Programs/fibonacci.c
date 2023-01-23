/******************************************************************************
 *  Compilation:  cc -O2 -o fibonacci fibonacci.c -lgmp
 *  Execution:    ./fibonacci n...
 *
 *  Uses the GMP library, see https://gmplib.org/
 *
 *  Reads in integer numbers n as command line arguments and calculates the
 *  respective Fibonacci numbers F_n.
 *
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>

#include <gmp.h>

static int bitlength(long x)
{
    long var = x >= 0 ? x : -x;
    int bits = 0;
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

static void fibonacci(mpz_t fib, long n)
{
    if (n == 0) {
        mpz_set_ui(fib, 0);
    } else if (n == 1) {
        mpz_set_ui(fib, 1);
    } else if (n < 0) {
        fibonacci(fib, -n);
        if (n % 2 == 0) {
            mpz_neg(fib, fib);
        }
    } else {
        mpz_t x, y;
        mpz_init_set_ui(x, 1);
        mpz_init_set_ui(y, 0);
        mpz_t xx, xy, xy2, yy;
        mpz_inits(xx, xy, xy2, yy, 0);
        int bit_length = bitlength(n);
        for (int k = bit_length-2; k >= 0; --k) {
            mpz_mul(xx, x, x);
            mpz_mul(xy, x, y);
            mpz_mul_ui(xy2, xy, 2);
            mpz_add(x, xx, xy2);
            mpz_mul(yy, y, y);
            mpz_add(y, xx, yy);
            if (((1 << k) & n) != 0) {
                mpz_t temp;
                mpz_init(temp);
                mpz_set(temp, x);
                mpz_add(x, x, y);
                mpz_set(y, temp);
                mpz_clear(temp);
            }
        }

        mpz_clears(xx, xy, xy2, yy, 0);
        mpz_set(fib, x);
        mpz_clear(x);
        mpz_clear(y);
    }
}

int main(int argc, char *argv[])
{
    mpz_t fib;
    mpz_init(fib);
    for (int i = 1; i < argc; ++i) {
        long n = atol(argv[i]);
        fibonacci(fib, n);
        printf("fib(%ld) = ", n);
        gmp_printf("%Zd\n", fib);
    }
    mpz_clear(fib);
    return 0;
}
