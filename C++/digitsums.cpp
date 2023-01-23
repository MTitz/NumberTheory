#include <cstdio>
#include <cstdlib>
#include <list>

#include "numbertheory.h"

using std::printf;

int main(int argc, char *argv[])
{
    if (argc != 2) {
        printf("Aufruf: %s limit\n", argv[0]);
        return 1;
    }
    int limit = std::atoi(argv[1]);
    if (limit <= 0) {
        return 1;
    }
    std::list<int> equal_sum;
    int max_difference = 0;
    for (int n = 0; n <= limit; ++n) {
        int sum2  = math::sum_of_digits(n, 2);
        int sum10 = math::sum_of_digits(n, 10);
        if (sum2 == sum10) {
            equal_sum.push_back(n);
        }
        int diff = abs(sum2 - sum10);
        if (diff > max_difference) {
            printf("New max. difference of %3d for %10d:  %3d %3d\n", diff, n, sum2, sum10);
            max_difference = diff;
        }
    }
    printf("\nEqual digit sums to base 2 and base 10:\n");
    int count = 0;
    for (auto it = equal_sum.cbegin(); it != equal_sum.cend(); ++it) {
        if (count++ % 8 == 0)
            printf("\n");
        printf("%10d", *it);
    }
    printf("\n");
    return 0;
}
