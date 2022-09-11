import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.models.common.ElementType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperUmlClass extends MyUmlElement {
    private boolean dirty;
    private String ancestor;
    private HashMap<String,SuperUmlMethod> method;
    private HashMap<String,SuperUmlAttribute> attributeSelf;
    private HashMap<String,SuperUmlAttribute> attributeAll;
    private SuperUmlClass father;
    private HashMap<String,MyUmlElement> assocHashed;
    private HashMap<String,SuperUmlAssEnd> assocAll;
    private HashMap<String,SuperUmlInterface> realization;

    public SuperUmlClass(UmlElement umlElement) {
        super(umlElement);
        ancestor = "";
        method = new HashMap<>();
        attributeSelf = new HashMap<>();
        attributeAll = new HashMap<>();
        father = null;
        assocHashed = new HashMap<>();
        assocAll = new HashMap<>();
        realization = new HashMap<>();
        dirty = true;
    }

    public int queryClassOperation(OperationQueryType[] queryTypes) {
        int sum = 0;
        for (SuperUmlMethod selfMethod:method.values()) {
            if (selfMethod.queryOperation(queryTypes)) {
                sum++;
            }
        }
        return sum;
    }

    public UmlClass getElement() {
        return (UmlClass) getUmlElement();
    }

    public void addAttribute(SuperUmlAttribute newAttribute) {
        attributeSelf.put(newAttribute.getId(),newAttribute);
        attributeAll.put(newAttribute.getId(),newAttribute);
    }

    public void addOperation(SuperUmlMethod newMethod) {
        method.put(newMethod.getId(),newMethod);
    }

    public void realizeInterface(SuperUmlInterface newInterface) {
        realization.put(newInterface.getId(),newInterface);
    }

    public void setFather(SuperUmlClass realFather) {
        father = realFather;
    }

    public void addAss(SuperUmlAssEnd newAss) {
        MyUmlElement assPoint = newAss.getReferenceElement();
        assocHashed.put(assPoint.getId(),assPoint);
        assocAll.put(newAss.getId(),newAss);
    }

    public HashMap<String, SuperUmlInterface> getRealization() {
        return realization;
    }

    public void update() {
        if (!dirty) {
            return;
        }
        HashMap<String,SuperUmlInterface> tempRealizeMap = new HashMap<>();
        if (father != null) {
            father.update();
            assocAll.putAll(father.getAssocAll());
            assocHashed.putAll(father.getAssocHashed());
            attributeAll.putAll(father.getAttributeAll());
            ancestor = father.getAncestor();
            tempRealizeMap.putAll(father.getRealization());
        }
        else {
            ancestor = getName();
        }
        for (SuperUmlInterface realizedInter:realization.values()) {
            realizedInter.update();
            tempRealizeMap.putAll(realizedInter.getGeneralization());
        }
        realization.putAll(tempRealizeMap);
        dirty = false;
    }

    private HashMap<String, SuperUmlAssEnd> getAssocAll() {
        return assocAll;
    }

    private HashMap<String,MyUmlElement> getAssocHashed() {
        return assocHashed;
    }

    public HashMap<String, SuperUmlAttribute> getAttributeAll() {
        return attributeAll;
    }

    public int getAttributeCount(AttributeQueryType queryType) {
        switch (queryType) {
            case ALL:
                return attributeAll.size();
            case SELF_ONLY:
                return attributeSelf.size();
            default:
                return 0;
        }
    }

    public int getAssCount() {
        return assocAll.size();
    }

    public List<String> getAssClassList() {
        ArrayList<String> classList = new ArrayList<>();
        for (MyUmlElement assClass:assocHashed.values()) {
            if (assClass.getElementType() == ElementType.UML_CLASS) {
                classList.add(assClass.getName());
            }
        }
        return classList;
    }

    public Map<Visibility, Integer> getOperationVisibility(String operationName) {
        HashMap<Visibility,Integer> visMap = new HashMap<>();
        for (SuperUmlMethod operation:method.values()) {
            if (operation.getName().equals(operationName)) {
                Visibility visibility = operation.getVisibility();
                if (!visMap.containsKey(visibility)) {
                    visMap.put(visibility,1);
                }
                else {
                    visMap.put(visibility,visMap.get(visibility) + 1);
                }
            }
        }
        return visMap;
    }

    public String getAncestor() {
        return ancestor;
    }

    public List<String> getInterfaceList() {
        ArrayList<String> interfaceList = new ArrayList<>();
        for (SuperUmlInterface inter:realization.values()) {
            interfaceList.add(inter.getName());
        }
        return interfaceList;
    }

    public List<AttributeClassInformation> getNotHidden() {
        ArrayList<AttributeClassInformation> notHidden = new ArrayList<>();
        for (SuperUmlAttribute traAttribute:attributeAll.values()) {
            if (traAttribute.getVisibility() != Visibility.PRIVATE) {
                notHidden.add(new AttributeClassInformation(traAttribute.getName(),
                        traAttribute.getBelongClass().getName()));
            }
        }
        return notHidden;
    }
}
