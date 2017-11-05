/** ScalaTest for Quick3String
  * @see https://algs4.cs.princeton.edu/51radix/Quick3string.java.html
  */
package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class Quick3StringSuite extends FlatSpec {
  behavior of "a Quick3String"
  
  it should "sort strings" in new WordArrayBuilder {
    val managedResource = readURI("https://algs4.cs.princeton.edu/51radix/shells.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    Quick3String.sort(strings)
    assert(strings(0) === "are")
    assert(strings(strings.length - 1) === "the")
  }
}
