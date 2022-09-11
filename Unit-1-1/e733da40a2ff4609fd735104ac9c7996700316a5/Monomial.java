import java.math.BigInteger;

public class Monomial {
    private BigInteger exponent;
    private BigInteger coefficient;

    public Monomial(String x, String k) {
        exponent = new BigInteger(x);
        coefficient = new BigInteger(k);
    }

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public void setCoefficient(BigInteger x) {
        coefficient = x;
    }

    public void derivative() {
        if (exponent.toString().equals("0")) {
            coefficient = new BigInteger("0");
            return;
        }
        coefficient = exponent.multiply(coefficient);
        exponent = exponent.subtract(new BigInteger("1"));
    }

}
