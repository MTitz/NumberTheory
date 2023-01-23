#include <cmath>
#include <iomanip>
#include <iostream>

#include "numbertheory.h"

int printPrimeTable(int limit)
{
    int count = 0;
    int width = static_cast<int>(std::ceil(std::log10(limit))) + 1;
    for (int i = 2; i <= limit; ++i) {
        if (math::isPrime(i)) {
            if (count % 10 == 0)
                std::cout << std::endl;
            ++count;
            std::cout << std::setw(width) << i;
        }
    }
    std::cout << std::endl;
    return count;
}

int main(int argc, char *argv[])
{
    for (int i = 1; i < argc; ++i) {
        printPrimeTable(std::atoi(argv[i]));
    }
}
