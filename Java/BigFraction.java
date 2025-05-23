import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class BigFraction extends Number implements Comparable<BigFraction>
{
    /** The BigFraction constant zero. */
    public static final BigFraction ZERO = new BigFraction();
    /** The BigFraction constant one. */
    public static final BigFraction ONE  = new BigFraction(BigInteger.ONE, BigInteger.ONE);
    /** The default rounding mode for the BigFraction class. */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public BigFraction()
    {
        this.numerator = BigInteger.ZERO;
        this.denominator = BigInteger.ONE;
    }

    /** Constructs a newly allocated <code>BigFraction</code> object that represents a fraction of the specified <code>BigInteger</code> values. */
    public BigFraction(BigInteger numerator, BigInteger denominator)
    {
        int denominatorSignum = denominator.signum();
        if (denominatorSignum > 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else if (denominatorSignum == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        } else {
            this.numerator = numerator.negate();
            this.denominator = denominator.negate();
        }
    }

    /** Constructs a newly allocated <code>BigFraction</code> object that represents a fraction of the specified <code>long</code> values. */
    public BigFraction(long numerator, long denominator)
    {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        } else if (denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }

    /** Returns the numerator of a <code>BigFraction</code> number. */
    public BigInteger getNumerator()
    {
        return numerator;
    }

    /** Returns the denominator of a <code>BigFraction</code> number. */
    public BigInteger getDenominator()
    {
        return denominator;
    }

    /** Compares this <code>BigFraction</code> with the specified <code>BigFraction</code>. */
    @Override public int compareTo(BigFraction val)
    {
        return this.numerator.multiply(val.denominator).compareTo(this.denominator.multiply(val.numerator));
    }

    /** Converts this <code>BigFraction</code> to a <code>BigDecimal</code> with given <code>scale</code> and <code>RoundingMode roundingMode</code>. */
    public BigDecimal bigDecimalValue(int scale, RoundingMode roundingMode)
    {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), scale, roundingMode);
    }

    /** Converts this <code>BigFraction</code> to a <code>BigDecimal</code> with given <code>scale</code> and default <code>RoundingMode</code>. */
    public BigDecimal bigDecimalValue(int scale)
    {
        return bigDecimalValue(scale, DEFAULT_ROUNDING_MODE);
    }


    // Implementation of the methods from the abstract base class Number

    /** Converts this <code>BigFraction</code> to a <code>double</code>. */
    @Override public double doubleValue()
    {
        return bigDecimalValue(20).doubleValue();
    }

    /** Converts this <code>BigFraction</code> to a <code>float</code>. */
    @Override public float floatValue()
    {
        return bigDecimalValue(12).floatValue();
    }

    /** Converts this <code>BigFraction</code> to an <code>int</code>. */
    @Override public int intValue()
    {
        return bigDecimalValue(12).intValue();
    }

    /** Converts this <code>BigFraction</code> to a <code>long</code>. */
    @Override public long longValue()
    {
        return bigDecimalValue(20).longValue();
    }


    // Arithmetic operations

    /** Returns a BigFraction number whose value is <code>(this + val)</code>. */
    public BigFraction add(BigFraction val)
    {
        BigInteger numerator = this.numerator.multiply(val.denominator).add(this.denominator.multiply(val.numerator));
        BigInteger denominator = this.denominator.multiply(val.denominator);
        return cancel(numerator, denominator);
    }

    /** Returns a BigFraction number whose value is <code>(this - val)</code>. */
    public BigFraction subtract(BigFraction val)
    {
        BigInteger numerator = this.numerator.multiply(val.denominator).subtract(this.denominator.multiply(val.numerator));
        BigInteger denominator = this.denominator.multiply(val.denominator);
        return cancel(numerator, denominator);
    }

    /** Returns a BigFraction number whose value is <code>(this * val)</code>. */
    public BigFraction multiply(BigFraction val)
    {
        BigInteger numerator = this.numerator.multiply(val.numerator);
        BigInteger denominator = this.denominator.multiply(val.denominator);
        return cancel(numerator, denominator);
    }

	    /** Returns a BigFraction number whose value is <code>(this * val)</code> where <code>val</code> is a BigInteger. */
    public BigFraction multiply(BigInteger val)
    {
        return cancel(numerator.multiply(val), denominator);
    }

    /** Returns a BigFraction number whose value is <code>(this / val)</code>. */
    public BigFraction divide(BigFraction val)
    {
        BigInteger numerator = this.numerator.multiply(val.denominator);
        BigInteger denominator = this.denominator.multiply(val.numerator);
        int denominatorSignum = denominator.signum();
        if (denominatorSignum > 0) {
            return cancel(numerator, denominator);
        } else if (denominatorSignum == 0) {
            throw new ArithmeticException("Division by 0 in BigFraction class");
        } else {
            return cancel(numerator.negate(), denominator.negate());
        }
    }

    /** Returns a BigFraction number whose value is an integer power of the given <code>BigFraction</code> number. */
    public BigFraction pow(int exponent)
    {
        // assuming already cancelled fraction
        if (exponent > 0)
            return new BigFraction(numerator.pow(exponent), denominator.pow(exponent));
        else if (exponent == 0)
            return BigFraction.ONE;
        else
            return new BigFraction(denominator.pow(-exponent), numerator.pow(-exponent));
    }

    /** Returns the absolute value of a <code>BigFraction</code> number. */
    public BigFraction abs()
    {
        return new BigFraction(numerator.abs(), denominator.abs());
    }

    /** Returns a BigFraction number whose value is <code>(-this)</code>. */
    public BigFraction negate()
    {
        return new BigFraction(numerator.negate(), denominator);
    }

    /** Returns the signum function of this <code>BigFraction</code>. */
    public int signum()
    {
       return numerator.signum();
    }


    // Miscellaneous useful methods

    /** Translates a <code>long</code> into a <code>BigFraction</code> with denominator 1. */
    public static BigFraction valueOf(long val)
    {
        return new BigFraction(BigInteger.valueOf(val), BigInteger.ONE);
    }

    /** Compares this <code>BigFraction</code> number with the specified <code>Object</code> for equality.*/
    @Override public boolean equals(Object o)
    {
        if (o == null || !(o instanceof BigFraction))
            return false;
        BigFraction fraction = (BigFraction)o;
        return this.numerator.multiply(fraction.denominator).equals(this.denominator.multiply(fraction.numerator));
    }

    /** Returns the String representation of this <code>BigFraction</code> number.*/
    @Override public String toString()
    {
        return denominator.equals(BigInteger.ONE)
                ? numerator.toString()
                : numerator.toString() + " / " + denominator.toString();
    }

    /** Returns a BigFraction number that has been canceled. */
    public BigFraction cancel()
    {
        return cancel(numerator, denominator);
    }

    private static BigFraction cancel(BigInteger numerator, BigInteger denominator)
    {
        BigInteger gcd = numerator.gcd(denominator);
        return new BigFraction(numerator.divide(gcd), denominator.divide(gcd));
    }

    private final BigInteger numerator;
    private final BigInteger denominator;
}
