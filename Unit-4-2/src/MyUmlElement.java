import com.oocourse.uml2.models.common.ElementType;
import com.oocourse.uml2.models.elements.UmlElement;

public class MyUmlElement {
    private UmlElement umlElement;

    public MyUmlElement(UmlElement umlElement) {
        this.umlElement = umlElement;
    }

    public UmlElement getUmlElement() {
        return umlElement;
    }

    public String getId() {
        return umlElement.getId();
    }

    public ElementType getElementType() {
        return umlElement.getElementType();
    }

    public String getName() {
        return umlElement.getName();
    }

    public String getParentId() {
        return umlElement.getParentId();
    }

}
