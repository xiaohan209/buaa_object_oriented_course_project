import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.HashMap;

public class MyGroup implements Group {
    private int id;
    private HashMap<Integer,Person> people;
    private int relationSum;
    private int valueSum;
    private long ageSum;
    private long ageSquareSum;
    private BigInteger conflictSum;

    public MyGroup(int id) {
        this.id = id;
        this.people = new HashMap<Integer, Person>();
        relationSum = 0;
        valueSum = 0;
        ageSum = 0;
        ageSquareSum = 0;
        conflictSum = BigInteger.ZERO;
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
        int age = person.getAge();
        ageSum += age;
        ageSquareSum += age * age;
        conflictSum = conflictSum.xor(person.getCharacter());
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
        return people.containsValue(person);
    }

    public int getRelationSum() {
        return relationSum;
    }

    public int getValueSum() {
        return valueSum;
    }

    public BigInteger getConflictSum() {
        return conflictSum;
    }

    public int getAgeMean() {
        if (people.size() == 0) {
            return 0;
        }
        return (int)(ageSum / (long) people.size());
    }

    public int getAgeVar() {
        int size = people.size();
        if (size == 0) {
            return 0;
        }
        long mean = getAgeMean();
        long sum = ageSquareSum - 2 * mean * ageSum + size * mean * mean;
        sum = sum / (long) size;
        return (int) sum;
    }

    public int getPeopleLength() {
        return people.size();
    }

    public void delPerson(Person person) {
        people.remove(person.getId());
        relationSum--;
        int age = person.getAge();
        ageSum -= age;
        ageSquareSum -= age * age;
        conflictSum = conflictSum.xor(person.getCharacter());
        if (people.isEmpty()) {
            return;
        }
        for (Person groupPerson:people.values()) {
            if (person.isLinked(groupPerson)) {
                relationSum -= 2;
                valueSum -= 2 * person.queryValue(groupPerson);
            }
        }
    }
}

