import java.util.Set;
import java.util.HashSet;

public class SquareTest
{
    public static void main(String[] args)
    {
        final long nMax = 100_000;
        Set<Long> squares = new HashSet<>();
        for (long n = 0; n <= nMax; ++n) {
            squares.add(n * n);
        }
        System.err.println("Initialized set of squares up to " + nMax + "^2 = " + nMax * nMax);
        for (long j = 0; j <= nMax * nMax; ++j) {
            boolean isSquare1 = (NumberTheory.squareTest(j) >= 0);
            boolean isSquare2 = squares.contains(j);
            if (isSquare1 != isSquare2) {
                System.err.println("Problem detected for n = " + j + ": " + isSquare1 + " " + isSquare2);
            }
            if (j % 100_000_000 == 0) {
                System.err.printf("Checked until %16d%n", j);
            }
        }
    }
}
