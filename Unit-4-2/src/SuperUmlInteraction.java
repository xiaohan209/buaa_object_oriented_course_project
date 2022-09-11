import com.oocourse.uml2.models.elements.UmlElement;

import java.util.HashMap;

public class SuperUmlInteraction extends MyUmlElement {
    private HashMap<String, SuperUmlMessage> messages;
    private HashMap<String, SuperUmlLifeline> lifelines;
    private HashMap<String, SuperUmlLifeline> nameToLifeline;
    private HashMap<String, Boolean> lifelineIsDuplicated;
    private HashMap<String, SuperUmlEndPoint> endPoints;

    public SuperUmlInteraction(UmlElement umlElement) {
        super(umlElement);
        messages = new HashMap<>();
        lifelines = new HashMap<>();
        endPoints = new HashMap<>();
        nameToLifeline = new HashMap<>();
        lifelineIsDuplicated = new HashMap<>();
    }

    public void addMessage(SuperUmlMessage message) {
        messages.put(message.getId(),message);
    }

    public void addLifeline(SuperUmlLifeline lifeline) {
        String name = lifeline.getName();
        if (nameToLifeline.containsKey(name)) {
            lifelineIsDuplicated.put(name,true);
        }
        else {
            nameToLifeline.put(name,lifeline);
            lifelineIsDuplicated.put(name,false);
        }
        lifelines.put(lifeline.getId(),lifeline);
    }

    public void addEndPoint(SuperUmlEndPoint endPoint) {
        endPoints.put(endPoint.getId(),endPoint);
    }

    public int getLifelinesCount() {
        return lifelines.size();
    }

    public int getMessageCount() {
        return messages.size();
    }

    public boolean has(String lifelineName) {
        return nameToLifeline.containsKey(lifelineName);
    }

    public boolean interactionDuplicated(String lifelineName) {
        return lifelineIsDuplicated.get(lifelineName);
    }

    public int getIncomingsCount(String lifelineName) {
        return nameToLifeline.get(lifelineName).getIncomingsCount();
    }

}
