/* Program Micropuzzle30.java to solve micropuzzle 30 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle30
{
    public static void resetDigitCount(int[] digitCount)
    {
        for (int i = 0; i < 10; ++i) {
            digitCount[i] = 0;
        }
    }

    public static void updateDigitCount(long number, int[] digitCount)
    {
        long n = number;
        while (n != 0) {
            ++digitCount[(int)(n % 10)];
            n /= 10;
        }
    }

    public static int nDigits(int[] digitCount)
    {
        int count = 0;
        for (int i = 0; i < 10; ++i) {
            count += digitCount[i];
        }
        return count;
    }

    public static void main(String[] args)
    {
        long n = 1;
        boolean done = false;
        int[] d = new int[10];
        while (!done) {
            long power3 = n * n * n;
            long power4 = n * power3;
            resetDigitCount(d);
            updateDigitCount(power3, d);
            updateDigitCount(power4, d);
            int numberDigits = nDigits(d);
            if (numberDigits == 10) {
                boolean isSolution = true;
                for (int k = 0; k < 10; ++k) {
                    if (d[k] != 1) {
                        isSolution = false;
                        break;
                    }
                }
                if (isSolution) {
                    System.out.println("Solution: " + n + " with cube " + power3 + " and fourth power " + power4);
                }
            } else if (numberDigits > 10) {
                done = true;
            }

            ++n;
        }
    }
}
