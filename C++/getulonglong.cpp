#include <cstdlib>
#include <cstring>
#include <stdexcept>

#include "getulonglong.h"

unsigned long long getulonglong(const char *str)
{
    char *ptr;
    unsigned long long n = std::strtoull(str, &ptr, 10);
    if (errno != 0)
        throw std::out_of_range(strerror(errno));
    if (*ptr != '\0')
        throw std::invalid_argument("Not a long");
    return n;
}
