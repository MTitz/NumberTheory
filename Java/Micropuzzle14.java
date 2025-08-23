/* Program Micropuzzle14.java to solve micropuzzle 14 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Ten-digit perfect squares
*/

public class Micropuzzle14
{
    public static long power(long base, int exponent)
    {
        long result = 1;
        while (exponent != 0)
        {
            if ((exponent & 1) != 0)
                result *= base;
            exponent >>= 1;
            base *= base;
        }
        return result;
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

        long lowLimit  = power(10L, digits-1);
        long highLimit = power(10L, digits)-1;
        long n = (long)Math.ceil(Math.sqrt(lowLimit));
        long square = n * n;
        while (square <= highLimit) {
            digitCounts(square, digitCount);
            for (int d = 0; d < 10; ++d) {
                if (digitCount[d] > digitRecord[d]) {
                    digitRecord[d] = digitCount[d];
                    recordNumber[d] = n;
                }
            }

            ++n;
            square = n * n;
        }
        System.out.println();
        System.out.println("Record table for " + digits + " digits, range from " + lowLimit + " to " + highLimit);
        System.out.println();
        for (int d = 0; d < 10; ++d) {
            long record = recordNumber[d];
            if (record != -1) {
                System.out.printf("%2d %3d %10d %18d%n", d, digitRecord[d], record, record * record);
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
        for (int i = 0; i < args.length; ++i)
        {
            int n = Integer.parseInt(args[i]);
            if (n >= 1 && n <= 18) {
                searchDigitRange(n);
            }
        }
    }
}
