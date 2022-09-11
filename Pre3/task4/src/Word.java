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

    Word(String word,int index,int[] record,int line) {
        int target = 0;
        for (int i = 1;i <= line;i++) {
            //System.out.println("ceshi:" +index + "," + record[i]);
            if (index < record[i]) {
                //System.out.println("haole:" + i);
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