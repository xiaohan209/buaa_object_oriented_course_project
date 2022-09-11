import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlGeneralization;

public class SuperUmlGeneral extends MyUmlElement {
    public SuperUmlGeneral(UmlElement umlElement) {
        super(umlElement);
    }

    public UmlGeneralization getElement() {
        return (UmlGeneralization) getUmlElement();
    }

    public String getSource() {
        return getElement().getSource();
    }

    public String getTarget() {
        return getElement().getTarget();
    }
}
