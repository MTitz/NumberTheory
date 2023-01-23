/* Program Micropuzzle57.java to solve micropuzzle 57 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

import java.util.Set;
import java.util.HashSet;

public class Micropuzzle57
{
    public static void main(String[] args)
    {
        final long PRODUCT = 1296;
        long[] divisors = NumberTheory.divisors(PRODUCT);
        Set<Long> found = new HashSet<>();
        for (int i = 0; i < divisors.length; ++i) {
            long age1 = divisors[i];
            for (int j = i; j < divisors.length; ++j) {
                long age2 = divisors[j];
                for (int k = j; k < divisors.length; ++k) {
                    long age3 = divisors[k];
                    if (age1 * age2 * age3 == PRODUCT) {
                        long sum = age1 + age2 + age3;
                        System.out.printf("%4d %4d %4d    %4d%n", age1, age2, age3, sum);
                        if (found.contains(sum)) {
                            System.out.println("Solution found with door number " + sum);
                        } else {
                            found.add(sum);
                        }
                    }
                }
            }
        }
    }
}
