import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlElement;

public class SuperUmlAttribute extends MyUmlElement {
    private MyUmlElement belongClass;

    public SuperUmlAttribute(UmlElement umlElement) {
        super(umlElement);
        belongClass = null;
    }

    private UmlAttribute getElement() {
        return (UmlAttribute) getUmlElement();
    }

    public Visibility getVisibility() {
        return getElement().getVisibility();
    }

    public void setBelongClass(MyUmlElement belongClass) {
        this.belongClass = belongClass;
    }

    public MyUmlElement getBelongClass() {
        return belongClass;
    }
}
