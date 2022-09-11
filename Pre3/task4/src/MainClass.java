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
        TreeMap<String, ArrayList<Word>> allWords =
                new TreeMap<String, ArrayList<Word>>();
        int cnt = 0;
        Pattern danCi = Pattern.compile("[-a-z]+");
        Matcher findWords = danCi.matcher(scan.scanIt());
        while (findWords.find()) {
            cnt++;
            String newWord = findWords.group();
            Word nextWord = new Word(newWord,findWords.start(),
                    scan.getRecord(),scan.getLine());
            ArrayList<Word> newList;
            if (allWords.get(nextWord.getWord()) != null) {
                newList = allWords.get(newWord);
                allWords.remove(newWord);
            }
            else {
                newList = new ArrayList<Word>();
            }
            newList.add(nextWord);
            allWords.put(newWord,newList);
        }
        Comparator<Map.Entry<String,ArrayList<Word>>> comp =
                new Comparator<Map.Entry<String,ArrayList<Word>>>() {
            public int compare(Map.Entry<String, ArrayList<Word>> s1,
                               Map.Entry<String, ArrayList<Word>> s2) {
                return s1.getKey().compareTo(s2.getKey());
            } };
        ArrayList<Map.Entry<String,ArrayList<Word>>> list =
                new ArrayList<Map.Entry<String,ArrayList
                        <Word>>>(allWords.entrySet());
        list.sort(comp);
        for (Map.Entry<String,ArrayList<Word>> word:list) {
            double rate = (double)(word.getValue().size())
                    / ((double)cnt) * 100;
            System.out.println(word.getKey() + " " + word.getValue().size()
                    + " " + String.format("%.2f",rate) + "%");
            for (int i = 0;i < word.getValue().size();i++) {
                System.out.println("\t(" +
                        word.getValue().get(i).getLine() + ", "
                        + word.getValue().get(i).getCount() + ")");
            }
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