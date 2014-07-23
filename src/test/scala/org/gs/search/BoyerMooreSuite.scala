/** ScalaTest, JUnit tests for Boyer Moore
  * @see http://algs4.cs.princeton.edu/53substring/BoyerMoore.java.html
  */
package org.gs.search

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class BoyerMooreSuite extends FlatSpec {
  behavior of "a BoyerMoore"

  val text = "abacadabrabracabracadabrabrabracad".toCharArray

  it should "find a pattern when it's in the text" in {
    val pattern = "abracadabra".toCharArray
    val bm = new BoyerMoore(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === 14, s"wrong offset:$offset")
  }

  it should "confirm a pattern is not in the text" in {
    val pattern = "bcara".toCharArray
    val bm = new BoyerMoore(pattern)
    val offset = bm.search(text)
    assert(offset === text.length, "false pattern found")
  }
  
  
  it should "find a pattern when it's at the end of the text" in {
    val pattern = "rabrabracad".toCharArray
    val bm = new BoyerMoore(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === text.length - pattern.length, s"wrong offset:$offset")
  }
    
  it should "find a pattern when it's at the start of the text" in {
    val pattern = "abacad".toCharArray
    val bm = new BoyerMoore(pattern)
    val offset = bm.search(text)
    assert(offset < text.length, "pattern not found")
    assert(offset === 0, s"wrong offset:$offset")
  }
}