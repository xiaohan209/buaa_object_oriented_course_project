import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        Regex regex = new Regex(text);
        if (regex.getWarn()) {
            System.out.println("WRONG FORMAT!");
        }
        else {
            Regex difRegex = regex.dif();
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