import java.util.Scanner;

public class Scan {
    private int[] record = new int[100000];
    private int line = 0;
    private int count = 0;

    public String scanIt() {
        Scanner sc = new Scanner(System.in);
        String text = "";
        record[1] = 0;
        this.line++;
        while (sc.hasNext()) {
            this.line += 1;
            String nextLine = sc.nextLine();
            if (nextLine.isEmpty()) {
                this.record[line] = this.count;
            }
            else if (nextLine.charAt(nextLine.length() - 1) == '-') {
                text = text + nextLine.substring(0,nextLine.length() - 1);
                this.count += nextLine.length() - 1;
                this.record[line] = this.count;
            }
            else {
                text = text + nextLine.substring(0,nextLine.length()) + " ";
                this.count += nextLine.length() + 1;
                this.record[line] = this.count;
            }
        }
        text = text.toLowerCase();
        return text;
    }

    public int getCount() {
        return this.count;
    }

    public int getLine() {
        return  this.line;
    }

    public int[] getRecord() {
        return record;
    }
}
