/* Generates all Pythagorean triples (a,b,c) with a^2+b^2=c^2
   until a given limit for the hypotenuse c */

#include <cstdlib>
#include <cstring>
#include <iomanip>
#include <iostream>
#include <set>

using std::cout;
using std::endl;


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


using triple_set = std::set<pythagorean_triple>;

static triple_set generate_triples(int limit, bool primitive_only = true)
{
    triple_set triples;
    for (int m = 1; m * m <= limit; ++m) {
        for (int n = (m & 1) + 1; n < m; n += 2) {
            if (gcd(m, n) == 1) {
                int a = m*m - n*n;
                int b = 2 * m * n;
                int c = m*m + n*n;
                if (c <= limit) {
                    if (primitive_only) {
                        triples.insert(pythagorean_triple(a, b, c));
                    } else {
                        int lambda = 1;
                        while (lambda * c <= limit) {
                            triples.insert(pythagorean_triple(a * lambda, b * lambda, c * lambda));
                            ++lambda;
                        }
                    }
                } else {
                    break;  // abort inner for loop
                }
            }
        }
    }
    return triples;
}

static char *progname;

static void usage()
{
    cout << "usage: " << progname << " [-a|--all] [-p|--primitive] limit" << endl
        << "  calculates the Pythagorean triples with hypotenuse <= limit" << endl
        << "    -a  or  --all         calculate all triples" << endl
        << "    -p  or  --primitive   calculate only primitive triples" << endl
        << "    limit                 a positive integer" << endl;
}

int main(int argc, char *argv[])
{
    bool primitive_triples_only = true;
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
    } else if (std::strcmp(argv[1], "--primitive") == 0 || std::strcmp(argv[1], "-p") == 0) {
        primitive_triples_only = true;
        --argc;
        ++argv;
    } else if (std::strcmp(argv[1], "--all") == 0 || std::strcmp(argv[1], "-a") == 0) {
        primitive_triples_only = false;
        --argc;
        ++argv;
    }
    if (argc != 2) {
        usage();
        return 1;
    }

    int limit = std::atoi(argv[1]);
    if (limit <= 0) {
        std::cerr << "limit should be positive, aborting..." << endl;
        return 1;
    }

    const int width = 9;

    cout << "All";
    if (primitive_triples_only)
        cout << " primitive";
    cout << " Pythagorean triples until " << limit << endl << endl;

    triple_set triples = generate_triples(limit, primitive_triples_only);

    for (auto triple : triples) {
        cout << std::setw(width) << triple.a() << std::setw(width) << triple.b() << std::setw(width) << triple.c() << endl;
    }

    return 0;
}
