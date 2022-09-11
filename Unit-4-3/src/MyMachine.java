import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.elements.UmlElement;

import java.util.HashMap;

public class MyMachine {
    private HashMap<String, UmlElement> idToElement;
    private HashMap<String,MyUmlElement> idToMyElement;
    private HashMap<String,SuperUmlStateMachine> nameToStateMachine;
    private HashMap<String,Boolean> stateMachineIsDuplicated;

    MyMachine(HashMap<String,UmlElement> idToElement,
              HashMap<String,MyUmlElement> idToMyElement) {
        this.idToElement = idToElement;
        this.idToMyElement = idToMyElement;
        nameToStateMachine = new HashMap<>();
        stateMachineIsDuplicated = new HashMap<>();
    }

    public HashMap<String, Boolean> getStateMachineIsDuplicated() {
        return stateMachineIsDuplicated;
    }

    public HashMap<String, SuperUmlStateMachine> getNameToStateMachine() {
        return nameToStateMachine;
    }

    public void gotoStateMachine(SuperUmlStateMachine element) {
        String name = element.getUmlElement().getName();
        if (nameToStateMachine.containsKey(name)) {
            stateMachineIsDuplicated.put(name,true);
        }
        else {
            nameToStateMachine.put(name,element);
            stateMachineIsDuplicated.put(name,false);
        }
    }

    public void gotoRegion(SuperUmlRegion element) {
        SuperUmlStateMachine belongMachine =
                (SuperUmlStateMachine) idToMyElement.get(element.getParentId());
        belongMachine.setRegion(element);
    }

    public void gotoState(SuperUmlState element) {
        SuperUmlRegion belongRegion = (SuperUmlRegion) idToMyElement.get(element.getParentId());
        belongRegion.addState(element);
    }

    public void gotoEvent(SuperUmlEvent element) {
        SuperUmlTransition belongTransition =
                (SuperUmlTransition) idToMyElement.get(element.getParentId());
        belongTransition.addEvent(element);
    }

    public void gotoBehavior(SuperUmlOpaqueBehavior element) {
        SuperUmlTransition belongTransition =
                (SuperUmlTransition) idToMyElement.get(element.getParentId());
        belongTransition.addBehaviors(element);
    }

    public void gotoTransition(SuperUmlTransition element) {
        SuperUmlRegion belongRegion = (SuperUmlRegion) idToMyElement.get(element.getParentId());
        belongRegion.addTransition(element);
        SuperUmlState sourceState = (SuperUmlState) idToMyElement.get(element.getSource());
        SuperUmlState targetState = (SuperUmlState) idToMyElement.get(element.getTarget());
        sourceState.addNext(element,targetState);
    }

    public boolean initOneOut() {
        for (MyUmlElement moveMachine:idToMyElement.values()) {
            if (moveMachine.getElementType() == ElementType.UML_PSEUDOSTATE) {
                if (((SuperUmlState)moveMachine).hasNotOneOut()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean finalHasOut() {
        for (MyUmlElement moveMachine:idToMyElement.values()) {
            if (moveMachine.getElementType() == ElementType.UML_FINAL_STATE) {
                if (((SuperUmlState)moveMachine).hasOut()) {
                    return true;
                }
            }
        }
        return false;
    }

}
