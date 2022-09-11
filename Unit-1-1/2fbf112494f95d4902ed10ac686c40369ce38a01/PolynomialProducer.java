import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialProducer {

    public static Polynomial creatPoly(String polyStr) {
        String num = "[\\+-]?\\d+";
        String index = "\\*\\*" + num;
        String powerFunction = "x" + "(" + index + ")" + "?";
        String value = "[\\+-]?" + powerFunction + "|" + num + "\\*" + powerFunction;
        String termRegex = value + "|" + num;
        Pattern pattern = Pattern.compile(termRegex);
        Matcher matcher = pattern.matcher(polyStr);
        ArrayList<Term> polyList = new ArrayList<>();
        Polynomial polynomial = new Polynomial(polyList);
        while (matcher.find()) {
            String termStr = matcher.group();
            Pattern variPattern = Pattern.compile("x");
            Matcher variMatcher = variPattern.matcher(termStr);
            if (variMatcher.find()) {
                String coefficientStr = "";
                String indexStr = "";
                Pattern multiSymbol = Pattern.compile("^(" + num + ")" + "\\*x");
                Matcher multiMatcher = multiSymbol.matcher(termStr);
                if (multiMatcher.find()) {
                    coefficientStr = multiMatcher.group(1);
                }
                else {
                    if (termStr.charAt(0) == '+' | termStr.charAt(0) == 'x') {
                        coefficientStr = "1";
                    }
                    else if (termStr.charAt(0) == '-') {
                        coefficientStr = "-1";
                    }
                }
                Pattern indexSymbol = Pattern.compile("\\*\\*" + "(" + num + ")$");
                Matcher indexMatcher = indexSymbol.matcher(termStr);
                if (indexMatcher.find()) {
                    indexStr = indexMatcher.group(1);
                }
                else {
                    indexStr = "1";
                }
                TermProducer termProducer = new TermProducer();
                polynomial.addTerm(termProducer.creatTerm(coefficientStr, indexStr));
            }
            else {
                Pattern numPattern = Pattern.compile(num);
                Matcher numMatcher = numPattern.matcher(termStr);
                if (numMatcher.find()) {
                    TermProducer termProducer = new TermProducer();
                    polynomial.addTerm(termProducer.creatTerm(numMatcher.group(), "0"));
                }
            }
        }
        return polynomial;

    }

}
