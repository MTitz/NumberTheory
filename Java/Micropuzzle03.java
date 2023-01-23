/* Program Micropuzzle03.java to solve micropuzzle 03 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A chessboard dilemma
*/

public class Micropuzzle03
{
    public static void search(int dimension, int index, int[] positions)
    {
        if (index < dimension) {
            Pos: for (positions[index] = 0; positions[index] < dimension; ++positions[index]) {
                for (int j = 0; j < index; ++j) {
                    if (positions[j] == positions[index] || positions[j] == positions[index] - (index-j) || positions[j] == positions[index] + (index-j))
                        continue Pos;
                }
                search(dimension, index + 1, positions);
            }
        } else {
            System.out.print("Found:");
            for (int k = 0; k < dimension; ++k) {
                System.out.print(" " + (positions[k] + 1));
            }
            boolean isSolution = true;
            for (int k = 0; k < dimension; ++k) {
                if (positions[k] == k || positions[k] == (dimension-1) - k) {
                    isSolution = false;
                    break;
                }
            }
            if (isSolution)
                System.out.print("  no pieces on diagonals -> solution");
            System.out.println();
        }
    }

    public static void search(int dimension)
    {
        if (dimension <= 0)
            return;
        int[] positions = new int[dimension];
        search(dimension, 0, positions);
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; ++i)
        {
            int n = Integer.parseInt(args[i]);
            search(n);
        }
    }
}
