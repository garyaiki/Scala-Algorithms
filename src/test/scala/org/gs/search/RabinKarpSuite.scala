/** ScalaTest, JUnit tests for Boyer Moore
  * @see https://algs4.cs.princeton.edu/53substring/RabinKarp.java.html
  */
package org.gs.search

import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class RabinKarpSuite extends FlatSpec {
  behavior of "a RabinKarp"

  val text = "abacadabrabracabracadabrabrabracad"

  it should "find existing pattern in the text" in {
    val pattern = "abracadabra"
    val bm = new RabinKarp(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === 14, s"wrong offset:$offset")
  }

  it should "fail to find non-existing pattern in the text" in {
    val pattern = "bcara"
    val bm = new RabinKarp(pattern)
    val offset = bm.search(text)
    assert(offset === text.length, "false pattern found")
  }
  
  it should "find existing pattern at the end of the text" in {
    val pattern = "rabrabracad"
    val bm = new RabinKarp(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === text.length - pattern.length, s"wrong offset:$offset")
  }
    
  it should "find existing pattern at the start of the text" in {
    val pattern = "abacad"
    val bm = new RabinKarp(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === 0, s"wrong offset:$offset")
  }
}
