import java.math.BigInteger;

public class Poly implements Comparable<Poly> {
    private BigInteger degree;
    private BigInteger coeff;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    
    public Poly(BigInteger coeff,BigInteger degree) {
        this.degree = degree;
        this.coeff = coeff;
    }
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public BigInteger getDegree() {
        return degree;
    }
    
    public void add(BigInteger value) {
        this.coeff = this.coeff.add(value);
    }
    
    public void add(String value) {
        this.coeff.add(new BigInteger(value));
    }
    
    public void diff() {
        if (degree.equals(ZERO)) {
            coeff = ZERO;
        } else {
            coeff = coeff.multiply(degree);
            degree = degree.add(MINUS_ONE);
        }
        // merge those zero terms
        if (coeff.equals(ZERO)) {
            degree = ZERO;
        }
    }
    
    @Override
    public String toString() {
        String output;
        // Check if exponent equals zero
        if (degree.equals(ZERO)) {
            output = coeff.toString();
            return output;
        }
        // Check if coefficient equals zero
        if (coeff.equals(ZERO)) {
            return "0";
        }
        
        // Check if the coefficient can be omitted
        if (coeff.equals(ONE)) {
            output = "x";
        } else if (coeff.equals(MINUS_ONE)) {
            output = "-x";
        } else {
            output = coeff.toString(10) + "*x";
        }
        // Check if the exponent can be omitted
        if (!degree.equals(ONE)) {
            output += "**" + degree.toString(10);
        }
        return output;
    }
    
    @Override
    public boolean equals(Object obj) {
        // Expect: the object is an instance of class Poly
        // Effect: if the degree is the same, they are equal
        Poly other = (Poly) obj;
        if (this.degree.equals(other.degree)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return degree.toString().hashCode();
    }
    
    @Override
    public int compareTo(Poly o) {
        // Put terms whose coeff is greater or equal than 0 to the front
        // Then sort by degree
        if ((this.coeff.compareTo(ZERO) < 0 && o.coeff.compareTo(ZERO) < 0)
            || (this.coeff.compareTo(ZERO) > 0 && o.coeff.compareTo(ZERO) > 0)) {
            return o.degree.compareTo(this.degree);
        } else {
            return o.coeff.compareTo(ZERO);
        }
    }
}
