import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;

import java.io.IOException;

public class InputHandler implements Runnable {
    private int elevatorNum;
    private Buffer buffer;

    InputHandler(Buffer buffer) {
        this.buffer = buffer;
    }

    public synchronized int getElevatorNum() {
        return elevatorNum;
    }

    public synchronized void setElevatorNum(int elevatorNum) {
        this.elevatorNum = elevatorNum;
    }

    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        setElevatorNum(elevatorInput.getElevatorNum());
        for (int i = 0; i < getElevatorNum(); i++) {
            String c = String.format("%c",i + 65);
            Elevator elevator = new Elevator(c,7,200,200,400,buffer);
            Thread ele = new Thread(elevator);
            ele.start();
        }
        while (true) {
            PersonRequest request = elevatorInput.nextPersonRequest();
            buffer.addRequest(request);
            if (request == null) {
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
