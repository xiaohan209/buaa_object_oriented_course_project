import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.io.IOException;

public class InputHandler implements Runnable {
    private Buffer buffer;
    private Cutter cutter;

    public InputHandler(Buffer buffer,Cutter cutter) {
        this.buffer = buffer;
        this.cutter = cutter;
    }

    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        ElevatorFactory elevatorFactory = new ElevatorFactory();
        for (int i = 0; i < 3; i++) {
            String c = String.format("%c",i + 65);
            Elevator elevator = elevatorFactory.produceElevator(c,c,buffer);
            Thread ele = new Thread(elevator);
            ele.start();
        }
        while (true) {
            Request request = elevatorInput.nextRequest();
            if (request == null) {
                buffer.addRequest(null);
                break;
            } else {
                if (request instanceof PersonRequest) {
                    buffer.addRequest(cutter.cut((PersonRequest) request));
                } else if (request instanceof ElevatorRequest) {
                    Elevator elevator = elevatorFactory.produceElevator(
                            ((ElevatorRequest) request).getElevatorId(),
                            ((ElevatorRequest) request).getElevatorType(),buffer);
                    Thread ele = new Thread(elevator);
                    ele.start();
                }
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
