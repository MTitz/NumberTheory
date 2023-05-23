import java.math.BigInteger;
import java.math.RoundingMode;

public class BigFractionTest
{
    private static final BigFraction f1 = new BigFraction(1, 2);
    private static final BigFraction f2 = new BigFraction(3, 4);
    private static final BigFraction f3 = new BigFraction(5, 24);
    private static final BigFraction f4 = new BigFraction(9, 32);
    private static final BigFraction f5 = new BigFraction(9, 12);  // not cancelled

    public static void main(String[] args)
    {
        System.out.println("Fractions: f1 = " + f1 + ", f2 = " + f2 + ", f3 = " + f3 + ", f4 = " + f4);
        System.out.println();
        System.out.println("Integer value of f3: " + f3.intValue());
        System.out.println("Long value of f3:    " + f3.longValue());
        System.out.println("Float value of f3:   " + f3.floatValue());
        System.out.println("Double value of f3:  " + f3.doubleValue());
        System.out.println("Better value of f3:  " + f3.bigDecimalValue(54, RoundingMode.HALF_EVEN));
        System.out.println();
        System.out.println("Sum        f1 + f2 = " + f1.add(f2));
        System.out.println("Sum        f3 + f4 = " + f3.add(f4));
        System.out.println("Difference f1 - f2 = " + f1.subtract(f2));
        System.out.println("Difference f2 - f1 = " + f2.subtract(f1));
        System.out.println("Product    f1 * f2 = " + f1.multiply(f2));
        System.out.println("Product    0 * f1  = " + f1.multiply(BigInteger.ZERO));
        System.out.println("Product    2 * f1  = " + f1.multiply(BigInteger.TWO));
        System.out.println("Quotient   f1 / f2 = " + f1.divide(f2));
        System.out.println("Quotient   f4 / f3 = " + f4.divide(f3));
        System.out.println("Power      f1^10   = " + f1.pow(10));
        System.out.println("Power      f1^0    = " + f1.pow(0));
        System.out.println("Power      f1^-10  = " + f1.pow(-10));
        System.out.println("Power      f2^2    = " + f2.pow(2));
        System.out.println("Power      f2^-2   = " + f2.pow(-2));
        System.out.println("Power      f1^128  = " + f1.pow(128));
        System.out.println("2 * f1 == 1          " + (BigFraction.ONE == f1.multiply(BigInteger.TWO)));
        System.out.println("equals(2 * f1, 1)    " + BigFraction.ONE.equals(f1.multiply(BigInteger.TWO)));
        System.out.println("equals(f2, f5)       " + f2.equals(f5));
        System.out.println("equals(f2, f3)       " + f2.equals(f3));
    }
}
