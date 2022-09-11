import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class PolyParser {
    // Parse single Polynomial term, can either be stored
    // or to be directly output
    
    private HashMap<Poly,Poly> diffPolyMap = new HashMap<>();
    
    public Poly parse(String exp) {
        // Parse and store poly
        Scanner scanner = new Scanner(exp);
        BigInteger degree;
        BigInteger coeff;
        // Trim space and \t, simplify sign
        String trimExp = strTrim(exp);
        // Check if it is a constant term
        int indexX = trimExp.indexOf("x");
        if (indexX == -1) {
            coeff = new BigInteger(trimExp);
            degree = BigInteger.ZERO;
        } else { // It is a variable term
            String coeffStr = trimExp.substring(0,indexX);
            String degreeStr = trimExp.substring(indexX + 1);
            coeff = parseCoeffStr(coeffStr);
            degree = parseDegreeStr(degreeStr);
        }
        return new Poly(coeff,degree);
    }
    
    public void parseList(ArrayList<String> exps) {
        for (String exp : exps) {
            Poly newPoly = parse(exp);
            newPoly.diff();
            addToMap(newPoly);
        }
    }
    
    /**
     * Transfer differential poly map into ArrayList
     * @return differential poly ArrayList
     */
    
    public ArrayList<Poly> getDiffPolyList() {
        ArrayList<Poly> polyArrayList = new ArrayList<>(diffPolyMap.size());
        for (Poly poly : diffPolyMap.keySet()) {
            polyArrayList.add(poly);
        }
        Collections.sort(polyArrayList);
        return polyArrayList;
    }
    
    /**
     * Trim space and multiple sign
     * @param origin original string to be trimmed
     * @return trimmed string
     */
    
    private String strTrim(String origin) {
        String ret;
        // trim all space and \t
        ret = origin.replace(" ","");
        ret = ret.replace("\t","");
        // return to avoid out of range
        if (ret.length() < 2) { return ret; }
        // check multiple add / minus
        char ch0 = ret.charAt(0);
        char ch1 = ret.charAt(1);
        if (ch0 == '-' && ch1 == '-') {
            ret = ret.substring(2);
        } else if (ch0 == '+' && ch1 == '+') {
            ret = ret.substring(1);
        } else if (ch0 == '-' && ch1 == '+') {
            ret = "-" + ret.substring(2);
        } else if (ch0 == '+' && ch1 == '-') {
            ret = "-" + ret.substring(2);
        }
        return ret;
    }
    
    /**
     * If poly map has new poly, merge. Otherwise add it to the map
     * @param newPoly the poly to be added
     */
    
    private void addToMap(Poly newPoly) {
        if (diffPolyMap.get(newPoly) != null) {
            diffPolyMap.get(newPoly).add(newPoly.getCoeff());
        } else {
            diffPolyMap.put(newPoly,newPoly);
        }
    }
    
    /**
     * @param str Everything before x, even if it is empty.
     * @return coefficient in BigInterger
     */
    private BigInteger parseCoeffStr(String str) {
        // Empty string, indicates x
        if (str.length() == 0) {
            return BigInteger.ONE;
        }
        // +x
        if (str.length() == 1 && str.charAt(0) == '+') {
            return BigInteger.ONE;
        }
        // -x
        if (str.length() == 1 && str.charAt(0) == '-') {
            return new BigInteger("-1");
        }
        // .*x
        int indexCoeff = str.indexOf("*");
        return new BigInteger(str.substring(0,indexCoeff));
    }
    
    /**
     * @param str Everything after x, even if it is empty
     * @return degree in BigInterger
     */
    private BigInteger parseDegreeStr(String str) {
        // Empty string, indicates .*x
        if (str.length() == 0) {
            return BigInteger.ONE;
        }
        // .*x\*\*.*
        int indexDegree = str.indexOf("**");
        return new BigInteger(str.substring(indexDegree + 2));
    }
}
