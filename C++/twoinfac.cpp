#include <cerrno>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iostream>
#include <stdexcept>

#include "numbertheory.h"

using namespace std;

int main(int argc, char *argv[])
{
    for (int i = 1; i < argc; ++i) {
        try {
            char *ptr;
            errno = 0;
            unsigned long n = strtoul(argv[i], &ptr, 10);
            if (errno == 0) {
                if (*ptr != '\0')
                    throw invalid_argument("Not an integer");
                unsigned long m = math::primefactor_in_factorial(n, 2UL);
                cout << "twoinfac(" << n << ") = " << m << endl;
            } else {
                throw out_of_range(strerror(errno));
            }
        }
        catch (exception& e) {
            cerr << "Error for '" << argv[i] << "': " << e.what() << endl;
        }
    }
}
