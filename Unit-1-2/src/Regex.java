import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private ArrayList<Term> poly;
    private boolean warn = false;

    public Regex(String text) {
        poly = new ArrayList<Term>();
        if (text.isEmpty()) {
            warn = true;
        }
        int index = 0;
        String symbol = "[+-]";//minus plus
        String white = "[ \\t]*";//space
        String realNumber = "[+-]?\\d+";
        String exp = white + "\\*\\*" + white + realNumber; //zhishu
        String var = "x";
        String sin = "(sin" + white + "\\(" + white + "x" + white + "\\))";
        String cos = "(cos" + white + "\\(" + white + "x" + white + "\\))";
        String varFact = "(" + sin + "|" + var + "|" + cos + ")(" + exp + ")?";
        String fact = "(" + varFact + "|" + realNumber + ")";
        String term = "(" + symbol + white + ")?" + fact +
                "(" + white + "\\*" + white + fact + ")*";
        String reg = white + "((?<fuHao>[+-])" + white + ")?" + "(?<term>" + term + ")" + white;
        String reg2 = "(?<fuHao>[+-])" + white + "(?<term>" + term + ")" + white;
        //first find
        Pattern powerFunctionFirst = Pattern.compile(reg);
        Matcher matcherFirst = powerFunctionFirst.matcher(text);
        if (matcherFirst.find()) {
            if (matcherFirst.start() != 0) {
                warn = true;
            }
            index = matcherFirst.end();
            String fuHao = matcherFirst.group("fuHao");
            String factors = matcherFirst.group("term");
            Split newSplit = new Split(factors);
            boolean flag = newSplit.plusOrMinus(fuHao);
            Term newTerm;
            newTerm = newSplit.produceTerm(flag);
            poly.add(newTerm);
        }
        else {
            warn = true;
        }
        String text2 = text.substring(index);
        Pattern powerFunctionSecond = Pattern.compile(reg2);
        Matcher matcherSecond = powerFunctionSecond.matcher(text2);
        index = 0;
        while (matcherSecond.find()) {
            if (matcherSecond.start() != index) {
                warn = true;
            }
            index = matcherSecond.end();
            String fuHao = matcherSecond.group("fuHao");
            String factors = matcherSecond.group("term");
            Split newSplit = new Split(factors);
            boolean flag = newSplit.plusOrMinus(fuHao);
            Term newTerm;
            newTerm = newSplit.produceTerm(flag);
            add(newTerm);
        }
        if (index != text2.length()) {
            warn = true;
        }
    }

    public Regex() {
        poly = new ArrayList<Term>();
    }

    public Regex(Regex origin) {
        poly = new ArrayList<Term>();
        for (int i = 0; i < origin.poly.size(); i++) {
            Term termi = origin.poly.get(i);
            Term newTerm = new Term(termi.getCoeff(),
                    termi.getXiExp(),termi.getSinExp(),termi.getCosExp());
            this.poly.add(newTerm);
        }
    }

    public Regex different() {
        Regex regex = new Regex();
        for (int i = 0;i < poly.size();i++) {
            ArrayList<Term> terms = poly.get(i).derivation();
            if (terms != null && !terms.isEmpty()) {
                for (int j = 0; j < terms.size(); j++) {
                    regex.add(terms.get(j));
                }
            }
        }
        return regex;
    }

    public void simplify() {
        BigInteger two = new BigInteger("2");
        Comparator compSin = new Comparator<Term>() {
            public int compare(Term p1,Term p2) {
                if (p1.getXiExp().equals(p2.getXiExp())) {
                    if (p1.getSinExp().equals(p2.getSinExp())) {
                        return p2.getCosExp().compareTo(p1.getCosExp());
                    }
                    return p2.getSinExp().compareTo(p1.getSinExp());
                }
                return p1.getXiExp().compareTo(p2.getXiExp());
            }
        };
        int improve = 1;
        while (improve > 0) {
            improve = 0;
            Collections.sort(poly,compSin);
            for (int i = 0; i < poly.size(); i++) {
                Term termi = poly.get(i);
                int br = 0;
                for (int j = i + 1;j < poly.size(); j++) {
                    Term termj = poly.get(j);
                    if (!(termj.getXiExp().equals(termi.getXiExp()) && termi.getSinExp()
                            .subtract(poly.get(j).getSinExp()).max(two).equals(two))) {
                        break;
                    }
                    if (termi.getSinExp().equals(termj.getSinExp())
                            && termi.getCosExp().subtract(termj.getCosExp()).equals(two)) {
                        improve = minusCos2(i,j);
                        if (improve > 0) {
                            br = 1;
                            break;
                        }
                    }
                    else if (termi.getSinExp().subtract(termj.getSinExp()).equals(two)
                            && termi.getCosExp().equals(termj.getCosExp())) {
                        improve = minusSin2(i,j);
                        if (improve > 0) {
                            br = 1;
                            break;
                        }
                    }
                    else if (termi.getSinExp().subtract(termj.getSinExp()).equals(two)
                            && termj.getCosExp().subtract(termi.getCosExp()).equals(two)) {
                        improve = plus(i,j);
                        if (improve > 0) {
                            br = 1;
                            break;
                        }
                    }
                }
                if (br != 0) {
                    break;
                }
            }
        }
    }

    public void sort() {
        Comparator compCoeff = new Comparator<Term>() {
            public int compare(Term p1,Term p2) {
                return p2.getCoeff().compareTo(p1.getCoeff());
            }
        };
        Collections.sort(poly,compCoeff);
    }

    public int minusCos2(int i,int j) {
        Term termi = poly.get(i);
        Term termj = poly.get(j);
        BigInteger coeff1 = termi.getCoeff();
        BigInteger coeff2 = termj.getCoeff();
        BigInteger exp = termi.getXiExp();
        BigInteger si = termi.getSinExp();
        BigInteger co1 = termi.getCosExp();
        BigInteger co2 = termj.getCosExp();
        BigInteger two = new BigInteger("2");
        Term newTermi = new Term(coeff2,exp,si.add(two),co2);
        Term newTermj = new Term(coeff1.add(coeff2),exp,si,co1);
        return checkIt(newTermi,newTermj,i,j);
    }

    public int minusSin2(int i,int j) {
        Term termi = poly.get(i);
        Term termj = poly.get(j);
        BigInteger coeff1 = termi.getCoeff();
        BigInteger coeff2 = termj.getCoeff();
        BigInteger exp = termi.getXiExp();
        BigInteger si1 = termi.getSinExp();
        BigInteger si2 = termj.getSinExp();
        BigInteger co = termi.getCosExp();
        BigInteger two = new BigInteger("2");
        Term newTermi = new Term(coeff2,exp,si2,co.add(two));
        Term newTermj = new Term(coeff1.add(coeff2),exp,si1,co);
        return checkIt(newTermi,newTermj,i,j);
    }

    public int plus(int i,int j) {
        Term termi = poly.get(i);
        Term termj = poly.get(j);
        BigInteger coeff1 = termi.getCoeff();
        BigInteger coeff2 = termj.getCoeff();
        BigInteger exp = termi.getXiExp();
        BigInteger si1 = termi.getSinExp();
        BigInteger si2 = termj.getSinExp();
        BigInteger co1 = termi.getCosExp();
        Term newTermi = new Term(coeff2,exp,si2,co1);
        Term newTermj = new Term(coeff1.subtract(coeff2),exp,si1,co1);
        return checkIt(newTermi,newTermj,i,j);
    }

    public int checkIt(Term newTermi,Term newTermj,int i,int j) {
        Regex newRegex = new Regex(this);
        newRegex.poly.remove(j);
        newRegex.poly.remove(i);
        newRegex.add(newTermi);
        newRegex.add(newTermj);
        newRegex.sort();
        String newOut = newRegex.out();
        Regex oldRegex = new Regex(this);
        oldRegex.sort();
        String thisOut = oldRegex.out();
        int originLength = thisOut.length();
        int impLength = newOut.length();
        if (impLength < originLength) {
            poly.remove(j);
            poly.remove(i);
            add(newTermi);
            add(newTermj);
            return originLength - impLength;
        }
        else {
            return 0;
        }
    }

    public void add(Term item) {
        for (int i = 0; i < poly.size(); i++) {
            if (poly.get(i).isSameTerm(item)) {
                poly.get(i).setCoeff(poly.get(i).getCoeff().add(item.getCoeff()));
                return;
            }
        }
        poly.add(item);
        return;
    }

    public boolean find(Term item) {
        for (int i = 0; i < poly.size(); i++) {
            if (poly.get(i).isSameTerm(item)) {
                return true;
            }
        }
        return false;
    }

    public String out() {
        BigInteger zero = new BigInteger("0");
        String outPut = "";
        if (poly.isEmpty()) {
            return outPut;
        }
        if (poly.get(0).getCoeff().min(zero).equals(zero)) {
            outPut = outPut + poly.get(0).print();
        }
        else {
            outPut = outPut + "-" + poly.get(0).print();
        }
        for (int i = 1;i < poly.size();i++) {
            Term termi = poly.get(i);
            if (termi.getCoeff().equals(zero)) {
                outPut = outPut;
            }
            else if (termi.getCoeff().min(zero).equals(zero)) {
                outPut = outPut + "+" + termi.print();
            }
            else {
                outPut = outPut + "-" + termi.print();
            }
        }
        return outPut;
    }

    public boolean getWarn() {
        if (warn) {
            return true;
        }
        else {
            for (int i = 0; i < poly.size(); i++) {
                if (poly.get(i).getWarn()) {
                    return true;
                }
            }
            return false;
        }
    }

    public ArrayList<Term> getPoly() {
        return poly;
    }
}
