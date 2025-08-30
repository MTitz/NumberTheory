/* Program Micropuzzle57.java to solve micropuzzle 57 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The census taker
*/

import java.util.Set;
import java.util.HashSet;

public class Micropuzzle57
{
    public static void solve(long product)
    {
        System.out.printf("%nLooking for solutions for product %d%n", product);
        long[] divisors = NumberTheory.divisors(product);
        Set<Long> found = new HashSet<>();
        for (int i = 0; i < divisors.length; ++i) {
            long age1 = divisors[i];
            for (int j = i; j < divisors.length; ++j) {
                long age2 = divisors[j];
                for (int k = j; k < divisors.length; ++k) {
                    long age3 = divisors[k];
                    if (age1 * age2 * age3 == product) {
                        long sum = age1 + age2 + age3;
                        System.out.printf("%6d %6d %6d    %6d%n", age1, age2, age3, sum);
                        if (found.contains(sum)) {
                            System.out.println(" (solution found with door number " + sum + ")");
                        } else {
                            found.add(sum);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        final long PRODUCT = 1296;  // the value given in micropuzzle 57
        if (args.length == 0) {
            solve(PRODUCT);
        } else {
            for (int i = 0; i < args.length; ++i) {
                long n = Long.parseLong(args[i]);
                if (n > 0) {
                    solve(n);
                }
            }
        }
    }
}
