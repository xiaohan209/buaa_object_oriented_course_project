/*
 * Test data strategy for MyNetwork.
 *
 * Generated by JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178), 2020-05-23 16:24 +0800.
 * (do not modify this comment, it is used by JMLUnitNG clean-up routines)
 */

import org.jmlspecs.jmlunitng.iterator.ObjectArrayIterator;
import org.jmlspecs.jmlunitng.iterator.RepeatedAccessIterator;

/**
 * Test data strategy for MyNetwork. Provides
 * test values for parameter "int value" 
 * of method "void borrowFrom(int, int, int)". 
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2020-05-23 16:24 +0800
 */
public /*@ nullable_by_default */ class MyNetwork_borrowFrom__int_id1__int_id2__int_value__0__value
  extends MyNetwork_ClassStrategy_int {
  /**
   * @return local-scope values for parameter 
   *  "int value".
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add local-scope int values or generators here */ });
  }
}
