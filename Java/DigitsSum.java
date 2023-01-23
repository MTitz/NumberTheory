public class DigitsSum
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
            int number1 = toNumber(p, 0, 3);
            int number2 = toNumber(p, 3, 3);
            int number3 = toNumber(p, 6, 3);
            if (number1 <= number2 && number1 + number2 == number3)
                System.out.printf("%3d + %3d = %3d%n", number1, number2, number3);
            permutation.next();
        }
    }
}
