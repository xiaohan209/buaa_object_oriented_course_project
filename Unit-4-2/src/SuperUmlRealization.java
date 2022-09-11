import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;

public class SuperUmlRealization extends MyUmlElement {
    public SuperUmlRealization(UmlElement umlElement) {
        super(umlElement);
    }

    private UmlInterfaceRealization getElement() {
        return (UmlInterfaceRealization) getUmlElement();
    }

    public String getSource() {
        return getElement().getSource();
    }

    public String getTarget() {
        return getElement().getTarget();
    }

}
