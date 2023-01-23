#include <stdio.h>

static int digit_sum(int n, int base)
{
    int sum = 0;
    while (n != 0) {
        sum += n % base;
        n /= base;
    }
    return sum;
}

int main(void)
{
    for (int n = 1; n < 32; ++n) {
        int square = n * n;
        if (digit_sum(square, 10) != 16)
            continue;
        printf("%2d^2 = %3d\n", n, square);
    }
}
