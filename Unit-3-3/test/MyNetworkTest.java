import com.oocourse.spec3.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyNetworkTest {

    private MyNetwork network = new MyNetwork();
    private MyPerson myPerson = new MyPerson(1, "test", BigInteger.valueOf(2), 100);
    private MyGroup myGroup = new MyGroup(10);

    @Before
    public void before() throws EqualPersonIdException, EqualGroupIdException {
        //normal
        network.addPerson(new MyPerson(1, "a", BigInteger.ONE, 1));
        network.addPerson(new MyPerson(2, "b", BigInteger.ZERO, 2));
        network.addPerson(new MyPerson(6, "c", BigInteger.ZERO, 3));
        network.addPerson(new MyPerson(7, "d", BigInteger.ZERO, 4));
        network.addPerson(new MyPerson(8, "e", BigInteger.ZERO, 5));
        network.addPerson(new MyPerson(9, "f", BigInteger.ZERO, 6));
        network.addGroup(myGroup);
    }

    @Test
    public void getId() {
        Assert.assertEquals(myPerson.getId(), 1);
    }

    @Test
    public void getName() {
        Assert.assertEquals(myPerson.getName(), "test");
    }

    @Test
    public void getCharacter() {
        Assert.assertEquals(myPerson.getCharacter(), BigInteger.valueOf(2));
    }

    @Test
    public void getAge() {
        Assert.assertEquals(myPerson.getAge(), 100);
    }

    @Test
    public void isLinked() {
        Assert.assertTrue(myPerson.isLinked(myPerson));
    }

    @Test
    public void getAcquaintanceSum() {
        Assert.assertEquals(myPerson.getAcquaintanceSum(), 0);
    }

    @Test
    public void queryValueInMyPerson() {
        Assert.assertEquals(myPerson.queryValue(myPerson), 0);
    }

    @Test(expected = EqualPersonIdException.class)
    public void addPerson() throws EqualPersonIdException {
        //exception
        network.addPerson(new MyPerson(1, "a", BigInteger.ONE, 1));
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void addRelationNoId() throws PersonIdNotFoundException, EqualRelationException {
        network.addRelation(3, 2, 3);
    }

    @Test(expected = EqualRelationException.class)
    public void addRelationEqualRelation() throws PersonIdNotFoundException, EqualRelationException {
        network.addRelation(1, 2, 100);
        network.addRelation(2, 1, 10);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryValue() throws PersonIdNotFoundException, RelationNotFoundException, EqualRelationException {
        //normal
        network.addRelation(6, 7, 100);
        int ans = network.queryValue(6, 7);
        Assert.assertEquals(ans, 100);
        int sameId = network.queryValue(1, 1);
        Assert.assertEquals(sameId, 0);
        //exception
        network.queryValue(2, 3);
    }

    @Test(expected = RelationNotFoundException.class)
    public void queryValueNoRelation() throws PersonIdNotFoundException, RelationNotFoundException, EqualRelationException {
        network.queryValue(6, 7);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryConflict() throws PersonIdNotFoundException {
        //normal
        BigInteger ans = network.queryConflict(1, 2);
        Assert.assertEquals(ans, BigInteger.ONE);
        //exception
        network.queryConflict(2, 3);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryAcquaintanceSum() throws PersonIdNotFoundException, EqualRelationException {
        //normal
        network.addRelation(8, 9, 1);
        int ansA = network.queryAcquaintanceSum(8);
        int ansB = network.queryAcquaintanceSum(9);
        Assert.assertEquals(ansA, 1);
        Assert.assertEquals(ansB, 1);
        //exception
        network.queryAcquaintanceSum(3);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void compareAge() throws PersonIdNotFoundException {
        //normal
        int ans = network.compareAge(1, 2);
        Assert.assertEquals(ans, -1);
        int sameId = network.compareAge(1, 1);
        Assert.assertEquals(sameId, 0);
        //exception
        network.compareAge(1, 3);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void compareName() throws PersonIdNotFoundException {
        //normal
        int ans = network.compareName(1, 2);
        Assert.assertEquals(ans, -1);
        int sameId = network.compareName(1, 1);
        Assert.assertEquals(sameId, 0);
        //exception
        network.compareName(1, 3);
    }

    @Test
    public void queryPeopleSum() {
        int ans = network.queryPeopleSum();
        Assert.assertEquals(ans, 6);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryNameRank() throws PersonIdNotFoundException {
        //normal
        int ans = network.queryNameRank(2);
        Assert.assertEquals(ans, 2);
        //exception
        network.queryNameRank(3);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void isCircle() throws PersonIdNotFoundException, EqualRelationException {
        //normal
        Assert.assertTrue(network.isCircle(1, 1));
        network.addRelation(1, 2, 100);
        Assert.assertTrue(network.isCircle(1, 2));
        Assert.assertFalse(network.isCircle(1, 6));
        network.addRelation(2, 6, 100);
        Assert.assertTrue(network.isCircle(1, 6));
        //exception
        network.isCircle(3, 2);
    }

    @Test
    public void containsGroup() { //ok
        //assertTrue(network.containsGroup(10));
    }

    @Test(expected = EqualGroupIdException.class)
    public void addGroupEquals() throws EqualGroupIdException {
        network.addGroup(new MyGroup(10));
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void addToGroupNoGroupId() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.addtoGroup(1, 2);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void addToGroupNoPersonId() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.addtoGroup(3, 10);
    }

    @Test(expected = EqualPersonIdException.class)
    public void addToGroupEqualPerson() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.addtoGroup(1, 10);
        network.addtoGroup(1, 10);
    }

    @Test
    public void addToGroupTooManyPerson() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException, EqualGroupIdException {
        MyGroup myGroup1 = new MyGroup(1919);
        network.addGroup(myGroup1);
        MyPerson[] myPerson1 = new MyPerson[2048];
        for (int i = 0; i < 2048; i++) {
            myPerson1[i] = new MyPerson(i + 2048, "a", BigInteger.valueOf(i), i);
            network.addPerson(myPerson1[i]);
            network.addtoGroup(i + 2048, 1919);
        }
        //Assert.assertEquals(1111, myGroup1.getPeopleSum());
        // ！！！如果你没有写addable这个方法，请删掉下面这行！！！
        // Assert.assertFalse(myGroup1.addable());
    }

    @Test
    public void queryGroupSum() {
        Assert.assertEquals(1, network.queryGroupSum());
    }

    @Test
    public void contains() {
        Assert.assertTrue(network.contains(1));
        Assert.assertFalse(network.contains(0));
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupPeopleSum() throws GroupIdNotFoundException {
        //normal
        network.queryGroupPeopleSum(10);
        //exception
        network.queryGroupPeopleSum(2);
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupRelationSum() throws GroupIdNotFoundException {
        //normal
        network.queryGroupRelationSum(10);
        //exception
        network.queryGroupRelationSum(2);
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupValueSum() throws GroupIdNotFoundException {
        //normal
        network.queryGroupValueSum(10);
        //exception
        network.queryGroupValueSum(2);
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupConflictSum() throws GroupIdNotFoundException {
        //normal
        network.queryGroupConflictSum(10);
        //exception
        network.queryGroupConflictSum(2);
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupAgeMean() throws GroupIdNotFoundException {
        //normal
        network.queryGroupAgeMean(10);
        //exception
        network.queryGroupAgeMean(2);
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void queryGroupAgeVar() throws GroupIdNotFoundException {
        //normal
        network.queryGroupAgeVar(10);
        //exception
        network.queryGroupAgeVar(2);
    }

    @Test
    public void queryAgeSum() {
        Assert.assertEquals(3, network.queryAgeSum(1, 3));
    }

    @Test(expected = GroupIdNotFoundException.class)
    public void deleteFromGroupNoGroupId() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.delFromGroup(1, 2);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void deleteFromGroupNoPersonId() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.delFromGroup(3, 10);
    }

    @Test(expected = EqualPersonIdException.class)
    public void deleteFromGroupEqualPerson() throws PersonIdNotFoundException, EqualPersonIdException, GroupIdNotFoundException {
        network.addtoGroup(1, 10);
        network.addtoGroup(2, 10);
        network.delFromGroup(1, 10);
        network.delFromGroup(1, 10);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryMinPath() throws PersonIdNotFoundException, EqualRelationException, EqualPersonIdException, GroupIdNotFoundException, EqualGroupIdException {
        addToGroupTooManyPerson();
        Assert.assertEquals(0, network.queryMinPath(1, 1));
        Assert.assertEquals(-1, network.queryMinPath(1, 2));
        network.addRelation(1, 2, 100);
        Assert.assertEquals(100, network.queryMinPath(1, 2));
        Assert.assertEquals(-1, network.queryMinPath(1, 6));
        network.addRelation(1, 6, 45);
        Assert.assertEquals(100, network.queryMinPath(1, 2));
        network.addRelation(2, 6, 54);
        Assert.assertEquals(99, network.queryMinPath(1, 2));
        network.addRelation(2, 7, 1);
        network.addRelation(7, 6, 1);
        Assert.assertEquals(47, network.queryMinPath(1, 2));
        //exception
        network.queryMinPath(0, 1);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryStrongLinked() throws PersonIdNotFoundException, EqualRelationException {
        Assert.assertTrue(network.queryStrongLinked(1, 1));
        network.addRelation(1, 2, 100);
        Assert.assertFalse(network.queryStrongLinked(1, 2));
        network.addRelation(2, 6, 99);
        Assert.assertFalse(network.queryStrongLinked(1, 2));
        network.addRelation(1, 7, 98);
        Assert.assertFalse(network.queryStrongLinked(1, 2));
        network.addRelation(1, 6, 97);
        Assert.assertTrue(network.queryStrongLinked(1, 2));
        network.addRelation(7, 9, 96);
        Assert.assertFalse(network.queryStrongLinked(1, 9));
        network.addRelation(1, 9, 95);
        Assert.assertTrue(network.queryStrongLinked(1, 9));
        Assert.assertFalse(network.queryStrongLinked(6, 9));
        //exception
        Assert.assertFalse(network.queryStrongLinked(7, 8));
        network.queryStrongLinked(0, 1);
    }

    @Test
    public void queryStrongLinkedAnotherTestCity() throws PersonIdNotFoundException, EqualRelationException {
        //测试目标：闪电图
        network.addRelation(1, 2, 100);
        network.addRelation(2, 6, 99);
        network.addRelation(6, 7, 98);
        network.addRelation(2, 7, 97);
        Assert.assertFalse(network.queryStrongLinked(1, 7));
        network.addRelation(1, 6, 96);
        Assert.assertTrue(network.queryStrongLinked(1, 7));
    }

    @Test
    public void queryStrongLinked8Graph() throws EqualPersonIdException, PersonIdNotFoundException, EqualRelationException {
        //测试目标：八字图
        network.addPerson(new MyPerson(3, "zz", BigInteger.ZERO, 3));
        network.addRelation(1, 6, 100);
        network.addRelation(2, 6, 100);
        Assert.assertFalse(network.queryStrongLinked(1, 2));
        network.addRelation(1, 7, 100);
        network.addRelation(2, 7, 100);
        Assert.assertTrue(network.queryStrongLinked(1, 2));
        network.addRelation(2, 8, 100);
        network.addRelation(3, 8, 100);
        Assert.assertFalse(network.queryStrongLinked(1, 3));
        network.addRelation(2, 9, 100);
        network.addRelation(3, 9, 100);
        Assert.assertFalse(network.queryStrongLinked(1, 3));
        network.addRelation(6, 8, 100);
        Assert.assertTrue(network.queryStrongLinked(1, 3));
    }

    @Test
    public void queryBlockSum() throws PersonIdNotFoundException, EqualRelationException, EqualPersonIdException {
        Assert.assertEquals(6, network.queryBlockSum());
        network.addRelation(1, 2, 100);
        Assert.assertEquals(5, network.queryBlockSum());
        network.addRelation(7, 6, 100);
        Assert.assertEquals(4, network.queryBlockSum());
        network.addRelation(2, 6, 100);
        Assert.assertEquals(3, network.queryBlockSum());
        network.addRelation(1, 6, 100);
        Assert.assertEquals(3, network.queryBlockSum());
        network.addPerson(new MyPerson(3, "zz", BigInteger.ZERO, 3));
        Assert.assertEquals(4, network.queryBlockSum());
        network.addPerson(new MyPerson(4, "ghs", BigInteger.ZERO, 4));
        network.addRelation(4, 6, 100);
        Assert.assertEquals(4, network.queryBlockSum());
    }

    @Test
    public void money() throws PersonIdNotFoundException, EqualPersonIdException {
        Assert.assertEquals(0, network.queryMoney(1));
        Assert.assertEquals(0, network.queryMoney(2));
        Assert.assertEquals(0, network.queryMoney(6));
        network.borrowFrom(1, 2, 100);
        Assert.assertEquals(-100, network.queryMoney(1));
        Assert.assertEquals(100, network.queryMoney(2));
        Assert.assertEquals(0, network.queryMoney(6));
        network.borrowFrom(2, 6, 100);
        Assert.assertEquals(-100, network.queryMoney(1));
        Assert.assertEquals(0, network.queryMoney(2));
        Assert.assertEquals(100, network.queryMoney(6));
        network.borrowFrom(1, 6, -100);
        Assert.assertEquals(0, network.queryMoney(1));
        Assert.assertEquals(0, network.queryMoney(2));
        Assert.assertEquals(0, network.queryMoney(6));
    }

    @Test(expected = EqualPersonIdException.class)
    public void borrowEqualId() throws EqualPersonIdException, PersonIdNotFoundException {
        network.borrowFrom(1, 1, 100);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void borrowFromNoId() throws EqualPersonIdException, PersonIdNotFoundException {
        network.borrowFrom(0, 1, 100);
    }

    @Test(expected = PersonIdNotFoundException.class)
    public void queryMoneyNoId() throws PersonIdNotFoundException {
        network.queryMoney(0);
    }

    @Test
    public void zyyTest() throws PersonIdNotFoundException, EqualRelationException {
        network.addRelation(1, 2, 1);
        assertFalse(network.queryStrongLinked(1, 2));
    }
}
