import com.oocourse.spec1.main.Person;

import java.math.BigInteger;
import java.util.ArrayList;

public class MyPerson implements Person {
    public MyPerson(int id, String name, BigInteger character, int age) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = age;
        acquaintance = new ArrayList<Person>();
        value = new ArrayList<Integer>();
    }

    /*@ public instance model non_null int id;
      @ public instance model non_null String name;
      @ public instance model non_null BigInteger character;
      @ public instance model non_null int age;
      @ public instance model non_null Person[] acquaintance;
      @ public instance model non_null int[] value;
      @*/
    private int id;
    private String name;
    private BigInteger character;
    private int age;
    private ArrayList<Person> acquaintance;
    private ArrayList<Integer> value;

    //@ ensures \result == id;
    public /*@pure@*/ int getId() {
        return id;
    }

    //@ ensures \result.equals(name);
    public /*@pure@*/ String getName() {
        return name;
    }

    //@ ensures \result.equals(character);
    public /*@pure@*/ BigInteger getCharacter() {
        return character;
    }

    //@ ensures \result == age;
    public /*@pure@*/ int getAge() {
        return age;
    }

    /*@ public normal_behavior
      @ requires obj != null && obj instanceof Person;
      @ assignable \nothing;
      @ ensures \result == (((Person) obj).getId() == id);
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Person);
      @ assignable \nothing;
      @ ensures \result == false;
      @*/
    public /*@pure@*/ boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        return ((Person) obj).getId() == id;
    }

    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures \result == (\exists int i; 0 <= i && i < acquaintance.length;
      @                     acquaintance[i].getId() == person.getId()) || person.getId() == id;
      @*/
    public /*@pure@*/ boolean isLinked(Person person) {
        if (person.getId() == id) {
            return true;
        }
        for (int i = 0; i < acquaintance.size(); i++) {
            if (acquaintance.get(i).getId() == person.getId()) {
                return true;
            }
        }
        return false;
    }

    /*@ public normal_behavior
      @ requires (\exists int i; 0 <= i && i < acquaintance.length;
      @          acquaintance[i].getId() == person.getId());
      @ assignable \nothing;
      @ ensures (\exists int i; 0 <= i && i < acquaintance.length;
      @         acquaintance[i].getId() == person.getId() && \result == value[i]);
      @ also
      @ public normal_behavior
      @ requires (\forall int i; 0 <= i && i < acquaintance.length;
      @          acquaintance[i].getId() != person.getId());
      @ ensures \result == 0;
      @*/
    public /*@pure@*/ int queryValue(Person person) {
        for (int i = 0; i < acquaintance.size(); i++) {
            if (acquaintance.get(i).getId() == person.getId()) {
                return (int)value.get(i);
            }
        }
        return 0;
    }

    /*@ public normal_behavior
      @ assignable value;
      @ ensures (\forall int i; 0 <= i && i < \old(acquaintance.length);
      @         \old(acquaintance[i]) == acquaintance[i] &&
      @         \old(value[i]) == value[i]);
      @ ensures (\exists int i; 0 <= i && i < acquaintance.length;
      @         acquaintance[i].getId() == friend.getId() && value[i] == friendValue)
      @ ensures acquaintance.length = \old(acquaintance.length) + 1;
     */
    public void addRelation(Person friend,int friendValue) {
        value.add(friendValue);
        acquaintance.add(friend);
    }

    //@ ensures \result == acquaintance.length;
    public /*@pure@*/ int getAcquaintanceSum() {
        return acquaintance.size();
    }

    //@ ensures \result == name.compareTo(p2.getName());
    public /*@pure@*/ int compareTo(Person p2) {
        return name.compareTo(p2.getName());
    }

    //@ ensures (\forall int i; 0 <= i && i < acquaintance.length;
    //@         (\exists int j; 0 <= j && j < acquaintance.length; acquaintance[i] == (\result)[j]))
    public ArrayList<Person> getAcquaintance() {
        return acquaintance;
    }
}
