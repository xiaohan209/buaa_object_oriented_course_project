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
 * class-scope test values for type Group.
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2020-05-23 16:24 +0800
 */
public /*@ nullable_by_default */ class MyNetwork_ClassStrategy_Group 
  extends PackageStrategy_Group {
  /**
   * @return class-scope values for type Group.
   */
  public RepeatedAccessIterator<?> classValues() {
    return new ObjectArrayIterator<Object>
    (new Object[] 
     { /* add class-scope Group values or generators here */ });
  }

  /**
   * The use of reflection can be controlled here for  
   * parameters of type Group
   * in this class by changing the parameters to <code>setReflective</code>
   * and <code>setMaxRecursionDepth<code>. 
   * In addition, the data generators used can be changed by adding 
   * additional data class lines, or by removing some of the automatically 
   * generated ones. Note that lower-level strategies can override any 
   * behavior specified here by calling the same control methods in 
   * their own constructors.
   *
   * @see NonPrimitiveStrategy#addDataClass(Class<?>)
   * @see NonPrimitiveStrategy#clearDataClasses()
   * @see ObjectStrategy#setReflective(boolean)
   * @see ObjectStrategy#setMaxRecursionDepth(int)
   */
  public MyNetwork_ClassStrategy_Group() {
    super();
    setReflective(false);
    // uncomment to control the maximum reflective instantiation
    // recursion depth, 0 by default
    // setMaxRecursionDepth(0);
  }
}
