/* Program Micropuzzle60.java to solve micropuzzle 60 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   A series of primes
*/

public class Micropuzzle60
{
    static final int PUZZLE_NUMBER = 106620;
    static final int PRIME_LIMIT = 10001000;

    public static boolean[] eratosthenes(int n)
    {
        boolean[] a = new boolean[n+1];
        a[0] = a[1] = false;
        for (int i = 2; i <= n; ++i) {
            a[i] = true;
        }
        int p = 2;
        while (p * p <= n) {
            int j = p * p;
            while (j <= n) {
                a[j] = false;
                j += p;
            }

            // find next prime
            do {
                ++p;
            } while (!a[p]);
        }
        return a;
    }

    public static final int[] primes;
    static {
        int n = PRIME_LIMIT;
        //System.out.println("Sieving for primes until " + n);
        boolean[] sieve = eratosthenes(n);
        // count how many primes we actually have until n
        int count = 0;
        for (int i = 1; i <= n; ++i) {
            if (sieve[i]) ++count;
        }
        //System.out.println(" ... found " + count + " primes");
        // now creating array of primes with correct size and insert the primes
        primes = new int[count];
        int k = 0;
        for (int i = 1; i <= n; ++i) {
            if (sieve[i]) {
                primes[k++] = i;
            }
        }
        //System.out.println("primes[" + (primes.length-1) + "] = " + primes[primes.length-1]);
        // give memory for sieve back for garbage collector
        sieve = null;
        if (k != count) {
            System.err.println("Error during initialization of prime array.");
        }
    }

    public static final int binarySearch(int[] a, int target)
    {
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int m = (left + right) / 2;
            if (a[m] < target)
                left = m + 1;
            else if (a[m] > target)
                right = m - 1;
            else
                return m;
        }
        return -1;
    }

    public static final int approximateBinarySearch(int[] a, int target)
    {
        int left = 0;
        int right = a.length - 1;
        int m = -1;
        while (left <= right) {
            m = (left + right) / 2;
            if (a[m] < target)
                left = m + 1;
            else if (a[m] > target)
                right = m - 1;
            else
                return m;
        }
        return m;
    }

    public static void searchPrimeSum(int number)
    {
        if (number > primes[primes.length-1]) {
            System.err.println("Not enough primes calculated for " + number);
            return;
        }
        boolean isPrime = binarySearch(primes, number) >= 0;
        if (isPrime) {
            System.out.println("Number " + number + " is prime");
            return;
        }
        for (int n = 2; n <= number; ++n) {
            int m = approximateBinarySearch(primes, number / n) - n;
            if (m < 0)
                m = 0;
            int sum = 0;
            for (int k = m; k <= 2 * n + m; ++k) {
                if (k == m) {
                    for (int j = 0; j < n; ++j) {
                        sum += primes[k + j];
                    }
                } else {
                    sum += primes[k+n-1] - primes[k-1];
                }
                if (sum > number) {
                    // If already sum of first n primes is greater than number,
                    // no representation with >= n summands is possible.
                    if (k == 0) {
                        System.out.println("Number " + number + " is not sum of consecutive primes.");
                        return;
                    }

                    // no representation with n summands
                    break;
                } else if (sum == number) {
                    System.out.print("Number " + number + " is sum of " + n + " consecutive primes: ");
                    for (int j = 0; j < n; ++j) {
                        System.out.print(" " + primes[k + j]);
                    }
                    System.out.println();
                    return;
                }
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Searching prime sum for " + PUZZLE_NUMBER +
                               " (for other numbers use command line parameters):");
            searchPrimeSum(PUZZLE_NUMBER);
            return;
        }
        for (int i = 0; i < args.length; ++i) {
            int n = Integer.parseInt(args[i]);
            if (n >= 1) {
                searchPrimeSum(n);
            }
        }
    }
}
