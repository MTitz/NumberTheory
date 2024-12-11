/* Program Micropuzzle61.java to solve micropuzzle 61 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The hymn board
*/

public class Micropuzzle61
{
    public static void main(String[] args)
    {
        final int[] numbers = new int[] {15556, 13913, 11422};
        final int n = numbers.length;
        final int dmax = numbers[0];  // could also use minimum of numbers
        int[] remainder = new int[n];
      divisors:
        for (int d = 1; d <= dmax; ++d) {
            remainder[0] = numbers[0] % d;

            // Remainder 0 not valid as door number
            if (remainder[0] == 0)
                continue divisors;

            for (int i = 1; i < n; ++i) {
                remainder[i] = numbers[i] % d;
                if (remainder[i] != remainder[0])
                    continue divisors;
            }

            System.out.println("Solution: House number " + d + ", brother's door number: " + remainder[0]);
        }
    }
}
