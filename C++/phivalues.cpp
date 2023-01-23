#include <cerrno>
#include <cmath>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iomanip>
#include <iostream>
#include <limits>
#include <stdexcept>

#include "numbertheory.h"

using namespace std;

static const int linelength = 72;

static int getint(const char *str)
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

static char *progname;

static void usage()
{
    cout << "usage: " << progname << " n   (1 <= n <= 9)\n";
}

static void phitable(int n)
{
    unsigned long P = 1UL;
    for (int i = 0; i < n; ++i)
        P *= static_cast<unsigned long>(math::primes[i]);
    unsigned long phiP = math::phi(P);
    cout << "phi(" << P << ") = " << phiP << endl << endl;

    unsigned long *minarg   = new unsigned long[phiP + 1];
    unsigned long *maxarg   = new unsigned long[phiP + 1];
    unsigned long *countarg = new unsigned long[phiP + 1];
    for (unsigned long i = 0; i <= phiP; ++i)
        countarg[i] = 0;

    for (unsigned long k = 1; k <= P; ++k) {
        unsigned long phi = math::phi(k);
        if (phi <= phiP) {
            if (countarg[phi] == 0)
                minarg[phi] = k;
            ++countarg[phi];
            maxarg[phi] = k;
        }
    }

    int w = static_cast<int>(ceil(log10((double)P))) + 2;
    w = max(w, 4);
    cout << setw(w) << (w <= 4 ? "val" : "value") << setw(w) << "#"
                    << setw(w) << "min" << setw(w) << "max" << endl << endl;
    for (unsigned long i = 0; i <= phiP; ++i) {
        if (countarg[i] > 0) {
            cout << setw(w) << i << setw(w) << countarg[i]
                 << setw(w) << minarg[i] << setw(w) << maxarg[i] << endl;
        }
    }
    cout << endl << "Even nontotients:" << endl;
    int charsum = 0;
    for (unsigned long i = 2; i <= phiP; i+=2) {
        if (countarg[i] == 0) {
            cout << setw(w) << i;
            charsum += w;
            if (charsum + w > linelength) {
                cout << endl;
                charsum = 0;
            }
        }
    }
    if (charsum > 0) {
        cout << endl;
    }

    delete[] countarg;
    delete[] maxarg;
    delete[] minarg;
}

int main(int argc, char *argv[])
{
    char *ptmp;
    progname = argv[0];
    if ((ptmp = strrchr(progname, '/')))
        progname = ptmp + 2;

    if (argc != 2) {
        usage();
        return 1;
    }

    try {
        errno = 0;
        int n = getint(argv[1]);
        cout << "n = " << n << endl;
        if (n >= 1 && (std::size_t)n <= math::n_primes && n <= 9)
            phitable(n);
        else {
            usage();
            return 1;
        }
    }
    catch (exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
        return 1;
    }
    return 0;
}
