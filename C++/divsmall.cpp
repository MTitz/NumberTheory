#include <algorithm>
#include <cmath>
#include <cstdlib>
#include <cstring>
#include <exception>
#include <iomanip>
#include <iostream>
#include <map>

#include "numbertheory.h"
#include "divrules.h"

using std::cerr;
using std::cout;
using std::endl;
using std::left;
using std::right;
using std::setw;

using count_type = unsigned long long;

constexpr count_type complete_lines = 10000UL;

static int* partition;
static double* log_primes;
static double log_limit;

struct exponent_info {
    double log;
    int* exponents;
    std::size_t n;
};

static count_type n_partitions, n_inserts, n_updates, n_ignored;

static std::map<count_type, exponent_info> divisor_first;

static inline count_type divisor_count(const int* exponents, std::size_t n)
{
    count_type count = 1;
    for (std::size_t i = 0; i < n; ++i) {
        count *= (count_type)(exponents[i] + 1);
    }
    return count;
}

std::string toString(const int* exponents, std::size_t n)
{
    std::stringstream ss;
    if (n == 0) {
        ss << 1;
    }
    for (std::size_t i = 0; i < n; ++i) {
        if (i > 0)
            ss << '*';
        ss << math::primes[i];
        if (exponents[i] != 1)
            ss << '^' << exponents[i];
    }
    return ss.str();
}

static void process_partition(const int* exponents, std::size_t n, double log_value)
{
    ++n_partitions;
    count_type number_of_divisors = divisor_count(exponents, n);

    // Check if not yet any entry for number_of_divisors is in table
    // or if entry exists, but value in table is higher than this one.
    // Then add / update the table entry.
    bool add_entry = divisor_first.count(number_of_divisors) == 0;
    bool replace_entry = !add_entry && divisor_first[number_of_divisors].log > log_value;
    if (add_entry || replace_entry) {
        divisor_first[number_of_divisors].log = log_value;
        divisor_first[number_of_divisors].n = n;
        if (replace_entry) {
            delete[] divisor_first[number_of_divisors].exponents;
            ++n_updates;
        } else {
            ++n_inserts;
        }
        divisor_first[number_of_divisors].exponents = new int[n];
        for (std::size_t i = 0; i < n; ++i) {
            divisor_first[number_of_divisors].exponents[i] = exponents[i];
        }
    } else {
        ++n_ignored;
    }
}

static void recursive_partition(int m, int B, int N, double log_sum)
{
    if (log_sum < log_limit) {
        if (m == 0) {
            process_partition(partition, (std::size_t)N, log_sum);
        } else {
            for (int i = 1; i <= std::min(B, m); ++i) {
                partition[N] = i;
                recursive_partition(m-i, i, N+1, log_sum + i * log_primes[N]);
            }
        }
    }
}

static void generate_partitions(int m)
{
    partition = new int[(std::size_t)m];
    recursive_partition(m, m, 0, 0.0);
    delete[] partition;
    cout << "length " << setw(3) << m << setw(20) << n_partitions << setw(12) << n_inserts << setw(12) << n_updates << setw(16) << n_ignored << endl;
}

static void table_line(const count_type& K, const exponent_info* N)
{
    const int FactorOutputWidth = 24;
    math::PrimeFactors<count_type> primeFactors(K);
    std::pair<bool, math::PrimeFactors<count_type>> rulesResult = applyRules(K);
    bool foundSieve = N != nullptr;
    bool foundRules = rulesResult.first;
    std::string sieve_factor_string = foundSieve ? toString(N->exponents, N->n) : "";
    std::string rules_factor_string = foundRules ? rulesResult.second.toString() : "";

    char kind;
    if (foundSieve) {
        if (foundRules) {
            // Check if both results match
            if (sieve_factor_string == rules_factor_string) {
                kind = 'B';
            } else {
                kind = '!';
                cerr << "Mismatch found for " << K << ": " << sieve_factor_string << " != " << rules_factor_string << endl;
            }
        } else {
            kind = 'S';
        }
    } else if (foundRules) {
        kind = 'R';
    } else {
        kind = '-';
    }
    cout << right << setw(12) << K << " = " << left << setw(FactorOutputWidth) << primeFactors << right << " " << kind;

    if (foundSieve || foundRules) {
        cout << setw(20);
        if (foundSieve) {
            double value = std::exp(N->log);
            cout << value << " = ";
        } else {
            cout << " ";
            cout << "   ";
        }
        cout << (foundSieve ? sieve_factor_string : rules_factor_string);
    }

    cout << right << endl;
}

static void table_results(count_type complete_until)
{
    for (count_type k = 1; k <= complete_until; ++k) {
        table_line(k, divisor_first.count(k) ? &divisor_first[k] : nullptr);
    }
    typename std::map<count_type, exponent_info>::const_iterator iter = divisor_first.begin();
    while (iter != divisor_first.end()) {
        if (iter->first > complete_until) {
            table_line(iter->first, &(iter->second));
        }
        ++iter;
    }
}

static void table_records()
{
    /* Determine list of records */
    count_type previousRecordNumber = 0;
    double previousRecordValue = -1.0;
    bool foundRecord;
    int counter = 0;

    do {
        count_type recordNumber = 0;
        double recordValue = -1.0;
        typename std::map<count_type, exponent_info>::const_iterator iter = divisor_first.begin();
        while (iter != divisor_first.end()) {
            auto value = iter->second.log;
            if (value > previousRecordValue && iter->first > previousRecordNumber && (recordValue < 0.0 || value <= recordValue)) {
                recordNumber = iter->first;
                recordValue = value;
            }
            ++iter;
        }
        foundRecord = (recordValue > previousRecordValue);
        if (foundRecord) {
            cout << setw(6) << ++counter << setw(15) << recordNumber << setw(21) << std::exp(recordValue) << endl;
            previousRecordNumber = recordNumber;
            previousRecordValue = recordValue;
        }
    } while (foundRecord);
}

static void generate_data(int n)
{
    if (n <= 0) {
        cerr << "Parameter must be an positive integer." << endl;
        return;
    }

    // Initialization
    if ((std::size_t)n > math::n_primes) {
        cerr << "Not sufficient pre-calculated prime numbers available." << endl;
        return;
    }
    log_primes = new double[(std::size_t)n];
    for (int i = 0; i < n; ++i) {
        log_primes[i] = std::log(math::primes[i]);
    }
    log_limit = (n+1) * log_primes[0];
    cout << std::setprecision(12) << "log_limit = " << log_limit << endl
         << "limit = " << std::exp(log_limit) << endl << endl;

    divisor_first.clear();
    cout << endl;
    n_partitions = n_inserts = n_updates = n_ignored = 0;

    // Going through partitions of numbers 1, 2, ..., n
    for (int m = 0; m <= n; ++m) {
        generate_partitions(m);
    }

    // Results
    cout << endl;
    cout << "Number of entries in table: " << divisor_first.size() << endl;
    cout << "Maximum number of divisors: " << divisor_first.rbegin()->first << endl << endl;
    table_results(std::min(divisor_first.rbegin()->first, complete_lines));
    cout << endl << endl;
    table_records();

    // Cleaning up
    delete[] log_primes;
    for (auto d : divisor_first) {
        delete[] d.second.exponents;
    }
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
        generate_data(n);
    }
    catch (std::exception& e) {
        cerr << "Error in " << progname << ": " << e.what() << endl;
        return 1;
    }
    return 0;
}
