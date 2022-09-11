import com.oocourse.uml3.interact.common.OperationQueryType;
import com.oocourse.uml3.models.common.Direction;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlParameter;

public class SuperUmlParameter extends MyUmlElement {
    public SuperUmlParameter(UmlElement umlElement) {
        super(umlElement);
    }

    private UmlParameter getElement() {
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

    public Direction getDirection() {
        return getElement().getDirection();
    }

}
