/*
 * Test data strategy for MyPerson.
 *
 * Generated by JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178), 2020-05-23 16:24 +0800.
 * (do not modify this comment, it is used by JMLUnitNG clean-up routines)
 */

import org.jmlspecs.jmlunitng.iterator.ObjectArrayIterator;
import org.jmlspecs.jmlunitng.iterator.RepeatedAccessIterator;

/**
 * Test data strategy for MyPerson. Provides
 * test values for parameter "int friendValue" 
 * of method "void addRelation(Person, int)". 
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2020-05-23 16:24 +0800
 */
public /*@ nullable_by_default */ class MyPerson_addRelation__Person_friend__int_friendValue__0__friendValue
  extends MyPerson_ClassStrategy_int {
  /**
   * @return local-scope values for parameter 
   *  "int friendValue".
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add local-scope int values or generators here */ });
  }
}
