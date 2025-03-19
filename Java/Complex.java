/* Complex Numbers */
/* Reference:
[AS] M. Abramowitz, I.A. Stegun, editors, Handbook of Mathematical Functions.
     United States Government Printing Office, 1964.
*/


public final class Complex {
    /** The Complex constant zero. */
    public static final Complex ZERO = new Complex(0.0, 0.0);
    /** The Complex constant one. */
    public static final Complex ONE  = new Complex(1.0, 0.0);
    /** The Complex constant i (the imaginary unit). */
    public static final Complex I    = new Complex(0.0, 1.0);

    /** Constructs a newly allocated <code>Complex</code> object that represents the specified <code>double</code> values. */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /** Constructs a newly allocated <code>Complex</code> object that represents the specified <code>double</code> value. */
    public Complex(double re) {
        this.re = re;
        this.im = 0.0;
    }

    /** Returns the real part of a <code>Complex</code> number. */
    public double real() { return re; }

    /** Returns the imaginary part of a <code>Complex</code> number. */
    public double imag() { return im; }

    /** Returns a Complex number whose value is <code>(this + d)</code>. */
    public Complex add(double d) {
        return new Complex(re + d, im);
    }

    /** Returns a Complex number whose value is <code>(this + c)</code>. */
    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    /** Returns a Complex number whose value is <code>(this - d)</code>. */
    public Complex subtract(double d) {
        return new Complex(re - d, im);
    }

    /** Returns a Complex number whose value is <code>(this - c)</code>. */
    public Complex subtract(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    /** Returns a Complex number whose value is <code>(this * d)</code>. */
    public Complex multiply(double d) {
        return new Complex(re * d, im * d);
    }

    /** Returns a Complex number whose value is <code>(this * c)</code>. */
    public Complex multiply(Complex c) {
        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }

    /** Returns a Complex number whose value is <code>(this / d)</code>. */
    public Complex divide(double d) {
        return new Complex(re / d, im / d);
    }

    /** Returns a Complex number whose value is <code>(this / c)</code>. */
    public Complex divide(Complex c) {
        double denominator = c.re * c.re + c.im * c.im;
        if (denominator == 0.0) {
            throw new ArithmeticException("Division by zero in Complex division");
        }
        return new Complex((re * c.re + im * c.im) / denominator,
                           (c.re * im - re * c.im) / denominator);
    }

    /** @deprecated */
    @Deprecated public double arg() {
        return Math.atan2(im, re);
    }

    public static double arg(Complex z) {
        return Math.atan2(z.im, z.re);
    }

    /** @deprecated */
    @Deprecated public Complex negate() {
        return new Complex(-re, -im);
    }

    /** Returns a Complex number whose value is <code>(-this)</code>. */
    public static Complex negate(Complex z) {
        return new Complex(-z.re, -z.im);
    }

    /** @deprecated */
    @Deprecated public Complex conj() {
        return new Complex(re, -im);
    }

    public static Complex conj(Complex z) {
        return new Complex(z.re, -z.im);
    }

    /** Returns the absolute value of a <code>Complex</code> number. */
    public static double abs(Complex z)
    {
        return Math.hypot(z.re, z.im);
    }

    /** Returns the square root of a <code>Complex</code> number. */
    public static Complex sqrt(Complex z)
    {
        if (z.im == 0.0) {
            if (z.re >= 0.0)
                return new Complex(Math.sqrt(z.re), 0.0);
            else
                return new Complex(0.0, Math.sqrt(-z.re));
        } else {
            double u = Math.sqrt(0.5 * (abs(z) + z.re));
            return new Complex(u, 0.5 * z.im / u);
        }
    }

    /** Returns the exponential number e raised to the power of a <code>Complex</code> value.*/
    public static Complex exp(Complex z)
    {
        double temp = Math.exp(z.re);
        return new Complex(temp * Math.cos(z.im), temp * Math.sin(z.im));
    }

    /** Returns the natural logarithm of a <code>Complex</code> value. */
    public static Complex log(Complex z)
    {
        if (z.re == 0.0 && z.im == 0.0) {
            throw new IllegalArgumentException("Logarithm of zero is undefined for Complex numbers");
        }
        return new Complex(0.5 * Math.log(z.re * z.re + z.im * z.im),
                           Math.atan2(z.im, z.re));
    }

    /** Returns the value of the first argument raised to the power of the second argument. */
    public static Complex pow(Complex a, double x)
    {
        if (a.re == 0.0 && a.im == 0.0 && x < 0.0) {
            if (x > 0.0) {
                return Complex.ZERO;
            } else if (x == 0.0) {
                return Complex.ONE;
            } else {
                throw new IllegalArgumentException("Cannot raise zero to a negative power");
            }
        }
        return Complex.exp(Complex.log(a).multiply(x));
    }

    /** Returns the value of the first argument raised to the power of the second argument. */
    public static Complex pow(Complex a, Complex z)
    {
        if (a.re == 0.0 && a.im == 0.0) {
            throw new IllegalArgumentException("Cannot raise zero to a complex power");
        }
        return Complex.exp(z.multiply(Complex.log(a)));
    }

    /** Returns the trigonometric sine of a <code>Complex</code> value.*/
    /* reference: [AS] 4.3.55 */
    public static Complex sin(Complex z) {
        return new Complex(Math.sin(z.re) * Math.cosh(z.im),
                           Math.cos(z.re) * Math.sinh(z.im));
    }

    /** Returns the trigonometric cosine of a <code>Complex</code> value.*/
    /* reference: [AS] 4.3.56 */
    public static Complex cos(Complex z) {
        return new Complex(Math.cos(z.re) * Math.cosh(z.im),
                          -Math.sin(z.re) * Math.sinh(z.im));
    }

    /** Returns the trigonometric tangent of a <code>Complex</code> value.*/
    /* reference: [AS] 4.3.57 */
    public static Complex tan(Complex z) {
        double x2 = 2.0 * z.re;
        double y2 = 2.0 * z.im;
        double denominator = Math.cos(x2) + Math.cosh(y2);
        return new Complex(Math.sin(x2)  / denominator,
                           Math.sinh(y2) / denominator);
    }

    /** Returns the hyperbolic sine of a <code>Complex</code> value.*/
    /* reference: [AS] 4.5.49 */
    public static Complex sinh(Complex z) {
        return new Complex(Math.sinh(z.re) * Math.cos(z.im),
                           Math.cosh(z.re) * Math.sin(z.im));
    }

    /** Returns the hyperbolic cosine of a <code>Complex</code> value.*/
    /* reference: [AS] 4.5.50 */
    public static Complex cosh(Complex z) {
        return new Complex(Math.cosh(z.re) * Math.cos(z.im),
                           Math.sinh(z.re) * Math.sin(z.im));
    }

    /** Returns the hyperbolic tangent of a <code>Complex</code> value.*/
    /* reference: [AS] 4.5.51 */
    public static Complex tanh(Complex z) {
        double x2 = 2.0 * z.re;
        double y2 = 2.0 * z.im;
        double denominator = Math.cosh(x2) + Math.cos(y2);
        return new Complex(Math.sinh(x2) / denominator,
                           Math.sin(y2)  / denominator);
    }

    public @Override boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex c = (Complex)o;
        return (Double.doubleToLongBits(re) ==
                Double.doubleToLongBits(c.re)) &&
               (Double.doubleToLongBits(im) ==
                Double.doubleToLongBits(c.im));
    }

    public @Override String toString() {
        return im >= 0
            ? "(" + re + " + " + im + "i)"
            : "(" + re + " - " + (-im) + "i)";
    }

    private final double re;
    private final double im;
}
