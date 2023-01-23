/* Program Micropuzzle62.java to solve micropuzzle 62 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle62
{
    public static int number(int a, int b, int c)
    {
        return 10 * (10 * a + b) + c;
    }

    public static void main(String[] args)
    {
        final int[] digitSet = new int[] {1, 2, 3, 4, 6, 7, 8, 9};  // this excludes divisor 5
        final int[] divisors = new int[] {7, 11, 13, 17};
        int[] n = new int[6];
        for (int a : digitSet) {
            for (int b : digitSet) {
                if (b >= a)
                    break;
     candidate: for (int c : digitSet) {
                    if (c >= b)
                        break;
                    if ((a + b + c) % 3 == 0)
                        continue;  // divisor 3 not allowed
                    System.out.print("number: " + a + b + c);
                    n[0] = number(a, b, c);
                    n[1] = number(a, c, b);
                    n[2] = number(b, a, c);
                    n[3] = number(b, c, a);
                    n[4] = number(c, a, b);
                    n[5] = number(c, b, a);
                    for (int i = 0 ; i < 6; ++i) {
                        for (int d = 0; d < divisors.length; ++d)
                            if (n[i] % divisors[d] == 0) {
                                System.out.println("  " + divisors[d] + " divides " + n[i] + ", excluded");
                            continue candidate;
                        }
                    }
                    System.out.println(" is a solution with product of digits " + (a * b * c));
                }
            }
        }
    }
}
