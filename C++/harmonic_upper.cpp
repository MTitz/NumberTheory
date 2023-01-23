#include <iomanip>
#include <iostream>
#include <vector>

#include "harmonic.h"

std::vector<double> calc_harmonic(std::size_t n, std::size_t n_iter)
{
    if (n_iter > math::n_A)
        n_iter = math::n_A;
    std::vector<double> res(n_iter+1);
    res[0] = math::harmonic_series<double>(n);
    res[1] = math::euler + log(n) + 0.5/n;
    double prod = n;
    for (std::size_t k = 2; k <= n_iter; ++k) {
        prod *= n+k-1;
        res[k] = res[k-1] - math::A[k] / prod;
    }
    return res;
}

void print_harmonic(const std::vector<double>& v)
{
    for (std::size_t i = 1; i < v.size(); ++i) {
        std::cout << "N" << std::setw(2) << i << "    "
                  << std::fixed << v[i] << std::endl;
    }
    std::cout << "Exakt: " << std::fixed << v[0] << std::endl;
}

void test_number(int n, int n_iter = math::n_A)
{
    std::cout << "\nn = " << n << std::endl;
    print_harmonic(calc_harmonic(n, n_iter));
}

int main(void)
{
    std::cout.precision(12);
    std::cout.fill('0');
    for (int i = 1; i <= 20; ++i) {
        test_number(i);
    }
    test_number(100, 8);
    test_number(1000, 5);
    test_number(10000, 5);
    test_number(100000, 3);
    test_number(1000000, 3);
}
