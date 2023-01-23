/* Program Micropuzzle61.java to solve micropuzzle 61 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle61
{
    public static void main(String[] args)
    {
        final int[] numbers = new int[] {15556, 13913, 11422};
        final int n = numbers.length;
        final int dmax = numbers[0];  // could also use minimum of numbers
        int[] remainder = new int[n];
        for (int d = 1; d <= dmax; ++d) {
            for (int i = 0; i < n; ++i) {
                int quotient = numbers[i] / d;
                remainder[i] = numbers[i] - d * quotient;
            }

            // Check if we have a possible solution: all remainders must be equal
            boolean isSolution = true;
            for (int j = 1; j < n; ++j) {
                if (remainder[j] != remainder[0]) {
                    isSolution = false;
                    break;
                }
            }
            // Remainder 0 not valid as door number
            if (remainder[0] == 0)
                isSolution = false;

            if (isSolution) {
                System.out.println("Solution: House number " + d + ", brother's door number: " + remainder[0]);
            }
        }
    }
}
