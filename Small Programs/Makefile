CC	= gcc -std=c99
CXX	= g++ -std=c++17
#CC	= clang
#CXX	= clang++ -std=c++17
OPTFLAGS= -O2 -funroll-loops
WARNINGS= -Wall -Wconversion -Wpointer-arith -Wcast-align -Wcast-qual \
	  -Wlarger-than-40000 -Wwrite-strings
#	  -Wlarger-than-40000 -Wwrite-strings -Wpedantic -Wextra

CFLAGS	= $(OPTFLAGS) $(WARNINGS)
CXXFLAGS= $(OPTFLAGS) $(WARNINGS)
LFLAGS	=

BINARIES= divrules fibonacci GenPartitions pythagorean_triples quaddrei
OBJECTS	=
LIBS	= -lm

RM	= rm -f


.c.o:
	$(CC) $(CFLAGS) -c $<

.cpp.o:
	$(CXX) $(CXXFLAGS) -c $<

all:	$(BINARIES) divrules.txt quaddrei.txt

divrules: divrules.o
	$(CC) $< -o $@ $(LFLAGS) $(LIBS)
	strip $@

fibonacci: fibonacci.o
	$(CC) $< -o $@ $(LFLAGS) -lgmp
	strip $@

GenPartitions: GenPartitions.o
	$(CXX) GenPartitions.o -o $@ $(LFLAGS) $(LIBS)
	strip $@

pythagorean_triples: pythagorean_triples.o
	$(CXX) pythagorean_triples.o -o $@ $(LFLAGS)
	strip $@

quaddrei: quaddrei.o
	$(CXX) quaddrei.o -o $@ $(LFLAGS) $(LIBS)
	strip $@

divrules.txt: divrules
	./$< 10000 > $@

quaddrei.txt: quaddrei
	./$< 12 > $@


divrules.o: divrules.c

GenPartitions.o: GenPartitions.cpp

quaddrei.o: quaddrei.cpp


clean:
	$(RM) *.o core $(BINARIES)
	$(RM) divrules.txt quaddrei.txt

.SUFFIXES:
.SUFFIXES: .c .cpp .h .o
