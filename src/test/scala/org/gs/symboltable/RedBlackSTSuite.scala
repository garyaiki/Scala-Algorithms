package org.gs.symboltable

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester
import math.Ordering

@RunWith(classOf[JUnitRunner])
class RedBlackSymbolTableSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  var testInput: ArrayBuffer[(Char, Int)] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[(Char, Int)](('K', 23), ('R', 84), ('A', 42), ('T', 11), ('E', 32), ('L', 74),
      ('P', 3), ('U', 45), ('I', 27), ('M', 63), ('Q', 52), ('C', 15), ('X', 30), ('O', 81),
      ('S', 0), ('B', 77), ('N', 66), ('H', 66), ('J', 55))
  }

  test("put and get 1 key value") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)

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

  test("put and get 2 key values") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    var item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.get('K') === Some(23))
    item = testInput(1)
    ost.put(item._1, item._2)
    assert(ost.get('R') === Some(84))
    assert(ost.get('K') === Some(23))

  }

  test("get invalid key") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  test("isEmpty") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  test("debug put contains") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
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

  test("contains") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
    assert(ost.isBST, "Not in Symmetric order")
    assert(ost.isSizeConsistent, "Subtree counts not consistent")
    assert(ost.isRankConsistent, "Ranks not consistent")
    assert(ost.is23, "Not a 2-3 tree")
    val str = ost.inorderTreeWalk
    assert(ost.isBalanced, "Not balanced")
  }

  test("size") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  test("size inclusive range") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size('A', 'U') === testInput.length - 1)
  }
  test("floor") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.floor('S') === 'S')
    assert(ost.floor('Z') === 'X')
  }

  test("ceiling") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.ceiling('S') === 'S')
    assert(ost.ceiling('F') === 'H')
    assert(ost.ceiling('D') === 'E')
  }

  test("select") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
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
  test("rank") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.rank('S') === 15)
    assert(ost.rank('Z') === ost.size())
  }

  test("keys") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys()
    val str = keys.mkString
    assert(str === "ABCEHIJKLMNOPQRSTUX")
  }

  test("min") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.min === 'A')
  }

  test("max") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.max === 'X')
  }

  test("delete") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
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

  test("deleteMin") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
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
  test("deleteMax") {
    val ost = new RedBlackSymbolTable[Char, Int]()(Ordering.Char)
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