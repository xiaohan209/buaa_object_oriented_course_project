package GCSimulation;

import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class MyHeapTest {
    MyHeap myHeap;
    @Before
    public void setUp() throws Exception {
        System.out.println("before test MyHeap");
        myHeap = new MyHeap(16);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("after test MyHeap");
    }

    @Test
    public void heapSizeFor() {

    }

    @Test
    public void getSize() {
        MyObject mo1 = new MyObject();
        myHeap.add(mo1);
        int size = myHeap.getSize();
        Assert.assertEquals(1,size);
        MyObject mo2 = new MyObject();
        myHeap.add(mo2);
        MyObject mo3 = new MyObject();
        myHeap.add(mo3);
        size = myHeap.getSize();
        Assert.assertEquals(3,size);
    }

    @Test
    public void getElementData() {

    }

    @Test
    public void setElementData() {
        MyObject mo1 = new MyObject();
        MyObject mo2 = new MyObject();
        myHeap.add(mo1);
        myHeap.setElementData(0,mo2);
        Assert.assertTrue(myHeap.getElementData()[0] == mo2);
        Assert.assertFalse(myHeap.getElementData()[0] == mo1);
    }

    @Test
    public void clear() {
        MyObject mo1 = new MyObject();
        MyObject mo2 = new MyObject();
        myHeap.add(mo1);
        myHeap.add(mo2);
        myHeap.clear();
        int size = myHeap.getSize();
        Assert.assertEquals(0,size);
    }

    @Test
    public void setSize() {
        MyObject mo1 = new MyObject();
        MyObject mo2 = new MyObject();
        myHeap.add(mo1);
        myHeap.add(mo2);
        myHeap.setSize(1);
        int size = myHeap.getSize();
        Assert.assertEquals(1,size);
    }

    @Test
    public void add() {
        MyObject mo1 = new MyObject();
        MyObject mo2 = new MyObject();
        MyObject mo3 = new MyObject();
        MyObject mo4 = new MyObject();
        MyObject mo5 = new MyObject();
        myHeap.add(mo1);
        boolean flag = false;
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo1) {
                flag = true;
            }
        }
        Assert.assertTrue(flag);
        flag = false;
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo3) {
                flag = true;
            }
        }
        Assert.assertFalse(flag);
        myHeap.add(mo3);
        myHeap.add(mo4);
        flag = false;
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo3) {
                flag = true;
            }
        }
        Assert.assertTrue(flag);
        flag = false;
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo1) {
                flag = true;
            }
        }
        Assert.assertTrue(flag);
        flag = false;
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo5) {
                flag = true;
            }
        }
        Assert.assertFalse(flag);
        for (int i = 0; i < myHeap.getSize(); i++) {
            if (myHeap.getElementData()[i] == mo2) {
                flag = true;
            }
        }
        Assert.assertFalse(flag);
    }

    @Test
    public void removeFirst() {
        MyObject mo1 = new MyObject();
        myHeap.add(mo1);
        myHeap.removeFirst();
        int size = myHeap.getSize();
        Assert.assertEquals(0,size);
        Assert.assertFalse(myHeap.getElementData().length == 0);
    }
}