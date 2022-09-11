import com.oocourse.uml3.interact.common.AttributeClassInformation;
import com.oocourse.uml3.interact.common.AttributeQueryType;
import com.oocourse.uml3.interact.common.OperationQueryType;
import com.oocourse.uml3.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.ConflictQueryTypeException;
import com.oocourse.uml3.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml3.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml3.interact.exceptions.user.UmlRule001Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule003Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule004Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule005Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule006Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule007Exception;
import com.oocourse.uml3.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlClassOrInterface;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.interact.format.UmlGeneralInteraction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    private MyUmlModel myModel;
    private MyMachine myMachine;
    private MyCollaboration myCollaboration;
    private HashMap<String,MyUmlElement> idToMyElement;
    private HashMap<String,UmlElement> idToElement;
    private HashMap<String,SuperUmlClass> nameToClass;
    private HashMap<String,Boolean> classIsDuplicated;
    private HashMap<String,SuperUmlStateMachine> nameToStateMachines;
    private HashMap<String,Boolean> stateMachineIsDuplicated;
    private HashMap<String,SuperUmlInteraction> nameToInteraction;
    private HashMap<String,Boolean> interactionIsDuplicated;

    public MyUmlGeneralInteraction(UmlElement... elements) {
        nameToClass = new HashMap<>();
        classIsDuplicated = new HashMap<>();
        idToMyElement = new HashMap<>();
        idToElement = new HashMap<>();
        myModel = new MyUmlModel(idToElement,idToMyElement);
        myMachine = new MyMachine(idToElement,idToMyElement);
        myCollaboration = new MyCollaboration(idToElement,idToMyElement);
        firstTraverse(elements);
        secondTraverse();
        thirdTraverse();
        nameToClass = myModel.getNameToClass();
        classIsDuplicated = myModel.getClassIsDuplicated();
        nameToStateMachines = myMachine.getNameToStateMachine();
        stateMachineIsDuplicated = myMachine.getStateMachineIsDuplicated();
        nameToInteraction = myCollaboration.getNameToInteraction();
        interactionIsDuplicated = myCollaboration.getInteractionIsDuplicated();
    }

    private void firstTraverse(UmlElement... elements) {
        for (UmlElement moveElement:elements) {
            idToElement.put(moveElement.getId(),moveElement);
            MyUmlElement newElement = null;
            switch (moveElement.getElementType()) {
                case UML_CLASS:
                case UML_INTERFACE:
                case UML_OPERATION:
                case UML_INTERFACE_REALIZATION:
                case UML_ASSOCIATION_END:
                case UML_GENERALIZATION:
                case UML_ASSOCIATION:
                case UML_PARAMETER:
                case UML_ATTRIBUTE:
                    newElement = createModel(moveElement);
                    break;
                case UML_STATE_MACHINE:
                case UML_REGION:
                case UML_STATE:
                case UML_FINAL_STATE:
                case UML_PSEUDOSTATE:
                case UML_EVENT:
                case UML_TRANSITION:
                case UML_OPAQUE_BEHAVIOR:
                    newElement = createStateMachine(moveElement);
                    break;
                case UML_MESSAGE:
                case UML_ENDPOINT:
                case UML_LIFELINE:
                case UML_INTERACTION:
                    newElement = createCollaboration(moveElement);
                    break;
                default:
                    break;
            }
            idToMyElement.put(moveElement.getId(),newElement);
        }
    }

    private MyUmlElement createModel(UmlElement moveElement) {
        MyUmlElement newElement = null;
        ElementType type = moveElement.getElementType();
        if (type == ElementType.UML_CLASS) {
            newElement = new SuperUmlClass(moveElement);
            myModel.gotoUmlClass((SuperUmlClass) newElement);
        } else if (type == ElementType.UML_INTERFACE) {
            newElement = new SuperUmlInterface(moveElement);
            myModel.gotoUmlInterface((SuperUmlInterface) newElement);
        } else if (type == ElementType.UML_OPERATION) {
            newElement = new SuperUmlMethod(moveElement);
        } else if (type == ElementType.UML_INTERFACE_REALIZATION) {
            newElement = new SuperUmlRealization(moveElement);
        } else if (type == ElementType.UML_ASSOCIATION_END) {
            newElement = new SuperUmlAssEnd(moveElement);
        } else if (type == ElementType.UML_GENERALIZATION) {
            newElement = new SuperUmlGeneral(moveElement);
        } else if (type == ElementType.UML_ASSOCIATION) {
            newElement = new SuperUmlAssociation(moveElement);
        } else if (type == ElementType.UML_PARAMETER) {
            newElement = new SuperUmlParameter(moveElement);
        } else if (type == ElementType.UML_ATTRIBUTE) {
            newElement = new SuperUmlAttribute(moveElement);
        }
        return newElement;
    }

    private MyUmlElement createStateMachine(UmlElement moveElement) {
        MyUmlElement newElement = null;
        ElementType type = moveElement.getElementType();
        if (type == ElementType.UML_STATE_MACHINE) {
            newElement = new SuperUmlStateMachine(moveElement);
            myMachine.gotoStateMachine((SuperUmlStateMachine) newElement);
        } else if (type == ElementType.UML_REGION) {
            newElement = new SuperUmlRegion(moveElement);
        } else if (type == ElementType.UML_STATE || type == ElementType.UML_FINAL_STATE ||
                type == ElementType.UML_PSEUDOSTATE) {
            newElement = new SuperUmlState(moveElement);
        } else if (type == ElementType.UML_EVENT) {
            newElement = new SuperUmlEvent(moveElement);
        } else if (type == ElementType.UML_TRANSITION) {
            newElement = new SuperUmlTransition(moveElement);
        } else if (type == ElementType.UML_OPAQUE_BEHAVIOR) {
            newElement = new SuperUmlOpaqueBehavior(moveElement);
        }
        return newElement;
    }

    private MyUmlElement createCollaboration(UmlElement moveElement) {
        MyUmlElement newElement = null;
        ElementType type = moveElement.getElementType();
        if (type == ElementType.UML_MESSAGE) {
            newElement = new SuperUmlMessage(moveElement);
        } else if (type == ElementType.UML_ENDPOINT) {
            newElement = new SuperUmlEndPoint(moveElement);
        } else if (type == ElementType.UML_LIFELINE) {
            newElement = new SuperUmlLifeline(moveElement);
        } else if (type == ElementType.UML_INTERACTION) {
            newElement = new SuperUmlInteraction(moveElement);
            myCollaboration.gotoInteraction((SuperUmlInteraction) newElement);
        }
        return newElement;
    }

    private void secondTraverse() {
        for (MyUmlElement moveElement:idToMyElement.values()) {
            ElementType type = moveElement.getElementType();
            if (type == ElementType.UML_ATTRIBUTE) {
                myModel.gotoUmlAttribute((SuperUmlAttribute) moveElement);
            } else if (type == ElementType.UML_OPERATION) {
                myModel.gotoUmlOperation((SuperUmlMethod) moveElement);
            } else if (type == ElementType.UML_PARAMETER) {
                myModel.gotoUmlParameter((SuperUmlParameter) moveElement);
            } else if (type == ElementType.UML_INTERFACE_REALIZATION) {
                myModel.gotoUmlRealization((SuperUmlRealization) moveElement);
            } else if (type == ElementType.UML_GENERALIZATION) {
                myModel.gotoUmlGeneralization((SuperUmlGeneral) moveElement);
            } else if (type == ElementType.UML_ASSOCIATION) {
                myModel.gotoUmlAssociation((SuperUmlAssociation) moveElement);
            } else if (type == ElementType.UML_REGION) {
                myMachine.gotoRegion((SuperUmlRegion) moveElement);
            } else if (type == ElementType.UML_EVENT) {
                myMachine.gotoEvent((SuperUmlEvent) moveElement);
            } else if (type == ElementType.UML_OPAQUE_BEHAVIOR) {
                myMachine.gotoBehavior((SuperUmlOpaqueBehavior) moveElement);
            } else if (type == ElementType.UML_STATE || type == ElementType.UML_FINAL_STATE
                    || type == ElementType.UML_PSEUDOSTATE) {
                myMachine.gotoState((SuperUmlState) moveElement);
            } else if (type == ElementType.UML_TRANSITION) {
                myMachine.gotoTransition((SuperUmlTransition) moveElement);
            } else if (type == ElementType.UML_MESSAGE) {
                myCollaboration.gotoMessage((SuperUmlMessage) moveElement);
            } else if (type == ElementType.UML_LIFELINE) {
                myCollaboration.gotoLifeline((SuperUmlLifeline) moveElement);
            } else if (type == ElementType.UML_ENDPOINT) {
                myCollaboration.gotoEndPoint((SuperUmlEndPoint) moveElement);
            }
        }
    }

    private void thirdTraverse() {
        for (MyUmlElement moveElement:idToMyElement.values()) {
            ElementType type = moveElement.getElementType();
            if (type == ElementType.UML_CLASS) {
                ((SuperUmlClass) moveElement).beginUpdate();
            } else if (type == ElementType.UML_INTERFACE) {
                ((SuperUmlInterface) moveElement).beginUpdate();
            } else if (type == ElementType.UML_STATE) {
                ((SuperUmlState) moveElement).beginUpdate();
            }
        }
    }

    public int getClassCount() {
        return myModel.getClassCount();
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
            if (type == OperationQueryType.PARAM) {
                pa = true;
            } else if (type == OperationQueryType.RETURN) {
                re = true;
            } else if (type == OperationQueryType.NON_PARAM) {
                npa = true;
            } else if (type == OperationQueryType.NON_RETURN) {
                nre = true;
            }
        }
        if ((pa && npa) || (nre && re)) {
            throw new ConflictQueryTypeException();
        }
        return nameToClass.get(className).queryClassOperation(queryTypes);
    }

    public int getClassAttributeCount(String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        return nameToClass.get(className).getAttributeCount(queryType);
    }

    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        return nameToClass.get(className).getAssCount();
    }

    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        return nameToClass.get(className).getAssClassList();
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
        return nameToClass.get(className).getOperationVisibility(operationName);
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
        return nameToClass.get(className).getAncestor();
    }

    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        return nameToClass.get(className).getInterfaceList();
    }

    public List<AttributeClassInformation> getInformationNotHidden(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!nameToClass.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classIsDuplicated.get(className)) {
            throw new ClassDuplicatedException(className);
        }
        return nameToClass.get(className).getNotHidden();
    }

    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException, InteractionDuplicatedException {
        if (!nameToInteraction.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        }
        if (interactionIsDuplicated.get(interactionName)) {
            throw new InteractionDuplicatedException(interactionName);
        }
        return nameToInteraction.get(interactionName).getLifelinesCount();
    }

    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException, InteractionDuplicatedException {
        if (!nameToInteraction.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        }
        if (interactionIsDuplicated.get(interactionName)) {
            throw new InteractionDuplicatedException(interactionName);
        }
        return nameToInteraction.get(interactionName).getMessageCount();
    }

    public int getIncomingMessageCount(String interactionName, String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        if (!nameToInteraction.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        }
        if (interactionIsDuplicated.get(interactionName)) {
            throw new InteractionDuplicatedException(interactionName);
        }
        SuperUmlInteraction thisInteraction = nameToInteraction.get(interactionName);
        if (!thisInteraction.has(lifelineName)) {
            throw new LifelineNotFoundException(interactionName,lifelineName);
        }
        if (thisInteraction.interactionDuplicated(lifelineName)) {
            throw new LifelineDuplicatedException(interactionName,lifelineName);
        }
        return thisInteraction.getIncomingsCount(lifelineName);
    }

    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!nameToStateMachines.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        }
        if (stateMachineIsDuplicated.get(stateMachineName)) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        return nameToStateMachines.get(stateMachineName).getStateCount();
    }

    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!nameToStateMachines.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        }
        if (stateMachineIsDuplicated.get(stateMachineName)) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        return nameToStateMachines.get(stateMachineName).getTransitionCount();
    }

    public int getSubsequentStateCount(String stateMachineName, String stateName)
            throws StateMachineNotFoundException, StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        if (!nameToStateMachines.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        }
        if (stateMachineIsDuplicated.get(stateMachineName)) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        SuperUmlStateMachine thisMachine = nameToStateMachines.get(stateMachineName);
        if (!thisMachine.has(stateName)) {
            throw new StateNotFoundException(stateMachineName,stateName);
        }
        if (thisMachine.stateDuplicated(stateName)) {
            throw new StateDuplicatedException(stateMachineName,stateName);
        }
        return thisMachine.getNextStatesCount(stateName);
    }

    public void checkForUml001() throws UmlRule001Exception {
        HashSet<AttributeClassInformation> allDuplicated = myModel.getDuplicated();
        if (allDuplicated.size() != 0) {
            throw new UmlRule001Exception(allDuplicated);
        }
    }

    public void checkForUml002() throws UmlRule002Exception {
        HashSet<UmlClassOrInterface> circleGeneral = myModel.findCircle();
        if (circleGeneral.size() != 0) {
            throw new UmlRule002Exception(circleGeneral);
        }
    }

    public void checkForUml003() throws UmlRule003Exception {
        HashSet<UmlClassOrInterface> generalDuplicated = myModel.findDuplicatedGeneral();
        if (generalDuplicated.size() != 0) {
            throw new UmlRule003Exception(generalDuplicated);
        }
    }

    public void checkForUml004() throws UmlRule004Exception {
        HashSet<UmlClassOrInterface> realizeDuplicated = myModel.findDuplicatedRealize();
        if (realizeDuplicated.size() != 0) {
            throw new UmlRule004Exception(realizeDuplicated);
        }
    }

    public void checkForUml005() throws UmlRule005Exception {
        if (myModel.nameIsNull()) {
            throw new UmlRule005Exception();
        }
    }

    public void checkForUml006() throws UmlRule006Exception {
        if (myModel.interfaceNotPublic()) {
            throw new UmlRule006Exception();
        }
    }

    public void checkForUml007() throws UmlRule007Exception {
        if (myMachine.finalHasOut()) {
            throw new UmlRule007Exception();
        }
    }

    public void checkForUml008() throws UmlRule008Exception {
        if (myMachine.initOneOut()) {
            throw new UmlRule008Exception();
        }
    }
}
