public class Cone extends CuboidBox {
    private Double r1;
    private Double hei;

    public Double getR1() {
        return this.r1;
    }

    public Double getHei() {
        return this.hei;
    }

    Cone(Double r1,Double hei) {
        super(r1,r1,Math.PI * hei / 3,"2.2");
        this.r1 = r1;
        this.hei = hei;
    }

    @Override
    public boolean isEqual(CuboidBox another) {
        if (super.isEqual(another)) {
            if (this.r1.equals(((Cone)another).r1)) {
                if (this.hei.equals(((Cone)another).hei)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
