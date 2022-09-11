package GCSimulation;

import java.util.ArrayList;
import java.util.List;

public class MyJVM {
    private final int DefaultSize = 16;
    private final int MaxTenuringThreshold = 8;

    private JVMHeap<MyObject> eden;
    private ArrayList<JVMHeap<MyObject>> survive = new ArrayList<>();
    // fixed:fromSurviveSpace 0->1 because the first survive area is S0
    // way to find : use main to test，use the example input and output to find the bug
    private int fromSurviveSpace = 1;
    private JVMHeap<MyObject> tenured;

    MyJVM() {
        eden = new JVMHeap<MyObject>(DefaultSize);
        survive.add(new JVMHeap<MyObject>(DefaultSize));
        survive.add(new JVMHeap<MyObject>(DefaultSize));
        tenured = new JVMHeap<MyObject>(DefaultSize);
    }

    public void createObject(int count) {
        for (int i = 0; i < count; i++) {
            MyObject newObject = new MyObject();
            eden.add(newObject);
            if (eden.getSize() == DefaultSize) {
                System.out.println("Eden reaches its capacity,triggered Minor Garbage Collection.");
                minorGC();
            }
        }
    }

    public void setUnreference(List<Integer> objectId) {
        eden.setUnreferenceId(objectId);
        survive.get(fromSurviveSpace).setUnreferenceId(objectId);
        tenured.setUnreferenceId(objectId);
    }

    public void minorGC() {
        /* Your Code Here */
        for (int i = 0; i < eden.getSize(); i++) {
            MyObject mo = (MyObject) eden.getElementData()[i];
            if (!mo.getReferenced()) {
                continue;
            }
            mo.setAge(mo.getAge() + 1);
            survive.get(1-fromSurviveSpace).add(mo);
        }
        for (int i = 0; i < survive.get(fromSurviveSpace).getSize(); i++) {
            MyObject mo = (MyObject) survive.get(fromSurviveSpace).getElementData()[i];
            if (!mo.getReferenced()) {
                continue;
            }
            mo.setAge(mo.getAge() + 1);
            if (mo.getAge() == MaxTenuringThreshold) {
                tenured.add(mo);
            } else {
                survive.get(1 - fromSurviveSpace).add(mo);
            }
        }
        eden.setSize(0);
        survive.get(fromSurviveSpace).setSize(0);
        fromSurviveSpace = 1 - fromSurviveSpace;
    }

    public void majorGC() {
        Object[] oldElement = tenured.getElementData().clone();
        int oldSize = tenured.getSize();
        tenured.setSize(0);
        for (int i = 0; i < oldSize; i++) {
            MyObject mo = (MyObject) oldElement[i];
            if (!mo.getReferenced()) {
                continue;
            }
            tenured.add(mo);
        }
    }

    public void getSnapShoot() {
        System.out.println("Eden: " + eden.getSize());
        for (int i = 0; i < eden.getSize(); i++) {
            MyObject mo = (MyObject) eden.getElementData()[i];
            System.out.print(mo.getId() + " ");
        }
        System.out.println("");

        System.out.println("Survive 0: " + survive.get(0).getSize());
        for (int i = 0; i < survive.get(0).getSize(); i++) {
            MyObject mo = (MyObject) survive.get(0).getElementData()[i];
            System.out.print(mo.getId() + " ");
        }
        System.out.println("");

        System.out.println("Survive 1: " + survive.get(1).getSize());
        for (int i = 0; i < survive.get(1).getSize(); i++) {
            MyObject mo = (MyObject) survive.get(1).getElementData()[i];
            System.out.print(mo.getId() + " ");
        }
        System.out.println("");

        System.out.println("Tenured: " + tenured.getSize());
        for (int i = 0; i < tenured.getSize(); i++) {
            MyObject mo = (MyObject) tenured.getElementData()[i];
            System.out.print(mo.getId() + " ");
        }

        System.out.println("\n---------------------------------");
    }

}
