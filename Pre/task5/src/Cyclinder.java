public class Cyclinder extends CuboidBox {
    private Double r1;
    private Double hei;

    public Double getR1() {
        return this.r1;
    }

    public Double getHei() {
        return this.hei;
    }

    Cyclinder(Double r1,Double hei) {
        super(r1,r1,Math.PI * hei,"2.1");
        this.r1 = r1;
        this.hei = hei;
    }
}
