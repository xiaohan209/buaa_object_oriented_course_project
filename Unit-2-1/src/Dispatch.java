import com.oocourse.elevator1.PersonRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class Dispatch {
    private ArrayList<PersonRequest> reque;
    private HashMap<Integer,Integer> waitingNum;

    private boolean work;

    Dispatch() {
        reque = new ArrayList<PersonRequest>();
        waitingNum = new HashMap<Integer, Integer>();
        work = true;
    }

    public synchronized boolean isEmpty() {
        if (reque.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return reque.isEmpty();
    }

    public synchronized void addRequest(PersonRequest newRequest) {
        if (newRequest == null) {
            stop();
            notifyAll();
            return;
        }
        reque.add(newRequest);
        Integer floor = newRequest.getFromFloor();
        if (waitingNum.containsKey(floor)) {
            waitingNum.put(floor,waitingNum.get(floor) + 1);
        }
        else {
            waitingNum.put(floor, 1);
        }
        notify();
    }

    public synchronized PersonRequest into(int i) {
        Integer floor = reque.get(i).getFromFloor();
        waitingNum.get(floor);
        if (waitingNum.get(floor) == 1) {
            waitingNum.remove(floor);
        }
        else {
            waitingNum.put(floor,waitingNum.get(floor) - 1);
        }
        return reque.remove(i);
    }

    public synchronized boolean hasPeople(int floor,int status) {
        for (int i = 0; i < reque.size(); i++) {
            PersonRequest tryRequest = reque.get(i);
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

    public synchronized void stop() {
        this.work = false;
    }

    public synchronized ArrayList<PersonRequest> getReque() {
        return reque;
    }

    public synchronized boolean isWork() {
        return work;
    }

    public synchronized int changeTarget() {
        if (reque.size() == 0) {
            return 0;
        }
        else {
            return reque.get(0).getToFloor();
        }
    }

    public synchronized int findTarget() {
        if (reque.size() == 0) {
            return 0;
        }
        else {
            return reque.get(0).getFromFloor();
        }
    }
}
