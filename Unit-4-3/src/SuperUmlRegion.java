import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlRegion;

import java.util.HashMap;

public class SuperUmlRegion extends MyUmlElement {
    private HashMap<String,SuperUmlState> states;
    private HashMap<String,SuperUmlTransition> transitions;
    private HashMap<String,SuperUmlState> nameToState;
    private HashMap<String,Boolean> stateIsDuplicated;

    public SuperUmlRegion(UmlElement umlElement) {
        super(umlElement);
        states = new HashMap<>();
        transitions = new HashMap<>();
        nameToState = new HashMap<>();
        stateIsDuplicated = new HashMap<>();
    }

    private UmlRegion getElement() {
        return (UmlRegion) getUmlElement();
    }

    public void addState(SuperUmlState state) {
        String name = state.getName();
        states.put(state.getId(),state);
        if (nameToState.containsKey(name)) {
            stateIsDuplicated.put(name,true);
        }
        else {
            nameToState.put(name,state);
            stateIsDuplicated.put(name,false);
        }
    }

    public void addTransition(SuperUmlTransition transition) {
        transitions.put(transition.getId(),transition);
    }

    public int getStateCount() {
        return states.size();
    }

    public int getTransitionCount() {
        return transitions.size();
    }

    public boolean has(String stateName) {
        return nameToState.containsKey(stateName);
    }

    public boolean stateDuplicated(String stateName) {
        return stateIsDuplicated.get(stateName);
    }

    public int getNextStatesCount(String stateName) {
        return nameToState.get(stateName).queryNextAllCount();
    }

}
