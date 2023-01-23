// Project Euler Problem 668
// Square root smooth Numbers
// A positive integer is called square root smooth if all of its prime factors are strictly less than its square root.
// Including the number 1, there are 29 square root smooth numbers not exceeding 100.
// How many square root smooth numbers are there not exceeding 10000000000?

#include "numbertheory.h"

using std::cout, std::endl;

template <class T>
bool isSquareRootSmooth(T n)
{
    if (n == 1)
        return true;
    math::PrimeFactors<T> primeFactors(n);
    auto nFactors = primeFactors.nFactors();
    auto largestFactor = primeFactors.factors(nFactors-1).base();
    return largestFactor * largestFactor < n;
}

template <class T>
T countSquareRootSmoothNumbers(T a, T b)
{
    T count {0};
    for (auto n = a; n <= b; ++n)
    {
        if (isSquareRootSmooth(n))
            ++count;
        if (n % 10000000 == 0)
            cout << "        " << "From " << a << " to " << n << ": " << count << endl;

    }
    return count;
}

int main()
{
    auto count100 = countSquareRootSmoothNumbers(1, 100);
    cout << "Square root smooth numbers until 100: " << count100 << endl;
    auto count1000000 = countSquareRootSmoothNumbers(1, 1000000);
    cout << "Square root smooth numbers until 1000000: " << count1000000 << endl;

    cout << endl << "Starting big count:" << endl;
    unsigned long limit1 = 4290000000UL;
    auto part1 = countSquareRootSmoothNumbers(1UL, limit1);
    cout << "    Part 1 until " << limit1 << ":  " << part1 << endl;
    unsigned long long limit2 = static_cast<unsigned long long>(limit1) + 1ULL;
    auto part2 = countSquareRootSmoothNumbers(limit2, 10000000000ULL);
    unsigned long long sum = part1 + part2;
    cout << "    Part 2 from  " << limit2 << ":  " << part2 << endl;
    cout << "Total: " << sum << endl;
    return 0;
}
