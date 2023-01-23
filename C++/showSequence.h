#ifndef SHOWSEQ_H
#define SHOWSEQ_H

#include <iostream>

template<class Container>
void showSequence(const Container& s, const char *sep = " ",
                  std::ostream& os = std::cout)
{
    typename Container::const_iterator iter = s.begin();
    while (iter != s.end())
    {
        os << *iter++;
        if (iter != s.end())
            os << sep;
    }
    os << std::endl;
}

#endif /* SHOWSEQ_H */
