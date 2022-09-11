import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlElement;

public class SuperUmlAssEnd extends MyUmlElement {
    private MyUmlElement referenceElement;

    public SuperUmlAssEnd(UmlElement umlElement) {
        super(umlElement);
    }

    private UmlAssociationEnd getElement() {
        return (UmlAssociationEnd) getUmlElement();
    }

    public String getReference() {
        return getElement().getReference();
    }

    public MyUmlElement getReferenceElement() {
        return referenceElement;
    }

    public void setReferenceElement(MyUmlElement referenceElement) {
        this.referenceElement = referenceElement;
    }
}
