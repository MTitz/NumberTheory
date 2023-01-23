/* Program Micropuzzle37.java to solve micropuzzle 37 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle37
{
    public static void main(String[] args)
    {
        int nMax = 1000;
        int[] cube = new int[nMax+1];
        for (int n = 0; n <= nMax; ++n) {
            cube[n] = n * n * n;
        }
        int cubeMax = cube[nMax];
        int smallestSum = Integer.MAX_VALUE;

        System.out.println("Looking for sequences of at least four consecutive perfect cubes");
        System.out.println("(except unity) equal to a perfect square with sum <= " + cubeMax);
        System.out.println();

        int nStart = 2;
        while (nStart < nMax) {
            int nEnd = nStart;
            int cubeSum = cube[nStart];
            int count = 1;
            while (cubeSum < cubeMax) {
                ++nEnd;
                cubeSum += cube[nEnd];
                ++count;
                if (count >= 4) {
                    int square = NumberTheory.squareTest(cubeSum);
                    if (square > 0) {
                        System.out.println("Sequence with " + count + " cubes and sum " + cubeSum +
                            " = " + nStart + "^3 + " + (nStart+1) + "^3 +...+ " + nEnd + "^3 = " + square + "^2");
                        if (cubeSum < smallestSum)
                            smallestSum = cubeSum;
                    }
                }
            }
            ++nStart;
        }
        if (smallestSum <= cubeMax) {
            System.out.println("The smallest found perfect square is " + smallestSum);
        }
    }
}
