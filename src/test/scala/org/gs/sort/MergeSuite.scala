/** ScalaTest for Merge
  * @see https://algs4.cs.princeton.edu/51radix/shells.txt
  */
package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class MergeSuite extends FlatSpec {
  behavior of "a Merge"
  
  it should "sort strings" in new WordArrayBuilder {
    val managedResource = readURI("https://algs4.cs.princeton.edu/51radix/shells.txt")
    val strings = managedResource.loan(readFileToArray).toList
    val sorted = Merge.msort[String](strings)
    assert(sorted(0) === "are")
    assert(sorted(strings.length - 1) === "the")
  }
}
