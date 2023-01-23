/* Program Micropuzzle64.java to solve micropuzzle 64 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle64
{
    public static int toNumber(int[] digits, int i1, int i2, int i3)
    {
        return 100 * digits[i1] + 10 * digits[i2] + digits[i3];
    }

    public static void main(String[] args)
    {
        Permutation permutation = new Permutation(9);
        while (permutation.hasNext()) {
            int[] d = permutation.getPermutation();
            int n1 = toNumber(d, 0, 1, 2);
            int n2 = toNumber(d, 3, 4, 5);
            int n3 = toNumber(d, 6, 7, 8);
            if (n1 + n2 == n3) {
               int n4 = toNumber(d, 6, 3, 0);
               int n5 = toNumber(d, 7, 4, 1);
               int n6 = toNumber(d, 8, 5, 2);
               if (n4 + n5 == n6) {
                   System.out.println("Solution: " + n1 + " + " + n2 + " = " + n3 +
                                      ", rotated " + n4 + " + " + n5 + " = " + n6);
               }
            }
            permutation.next();
        }
    }
}
