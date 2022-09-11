import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
    private String word;
    private boolean[] gruop = new boolean[5];

    public String getWord() {
        return this.word;
    }

    Word(String word) {
        this.word = word;
        String wordLower = word.toLowerCase();
        int status = 0;
        gruop[0] = false;
        gruop[3] = false;
        Pattern patternA = Pattern.compile("a{2,3}b{2,4}a{2,4}c{2,3}");
        Pattern patternBC = Pattern.compile("a{2,3}(ba)*(bc){2,4}");
        Pattern patternD1 = Pattern.compile("a{0,3}b+c{2,3}");
        Pattern patternD2 = Pattern.compile("b{1,2}a{1,2}c{0,3}");
        Matcher matcherA = patternA.matcher(this.word);
        Matcher matcherBC = patternBC.matcher(this.word);
        Matcher matcherD1 = patternD1.matcher(this.word);
        Matcher matcherD2 = patternD2.matcher(wordLower);
        if (matcherA.find()) {
            gruop[0] = true;
        }
        if (matcherBC.find()) {
            gruop[1] = true;
            gruop[2] = true;
        }
        else {
            gruop[1] = false;
            if (patternBC.matcher(wordLower).find()) {
                gruop[2] = true;
            }
            else {
                gruop[2] = false;
            }
        }
        if (matcherD1.find() && matcherD1.start() == 0) {
            while (matcherD2.find()) {
                if (matcherD2.end() == this.word.length()) {
                    gruop[3] = true;
                }
            }
        }
        boolean flag = false;
        for (int i = 0;i < this.word.length() && !flag;i++) {
            switch (status) {
                case 0:
                    if (this.word.charAt(i) == 'a') {
                        status = 1;
                    }
                    break;
                case 1:
                    if (this.word.charAt(i) == 'b') {
                        status = 2;
                    }
                    break;
                case 2:
                    if (this.word.charAt(i) == 'b') {
                        status = 3;
                    }
                    break;
                case 3:
                    if (this.word.charAt(i) == 'c') {
                        status = 4;
                    }
                    break;
                case 4:
                    if (this.word.charAt(i) == 'b') {
                        status = 5;
                    }
                    break;
                case 5:
                    if (this.word.charAt(i) == 'c') {
                        status = 6;
                    }
                    break;
                case 6:
                    if (this.word.charAt(i) == 'c') {
                        status = 7;
                        flag = true;
                    }
                    break;
                default:
                    flag = true;
            }
        }
        gruop[4] = flag;
    }

    public void getStatus() {
        int sum = 0;
        for (int i = 0;i < 5;i++) {
            if (gruop[i]) {
                sum++;
            }
        }
        System.out.print(sum);
        if (sum != 0) {
            System.out.print(" ");
            for (int i = 0;i < 5;i++) {
                if (gruop[i]) {
                    switch (i) {
                        case 0:
                            System.out.print("A");
                            break;
                        case 1:
                            System.out.print("B");
                            break;
                        case 2:
                            System.out.print("C");
                            break;
                        case 3:
                            System.out.print("D");
                            break;
                        default:
                            System.out.print("E");
                    }
                }
            }
        }
        System.out.println();
    }
}
/*
ni hao,
ni shi zui pang de.
 */