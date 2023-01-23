#include <cerrno>
#include <cmath>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iomanip>
#include <iostream>
#include <locale>
#include <stdexcept>

using namespace std;

void print_solutions(int solutions)
{
    int n = 2*solutions-1;
    long *P = new long[n+1];
    long *Q = new long[n+1];
    const long double sqrt2 = sqrt(2.0L);
    P[0] = 1;
    Q[0] = 1;
    P[1] = 3;
    Q[1] = 2;
    for (int k = 2; k <= n; ++k) {
        P[k] = 2 * P[k-1] + P[k-2];
        Q[k] = 2 * Q[k-1] + Q[k-2];
    }
    cout << "Nr.        Zähler        Nenner"
            "     Quotient            Quotient-sqrt(2)\n\n";
    cout.precision(15);
    cout.setf(ios_base::fixed, ios_base::floatfield);
    for (int k = 0; k <= n; ++k) {
        long double p = P[k];
        long double q = Q[k];
        cout << setw(3) << k
             << setw(14) << P[k] << setw(14) << Q[k]
             << setw(22) << p/q << setw(21) << p/q-sqrt2 << endl;
    }
    cout << "\n\nNr. Ref       Dreieck       Quadrat"
            "                     Fläche\n\n";
    for (int i = 1; i <= solutions; ++i) {
        int k = 2*i-1;
        long drei = (P[k]-1)/2;
        long quad = Q[k]/2;
        long long flaeche = (long long)quad * (long long)quad;
        cout << setw(3) << i << setw(4) << k
             << setw(14) << drei << setw(14) << quad
             << setw(27) << flaeche << endl;

    }
    delete[] P;
    delete[] Q;
}

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
    cout << "usage: " << progname << " n\n";
}

int main(int argc, char *argv[])
{
    char *ptmp;
    progname = argv[0];
    if (ptmp = strrchr(progname, '/'))
        progname = ptmp + 1;

    try {
        locale loc("de_DE.utf8");
        cout.imbue(loc);
    }
    catch (exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
    }

    if (argc != 2) {
        usage();
        return 1;
    }

    try {
        errno = 0;
        int n = getlong(argv[1]);
        if (n > 12)
            n = 12;
        print_solutions(n);
    }
    catch (exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
        return 1;
    }
    return 0;
}
