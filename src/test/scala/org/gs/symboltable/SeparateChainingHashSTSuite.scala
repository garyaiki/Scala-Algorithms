package org.gs.symboltable

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester

@RunWith(classOf[JUnitRunner])
class SeparateChainingHashSTSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {
  var testInput: ArrayBuffer[(Char, Int)] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[(Char, Int)](('K', 23), ('R', 84), ('A', 42), ('T', 11), ('E', 32), ('L', 74),
      ('P', 3), ('U', 45), ('I', 27), ('M', 63), ('Q', 52), ('C', 15), ('X', 30), ('O', 81), ('S', 0))
  }

  test("put and get 1 key value") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    val item = testInput(0)
    ost.put(item._1, item._2)
    val value = ost.get('K')
    assert(value === Some(23))
  }

  test("put and get 2 key values") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    var item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.get('K') === Some(23))
    item = testInput(1)
    ost.put(item._1, item._2)
    assert(ost.get('R') === Some(84))
    assert(ost.get('K') === Some(23))
  }

  test("get invalid key") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  test("isEmpty") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  test("contains") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
  }

  test("size") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  test("keys") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys()
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPQRSTUX")
  }

  test("delete") {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    ost.delete('Q')
    val keys = ost.keys()
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPRSTUX")
  }

  test("double size") {
    val ost = new SeparateChainingHashST[Char, Int](testInput.length / 10)
    val oldSize = ost.st.size
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.st.size === (oldSize * 2))
  }
}