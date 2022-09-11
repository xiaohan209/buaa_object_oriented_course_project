class CuboidBox {
    private Double length;
    private Double width;
    private Double height;

    CuboidBox(Double length, Double width, Double height) {
        this.height = height;
        this.length = length;
        this.width = width;
    }

    public Double printVolumn() {
        return this.length * this.width * this.height;
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
}
