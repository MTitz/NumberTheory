/* Program Micropuzzle31.java to solve micropuzzle 31 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A question of remainders
*/

public class Micropuzzle31
{
    public static void main(String[] args)
    {
        NumberTheory.SimpleLinearCongruence[] congruence =
            { new NumberTheory.SimpleLinearCongruence(9, 10),
              new NumberTheory.SimpleLinearCongruence(8,  9),
              new NumberTheory.SimpleLinearCongruence(7,  8),
              new NumberTheory.SimpleLinearCongruence(6,  7),
              new NumberTheory.SimpleLinearCongruence(5,  6),
              new NumberTheory.SimpleLinearCongruence(4,  5),
              new NumberTheory.SimpleLinearCongruence(3,  4),
              new NumberTheory.SimpleLinearCongruence(2,  3),
              new NumberTheory.SimpleLinearCongruence(1,  2)};
        NumberTheory.SimpleLinearCongruence simplified = NumberTheory.solve(congruence);
        long b = simplified.b();
        long m = simplified.m();
        System.out.printf("The general solution is %d + %d * n with n an arbitrary integer, for example:%n", b, m);
        System.out.print("..., ");
        for (int n = -2; n <= 10; ++n) {
            System.out.print(b + m * n + ", ");
        }
        System.out.println("...");
    }
}
