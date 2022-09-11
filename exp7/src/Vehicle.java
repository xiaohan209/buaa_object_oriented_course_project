public class Vehicle {
    private int id;
    private int price;
    private String name;
    private double speed;

    public void Run() {
        System.out.println("caonima\n");
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public Vehicle(int id,int price,String name,double speed) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.speed = speed;
    }
}
