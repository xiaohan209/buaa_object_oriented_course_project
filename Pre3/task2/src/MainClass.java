import java.util.TreeSet;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainClass {
    public static String scan(Scanner sc) throws Exception {
        return sc.nextLine();
    }

    public static void main(String[] args) {
        Pattern splitter1 = Pattern.compile("[\\s\\n]+");
        Pattern splitter2 = Pattern.compile("[.,]+[\\s\\n]");
        Pattern finder = Pattern.compile("\\b[a-z]+\\b");
        TreeSet<String> allWords = new TreeSet<String>(new Comparator<String>()
        { public int compare(String s1, String s2) {
                return s1.compareTo(s2); } });
        Scanner sc = new Scanner(System.in);
        String text = "";
        while (sc.hasNext()) {
            text += sc.nextLine() + "\n";
        }
        text = text.toLowerCase();
        String[] thisLine = splitter2.split(text);
        //System.out.println(thisLine.length + "fucked");
        for (int i = 0; i < thisLine.length; i++) {
            String[] secondSplit = splitter1.split(thisLine[i]);
            for (int j = 0;j < secondSplit.length;j++) {
                allWords.add(secondSplit[j]);
            }
        }
        for (String str:allWords) {
            System.out.println(str);
        }
    }
}
