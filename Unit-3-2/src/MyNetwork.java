import com.oocourse.spec2.exceptions.EqualPersonIdException;
import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.exceptions.EqualRelationException;
import com.oocourse.spec2.exceptions.RelationNotFoundException;
import com.oocourse.spec2.exceptions.EqualGroupIdException;
import com.oocourse.spec2.exceptions.GroupIdNotFoundException;
import com.oocourse.spec2.main.Group;
import com.oocourse.spec2.main.Network;
import com.oocourse.spec2.main.Person;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class MyNetwork implements Network {
    private HashMap<Integer,Person> people;
    private HashMap<Integer,Group> groups;

    public MyNetwork() {
        people = new HashMap<Integer,Person>();
        groups = new HashMap<Integer, Group>();
    }

    public boolean contains(int id) {
        return people.containsKey(id);
    }

    public Person getPerson(int id) {
        if (contains(id)) {
            return people.get(id);
        }
        else {
            return null;
        }
    }

    public void addPerson(Person person) throws EqualPersonIdException {
        if (people.containsValue(person)) {
            throw new EqualPersonIdException();
        }
        people.put(person.getId(),person);
    }

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

    public int queryValue(int id1, int id2) throws
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

    public BigInteger queryConflict(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getCharacter().xor(getPerson(id2).getCharacter());
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    public int queryAcquaintanceSum(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            return getPerson(id).getAcquaintanceSum();
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    public int compareAge(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getAge() - getPerson(id2).getAge();
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getName().compareTo(getPerson(id2).getName());
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    public int queryPeopleSum() {
        return people.size();
    }

    public int queryNameRank(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            int sum = 0;
            for (Person person:people.values()) {
                if (compareName(id, person.getId()) > 0) {
                    sum++;
                }
            }
            sum++;
            return sum;
        }
        else {
            throw new PersonIdNotFoundException();
        }
    }

    public boolean isCircle(int id1, int id2)throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            ArrayList<Person> linkQueue = new ArrayList<Person>();
            linkQueue.add(getPerson(id1));
            int i = 0;
            while (i < linkQueue.size()) {
                Person thisPerson = linkQueue.get(i);
                if (thisPerson.getId() == id2) {
                    return true;
                }
                HashMap<Integer,Person> linked = ((MyPerson) thisPerson).getAcquaintance();
                for (Integer personId:linked.keySet()) {
                    Person linkedPerson = linked.get(personId);
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

    public void addGroup(Group group) throws EqualGroupIdException {
        Integer groupId = group.getId();
        if (groups.containsKey(groupId)) {
            throw new EqualGroupIdException();
        }
        groups.put(group.getId(),group);
    }

    public Group getGroup(int id) {
        return groups.get(id);
    }

    public void addtoGroup(int id1, int id2) throws GroupIdNotFoundException,
            PersonIdNotFoundException, EqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        }
        if (!contains(id1)) {
            throw new PersonIdNotFoundException();
        }
        if (getGroup(id2).hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
        MyGroup group = (MyGroup) getGroup(id2);
        MyPerson person = (MyPerson) getPerson(id1);
        if (group.getPeopleLength() < 1111) {
            group.addPerson(person);
            person.addGroup(group);
        }
    }

    public int queryGroupSum() {
        return groups.size();
    }

    public int queryGroupPeopleSum(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        MyGroup group = (MyGroup) groups.get(id);
        return group.getPeopleLength();
    }

    public int queryGroupRelationSum(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return groups.get(id).getRelationSum();
    }

    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return groups.get(id).getValueSum();
    }

    public BigInteger queryGroupConflictSum(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return groups.get(id).getConflictSum();
    }

    public int queryGroupAgeMean(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return groups.get(id).getAgeMean();
    }

    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if (!groups.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return groups.get(id).getAgeVar();
    }
}
