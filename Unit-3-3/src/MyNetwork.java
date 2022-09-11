import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class MyNetwork implements Network {
    private HashMap<Integer,Person> people;
    private HashMap<Integer,Group> groups;
    private HashMap<Integer,Integer> money;
    private Stack stack;
    private HashMap<Integer,Integer> index;
    private HashMap<Integer,Integer> low;
    private HashMap<Integer,Integer> forbidden;
    private HashSet<Integer> visited;
    private int tarjanIndex;

    public MyNetwork() {
        people = new HashMap<Integer,Person>();
        groups = new HashMap<Integer, Group>();
        money = new HashMap<Integer, Integer>();
        index = new HashMap<Integer, Integer>();
        low = new HashMap<Integer, Integer>();
        forbidden = new HashMap<Integer, Integer>();
        stack = new Stack();
        visited = new HashSet<Integer>();
        tarjanIndex = 0;
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
        money.put(person.getId(),0);
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
        HashMap<Integer,Person> mergeReach = new HashMap<Integer, Person>();
        mergeReach.putAll(firstPerson.getReach());
        mergeReach.putAll(secondPerson.getReach());
        for (Person allPerson:mergeReach.values()) {
            ((MyPerson)allPerson).setReach(mergeReach);
        }
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

    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            MyPerson personOne = (MyPerson) getPerson(id1);
            return personOne.canReach(id2);
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

    public int queryAgeSum(int l, int r) {
        int sum = 0;
        for (Person testPerson:people.values()) {
            int age = testPerson.getAge();
            if (age >= l && age <= r) {
                sum++;
            }
        }
        return sum;
    }

    public void delFromGroup(int id1, int id2)
            throws GroupIdNotFoundException, PersonIdNotFoundException, EqualPersonIdException {
        if (!groups.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        }
        if (!people.containsKey(id1)) {
            throw new PersonIdNotFoundException();
        }
        MyGroup deleteGroup = (MyGroup) groups.get(id2);
        if (!deleteGroup.hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
        MyPerson deletePerson = (MyPerson) people.get(id1);
        deleteGroup.delPerson(deletePerson);
        deletePerson.delGroup(deleteGroup);
    }

    public int queryMinPath(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) {
            return 0;
        }
        if (!isCircle(id1,id2)) {
            return -1;
        }
        PriorityQueue<NewMinPath> pathQueue = new PriorityQueue<NewMinPath>();
        HashMap<Integer,Integer> minPath = new HashMap<Integer, Integer>();
        pathQueue.add(new NewMinPath(0,id1));
        HashSet<Integer> invalidPerson = new HashSet<Integer>();
        int reachSum = ((MyPerson) getPerson(id1)).getReachSum();
        while (invalidPerson.size() < reachSum) {
            int min = 0;
            int thisId = 0;
            while (!pathQueue.isEmpty()) {
                NewMinPath newMinPath = pathQueue.poll();
                int id = newMinPath.getPoint();
                int nowValue = newMinPath.getMinPath();
                if (!invalidPerson.contains(id)) {
                    min = nowValue;
                    thisId = id;
                    break;
                }
            }
            invalidPerson.add(thisId);
            minPath.put(thisId,min);
            if (id2 == thisId) {
                return minPath.get(id2);
            }
            MyPerson traPerson = (MyPerson) getPerson(thisId);
            for (Person linkedPerson:traPerson.getAcquaintance().values()) {
                int linkedId = linkedPerson.getId();
                if (!invalidPerson.contains(linkedId)) {
                    int linkedValue = traPerson.queryValue(linkedPerson);
                    int valueSum = minPath.get(thisId) + linkedValue;
                    pathQueue.add(new NewMinPath(valueSum,linkedId));
                }
            }
        }
        return -2;
    }

    public boolean queryStrongLinked(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) {
            return true;
        }
        tarjanIndex = 0;
        stack.clear();
        index.clear();
        low.clear();
        forbidden.clear();
        visited.clear();
        tarjan(id1);
        if (!low.containsKey(id2)) {
            return false;
        }
        return low.get(id2).equals(1);
    }

    @SuppressWarnings("unchecked")
    public int tarjan(int id) {
        stack.push((Integer)id);
        index.put(id,++tarjanIndex);
        low.put(id,tarjanIndex);
        visited.add(id);
        HashMap<Integer,Person> linkedPeople = ((MyPerson) getPerson(id)).getAcquaintance();
        for (Person linkedPerson:linkedPeople.values()) {
            int linkedId = linkedPerson.getId();
            Integer father = forbidden.get(id);
            if (father != null) {
                if (father.equals(linkedId)) {
                    continue;
                }
            }
            if (!visited.contains(linkedId)) {
                forbidden.put(linkedId,id);
                int lowSon = tarjan(linkedId);
                if (lowSon < low.get(id)) {
                    low.put(id,lowSon);
                }
                if (low.get(linkedId) >= index.get(id)) {
                    Integer stackTop = (Integer) stack.peek();
                    while (low.get(stackTop) >= index.get(id)  &&
                            !low.get(stackTop).equals(index.get(stackTop))) {
                        low.put((Integer) stack.pop(),index.get(id));
                        stackTop = (Integer) stack.peek();
                    }
                    if (stackTop != id) {
                        stack.pop();
                    }
                }
            }
            else if (stack.contains(linkedId)) {
                int reachFather = index.get(linkedId);
                if (reachFather < low.get(id)) {
                    low.put(id,reachFather);
                }
            }
        }
        return low.get(id);
    }

    public int queryBlockSum() {
        int reachSum = 0;
        HashSet<Integer> allPeople = new HashSet<Integer>();
        for (Person traPerson:people.values()) {
            if (!allPeople.contains(traPerson.getId())) {
                allPeople.addAll(((MyPerson)traPerson).getReach().keySet());
                reachSum++;
            }
        }
        return reachSum;
    }

    public void borrowFrom(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualPersonIdException {
        if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) {
            throw new EqualPersonIdException();
        }
        money.put(id1,money.get(id1) - value);
        money.put(id2,money.get(id2) + value);
    }

    public int queryMoney(int id) throws  PersonIdNotFoundException {
        if (!contains(id)) {
            throw new PersonIdNotFoundException();
        }
        return money.get(id);
    }
}
