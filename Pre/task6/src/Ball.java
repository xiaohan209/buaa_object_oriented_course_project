public class Ball extends CuboidBox {
    private Double r1;

    public Double getR1() {
        return this.r1;
    }

    Ball(Double r1) {
        super(r1, r1,Math.PI * r1 * 4 / 3,"3");
        this.r1 = r1;
    }

    @Override
    public boolean isEqual(CuboidBox another) {
        if (super.isEqual(another)) {
            if (this.r1.equals(((Ball)another).r1)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
