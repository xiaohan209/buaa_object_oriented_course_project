import java.util.Scanner;

public class Scan {

    public String scanIt() {
        Scanner sc = new Scanner(System.in);
        String text = "";
        while (sc.hasNext()) {
            String nextLine = sc.nextLine();
            if (nextLine.isEmpty()) {
                text = text;
            }
            else if (nextLine.charAt(nextLine.length() - 1) == '-') {
                text = text + nextLine.substring(0,nextLine.length() - 1);
            }
            else {
                text = text + nextLine + " ";
            }
        }
        return text;
    }
}
/*
aabbaacc aabcbcbc AAbcBc bccxxbA axaxbxbxcxbxcxc y
 */
