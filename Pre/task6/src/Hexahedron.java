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

    @Override
    public boolean isEqual(CuboidBox another) {
        if (super.isEqual(another)) {
            if (this.bx.equals(((Hexahedron)another).bx)) {
                if (this.cx.equals(((Hexahedron)another).cx)) {
                    if (this.cy.equals(((Hexahedron)another).cy)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
