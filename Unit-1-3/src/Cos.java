import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cos extends Factor {
    private Factor insideFact;
    private BigInteger exp;

    public String out() {
        StringBuilder outPut = new StringBuilder();
        if (exp.equals(BigInteger.ZERO)) {
            outPut.append("1");
        }
        if (exp.equals(BigInteger.ONE)) {
            outPut.append("cos(" + insideFact.out() + ")");
        }
        else {
            outPut.append("cos(" + insideFact.out() + ")**" + exp.toString());
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
        if (exp.equals(one)) {
            term = insideFact.dif();
            Factor conFact = new Constant(zero.subtract(one));
            Factor sinFact = new Sin(insideFact,one);
            term.add(conFact);
            term.add(sinFact);
        }
        else {
            term = insideFact.dif();
            Factor conFact = new Constant(zero.subtract(exp));
            Factor cosFact = new Cos(insideFact,exp.subtract(one));
            Factor sinFact = new Sin(insideFact,one);
            term.add(cosFact);
            term.add(sinFact);
            term.add(conFact);
        }
        return term;
    }

    public Cos(String text) {
        super(4);
        String white = "[ \\t]*";//space
        String realNumber = "(?<fuHao>[+-])?(?<number>\\d+)";
        String expo = white + "\\*\\*" + white + realNumber; //zhishu
        String cos = "(cos" + white + "l(?<inside>.+?)r)(" + expo + ")?";
        Pattern cosRegex = Pattern.compile(cos);
        Matcher cosMatcher = cosRegex.matcher(text);
        if (cosMatcher.find()) {
            boolean flag;
            String fuHao = cosMatcher.group("fuHao");
            if (fuHao == null) {
                flag = false;
            }
            else if (fuHao.equals("+")) {
                flag = false;
            }
            else {
                flag = true;
            }
            String number = cosMatcher.group("number");
            if (number == null) {
                number = "1";
            }
            if (flag) {
                number = "-" + number;
            }
            exp = new BigInteger(number);
            String inside = cosMatcher.group("inside");
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

    public Cos(Factor insideFact,BigInteger exp) {
        super(4);
        this.insideFact = insideFact;
        this.exp = exp;
    }

    public Factor getInsideFact() {
        return insideFact;
    }

    public BigInteger getExp() {
        return exp;
    }

    public void setExp(BigInteger exp) {
        this.exp = exp;
    }

    public boolean plusFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (!this.exp.equals(((Cos)fact).getExp())) {
            return false;
        }
        return this.insideFact.isSame(((Cos)fact).getInsideFact());
    }

    public boolean multiFactor(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        return this.insideFact.isSame(((Cos)fact).getInsideFact());
    }

    public boolean isSame(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        if (exp.equals(((Cos)fact).exp)) {
            return false;
        }
        return insideFact.isSame(((Cos)fact).insideFact);
    }

    public void merge(Factor fact) {
        setExp(exp.add(((Cos)fact).getExp()));
        setWarn(getWarn() || fact.getWarn());
    }
}
