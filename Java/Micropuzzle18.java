/* Program Micropuzzle18.java to solve micropuzzle 18 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

import java.math.BigInteger;

public class Micropuzzle18
{
    static final int N_SOLUTIONS = 8;  // number of solutions to calculate

    public static void printSolution(int count, BigInteger x, BigInteger y)
    {
        System.out.println();
        System.out.println("Solution " + count);
        System.out.println("  x = " + x + ", y = " + y);
        if (x.testBit(0)) {
            // x is an odd integer
            BigInteger hours = x.subtract(BigInteger.ONE).divide(BigInteger.TWO);
            BigInteger rice  = y.multiply(y);
            System.out.println("  hours: " + hours);
            System.out.println("  grains of rice: " + y + "^2 = " + rice);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Pell's equation x^2 - 184 * y^2 = 1");
        BigInteger d  = BigInteger.valueOf(  184);
        BigInteger x1 = BigInteger.valueOf(24335);
        BigInteger y1 = BigInteger.valueOf( 1794);
        int count = 0;

        BigInteger xp = x1;
        BigInteger yp = y1;
        printSolution(++count, xp, yp);
        for (int i = 1; i < N_SOLUTIONS; ++i) {
            BigInteger x = x1.multiply(xp).add(d.multiply(y1).multiply(yp));
            BigInteger y = x1.multiply(yp).add(y1.multiply(xp));
            printSolution(++count, x, y);
            xp = x;
            yp = y;
        }
    }
}
