/* Program Micropuzzle63.java to solve micropuzzle 63 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
*/


import java.util.Map;
import java.util.TreeMap;

public class Micropuzzle63
{
    public static int digitSum(int n)
    {
        final int base = 10;
        int sum = 0;
        while (n != 0) {
            sum += n % base;
            n /= base;
        }
        return sum;
    }

    public static void main(String[] args)
    {
        Map<Integer, Integer> counter = new TreeMap<>();
        for (int n = 10000; n < 100000; ++n) {
            int d = digitSum(n);
            if (n % d == 0) {
                int quotient = n / d;
                if (counter.containsKey(quotient)) {
                    int val = counter.get(quotient);
                    counter.put(quotient, ++val);
                } else {
                    counter.put(quotient, 1);
                }
            }
        }
        System.out.println("Size of map: " + counter.size());
        System.out.println("Entries into the map are:");
        int maxQuot = 0;
        int maxCount = 0;
        for(Map.Entry<Integer,Integer> pair : counter.entrySet()) {
            int key = pair.getKey();
            int value = pair.getValue();
            if (value > maxCount) {
                maxQuot = key;
                maxCount = value;
            }
            System.out.printf("%d ==> %d%n", key, value);
        }
        System.out.println();
        System.out.println("Quotient " + maxQuot + " is the most frequent one with " + maxCount + " occasions.");
    }
}
