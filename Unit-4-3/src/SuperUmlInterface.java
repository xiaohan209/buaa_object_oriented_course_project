import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SuperUmlInterface extends MyUmlElement {
    private boolean dirty;
    private boolean dupDirty;
    private boolean visited;
    private HashMap<String,SuperUmlMethod> method;
    private HashMap<String,SuperUmlAttribute> attributeSelf;
    private HashMap<String,SuperUmlAttribute> attributeAll;
    private HashMap<String,SuperUmlInterface> generalization;
    private HashMap<String,SuperUmlInterface> generalSelf;
    private HashMap<String,MyUmlElement> assocHashed;
    private HashMap<String,SuperUmlAssEnd> assocAll;
    private HashSet<String> generalDuplicated;
    private ArrayList<SuperUmlInterface> allGeneral;
    private HashSet<String> generalId;

    public SuperUmlInterface(UmlElement umlElement) {
        super(umlElement);
        method = new HashMap<>();
        attributeSelf = new HashMap<>();
        attributeAll = new HashMap<>();
        generalization = new HashMap<>();
        assocHashed = new HashMap<>();
        assocAll = new HashMap<>();
        generalDuplicated = new HashSet<>();
        generalSelf = new HashMap<>();
        allGeneral = new ArrayList<>();
        generalId = new HashSet<>();
        dirty = true;
        dupDirty = true;
        visited = false;
    }

    private UmlInterface getElement() {
        return (UmlInterface) getUmlElement();
    }

    public void addAttribute(SuperUmlAttribute newAttribute) {
        attributeSelf.put(newAttribute.getId(),newAttribute);
        attributeAll.put(newAttribute.getId(),newAttribute);
    }

    public void addOperation(SuperUmlMethod newMethod) {
        method.put(newMethod.getId(),newMethod);
    }

    public void addGeneral(SuperUmlInterface realFather) {
        generalization.put(realFather.getId(),realFather);
        generalSelf.put(realFather.getId(),realFather);
        if (generalId.contains(realFather.getId())) {
            generalDuplicated.add(realFather.getId());
        }
        else {
            generalId.add(realFather.getId());
        }
        allGeneral.add(realFather);
    }

    public void addAss(SuperUmlAssEnd newAss) {
        MyUmlElement assPoint = newAss.getReferenceElement();
        assocHashed.put(assPoint.getId(),assPoint);
        assocAll.put(newAss.getId(),newAss);
    }

    public void beginUpdate() {
        update();
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void update() {
        if (!dirty) {
            return;
        }
        visited = true;
        for (SuperUmlInterface generalInterface: generalSelf.values()) {
            if (!generalInterface.isVisited()) {
                generalInterface.update();
                assocAll.putAll(generalInterface.getAssocAll());
                assocHashed.putAll(generalInterface.getAssocHashed());
                attributeAll.putAll(generalInterface.getAttributeAll());
                generalization.putAll(generalInterface.getGeneralization());
            }
        }
        dirty = false;
        visited = false;
    }

    public void queryDup(HashSet<String> idSet,HashSet<String> dupSet) {
        ArrayList<SuperUmlInterface> bfsQueue = new ArrayList<>();
        int head = 0;
        bfsQueue.add(this);
        int top = 1;
        while (head < top) {
            SuperUmlInterface first = bfsQueue.get(head);
            head++;
            dupSet.addAll(first.getGeneralDuplicated());
            for (String id:first.getGeneralId()) {
                if (idSet.contains(id)) {
                    dupSet.add(id);
                }
                else {
                    idSet.add(id);
                }
            }
            for (SuperUmlInterface inter: first.getGeneralSelf().values()) {
                if (!bfsQueue.contains(inter)) {
                    bfsQueue.add(inter);
                    top++;
                }
            }
        }
    }

    public HashSet<String> getGeneralId() {
        return generalId;
    }

    public ArrayList<SuperUmlInterface> getAllGeneral() {
        return allGeneral;
    }

    public HashSet<String> getGeneralDuplicated() {
        return generalDuplicated;
    }

    public HashMap<String, SuperUmlAssEnd> getAssocAll() {
        return assocAll;
    }

    public HashMap<String, MyUmlElement> getAssocHashed() {
        return assocHashed;
    }

    public HashMap<String, SuperUmlAttribute> getAttributeAll() {
        return attributeAll;
    }

    public HashMap<String, SuperUmlInterface> getGeneralization() {
        return generalization;
    }

    public HashMap<String, SuperUmlInterface> getGeneralSelf() {
        return generalSelf;
    }

    public boolean allAttributePublic() {
        for (SuperUmlAttribute attribute:attributeSelf.values()) {
            if (attribute.getVisibility() != Visibility.PUBLIC) {
                return false;
            }
        }
        return true;
    }

    public boolean circleUpdate(String id) {
        ArrayList<SuperUmlInterface> bfsQueue = new ArrayList<>();
        int head = 0;
        bfsQueue.add(this);
        int top = 1;
        while (head < top) {
            SuperUmlInterface first = bfsQueue.get(head);
            head++;
            for (String thisId: first.getGeneralSelf().keySet()) {
                if (thisId.equals(id)) {
                    return true;
                }
            }
            for (SuperUmlInterface inter: first.getGeneralSelf().values()) {
                if (!bfsQueue.contains(inter)) {
                    bfsQueue.add(inter);
                    top++;
                }
            }
        }
        return false;
    }

    public boolean circleGeneral() {
        return circleUpdate(getId());
    }

    public boolean generalIsDuplicated() {
        HashSet<String> idSet = new HashSet<>();
        HashSet<String> dupSet = new HashSet<>();
        queryDup(idSet,dupSet);
        return dupSet.size() != 0;
    }
}
