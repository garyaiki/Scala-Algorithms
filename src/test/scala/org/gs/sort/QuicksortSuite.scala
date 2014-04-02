package org.gs.sort

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.ArrayBuffer
import org.scalatest.PrivateMethodTester

@RunWith(classOf[JUnitRunner])
class QuicksortSuite extends FunSuite with BeforeAndAfter with PrivateMethodTester {

  var testInput: ArrayBuffer[Char] = _
  var lo = 0
  before {
    testInput = ArrayBuffer[Char]('K','R','A','T','E','L','E','P','U','I','M','Q','C','X','O','S')
  }
  test("private partition unshuffled input") {
    assert(testInput(lo) === 'K')
    val qs = new Quicksort[Char]
    val setInput = PrivateMethod[Unit]('setInput)
    qs invokePrivate setInput(testInput)
    val partition = PrivateMethod[Int]('partition)
    val j = qs invokePrivate partition(lo, testInput.length - 1)
    assert(j === 5)
  }

  test("protected sort shuffled input") {
    val qs = new Quicksort[Char]()
    val shuffleArrayBuffer = PrivateMethod[Unit]('shuffleArrayBuffer)
    qs invokePrivate shuffleArrayBuffer(testInput)
    val setInput = PrivateMethod[Unit]('setInput)
    qs invokePrivate setInput(testInput)
    val sort = PrivateMethod[Unit]('sort)
    qs invokePrivate sort(lo, testInput.length - 1)
    assert(testInput(lo) === 'A')
    assert(testInput(5) === 'K')
    assert(testInput(testInput.length - 1) === 'X')
  }

  test("public sort shuffled input") {
    val qs = new Quicksort[Char]()
    val sorted = qs.sort(testInput)
    assert(sorted(lo) === 'A')
    assert(sorted(5) === 'K')
    assert(sorted(testInput.length - 1) === 'X')
    assert( sorted eq testInput)
  }
}