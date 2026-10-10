package complex;

public class Complex {
    private final double realPart;
    private final double imaginaryPart;

    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public Complex(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = 0;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public static Complex convertStringToComplex(String c) {
        if (c.contains("i")) {
            if (c.equals("i")) return new Complex(0, 1);
            if (c.equals("-i")) return new Complex(0, -1);
            if (c.contains("+i")) return new Complex(Double.parseDouble(c.split("[+]")[0]), 1.0);
            if (c.contains("-i") && !c.startsWith("-")) return new Complex(Double.parseDouble(c.split("[-]")[0]), -1.0);
            if (c.contains("+")) return new Complex(Double.parseDouble(c.split("[+]")[0]), Double.parseDouble(c.split("[+]")[1].replace("i", "")));
            if (c.contains("-") && !c.startsWith("-")) return new Complex(Double.parseDouble(c.split("[-]")[0]), Double.parseDouble(c.split("[-]")[1].replace("i", "")) * -1.0);
            return new Complex(0, Double.parseDouble(c.replace("i", "")));
        }
        return new Complex(Double.parseDouble(c));
    }

    public String convertComplexToNormal() {
        if (this.getRealPart() != 0 && this.getImaginaryPart() > 0) {
            return this.getRealPart() + "+" + this.getImaginaryPart() + "i";
        }
        if (this.getRealPart() != 0 && this.getImaginaryPart() < 0) {
            return this.getRealPart() + "" + this.getImaginaryPart() + "i";
        }
        if  (this.getRealPart() != 0) {
            return this.getRealPart() + "";
        }
        if (this.getImaginaryPart() != 0) {
            return this.getImaginaryPart() + "i";
        }
        return "0";
    }

    public static Complex multiply(Complex a, Complex b) {
        return new Complex(a.getRealPart() * b.getRealPart() + (a.getImaginaryPart() * b.getImaginaryPart() * -1),
                a.getRealPart() * b.getImaginaryPart() + b.getRealPart() * a.getImaginaryPart());
    }

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.getRealPart() + b.getRealPart(), a.getImaginaryPart() + b.getImaginaryPart());
    }

    public static Complex divide(Complex a, Complex b) {
        double d = b.getRealPart() * b.getRealPart()
                + b.getImaginaryPart() * b.getImaginaryPart();
        if (d == 0) {
            return null;
        }
        double real = (a.getRealPart() * b.getRealPart()
                + a.getImaginaryPart() * b.getImaginaryPart()) / d;

        double imaginary = (a.getImaginaryPart() * b.getRealPart()
                - a.getRealPart() * b.getImaginaryPart()) / d;

        return new Complex(real, imaginary);
    }
}
