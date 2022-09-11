import com.oocourse.uml3.interact.common.AttributeClassInformation;
import com.oocourse.uml3.interact.common.AttributeQueryType;
import com.oocourse.uml3.interact.common.OperationQueryType;
import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlClass;
import com.oocourse.uml3.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SuperUmlClass extends MyUmlElement {
    private boolean dirty;
    private boolean dupDirty;
    private boolean visited;
    private String ancestor;
    private HashMap<String,SuperUmlMethod> method;
    private HashMap<String,SuperUmlAttribute> attributeSelf;
    private HashMap<String,SuperUmlAttribute> attributeAll;
    private HashMap<String,SuperUmlClass> father;
    private HashMap<String,MyUmlElement> assocHashed;
    private HashMap<String,SuperUmlAssEnd> assocSelf;
    private HashMap<String,SuperUmlAssEnd> assocAll;
    private HashMap<String,SuperUmlInterface> realization;
    private HashMap<String,SuperUmlInterface> realSelf;
    private HashSet<String> realDuplicated;
    private HashMap<String,SuperUmlClass> allFather;
    private HashMap<String,SuperUmlInterface> realSon;
    private HashSet<String> realId;
    private ArrayList<SuperUmlInterface> allReal;

    public SuperUmlClass(UmlElement umlElement) {
        super(umlElement);
        ancestor = "";
        method = new HashMap<>();
        attributeSelf = new HashMap<>();
        attributeAll = new HashMap<>();
        father = new HashMap<>();
        assocHashed = new HashMap<>();
        assocSelf = new HashMap<>();
        assocAll = new HashMap<>();
        realization = new HashMap<>();
        realSelf = new HashMap<>();
        realDuplicated = new HashSet<>();
        allFather = new HashMap<>();
        realSon = new HashMap<>();
        allReal = new ArrayList<>();
        realId = new HashSet<>();
        dirty = true;
        dupDirty = true;
        visited = false;
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

    private UmlClass getElement() {
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
        realSelf.put(newInterface.getId(),newInterface);
        realSon.put(newInterface.getId(),newInterface);
        allReal.add(newInterface);
        if (realId.contains(newInterface.getId())) {
            realDuplicated.add(newInterface.getId());
        }
        else {
            realId.add(newInterface.getId());
        }
    }

    public void setFather(SuperUmlClass realFather) {
        father.put(realFather.getId(),realFather);
        allFather.put(realFather.getId(),realFather);
    }

    public void addAss(SuperUmlAssEnd newAss) {
        MyUmlElement assPoint = newAss.getReferenceElement();
        assocHashed.put(assPoint.getId(),assPoint);
        assocAll.put(newAss.getId(),newAss);
        assocSelf.put(newAss.getId(),newAss);
    }

    public HashMap<String, SuperUmlInterface> getRealization() {
        return realization;
    }

    public HashMap<String, SuperUmlInterface> getRealSon() {
        return realSon;
    }

    public HashSet<String> getRealId() {
        return realId;
    }

    public ArrayList<SuperUmlInterface> getAllReal() {
        return allReal;
    }

    public boolean isVisited() {
        return visited;
    }

    public void beginUpdate() {
        updateFather();
    }

    public void updateFather() {
        visited = true;
        for (SuperUmlClass fat:father.values()) {
            if (!fat.isVisited()) {
                fat.updateFather();
                allFather.putAll(fat.getAllFather());
                assocAll.putAll(fat.getAssocAll());
                assocHashed.putAll(fat.getAssocHashed());
                attributeAll.putAll(fat.getAttributeAll());
                ancestor = fat.getAncestor();
                realization.putAll(fat.getRealization());
            }
        }
        if (father.size() == 0) {
            ancestor = getName();
        }
        for (SuperUmlInterface realizedInter:realSelf.values()) {
            if (!realizedInter.isVisited()) {
                realizedInter.update();
                realSon.putAll(realizedInter.getGeneralization());
                realization.putAll(realizedInter.getGeneralization());
            }
        }
        visited = false;
    }

    public void queryDup(HashSet<String> idSet,HashSet<String> dupSet) {
        dupSet.addAll(realDuplicated);
        for (String id:realId) {
            if (idSet.contains(id)) {
                dupSet.add(id);
            }
            else {
                idSet.add(id);
            }
        }
        for (SuperUmlInterface interSelf:realSelf.values()) {
            for (SuperUmlInterface inter:interSelf.getGeneralization().values()) {
                if (idSet.contains(inter.getId())) {
                    dupSet.add(inter.getId());
                }
                else {
                    idSet.add(inter.getId());
                }
            }
            allReal.addAll(interSelf.getAllGeneral());
        }
        for (SuperUmlClass fat:father.values()) {
            fat.queryDup(idSet,dupSet);
        }
    }

    public HashSet<String> getRealDuplicated() {
        return realDuplicated;
    }

    public HashMap<String, SuperUmlClass> getAllFather() {
        return allFather;
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

    public HashSet<AttributeClassInformation> getDuplicated() {
        HashSet<AttributeClassInformation> dupThing = new HashSet<>();
        HashSet<String> allName = new HashSet<>();
        for (SuperUmlAttribute attribute:attributeSelf.values()) {
            String name = attribute.getName();
            if (name == null) {
                continue;
            }
            if (allName.contains(name)) {
                dupThing.add(new AttributeClassInformation(name,getName()));
            }
            else {
                allName.add(name);
            }
        }
        for (SuperUmlAssEnd assEnd:assocSelf.values()) {
            String name = assEnd.getName();
            if (name == null) {
                continue;
            }
            if (allName.contains(assEnd.getName())) {
                dupThing.add(new AttributeClassInformation(name,getName()));
            }
            else {
                allName.add(name);
            }
        }
        return dupThing;
    }

    public boolean circleUpdate(String id) {
        this.visited = true;
        for (SuperUmlClass fat:father.values()) {
            if (fat.getId().equals(id)) {
                this.visited = false;
                return true;
            }
            if (!fat.isVisited()) {
                if (fat.circleUpdate(id)) {
                    this.visited = false;
                    return true;
                }
            }
        }
        this.visited = false;
        return false;
    }

    public boolean circleFather() {
        return circleUpdate(getId());
    }

    public boolean realizeIsDuplicated() {
        HashSet<String> idSet = new HashSet<>();
        HashSet<String> dupSet = new HashSet<>();
        queryDup(idSet,dupSet);
        return dupSet.size() != 0;
    }
}
