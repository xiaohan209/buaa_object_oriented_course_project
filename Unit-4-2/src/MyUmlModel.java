import com.oocourse.uml2.models.elements.UmlElement;
import java.util.HashMap;

public class MyUmlModel {
    private int classCount;
    private int interfaceCount;
    private HashMap<String, UmlElement> idToElement;
    private HashMap<String,MyUmlElement> idToMyElement;
    private HashMap<String,SuperUmlClass> nameToClass;
    private HashMap<String,Boolean> classIsDuplicated;

    public MyUmlModel(HashMap<String,UmlElement> idToElement,
                      HashMap<String,MyUmlElement> idToMyElement) {
        classCount = 0;
        interfaceCount = 0;
        this.idToElement = idToElement;
        this.idToMyElement = idToMyElement;
        nameToClass = new HashMap<>();
        classIsDuplicated = new HashMap<>();
    }

    public HashMap<String, SuperUmlClass> getNameToClass() {
        return nameToClass;
    }

    public HashMap<String, Boolean> getClassIsDuplicated() {
        return classIsDuplicated;
    }

    public void gotoUmlClass(SuperUmlClass element) {
        classCount++;
        String name = element.getUmlElement().getName();
        if (nameToClass.containsKey(name)) {
            classIsDuplicated.put(name,true);
        }
        else {
            nameToClass.put(name,element);
            classIsDuplicated.put(name,false);
        }
    }

    public void gotoUmlInterface(SuperUmlInterface element) {
        interfaceCount++;
    }

    public void gotoUmlAttribute(SuperUmlAttribute element) {
        if (!idToMyElement.containsKey(element.getParentId())) {
            return;
        }
        MyUmlElement belongClass = idToMyElement.get(element.getParentId());
        switch (belongClass.getElementType()) {
            case UML_CLASS:
                ((SuperUmlClass) belongClass).addAttribute(element);
                element.setBelongClass(belongClass);
                break;
            case UML_INTERFACE:
                ((SuperUmlInterface) belongClass).addAttribute(element);
                element.setBelongClass(belongClass);
                break;
            default:
                break;
        }
    }

    public void gotoUmlOperation(SuperUmlMethod element) {
        MyUmlElement belongClass = idToMyElement.get(element.getParentId());
        switch (belongClass.getElementType()) {
            case UML_CLASS:
                ((SuperUmlClass) belongClass).addOperation(element);
                break;
            case UML_INTERFACE:
                ((SuperUmlInterface) belongClass).addOperation(element);
                break;
            default:
                break;
        }
    }

    public void gotoUmlRealization(SuperUmlRealization element) {
        SuperUmlClass sourceClass = (SuperUmlClass) idToMyElement.get(element.getSource());
        SuperUmlInterface targetInterface =
                (SuperUmlInterface) idToMyElement.get(element.getTarget());
        sourceClass.realizeInterface(targetInterface);
    }

    public void gotoUmlGeneralization(SuperUmlGeneral element) {
        MyUmlElement source = idToMyElement.get(element.getSource());
        MyUmlElement target = idToMyElement.get(element.getTarget());
        switch (source.getElementType()) {
            case UML_CLASS:
                ((SuperUmlClass) source).setFather((SuperUmlClass) target);
                break;
            case UML_INTERFACE:
                ((SuperUmlInterface) source).addGeneral((SuperUmlInterface) target);
                break;
            default:
                break;
        }
    }

    public void gotoUmlAssociation(SuperUmlAssociation element) {
        SuperUmlAssEnd end1 =
                (SuperUmlAssEnd) idToMyElement.get(element.getEnd1());
        SuperUmlAssEnd end2 =
                (SuperUmlAssEnd) idToMyElement.get(element.getEnd2());
        MyUmlElement ass1 = idToMyElement.get(end1.getReference());
        MyUmlElement ass2 = idToMyElement.get(end2.getReference());
        end1.setReferenceElement(ass1);
        end2.setReferenceElement(ass2);
        switch (ass1.getElementType()) {
            case UML_INTERFACE:
                ((SuperUmlInterface) ass1).addAss(end2);
                break;
            case UML_CLASS:
                ((SuperUmlClass) ass1).addAss(end2);
                break;
            default:
                break;
        }
        switch (ass2.getElementType()) {
            case UML_INTERFACE:
                ((SuperUmlInterface) ass2).addAss(end1);
                break;
            case UML_CLASS:
                ((SuperUmlClass) ass2).addAss(end1);
                break;
            default:
                break;
        }
    }

    public void gotoUmlParameter(SuperUmlParameter element) {
        SuperUmlMethod belongMethod = (SuperUmlMethod) idToMyElement.get(element.getParentId());
        belongMethod.addParameter(element);
    }

    public int getClassCount() {
        return classCount;
    }

}
