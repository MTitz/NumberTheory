#ifndef DIVRULES_H
#define DIVRULES_H

#include <utility>

#include "numbertheory.h"

template<typename T>
std::pair<bool, math::PrimeFactors<T>> applyRules(T nDivisors)
{
    using exp_type = unsigned int;
    math::PrimeFactors<T> primeFactors(nDivisors);
    if (nDivisors == 1) {
        return std::make_pair(true, math::PrimeFactors<T>());
    } else if (primeFactors.isPrime()) {
        // number = p with p prime
        return std::make_pair(true, math::PrimeFactors<T>(2, (exp_type)nDivisors-1));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 1
                        && primeFactors.factors(1).exponent() == 1) {
        // number = p*q with p, q different primes
        exp_type exp2 = (exp_type)primeFactors.factors(1).base()-1;
        exp_type exp3 = (exp_type)primeFactors.factors(0).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exp2, 3, exp3));
    } else if (primeFactors.nFactors() == 1
                        && primeFactors.factors(0).exponent() == 2) {
        // number = p^2 with p prime
        exp_type exponent = (exp_type)primeFactors.factors(0).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, exponent));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 2
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() >= 3) {
        // number = 4*p with p >= 3 prime
        exp_type exponent = (exp_type)primeFactors.factors(1).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, 1, 5, 1));
    } else if (primeFactors.nFactors() == 3
                        && primeFactors.factors(0).exponent() == 1
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(2).exponent() == 1) {
        // number = p*q*r with p > q > r prime
        exp_type exponent_p = (exp_type)primeFactors.factors(2).base()-1;
        exp_type exponent_q = (exp_type)primeFactors.factors(1).base()-1;
        exp_type exponent_r = (exp_type)primeFactors.factors(0).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent_p, 3, exponent_q, 5, exponent_r));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 2
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(0).base() * primeFactors.factors(0).base() < primeFactors.factors(1).base()) {
        // number = q^2*p with q^2 < p and p, q prime
        exp_type exp2 = (exp_type)primeFactors.factors(1).base()-1;
        exp_type exp3 = (exp_type)primeFactors.factors(0).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exp2, 3, exp3, 5, exp3));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 3
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() >= 5) {
        // number = 8*p with p >= 5 prime
        exp_type exponent = (exp_type)primeFactors.factors(1).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, 1, 5, 1, 7, 1));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 4
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() >= 5) {
        // number = 16*p with p >= 5 prime
        exp_type exponent = (exp_type)primeFactors.factors(1).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, 3, 5, 1, 7, 1));
    } else if (primeFactors.nFactors() == 1
                        && primeFactors.factors(0).exponent() == 3
                        && primeFactors.factors(0).base() >= 3) {
        // number = p^3 with p >= 3 prime
        exp_type exponent = (exp_type)primeFactors.factors(0).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, exponent, 5, exponent));
    } else if (primeFactors.nFactors() == 1
                        && primeFactors.factors(0).exponent() == 3
                        && primeFactors.factors(0).base() == 2) {
        // number = 2^3
        return std::make_pair(true, math::PrimeFactors<T>(2, 3, 3, 1));
    } else if (primeFactors.nFactors() == 3
                        && primeFactors.factors(0).exponent() == 2
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(2).exponent() == 1
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() == 3
                        && primeFactors.factors(2).base() >= 5) {
        // number = 12*p with p >= 5 prime
        exp_type exponent = (exp_type)primeFactors.factors(2).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, 2, 5, 1, 7, 1));
    } else if (primeFactors.nFactors() == 1
                        && primeFactors.factors(0).exponent() == 4
                        && primeFactors.factors(0).base() == 2) {
        // number = 2^4 == 16
        return std::make_pair(true, math::PrimeFactors<T>(2, 3, 3, 1, 5, 1));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 1
                        && primeFactors.factors(1).exponent() == 2
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() >= 3) {
        // number = 2*p^2 with p >= 3 prime
        exp_type exponent = (exp_type)primeFactors.factors(1).base()-1;
        return std::make_pair(true, math::PrimeFactors<T>(2, exponent, 3, exponent, 5, 1));
    } else if (primeFactors.nFactors() == 2
                        && primeFactors.factors(0).exponent() == 3
                        && primeFactors.factors(1).exponent() == 1
                        && primeFactors.factors(0).base() == 2
                        && primeFactors.factors(1).base() == 3) {
        // number = 2^3*8 == 24
        return std::make_pair(true, math::PrimeFactors<T>(2, 3, 3, 2, 5, 1));
    } else if (primeFactors.nFactors() == 1
                        && primeFactors.factors(0).exponent() == 5
                        && primeFactors.factors(0).base() == 2) {
        // number = 2^5 == 32
        return std::make_pair(true, math::PrimeFactors<T>(2, 3, 3, 1, 5, 1, 7, 1));
    } else {
        return std::make_pair(false, math::PrimeFactors<T>(0));
    }
}

#endif
