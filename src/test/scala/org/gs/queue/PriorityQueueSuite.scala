package org.gs.queue

import scala.collection.mutable.ArrayBuffer
import scala.math.Ordering._

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PriorityQueueSuite extends FunSuite {
  test("max") {
    val pq = new MaxPQ(new ArrayBuffer[Char](10).+=('!'))

    pq.insert('P')
    pq.insert('H')
    pq.insert('I')
    pq.insert('O')
    pq.insert('E')
    pq.insert('G')
    pq.insert('A')
    pq.insert('R')
    pq.insert('T')
    pq.insert('N')
    assert(pq.pop() === Some('T'))
  }

  test("min") {
    val pq = new MinPQ(new ArrayBuffer[Char](10).+=('!'))

    pq.insert('P')
    pq.insert('H')
    pq.insert('I')
    pq.insert('O')
    pq.insert('E')
    pq.insert('G')
    pq.insert('A')
    pq.insert('R')
    pq.insert('T')
    pq.insert('N')
    assert(pq.pop() === Some('A'))
  }

  test("min empty") {
    val pq = new MinPQ(new ArrayBuffer[Char](10).+=('!'))
    val r = pq.pop()
    assert(r === None)
  }
}