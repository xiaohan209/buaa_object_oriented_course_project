import java.math.BigInteger;

public class TermProducer {

    public Term creatTerm(String coefficientStr, String indexStr) {
        if (coefficientStr.length() != 0 && indexStr.length() != 0) {
            BigInteger coefficient = new BigInteger(coefficientStr);
            BigInteger index = new BigInteger(indexStr);
            return new Term(coefficient, index);
        }
        else {
            return new Term(BigInteger.ZERO, BigInteger.ZERO);
        }
    }

}
