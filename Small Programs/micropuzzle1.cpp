/* Program micropuzzle1.c to solve micropuzzle 1 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

#include <cmath>
#include <cstdlib>
#include <cstring>
#include <iomanip>
#include <iostream>
#include <set>

using std::setw;

const double eps = 1.0e-8;

template<typename T>
bool is_square(T n)
{
    double x = n;
    T root = std::floor(std::sqrt(x) + eps);
    return root * root == n;
}

template<typename T>
bool is_cube(T n)
{
    double x = n;
    T root = std::floor(std::cbrt(x) + eps);
    return root * root * root == n;
}

template<typename T>
T gcd(T m, T n)
{
    while (n != 0) {
        T tmp = n;
        n = m % n;
        m = tmp;
    }
    return m;
}

class pythagorean_triple
{
    public:
        pythagorean_triple(int a, int b, int c)
        {
            if (a < b) {
                _a = a;
                _b = b;
            } else {
                _a = b;
                _b = a;
            }
            _c = c;
        }

        int a() const { return _a; }
        int b() const { return _b; }
        int c() const { return _c; }

    private:
        int _a;
        int _b;
        int _c;
};

bool operator<(const pythagorean_triple& lhs, const pythagorean_triple& rhs)
{
    return lhs.c() < rhs.c() || (lhs.c() == rhs.c() && lhs.b() < rhs.b());
}


/* Generates all Pythagorean triples (a,b,c) with a^2+b^2=c^2
   and perimeter a square and area a cube until a given limit */

using triple_set = std::set<pythagorean_triple>;

triple_set search_solutions(int limit)
{
    triple_set triples;
    for (int m = 1; m * m <= limit; ++m) {
        for (int n = (m & 1) + 1; n < m; n += 2) {
            if (gcd(m, n) == 1) {
                int a_base = m*m - n*n;
                int b_base = 2 * m * n;
                int c_base = m*m + n*n;
                if (c_base <= limit) {
                    int lambda = 1;
                    while (lambda * c_base <= limit) {
                        int a = a_base * lambda;
                        int b = b_base * lambda;
                        int c = c_base * lambda;
                        long long perimeter = (long long)a + b + c;
                        if (is_square(perimeter)) {
                            long long area = ((long long)a * (long long)b) / 2;
                            if (is_cube(area)) {
                                triples.insert(pythagorean_triple(a, b, c));
                            }
                        }
                        ++lambda;
                    }
                } else {
                    break;
                }
            }
        }
    }
    return triples;
}

static char *progname;

static void usage()
{
    std::cout << "usage: " << progname << " limit" << std::endl;
}

int main(int argc, char *argv[])
{
    char *ptmp;
    progname = argv[0];
    if ((ptmp = strrchr(progname, '/')))
        progname = ptmp + 1;

    if (argc < 2) {
        usage();
        return 1;
    }
    if (std::strcmp(argv[1], "--help") == 0 || std::strcmp(argv[1], "-h") == 0) {
        usage();
        return 0;
    }

    int limit = std::atoi(argv[1]);
    if (limit <= 0) {
        std::cerr << "limit should be positive, aborting..." << std::endl;
        return 1;
    }
    if (limit > 1000000000) {
        std::cerr << "limit too big for integer calculation, aborting..." << std::endl;
        return 1;
    }

    std::cout << "All solutions with hypotenuse <= " << limit << std::endl << std::endl;

    triple_set all_triples = search_solutions(limit);

    for (auto triple : all_triples) {
        int a = triple.a();
        int b = triple.b();
        int c = triple.c();
        long long perimeter = (long long)a + b + c;
        long long area = ((long long)a * (long long)b) / 2;
        const int width = 12;
        std::cout << setw(width) << a << setw(width) << b << setw(width) << c
            << "    " << setw(width) << perimeter
            << "  " << setw(2*width) << area << std::endl;
    }
    return 0;
}
