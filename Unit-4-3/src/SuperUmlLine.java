import com.oocourse.uml3.models.elements.UmlElement;

import java.util.HashMap;

public class SuperUmlLine extends MyUmlElement {
    private HashMap<String,SuperUmlMessage> incomings;
    private HashMap<String,SuperUmlMessage> outgoings;

    public SuperUmlLine(UmlElement umlElement) {
        super(umlElement);
        incomings = new HashMap<>();
        outgoings = new HashMap<>();
    }

    public void addIn(SuperUmlMessage message) {
        incomings.put(message.getId(),message);
    }

    public void addOut(SuperUmlMessage message) {
        outgoings.put(message.getId(),message);
    }

    public int getIncomingsCount() {
        return incomings.size();
    }

    public int getOutgoingsCount() {
        return outgoings.size();
    }
}
