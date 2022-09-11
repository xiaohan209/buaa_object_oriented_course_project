import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant extends Factor {
    private BigInteger coeff;

    public BigInteger getCoeff() {
        return coeff;
    }

    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }

    public Constant(BigInteger coeff) {
        super(0);
        this.coeff = coeff;
    }

    public String out() {
        StringBuilder outPut = new StringBuilder();
        //youhua
        outPut.append(coeff.toString());
        return outPut.toString();
    }

    public Term dif() {
        ArrayList<Factor> factors = new ArrayList<Factor>();
        Factor con = new Constant(new BigInteger("0"));
        Term term = new Term(factors);
        term.add(con);
        return term;
    }

    public boolean plusFactor(Factor fact) {
        return this.getType() == fact.getType();
    }

    public boolean multiFactor(Factor fact) {
        return this.getType() == fact.getType();
    }

    public boolean isSame(Factor fact) {
        if (this.getType() != fact.getType()) {
            return false;
        }
        return coeff.equals(((Constant)fact).coeff);
    }

    public Constant(String text) {
        super(0);
        String realNumber = "(?<fuHao>[+-])?(?<coeff>\\d+)";
        Pattern numberRegex = Pattern.compile(realNumber);
        Matcher numberMatcher = numberRegex.matcher(text);
        if (numberMatcher.find()) {
            boolean flag;
            String fuHao = numberMatcher.group("fuHao");
            String co = numberMatcher.group("coeff");
            if (fuHao == null) {
                flag = false;
            }
            else if (fuHao.equals("+")) {
                flag = false;
            }
            else {
                flag = true;
            }
            if (flag) {
                co = "-" + co;
            }
            coeff = new BigInteger(co);
        }
    }

    public void merge(Factor fact) {
        setCoeff(coeff.multiply(((Constant)fact).getCoeff()));
        setWarn(getWarn() || fact.getWarn());
    }

}
