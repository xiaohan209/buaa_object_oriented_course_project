import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Monomial> poly = new ArrayList<>();

    public void add(Monomial monomial) {
        boolean check = false;
        for (Monomial i : poly) {
            if (i.getExponent().compareTo(monomial.getExponent()) == 0) {
                i.setCoefficient(i.getCoefficient().add(monomial.getCoefficient()));
                check = true;
                break;
            }
        }
        if (!check) {
            poly.add(monomial);
        }
    }

    public void test() {
        for (Monomial i : poly) {
            System.out.println(i.getCoefficient().toString() + "x^" + i.getExponent().toString());
        }
    }

    public void derivative() {
        for (Monomial i : poly) {
            i.derivative();
        }
    }

    public String print() {
        StringBuilder outcome = new StringBuilder();
        for (Monomial monomial : poly) {
            boolean positive = false;
            StringBuilder str = new StringBuilder();
            String exponent = monomial.getExponent().toString();
            String coefficient = monomial.getCoefficient().toString();
            if (coefficient.charAt(0) != '-' && coefficient.charAt(0) != '0') {
                positive = true;
                str.append("+").append(coefficient);
            } else if (coefficient.charAt(0) == '-') {
                str.append(coefficient);
            } else {
                continue;
            }
            if (exponent.equals("1")) {
                str.append("*x");
            } else if (!exponent.equals("0")) {
                str.append("*x**").append(exponent);
            }
            if (positive) {
                outcome.insert(0, str);
            } else {
                outcome.append(str);
            }
        }
        String out = outcome.toString();
        if (out.equals("")) {
            return "0";
        }
        if (out.charAt(0) == '+') {
            return out.substring(1);
        }
        else { return out; }
    }
}
