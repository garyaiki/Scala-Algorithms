/** ScalaTest, JUnit tests for Merge
  * @see http://algs4.cs.princeton.edu/51radix/shells.txt
  */
package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class MergeSuite extends FlatSpec {
  behavior of "a Merge"
  
  it should "sort strings" in new WordArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/51radix/shells.txt")
    val strings = managedResource.loan(readFileToArray).toList
    val sorted = Merge.msort[String](strings)
    assert(sorted(0) === "are")
    assert(sorted(strings.length - 1) === "the")
  }
}