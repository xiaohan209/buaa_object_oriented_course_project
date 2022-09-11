import java.math.BigInteger;
import java.util.Objects;

public class Poly implements Comparable {
    private BigInteger coeff;
    private BigInteger index;

    public Poly(BigInteger coeff, BigInteger index) {
        this.coeff = coeff;
        this.index = index;
    }

    public BigInteger getCoeff() {
        return coeff;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void addCoef(BigInteger coef) {
        this.coeff = this.coeff.add(coef);
    }

    public Poly derivative() {
        Poly tmpPoly;
        if (index.equals(BigInteger.ZERO) | coeff.equals(BigInteger.ZERO)) {
            tmpPoly = new Poly(BigInteger.ZERO, BigInteger.ZERO);
        } else {
            BigInteger muti = coeff.multiply(index);
            BigInteger tmpindex = index.subtract(BigInteger.ONE);
            tmpPoly = new Poly(muti, tmpindex);
        }
        return tmpPoly;
    }

    @Override
    public String toString() {
        String str = "";
        BigInteger negateone = BigInteger.ONE.negate();
        if (coeff.equals(BigInteger.ZERO)) {
            // do nothing
        } else if (index.equals(BigInteger.ZERO)) {
            str += coeff;
        } else if (coeff.equals(BigInteger.ONE) && !index.equals(BigInteger.ONE)) {
            str += "x**" + index;
        } else if (coeff.equals(BigInteger.ONE) && index.equals(BigInteger.ONE)) {
            str += "x";
        } else if (coeff.equals(negateone) && !index.equals(BigInteger.ONE)) {
            str += "-x**" + index;
        } else if (coeff.equals(negateone) && index.equals(BigInteger.ONE)) {
            str += "-x";
        } else if (index.equals(BigInteger.ONE)) {
            str += coeff + "*x";
        } else {
            str += coeff + "*x**" + index;
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Poly) {
            Poly p = (Poly) o;
            return this.getIndex().equals(p.getIndex());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public int compareTo(Object o) {
        Poly p = (Poly) o;
        return this.index.compareTo(p.index);
    }
}
