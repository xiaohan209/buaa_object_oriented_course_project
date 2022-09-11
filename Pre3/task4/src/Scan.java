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
                //System.out.println("youganggang");
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
        //System.out.println("record1:" + record[1]);
        //System.out.println("record2:" + record[2]);
        //System.out.println("record3:" + record[3]);
        //System.out.println("record4:" + record[4]);
        //System.out.println("record5:" + record[5]);
        //System.out.println("record6:" + record[6]);
        text = text.toLowerCase();
        
        //System.out.println(text);
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
