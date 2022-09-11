import com.oocourse.uml3.models.elements.UmlElement;

public class SuperUmlStateMachine extends MyUmlElement {
    private SuperUmlRegion region;

    public SuperUmlStateMachine(UmlElement umlElement) {
        super(umlElement);
        region = null;
    }

    public void setRegion(SuperUmlRegion region) {
        this.region = region;
    }

    public int getStateCount() {
        return region.getStateCount();
    }

    public int getTransitionCount() {
        return region.getTransitionCount();
    }

    public boolean has(String stateName) {
        return region.has(stateName);
    }

    public boolean stateDuplicated(String stateName) {
        return region.stateDuplicated(stateName);
    }

    public int getNextStatesCount(String stateName) {
        return region.getNextStatesCount(stateName);
    }

}
