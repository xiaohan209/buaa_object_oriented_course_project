import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compute {
    private ArrayList<Power> poly;

    public Compute(String text) {
        poly = new ArrayList<Power>();
        if (text.isEmpty()) {
            System.out.println("caonima");
        }
        String symbol = "[+-]";//minus plus
        String white = "[\\s\\t]*";//space
        String number = symbol + "?\\d*";//0 before number
        String realNumber = "[+-]?\\d+";
        String exp = "\\*\\*" + white + number; //zhishu
        String pow = "x(" + white + exp + ")?";//mihanshu
        String bianLiang = symbol + "?(\\d+" + white + "\\*" + white + ")?" + pow;
        String xiang = bianLiang + "|" + realNumber;
        String shi = white + "((?<fuHao>[+-])" + white + ")?" + "(?<xiang>" + xiang + ")";
        //next matcher
        String changshu = "(?<hao>[+-])?(?<abs>\\d+)";
        String zhiShuPart = "\\*\\*" + white + "(?<expHao>[+-])?(?<expo>\\d+)";
        String zhiShu = "x(" + white + zhiShuPart + ")?";
        String duoXiang = "(?<hao>" + symbol + ")?((?<xiShu>\\d+)"
                + white + "\\*" + white + ")?" + zhiShu;
        //first find
        Pattern powerFuction = Pattern.compile(shi);
        Matcher matcher = powerFuction.matcher(text);
        while (matcher.find()) {
            boolean flag1;
            boolean flag2;
            boolean expFlag;
            boolean nega;
            String fuHao = matcher.group("fuHao");
            String accXiang = matcher.group("xiang");
            flag1 = plusOrMinus(fuHao);
            if (accXiang.contains("x")) {
                Pattern findDuoXiang = Pattern.compile(duoXiang);
                Matcher matchDuoXiang = findDuoXiang.matcher(accXiang);
                if (matchDuoXiang.find()) {
                    //split
                    String coeffHao = matchDuoXiang.group("hao");
                    flag2 = plusOrMinus(coeffHao);
                    String coeff = matchDuoXiang.group("xiShu");
                    if (coeff == null) {
                        coeff = "1";
                    }
                    String expHao = matchDuoXiang.group("expHao");
                    expFlag = plusOrMinus(expHao);
                    String expo = matchDuoXiang.group("expo");
                    if (expo == null) {
                        expo = "1";
                    }
                    if (!expFlag) {
                        expo = "-" + expo;
                    }
                    nega = flag1 ^ flag2;
                    //find and replace
                    int cnt = 0;
                    for (int i = 0; i < poly.size(); i++) {
                        Power acc = poly.get(i);
                        if (acc.getExp().equals(new BigInteger(expo))) {
                            if (nega) {
                                acc.setCoeff(acc.getCoeff().subtract(new BigInteger(coeff)));
                            } else {
                                acc.setCoeff(acc.getCoeff().add(new BigInteger(coeff)));
                            }
                            cnt = 1;
                            break;
                        }
                    }
                    //null and new
                    if (cnt == 0) {
                        if (nega) {
                            Power newPower = new Power("-" + coeff, expo);
                            poly.add(newPower);
                        } else {
                            Power newPower = new Power(coeff,expo);
                            poly.add(newPower);
                        }
                    }
                }
            }
            else {
                //number
                Pattern findChangShu = Pattern.compile(changshu);
                Matcher matchChangshu = findChangShu.matcher(accXiang);
                if (matchChangshu.find()) {
                    //split
                    String xiShuFuHao = matchChangshu.group("hao");
                    String zhi = matchChangshu.group("abs");
                    flag2 = plusOrMinus(xiShuFuHao);
                    nega = flag1 ^ flag2;
                    //find and replace
                    int cnt = 0;
                    for (int i = 0; i < poly.size(); i++) {
                        Power acc = poly.get(i);
                        if (acc.getExp().intValue() == 0) {
                            if (nega) {
                                acc.setCoeff(acc.getCoeff().subtract(new BigInteger(zhi)));
                            } else {
                                acc.setCoeff(acc.getCoeff().add(new BigInteger(zhi)));
                            }
                            cnt = 1;
                            break;
                        }
                    }
                    //null and new
                    if (cnt == 0) {
                        if (nega) {
                            Power newPower = new Power("-" + zhi, "0");
                            poly.add(newPower);
                        } else {
                            Power newPower = new Power(zhi, "0");
                            poly.add(newPower);
                        }
                    }
                }
            }
        }
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

    public Compute() {
        poly = new ArrayList<Power>();
    }

    public Compute different() {
        Compute compute = new Compute();
        for (int i = 0;i < poly.size();i++) {
            compute.getPoly().add(poly.get(i).derivation());
        }
        return compute;
    }

    public String out() {
        String outPut = "";
        Comparator comp = new Comparator<Power>() {
            public int compare(Power p1,Power p2) {
                return p2.getCoeff().compareTo(p1.getCoeff());
            }
        };
        Collections.sort(poly,comp);
        outPut = outPut + poly.get(0).print(true);
        for (int i = 1;i < poly.size();i++) {
            outPut = outPut + poly.get(i).print(false);
        }
        return outPut;
    }

    public ArrayList<Power> getPoly() {
        return poly;
    }
}
