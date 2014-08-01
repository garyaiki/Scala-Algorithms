/** ScalaTest, JUnit tests for LSD
  * @see http://algs4.cs.princeton.edu/51radix/LSD.java.html
  */
package org.gs.sort

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.gs.fixtures.WordArrayBuilder
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class LSDSuite extends FlatSpec {
  behavior of "a LSD"
  
  it should "sort fixed length strings" in new WordArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/51radix/words3.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    val N = strings.length
    val W = strings(0).length
    assert(strings.forall(_.length == W) === true)
    LSD.sort(strings, W)
    assert(strings(0) === "all")
    assert(strings(N - 1) === "zoo")
  }
}
