import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlMessage;

public class SuperUmlMessage extends MyUmlElement {
    public SuperUmlMessage(UmlElement umlElement) {
        super(umlElement);
    }

    private UmlMessage getElement() {
        return (UmlMessage) getUmlElement();
    }

    public String getSource() {
        return getElement().getSource();
    }

    public String getTarget() {
        return getElement().getTarget();
    }
}
