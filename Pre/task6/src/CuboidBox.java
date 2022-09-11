public class CuboidBox {

    private Double length;
    private Double width;
    private Double height;
    private String type;

    CuboidBox(Double length, Double width, Double height, String type) {
        this.height = height;
        this.length = length;
        this.width = width;
        this.type = type;
    }

    public Double printVolumn() {
        return Math.abs(this.length * this.width * this.height);
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEqual(CuboidBox another) {
        if (another.getClass().equals(this.getClass())) {
            if (this.type.equals(another.type)) {
                if (this.getLength().equals(another.getLength())) {
                    if (this.getWidth().equals(another.getWidth())) {
                        if (this.getHeight().equals(another.getHeight())) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
            }
            return false;
        }
        return false;
    }
}
