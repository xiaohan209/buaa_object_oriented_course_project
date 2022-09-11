import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sin extends Factor {
    private Factor insideFact;
    private BigInteger exp;

    public String out() {
        StringBuilder outPut = new StringBuilder();
        if (exp.equals(BigInteger.ZERO)) {
            outPut.append("1");
        }
        if (exp.equals(BigInteger.ONE)) {
            outPut.append("sin(" + insideFact.out() + ")");
        }
        else {
            outPut.append("sin(" + insideFact.out() + ")**" + exp.toString());
        }
        return outPut.toString();
    }

    public Term dif() {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        Term term;
        if (exp.equals(zero)) {
            ArrayList<Factor> factors = new ArrayList<Factor>();
            term = new Term(factors);
            Factor conFact = new Constant(zero);
            term.add(conFact);
        }
        else if (exp.equals(one)) {
            term = insideFact.dif();
            Factor cosFact = new Cos(insideFact,one);
            term.add(cosFact);
        }
        else {
            term = insideFact.dif();
            Factor conFact = new Constant(exp);
            Factor sinFact = new Sin(insideFact,exp.subtract(one));
            Factor cosFact = new Cos(insideFact,one);
            term.add(sinFact);
            term.add(cosFact);
            term.add(conFact);
        }
        return term;
    }

    public Factor getInsideFact() {
        return insideFact;
    }

    public Sin(String text) {
        super(3);
        String white = "[ \\t]*";//space
        String realNumber = "(?<fuHao>[+-])?(?<number>\\d+)";
        String expo = white + "\\*\\*" + white + realNumber; //zhishu
        String sin = "(sin" + white + "l(?<inside>.+?)r)(" + expo + ")?";
        Pattern sinRegex = Pattern.compile(sin);
        Matcher sinMatcher = sinRegex.matcher(text);
        if (sinMatcher.find()) {
            boolean flag;
            String fuHao = sinMatcher.group("fuHao");
            if (fuHao == null) {
                flag = false;
            }
            else if (fuHao.equals("+")) {
                flag = false;
            }
            else {
                flag = true;
            }
            String number = sinMatcher.group("number");
            if (number == null) {
                number = "1";
            }
            if (flag) {
                number = "-" + number;
            }
            exp = new BigInteger(number);
            String inside = sinMatcher.group("inside");
            Extract ext = new Extract(inside);
            boolean flag1 = ext.preset();
            Split split = new Split(ext.getText());
            Factor inFact = split.newFactor(ext.getText());
            insideFact = inFact;
            this.setWarn(inFact.getWarn() || flag1);
            BigInteger fifty = new BigInteger("50");
            if (!exp.abs().max(fifty).equals(fifty)) {
                setWarn(true);
            }
        }

    }

    public Sin(Factor insideFact,BigInteger exp) {
        super(3);
        this.insideFact = insideFact;
        this.exp = exp;
    }

    public boolean plusFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (!this.exp.equals(((Sin)fact).getExp())) {
            return false;
        }
        return this.insideFact.isSame(((Sin)fact).getInsideFact());
    }

    public boolean multiFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        return this.insideFact.isSame(((Sin)fact).getInsideFact());
    }

    public boolean isSame(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (exp.equals(((Sin)fact).exp)) {
            return false;
        }
        return insideFact.isSame(((Sin)fact).insideFact);
    }

    public BigInteger getExp() {
        return exp;
    }

    public void setExp(BigInteger exp) {
        this.exp = exp;
    }

    public void merge(Factor fact) {
        setExp(exp.add(((Sin)fact).getExp()));
        setWarn(getWarn() || fact.getWarn());
    }
}
