/* Program Micropuzzle10.java to solve micropuzzle 10 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/

public class Micropuzzle10
{
    static final boolean isLeapYear(int year)
    {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    static final int daysInMonth(int year, int month)
    {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else {
            return 31;
        }
    }

    public static void main(String[] args)
    {
        final int nCages = 100;
        boolean cage[] = new boolean[nCages+1];  // +1 because we also have cage[0]
        for (int i = 1; i <= nCages; ++i) {
            cage[i] = true;
        }

        // Set initial month and year and the cage number to start with.
        int month = 1;
        int year = 1980;
        int birdPosition = 31;

        for (int i = 1; i <= nCages; ++i) {
            // Determine cage for this month unless we are at first bird.
            if (i >= 2) {
                for (int k = 1; k <= daysInMonth(year, month); ++k) {
                    do {
                        ++birdPosition;
                        if (birdPosition > nCages)
                            birdPosition = 1;
                    } while (!cage[birdPosition]);
                }
            }

            // Take the birth out of the cage and report this.
            cage[birdPosition] = false;
            System.out.printf("%02d.%4d  cage %3d%n", month, year, birdPosition);

            // Advance month.
            ++month;
            if (month > 12) {
                month = 1;
                ++year;
            }
        }
    }
}
