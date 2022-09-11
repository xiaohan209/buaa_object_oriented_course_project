import com.oocourse.TimableOutput;
import com.oocourse.elevator2.PersonRequest;

import java.util.ArrayList;

public class Elevator implements Runnable {

    private volatile String elevatorId;
    private volatile int maxMember;
    private volatile int timeOpen;
    private volatile int timeClose;
    private volatile int timeFloor;
    //0 is not move
    //1 is opening
    //2 is closing
    //3 is up
    //4 is down
    private volatile int status;
    private volatile int floor;
    private volatile int target;
    private volatile ArrayList<PersonRequest> queue;
    private volatile ArrayList<PersonRequest> waiting;
    private volatile int memberCount;
    private Buffer buffer;

    Elevator(String elevatorId,int maxMember,int timeOpen,
             int timeClose,int timeFloor,Buffer buffer) {
        this.elevatorId =  elevatorId;
        this.maxMember = maxMember;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.timeFloor = timeFloor;
        this.memberCount = 0;
        this.floor = 1;
        this.status = 0;
        this.buffer = buffer;
        this.target = 0;
        queue = new ArrayList<PersonRequest>();
        waiting = new ArrayList<PersonRequest>();
    }

    public int getFloor() {
        return floor;
    }

    public int getStatus() {
        return status;
    }

    public void up() {
        try {
            Thread.sleep(timeFloor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (floor == -1) {
            floor = 1;
        }
        else {
            floor++;
        }
        TimableOutput.println("ARRIVE-" + floor + "-" + elevatorId);
    }

    public void down() {
        try {
            Thread.sleep(timeFloor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (floor == 1) {
            floor = -1;
        }
        else {
            floor--;
        }
        TimableOutput.println("ARRIVE-" + floor + "-" + elevatorId);
    }

    public void addToQueue(PersonRequest request) {
        queue.add(request);
        TimableOutput.println("IN-" + request.getPersonId() + "-" + floor + "-" + elevatorId);
        memberCount++;
    }

    public void remove(int i) {
        TimableOutput.println("OUT-" + queue.remove(i).getPersonId()
                + "-" + floor + "-" + elevatorId);
        memberCount--;
    }

    public void open() {
        TimableOutput.println("OPEN-" + floor + "-" + elevatorId);
        try {
            Thread.sleep(timeOpen);
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
        if (queue.isEmpty() && waiting.isEmpty()) {
            //queue->waiting
            PersonRequest req = buffer.findTarget(floor);
            if (req != null) {
                waiting.add(req);
            }
            else {
                target = 0;
                return;
            }
        }
        if (queue.isEmpty()) {
            target = waiting.get(0).getFromFloor();
            if (target == floor) {
                target = waiting.get(0).getToFloor();
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

        int cnt = memberCount;
        while (cnt < maxMember) {
            PersonRequest intoRequest = buffer.into(floor,status);
            if (intoRequest == null) {
                break;
            }
            else {
                waiting.add(intoRequest);
                cnt++;
            }
        }
    }

    public void close() {
        try {
            Thread.sleep(timeClose);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < waiting.size(); i++) {
            PersonRequest tryRequest = waiting.get(i);
            if (tryRequest.getFromFloor() == floor && memberCount < maxMember) {
                if (tryRequest.getToFloor() > floor && status == 3) {
                    addToQueue(waiting.remove(i));
                    i--;
                }
                else if (tryRequest.getToFloor() < floor && status == 4) {
                    addToQueue(waiting.remove(i));
                    i--;
                }
            }
        }
        TimableOutput.println("CLOSE-" + floor + "-" + elevatorId);
    }

    public boolean checkFloor() {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getToFloor() == floor) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPeople() {
        for (int i = 0; i < waiting.size(); i++) {
            PersonRequest tryRequest = waiting.get(i);
            if (tryRequest.getFromFloor() == floor) {
                if (tryRequest.getToFloor() > floor && status == 3) {
                    return true;
                }
                else if (tryRequest.getToFloor() < floor && status == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public void flush() {
        while (waiting.size() > 0) {
            buffer.addRequest(waiting.remove(0));
        }
    }

    public void run() {
        while (buffer.isWork() || status != 0) {
            buffer.isEmpty(target);
            findTarget();
            findStatus();
            if (checkFloor() || (hasPeople() && memberCount < maxMember)) {
                open();
                findTarget();
                findStatus();
                close();
                flush();
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