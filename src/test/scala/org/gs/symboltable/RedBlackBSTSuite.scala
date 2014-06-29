package org.gs.symboltable
/*
 * @see http://algs4.cs.princeton.edu/33balanced/tinyST.txt
 * @see http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester
import math.Ordering
/*
 * @author Gary Struthers
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

  }

  it should "get invalid key" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  it should "isEmpty" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  it should "debug put contains" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    var item = testInput(0)
    ost.put(item._1, item._2)
    item = testInput(1)
    ost.put(item._1, item._2)
    item = testInput(2) //!! crash
    ost.put(item._1, item._2)
    item = testInput(3)
    ost.put(item._1, item._2)
    item = testInput(4)
    ost.put(item._1, item._2)
    item = testInput(5)
    ost.put(item._1, item._2)
    item = testInput(6)
    ost.put(item._1, item._2)
    item = testInput(7)
    ost.put(item._1, item._2)
    item = testInput(8)
    ost.put(item._1, item._2)
    item = testInput(9)
    ost.put(item._1, item._2)
    item = testInput(10)
    ost.put(item._1, item._2)
    item = testInput(11)
    ost.put(item._1, item._2)
    item = testInput(12)
    ost.put(item._1, item._2)
    item = testInput(13)
    ost.put(item._1, item._2)
    item = testInput(14)
    ost.put(item._1, item._2)
    item = testInput(15)
    ost.put(item._1, item._2)
    item = testInput(16)
    ost.put(item._1, item._2)
    item = testInput(17)
    ost.put(item._1, item._2)
    item = testInput(18)
    ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")
  }

  it should "contains" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    val str = ost.inorderTreeWalk()
    assert(ost.isBalanced, "Not balanced")
  }

  it should "size" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  it should "size inclusive range" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size('A', 'U') === testInput.length - 1)
  }
  
  it should "floor" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.floor('S') === 'S')
    assert(ost.floor('Z') === 'X')
  }

  it should "ceiling" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.ceiling('S') === 'S')
    assert(ost.ceiling('F') === 'H')
    assert(ost.ceiling('D') === 'E')
  }

  it should "select" in {
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
  it should "rank" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.rank('S') === 15)
    assert(ost.rank('Z') === ost.size())
  }

  it should "keys" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys()
    val str = keys.mkString
    assert(str === "ABCEHIJKLMNOPQRSTUX")
  }

  it should "min" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.min === 'A')
  }

  it should "max" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.max === 'X')
  }

  it should "delete" in {
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

  it should "deleteMin" in {
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
  it should "deleteMax" in {
    val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    ost.deleteMax
    assert(ost.get('X') === None)
    var keys = ost.keys()
    var str = keys.mkString
    assert(str === "ABCEHIJKLMNOPQRSTU")
    ost.deleteMax
    assert(ost.get('U') === None)
    keys = ost.keys()
    str = keys.mkString
    assert(str === "ABCEHIJKLMNOPQRST")
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
     //   assert(ost.is23, "Not a 2-3 tree")
    assert(ost.isBalanced, "Not balanced")

  }

}