/* Program Micropuzzle34.java to solve micropuzzle 34 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle34
{
    public static int number(int[] digits)
    {
        final int base = 10;
        if (digits == null)
            return 0;
        int n = 0;
        for (int i = digits.length-1; i >= 0; --i) {
            n *= base;
            n += digits[i];
        }
        return n;
    }

    public static void main(String[] args)
    {
        for (int a = 2; a <= 9; ++a) {
            int[] digits = new int[6];
            int dLimit = 1000000 / a;
            for (int d = 100000; d <= dLimit; ++d) {
                if (d % 10 == 0)
                    continue;
                int n = d;
                for (int i = 0; i < 6; ++i) {
                    digits[5-i] = n % 10;
                    n /= 10;
                }
                n = number(digits);
                if (d * a == n) {
                    System.out.println("Solution: " + d + " * " + a + " = " + n);
                }
            }
        }
    }
}
