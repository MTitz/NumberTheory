#include <cstdlib>
#include <cstring>
#include <stdexcept>

#include "getlong.h"

long getlong(const char *str)
{
    char *ptr;
    long n = std::strtol(str, &ptr, 10);
    if (errno != 0)
        throw std::out_of_range(strerror(errno));
    if (*ptr != '\0')
        throw std::invalid_argument("Not a long");
    return n;
}
