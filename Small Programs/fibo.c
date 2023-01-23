/******************************************************************************
 *  Compilation:  cc -O2 -o fibo fibo.c -lgmp
 *  Execution:    ./fibo n...
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

static void fibonacci(mpz_t fib, long n)
{
    if (n == 0) {
        mpz_set_ui(fib, 0);
    } else if (n < 0) {
        fibonacci(fib, -n);
        if (n % 2 == 0) {
            mpz_neg(fib, fib);
        }
    } else {
        mpz_fib_ui(fib, n);
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
