/* Program Micropuzzle45.java to solve micropuzzle 45 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Palindromic cycles
*/

import java.math.BigInteger;
import java.util.SortedSet;
import java.util.TreeSet;

public class Micropuzzle45
{
    public static BigInteger reverseDigits(BigInteger n)
    {
        StringBuilder sb = new StringBuilder(n.toString());
        return new BigInteger(sb.reverse().toString());
    }

    public static void main(String[] args)
    {
        int nMax;
        if (args.length == 0) {
            nMax = 99;  // highest two-digit number, value from puzzle
        } else {
            nMax = Integer.parseInt(args[0]);
        }
        int maxCount = -1;  // not yet found any maximum
        int maxNumber = 0;
        final int ITERATION_LIMIT = 1000;
        SortedSet<Integer> exceptionalCases = new TreeSet<>();
        loop: for (int n = 10; n <= nMax; ++n) {
            BigInteger number = BigInteger.valueOf(n);
            BigInteger reverseNumber = reverseDigits(number);
            int count = 0;
            while (number.compareTo(reverseNumber) != 0) {
                ++count;
                if (count > ITERATION_LIMIT) {
                    exceptionalCases.add(n);
                    continue loop;
                }
                number = number.add(reverseNumber);
                reverseNumber = reverseDigits(number);
            }
            if (count > maxCount) {
                maxCount = count;
                maxNumber = n;
            }
            System.out.printf("%8d %7d   %d%n", n, count, number);
        }
        if (maxCount >= 0) {
            System.out.println();
            System.out.println("Maximum of " + maxCount +
                    " iterations reached for number " + maxNumber);
        }
        if (!exceptionalCases.isEmpty()) {
            final int NUMBERS_PER_LINE = 8;
            System.out.println();
            System.out.println("No palindrome after " + ITERATION_LIMIT + " iterations:");
            int outputCount = 0;
            for (Integer n : exceptionalCases) {
                System.out.printf(" %8d", n);
                ++outputCount;
                if (outputCount % NUMBERS_PER_LINE == 0)
                    System.out.println();
            }
            if (outputCount % NUMBERS_PER_LINE != 0)
                System.out.println();
        }
    }
}
