package GCSimulation;

import java.util.ArrayList;
import java.util.List;

public class MyHeap<T extends Comparable<T>> {
    //@ public instance model non_null Object[] elementData;
    /*@ invariant (\forall int i; 0 <= i && 2 * i + 1 < elementData.length; elementData[i].compareTo(elementData[2 * i + 1]) < 0)
      @        && (\forall int i; 0 <= i && 2 * i + 2 < elementData.length; elementData[i].compareTo(elementData[2 * i + 2]) < 0)
      @        && (\forall int i; 0 < i && 2 * i + 2 < elementData.length; elementData[2 * i + 1].compareTo(elementData[2 * i + 2]) < 0);
      @*/

    private final int DefaultSize = 16;
    private final double DefaultFactor = 0.75;

    private int capacity;
    private Object[] elementData;
    private int size;
    private double factor;

    static final int heapSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    MyHeap(int size) {
        if (size < 0) {
            //fixed:turn size to capacity or the capacity is 0
            this.capacity = DefaultSize;
        } else {
            this.capacity = heapSizeFor(size);
        }
        factor = DefaultFactor;
        elementData = new Object[size];
        this.size = 0;
    }

    MyHeap(int size, double factor) {
        if (size < 0) {
            //fixed:turn size to capacity or the capacity is 0
            this.capacity = DefaultSize;
        } else {
            this.capacity = heapSizeFor(size);
        }
        this.factor = factor;
        elementData = new Object[size];
        this.size = 0;
    }

    MyHeap(List<T> list) {
        this.factor = DefaultFactor;
        this.capacity = DefaultSize;
        // fixed: elementdata is not declared clearly, so it may be null
        // way to find: JVMHeapTest it can't be add because null
        elementData = new Object[list.size()];
        for (T i : list) {
            this.add(i);
        }
        size = list.size();
    }

    //@ ensures \result == elementData.length;
    public /*@pure@*/ int getSize() {
        return size;
    }

    //@ ensures \result == elementData
    public /*@pure@*/ Object[] getElementData() {
        return elementData;
    }

    /*@ public normal_behavior
      @ requires index >= 0 && index < getSize();
      @ assignable elementData;
      @ ensures elementData[index] == element;
      @*/
    protected void setElementData(int index, Object element) {
        this.elementData[index] = element;
    }

    /*@ public normal_behavior
      @ assignable size;
      @ ensures size == 0;
      @*/
    protected void clear() {
        this.size = 0;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    /*@ public normal_behavior
      @ assignable size, elementData, capacity;
      @ ensures (\exists int i; 0 <= i && i < elementData.length; newElement == elementData[i]);
      @*/
    @SuppressWarnings("unchecked")
    public void add(Object newElement) {
        elementData[size] = newElement;
        if (size == 0) {
            size++;
            return;
        }

        int tIndex = size;
        boolean flag = true;
        Object elementSwitch;
        while (flag && tIndex > 0) {
            flag = false;
            if (tIndex % 2 == 0 &&
                    ((T) elementData[tIndex]).compareTo((T) elementData[tIndex - 1]) < 0) {
                elementSwitch = elementData[tIndex];
                elementData[tIndex] = elementData[tIndex - 1];
                elementData[tIndex - 1] = elementSwitch;
                tIndex--;
                flag = true;
            }

            if (tIndex != 0 &&
                    ((T) elementData[tIndex]).compareTo((T) elementData[tIndex / 2]) < 0) {
                elementSwitch = elementData[tIndex / 2];
                elementData[tIndex / 2] = elementData[tIndex];
                elementData[tIndex] = elementSwitch;
                tIndex = tIndex / 2;
                flag = true;
            }
        }
        size++;
        if (size >= capacity * factor) {
            Object[] oldElementData = elementData.clone();
            elementData = new Object[capacity << 1];
            for (int i = 0; i < size; i++) {
                elementData[i] = oldElementData[i];
            }
            capacity = capacity << 1;
            // System.out.println("Capacity doubled: "+capacity);
        }
    }

    /*@ public normal_behavior
      @ requires size != 0;
      @ ensures (\forall int i; 0 <= i && i < elementData.length; \old(elementData[0]) != elementData[i] );
      @ assignable elementData;
      @ also
      @ public normal_behavior
      @ requires size == 0;
      @ assignable \nothing;
      @*/
    @SuppressWarnings("unchecked")
    public void removeFirst() {
        if (size == 0) {
            System.err.println("No element found in list.");
        }
        int newIndex = 0;
        List<Object> leftHand = sort(1);
        List<Object> rightHand = sort(2);
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftHand.size() && rightIndex < rightHand.size()) {
            if (((T) leftHand.get(leftIndex)).compareTo((T) rightHand.get(rightIndex)) < 0) {
                elementData[newIndex] = leftHand.get(leftIndex);
                leftIndex++;
            } else {
                elementData[newIndex] = rightHand.get(rightIndex);
                rightIndex++;
            }
            newIndex++;
        }
        while (leftIndex < leftHand.size()) {
            elementData[newIndex] = leftHand.get(leftIndex);
            leftIndex++;
            newIndex++;
        }
        while (rightIndex < rightHand.size()) {
            elementData[newIndex] = rightHand.get(rightIndex);
            rightIndex++;
            newIndex++;
        }
        //fixed: by test/MyHeapTest removeFirst() please see the test part of the code
        //add "setSize(getSize() - 1)" the size must minus one after remove
        setSize(getSize() - 1);
    }

    @SuppressWarnings("unchecked")
    private List<Object> sort(int root) {
        List<Object> result = new ArrayList<Object>();
        if (root >= size) {
            return result;
        }
        result.add(elementData[root]);
        if (root * 2 + 1 >= size) {
            return result;
        }
        List<Object> leftHand = sort(root * 2 + 1);
        List<Object> rightHand = sort(root * 2 + 2);
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftHand.size() && rightIndex < rightHand.size()) {
            if (((T) leftHand.get(leftIndex)).compareTo((T) rightHand.get(rightIndex)) < 0) {
                result.add(leftHand.get(leftIndex));
                leftIndex++;
            } else {
                result.add(rightHand.get(rightIndex));
                rightIndex++;
            }
        }
        while (leftIndex < leftHand.size()) {
            result.add(leftHand.get(leftIndex));
            leftIndex++;
        }
        while (rightIndex < rightHand.size()) {
            result.add(rightHand.get(rightIndex));
            rightIndex++;
        }
        return result;
    }
}
