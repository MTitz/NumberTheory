#include <algorithm>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iostream>
#include <vector>

using std::cerr;
using std::cout;
using std::endl;

static std::vector<int> a;

static void recursivePartition(int m, int B, int N)
{
    if (m == 0) {
        for (int i = 0; i < N; ++i) {
            cout << a[i];
            if (i < N-1)
                cout << " ";
        }
        cout << endl;
    } else {
        for (int i = 1; i <= std::min(B, m); ++i) {
            a[N] = i;
            recursivePartition(m-i, i, N+1);
        }
    }
}

static void generatePartitions(int m)
{
    a.clear();
    a.reserve(m);
    recursivePartition(m, m, 0);
}

static char *progname;

static void usage()
{
    cout << "usage: " << progname << " n" << endl;
}

int main(int argc, char *argv[])
{
    char *ptmp;
    progname = argv[0];
    if ((ptmp = strrchr(progname, '/')))
        progname = ptmp + 1;

    if (argc != 2) {
        usage();
        return 1;
    }

    int n = std::atoi(argv[1]);

    try {
        generatePartitions(n);
    }
    catch (std::exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
        return 1;
    }
    return 0;
}
