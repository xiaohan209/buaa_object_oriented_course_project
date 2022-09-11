import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static String convert(String x, String y) {
        if ((x.equals("-") && y.equals("-")) || (x.equals("+") && y.equals("+"))) {
            return "+";
        }
        else { return "-"; }
    }

    public static void main(String[] args) {
        int state = 0;
        String notation = "";
        String coefficient = "";
        String exponent = "";
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        char[] input = str.toCharArray();
        ArrayList<String> input2 = new ArrayList<>();
        Polynomial polynomial = new Polynomial();
        for (char c : input) {
            String in = String.valueOf(c);
            if (in.equals(" ") || in.equals("\t")) {
                continue;
            }
            input2.add(in);
        }
        input2.add("+");
        for (int i = 0; i < input2.size(); i++) {
            String in = input2.get(i);
            switch (state) {
                case 0:
                    if (in.equals("+") || in.equals("-")) {
                        state = 1;
                        notation = in;
                    }
                    else if (Character.isDigit(in.charAt(0))) {
                        state = 3;
                        notation = "+";
                        coefficient = in;
                    }
                    else if (in.equals("x")) {
                        coefficient = "1";
                        state = 4;
                    }
                    break;
                case 1:
                    if (in.equals("-") || in.equals("+")) {
                        state = 2;
                        notation = convert(notation, in);
                    }
                    else if (Character.isDigit(in.charAt(0))) {
                        state = 3;
                        coefficient = in;
                    }
                    else if (in.equals("x")) {
                        coefficient = "1";
                        state = 4;
                    }
                    break;
                case 2:
                    if (Character.isDigit(in.charAt(0))) {
                        state = 3;
                        coefficient = in;
                    }
                    else if (in.equals("x")) {
                        state = 4;
                        coefficient = "1";
                    }
                    break;
                case 3:
                    if (Character.isDigit(in.charAt(0))) {
                        coefficient = coefficient + in;
                    }
                    else if (in.equals("*")) {
                        state = 4;
                        i = i + 1;
                    }
                    else {
                        //System.out.println("!3!" + notation + coefficient);
                        polynomial.add(new Monomial("0", notation + coefficient));
                        state = 1;
                        coefficient = "";
                        notation = in;
                    }
                    break;
                case 4:
                    if (in.equals("*")) {
                        i = i + 1;
                        state = 5;
                    }
                    else {
                        //System.out.println("!4!" + notation + coefficient + "x");
                        polynomial.add(new Monomial("1", notation + coefficient));
                        state = 1;
                        coefficient = "";
                        notation = in;
                    }
                    break;
                case 5:
                    if (Character.isDigit(in.charAt(0))) {
                        exponent = exponent + in;
                    }
                    else if (exponent.equals("") && (in.equals("+") || in.equals("-"))) {
                        exponent = exponent + in;
                    }
                    else {
                        //System.out.println("!5!" + notation + coefficient + "x^" + Integer.parseInt(exponent));
                        polynomial.add(new Monomial(exponent, notation + coefficient));
                        exponent = "";
                        coefficient = "";
                        state = 1;
                        notation = in;
                    }
                    break;
                default:
                    break;
            }
        }
        polynomial.derivative();
        System.out.println(polynomial.print());
    }
}
