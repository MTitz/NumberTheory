#include "harmonic.h"

namespace math {

const double A[] = { 0.5772156649015328606065120900824024310422,
    0.0,                        /* unused */
    1.0/12.0,
    1.0/12.0,
    19.0/120.0,
    9.0/20.0,
    863.0/504.0,
    1375.0/168.0,
    33953.0/720.0,
    57281.0/180.0,
    3250433.0/1320.0
};

std::size_t n_A = sizeof(A) / sizeof(double) -1;

const double euler = A[0];

} /* end of namespace math */
