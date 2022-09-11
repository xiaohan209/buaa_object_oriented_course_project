import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.elements.UmlElement;

import java.util.HashMap;

public class MyCollaboration {
    private HashMap<String, UmlElement> idToElement;
    private HashMap<String, MyUmlElement> idToMyElement;
    private HashMap<String, SuperUmlInteraction> nameToInteraction;
    private HashMap<String, Boolean> interactionIsDuplicated;

    MyCollaboration(HashMap<String, UmlElement> idToElement,
              HashMap<String,MyUmlElement> idToMyElement) {
        this.idToElement = idToElement;
        this.idToMyElement = idToMyElement;
        nameToInteraction = new HashMap<>();
        interactionIsDuplicated = new HashMap<>();
    }

    public HashMap<String, Boolean> getInteractionIsDuplicated() {
        return interactionIsDuplicated;
    }

    public HashMap<String, SuperUmlInteraction> getNameToInteraction() {
        return nameToInteraction;
    }

    public void gotoInteraction(SuperUmlInteraction element) {
        String name = element.getUmlElement().getName();
        if (nameToInteraction.containsKey(name)) {
            interactionIsDuplicated.put(name,true);
        }
        else {
            nameToInteraction.put(name,element);
            interactionIsDuplicated.put(name,false);
        }
    }

    public void gotoMessage(SuperUmlMessage moveElement) {
        SuperUmlInteraction belongInteraction =
                (SuperUmlInteraction) idToMyElement.get(moveElement.getParentId());
        belongInteraction.addMessage(moveElement);
        SuperUmlLine sourceLine = (SuperUmlLine) idToMyElement.get(moveElement.getSource());
        sourceLine.addOut(moveElement);
        SuperUmlLine targetLine = (SuperUmlLine) idToMyElement.get(moveElement.getTarget());
        targetLine.addIn(moveElement);
    }

    public void gotoLifeline(SuperUmlLifeline moveElement) {
        SuperUmlInteraction belongInteraction =
                (SuperUmlInteraction) idToMyElement.get(moveElement.getParentId());
        belongInteraction.addLifeline(moveElement);
        if (idToMyElement.containsKey(moveElement.getRepresent())) {
            MyUmlElement representAttribute = idToMyElement.get(moveElement.getRepresent());
            if (representAttribute.getElementType() == ElementType.UML_ATTRIBUTE) {
                moveElement.setRepresentation((SuperUmlAttribute) representAttribute);
            }
        }
    }

    public void gotoEndPoint(SuperUmlEndPoint moveElement) {
        SuperUmlInteraction belongInteraction =
                (SuperUmlInteraction) idToMyElement.get(moveElement.getParentId());
        belongInteraction.addEndPoint(moveElement);
    }
}
