import com.oocourse.uml3.models.elements.UmlElement;

import java.util.HashMap;

public class SuperUmlState extends MyUmlElement {
    private HashMap<String,SuperUmlTransition> nextTransitions;
    private HashMap<String,SuperUmlState> selfNextState;
    private HashMap<String,SuperUmlState> allNextState;
    private boolean dirty;
    private boolean visited;

    public SuperUmlState(UmlElement umlElement) {
        super(umlElement);
        nextTransitions = new HashMap<>();
        selfNextState = new HashMap<>();
        allNextState = new HashMap<>();
        dirty = true;
        visited = false;
    }

    public void addNext(SuperUmlTransition nextTrans,SuperUmlState nextState) {
        nextTransitions.put(nextTrans.getId(),nextTrans);
        selfNextState.put(nextState.getId(),nextState);
        allNextState.put(nextState.getId(),nextState);
    }

    public void update() {
        if (!dirty) {
            return;
        }
        visited = true;
        for (SuperUmlState nextState:selfNextState.values()) {
            if (nextState.isVisited()) {
                continue;
            }
            nextState.update();
            allNextState.putAll(nextState.getAllNextState());
        }
        visited = false;
    }

    public void beginUpdate() {
        update();
        dirty = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public HashMap<String, SuperUmlState> getAllNextState() {
        return allNextState;
    }

    public int queryNextAllCount() {
        return allNextState.size();
    }

    public boolean hasNotOneOut() {
        return nextTransitions.size() > 1;
    }

    public boolean hasOut() {
        return nextTransitions.size() > 0;
    }
}
