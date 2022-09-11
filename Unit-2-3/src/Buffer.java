import java.util.ArrayList;

public class Buffer {
    private ArrayList<PartRequest> queue;
    private boolean work;

    Buffer() {
        queue = new ArrayList<PartRequest>();
        work = true;
    }

    public synchronized void isEmpty(int target,ArrayList<Integer> floors) {
        boolean rest = false;
        for (int i = 0; i < queue.size(); i++) {
            if (floors.contains(queue.get(i).getFromFloor()) &&
                    queue.get(i).getPriority() == 0 &&
                    floors.contains(queue.get(i).getToFloor())) {
                rest = true;
                break;
            }
        }
        if (!rest && (target == 0)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addRequest(ArrayList<PartRequest> newRequest) {
        if (newRequest == null) {
            stop();
            notifyAll();
            return;
        }
        queue.addAll(newRequest);
        notifyAll();
    }

    public synchronized PartRequest into(int floor,int status,ArrayList<Integer> floors) {
        int index = -1;
        if (!floors.contains(floor)) {
            return null;
        }
        for (int i = 0; i < queue.size(); i++) {
            PartRequest req = queue.get(i);
            if (req.getFromFloor() == floor && req.getPriority() == 0
                    && floors.contains(req.getToFloor())) {
                if (req.getToFloor() > floor && status == 3) {
                    index = i;
                    break;
                }
                else if (req.getToFloor() > floor && status == 4) {
                    index = i;
                    break;
                }
            }

        }
        if (index == -1) {
            return null;
        }
        return queue.remove(index);
    }

    public synchronized PartRequest findTarget(int floor,ArrayList<Integer> floors) {
        for (int i = 0; i < queue.size(); i++) {
            PartRequest req = queue.get(i);
            if (req.getPriority() == 0 && floors.contains(req.getFromFloor())
                    && floors.contains(req.getToFloor())) {
                return queue.remove(i);
            }
        }
        return null;
    }

    public synchronized void next(int personId) {
        for (int i = 0; i < queue.size(); i++) {
            PartRequest req = queue.get(i);
            if (req.getPersonId() == personId) {
                req.nextOne();
            }
        }
        notifyAll();
    }

    public synchronized void stop() {
        work = false;
    }

    public synchronized boolean isWork(ArrayList<Integer> floors) {
        for (int i = 0; i < queue.size(); i++) {
            if (floors.contains(queue.get(i).getFromFloor()) &&
                    floors.contains(queue.get(i).getToFloor())) {
                return true;
            }
        }
        return work;
    }
}
