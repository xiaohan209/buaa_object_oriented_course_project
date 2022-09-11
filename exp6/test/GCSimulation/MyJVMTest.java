package GCSimulation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyJVMTest {
    MyJVM myJVM;
    @Before
    public void setUp() throws Exception {
        System.out.println("before test JVM");
        myJVM = new MyJVM();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("After test JVM");
    }

    @Test
    public void createObject() {
        myJVM.createObject(5);
        getSnapShoot();
        myJVM.createObject(7);
        getSnapShoot();
        myJVM.createObject(6);
        getSnapShoot();
    }

    @Test
    public void setUnreference() {
        myJVM.createObject(5);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(3);
        myJVM.setUnreference(list);
        getSnapShoot();
    }

    @Test
    public void minorGC() {
        myJVM.createObject(32);
        getSnapShoot();
    }

    @Test
    public void majorGC() {
        myJVM.createObject(320);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(3);
        myJVM.setUnreference(list);
        getSnapShoot();
    }

    @Test
    public void getSnapShoot() {
        myJVM.createObject(20);
        myJVM.getSnapShoot();
        myJVM.createObject(20);
        myJVM.getSnapShoot();
    }
}