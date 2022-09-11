import java.math.BigInteger;
import java.util.HashMap;

public class MyGroup implements Group {
    /*@ public instance model non_null int id;
      @ public instance model non_null Person[] people;
      @*/
    public int groupId;
    public HashMap<Integer,Person> groupPeople;
    public int relationSum;
    public int valueSum;
    public long ageSum;
    public long ageSquareSum;
    public BigInteger conflictSum;

    public MyGroup(int id) {
        this.groupId = id;
        this.groupPeople = new HashMap<Integer, Person>();
        relationSum = 0;
        valueSum = 0;
        ageSum = 0;
        ageSquareSum = 0;
        conflictSum = BigInteger.ZERO;
    }

    //@ ensures \result == id;
    public /*@pure@*/ int getId() {
        return groupId;
    }

    /*@ also
      @ public normal_behavior
      @ requires obj != null && obj instanceof Group;
      @ assignable \nothing;
      @ ensures \result == (((Group) obj).getId() == id);
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Group);
      @ assignable \nothing;
      @ ensures \result == false;
      @*/
    public /*@pure@*/ boolean equals(Object obj) {
        if (obj != null && obj instanceof Group) {
            return ((Group) obj).getId() == groupId;
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
        if (groupPeople.isEmpty()) {
            groupPeople.put(person.getId(),person);
            relationSum++;
            return;
        }
        for (Person groupPerson:groupPeople.values()) {
            if (person.isLinked(groupPerson)) {
                relationSum += 2;
                valueSum += 2 * person.queryValue(groupPerson);
            }
        }
        groupPeople.put(person.getId(),person);
        relationSum++;
    }

    public void updateRelations(int queryValue) {
        valueSum += queryValue;
        relationSum++;
    }

    //@ ensures \result == (\exists int i; 0 <= i && i < people.length; people[i].equals(person));
    public /*@pure@*/ boolean hasPerson(Person person) {
        return groupPeople.containsValue(person);
    }

    /*@ ensures \result == (\sum int i; 0 <= i && i < people.length;
      @          (\sum int j; 0 <= j && j < people.length && people[i].isLinked(people[j]); 1));
      @*/
    public /*@pure@*/ int getRelationSum() {
        return relationSum;
    }

    /*@ ensures \result == (\sum int i; 0 <= i && i < people.length;
      @          (\sum int j; 0 <= j && j < people.length &&
      @           people[i].isLinked(people[j]); people[i].queryValue(people[j])));
      @*/
    public /*@pure@*/ int getValueSum() {
        return valueSum;
    }

    /*@ public normal_behavior
     @ requires people.length > 0;
     @ ensures (\exists BigInteger[] temp;
     @          temp.length == people.length && temp[0] == people[0].getCharacter();
     @           (\forall int i; 1 <= i && i < temp.length;
     @            temp[i] == temp[i-1].xor(people[i].getCharacter())) &&
     @             \result == temp[temp.length - 1]);
     @ also
     @ public normal_behavior
     @ requires people.length == 0;
     @ ensures \result == BigInteger.ZERO;
     @*/
    public /*@pure@*/ BigInteger getConflictSum() {
        return conflictSum;
    }

    /*@ public normal_behavior
      @ requires people.length == 0;
      @ ensures \result == 0;
      @ also
      @ public normal_behavior
      @ requires people.length != 0;
      @ ensures \result == ((\sum int i; 0 <= i && i < people.length; people[i].getAge()) / people.length);
      @*/
    public /*@pure@*/ int getAgeMean() {
        if (groupPeople.size() == 0) {
            return 0;
        }
        return (int)(ageSum / (long) groupPeople.size());
    }

    /*@ public normal_behavior
      @ requires people.length == 0;
      @ ensures \result == 0;
      @ also
      @ public normal_behavior
      @ requires people.length != 0;
      @ ensures \result == ((\sum int i; 0 <= i && i < people.length;
      @          (people[i].getAge() - getAgeMean()) * (people[i].getAge() - getAgeMean())) /
      @           people.length);
      @*/
    public /*@pure@*/ int getAgeVar() {
        int size = groupPeople.size();
        if (size == 0) {
            return 0;
        }
        long mean = getAgeMean();
        long sum = ageSquareSum - 2 * mean * ageSum + size * mean * mean;
        sum = sum / (long) size;
        return (int) sum;
    }

    public int getPeopleLength() {
        return groupPeople.size();
    }

    public void delPerson(Person person) {
        groupPeople.remove(person.getId());
        relationSum--;
        int age = person.getAge();
        ageSum -= age;
        ageSquareSum -= age * age;
        conflictSum = conflictSum.xor(person.getCharacter());
        if (groupPeople.isEmpty()) {
            return;
        }
        for (Person groupPerson:groupPeople.values()) {
            if (person.isLinked(groupPerson)) {
                relationSum -= 2;
                valueSum -= 2 * person.queryValue(groupPerson);
            }
        }
    }
}

