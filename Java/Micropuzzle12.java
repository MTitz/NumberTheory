/* Program Micropuzzle12.java to solve micropuzzle 12 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle12
{
    public static int toNumber(int[] digits, int indexStart, int totalDigits)
    {
        final int base = 10;
        if (digits == null)
            return 0;
        int n = 0;
        for (int i = 0; i < totalDigits; ++i) {
            n *= base;
            n += digits[indexStart + i];
        }
        return n;
    }

    public static void main(String[] args)
    {
        Permutation permutation = new Permutation(10, 0);
        while (permutation.hasNext()) {
            int[] p = permutation.getPermutation();
            if (p[0] != 0 && p[6] != 0 && p[0] != 5 && p[6] != 5) {
                long a = toNumber(p, 0, 6);
                long b = toNumber(p, 6, 4);
                if (a * a == b * b * b) {
                    System.out.println("Solution: A = " + a + ", B = " + b);
                }
            }
            permutation.next();
        }
    }
}
