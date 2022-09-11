import com.oocourse.TimableOutput;

public class MainClass {
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        Dispatch dispatch = new Dispatch();
        Elevator elevator1 = new Elevator(1);
        elevator1.controlEle(dispatch);
        InputHandler input = new InputHandler(dispatch,elevator1);
        Thread haveRequest = new Thread(input);
        Thread ele = new Thread(elevator1);
        haveRequest.start();
        ele.start();
    }
}
