import java.math.BigInteger;
import java.util.ArrayList;

public class Polynomial {

    private ArrayList<Term> polynomial;

    public Polynomial(ArrayList<Term> polynomial) {
        this.polynomial = polynomial;
    }

    public void addTerm(Term newTerm) {
        for (Term term : this.polynomial) {
            if (term.getIndex().compareTo(newTerm.getIndex()) == 0) {
                term.add(newTerm);
                return;
            }
        }
        this.polynomial.add(newTerm);
    }

    public Polynomial derivative() {
        ArrayList<Term> newPoly = new ArrayList<>();
        for (Term term : this.polynomial) {
            newPoly.add(term.derivative());
        }
        return new Polynomial(newPoly);
    }

    private void changeTheFirst() {
        Term firstOne = this.polynomial.get(0);
        if (firstOne.getCoefficient().compareTo(BigInteger.ZERO) <= 0) {

            for (int i = 1; i < this.polynomial.size(); i++) {
                if (this.polynomial.get(i).getCoefficient().compareTo(BigInteger.ZERO) > 0) {
                    Term temp = new Term(firstOne.getCoefficient(), firstOne.getIndex());
                    this.polynomial.set(0, this.polynomial.get(i));
                    this.polynomial.set(i, temp);
                    return;
                }
            }

        }
    }

    @Override
    public String toString() {
        String out = "";
        this.changeTheFirst();
        for (Term term : this.polynomial) {
            out = out + term.toString();
        }
        return out;
    }

}
