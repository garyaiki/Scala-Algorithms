/** ScalaTest, JUnit tests for Ternary Search Trie
  * @see http://algs4.cs.princeton.edu/52trie/TST.java.html
  */
package org.gs.search

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.gs.fixtures.WordArrayBuilder

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class TSTSuite extends FlatSpec {
  behavior of "a TST"

  it should "sort fixed length strings and return their stored values" in new WordArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/52trie/shellsST.txt")
    val strings = managedResource.loan(readFileToArray).toArray
    val tst = new TST[Int]()
    strings.zipWithIndex.foreach(x => tst.put(x._1, x._2))
    assert(tst.keys.diff(Array[String]("by", "sea", "sells", "she", "shells", "shore", "the")) === List())

    val values = for (key <- tst.keys) yield tst.get(key) match {
      case None =>
      case Some(x) => x
    }
    assert(values.diff(Array[Int](4,6,1,0,3,7,5)) === List())
  }
}