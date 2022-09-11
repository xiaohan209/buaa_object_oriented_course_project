import com.oocourse.TimableOutput;
import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;

public class Elevator implements Runnable {
    private String elevatorId;
    private int maxMember;
    private int timeOpen;
    private int timeClose;
    private int timeFloor;
    private ArrayList<Integer> floors;
    private int status;
    private int floor;
    private int target;
    private ArrayList<PartRequest> queue;
    private ArrayList<PartRequest> waiting;
    private int memberCount;
    private Buffer buffer;

    public Elevator(String elevatorId, ArrayList<Integer> floors,int maxMember,
                    int timeOpen, int timeClose, int timeFloor, Buffer buffer) {
        this.elevatorId = elevatorId;
        this.maxMember = maxMember;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.timeFloor = timeFloor;
        this.floors = floors;
        this.buffer = buffer;
        this.status = 0;
        this.floor = 1;
        this.target = 0;
        this.memberCount = 0;
        this.queue = new ArrayList<PartRequest>();
        this.waiting = new ArrayList<PartRequest>();
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

    public void addToQueue(PartRequest request) {
        queue.add(request);
        TimableOutput.println("IN-" + request.getPersonId() + "-" + floor + "-" + elevatorId);
        memberCount++;
    }

    public void remove(int i) {
        PartRequest outRequest = queue.remove(i);
        TimableOutput.println("OUT-" + outRequest.getPersonId()
                + "-" + floor + "-" + elevatorId);
        memberCount--;
        if (outRequest.isPart()) {
            buffer.next(outRequest.getPersonId());
        }
    }

    public void open() {
        TimableOutput.println("OPEN-" + floor + "-" + elevatorId);
        for (int i = 0; i < queue.size(); i++) {
            if (floor == queue.get(i).getToFloor()) {
                remove(i);
                i--;
            }
        }
        try {
            Thread.sleep(timeOpen);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void findTarget() {
        if (queue.isEmpty() && waiting.isEmpty()) {
            //queue->waiting
            PartRequest req = buffer.findTarget(floor,floors);
            if (req == null) {
                target = 0;
                return;
            }
            waiting.add(req);
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
    }

    public void addToWaiting() {
        int cnt = memberCount;
        while (cnt < maxMember) {
            PartRequest intoRequest = buffer.into(floor,status,floors);
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
                    PartRequest request = waiting.remove(i);
                    addToQueue(request);
                    i--;
                }
                else if (tryRequest.getToFloor() < floor && status == 4) {
                    PartRequest request = waiting.remove(i);
                    addToQueue(request);
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
            PartRequest tryRequest = waiting.get(i);
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
        buffer.addRequest(waiting);
        waiting.clear();
    }

    public void run() {
        while (buffer.isWork(floors) || status != 0) {
            buffer.isEmpty(target,floors);
            findTarget();
            findStatus();
            addToWaiting();
            if ((checkFloor() || (hasPeople() && memberCount < maxMember))
                    && floors.contains(floor)) {
                open();
                findTarget();
                findStatus();
                close();
                flush();
                findTarget();
                findStatus();
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



