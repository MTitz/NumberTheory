#include <cstdlib>
#include <cstring>
#include <stdexcept>

#include "getulong.h"

unsigned long getulong(const char *str)
{
    char *ptr;
    unsigned long n = std::strtoul(str, &ptr, 10);
    if (errno != 0)
        throw std::out_of_range(strerror(errno));
    if (*ptr != '\0')
        throw std::invalid_argument("Not a long");
    return n;
}
