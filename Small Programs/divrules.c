#include <math.h>
#include <stdio.h>
#include <stdlib.h>

void usage()
{
    printf("Aufruf: divrules limit\n        divrules base limit\n");
}

int isprime(int n)
{
    int i, limit;
    if (n < 2)
        return 0;
    if (n == 2)
        return 1;
    if (n % 2 == 0)
        return 0;
    limit = (int)sqrt(n);
    for (i = 3; i <= limit; i += 2) {
        if (n % i == 0)
            return 0;
    }
    return 1;
}

int nextprime(int n)
{
    if (n <= 2)
        return 2;
    if (n % 2 == 0)
        ++n;
    while (!isprime(n))
        n += 2;
    return n;
}

void divisibilitytests(int base, int limit)
{
    int p = 2;
    while (p <= limit) {
        if (base % p == 0) {
            /* last digit rule */
            int i;
            printf("Regel für %4d: letzte Ziffer aus", p);
            for (i = 0; i < base; ++i) {
                if (i % p == 0) {
                    printf(" %d", i);
                }
            }
        } else {
            int power = 1;        /* power modulo p */
            int indexM1 = -1;
            int indexP1 = -1;
            int n;
            for (n = 1; n <= p-1; ++n) {
                /* p divides n**(p-1)-1, so limit p-1 is sufficient */
                power *= base;
                power %= p;
                if ((power+1) % p == 0) {
                    indexP1 = n;
                }
                if ((power-1) % p == 0) {
                    indexM1 = n;
                    break;
                }
            }
            printf("Regel für %4d: Q%d", p, indexM1);
            if (indexP1 != -1)
                printf(" oder A%d", indexP1);
        }
        puts("");

        p = nextprime(++p);
    }
}

int main(int argc, char *argv[])
{
    int base  = 10;
    int limit = 1000;
    if (argc == 2)
        limit = atoi(argv[1]);
    else if (argc == 3) {
        base  = atoi(argv[1]);
        limit = atoi(argv[2]);
    } else {
        usage();
        return 1;
    }
    if (base >= 2) {
        divisibilitytests(base, limit);
        return 0;
    } else {
        usage();
        return 1;
    }
}
