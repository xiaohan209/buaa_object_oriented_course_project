import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Regex {
    private ArrayList<Term> poly;
    private boolean warn = false;

    public Regex() {
        poly = new ArrayList<Term>();
    }

    public Regex(Regex origin) {
        poly = new ArrayList<Term>();
        for (int i = 0; i < origin.poly.size(); i++) {
            Term termi = origin.poly.get(i);
            Term newTerm = new Term(termi);
            this.poly.add(newTerm);
        }
    }

    public String out() {
        standard();
        BigInteger zero = new BigInteger("0");
        StringBuilder outPut = new StringBuilder();
        if (poly.isEmpty()) {
            return outPut.toString();
        }
        if (!poly.get(0).getCoeff().equals(BigInteger.ZERO)) {
            outPut.append(poly.get(0).out());
        }
        for (int i = 1;i < poly.size();i++) {
            Term termi = poly.get(i);
            if (termi.getCoeff().equals(zero)) {
                outPut = outPut;
            }
            else if (termi.getCoeff().min(zero).equals(zero)) {
                outPut.append("+" + termi.out());
            }
            else {
                outPut.append(termi.out());
            }
        }
        return outPut.toString();
    }

    public ArrayList<Term> getPoly() {
        return poly;
    }

    public void add(Term item) {
        item.standard();
        for (int i = 0; i < poly.size(); i++) {
            if (poly.get(i).isSameTerm(item)) {
                BigInteger originFact = poly.get(i).getCoeff();
                BigInteger newFact = item.getCoeff();
                poly.get(i).setCoeff(originFact.add(newFact));
                return;
            }
        }
        poly.add(item);
    }

    public boolean getWarn() {
        boolean warn = this.warn;
        if (warn) {
            return true;
        }
        for (int i = 0;i < poly.size();i++) {
            warn = warn || poly.get(i).getWarn();
        }
        return warn;
    }

    public Regex dif() {
        Regex regex = new Regex();
        for (int i = 0;i < poly.size();i++) {
            Regex terms = poly.get(i).dif();
            if (terms != null && !terms.getPoly().isEmpty()) {
                for (int j = 0; j < terms.getPoly().size(); j++) {
                    regex.add(terms.getPoly().get(j));
                }
            }
        }
        return regex;
    }

    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    public Regex(String text) {
        Extract extract = new Extract(text);
        Regex thisRegex = extract.getRegex();
        this.poly = thisRegex.getPoly();
        this.warn = thisRegex.getWarn();
    }

    public void standard() {
        for (int i = 0; i < poly.size(); i++) {
            poly.get(i).standard();
        }
        Comparator compCoeff = new Comparator<Term>() {
            public int compare(Term p1,Term p2) {
                if (p2.getCoeff().equals(p1.getCoeff())) {
                    return p1.hashCode() - p2.hashCode();
                }
                return p2.getCoeff().compareTo(p1.getCoeff());
            }
        };
        Collections.sort(poly,compCoeff);
    }

    public Boolean isSameRegex(Regex regex) {
        if (poly.size() != regex.getPoly().size()) {
            return false;
        }
        regex.standard();
        this.standard();
        for (int i = 0; i < poly.size(); i++) {
            if (!poly.get(i).isSameTerm(regex.getPoly().get(i))) {
                return false;
            }
        }
        return true;
    }
}


