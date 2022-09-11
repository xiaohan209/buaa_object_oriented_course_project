import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ConflictQueryTypeException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {
    private int classCount;
    private int interfaceCount;
    private HashMap<String,UmlElement> idToElement;
    private HashMap<String,MyUmlElement> idToMyElement;
    private HashMap<String,SuperUmlClass> nameToClass;
    private HashMap<String,Boolean> classIsDuplicated;

    public MyUmlInteraction(UmlElement... elements) {
        classCount = 0;
        interfaceCount = 0;
        idToElement = new HashMap<>();
        idToMyElement = new HashMap<>();
        nameToClass = new HashMap<>();
        classIsDuplicated = new HashMap<>();
        for (UmlElement moveElement:elements) {
            //first time:class,interface
            idToElement.put(moveElement.getId(),moveElement);
            MyUmlElement newElement = null;
            switch (moveElement.getElementType()) {
                case UML_CLASS:
                    newElement = new SuperUmlClass(moveElement);
                    gotoUmlClass((SuperUmlClass) newElement);
                    break;
                case UML_INTERFACE:
                    newElement = new SuperUmlInterface(moveElement);
                    gotoUmlInterface((SuperUmlInterface) newElement);
                    break;
                case UML_OPERATION:
                    newElement = new SuperUmlMethod(moveElement);
                    break;
                case UML_INTERFACE_REALIZATION:
                    newElement = new SuperUmlRealization(moveElement);
                    break;
                case UML_ASSOCIATION_END:
                    newElement = new SuperUmlAssEnd(moveElement);
                    break;
                case UML_GENERALIZATION:
                    newElement = new SuperUmlGeneral(moveElement);
                    break;
                case UML_ASSOCIATION:
                    newElement = new SuperUmlAssociation(moveElement);
                    break;
                case UML_PARAMETER:
                    newElement = new SuperUmlParameter(moveElement);
                    break;
                case UML_ATTRIBUTE:
                    newElement = new SuperUmlAttribute(moveElement);
                    break;
                default:
                    break;
            }
            idToMyElement.put(moveElement.getId(),newElement);
        }
        for (MyUmlElement moveElement:idToMyElement.values()) {
            //second time: attribute,operation,associationEnd,realization
            switch (moveElement.getElementType()) {
                case UML_ATTRIBUTE:
                    gotoUmlAttribute((SuperUmlAttribute) moveElement);
                    break;
                case UML_OPERATION:
                    gotoUmlOperation((SuperUmlMethod) moveElement);
                    break;
                case UML_INTERFACE_REALIZATION:
                    gotoUmlRealization((SuperUmlRealization) moveElement);
                    break;
                case UML_GENERALIZATION:
                    gotoUmlGeneralization((SuperUmlGeneral) moveElement);
                    break;
                case UML_ASSOCIATION:
                    gotoUmlAssociation((SuperUmlAssociation) moveElement);
                    break;
                default:
                    break;
            }
        }
        for (MyUmlElement moveElement:idToMyElement.values()) {
            //third time : parameter & update
            switch (moveElement.getElementType()) {
                case UML_PARAMETER:
                    gotoUmlParameter((SuperUmlParameter) moveElement);
                    break;
                case UML_CLASS:
                    ((SuperUmlClass) moveElement).update();
                    break;
                case UML_INTERFACE:
                    ((SuperUmlInterface) moveElement).update();
                    break;
                default:
                    break;
            }
        }
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

    public int getClassOperationCount(String className, OperationQueryType[] queryTypes)
            throws ClassNotFoundException, ClassDuplicatedException, ConflictQueryTypeException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        boolean npa = false;
        boolean pa = false;
        boolean nre = false;
        boolean re = false;
        for (OperationQueryType type:queryTypes) {
            switch (type) {
                case PARAM:
                    pa = true;
                    break;
                case RETURN:
                    re = true;
                    break;
                case NON_PARAM:
                    npa = true;
                    break;
                case NON_RETURN:
                    nre = true;
                    break;
                default:
                    break;
            }
        }
        if ((pa && npa) || (nre && re)) {
            throw new ConflictQueryTypeException();
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.queryClassOperation(queryTypes);
    }

    public int getClassAttributeCount(String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getAttributeCount(queryType);
    }

    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getAssCount();
    }

    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getAssClassList();
    }

    public Map<Visibility, Integer>
        getClassOperationVisibility(String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getOperationVisibility(operationName);
    }

    public Visibility getClassAttributeVisibility(String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        int count = 0;
        SuperUmlClass thisClass = nameToClass.get(className);
        Visibility visibility = Visibility.DEFAULT;
        for (SuperUmlAttribute queryAttribute:thisClass.getAttributeAll().values()) {
            if (queryAttribute.getName().equals(attributeName)) {
                visibility = queryAttribute.getVisibility();
                count++;
            }
        }
        if (count == 0) {
            throw new AttributeNotFoundException(className,attributeName);
        }
        if (count > 1) {
            throw new AttributeDuplicatedException(className,attributeName);
        }
        return visibility;
    }

    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getAncestor();
    }

    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getInterfaceList();
    }

    public List<AttributeClassInformation> getInformationNotHidden(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        SuperUmlClass thisClass = nameToClass.get(className);
        return thisClass.getNotHidden();
    }
}
