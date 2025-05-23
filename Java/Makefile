JAVAC	= javac
JFLAGS	= -O
#JUNIT	= /usr/share/java/junit.jar
JUNIT	= lib/junit-3.8.2.jar
TESTPATH= .:$(JUNIT)
ifeq ($(OS),Windows_NT)
TESTPATH= .;$(JUNIT)
endif

.java.class:
	$(JAVAC) $(JFLAGS) $<

.java.pdf:
	(MPAGE=;mpage -H -2 -c -f -bA4 -D"%d.%m.%Y %H:%M" $< |ps2pdf - > $@)

.ltx.pdf:
	pdflatex $<

RIDDLE	= Micropuzzle01.class Micropuzzle03.class Micropuzzle04.class Micropuzzle05.class Micropuzzle06.class Micropuzzle07.class Micropuzzle10.class Micropuzzle11.class Micropuzzle12.class Micropuzzle13.class Micropuzzle14.class Micropuzzle17.class Micropuzzle18.class Micropuzzle19.class Micropuzzle21.class Micropuzzle22.class Micropuzzle25.class Micropuzzle30.class Micropuzzle31.class Micropuzzle32.class Micropuzzle33.class Micropuzzle34.class Micropuzzle35.class Micropuzzle36.class Micropuzzle37.class Micropuzzle41.class Micropuzzle42.class Micropuzzle43.class Micropuzzle45.class Micropuzzle49.class Micropuzzle50.class Micropuzzle51.class Micropuzzle54.class Micropuzzle57.class Micropuzzle60.class Micropuzzle61.class Micropuzzle62.class Micropuzzle63.class Micropuzzle64.class
RIDDLE_EXTRA = Micropuzzle34a.class Micropuzzle34b.class Micropuzzle34c.class Micropuzzle36a.class Micropuzzle42a.class
TESTCLASSES = ComplexTest.class
CLASSFILES = AllDigits.class BernoulliNumbers.class BigFraction.class BigFractionTest.class Complex.class DigitsSum.class DivRules.class Fibonacci.class GenPartitions.class HarmonicSum.class HeadsAlgorithm.class NumberTheory.class Pair.class Permutation.class PermutationTest.class PrimeFactors.class PythagoreanTriples.class NTTest.class SquareTest.class $(RIDDLE) $(RIDDLE_EXTRA) $(TESTCLASSES)
RESULTS = bern60.txt BigFractionTest.txt NTTest.txt PermutationTest.txt Micropuzzle07.txt
LISTINGS = BigFraction.pdf Complex.pdf NumberTheory.pdf Pair.pdf Permutation.pdf PrimeFactors.pdf
DOCU = JavaNumberTheory_A4.pdf JavaNumberTheory_US.pdf puzzle_notes.pdf
ifeq ($(OS),Windows_NT)
DOCU = puzzle_notes.pdf
LISTINGS =
endif

all:	$(CLASSFILES) $(RESULTS) $(LISTINGS) $(DOCU)

docu:	$(DOCU)

test:	$(TESTCLASSES) Complex.class
	java -cp $(TESTPATH) junit.textui.TestRunner ComplexTest

clean:
	rm -f *.class $(RESULTS) $(LISTINGS) $(DOCU)

ComplexTest.class: ComplexTest.java
	javac -cp $(TESTPATH) $<

Micropuzzle01.txt: Micropuzzle01.class NumberTheory.class
	java Micropuzzle01 1000000000 > $@

Micropuzzle07.txt: Micropuzzle07.class
	java Micropuzzle07 7200 > $@

bern60.txt: BernoulliNumbers.class BigFraction.class
	java BernoulliNumbers 60 > $@

bern1000.txt: BernoulliNumbers.class BigFraction.class
	java BernoulliNumbers 1000 > $@

BigFractionTest.txt: BigFraction.class BigFractionTest.class
	java BigFractionTest > $@

NTTest.txt: NTTest.class HeadsAlgorithm.class NumberTheory.class Pair.class
	java -Xmx1024m NTTest > $@

PermutationTest.txt: Permutation.class PermutationTest.class
	java PermutationTest > $@

PythagoreanTriples.txt: PythagoreanTriples.class
	java -Xmx2048m PythagoreanTriples 200000000 > $@

JavaNumberTheory_A4.pdf: $(CLASSFILES)
	(MPAGE=;mpage -H -2 -c -f -bA4 -D"%d.%m.%Y %H:%M" *.java|ps2pdf - > $@)

JavaNumberTheory_US.pdf: $(CLASSFILES)
	(MPAGE=;mpage -H -2 -c -f -bLetter -D"%Y-%m-%d %H:%M" *.java|ps2pdf -sPAPERSIZE=letter - > $@)

.SUFFIXES:
.SUFFIXES: .java .class .ltx .pdf
