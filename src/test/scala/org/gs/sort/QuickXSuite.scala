/**
 * ScalaTest, JUnit tests for QuickX
 * @see http://algs4.cs.princeton.edu/23quicksort/QuickX.java.html
 */
package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class QuickXSuite extends FlatSpec {
  behavior of "a QuickX"
  
  it should "sort strings" in new WordArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/51radix/shells.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    val sorter = new QuickX[String]
    val sorted = sorter.sort(strings)
    assert(sorted(0) === "are")
    assert(sorted(strings.length - 1) === "the")
  }
}