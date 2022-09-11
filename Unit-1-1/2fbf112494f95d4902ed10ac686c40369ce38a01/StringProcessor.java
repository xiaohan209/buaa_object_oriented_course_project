
public class StringProcessor {

    public static String processString(String str) {
        String newStr;
        newStr = str.replaceAll("\\s+", "");
        newStr = newStr.replaceAll("\\+\\+|--", "+");
        newStr = newStr.replaceAll("\\+-|-\\+", "-");
        return newStr;
    }

    //    public static void main(String[] args) {
    //        Scanner scan = new Scanner(System.in);
    //        String str = scan.nextLine();
    //        System.out.println(StringProcessor.processString(str));
    //    }

}
