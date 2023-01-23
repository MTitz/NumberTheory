/* Program Micropuzzle43.java to solve micropuzzle 43 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Ten-digit primes
*/

public class Micropuzzle43
{
    public static long nextPrime(long n)
    {
        if (n <= 2)
            return 2;
        if ((n & 1) == 0)
            ++n;
        while (!NumberTheory.isPrime(n))
            n += 2;
        return n;
    }

    public static void digitCounts(long number, int[] digitCount)
    {
        for (int i = 0; i < 10; ++i) {
            digitCount[i] = 0;
        }
        long n = number;
        while (n != 0) {
            ++digitCount[(int)(n % 10)];
            n /= 10;
        }
    }

    public static void searchDigitRange(int digits)
    {
        int[] digitCount = new int[10];
        int[] digitRecord = new int[10];
        long[] recordNumber = new long[10];
        for (int d = 0; d < 10; ++d) {
            digitRecord[d] = 0;
            recordNumber[d] = -1;
        }

        long lowLimit  = NumberTheory.power(10L, digits-1);
        long highLimit = NumberTheory.power(10L, digits)-1;
        long prime = nextPrime(lowLimit + 1);
        while (prime <= highLimit) {
            digitCounts(prime, digitCount);
            for (int d = 0; d < 10; ++d) {
                if (digitCount[d] > digitRecord[d]) {
                    System.err.println("New record for digit " + d + " with count " + digitCount[d] + " for " + prime);

                    digitRecord[d] = digitCount[d];
                    recordNumber[d] = prime;
                }
            }
            prime = nextPrime(++prime);
        }
        System.out.println();
        System.out.println("Record table for " + digits + " digits, range from " + lowLimit + " to " + highLimit);
        System.out.println();
        for (int d = 0; d < 10; ++d) {
            long record = recordNumber[d];
            if (record != -1) {
                System.out.printf("%2d %3d %12d%n", d, digitRecord[d], record);
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Solution (for other number of digits use parameters):");
            searchDigitRange(10);
            return;
        }
        for (int i = 0; i < args.length; ++i) {
            int n = Integer.parseInt(args[i]);
            if (n >= 1 && n <= 18) {
                searchDigitRange(n);
            }
        }
    }
}
