import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Person;

import java.math.BigInteger;
import java.util.HashMap;

public class MyGroup implements Group {
    private int id;
    private HashMap<Integer,Person> people;
    private int relationSum;
    private int valueSum;

    public MyGroup(int id) {
        this.id = id;
        this.people = new HashMap<Integer, Person>();
        relationSum = 0;
        valueSum = 0;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Group) {
            return ((Group) obj).getId() == id;
        }
        else {
            return false;
        }
    }

    public void addPerson(Person person) {
        if (people.isEmpty()) {
            people.put(person.getId(),person);
            relationSum++;
            return;
        }
        for (Person groupPerson:people.values()) {
            if (person.isLinked(groupPerson)) {
                relationSum += 2;
                valueSum += 2 * person.queryValue(groupPerson);
            }
        }
        people.put(person.getId(),person);
        relationSum++;
    }

    public void updateRelations(int queryValue) {
        valueSum += queryValue;
        relationSum++;
    }

    public boolean hasPerson(Person person) {
        Integer personId = person.getId();
        return people.containsKey(personId);
    }

    public int getRelationSum() {
        return relationSum;
        /*int sum = 0;
        for (Integer thisId: people.keySet()) {
            for (Integer linkedId:people.keySet()) {
                if (people.get(thisId).isLinked(people.get(linkedId))) {
                    sum++;
                }
            }
        }
        return sum;*/
    }

    public int getValueSum() {
        return valueSum;
        /*int sum = 0;
        for (Integer thisId: people.keySet()) {
            for (Integer linkedId:people.keySet()) {
                if (people.get(thisId).isLinked(people.get(linkedId))) {
                    sum = sum + people.get(thisId).queryValue(people.get(linkedId));
                }
            }
        }
        return sum;*/
    }

    public BigInteger getConflictSum() {
        if (people.size() == 0) {
            return BigInteger.ZERO;
        }
        boolean init = true;
        BigInteger temp = BigInteger.ZERO;
        for (Integer thisId: people.keySet()) {
            if (init) {
                temp = people.get(thisId).getCharacter();
                init = false;
            }
            else {
                temp = temp.xor(people.get(thisId).getCharacter());
            }
        }
        return temp;
    }

    public int getAgeMean() {
        if (people.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (Integer thisId:people.keySet()) {
            sum += people.get(thisId).getAge();
        }
        sum = sum / people.size();
        return sum;
    }

    public int getAgeVar() {
        if (people.size() == 0) {
            return 0;
        }
        int mean = getAgeMean();
        int sum = 0;
        int age = 0;
        for (Integer thisId:people.keySet()) {
            age = people.get(thisId).getAge();
            sum += (age - mean) * (age - mean);
        }
        sum = sum / people.size();
        return sum;
    }

    public int getPeopleLength() {
        return people.size();
    }
}

