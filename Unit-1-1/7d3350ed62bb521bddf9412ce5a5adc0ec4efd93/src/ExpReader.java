import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpReader {
    private String source;
    private final String polyRegEx =
            "([+-]\\s*)?((([+-]?\\d*\\s*\\*?\\s*)?x(\\s*\\*\\*\\s*[+-]?\\d+)?)|([+-]?\\d+))";
    private ArrayList<String> strTerms = new ArrayList<>();
    
    public ExpReader(String source) {
        this.source = source;
    }
    
    public ArrayList<String> readPolyTerms() {
        Pattern pattern = Pattern.compile(polyRegEx);
        Matcher matcher = pattern.matcher(source);
        int start;
        int end;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
            strTerms.add(source.substring(start,end));
        }
        return strTerms;
    }
}
