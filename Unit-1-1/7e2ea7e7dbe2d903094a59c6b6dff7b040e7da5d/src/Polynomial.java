import java.math.BigInteger;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    public static final String type0 = "(?<sign>[+-]{0,2})";
    public static final String type1 =
            "(?<coef>[0-9]+)\\*x\\*\\*(?<index>[+-]?[0-9]+)";
    public static final String type2 = "x\\*\\*(?<index>[+-]?[0-9]+)";
    public static final String type3 = "(?<coef>[0-9]+)\\*x";
    public static final String type4 = "x";
    public static final String type5 = "(?<coef>[0-9]+)";
    private Pattern regex0 = Pattern.compile(type0);
    private Pattern regex1 = Pattern.compile(type1);
    private Pattern regex2 = Pattern.compile(type2);
    private Pattern regex3 = Pattern.compile(type3);
    private Pattern regex4 = Pattern.compile(type4);
    private Pattern regex5 = Pattern.compile(type5);
    private HashMap<BigInteger, Poly> polyitem = new HashMap<>();
    private String polynomial;

    public Polynomial(String line) {
        polynomial = line.replaceAll("[ \\t]", "");
        // Task1 expressions must be valid, clear all spaces/tabs first
    }

    public HashMap<BigInteger, Poly> getPolyitem() {
        while (polynomial.length() > 0) {
            getfirstitem();
        }
        return polyitem;
    }

    private void getfirstitem() {
        BigInteger tmpcoef;
        BigInteger tmpinde;
        Matcher item0 = regex0.matcher(polynomial);
        item0.lookingAt();
        String tmpcs = item0.group("sign");
        polynomial = polynomial.substring(tmpcs.length());
        if ((tmpcs.length() == 2 && tmpcs.charAt(0) != tmpcs.charAt(1))
                | (tmpcs.length() == 1 && tmpcs.charAt(0) == '-')) {
            tmpcs = "-";
        } else {
            tmpcs = "+";
        }
        Matcher item1 = regex1.matcher(polynomial);
        Matcher item2 = regex2.matcher(polynomial);
        Matcher item3 = regex3.matcher(polynomial);
        Matcher item4 = regex4.matcher(polynomial);
        Matcher item5 = regex5.matcher(polynomial);
        if (item1.lookingAt()) {
            tmpcoef = new BigInteger(tmpcs + item1.group("coef"));
            tmpinde = new BigInteger(item1.group("index"));
            polynomial = polynomial.substring(item1.group().length());
        } else if (item2.lookingAt()) {
            tmpcoef = new BigInteger(tmpcs + "1");
            tmpinde = new BigInteger(item2.group("index"));
            polynomial = polynomial.substring(item2.group().length());
        } else if (item3.lookingAt()) {
            tmpcoef = new BigInteger(tmpcs + item3.group("coef"));
            tmpinde = BigInteger.ONE;
            polynomial = polynomial.substring(item3.group().length());
        } else if (item4.lookingAt()) {
            tmpcoef = new BigInteger(tmpcs + "1");
            tmpinde = BigInteger.ONE;
            polynomial = polynomial.substring(item4.group().length());
        } else if (item5.lookingAt()) {
            tmpcoef = new BigInteger(tmpcs + item5.group("coef"));
            tmpinde = BigInteger.ZERO;
            polynomial = polynomial.substring(item5.group().length());
        } else {
            tmpcoef = BigInteger.ZERO;
            tmpinde = BigInteger.ZERO;
            // Add legal tests next week
        }
        if (polyitem.containsKey(tmpinde)) {
            Poly newpoly = polyitem.get(tmpinde);
            newpoly.addCoef(tmpcoef);
            polyitem.put(tmpinde, newpoly);
        } else {
            polyitem.put(tmpinde, new Poly(tmpcoef, tmpinde));
        }
    }
}
