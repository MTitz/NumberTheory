import static java.lang.Math.*;
import junit.framework.TestCase;

public class ComplexTest extends TestCase {
    public static final double DELTA = 1e-12;

    private static void assertEquals(Complex expected, Complex actual, double delta)
    {
        assertEquals(expected.real(), actual.real(), delta);
        assertEquals(expected.imag(), actual.imag(), delta);
    }


    public void testAdd() {
        assertEquals(new Complex(1, 1), Complex.ONE.add(Complex.I));
        assertEquals(new Complex(4, 1), Complex.I.add(4.0));
        assertEquals(new Complex(5, 4), new Complex(3, 5).add(new Complex(2, -1)));
    }

    public void testSubtract() {
        assertEquals(new Complex(1, -1), Complex.ONE.subtract(Complex.I));
        assertEquals(new Complex(-4, 1), Complex.I.subtract(4.0));
    }

    public void testMultiply() {
        assertEquals(new Complex( 2, 2), new Complex(1, 1).multiply(2));
        assertEquals(new Complex(10, 4), new Complex(1, 1).multiply(new Complex(7, -3)));
        assertEquals(new Complex( 5, 1), new Complex(2, 3).multiply(new Complex(1, -1)));
    }

    public void testDivide() {
        assertEquals(new Complex(0.5, 0.5), new Complex(1, 1).divide(2.0));
        assertEquals(new Complex(0.7, -0.1), new Complex(3, 1).divide(new Complex(4, 2)), DELTA);
    }

    public void testSqrt() {
        assertEquals(new Complex(3.0, 0.0), Complex.sqrt(new Complex(9.0)), DELTA);
        assertEquals(new Complex(0.0, 3.0), Complex.sqrt(new Complex(-9.0)), DELTA);
        assertEquals(new Complex(sqrt(2.0)/2.0, sqrt(2.0)/2.0), Complex.sqrt(Complex.I), DELTA);
    }

    public void testPow() {
        assertEquals(new Complex(-1.0, 0.0), Complex.pow(Complex.I, 2), DELTA);
        assertEquals(new Complex(+1.0, 0.0), Complex.pow(Complex.I, 4), DELTA);
        assertEquals(new Complex(0.20787957635076193), Complex.pow(Complex.I, Complex.I), DELTA);
        assertEquals(new Complex(exp(-PI/2.0)), Complex.pow(Complex.I, Complex.I), DELTA);
    }

    public void testToString() {
        assertEquals("(0.0 + 0.0i)", Complex.ZERO.toString());
        assertEquals("(1.0 + 0.0i)", Complex.ONE.toString());
        assertEquals("(1.0 + 1.0i)", Complex.ONE.add(Complex.I).toString());
        assertEquals("(1.0 - 1.0i)", Complex.ONE.subtract(Complex.I).toString());
    }
}
