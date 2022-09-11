import java.math.BigInteger;

public class Term {
    ///zhushizhushi
    private BigInteger index;
    private BigInteger coef;

    public BigInteger getCoef() {
        return coef;
    }

    public BigInteger getIndex() { return index; }

    public Term(String s) {
        coef = BigInteger.valueOf(1);
        index = BigInteger.valueOf(1);
        boolean flag = false;
        int i = 0;
        int j = s.indexOf('x');;
        while (!Character.isDigit(s.charAt(i))&&s.charAt(i)!='x') {
            if (s.charAt(i) == '-') { flag = !flag; }
            i++;
        }
        if (i!=j) {
            BigInteger bigInteger = new BigInteger(s.substring(i, j-1));
            coef = coef.multiply(bigInteger);
        }
        if (j != s.length() - 1)
        {
            //有指数的情况
            j += 3;
            index = new BigInteger(s.substring(j));
        }
        if (flag) { coef = coef.multiply(BigInteger.valueOf(-1)); }
    }



}
