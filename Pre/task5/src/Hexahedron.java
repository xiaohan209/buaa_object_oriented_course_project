public class Hexahedron extends CuboidBox {
    private Double bx;
    private Double cx;
    private Double cy;

    Hexahedron(Double ax,Double bx,Double by,Double cx,Double cy,Double cz) {
        super(ax,by,cz,"1");
        this.bx = bx;
        this.cx = cx;
        this.cy = cy;
    }

    public Double getBx() {
        return bx;
    }

    public Double getCx() {
        return cx;
    }

    public Double getCy() {
        return cy;
    }
}
