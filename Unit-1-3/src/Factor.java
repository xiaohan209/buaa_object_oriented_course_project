public abstract class Factor {
    /*
        constant is 0;
        power is 1;
        regex is 2;
        sin is 3;
        cos is 4;
    */

    private int type;
    private boolean warn;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getWarn() {
        return warn;
    }

    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    public Factor(int type) {
        this.warn = false;
        this.type = type;
    }

    public abstract String out();

    public abstract Term dif();

    public abstract boolean plusFactor(Factor fact);

    public abstract boolean multiFactor(Factor fact);

    public abstract void merge(Factor fact);

    public abstract boolean isSame(Factor fact);
}
