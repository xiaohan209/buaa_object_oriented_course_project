import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factor {
    private BigInteger co;
    private BigInteger ex;
    private boolean warn = false;
    //0 is number
    //1 is power
    //2 is sin
    //3 is cos
    private int type;

    public int getType() {
        return type;
    }

    public boolean getWarn() {
        return this.warn;
    }

    public BigInteger getCo() {
        return co;
    }

    public BigInteger getEx() {
        return ex;
    }

    public Factor() {
        co = new BigInteger("1");
        ex = new BigInteger("0");
        type = 0;
    }

    public Factor(boolean flag) {
        if (flag) {
            co = new BigInteger("1");
        }
        else {
            co = new BigInteger("-1");
        }
        ex = new BigInteger("0");
        type = 0;
    }

    public Factor(BigInteger co,BigInteger ex,int type) {
        BigInteger wan = new BigInteger("10000");
        this.co = co;
        this.ex = ex;
        this.type = type;
        if (type == 1) {
            if (ex.abs().min(wan).equals(wan)) {
                this.warn = true;
            }
        }
    }

    public Factor(String fac) {
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String number = symbol + "?\\d*";//0 before number
        String realNumber = "[+-]?\\d+";
        String exp = white + "\\*\\*" + white + "(?<hao>[+-])?(?<abs>\\d+)"; //zhishu
        String var = "x";
        String sin = "(sin" + white + "\\(" + white + "x" + white + "\\))";
        String cos = "(cos" + white + "\\(" + white + "x" + white + "\\))";
        String varFact = "(?<type>" + sin + "|" + var + "|" + cos + ")(" + exp + ")?";
        boolean expFlag;
        boolean flag2;
        BigInteger wan = new BigInteger("10000");
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        if (fac.contains("x")) {
            Pattern findExp = Pattern.compile(varFact);
            Matcher getExp = findExp.matcher(fac);
            if (getExp.find()) {
                String expSign = getExp.group("hao");
                String expo = getExp.group("abs");
                expFlag = plusOrMinus(expSign);
                if (expo == null) {
                    expo = "1";
                }
                if (!expFlag) {
                    expo = "-" + expo;
                }
                this.co = one;
                this.ex = new BigInteger(expo);
                String type = getExp.group("type");
                if (type.contains("sin")) {
                    this.type = 2;
                }
                else if (type.contains("cos")) {
                    this.type = 3;
                }
                else {
                    this.type = 1;
                    if (!ex.abs().max(wan).equals(wan)) {
                        this.warn = true;
                    }
                }
            }
        }
        else {
            String changshu = "(?<hao>[+-])?(?<abs>\\d+)";
            Pattern constant = Pattern.compile(changshu);
            Matcher matchConstant = constant.matcher(fac);
            if (matchConstant.find()) {
                flag2 = plusOrMinus(matchConstant.group("hao"));
                String abs = matchConstant.group("abs");
                if (!flag2) {
                    abs = "-" + abs;
                }
                this.co = new BigInteger(abs);
                this.ex = zero;
                this.type = 0;
            }
        }
    }

    public ArrayList<Factor> different() {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        ArrayList<Factor> retFact = new ArrayList<Factor>();
        if (type == 0 || co.equals(zero) || ex.equals(zero)) {
            retFact.add(new Factor(zero,zero,0));
            return retFact;
        }
        if (type == 1) {
            if (ex.equals(one)) {
                retFact.add(new Factor(ex.multiply(co),zero,0));
            }
            else {
                retFact.add(new Factor(ex.multiply(co),ex.subtract(one),1));
            }
        }
        else if (type == 2) {
            if (ex.equals(one)) {
                retFact.add(new Factor(co,ex,3));
            }
            else {
                retFact.add(new Factor(ex.multiply(co),ex.subtract(one),2));
                retFact.add(new Factor(one,one,3));
            }
        }
        else if (type == 3) {
            if (ex.equals(one)) {
                retFact.add(new Factor(co.multiply(zero.subtract(one)),ex,2));
            }
            else {
                retFact.add(new Factor(ex.multiply(co),ex.subtract(one),3));
                retFact.add(new Factor(zero.subtract(one),one,2));
            }
        }
        return retFact;
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
