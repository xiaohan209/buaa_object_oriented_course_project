import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.models.common.Direction;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlParameter;

public class SuperUmlParameter extends MyUmlElement {
    public SuperUmlParameter(UmlElement umlElement) {
        super(umlElement);
    }

    public UmlParameter getElement() {
        return (UmlParameter) getUmlElement();
    }

    public boolean isType(OperationQueryType type) {
        switch (type) {
            case PARAM:
            case NON_PARAM:
                return getElement().getDirection() != Direction.RETURN;
            case RETURN:
            case NON_RETURN:
                return getElement().getDirection() == Direction.RETURN;
            default:
                return true;
        }
    }

}
