/* Program NewtonMethod.java to solve micropuzzle 25 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Fieldcraft
   (see LaTeX file puzzle_notes.ltx for more details)
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;

import static java.lang.Math.*;

public class NewtonMethod
{
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final int GUARD_DIGITS = 5 + 2;  // 2 extra digits because of scaling by 100
    public static final int GUARD_DIGITS_FCT = 20;

    static int[] polyCoefficients;
    static int[] derivativeCoefficients;

    public static int[] calculateDerivative(final int [] a)
    {
        int n = a.length;
        if (n == 0) return new int[0];
        int[] deriv = new int[n-1];
        for (int k = 1; k < n; ++k) {
            deriv[k-1] = k * a[k];
        }
        return deriv;
    }

    public static BigDecimal horner(final int[] a, final BigDecimal x)
    {
        if (a == null || a.length == 0)
            return BigDecimal.ZERO;
        int degree = a.length - 1;
        BigDecimal value = BigDecimal.valueOf(a[degree]);
        for (int k = degree-1; k >= 0; --k) {
            value = value.multiply(x).add(BigDecimal.valueOf(a[k]));
        }
        return value;
    }

    public static BigDecimal newton(Function<BigDecimal, BigDecimal> fct, Function<BigDecimal, BigDecimal> derivative, BigDecimal x0, BigDecimal eps1, BigDecimal eps2, int maxIter, MathContext mc)
    {
        int fctScale = mc.getPrecision() + GUARD_DIGITS_FCT;
        BigDecimal x = x0;
        int iter = 0;
        boolean done1 = false, done2 = false, done = false;
        System.out.println("iteration: " + iter);
        System.out.println("  x = " + x);

        // Perform iterations of Newton's method to find the root of the function
        // until the desired precision is achieved or the maximum number of iterations is reached.
        do {
            ++iter;
            BigDecimal y = fct.apply(x);
            if (y.scale() != fctScale) {
                y = y.setScale(fctScale, mc.getRoundingMode());
            }
            System.out.println("  y = " + y);
            if (y.abs().compareTo(eps2) <= 0) {
                done1 = true;
            }
            BigDecimal yDerivative = derivative.apply(x);
            if (yDerivative.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("Derivative is zero. Newton's method cannot proceed.");
                return null; // Exit the method as division by zero is not allowed
            }
            BigDecimal diff = y.divide(yDerivative, fctScale, mc.getRoundingMode());
            x = x.subtract(diff);
            System.out.println("iteration: " + iter);
            System.out.println("  x = " + x.setScale(mc.getPrecision(), mc.getRoundingMode()));
            if (diff.abs().compareTo(eps1) <= 0) {
                done2 = true;
            }
            if (iter > maxIter) {
                return null;
            }
            done = done1 && done2;
        } while (!done);
        return x.setScale(mc.getPrecision(), mc.getRoundingMode());
    }

    public static BigDecimal f(BigDecimal x)
    {
        return horner(polyCoefficients, x);
    }

    public static BigDecimal d(BigDecimal x)
    {
        return horner(derivativeCoefficients, x);
    }

    public static BigDecimal time(BigDecimal u, BigDecimal v, MathContext mc)
    {
        BigDecimal diff2 = v.subtract(u);
        BigDecimal diff3 = BigDecimal.valueOf(6).subtract(v);
        BigDecimal s1 = u.multiply(u).add(BigDecimal.ONE).sqrt(mc);
        BigDecimal s2 = diff2.multiply(diff2).add(BigDecimal.valueOf(4)).sqrt(mc);
        BigDecimal s3 = diff3.multiply(diff3).add(BigDecimal.valueOf(9)).sqrt(mc);
        return s1.multiply(BigDecimal.valueOf(40)).add(s2.multiply(BigDecimal.valueOf(20))).add(s3.multiply(BigDecimal.valueOf(10)));
    }

    private static BigDecimal negPowerOfTen(int n)
    {
        return BigDecimal.ONE.divide(BigDecimal.TEN.pow(n));
    }

    private static void print(String text, BigDecimal d, int digits)
    {
        String format = String.format("%%s%%%d.%df%%n", digits + 5, digits);
        System.out.printf(format, text, d.setScale(digits, DEFAULT_ROUNDING_MODE));
    }

    private static void printBigDecimal(String text, BigDecimal d, int digits, boolean round)
    {
        if (text != null && !text.isEmpty())
            System.out.println(text);
        String s = d.setScale(digits, round ? RoundingMode.HALF_UP : RoundingMode.DOWN).toString();
        int index = s.indexOf('.');

        // print integral part of number and decimal point
        String integralPart = (index == -1) ? s : s.substring(0, index);
        if (integralPart.isEmpty())
            integralPart = "0";
        System.out.printf("%5s", integralPart);
        if (index >= 0) {
            // print fractional part
            System.out.print('.');
            int count = 0;
            for (int k = index+1; k < s.length(); ++k) {
                System.out.print(s.charAt(k));
                ++count;
                if (count == digits) break;
                if (count % 50 == 0) {
                    System.out.println();
                    System.out.print("      ");
                } else if (count % 5 == 0) {
                    System.out.print(' ');
                }
            }
            if (!round)
                System.out.print(" ...");
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        int nDigits;
        if (args.length == 0) {
            nDigits = 305;
        } else {
            nDigits = Integer.parseInt(args[0]);
        }
        MathContext mc = new MathContext(nDigits + GUARD_DIGITS , DEFAULT_ROUNDING_MODE);

        polyCoefficients = new int[] {144, -96, -6440, 3880, 88953, -49120, -382388, 205872, 494494, -270720, 55140, -5400,  225};
        derivativeCoefficients = calculateDerivative(polyCoefficients);
        BigDecimal x0 = new BigDecimal("0.217500597");
        int maxIter = 16 + (int)round(log(nDigits));
        BigDecimal u =
            newton(x -> f(x), x -> d(x), x0, negPowerOfTen(nDigits + 10), negPowerOfTen(nDigits + 20), maxIter, mc);
        if (u != null) {
            final int[] vCoefficients = new int[] {1049256, -416084, -26192402, 10116609, 122986520, -44541932, -148893612, 53193382, -2841840, -1067880, 190350, -10575};
            final int vDenominator = 54272;
            BigDecimal v = horner(vCoefficients, u).divide(BigDecimal.valueOf(vDenominator), mc);
            BigDecimal walkingTime = time(u, v, mc);

            System.out.println();
            System.out.println("  u = " + u);
            System.out.println("  v = " + v);
            System.out.println("  t = " + walkingTime);
            System.out.println();

            BigDecimal x1 = u.scaleByPowerOfTen(2);
            BigDecimal x2 = v.scaleByPowerOfTen(2);

            print(" x1 = ", x1, nDigits);
            print(" x2 = ", x2, nDigits);
            print("  t = ", time(u, v, mc), nDigits);
            System.out.println();

            printBigDecimal("x1 =", x1, nDigits, false);
            printBigDecimal("x2 =", x2, nDigits, false);
            printBigDecimal("t =", walkingTime, nDigits, false);
        } else {
            System.out.println("Newton method failed to converge.");
        }
    }
}
