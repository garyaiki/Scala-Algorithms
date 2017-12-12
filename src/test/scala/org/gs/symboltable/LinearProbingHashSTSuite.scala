package org.gs.symboltable

import org.scalatest.{BeforeAndAfter, FlatSpec, PrivateMethodTester}
import scala.collection.mutable.ArrayBuffer

class LinearProbingHashSTSuite extends FlatSpec with BeforeAndAfter with PrivateMethodTester {
  var testInput: ArrayBuffer[(Char, Int)] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[(Char, Int)](('K', 23), ('R', 84), ('A', 42), ('T', 11), ('E', 32), ('L', 74),
      ('P', 3), ('U', 45), ('I', 27), ('M', 63), ('Q', 52), ('C', 15), ('X', 30), ('O', 81), ('S', 0))
  }

  it should "put and get 1 key value" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    val item = testInput(0)
    ost.put(item._1, item._2)
    val value = ost.get('K')
    assert(value === Some(23))
  }

  it should "put and get 2 key values" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    var item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.get('K') === Some(23))
    item = testInput(1)
    ost.put(item._1, item._2)
    assert(ost.get('R') === Some(84))
    assert(ost.get('K') === Some(23))
  }

  it should "confirm when a key is not in the table" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    val item = testInput(0)
    val value = ost.get('K')
    assert(value === None)
  }

  it should "confirm when the table is empty and when it isn't" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    assert(ost.isEmpty === true)
    val item = testInput(0)
    ost.put(item._1, item._2)
    assert(ost.isEmpty === false)
  }

  it should "find keys the table contains" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.contains('S') === true)
    assert(ost.contains('z') === false)
  }

  it should "find the size of the table" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }

  it should "find all keys" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    val keys = ost.keys
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPQRSTUX")
  }

  it should "delete a key" in {
    val ost = new LinearProbingHashST[Char, Int](50)
    for (item <- testInput) ost.put(item._1, item._2)
    ost.delete('Q')
    val keys = ost.keys
    keys.mkString
    assert(keys.mkString === "ACEIKLMOPRSTUX")
  }

  it should "resize table when needed" in {
    val ost = new LinearProbingHashST[Char, Int](testInput.length)
    assert(ost.size === 0)

    for (item <- testInput) ost.put(item._1, item._2)
    assert(ost.size === testInput.length)
  }
}
