#include <cerrno>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <limits>
#include <stdexcept>

using namespace std;

static const int linelength = 72;

int getint(const char *str)
{
    char *ptr;
    long l = strtol(str, &ptr, 10);
    if (l < numeric_limits<int>::min() || l > numeric_limits<int>::max())
        throw out_of_range("out of int range");
    int n = static_cast<int>(l);
    if (errno != 0)
        throw out_of_range(strerror(errno));
    if (*ptr != '\0')
        throw invalid_argument("not an int");
    return n;
}
