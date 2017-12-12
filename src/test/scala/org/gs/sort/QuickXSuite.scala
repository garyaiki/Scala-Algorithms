package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.scalatest.FlatSpec

/** ScalaTest for QuickX
  * @see [[https://algs4.cs.princeton.edu/23quicksort/QuickX.java.html]]
  *
  * @author Gary Struthers
  */
class QuickXSuite extends FlatSpec {
  behavior of "a QuickX"
  
  it should "sort strings" in new WordArrayBuilder {
    val managedResource = readURI("https://algs4.cs.princeton.edu/51radix/shells.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    val sorter = new QuickX[String]
    val sorted = sorter.sort(strings)
    assert(sorted(0) === "are")
    assert(sorted(strings.length - 1) === "the")
  }
}
