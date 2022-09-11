package GCSimulation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JVMHeapTest {
    JVMHeap jvmHeap;

    @Before
    public void setUp() throws Exception {
        ArrayList<MyObject> arrays = new ArrayList<MyObject>();
        for (int i = 0; i < 10; i++) {
            arrays.add(new MyObject());
        }
        jvmHeap = new JVMHeap(arrays);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setUnreferenceId() {
        ArrayList<Integer> arrays = new ArrayList<Integer>();
        arrays.add(1);
        arrays.add(3);
        arrays.add(5);
        arrays.add(7);
        jvmHeap.setUnreferenceId(arrays);
        Assert.assertEquals(10,jvmHeap.getSize());
    }

    @Test
    public void removeUnreference() {
        setUnreferenceId();
        jvmHeap.removeUnreference();
        Assert.assertEquals(6,jvmHeap.getSize());
    }
}