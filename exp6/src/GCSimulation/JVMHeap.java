package GCSimulation;

import java.util.List;

public class JVMHeap<T extends Comparable<T>> 
    extends MyHeap<T> {
    
    JVMHeap(int size) {
        super(size);
    }

    JVMHeap(int size, double factor) {
        super(size, factor);
    }

    JVMHeap(List<T> list) {
        super(list);
    }

    /*@ public normal_behavior
      @ requires objectId != null
      @ ensures (\forall int i; 0 <= i && i < objectId.length;
                (\exists int j; 0 <= j && j < elementData.length; elementData[j].id == objectId[i] ==> elementData[j].getReferenced() == false)
                )
      @ assignable elementData;
      @*/
    public void setUnreferenceId(List<Integer> objectId) {
        // fixed:the JML specification "requires objectId != null" so add the first step in this function
        // way to find: read the JML specification
        if (objectId == null) {
            return;
        }
        for (int id : objectId) {
            for (int index = 0;index < this.getSize();index++) {
                MyObject mo = (MyObject)this.getElementData()[index];
                if (mo.getId() == id) {
                    mo.setReferenced(false);
                    this.setElementData(index, mo);
                }
            }
        }
    }

    /*@ public normal_behavior
      @ ensures (\forall int i; 0 <= i && i < elementData.length; elementData[i].getReferenced() == true)
      @ assignable elementData;
      @*/
    public void removeUnreference() {
        Object[] oldObject = getElementData().clone();
        this.clear(); 
        int newSize = 0;
        //fixed: getsize() -> oldObject.length
        //way to find: test for remove
        for (int i = 0;i < oldObject.length;i++) {
            MyObject mo = (MyObject) oldObject[i];
            if (mo.getReferenced() == true) {
                this.add(mo);
                newSize++;
            }
        }
        this.setSize(newSize);
    }
}
