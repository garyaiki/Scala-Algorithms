/** ScalaTest for LSD
  * @see https://algs4.cs.princeton.edu/51radix/LSD.java.html
  */
package org.gs.sort

import org.gs.fixtures.WordArrayBuilder
import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class LSDSuite extends FlatSpec {
  behavior of "a LSD"
  
  it should "sort fixed length strings" in new WordArrayBuilder {
    val managedResource = readURI("https://algs4.cs.princeton.edu/51radix/words3.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    val N = strings.length
    val W = strings(0).length
    assert(strings.forall(_.length == W) === true)
    LSD.sort(strings, W)
    assert(strings(0) === "all")
    assert(strings(N - 1) === "zoo")
  }
}
