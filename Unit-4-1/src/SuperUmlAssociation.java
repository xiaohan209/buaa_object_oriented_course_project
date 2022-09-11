import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlElement;

public class SuperUmlAssociation extends MyUmlElement {
    public SuperUmlAssociation(UmlElement umlElement) {
        super(umlElement);
    }

    public UmlAssociation getElement() {
        return (UmlAssociation) getUmlElement();
    }

    public String getEnd1() {
        return getElement().getEnd1();
    }

    public String getEnd2() {
        return getElement().getEnd2();
    }
}
