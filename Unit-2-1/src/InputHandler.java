import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.io.IOException;

public class InputHandler implements Runnable {
    private PersonRequest nowRequest;
    private boolean haveRequest;
    private Dispatch dispatch;
    private Elevator elevator;

    public void setHaveRequest(boolean haveRequest) {
        this.haveRequest = haveRequest;
    }

    public boolean getHaveRequest() {
        return haveRequest;
    }

    public PersonRequest getNowRequest() {
        return nowRequest;
    }

    public InputHandler(Dispatch dispatch,Elevator elevator) {
        this.dispatch = dispatch;
        this.elevator = elevator;
    }

    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            nowRequest = elevatorInput.nextPersonRequest();
            haveRequest = true;
            dispatch.addRequest(nowRequest);
            if (nowRequest == null) {
                break;
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
