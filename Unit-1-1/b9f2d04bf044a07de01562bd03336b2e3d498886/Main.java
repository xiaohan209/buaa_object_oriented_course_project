import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

///begin the assh

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ss = scanner.nextLine();
        ss = ss.replaceAll("\\s*", "");
        Pattern pattern = Pattern.compile("[+|-]{0,2}(\\d*.)?x(..[+|-]?\\d+)?");
        Matcher m = pattern.matcher(ss);
        Map<BigInteger, BigInteger> map = new HashMap<>();
        boolean have = false;
        while (m.find()) {
            String son = ss.substring(m.start(), m.end());
            Term term = new Term(son);
            BigInteger coef = term.getCoef();
            BigInteger index = term.getIndex();
            coef = coef.multiply(index);
            index = index.subtract(BigInteger.valueOf(1));
            if (!map.containsKey(index)) {map.put(index, coef); }
            else {  BigInteger bigInteger = map.get(index);
                map.put(index, bigInteger.add(coef));}
        }
        for (BigInteger bigInteger : map.keySet()) {
            BigInteger one = BigInteger.valueOf(1);
            BigInteger zero = BigInteger.valueOf(0);
            BigInteger minus = BigInteger.valueOf(-1);
            BigInteger coef = map.get(bigInteger);
            if (coef.compareTo(zero) != 0) {
                if (coef.compareTo(zero) > 0) {
                    System.out.print("+");
                }//正数补加号；
                if (coef.compareTo(minus) == 0) {
                    if (bigInteger.compareTo(zero) == 0) {
                        System.out.print(-1);
                    } else if (bigInteger.compareTo(one) == 0) {
                        System.out.print("-x");
                    } else { System.out.print("-x**" + bigInteger); }
                } else if (bigInteger.compareTo(one) == 0) {
                    System.out.print(coef + "*x");
                } else if (bigInteger.compareTo(zero) == 0) {
                    System.out.print(coef);
                } else if (coef.compareTo(one) == 0) {
                    if (bigInteger.compareTo(zero) == 0) {
                        System.out.print(1);
                    } else if (bigInteger.compareTo(one) == 0) {
                        System.out.print("x");
                    } else { System.out.print("x**" + bigInteger); }
                }   else { System.out.print(coef + "*x**" + bigInteger); }
                have = true;//有东西输出
            }
        } if (!have) {
            System.out.print("0");
        }
    }
}
