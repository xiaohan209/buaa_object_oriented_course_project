import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Term {
    private ArrayList<Factor> factors;
    private boolean warn = false;
    private BigInteger coeff;

    public Term(Term termi) {
        factors = new ArrayList<Factor>();
        coeff = termi.getCoeff();
        for (int i = 0;i < termi.getFactors().size();i++) {
            Factor factor = termi.getFactors().get(i);
            Factor newFactor;
            switch (factor.getType()) {
                case 0:
                    newFactor = new Constant(((Constant)factor).getCoeff());
                    break;
                case 1:
                    newFactor = new Power(((Power)factor).getExp());
                    break;
                case 2:
                    newFactor = new Matry(((Matry)factor).getInsideRegex(),
                            ((Matry)factor).getExp());
                    break;
                case 3:
                    newFactor = new Sin(((Sin)factor).getInsideFact(),((Sin)factor).getExp());
                    break;
                case 4:
                    newFactor = new Cos(((Cos)factor).getInsideFact(),((Cos)factor).getExp());
                    break;
                default:
                    newFactor = new Constant(new BigInteger("0"));
            }
            factors.add(newFactor);
        }
    }

    public ArrayList<Factor> getFactors() {
        return factors;
    }

    public String out() {
        StringBuilder outPut = new StringBuilder();
        if (factors.isEmpty()) {
            return coeff.toString();
        }
        if (coeff.equals(BigInteger.ONE)) {
            outPut.append(factors.get(0).out());
            for (int i = 1;i < factors.size();i++) {
                outPut.append("*" + factors.get(i).out());
            }
        }
        else if (coeff.equals(BigInteger.ZERO.subtract(BigInteger.ONE))) {
            outPut.append("-" + factors.get(0).out());
            for (int i = 1;i < factors.size();i++) {
                outPut.append("*" + factors.get(i).out());
            }
        }
        else {
            outPut.append(coeff.toString());
            for (int i = 0;i < factors.size();i++) {
                outPut.append("*" + factors.get(i).out());
            }
        }
        return outPut.toString();
    }

    public boolean getWarn() {
        boolean warn = this.warn;
        if (warn) {
            return true;
        }
        for (int i = 0; i < factors.size(); i++) {
            warn = warn || factors.get(i).getWarn();
        }
        return warn;
    }

    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    public Regex dif() {
        Regex newRegex = new Regex();
        for (int i = 0; i < factors.size(); i++) {
            Term fac = factors.get(i).dif();
            Constant con = new Constant(coeff);
            fac.add(con);
            for (int j = 0; j < factors.size(); j++) {
                if (j == i) {
                    continue;
                }
                fac.add(factors.get(j));
            }
            newRegex.add(fac);
        }
        return newRegex;
    }

    public boolean isSameTerm(Term t) {
        if (t.getFactors().size() != factors.size()) {
            return false;
        }
        if (!t.getCoeff().equals(this.getCoeff())) {
            return false;
        }
        for (int i = 0;i < t.getFactors().size();i++) {
            Factor facti = factors.get(i);
            Factor factj = t.getFactors().get(i);
            if (!facti.plusFactor(factj)) {
                return false;
            }
        }
        return true;
    }

    public void add(Factor fact) {
        if (fact.getType() == 0) {
            setCoeff(getCoeff().multiply(((Constant)fact).getCoeff()));
            return;
        }
        if (factors.isEmpty()) {
            factors.add(fact);
            return;
        }
        for (int i = 0; i < factors.size(); i++) {
            if (factors.get(i).multiFactor(fact)) {
                factors.get(i).merge(fact);
                return;
            }
        }
        factors.add(fact);
    }

    public Term(ArrayList<Factor> factors) {
        this.factors = factors;
        this.coeff = BigInteger.ONE;
    }

    public BigInteger getCoeff() {
        standard();
        return coeff;
    }

    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }

    public void standard() {
        for (int i = 0; i < factors.size(); i++) {
            Factor facti = factors.get(i);
            if (facti.getType() == 2) {
                ((Matry)facti).getInsideRegex().standard();
            }
        }
        Comparator compType = new Comparator<Factor>() {
            public int compare(Factor f1,Factor f2) {
                if (f1.getType() == f2.getType()) {
                    return f1.hashCode() - f2.hashCode();
                }
                return f1.getType() - f2.getType();
            }
        };
        Collections.sort(factors,compType);
    }
}
