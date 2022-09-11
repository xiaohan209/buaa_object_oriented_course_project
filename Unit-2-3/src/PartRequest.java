import com.oocourse.elevator3.PersonRequest;

public class PartRequest extends PersonRequest {
    private boolean part;
    private int priority;

    public boolean isPart() {
        return part;
    }

    public int getPriority() {
        return priority;
    }

    public PartRequest(PersonRequest personRequest,boolean part,int priority) {
        super(personRequest.getFromFloor(),personRequest.getToFloor(),personRequest.getPersonId());
        this.part = part;
        this.priority = priority;
    }

    public void nextOne() {
        this.priority--;
    }
}
