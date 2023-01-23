/* Program Micropuzzle35.java to solve micropuzzle 35 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Tadpoles, terrapins, tortoises, and turtles
*/

public class Micropuzzle35
{
    public static void main(String[] args)
    {
        int b = 0;
        while (140 * b <= 4100) {
            int c = 0;
            while (140 * b + 228 * c <= 4100) {
                int d = 4100 - 140 * b - 228 * c;
                if (d % 285 == 0) {
                    d /= 285;
                    int a = 100 - b - c - d;
                    System.out.printf("Solution: %2d tadpoles, %2d terrapins, %2d tortoises, %2d turtles%n", a, b, c, d);
                }
                ++c;
            }
            ++b;
        }
    }
}
