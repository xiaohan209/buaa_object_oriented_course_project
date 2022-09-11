import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ConflictQueryTypeException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;

import java.util.HashMap;
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
            //first time:class,interface,statemachine
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
        switch (moveElement.getElementType()) {
            case UML_CLASS:
                newElement = new SuperUmlClass(moveElement);
                myModel.gotoUmlClass((SuperUmlClass) newElement);
                break;
            case UML_INTERFACE:
                newElement = new SuperUmlInterface(moveElement);
                myModel.gotoUmlInterface((SuperUmlInterface) newElement);
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
        return newElement;
    }

    private MyUmlElement createStateMachine(UmlElement moveElement) {
        MyUmlElement newElement = null;
        switch (moveElement.getElementType()) {
            case UML_STATE_MACHINE:
                newElement = new SuperUmlStateMachine(moveElement);
                myMachine.gotoStateMachine((SuperUmlStateMachine) newElement);
                break;
            case UML_REGION:
                newElement = new SuperUmlRegion(moveElement);
                break;
            case UML_STATE:
            case UML_FINAL_STATE:
            case UML_PSEUDOSTATE:
                newElement = new SuperUmlState(moveElement);
                break;
            case UML_EVENT:
                newElement = new SuperUmlEvent(moveElement);
                break;
            case UML_TRANSITION:
                newElement = new SuperUmlTransition(moveElement);
                break;
            case UML_OPAQUE_BEHAVIOR:
                newElement = new SuperUmlOpaqueBehavior(moveElement);
                break;
            default:
                break;
        }
        return newElement;
    }

    private MyUmlElement createCollaboration(UmlElement moveElement) {
        MyUmlElement newElement = null;
        switch (moveElement.getElementType()) {
            case UML_MESSAGE:
                newElement = new SuperUmlMessage(moveElement);
                break;
            case UML_ENDPOINT:
                newElement = new SuperUmlEndPoint(moveElement);
                break;
            case UML_LIFELINE:
                newElement = new SuperUmlLifeline(moveElement);
                break;
            case UML_INTERACTION:
                newElement = new SuperUmlInteraction(moveElement);
                myCollaboration.gotoInteraction((SuperUmlInteraction) newElement);
                break;
            default:
                break;
        }
        return newElement;
    }

    private void secondTraverse() {
        for (MyUmlElement moveElement:idToMyElement.values()) {
            //second time: excludes the higher hierarchy
            switch (moveElement.getElementType()) {
                case UML_ATTRIBUTE:
                    myModel.gotoUmlAttribute((SuperUmlAttribute) moveElement);
                    break;
                case UML_OPERATION:
                    myModel.gotoUmlOperation((SuperUmlMethod) moveElement);
                    break;
                case UML_PARAMETER:
                    myModel.gotoUmlParameter((SuperUmlParameter) moveElement);
                    break;
                case UML_INTERFACE_REALIZATION:
                    myModel.gotoUmlRealization((SuperUmlRealization) moveElement);
                    break;
                case UML_GENERALIZATION:
                    myModel.gotoUmlGeneralization((SuperUmlGeneral) moveElement);
                    break;
                case UML_ASSOCIATION:
                    myModel.gotoUmlAssociation((SuperUmlAssociation) moveElement);
                    break;
                case UML_REGION:
                    myMachine.gotoRegion((SuperUmlRegion) moveElement);
                    break;
                case UML_EVENT:
                    myMachine.gotoEvent((SuperUmlEvent) moveElement);
                    break;
                case UML_OPAQUE_BEHAVIOR:
                    myMachine.gotoBehavior((SuperUmlOpaqueBehavior) moveElement);
                    break;
                case UML_STATE:
                case UML_FINAL_STATE:
                case UML_PSEUDOSTATE:
                    myMachine.gotoState((SuperUmlState) moveElement);
                    break;
                case UML_TRANSITION:
                    myMachine.gotoTransition((SuperUmlTransition) moveElement);
                    break;
                case UML_MESSAGE:
                    myCollaboration.gotoMessage((SuperUmlMessage) moveElement);
                    break;
                case UML_LIFELINE:
                    myCollaboration.gotoLifeline((SuperUmlLifeline) moveElement);
                    break;
                case UML_ENDPOINT:
                    myCollaboration.gotoEndPoint((SuperUmlEndPoint) moveElement);
                    break;
                default:
                    break;
            }
        }
    }

    private void thirdTraverse() {
        for (MyUmlElement moveElement:idToMyElement.values()) {
            //third time : update
            switch (moveElement.getElementType()) {
                case UML_CLASS:
                    ((SuperUmlClass) moveElement).update();
                    break;
                case UML_INTERFACE:
                    ((SuperUmlInterface) moveElement).update();
                    break;
                case UML_STATE:
                    ((SuperUmlState) moveElement).beginUpdate();
                    break;
                default:
                    break;
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
        SuperUmlStateMachine thisMachine = nameToStateMachines.get(stateMachineName);
        return thisMachine.getStateCount();
    }

    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!nameToStateMachines.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        }
        if (stateMachineIsDuplicated.get(stateMachineName)) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        SuperUmlStateMachine thisMachine = nameToStateMachines.get(stateMachineName);
        return thisMachine.getTransitionCount();
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
}
