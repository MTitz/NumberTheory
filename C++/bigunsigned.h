#ifndef BIGUNSIGNED_H
#define BIGUNSIGNED_H

#include <iostream>
#include <sstream>
#include <string>
#include <vector>

namespace math {


class bigunsigned
{
    public:
        typedef unsigned long long digit_t;

        bigunsigned() { digit.reserve(1); digit.push_back(static_cast<digit_t>(0)); }
        explicit bigunsigned(int n);
        explicit bigunsigned(unsigned int n);
        explicit bigunsigned(unsigned long n);
        explicit bigunsigned(digit_t n);

        std::string toString() const;

    private:
        static const int blocksize = 9;
        static const digit_t base = 1000000000ULL;

        std::vector<bigunsigned::digit_t> digit;

    friend bool operator==(const bigunsigned& lhs, const bigunsigned& rhs);
    friend bool operator!=(const bigunsigned& lhs, const bigunsigned& rhs);
    friend int cmp(const bigunsigned& lhs, const bigunsigned& rhs);

    friend const bigunsigned operator+(const bigunsigned& lhs, const bigunsigned& rhs);
    friend const bigunsigned operator-(const bigunsigned& lhs, const bigunsigned& rhs);
    friend const bigunsigned operator*(const bigunsigned& lhs, const bigunsigned& rhs);

    friend const bigunsigned operator+=(const bigunsigned& lhs, const bigunsigned& rhs);
    friend const bigunsigned operator-=(const bigunsigned& lhs, const bigunsigned& rhs);
    friend const bigunsigned operator*=(const bigunsigned& lhs, const bigunsigned& rhs);

    friend bool even(const bigunsigned& n);
    friend bool odd(const bigunsigned& n);
};


inline bool operator!=(const bigunsigned& lhs, const bigunsigned& rhs)
{
    return !(lhs == rhs);
}

inline bool operator<(const bigunsigned& lhs, const bigunsigned& rhs)
{
    return cmp(lhs, rhs) == -1;
}

inline bool operator<=(const bigunsigned& lhs, const bigunsigned& rhs)
{
    return cmp(lhs, rhs) <= 0;
}

inline bool operator>(const bigunsigned& lhs, const bigunsigned& rhs)
{
    return cmp(lhs, rhs) == 1;
}

inline bool operator>=(const bigunsigned& lhs, const bigunsigned& rhs)
{
    return cmp(lhs, rhs) >= 0;
}

inline bool even(const bigunsigned& n)
{
    return (n.digit[0] & 1) == 0;
}

inline bool odd(const bigunsigned& n)
{
    return (n.digit[0] & 1) == 1;
}

inline std::ostream& operator<<(std::ostream& os, const bigunsigned& number)
{
    os << number.toString();
    return os;
}

} /* end of namespace math */

#endif
