/* Program Micropuzzle36.cpp to solve micropuzzle 36 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

#include <cmath>
#include <iostream>
#include <vector>

#include "numbertheory.h"

using std::cout;
using std::endl;

using namespace math;

const double eps = 1.0e-8;

template<typename T>
bool is_cube(T n)
{
    double x = static_cast<double>(n);
    T root = static_cast<T>(std::floor(std::cbrt(x) + eps));
    return root * root * root == n;
}

int main(void)
{
    std::vector<long long> div = divisors(2000000LL);
    for (auto d : div) {
        if (is_cube(d * d + 2000000LL)) {
            cout << "Solution: factor = " << d << endl;
        }
    }
}
