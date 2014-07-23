package org.gs.symboltable

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester

@RunWith(classOf[JUnitRunner])
class SeparateChainingHashSTSuite extends FlatSpec with BeforeAndAfter with PrivateMethodTester {
  var testInput: ArrayBuffer[(Char, Int)] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[(Char, Int)](('K', 23), ('R', 84), ('A', 42), ('T', 11), ('E', 32), ('L', 74),
      ('P', 3), ('U', 45), ('I', 27), ('M', 63), ('Q', 52), ('C', 15), ('X', 30), ('O', 81), ('S', 0))
  }

  it should "put and get 1 key value" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    val item = testInput(0)
    ost.put(item._1, item._2)
    val value = ost.get('K')
    assert(value === Some(23))
  }

  it should "put and get 2 key values" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    var item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.get('K') === Some(23))
    item = testInput(1)
    ost.put(item._1, item._2)
    assert(ost.get('R') === Some(84))
    assert(ost.get('K') === Some(23))
  }

  it should "confirm a key isn't in the table" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  it should "when table isEmpty and when it isn't" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  it should "confirm whan a key is contained in the table" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
  }

  it should "find the table's size" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  it should "find all keys" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys()
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPQRSTUX")
  }

  it should "delete a key" in {
    val ost = new SeparateChainingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    ost.delete('Q')
    val keys = ost.keys()
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPRSTUX")
  }

  it should "resize table when needed" in {
    val ost = new SeparateChainingHashST[Char, Int](testInput.length / 10)
    assert(ost.size === 0)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.size)
  }
}