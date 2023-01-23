#include <algorithm>
#include <iomanip>
#include <stdexcept>

#include "bigunsigned.h"
//#include "showSequence.h"

using std::cout;
using std::endl;

namespace math
{

bigunsigned::bigunsigned(int n)
{
    if (n < 0)
        throw std::runtime_error("No negative number possible.");
    digit.reserve(1);
    digit.push_back((digit_t)n);
}

bigunsigned::bigunsigned(unsigned int n)
{
    digit.reserve(1);
    digit.push_back(n);
}

bigunsigned::bigunsigned(unsigned long n)
{
    digit.reserve(1);
    digit.push_back(n);
}

bigunsigned::bigunsigned(bigunsigned::digit_t n)
{
    digit.reserve(1);
    digit.push_back(n);
}

std::string bigunsigned::toString() const {
    std::stringstream ss;
    const auto n = digit.size();
    if (n == 0) {
        ss << 0;
    } else {
        ss << digit[n-1] << std::setfill('0');
        for (long long i = n-2; i >= 0; --i) {
            ss << std::setw(blocksize) << digit[i];
        }
    }


/***

static void print_long_digital(const digit_t *number, int blocksize, int digits)
{
    printf("%5llu.", number[0]);

    for (int i = 1; i <= digits; ++i)
    {
        // extract i-th decimal digit from the digit blocks and write it
        int block_index = (i + blocksize-1) / blocksize;
        digit_t b = number[block_index];
        int n_divides = blocksize - (i - blocksize * (block_index-1));
        for (int j = 0; j < n_divides; ++j)
        {
            b /= 10;
        }
        b %= 10;
        printf("%1llu", b);

        if (i == digits) {
            putchar('\n');
        } else if (i % 5 == 0) {
            if (i % 50 == 0) {
                printf("\n      ");
            } else {
                putchar(' ');
            }
        }
    }
}
***/
    return ss.str();
}

bool operator==(const bigunsigned& lhs, const bigunsigned& rhs)
{
    const auto lhs_size = lhs.digit.size();
    const auto rhs_size = rhs.digit.size();
    if (lhs_size != rhs_size)
        return false;
    for (typename std::vector<bigunsigned::digit_t>::size_type i = 0; i < lhs_size; ++i) {
        if (lhs.digit[i] != rhs.digit[i])
            return false;
    }
    return true;
}

// return -1 if lhs < rhs
// return  0 if lhs == rhs
// return  1 if lhs > rhs
int cmp(const bigunsigned& lhs, const bigunsigned& rhs)
{
    const auto lhs_size = lhs.digit.size();
    const auto rhs_size = rhs.digit.size();
    if (lhs_size < rhs_size)
        return -1;
    else if (lhs_size > rhs_size)
        return 1;
    for (typename std::vector<bigunsigned::digit_t>::size_type i = lhs_size; i <= lhs_size; --i) {
        if (lhs.digit[i] < rhs.digit[i])
            return -1;
        else if (lhs.digit[i] > rhs.digit[i])
            return 1;
    }
    return 0;
}

const bigunsigned operator+(const bigunsigned& lhs, const bigunsigned& rhs)
{
    const auto lhs_size = lhs.digit.size();
    const auto rhs_size = rhs.digit.size();
    const auto n = std::max(lhs_size, rhs_size);
    bigunsigned::digit_t overflow = 0;
    bigunsigned sum;
    if (n > 1)
        sum.digit.reserve(n);
    for (typename std::vector<bigunsigned::digit_t>::size_type i = 0; i < n; ++i) {
        bigunsigned::digit_t s = overflow;
        if (i < lhs_size)
            s += lhs.digit[i];
        if (i < rhs_size)
            s += rhs.digit[i];
        sum.digit[i] = s % bigunsigned::base;
        overflow     = s / bigunsigned::base;
    }
    if (overflow > 0)
        sum.digit.push_back(overflow);
    //showSequence(sum.digit);
    return sum;
}

} /* end of namespace math */
