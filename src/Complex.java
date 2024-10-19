public class Complex {
    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.real + c2.real, c1.imag + c2.imag);
    }

    public static Complex subtract(Complex c1, Complex c2) {
        return new Complex(c1.real - c2.real, c1.imag - c2.imag);
    }

    public static Complex multiply(Complex c1, Complex c2) {
        double realPart = c1.real * c2.real - c1.imag * c2.imag;
        double imagPart = c1.real * c2.imag + c1.imag * c2.real;
        return new Complex(realPart, imagPart);
    }

    public Complex negative() {
        return new Complex(-this.real, -this.imag);
    }

    public static Complex divide(Complex c1, Complex c2) throws ArithmeticException {
        double denominator = c2.real * c2.real + c1.imag * c1.imag;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль!");
        }
        double realPart = (c1.real * c2.real + c1.imag * c2.imag) / denominator;
        double imagPart = (c1.imag * c2.real - c1.real * c2.imag) / denominator;
        return new Complex(realPart, imagPart);
    }


    @Override
    public String toString() {
        if (imag >= 0) {
            return real + " + " + imag + "i";
        } else {
            return real + " - " + (-imag) + "i";
        }
    }

}
