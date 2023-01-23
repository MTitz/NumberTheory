#include "bigunsigned.h"

using std::cout;
using std::endl;
//using std::setw;

using namespace math;

int main()
{
    bigunsigned ten(10);
    std::cout << ten << std::endl;
    std::cout << ten + ten << std::endl;
    return 0;
}
