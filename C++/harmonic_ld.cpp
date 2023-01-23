#include "harmonic.h"

namespace math {

const long double A_l[] = { 0.5772156649015328606065120900824024310422L,
    0.0L,                        /* unused */
    1.0L/12.0L,
    1.0L/12.0L,
    19.0L/120.0L,
    9.0L/20.0L,
    863.0L/504.0L,
    1375.0L/168.0L,
    33953.0L/720.0L,
    57281.0L/180.0L,
    3250433.0L/1320.0L
};

std::size_t n_A_l = sizeof(A_l) / sizeof(long double) -1;

const long double euler_l = A_l[0];

} /* end of namespace math */
