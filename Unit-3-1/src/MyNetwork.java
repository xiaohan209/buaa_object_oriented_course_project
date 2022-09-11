import com.oocourse.spec1.exceptions.EqualPersonIdException;
import com.oocourse.spec1.exceptions.EqualRelationException;
import com.oocourse.spec1.exceptions.PersonIdNotFoundException;
import com.oocourse.spec1.exceptions.RelationNotFoundException;
import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class MyNetwork implements Network {
    /*@ public instance model non_null Person[] people;
      @*/
    private ArrayList<Person> people;
    private HashMap<Person, Boolean> isValid;

    public MyNetwork() {
        people = new ArrayList<Person>();
        isValid = new HashMap<Person, Boolean>();
    }

    //@ ensures \result == (\exists int i; 0 <= i && i < people.length; people[i].getId() == id);
    public /*@pure@*/ boolean contains(int id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    /*@ public normal_behavior
      @ requires contains(id);
      @ ensures (\exists int i; 0 <= i && i < people.length; people[i].getId() == id &&
      @         \result == people[i]);
      @ also
      @ public normal_behavior
      @ requires !contains(id);
      @ ensures \result == null;
      @*/
    public /*@pure@*/ Person getPerson(int id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                return people.get(i);
            }
        }
        return null;
    }

    /*@ public normal_behavior
      @ requires !(\exists int i; 0 <= i && i < \old(people.length);
      @              \old(people[i].equals(person)));
      @ assignable people;
      @ ensures people.length == \old(people.length) + 1;
      @ ensures (\forall int i; 0 <= i && i < \old(people.length);
      @          (\exists int j; 0 <= j && j < people.length; people[j] == (\old(people[i]))));
      @ ensures (\exists int i; 0 <= i && i < people.length; people[i] == person);
      @ also
      @ public exceptional_behavior
      @ signals (EqualPersonIdException e) (\exists int i; 0 <= i && i < people.length;
      @                                     people[i].equals(person));
      @*/
    public void addPerson(Person person) throws EqualPersonIdException {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).equals(person)) {
                throw new EqualPersonIdException();
            }
        }
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().compareTo(person.getName()) > 0) {
                people.add(i,person);
                isValid.put(person,true);
                return;
            }
        }
        people.add(person);
        isValid.put(person,true);
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2) && id1 != id2 &&
      @         !\old(getPerson(id1).isLinked(getPerson(id2)));
      @ assignable people;
      @ ensures people.length == \old(people.length);
      @ ensures (\forall int i; 0 <= i && i < \old(people.length);
      @         (\exists int j; 0 <= j && j < people.length; people[j] == \old(people[i])));
      @ ensures (\forall int i; 0 <= i && i < people.length && \old(people[i].getId()) != id1 &&
      @         \old(people[i].getId()) != id2; \not_assigned(people[i]));
      @ ensures getPerson(id1).isLinked(getPerson(id2)) && getPerson(id2).isLinked(getPerson(id1));
      @ ensures getPerson(id1).queryValue(getPerson(id2)) == value;
      @ ensures getPerson(id2).queryValue(getPerson(id1)) == value;
      @ ensures (\forall int i; 0 <= i && i < \old(getPerson(id1).acquaintance.length);
      @         \old(getPerson(id1).acquaintance[i]) == getPerson(id1).acquaintance[i] &&
      @         \old(getPerson(id1).value[i]) == getPerson(id1).value[i]);
      @ ensures (\forall int i; 0 <= i && i < \old(getPerson(id2).acquaintance.length);
      @         \old(getPerson(id2).acquaintance[i]) == getPerson(id2).acquaintance[i] &&
      @         \old(getPerson(id2).value[i]) == getPerson(id2).value[i]);
      @ ensures getPerson(id1).value.length == getPerson(id1).acquaintance.length;
      @ ensures getPerson(id2).value.length == getPerson(id2).acquaintance.length;
      @ ensures \old(getPerson(id1).value.length) == getPerson(id1).acquaintance.length - 1;
      @ ensures \old(getPerson(id2).value.length) == getPerson(id2).acquaintance.length - 1;
      @ also
      @ public normal_behavior
      @ requires id1 == id2 && contains(id1);
      @ assignable \nothing;
      @ also
      @ public exceptional_behavior
      @ requires !contains(id1)||!contains(id2)||
      @         (\old(getPerson(id1)).isLinked(\old(getPerson(id2))) && id1 != id2);
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @ signals (EqualRelationException e) contains(id1) && contains(id2) &&
      @         \old(getPerson(id1)).isLinked(\old(getPerson(id2)));
      @*/
    public void addRelation(int id1, int id2, int value) throws
            PersonIdNotFoundException, EqualRelationException {
        if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) {
            return;
        }
        if (getPerson(id1).isLinked(getPerson(id2))) {
            throw new EqualRelationException();
        }
        MyPerson firstPerson = (MyPerson) getPerson(id1);
        MyPerson secondPerson = (MyPerson) getPerson(id2);
        firstPerson.addRelation(getPerson(id2),value);
        secondPerson.addRelation(getPerson(id1),value);
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2) && getPerson(id1).isLinked(getPerson(id2));
      @ ensures \result == getPerson(id1).queryValue(getPerson(id2));
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @ signals (RelationNotFoundException e) contains(id1) && contains(id2) &&
      @         !getPerson(id1).isLinked(getPerson(id2));
      @*/
    public /*@pure@*/ int queryValue(int id1, int id2) throws
            PersonIdNotFoundException, RelationNotFoundException {
        if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        else if (!getPerson(id1).isLinked(getPerson(id2))) {
            throw new RelationNotFoundException();
        }
        else {
            return getPerson(id1).queryValue(getPerson(id2));
        }
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2);
      @ ensures \result.equals(getPerson(id1).getCharacter().xor(getPerson(id2).getCharacter()));
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @*/
    public /*@pure@*/ BigInteger queryConflict(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getCharacter().xor(getPerson(id2).getCharacter());
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    /*@ public normal_behavior
      @ requires contains(id);
      @ ensures \result.equals(getPerson(id).getAcquaintanceSum());
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id));
      @*/
    public /*@pure@*/ int queryAcquaintanceSum(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            return getPerson(id).getAcquaintanceSum();
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2);
      @ ensures \result == getPerson(id1).getAge() - getPerson(id2).getAge();
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @*/
    public /*@pure@*/ int compareAge(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getAge() - getPerson(id2).getAge();
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2);
      @ ensures \result == getPerson(id1).getName().compareTo(getPerson(id2).getName);
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @*/
    public /*@pure@*/ int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return  getPerson(id1).getName().compareTo(getPerson(id2).getName());
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    //ensures \result == people.length;
    public /*@pure@*/ int queryPeopleSum() {
        return people.size();
    }

    /*@ public normal_behavior
      @ requires contains(id);
      @ ensures \result == (\sum int i; 0 <= i && i < people.length &&
      @                     compareName(id, people[i].getId()) > 0; 1) + 1;
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id));
      @*/

    public /*@pure@*/ int queryNameRank(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            int sum = 0;
            for (int i = 0; i < people.size(); i++) {
                if (compareName(id, people.get(i).getId()) == 0) {
                    return i + 1;
                }
            }
            return people.size();
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    /*@ public normal_behavior
      @ requires contains(id1) && contains(id2);
      @ ensures \result == (\exists Person[] array; array.length >= 2;
      @                     array[0].equals(getPerson(id1)) &&
      @                     array[array.length - 1].equals(getPerson(id2)) &&
      @                      (\forall int i; 0 <= i && i < array.length - 1;
      @                      array[i].isLinked(array[i + 1]) == true));
      @ also
      @ public exceptional_behavior
      @ signals (PersonIdNotFoundException e) !contains(id1) || !contains(id2);
      @*/
    public /*@pure@*/ boolean isCircle(int id1, int id2)throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            ArrayList<Person> linkQueue = new ArrayList<Person>();
            linkQueue.add(getPerson(id1));
            int i = 0;
            while (i < linkQueue.size()) {
                Person thisPerson = linkQueue.get(i);
                if (thisPerson.getId() == id2) {
                    return true;
                }
                ArrayList<Person> linked = ((MyPerson) thisPerson).getAcquaintance();
                for (int j = 0; j < linked.size(); j++) {
                    Person linkedPerson = linked.get(j);
                    if (!linkQueue.contains(linkedPerson)) {
                        linkQueue.add(linkedPerson);
                    }
                }
                i++;
            }
            return false;
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }
}
