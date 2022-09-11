import java.math.BigInteger;

public class Power {
    private BigInteger coeff;
    private BigInteger exp;

    public BigInteger getCoeff() {
        return coeff;
    }

    public BigInteger getExp() {
        return exp;
    }

    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }

    public void setExp(BigInteger exp) {
        this.exp = exp;
    }

    public String print(boolean first) {
        String outPut = "";
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger miOne = new BigInteger("-1");
        if (coeff.equals(zero)) {
            outPut = "";
        }
        else if (exp.equals(zero)) {
            if (coeff.compareTo(zero) > 0 && first) {
                outPut = coeff.toString();
            }
            else if (coeff.compareTo(zero) > 0) {
                outPut = "+" + coeff.toString();
            }
            else {
                outPut = coeff.toString();
            }
        }
        else if (coeff.equals(one)) {
            if (exp.equals(one) && first) {
                outPut = "x";
            }
            else if (exp.equals(one)) {
                outPut = "+x";
            }
            else if (first) {
                outPut = "x**" + exp.toString();
            }
            else {
                outPut = "+x**" + exp.toString();
            }
        }
        else if (coeff.equals(miOne) && exp.equals(one)) {
            outPut = "-x";
        }
        else if (coeff.equals(miOne)) {
            outPut = "-x**" + exp.toString();
        }
        else if (exp.equals(one)) {
            if (coeff.compareTo(zero) < 0) {
                outPut = coeff.toString() + "*x";
            }
            else if (first) {
                outPut = coeff.toString() + "*x";
            }
            else {
                outPut = "+" + coeff.toString() + "*x";
            }
        }
        else {
            outPut = printf(first);
        }
        return outPut;
    }

    public String printf(boolean first) {
        String outPut = "";
        BigInteger zero = new BigInteger("0");
        if (coeff.compareTo(zero) < 0) {
            outPut = coeff.toString() + "*x**" + exp.toString();
        }
        else if (first) {
            outPut = coeff.toString() + "*x**" + exp.toString();
        }
        else {
            outPut = "+" + coeff.toString() + "*x**" + exp.toString();
        }
        return outPut;
    }

    public Power(String coeff,String exp) {
        this.coeff = new BigInteger(coeff);
        this.exp = new BigInteger(exp);
    }

    public Power(BigInteger coeff,BigInteger exp) {
        this.coeff = coeff;
        this.exp = exp;
    }

    public Power() {
        coeff = new BigInteger("1");
        exp = new BigInteger("1");
    }

    public Power derivation() {
        BigInteger one = new BigInteger("1");
        BigInteger newCoefficient = coeff.multiply(exp);
        BigInteger newExponent = exp.subtract(one);
        return new Power(newCoefficient,newExponent);
    }
}
