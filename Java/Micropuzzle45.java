/* Program Micropuzzle45.java to solve micropuzzle 45 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Palindromic cycles
*/

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class Micropuzzle45
{
    public static BigInteger reverseDigits(BigInteger n)
    {
        StringBuilder sb = new StringBuilder(n.toString());
        return new BigInteger(sb.reverse().toString());
    }

    public static BigInteger step(BigInteger n)
    {
        return n.add(reverseDigits(n));
    }

    public static boolean isPalindromic(BigInteger n)
    {
        return n.compareTo(reverseDigits(n)) == 0;
    }

    public static void main(String[] args)
    {
        int nMax;
        if (args.length == 0) {
            nMax = 99;  /* highest two-digit number, value from puzzle */
        } else {
            nMax = Integer.parseInt(args[0]);
        }
        int maxCount = -1;  // not yet found any maximum
        int maxNumber = 0;
        final int cycleLimit = 1000;
        Set<Integer> exceptionalCase = new TreeSet<>();
        loop: for (int n = 10; n <= nMax; ++n) {
            BigInteger number = BigInteger.valueOf(n);
            int count = 0;
            while(!isPalindromic(number)) {
                number = step(number);
                ++count;
                if (count >= cycleLimit) {
                    exceptionalCase.add(n);
                    continue loop;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxNumber = n;
            }
            System.out.printf("%7d %7d   %s%n", n, count, number);
        }
        if (maxCount >= 0) {
            System.out.println();
            System.out.println("Maximum of " + maxCount + " cycles reached for number " + maxNumber);
        }
        if (exceptionalCase.size() > 0) {
            System.out.print("No palindrome after " + cycleLimit + " cycles:");
            for (Integer n : exceptionalCase) {
                System.out.print(" " + n);
            }
            System.out.println();
        }
    }
}
