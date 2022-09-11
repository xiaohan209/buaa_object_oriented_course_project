import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlGeneralization;

public class SuperUmlGeneral extends MyUmlElement {
    public SuperUmlGeneral(UmlElement umlElement) {
        super(umlElement);
    }

    private UmlGeneralization getElement() {
        return (UmlGeneralization) getUmlElement();
    }

    public String getSource() {
        return getElement().getSource();
    }

    public String getTarget() {
        return getElement().getTarget();
    }
}
