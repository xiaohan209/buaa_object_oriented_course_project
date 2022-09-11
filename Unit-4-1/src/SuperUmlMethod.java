import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlOperation;

import java.util.HashMap;

public class SuperUmlMethod extends MyUmlElement {
    private HashMap<String,SuperUmlParameter> parameter;

    public SuperUmlMethod(UmlElement umlElement) {
        super(umlElement);
        parameter = new HashMap<>();
    }

    public boolean queryOperation(OperationQueryType[] queryTypes) {
        boolean flag = true;
        for (OperationQueryType type:queryTypes) {
            boolean typeFlag;
            if (type == OperationQueryType.ALL) {
                continue;
            }
            typeFlag = false;
            for (SuperUmlParameter para:parameter.values()) {
                typeFlag = typeFlag || para.isType(type);
            }
            switch (type) {
                case NON_RETURN:
                case NON_PARAM:
                    typeFlag = !typeFlag;
                    break;
                default:
                    break;
            }
            flag = flag && typeFlag;
        }
        return flag;
    }

    public void addParameter(SuperUmlParameter newParameter) {
        parameter.put(newParameter.getId(),newParameter);
    }

    public UmlOperation getElement() {
        return (UmlOperation) getUmlElement();
    }

    public Visibility getVisibility() {
        return getElement().getVisibility();
    }
}
