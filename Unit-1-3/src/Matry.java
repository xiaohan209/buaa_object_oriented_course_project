import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matry extends Factor {
    private Regex insideRegex;
    private BigInteger exp;

    public BigInteger getExp() {
        return exp;
    }

    public void setExp(BigInteger exp) {
        this.exp = exp;
    }

    public String out() {
        StringBuilder outPut = new StringBuilder();
        if (exp.equals(BigInteger.ZERO)) {
            outPut.append("1");
        }
        else {
            String result = insideRegex.out();
            if (result.isEmpty()) {
                result = "0";
            }
            if (canBeSimp()) {
                outPut.append(result);
            }
            else {
                outPut.append("(" + result + ")");
            }
            for (int i = 1; i < exp.intValue(); i++) {
                if (canBeSimp()) {
                    outPut.append("*" + result);
                }
                else {
                    outPut.append("*(" + result + ")");
                }
            }
        }
        return outPut.toString();
    }

    public boolean canBeSimp() {
        boolean flag = false;
        if (insideRegex.getPoly().size() == 1) {
            Term term = insideRegex.getPoly().get(0);
            if (term.getFactors().size() == 1 && term.getCoeff().equals(BigInteger.ONE)) {
                if (term.getFactors().get(0).getType() == 0) {
                    Constant con = (Constant)term.getFactors().get(0);
                    if (con.getCoeff().min(BigInteger.ZERO).equals(BigInteger.ZERO)) {
                        flag = true;
                    }
                    else {
                        flag = false;
                    }
                }
                else {
                    flag = true;
                }
            }
            else if (term.getFactors().size() == 0) {
                flag = true;
            }
            else {
                flag = false;
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    public Term dif() {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        ArrayList<Factor> factors = new ArrayList<Factor>();
        Term term = new Term(factors);
        if (exp.equals(zero)) {
            Factor conFact = new Constant(zero);
            term.add(conFact);
        }
        else if (exp.equals(one)) {
            Factor matryFact = new Matry(insideRegex.dif(),one);
            term.add(matryFact);
        }
        else {
            Factor conFact = new Constant(exp);
            Factor matryFact1 = new Matry(insideRegex,exp.subtract(one));
            Factor matryFact2 = new Matry(insideRegex.dif(),one);
            term.add(conFact);
            term.add(matryFact1);
            term.add(matryFact2);
        }
        return term;
    }

    public Matry(String text) {
        super(2);
        this.exp = new BigInteger("1");
        String regFact = "l(?<inside>.+?)r";
        Pattern matryRegex = Pattern.compile(regFact);
        Matcher matryMatcher = matryRegex.matcher(text);
        if (matryMatcher.find()) {
            String inside = matryMatcher.group("inside");
            Extract newExtrach = new Extract(inside);
            Regex newregex = newExtrach.getRegex();
            insideRegex = newregex;
            this.setWarn(newregex.getWarn());
        }
    }

    public Matry(Regex regex,BigInteger exp) {
        super(2);
        this.insideRegex = new Regex(regex);
        this.exp = exp;
    }

    public boolean plusFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (!exp.equals(((Matry)fact).getExp())) {
            return false;
        }
        return this.insideRegex.isSameRegex(((Matry)fact).getInsideRegex());
    }

    public boolean multiFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        return this.insideRegex.isSameRegex(((Matry)fact).getInsideRegex());
    }

    public boolean isSame(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (exp.equals(((Matry)fact).exp)) {
            return false;
        }
        return insideRegex.isSameRegex(((Matry)fact).insideRegex);
    }

    public Regex getInsideRegex() {
        return insideRegex;
    }

    public void merge(Factor fact) {
        setExp(exp.add(((Matry)fact).getExp()));
        setWarn(getWarn() || fact.getWarn());
    }

}
