import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String polyStr = scanner.nextLine();

        StringProcessor stringProcessor = new StringProcessor();
        polyStr = stringProcessor.processString(polyStr);

        ArrayList<Term> arrayList = new ArrayList<>();
        Polynomial polynomial = new Polynomial(arrayList);
        PolynomialProducer polynomialProducer = new PolynomialProducer();
        polynomial = polynomialProducer.creatPoly(polyStr);

        polynomial = polynomial.derivative();
        if (polynomial.toString().length() == 0) {
            System.out.println("0");
        }
        else {
            if (polynomial.toString().charAt(0) == '+') {
                System.out.println(polynomial.toString().substring(1));
            }
            else {
                System.out.println(polynomial.toString());
            }
        }

    }
}
