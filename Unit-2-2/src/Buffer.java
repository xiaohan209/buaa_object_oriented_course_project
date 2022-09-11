import com.oocourse.elevator2.PersonRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class Buffer {
    private ArrayList<PersonRequest> queue;
    private HashMap<Integer,Integer> waitingNum;
    private boolean work;

    Buffer() {
        queue = new ArrayList<PersonRequest>();
        waitingNum = new HashMap<Integer, Integer>();
        work = true;
    }

    public synchronized void isEmpty(int target) {
        if (queue.isEmpty() && target == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addRequest(PersonRequest newRequest) {
        if (newRequest == null) {
            stop();
            notifyAll();
            return;
        }
        queue.add(newRequest);
        Integer floor = newRequest.getFromFloor();
        if (waitingNum.containsKey(floor)) {
            waitingNum.put(floor,waitingNum.get(floor) + 1);
        }
        else {
            waitingNum.put(floor, 1);
        }
        notifyAll();
    }

    public synchronized PersonRequest into(int floor,int status) {
        int i;
        for (i = 0; i < queue.size(); i++) {
            PersonRequest req = queue.get(i);
            if (req.getToFloor() > floor && status == 3 && req.getFromFloor() == floor) {
                break;
            }
            else if (req.getToFloor() < floor && status == 4 && req.getFromFloor() == floor) {
                break;
            }
        }
        if (i == queue.size()) {
            return null;
        }
        if (waitingNum.get(floor) == 1) {
            waitingNum.remove(floor);
        }
        else {
            waitingNum.put(floor,waitingNum.get(floor) - 1);
        }
        return queue.remove(i);
    }

    public synchronized void stop() {
        this.work = false;
    }

    public synchronized boolean isWork() {
        return work;
    }

    public synchronized PersonRequest findTarget(int floor) {
        if (queue.size() == 0) {
            return null;
        }
        else {
            Integer max = Integer.MIN_VALUE;
            Integer min = Integer.MAX_VALUE;
            for (HashMap.Entry<Integer, Integer> entry : waitingNum.entrySet()) {
                if (entry.getValue() > 0) {
                    if (entry.getKey() > max) {
                        max = entry.getKey();
                    }
                    if (entry.getKey() < min) {
                        min = entry.getKey();
                    }
                }
            }
            Integer target;
            if (Math.abs(max - floor) > Math.abs(floor - min)) {
                target = max;
            }
            else {
                target = min;
            }
            for (int i = 0; i < queue.size(); i++) {
                if (target.equals(queue.get(i).getFromFloor())) {
                    return queue.remove(i);
                }
            }
            return queue.remove(0);
        }
    }
}
