import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power extends Factor {
    private BigInteger exp;

    public String out() {
        StringBuilder outPut = new StringBuilder();
        if (exp.equals(BigInteger.ZERO)) {
            outPut.append("1");
        }
        if (exp.equals(BigInteger.ONE)) {
            outPut.append("x");
        }
        else {
            outPut.append("x**" + exp.toString());
        }
        return outPut.toString();
    }

    public Term dif() {
        ArrayList<Factor> factors = new ArrayList<>();
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        Term term = new Term(factors);
        if (exp.equals(zero)) {
            Factor constFact = new Constant(zero);
            term.add(constFact);
        }
        else if (exp.equals(one)) {
            Factor constFact = new Constant(exp);
            term.add(constFact);
        }
        else {
            Factor powerFact = new Power(exp.subtract(one));
            Factor constFact = new Constant(exp);
            term.add(powerFact);
            term.add(constFact);
        }
        return term;
    }

    public Power(String text) {
        super(1);
        String white = "[ \\t]*";//space
        String realNumber = "(?<fuHao>[+-])?(?<number>\\d+)";
        String expo = white + "\\*\\*" + white + realNumber; //zhishu
        String var = "x(" + expo + ")?";
        Pattern powerRegex = Pattern.compile(var);
        Matcher powerMatcher = powerRegex.matcher(text);
        if (powerMatcher.find()) {
            boolean flag;
            String fuHao = powerMatcher.group("fuHao");
            if (fuHao == null) {
                flag = false;
            }
            else if (fuHao.equals("+")) {
                flag = false;
            }
            else {
                flag = true;
            }
            String number = powerMatcher.group("number");
            if (number == null) {
                number = "1";
            }
            if (flag) {
                number = "-" + number;
            }
            exp = new BigInteger(number);
            BigInteger fifty = new BigInteger("50");
            if (!exp.abs().max(fifty).equals(fifty)) {
                setWarn(true);
            }
        }
    }

    public Power(BigInteger exp) {
        super(1);
        this.exp = exp;
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
        return this.exp.equals(((Power)fact).getExp());
    }

    public boolean multiFactor(Factor fact) {
        return this.getType() == fact.getType();
    }

    public boolean isSame(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        return exp.equals(((Power)fact).exp);
    }

    public void merge(Factor fact) {
        setExp(exp.add(((Power)fact).getExp()));
        setWarn(getWarn() || fact.getWarn());
    }
}
