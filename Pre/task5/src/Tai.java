public class Tai extends CuboidBox {
    private Double r1;
    private Double r2;
    private Double hei;

    public Double getR1() {
        return this.r1;
    }

    public Double getR2() {
        return this.r2;
    }

    public Double getHei() {
        return this.hei;
    }

    Tai(Double r1,Double r2,Double hei) {
        super(Math.PI,hei / 3,r1 * r1 + r2 * r2 + r1 * r2,"2");
        this.r1 = r1;
        this.r2 = r2;
        this.hei = hei;
    }
}
