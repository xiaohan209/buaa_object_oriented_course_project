import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlTransition;

import java.util.HashMap;

public class SuperUmlTransition extends MyUmlElement {
    private HashMap<String,SuperUmlEvent> events;
    private HashMap<String,SuperUmlOpaqueBehavior> behaviors;

    public SuperUmlTransition(UmlElement umlElement) {
        super(umlElement);
        events = new HashMap<>();
        behaviors = new HashMap<>();
    }

    private UmlTransition getElement() {
        return (UmlTransition) getUmlElement();
    }

    public String getSource() {
        return getElement().getSource();
    }

    public String getTarget() {
        return getElement().getTarget();
    }

    public void addEvent(SuperUmlEvent event) {
        events.put(event.getId(),event);
    }

    public void addBehaviors(SuperUmlOpaqueBehavior behavior) {
        behaviors.put(behavior.getId(),behavior);
    }

}
