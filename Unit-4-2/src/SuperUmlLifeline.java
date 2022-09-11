import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlLifeline;

public class SuperUmlLifeline extends SuperUmlLine {
    private SuperUmlAttribute representation;

    public SuperUmlLifeline(UmlElement umlElement) {
        super(umlElement);
        representation = null;
    }

    public void setRepresentation(SuperUmlAttribute representation) {
        this.representation = representation;
    }

    private UmlLifeline getElement() {
        return (UmlLifeline) getUmlElement();
    }

    public String getRepresent() {
        return getElement().getRepresent();
    }
}
