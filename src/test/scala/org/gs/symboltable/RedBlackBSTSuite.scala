package org.gs.symboltable
/** @see http://algs4.cs.princeton.edu/33balanced/tinyST.txt
  * @see http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
  */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester
import math.Ordering

/** @author Gary Struthers
  */
@RunWith(classOf[JUnitRunner])
class RedBlackBSTSuite extends FlatSpec with BeforeAndAfter with PrivateMethodTester {
  var testInput: ArrayBuffer[(Char, Int)] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[(Char, Int)](('K', 23), ('R', 84), ('A', 42), ('T', 11), ('E', 32), ('L', 74),
      ('P', 3), ('U', 45), ('I', 27), ('M', 63), ('Q', 52), ('C', 15), ('X', 30), ('O', 81),
      ('S', 0), ('B', 77), ('N', 66), ('H', 66), ('J', 55))
  }

  behavior of "a RedBlackSymbolTable"
  it should "put and get 1 key value" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)

    val item = testInput(0)
    ost.put(item._1, item._2)
    val value = ost.get('K')
    assert(value === Some(23))
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

  it should "put and get 2 key values" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    var item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.get('K') === Some(23))
    item = testInput(1)
    ost.put(item._1, item._2)
    assert(ost.get('R') === Some(84))
    assert(ost.get('K') === Some(23))
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

  it should "confirm when a key isn't present" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  it should "confirm when a kv is in the tree isEmpty returns false" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  it should "validate after a series of puts" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

  it should "confirm correct number of entries" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  it should "confirm correct number of entries in an inclusive range from low to high" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size('A', 'U') === testInput.length - 1)
  }

  it should "find a key's floor, the largest key less than or equal to a given key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.floor('S') === 'S')
    assert(ost.floor('Z') === 'X')
  }

  it should "find a key's ceiling, the smallest key greater than or equal to a given key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.ceiling('S') === 'S')
    assert(ost.ceiling('F') === 'H')
    assert(ost.ceiling('D') === 'E')
  }

  it should "select the key of a given rank" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    var k = ost.select(0)
    k = ost.select(1)
    k = ost.select(2)
    k = ost.select(3)
    k = ost.select(4)
    k = ost.select(5)
    k = ost.select(ost.size - 1)
    assert(k === Some('X'))
  }
  
  it should "find the rank of a key, the number keys less than the key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.rank('S') === 15)
    assert(ost.rank('Z') === ost.size())
  }

  it should "find all the keys" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys()
    val str = keys.mkString
    assert(str === "ABCEHIJKLMNOPQRSTUX")
  }

  it should "find the min, smallest key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.min === 'A')
  }

  it should "find the max, largest key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.max === 'X')
  }

  it should "delete the given key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    ost.delete('S')
    assert(ost.contains('S') === false)
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    //  assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

  it should "delete the minimum key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.get('M') === Some(63))
    ost.deleteMin
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
    assert(ost.get('A') === None)
    assert(ost.get('P') === Some(3))
    val keys = ost.keys()
    val str = keys.mkString
    assert(str === "BCEHIJKLMNOPQRSTUX")
  }
  
  it should "delete the maximum key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    ost.deleteMax
    assert(ost.get('X') === None)
    assert(ost.keys().mkString === "ABCEHIJKLMNOPQRSTU")
    ost.deleteMax
    assert(ost.get('U') === None)
    assert(ost.keys().mkString === "ABCEHIJKLMNOPQRST")
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    //  assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

}
