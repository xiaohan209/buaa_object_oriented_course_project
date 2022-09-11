import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;

import java.util.HashMap;

public class SuperUmlInterface extends MyUmlElement {
    private boolean dirty;
    private HashMap<String,SuperUmlMethod> method;
    private HashMap<String,SuperUmlAttribute> attributeSelf;
    private HashMap<String,SuperUmlAttribute> attributeAll;
    private HashMap<String,SuperUmlInterface> generalization;
    private HashMap<String,MyUmlElement> assocHashed;
    private HashMap<String,SuperUmlAssEnd> assocAll;

    public SuperUmlInterface(UmlElement umlElement) {
        super(umlElement);
        method = new HashMap<>();
        attributeSelf = new HashMap<>();
        attributeAll = new HashMap<>();
        generalization = new HashMap<>();
        assocHashed = new HashMap<>();
        assocAll = new HashMap<>();
        dirty = true;
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
    }

    public void addAss(SuperUmlAssEnd newAss) {
        MyUmlElement assPoint = newAss.getReferenceElement();
        assocHashed.put(assPoint.getId(),assPoint);
        assocAll.put(newAss.getId(),newAss);
    }

    public void update() {
        if (!dirty) {
            return;
        }
        HashMap<String,SuperUmlInterface> tempGeneralMap = new HashMap<>();
        for (SuperUmlInterface generalInterface: generalization.values()) {
            generalInterface.update();
            assocAll.putAll(generalInterface.getAssocAll());
            assocHashed.putAll(generalInterface.getAssocHashed());
            attributeAll.putAll(generalInterface.getAttributeAll());
            tempGeneralMap.putAll(generalInterface.getGeneralization());
        }
        generalization.putAll(tempGeneralMap);
        dirty = false;
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
}
