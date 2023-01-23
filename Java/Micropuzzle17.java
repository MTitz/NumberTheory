/* Program Micropuzzle17.java to solve micropuzzle 17 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle17
{
    public static final int[] prime = new int[] {
         2,    3,    5,    7,   11,   13,   17,   19,   23,   29,
        31,   37,   41,   43,   47,   53,   59,   61,   67,   71,
        73,   79,   83,   89,   97
    };

    public static final int N_WEIGHTS = 6;
    public static final int MOD = 97;

    public static final int[] givenNumbers = new int[] {
        693847, 264315, 927064, 472289,
        838521, 741318, 553846, 385132
    };

    public static final int[] givenCheckdigits = new int[] {
        16, 27, 32, 14, 73, 69, 14, 10
    };

    public static final int testNumber = 123456;


    public static int checksum(int[] weight, int num)
    {
        final int base = 10;
        int sum = 0;
        int index = N_WEIGHTS-1;
        while (num != 0) {
            sum += weight[index--] * (num % base);
            num /= base;
        }
        return sum % MOD;
    }

    public static void checkWeights(int[] weight)
    {
        for (int i = 0; i < givenNumbers.length; ++i) {
            if (checksum(weight, givenNumbers[i]) != givenCheckdigits[i])
                return;
        }

        int testChecksum = checksum(weight, testNumber);
        System.out.print("Solution: weights");
        for (int i = 0; i < weight.length; ++i) {
            System.out.print(" " + weight[i]);
        }
        System.out.println(" and checksum of " + testNumber + " is " + testChecksum);
    }

    public static void check(int pIndex, int offset, int[] weight)
    {
        final int MIN_WEIGHT = 0;
        final int MAX_WEIGHT = 99;
        if (offset < N_WEIGHTS) {
            int p = prime[pIndex + offset];
            int w = MIN_WEIGHT;
            while (w <= MAX_WEIGHT) {
                weight[offset] = w;
                check(pIndex, offset + 1, weight);
                w += p;
            }
        } else {
            checkWeights(weight);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Checksum example: "
            + checksum(new int[] {10, 20, 30, 40, 50, 60}, 987654));
        System.out.println();
        for (int pIndex = 0; pIndex < prime.length-(N_WEIGHTS-1); ++pIndex) {
            System.out.println("index: " + pIndex + ", first prime " + prime[pIndex]);
            check(pIndex, 0, new int[N_WEIGHTS]);
        }
    }
}
