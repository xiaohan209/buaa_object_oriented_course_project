public class Vector {
    private Double x1;
    private Double y1;
    private Double z1;

    public Double getX() {
        return this.x1;
    }

    public void setX(Double x) {
        this.x1 = x;
    }

    public Double getY() {
        return this.y1;
    }

    public void setY(Double y) {
        this.y1 = y;
    }

    public Double getZ() {
        return this.z1;
    }

    public void setZ(Double z) {
        this.z1 = z;
    }

    public Vector chaCheng(Vector another) {
        Vector result = new Vector(.0,.0,.0);
        result.setX(this.y1 * another.getZ() - this.z1 * another.getY());
        result.setY(this.z1 * another.getX() - this.x1 * another.getZ());
        result.setZ(this.x1 * another.getY() - this.y1 * another.getX());
        System.out.print(result.getX() + " ");
        System.out.print(result.getY() + " ");
        System.out.println(result.getZ());
        return result;
    }

    public Vector plus(Vector another) {
        Vector plusResult = new Vector(.0,.0,.0);
        plusResult.setX(this.x1 + another.getX());
        plusResult.setY(this.y1 + another.getY());
        plusResult.setZ(this.z1 + another.getZ());
        System.out.print(plusResult.getX() + " ");
        System.out.print(plusResult.getY() + " ");
        System.out.println(plusResult.getZ());
        return plusResult;
    }

    public Vector minus(Vector another) {
        Vector minusResult = new Vector(.0,.0,.0);
        minusResult.setX(this.x1 - another.getX());
        minusResult.setY(this.y1 - another.getY());
        minusResult.setZ(this.z1 - another.getZ());
        System.out.print(minusResult.getX() + " ");
        System.out.print(minusResult.getY() + " ");
        System.out.println(minusResult.getZ());
        return minusResult;
    }

    public Double moChang() {
        return Math.sqrt(this.x1 * this.x1 + this.y1
                * this.y1 + this.z1 * this.z1);
    }

    Vector(Double x1,Double y1,Double z1) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
    }
}
