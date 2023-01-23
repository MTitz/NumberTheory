/* Program Micropuzzle21.java to solve micropuzzle 21 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A positional problem
*/

import static java.lang.Math.abs;

public class Micropuzzle21
{
    static final int[][] data = {
        {1, 2, 3, 2, 6, 8, 7, 2},
        {8, 6, 2, 5, 1, 3, 1, 4},
        {7, 1, 5, 4, 2, 5, 6, 8},
        {2, 8, 4, 7, 5, 1, 4, 3},
        {4, 3, 7, 2, 3, 8, 5, 1},
        {6, 5, 6, 3, 4, 7, 8, 3},
        {3, 7, 1, 8, 6, 2, 4, 6},
        {8, 4, 5, 6, 7, 5, 1, 7}
    };

    static final int[] xCoord, yCoord;
    static final boolean[][] onDiagonal;

    static int maxSum = 0;

    static {
        xCoord = new int[64];
        yCoord = new int[64];
        for (int n = 0; n < 64; ++n) {
            xCoord[n] = n % 8;
            yCoord[n] = n / 8;
        }

        onDiagonal = new boolean[64][64];
        for (int i = 0; i < 64; ++i) {
            for (int j = 0; j < 64; ++j) {
                onDiagonal[i][j] = abs(xCoord[i] - xCoord[j]) == abs(yCoord[i] - yCoord[j]);
            }
        }
    }

    public static void checkSolution(int[] n)
    {
        int[] x = new int[8];
        int[] y = new int[8];
        for (int k = 0; k < 8; ++k) {
            x[k] = xCoord[n[k]];
            y[k] = yCoord[n[k]];
        }
        int sum = 0;
        for (int k = 0; k < 8; ++k) {
            sum += data[x[k]][y[k]];
        }
        if (sum > maxSum) {
            System.out.print("New record " + sum + " with");
            for (int j = 0; j < 8; ++j) {
                System.out.print(" " + (char)('A'+x[j]) + (y[j]+1));
            }
            System.out.println();
            maxSum = sum;
        }
    }

    public static void search(int index, int[] n)
    {
        if (index < 8) {
            int lowIndex = index == 0 ? 0 : n[index-1] + 1;
            for (n[index] = lowIndex; n[index] < 64; ++n[index]) {
                boolean onDiag = false;
                for (int k = 0; k < index; ++k) {
                    if (onDiagonal[n[k]][n[index]])
                        onDiag = true;
                }
                if (!onDiag)
                    search(index + 1, n);
            }
        } else {
            checkSolution(n);
        }
    }

    public static void main(String[] args)
    {
        int[] n = new int[8];
        search(0, n);
    }
}
