public class Word {
    private int line;
    private int count;
    private String word;

    public int getLine() {
        return this.line;
    }

    public int getCount() {
        return this.count;
    }

    public String getWord() {
        return this.word;
    }

    public String getReverse() {
        String reversedWord = "";
        for (int i = this.word.length() - 1;i >= 0;i--) {
            reversedWord += this.word.charAt(i);
        }
        if (this.word.compareTo(reversedWord) > 0) {
            return reversedWord;
        }
        else {
            return this.word;
        }
    }

    Word(String word,int index,int[] record,int line) {
        int target = 0;
        for (int i = 1;i <= line;i++) {
            if (index < record[i]) {
                target = i;
                break;
            }
        }
        if (target == 0) {
            target = line + 1;
        }
        this.line = target - 1;
        this.count = index - record[target - 1] + 1;
        this.word = word;
    }
}
/*
ni hao,
ni shi zui pang de.
 */