import java.util.Scanner;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        Pattern splitter = Pattern.compile("[.,]*[\\s\\n]+");
        Pattern finder = Pattern.compile("\\b\\w\\b");
        Scanner sc = new Scanner(System.in);
        for (;sc.hasNext();) {
            System.out.println(splitter.split(sc.nextLine()).length);
        }
    }
}
