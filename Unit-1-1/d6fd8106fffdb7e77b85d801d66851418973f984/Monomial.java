package oo.homework1;

import java.math.BigInteger;

public class Monomial {

    private BigInteger index;
    private BigInteger coefficient;

    public Monomial(String monoStr) {
        String strTemp;
        if (monoStr.contains("x")) {
            strTemp = monoStr.substring(0, monoStr.indexOf("x"));

            if (strTemp.equals("")) {
                strTemp = "1";
            } else if (strTemp.matches("^[+-]$")) {
                strTemp = strTemp + "1";
            } else {
                strTemp = strTemp.substring(0,strTemp.length() - 1);
            }
            this.coefficient = new BigInteger(strTemp);

            if (monoStr.contains("**")) {
                strTemp = monoStr.substring(monoStr.indexOf("**") + 2);
                this.index = new BigInteger(strTemp);
            } else {
                this.index = new BigInteger("1");
            }
        } else {
            this.index = new BigInteger("0");
            this.coefficient = new BigInteger(monoStr);
        }
    }

    public BigInteger getIndex() {
        return index;
    }

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public void monomialAdd(Monomial mono) {
        this.coefficient = this.coefficient.add(mono.getCoefficient());
    }

    public String getMonoDerStr() {
        String resStr;
        if (this.index.equals(BigInteger.ZERO)) {
            resStr = "";
        } else {
            BigInteger derIndex = this.index.subtract(BigInteger.ONE);
            BigInteger derCoeff = this.coefficient.multiply(this.index);
            if (derCoeff.equals(BigInteger.ZERO)) {
                resStr = "";
            } else if (derIndex.equals(BigInteger.ZERO)) {
                resStr = derCoeff.toString();
            } else if (derIndex.equals(BigInteger.ONE)) {
                if (derCoeff.equals(BigInteger.ONE)) {
                    resStr = "x";
                } else if (derCoeff.equals(BigInteger.valueOf(-1))) {
                    resStr = "-x";
                } else {
                    resStr = derCoeff.toString() + "*x";
                }
            } else {
                if (derCoeff.equals(BigInteger.ONE)) {
                    resStr = "x**" + derIndex.toString();
                } else if (derCoeff.equals(BigInteger.valueOf(-1))) {
                    resStr = "-x**" + derIndex.toString();
                } else {
                    resStr = derCoeff.toString() + "*x**" + derIndex.toString();
                }
            }
        }
        return resStr;
    }
}