import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
    private String text;

    public String getText() {
        return text;
    }

    public Extract(String text) {
        this.text = text;
    }

    public boolean preset() {
        String allChars = "[^-acinosx \\t0-9()*+]";
        Pattern alChars = Pattern.compile(allChars);
        Matcher preMatch = alChars.matcher(text);
        if (preMatch.find()) {
            return true;
        }
        if (text.isEmpty()) {
            return true;
        }
        int index = 0;
        StringBuilder origin = new StringBuilder(text);
        for (int i = 0; i < origin.length() && index >= 0; i++) {
            if (origin.charAt(i) == '(') {
                if (index == 0) {
                    origin.setCharAt(i,'l');
                }
                index++;
            }
            else if (origin.charAt(i) == ')') {
                index--;
                if (index == 0) {
                    origin.setCharAt(i,'r');
                }
            }
        }
        text = origin.toString();
        if (index != 0) {
            return true;
        }
        return false;
    }

    public Regex getRegex() {
        Regex regex = new Regex();
        regex.setWarn(preset());
        int index = 0;
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String realNumber = "[+-]?\\d+";
        String exp = white + "\\*\\*" + white + realNumber; //zhishu
        String var = "x";
        String sin = "sin" + white + "l.+?r";
        String cos = "cos" + white + "l.+?r";
        String regFact = "l.+?r";
        String varFact = "(" + sin + "|" + var + "|" + cos + ")(" + exp + ")?";
        String fact = "(" + varFact + "|" + realNumber + "|" + regFact + ")";
        String term = "(" + symbol + white + ")?" + fact +
                "(" + white + "\\*" + white + fact + ")*";
        String reg = white + "((?<fuHao>[+-])" + white + ")?" + "(?<term>" + term + ")" + white;
        String reg2 = "(?<fuHao>[+-])" + white + "(?<term>" + term + ")" + white;
        //first find
        Pattern powerFunctionFirst = Pattern.compile(reg);
        Matcher matcherFirst = powerFunctionFirst.matcher(text);
        if (matcherFirst.find()) {
            if (matcherFirst.start() != 0) {
                regex.setWarn(true);
            }
            index = matcherFirst.end();
            String fuHao = matcherFirst.group("fuHao");
            String factors = matcherFirst.group("term");
            Split newSplit = new Split(factors);
            boolean flag = newSplit.plusOrMinus(fuHao);
            Term newTerm;
            newTerm = newSplit.produceTerm(flag);
            regex.getPoly().add(newTerm);
        }
        else {
            regex.setWarn(true);
        }
        String text2 = text.substring(index);
        Pattern powerFunctionSecond = Pattern.compile(reg2);
        Matcher matcherSecond = powerFunctionSecond.matcher(text2);
        index = 0;
        while (matcherSecond.find()) {
            if (matcherSecond.start() != index) {
                regex.setWarn(true);
            }
            index = matcherSecond.end();
            String fuHao = matcherSecond.group("fuHao");
            String factors = matcherSecond.group("term");
            Split newSplit = new Split(factors);
            boolean flag = newSplit.plusOrMinus(fuHao);
            Term newTerm;
            newTerm = newSplit.produceTerm(flag);
            regex.add(newTerm);
        }
        if (index != text2.length()) {
            regex.setWarn(true);
        }
        regex.standard();
        return regex;
    }

}
