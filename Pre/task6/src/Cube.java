public class Cube extends CuboidBox {

    Cube(Double length) {
        super(length,length,length,"1.1.1");
    }

    @Override
    public void setLength(Double length) {
        super.setLength(length);
        super.setWidth(length);
        super.setHeight(length);
    }
}
