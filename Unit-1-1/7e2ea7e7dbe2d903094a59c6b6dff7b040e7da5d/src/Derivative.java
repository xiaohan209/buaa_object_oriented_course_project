import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

public class Derivative {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Polynomial mypoly = new Polynomial(input.nextLine());
        HashMap<BigInteger, Poly> polymap = mypoly.getPolyitem();
        printAnswer(polymap);
    }

    public static void printAnswer(HashMap<BigInteger, Poly> polymap) {
        String firstitem = "";
        String answer = "";
        for (Poly p : polymap.values()) {
            Poly tmp = p.derivative();
            if (firstitem.isEmpty()) {
                if (tmp.getCoeff().compareTo(BigInteger.ZERO) > 0) {
                    firstitem += tmp.toString();
                    continue;
                }
            }
            String tmpstr = tmp.toString();
            if (!tmpstr.isEmpty()) {
                if (tmpstr.charAt(0) == '-') {
                    answer += tmpstr;
                } else {
                    answer += "+" + tmpstr;
                }
            }
        }
        answer = firstitem + answer;
        if (answer.isEmpty()) {
            answer = "0";
        }
        System.out.println(answer);
    }
}