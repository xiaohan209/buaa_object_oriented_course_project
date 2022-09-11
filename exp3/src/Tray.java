import java.util.ArrayList;

public class Tray {
    private volatile int num;
    private int[] array;

    Tray() {
        array = new int[2];
        num = 0;
    }

    public synchronized int get() {
        while (num<=0) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                ;
            }
        }
        num--;
        notifyAll();
        int value=array[0];
        array[0]=array[1];
        //System.out.println("Consumer get:" + value);
        return value;
    }

    public synchronized void put(int v) {
        while (num>=2) {
            try{
                wait();
            }
            catch (InterruptedException e){
                ;
            }
        }
        //System.out.println("Producer put:" + v);
        array[num]=v;
        num++;
        notifyAll();
    }
}

