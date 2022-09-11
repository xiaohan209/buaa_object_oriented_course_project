import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        Regex regex = new Regex(text);
        if (regex.getWarn()) {
            System.out.println("WRONG FORMAT!");
            return;
        }
        else {
            Regex difRegex = regex.different();
            difRegex.simplify();
            difRegex.sort();
            String result = difRegex.out();
            if (result.isEmpty()) {
                System.out.println("0");
            }
            else {
                System.out.println(result);
            }
        }
    }
}
/*
4*x+ x**2 +x
4x+ x**2 +x
- -4*x + x ** 2 + x
+4*x - -x**2 + x
cos(x)* sin(x)*5+4 *x**3
5* x**4* sin(x) -- -5* x**4* cos(x)
sin(x)
cos(x)* sin(x)*5+4 *x**3

for (int i = 0;i < regex.getPoly().size();i++) {
            System.out.print("main :(" + regex.getPoly().get(i).getCoeff());
            System.out.print("," + regex.getPoly().get(i).getXiExp());
            System.out.print("," + regex.getPoly().get(i).getSinExp());
            System.out.println("," + regex.getPoly().get(i).getCosExp() + ")");
        }
 */
