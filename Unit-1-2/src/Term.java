import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private BigInteger coeff;
    private BigInteger xiExp;
    private BigInteger sinExp;
    private BigInteger cosExp;
    private boolean warn = false;

    public BigInteger getCoeff() {
        return coeff;
    }

    public BigInteger getXiExp() {
        return xiExp;
    }

    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }

    public void setXiExp(BigInteger xiExp) {
        this.xiExp = xiExp;
    }

    public BigInteger getCosExp() {
        return cosExp;
    }

    public BigInteger getSinExp() {
        return sinExp;
    }

    public void setCosExp(BigInteger cosExp) {
        this.cosExp = cosExp;
    }

    public void setSinExp(BigInteger sinExp) {
        this.sinExp = sinExp;
    }

    // print the absolute value of term
    public String print() {
        String outPut = "";
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        if (coeff.equals(zero)) {
            outPut = "";
        }
        else if (xiExp.equals(zero) && sinExp.equals(zero) && cosExp.equals(zero)) {
            outPut = coeff.abs().toString();
        }
        else if (coeff.abs().equals(one)) {
            outPut = printf(true);
        }
        else {
            outPut = coeff.abs().toString() + printf(false);
        }
        return outPut;
    }

    public String printf(boolean flag) {
        String outPut;
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        boolean flag1 = flag;
        if (xiExp.equals(zero)) {
            outPut = "";
        }
        else if (xiExp.equals(one)) {
            if (flag1) {
                outPut = "x";
                flag1 = false;
            }
            else {
                outPut = "*x";
            }
        }
        else if (xiExp.equals(two)) {
            if (flag1) {
                outPut = "x*x";
                flag1 = false;
            }
            else {
                outPut = "*x*x";
            }
        }
        else {
            if (flag1) {
                outPut = "x**" + xiExp.toString();
                flag1 = false;
            }
            else {
                outPut = "*x**" + xiExp.toString();
            }
        }
        if (sinExp.equals(zero)) {
            outPut = outPut;
        }
        else if (sinExp.equals(one)) {
            if (flag1) {
                outPut = outPut + "sin(x)";
                flag1 = false;
            }
            else {
                outPut = outPut + "*sin(x)";
            }
        }
        else {
            if (flag1) {
                outPut = outPut + "sin(x)**" + sinExp.toString();
                flag1 = false;
            }
            else {
                outPut = outPut + "*sin(x)**" + sinExp.toString();
            }
        }
        outPut = outPut + printg(flag1);
        return outPut;
    }

    public String printg(boolean flag) {
        String outPut = "";
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        boolean flag1 = flag;
        if (cosExp.equals(zero)) {
            outPut = outPut;
        }
        else if (cosExp.equals(one)) {
            if (flag1) {
                outPut = outPut + "cos(x)";
                flag1 = false;
            }
            else {
                outPut = outPut + "*cos(x)";
            }
        }
        else {
            if (flag1) {
                outPut = outPut + "cos(x)**" + cosExp.toString();
            }
            else {
                outPut = outPut + "*cos(x)**" + cosExp.toString();
            }
        }
        return outPut;
    }

    public Term() {
        this.coeff = new BigInteger("1");
        this.xiExp = new BigInteger("0");
        this.sinExp = new BigInteger("0");
        this.cosExp = new BigInteger("0");
        this.warn = false;
    }

    public Term(BigInteger coeff,BigInteger xiExp,BigInteger sinExp,BigInteger cosExp) {
        this.coeff = coeff;
        this.xiExp = xiExp;
        this.sinExp = sinExp;
        this.cosExp = cosExp;
        this.warn = false;
    }

    public Term(ArrayList<Factor> fact) {
        this.coeff = new BigInteger("1");
        this.xiExp = new BigInteger("0");
        this.sinExp = new BigInteger("0");
        this.cosExp = new BigInteger("0");
        this.warn = false;
        for (int i = 0; i < fact.size(); i++) {
            Factor fac = fact.get(i);
            this.coeff = coeff.multiply(fac.getCo());
            switch (fac.getType()) {
                case 1: {
                    this.xiExp = xiExp.add(fac.getEx());
                    break;
                }
                case 2: {
                    this.sinExp = sinExp.add(fac.getEx());
                    break;
                }
                case 3: {
                    this.cosExp = cosExp.add(fac.getEx());
                    break;
                }
                default: {
                    this.coeff = this.coeff;
                }
            }
            this.warn = this.getWarn() || fac.getWarn();
        }
    }

    public ArrayList<Term> derivation() {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        ArrayList<Term> reTerm = new ArrayList<Term>();
        Factor xi = new Factor(coeff,zero,0);
        Factor po = new Factor(one,xiExp,1);
        Factor si = new Factor(one,sinExp,2);
        Factor co = new Factor(one,cosExp,3);
        ArrayList<Factor> dec1 = po.different();
        dec1.add(xi);
        dec1.add(si);
        dec1.add(co);
        ArrayList<Factor> dec2 = si.different();
        dec2.add(xi);
        dec2.add(po);
        dec2.add(co);
        ArrayList<Factor> dec3 = co.different();
        dec3.add(xi);
        dec3.add(po);
        dec3.add(si);
        Term term1 = new Term(dec1);
        Term term2 = new Term(dec2);
        Term term3 = new Term(dec3);
        if (!term1.getCoeff().equals(zero)) {
            reTerm.add(term1);
        }
        if (!term2.getCoeff().equals(zero)) {
            reTerm.add(term2);
        }
        if (!term3.getCoeff().equals(zero)) {
            reTerm.add(term3);
        }
        return reTerm;
    }

    public boolean isSameTerm(Term t) {
        if (t.getXiExp().equals(xiExp)) {
            if (t.getSinExp().equals(sinExp)) {
                if (t.getCosExp().equals(cosExp)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public int getLength() {
        int length = 0;
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        if (coeff.equals(zero)) {
            length = 0;
            return length;
        }
        else if (xiExp.equals(zero) && sinExp.equals(zero) && cosExp.equals(zero)) {
            length = coeff.abs().toString().length();
            return length;
        }
        else if (!coeff.abs().equals(one)) {
            length += coeff.abs().toString().length() + getLengthf(false);
        }
        else {
            length += getLengthf(true);
        }
        return length;
    }

    public int getLengthf(boolean flag) {
        int length = 0;
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        boolean flag1 = flag;
        if (xiExp.equals(zero)) {
            length += 0;
        }
        else if (xiExp.equals(one)) {
            if (flag1) {
                length += 1;
                flag1 = false;
            }
            else {
                length += 2;
            }
        }
        else if (xiExp.equals(two)) {
            if (flag1) {
                length += 3;
                flag1 = false;
            }
            else {
                length += 4;
            }
        }
        else {
            if (flag1) {
                length += 3 + xiExp.toString().length();
                flag1 = false;
            }
            else {
                length += 4 + xiExp.toString().length();
            }
        }
        if (sinExp.equals(zero)) {
            length += 0;
        }
        else if (sinExp.equals(one)) {
            if (flag1) {
                length += 6;
                flag1 = false;
            }
            else {
                length += 7;
            }
        }
        else {
            if (flag1) {
                length += 8 + sinExp.toString().length();
                flag1 = false;
            }
            else {
                length += 9 + sinExp.toString().length();
            }
        }
        length += getLengthg(flag1);
        return length;
    }

    public int getLengthg(boolean flag) {
        int length = 0;
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        boolean flag1 = flag;
        if (cosExp.equals(zero)) {
            length += 0;
        }
        else if (cosExp.equals(one)) {
            if (flag1) {
                length += 6;
                flag1 = false;
            }
            else {
                length += 7;
            }
        }
        else {
            if (flag1) {
                length += 8 + cosExp.toString().length();
            }
            else {
                length += 9 + cosExp.toString().length();
            }
        }
        return length;
    }

    public boolean getWarn() {
        return this.warn;
    }

}
