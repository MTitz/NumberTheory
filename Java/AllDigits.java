/* From [Peter Giblin] "Primes and Programming", chapter 1, project 5.12 */

public class AllDigits
{
    public static int toNumber(int[] digits, int indexStart, int totalDigits)
    {
        final int base = 10;
        if (digits == null)
            return 0;
        int n = 0;
        for (int i = 0; i < totalDigits; ++i) {
            n *= base;
            n += digits[indexStart + i];
        }
        return n;
    }

    public static void main(String[] args)
    {
        Permutation permutation = new Permutation(9);
        while (permutation.hasNext()) {
            int[] p = permutation.getPermutation();
            int number1 = toNumber(p, 0, 5);
            int number2 = toNumber(p, 5, 4);
            if (number1 % number2 == 0)
                System.out.printf("%5d / %4d = %2d%n", number1, number2, number1 / number2);
            permutation.next();
        }
    }
}
