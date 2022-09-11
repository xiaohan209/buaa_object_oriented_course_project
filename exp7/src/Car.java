public class Car extends Vehicle {
    @Override
    public void Run() {
        super.Run();
        System.out.println("is Car");
    }

    public Car(int id,int price,String name,double speed) {
        super(id,price,name,speed);
    }

    public int fix(int fixInt) {
        return fixInt;
    }

    public String fix(String fixString) {
        return fixString;
    }
}
