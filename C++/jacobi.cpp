#include <cerrno>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iostream>
#include <stdexcept>

#include "numbertheory.h"

using namespace std;

static long getlong(const char *str)
{
    char *ptr;
    long n = strtol(str, &ptr, 10);
    if (errno != 0)
        throw out_of_range(strerror(errno));
    if (*ptr != '\0')
        throw invalid_argument("Not a long");
    return n;
}

static char *progname;

static void usage()
{
    cout << "usage: " << progname << " a b\n";
}

int main(int argc, char *argv[])
{
    char *ptmp;
    progname = argv[0];
    if ((ptmp = strrchr(progname, '/')))
        progname = ptmp + 1;

    if (argc != 3) {
        usage();
        return 1;
    }

    try {
        errno = 0;
        long a = getlong(argv[1]);
        long b = getlong(argv[2]);
        long k = math::jacobi(a, b);
        cout << "jacobi(" << a << ", " << b  << ") = " << k << endl;
    }
    catch (exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
        return 1;
    }
    return 0;
}
