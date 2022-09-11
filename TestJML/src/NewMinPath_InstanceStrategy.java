/*
 * Test data strategy for NewMinPath.
 *
 * Generated by JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178), 2020-05-23 16:24 +0800.
 * (do not modify this comment, it is used by JMLUnitNG clean-up routines)
 */

import java.util.LinkedList;
import java.util.List;

import org.jmlspecs.jmlunitng.iterator.InstantiationIterator;
import org.jmlspecs.jmlunitng.iterator.IteratorAdapter;
import org.jmlspecs.jmlunitng.iterator.NonNullMultiIterator;
import org.jmlspecs.jmlunitng.iterator.ObjectArrayIterator;
import org.jmlspecs.jmlunitng.iterator.RepeatedAccessIterator;
import org.jmlspecs.jmlunitng.strategy.ObjectStrategy;

/**
 * Test data strategy for NewMinPath. Provides
 * instances of NewMinPath for testing, using
 * parameters from constructor tests.
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2020-05-23 16:24 +0800
 */
public /*@ nullable_by_default */ class NewMinPath_InstanceStrategy extends ObjectStrategy {
  /**
   * @return local-scope instances of NewMinPath.
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add NewMinPath values or generators here */ });
  }

  /**
   * @return default instances of NewMinPath, generated
   *  using constructor test parameters.
   */ 
  public RepeatedAccessIterator<NewMinPath> defaultValues() {
    final List<RepeatedAccessIterator<NewMinPath>> iters = 
      new LinkedList<RepeatedAccessIterator<NewMinPath>>();

    // an instantiation iterator for the default constructor
    // (if there isn't one, it will fail silently)
    iters.add(new InstantiationIterator<NewMinPath>
      (NewMinPath.class, 
       new Class<?>[0], 
       new ObjectArrayIterator<Object[]>(new Object[][]{{}})));

    // parameters for method NewMinPath(int, int)
    iters.add(new InstantiationIterator<NewMinPath>
      (NewMinPath.class, 
       new Class<?>[]
       {int.class, 
        int.class},
       NewMinPath_JML_Test.p_NewMinPath__int_minPath__int_point__0().wrapped()));

    return new NonNullMultiIterator<NewMinPath>(iters);
  }

  /**
   * Constructor. The boolean parameter to <code>setReflective</code>
   * determines whether or not reflection will be used to generate
   * test objects, and the int parameter to <code>setMaxRecursionDepth</code>
   * determines how many levels reflective generation of self-referential classes
   * will recurse.
   *
   * @see ObjectStrategy#setReflective(boolean)
   * @see ObjectStrategy#setMaxRecursionDepth(int)
   */
  public NewMinPath_InstanceStrategy() {
    super(NewMinPath.class);
    setReflective(false);
    // uncomment to control the maximum reflective instantiation
    // recursion depth, 0 by default
    // setMaxRecursionDepth(0);
  }
}
