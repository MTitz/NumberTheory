#include <cmath>
#include <iomanip>
#include <iostream>
#include <string>
#include <type_traits>
#include <utility>
#include <vector>

#include "getulong.h"
#include "getulonglong.h"
#include "numbertheory.h"
#include "divrules.h"

using std::cout;
using std::endl;
using std::left;
using std::right;
using std::setw;

using count_type = unsigned short;
using iter_type = typename std::vector<count_type>::size_type;

static void dumpTable(const std::string& name, const std::vector<count_type>& table, iter_type n)
{
    for (iter_type i = 1; i <= n; ++i) {
        cout << name << "(" << i << ") = " << table[i] << endl;
    }
    cout << endl;
}

static std::vector<iter_type> sieveDivisors(iter_type n)
{
    const iter_type limit = math::integerSqrt(n);

    std::vector<count_type> divisorCount(n+1);

    /* Sieve divisorCount */
    for (iter_type j = 1; j <= limit; ++j) {
        iter_type idx = j*j;
        ++divisorCount[idx];
        for (iter_type k = j+1; k <= n/j; ++k) {
            idx += j;
            divisorCount[idx] += 2;
        }
    }

    if (n <= 100) {
        dumpTable("d", divisorCount, n);
    }

    iter_type maxDivisorNumber = 2 * limit;
    std::vector<iter_type> divisorFirst(maxDivisorNumber+1);
    for (iter_type i = 1; i <= n; ++i) {
        if (divisorFirst[divisorCount[i]] == 0) {
            divisorFirst[divisorCount[i]] = i;
        }
    }

    while (maxDivisorNumber > 0 && divisorFirst[maxDivisorNumber] == 0) {
        --maxDivisorNumber;
    }
    divisorFirst.resize(maxDivisorNumber+1);
    divisorFirst.shrink_to_fit();
    return divisorFirst;
}

static void mainTable(const std::vector<iter_type>& divisorFirst)
{
    // Main Table:  K, N_sieve N_sieve_factors  N_rule_factors  N_factors
    //
    // case 1: found by sieve  found by rule
    // case 2: found by sieve  no rule
    // case 3: not in sieve    found by rule
    // case 4: not in sieve    not by rule
    iter_type maxDivisorNumber = divisorFirst.size()-1;
    for (iter_type K = 1; K <= maxDivisorNumber; ++K) {
        const int FactorOutputWidth = 21;
        math::PrimeFactors<iter_type> primeFactors(K);
        cout << right << setw(5) << K << " = " << left << setw(FactorOutputWidth-6) << primeFactors << right << " " << setw(12);
        std::pair<bool, math::PrimeFactors<iter_type>> rulesResult = applyRules(K);
        bool foundSieve = (divisorFirst[K] != 0);
        bool foundRules = rulesResult.first;
        if (foundSieve) {
            math::PrimeFactors<iter_type> factors(divisorFirst[K]);
            if (foundRules) {    // different cases to avoid whitespace at end of line
                cout << divisorFirst[K] << " = " << left << setw(FactorOutputWidth) << factors;
            } else {
                cout << divisorFirst[K] << " = " << left << factors;
            }
        } else if (foundRules) {
            cout << " " << "   " << left << setw(FactorOutputWidth) << " ";
        } else {
            cout << "---";
        }
        if (foundRules) {
            cout << "  " << left << rulesResult.second;
        }
        cout << right << endl;
    }
}

static void recordsTable(const std::vector<iter_type>& divisorFirst)
{
    /* Determine list of records */
    iter_type maxDivisorNumber = divisorFirst.size()-1;
    iter_type previousRecordNumber = 0;
    iter_type previousRecordValue = 0;
    bool foundRecord;

    do {
        iter_type recordNumber = 0;
        iter_type recordValue = 0;
        for (iter_type i = 1; i <= maxDivisorNumber; ++i) {
            auto value = divisorFirst[i];
            if (value > previousRecordValue && i > previousRecordNumber && (recordValue == 0 || value <= recordValue)) {
                recordValue = value;
                recordNumber = i;
            }
        }
        foundRecord = (recordValue > previousRecordValue);
        if (foundRecord) {
            cout << setw(5) << recordNumber << setw(12) << recordValue << endl;
            previousRecordNumber = recordNumber;
            previousRecordValue = recordValue;
        }
    } while (foundRecord);
}

void divtable(iter_type n)
{
    auto divisorFirst = sieveDivisors(n);
    mainTable(divisorFirst);
    cout << endl << endl;
    recordsTable(divisorFirst);
}

int main(int argc, char *argv[])
{
    if (argc != 2) {
        cout << "Syntax: divtbl n" << endl;
    } else {
        if constexpr (std::is_same<iter_type, unsigned long>::value) {
            unsigned long n = getulong(argv[1]);
            divtable(n);
        } else {
            unsigned long long n = getulonglong(argv[1]);
            divtable(n);
        }
    }
    return 0;
}
