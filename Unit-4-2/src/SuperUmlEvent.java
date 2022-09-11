import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlEvent;

public class SuperUmlEvent extends MyUmlElement {
    public SuperUmlEvent(UmlElement umlElement) {
        super(umlElement);
    }

    public UmlEvent getElement() {
        return (UmlEvent) getUmlElement();
    }
}
