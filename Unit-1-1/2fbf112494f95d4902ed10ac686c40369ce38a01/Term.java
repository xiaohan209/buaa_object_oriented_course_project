import java.math.BigInteger;

public class Term {

    private BigInteger coefficient;
    private BigInteger index;

    public Term(BigInteger coefficient, BigInteger index) {
        this.coefficient = coefficient;
        this.index = index;
    }

    public BigInteger getIndex() {
        return index;
    }

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public boolean add(Term term) {
        if (term.getIndex().compareTo(this.index) == 0) {
            this.coefficient = this.coefficient.add(term.getCoefficient());
            return true;
        }
        else {
            return false;
        }
    }

    public Term derivative() {
        if (this.index.compareTo(BigInteger.ZERO) == 0) {
            return new Term(BigInteger.ZERO, BigInteger.ZERO);
        }
        else {
            BigInteger newIndex = this.index.add(new BigInteger("-1"));
            BigInteger newCoefficient = this.coefficient.multiply(this.index);
            return new Term(newCoefficient, newIndex);
        }
    }

    @Override
    public String toString() {
        String out = "";
        if (this.coefficient.compareTo(BigInteger.ZERO) == 0) {
            return "";
        }
        if (this.index.compareTo(BigInteger.ZERO) == 0) {
            if (this.coefficient.compareTo(BigInteger.ZERO) > 0) {
                out = out + "+" + this.coefficient.toString();
            }
            else {
                out = out + this.coefficient.toString();
            }
        }
        else {
            if (this.coefficient.compareTo(new BigInteger("1")) == 0) {
                out = out + "+x";
            }
            else if (this.coefficient.compareTo(new BigInteger("-1")) == 0) {
                out = out + "-x";
            }
            else {
                if (this.coefficient.compareTo(BigInteger.ZERO) > 0) {
                    out = out + "+" + this.coefficient.toString() + "*x";
                }
                else {
                    out = out + this.coefficient.toString() + "*x";
                }
            }
            if (this.index.compareTo(new BigInteger("1")) != 0) {
                out = out + "**" + this.index.toString();
            }
        }
        return out;
    }

    //    public static void main(String[] args) {
    //        Term term = new Term(new BigInteger("-1"), new BigInteger("-1"));
    //        Term term1= term.derivative();
    //        System.out.println(term.toString());
    //        System.out.println(term1.toString());
    //    }

}
