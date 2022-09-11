import java.util.Vector;

public class Rocket extends Vehicle{
    @Override
    public void Run() {
        super.Run();
        System.out.println("is Rocket");
    }

    public Rocket(int id,int price,String name,double speed) {
        super(id,price,name,speed);
    }

    public void launch(Vector<Integer> as) {
        System.out.println(as.toString());;
    }
}
