/** ScalaTest, JUnit tests for Knuth, Morris, Pratt
  * @see http://algs4.cs.princeton.edu/53substring/KMP.java.html
  */
package org.gs.search

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class KMPSuite extends FlatSpec {
  behavior of "a KMP"

  it should "find the offset in the text where a  pattern is found" in {
    val pattern = "abracadabra".toCharArray
    val kmp = new KMP(pattern)
    val text = "abacadabrabracabracadabrabrabracad".toCharArray
    val offset = kmp.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === 14, s"wrong offset:$offset")
  }
}