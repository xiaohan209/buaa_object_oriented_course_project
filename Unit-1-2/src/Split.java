import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Split {
    private String wholeTerm;

    public Split(String wholeTerm) {
        this.wholeTerm = wholeTerm;
    }

    public Term produceTerm(boolean flag) {
        BigInteger zero = new BigInteger("0");
        ArrayList<Factor> fact = new ArrayList<Factor>();
        fact.add(new Factor(flag));
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String number = symbol + "?\\d*";//0 before number
        String realNumber = "[+-]?\\d+";
        String exp = white + "\\*\\*" + white + realNumber; //zhishu
        String var = "x";
        String sin = "(sin" + white + "\\(" + white + "x" + white + "\\))";
        String cos = "(cos" + white + "\\(" + white + "x" + white + "\\))";
        String varFact = "(" + sin + "|" + var + "|" + cos + ")(" + exp + ")?";
        String factor = "(" + varFact + "|" + realNumber + ")";
        String term1 = "(" + "(?<fuHao>" + symbol + ")" + white + ")?(?<fac>" + factor + ")";
        String term2 = white + "\\*" + white + "(?<fac>" + factor + ")";
        boolean flag1;
        Pattern powerFuction1 = Pattern.compile(term1);
        Matcher matcher1 = powerFuction1.matcher(wholeTerm);
        if (matcher1.find()) {
            wholeTerm = wholeTerm.substring(matcher1.end());
            String fuHao = matcher1.group("fuHao");
            flag1 = plusOrMinus(fuHao);
            fact.add(new Factor(flag1));
            String fac = matcher1.group("fac");
            fact.add(new Factor(fac));
        }
        Pattern powerFunction2 = Pattern.compile(term2);
        Matcher matcher2 = powerFunction2.matcher(wholeTerm);
        while (matcher2.find()) {
            String fac = matcher2.group("fac");
            fact.add(new Factor(fac));
        }
        Term term = new Term(fact);
        return term;
    }

    public boolean plusOrMinus(String s) {
        boolean flag;
        if (s == null) {
            flag = true;
        } else if (s.equals("+")) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
