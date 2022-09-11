import com.oocourse.TimableOutput;

public class Main {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        Cutter cutter = new Cutter();
        Buffer buffer = new Buffer();
        InputHandler inputHandler = new InputHandler(buffer,cutter);
        Thread input = new Thread(inputHandler);
        input.start();
    }
}
