import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    private static PolyParser parser = new PolyParser();
    private static Scanner scanner = new Scanner(System.in);
    private static ExpReader reader = new ExpReader(scanner.nextLine());
    
    public static void main(String[] args) {
        ArrayList<String> termList = reader.readPolyTerms();
        printPolyList(termList);
    }
    
    private static void printPolyList(ArrayList<String> list) {
        parser.parseList(list);
        ArrayList<Poly> polyList = parser.getDiffPolyList();
        boolean firstPrint = true;
        boolean hasTwoTerm = true;
        boolean allZeroTerm = true;
        for (Poly poly : polyList) {
            if (poly.getCoeff().compareTo(BigInteger.ZERO) != 0) {
                allZeroTerm = false;
            }
        }
        if (allZeroTerm) {
            System.out.println("0");
        } else {
            for (Poly poly : polyList) {
                // Skip zero term
                if (poly.getCoeff().equals(BigInteger.ZERO)) { continue; }
                // print poly set
                if (poly.getCoeff().compareTo(BigInteger.ZERO) >= 0 && !firstPrint) {
                    System.out.print("+");
                }
                System.out.print(poly.toString());
                firstPrint = false;
            }
        }
        
        
    }
    
}
