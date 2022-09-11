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
        int index = 0;
        ArrayList<Factor> fact = new ArrayList<Factor>();
        Term term = new Term(fact);
        toFactor(term,flag);
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String realNumber = "[+-]?\\d+";
        String exp = white + "\\*\\*" + white + realNumber; //zhishu
        String sin = "(sin" + white + "l.+?r)";
        String cos = "(cos" + white + "l.+?r)";
        String regFact = "l.+?r";
        String var = "x";
        String varFact = "(" + sin + "|" + var + "|" + cos + ")(" + exp + ")?";
        String factor = "(" + varFact + "|" + realNumber + "|" + regFact + ")";
        String term1 = white + "(" + "(?<fuHao>" + symbol + ")" + white
                + ")?(?<fac>" + factor + ")" + white;
        boolean flag1;
        Pattern powerFuction1 = Pattern.compile(term1);
        Matcher matcher1 = powerFuction1.matcher(wholeTerm);
        if (matcher1.find()) {
            if (matcher1.start() != index) {
                term.setWarn(true);
            }
            wholeTerm = wholeTerm.substring(matcher1.end());
            String fuHao = matcher1.group("fuHao");
            flag1 = plusOrMinus(fuHao);
            toFactor(term,flag1);
            String fac = matcher1.group("fac");
            term.add(newFactor(fac));
        }
        index = 0;
        String term2 = "\\*" + white + "(?<fac>" + factor + ")" + white;
        Pattern powerFunction2 = Pattern.compile(term2);
        Matcher matcher2 = powerFunction2.matcher(wholeTerm);
        while (matcher2.find()) {
            if (matcher2.start() != index) {
                term.setWarn(true);
            }
            index = matcher2.end();
            String fac = matcher2.group("fac");
            term.add(newFactor(fac));
        }
        if (index != wholeTerm.length()) {
            term.setWarn(true);
        }
        return term;
    }

    public Factor newFactor(String text) {
        Factor fac;
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String realNumber = white + "[+-]?\\d+" + white;
        String exp = white + "\\*\\*" + realNumber; //zhishu
        String sin = white + "(sin" + white + "l.+?r)(" + exp + ")?" + white;
        String cos = white + "(cos" + white + "l.+?r)(" + exp + ")?" + white;
        String regFact = white + "l.+?r" + white;
        Pattern cosRegex = Pattern.compile(cos);
        Matcher cosMatcher = cosRegex.matcher(text);
        if (cosMatcher.find()) {
            if (cosMatcher.start() == 0 && cosMatcher.end() == text.length()) {
                fac = new Cos(text);
                return fac;
            }
        }
        Pattern sinRegex = Pattern.compile(sin);
        Matcher sinMatcher = sinRegex.matcher(text);
        if (sinMatcher.find()) {
            if (sinMatcher.start() == 0 && sinMatcher.end() == text.length()) {
                fac = new Sin(text);
                return fac;
            }
        }
        Pattern matryRegex = Pattern.compile(regFact);
        Matcher matryMatcher = matryRegex.matcher(text);
        if (matryMatcher.find()) {
            if (matryMatcher.start() == 0 && matryMatcher.end() == text.length()) {
                fac = new Matry(text);
                return fac;
            }
        }
        String var = white + "x(" + exp + ")?" + white;
        Pattern powerRegex = Pattern.compile(var);
        Matcher powerMatcher = powerRegex.matcher(text);
        if (powerMatcher.find()) {
            if (powerMatcher.start() == 0 && powerMatcher.end() == text.length()) {
                fac = new Power(text);
                return fac;
            }
        }
        Pattern numberRegex = Pattern.compile(realNumber);
        Matcher numberMatcher = numberRegex.matcher(text);
        if (numberMatcher.find()) {
            if (numberMatcher.start() == 0 && numberMatcher.end() == text.length()) {
                fac = new Constant(text);
                return fac;
            }
        }
        fac = new Constant(new BigInteger("0"));
        fac.setWarn(true);
        return fac;
    }

    public void toFactor(Term term,boolean flag) {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        if (flag) {
            term.add(new Constant(zero.subtract(one)));
        }
        else {
            term.add(new Constant(one));
        }
    }

    public boolean plusOrMinus(String s) {
        boolean flag;
        if (s == null) {
            flag = false;
        } else if (s.equals("+")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
}


