import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;

import java.util.ArrayList;

public class Elevator implements Runnable {
    private volatile int status;
    //0 is not move
    //1 is opening
    //2 is closing
    //3 is up
    //4 is down
    private volatile int elevatorId;
    private volatile int floor;
    private volatile int target;
    private volatile ArrayList<PersonRequest> queue;
    private boolean empty;
    private Dispatch dispatch;

    public boolean isEmpty() {
        return empty;
    }

    Elevator(int id) {
        elevatorId = id;
        status = 0;
        floor = 1;
        queue = new ArrayList<PersonRequest>();
        empty = true;
    }

    public void controlEle(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    public int getFloor() {
        return floor;
    }

    public int getStatus() {
        return status;
    }

    public void up() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        floor++;
        TimableOutput.println("ARRIVE-" + floor);
    }

    public void down() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        floor--;
        TimableOutput.println("ARRIVE-" + floor);
    }

    public void addToQueue(PersonRequest request) {
        queue.add(request);
        TimableOutput.println("IN-" + request.getPersonId() + "-" + floor);
        if (empty) {
            empty = false;
        }
    }

    public void remove(int i) {
        TimableOutput.println("OUT-" + queue.remove(i).getPersonId() + "-" + floor);
        empty = queue.isEmpty();
    }

    public void open() {
        TimableOutput.println("OPEN-" + floor);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < queue.size(); i++) {
            if (floor == queue.get(i).getToFloor()) {
                remove(i);
                i--;
            }
        }
    }

    public void findTarget() {
        if (queue.isEmpty()) {
            target = dispatch.findTarget();
            if (target == floor) {
                target = dispatch.changeTarget();
            }
        }
        else {
            target = queue.get(0).getToFloor();
        }
    }

    public void findStatus() {
        if (target == 0) {
            status = 0;
        }
        else if (target > floor) {
            status = 3;
        }
        else if (target < floor) {
            status = 4;
        }
    }

    public void close() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < dispatch.getReque().size(); i++) {
            PersonRequest tryRequest = dispatch.getReque().get(i);
            if (tryRequest.getFromFloor() == floor) {
                if (tryRequest.getToFloor() > floor && status == 3) {
                    addToQueue(dispatch.into(i));
                    i--;
                }
                else if (tryRequest.getToFloor() < floor && status == 4) {
                    addToQueue(dispatch.into(i));
                    i--;
                }
            }
        }
        TimableOutput.println("CLOSE-" + floor);
    }

    public boolean checkFloor() {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getToFloor() == floor) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        while (dispatch.isWork() || status != 0) {
            if (target == 0 && dispatch.isEmpty()) {
                ;
            }
            findTarget();
            findStatus();
            if (checkFloor() || dispatch.hasPeople(floor,status)) {
                open();
                findTarget();
                findStatus();
                close();
                continue;
            }
            if (status == 3) {
                up();
            }
            else if (status == 4) {
                down();
            }
        }
    }
}
