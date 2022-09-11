import java.util.Map;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        String text = scanIt();
        collect(text);
    }

    public static String scanIt() {
        Scanner sc = new Scanner(System.in);
        String text = "";
        int[] record = new int[100000];
        int line = 0;
        int count = 0;
        while (sc.hasNext()) {
            line++;
            String nextLine = sc.nextLine();
            if (nextLine.isEmpty()) {
                record[line]=count;
            }
            else if (nextLine.charAt(nextLine.length() - 1) == '-') {
                text = text + nextLine.substring(0,nextLine.length() - 2);
                count += nextLine.length() - 1;
                record[line] = count;
            }
            else {
                text = text + nextLine.substring(0,nextLine.length() - 1);
                count += nextLine.length();
                record[line] = count;
            }
        }
        text = text.toLowerCase();
        return text;
    }

    public static void collect(String text) {
        TreeMap<String, Integer> allWords = new TreeMap<String, Integer>();
        int cnt = 0;
        Pattern danCi = Pattern.compile("[-a-z]+");
        Matcher findWords = danCi.matcher(text);
        while (findWords.find()) {
            cnt++;
            String newWord = findWords.group();
            if (allWords.get(newWord) == null) {
                allWords.put(newWord,1);
            }
            else {
                allWords.put(newWord,allWords.get(newWord) + 1);
            }
        }
        Comparator<Map.Entry<String, Integer>> comp =
                new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> s1,
                               Map.Entry<String, Integer> s2) {
                return s1.getKey().compareTo(s2.getKey());
            } };
        ArrayList<Map.Entry<String, Integer>> list =
                new ArrayList<Map.Entry<String,Integer>>(allWords.entrySet());
        list.sort(comp);
        for (Map.Entry<String,Integer> word:list) {
            Double rate = (word.getValue().doubleValue()) / cnt * 100;
            System.out.println(word.getKey() + " " + word.getValue()
                    + " " + String.format("%.2f",rate) + "%");
        }
    }
}

/*
asdh weiury kqjwh ufsd-
werkj hsdfio uoiewur ou qw-
ksdjh oweui ruoiwqwyoe iuoiue-
asd-sdf-sdf-ewr-dfg-asd sdhfjk hkjhs
skdjf hkjsdhfjk sdh jksdh jksh
 */