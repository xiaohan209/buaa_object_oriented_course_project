import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        Scan scan = new Scan();
        collect(scan);
    }

    public static void collect(Scan scan) {
        TreeMap<String, Word> allWords =
                new TreeMap<String, Word>();
        Pattern danCi = Pattern.compile("[-a-zA-Z]+");
        Matcher findWords = danCi.matcher(scan.scanIt());
        while (findWords.find()) {
            String newWord = findWords.group();
            if (allWords.get(newWord) == null) {
                Word nextWord = new Word(newWord);
                allWords.put(newWord,nextWord);
            }
        }
        Comparator<Map.Entry<String,Word>> comp =
                new Comparator<Map.Entry<String,Word>>() {
            public int compare(Map.Entry<String, Word> s1,
                               Map.Entry<String, Word> s2) {
                return s1.getKey().compareTo(s2.getKey());
            } };
        ArrayList<Map.Entry<String,Word>> list =
                new ArrayList<Map.Entry<String,Word>>(allWords.entrySet());
        list.sort(comp);
        for (Map.Entry<String,Word> word:list) {
            word.getValue().getStatus();
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