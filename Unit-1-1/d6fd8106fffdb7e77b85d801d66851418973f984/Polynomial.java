package oo.homework1;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {

    private Map<BigInteger, Monomial> monomials = new HashMap<>();

    private String process(String str) {
        String strReturn = str.replaceAll("\\s*", "");
        strReturn = strReturn.replace("++", "+");
        strReturn = strReturn.replace("--", "+");
        strReturn = strReturn.replace("-+", "-");
        strReturn = strReturn.replace("+-", "-");
        return strReturn;
    }

    public void read() {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        line = process(line);

        String r = "(((([+-]?\\d+\\*)?)|[+-])x(\\*\\*[+-]?\\d+)?)|([+-]?\\d+)";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(line);

        while (m.find()) {
            String monoStr = line.substring(m.start(), m.end());
            Monomial mono1 = new Monomial(monoStr);
            BigInteger mono1Index = mono1.getIndex();
            if (monomials.containsKey(mono1Index)) {
                Monomial mono2 = monomials.get(mono1Index);
                mono2.monomialAdd(mono1);
            }
            else {
                monomials.put(mono1Index, mono1);
            }
        }
    }

    public void print() {
        StringBuilder result = new StringBuilder();
        for (Monomial mono : monomials.values()) {
            String strTemp = mono.getMonoDerStr();

            if (strTemp.equals("")) {
                continue;
            }

            if (strTemp.charAt(0) != '-') {
                if (result.toString().equals("") || result.charAt(0) == '-') {
                    result.insert(0, strTemp);
                } else {
                    result.insert(0, strTemp + "+");
                }
            } else {
                result.append(strTemp);
            }
        }
        if (result.toString().equals("")) {
            result = new StringBuilder("0");
        }
        else if (result.charAt(0) == '+') {
            result = new StringBuilder(result.substring(1));
        }
        System.out.println(result);
    }

}
