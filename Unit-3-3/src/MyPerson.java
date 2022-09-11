import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.HashMap;

public class MyPerson implements Person {
    public MyPerson(int id, String name, BigInteger character, int age) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = age;
        acquaintance = new HashMap<Integer, Person>();
        value = new HashMap<Integer, Integer>();
        personGroup = new HashMap<Integer, Group>();
        reach = new HashMap<Integer, Person>();
        reach.put(id,this);
    }

    private int id;
    private String name;
    private BigInteger character;
    private int age;
    private HashMap<Integer,Person> acquaintance;
    private HashMap<Integer,Integer> value;
    private HashMap<Integer, Group> personGroup;
    private HashMap<Integer,Person> reach;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getCharacter() {
        return character;
    }

    public int getAge() {
        return age;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        return ((Person) obj).getId() == id;
    }

    public boolean isLinked(Person person) {
        if (person.getId() == id) {
            return true;
        }
        Integer personId = person.getId();
        return acquaintance.containsKey(personId);
    }

    public int queryValue(Person person) {
        Integer personId = person.getId();
        if (value.containsKey(personId)) {
            return value.get(personId);
        }
        return 0;
    }

    public void addRelation(Person friend,int friendValue) {
        Integer personId = friend.getId();
        value.put(personId,friendValue);
        acquaintance.put(personId,friend);
        updateRelations(friendValue,friend);
    }

    public int getAcquaintanceSum() {
        return acquaintance.size();
    }

    public int compareTo(Person p2) {
        return name.compareTo(p2.getName());
    }

    public HashMap<Integer, Person> getAcquaintance() {
        return acquaintance;
    }

    public void addGroup(Group group) {
        personGroup.put(group.getId(),group);
    }

    public void updateRelations(int query,Person linkedPerson) {
        for (Group thisGroup:personGroup.values()) {
            if (thisGroup.hasPerson(linkedPerson)) {
                ((MyGroup)thisGroup).updateRelations(query);
            }
        }
    }

    public void delGroup(Group group) {
        personGroup.remove(group.getId());
    }

    public int getReachSum() {
        return reach.size();
    }

    public boolean canReach(int id) {
        return reach.containsKey(id);
    }

    public HashMap<Integer,Person> getReach() {
        return reach;
    }

    public void setReach(HashMap<Integer,Person> newReach) {
        this.reach = newReach;
    }
}
