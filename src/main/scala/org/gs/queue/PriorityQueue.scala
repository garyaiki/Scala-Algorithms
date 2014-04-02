package org.gs.queue

import collection.mutable.ArrayBuffer
import math.Ordering

class PriorityQueue[T](bHeap: ArrayBuffer[T]) {
  def isEmpty(): Boolean = bHeap.length < 2
  def less(a: Int, b: Int)(implicit ord: Ordering[T]) = ord.lt(bHeap(a), bHeap(b))
  def greater(a: Int, b: Int)(implicit ord: Ordering[T]) = ord.gt(bHeap(a), bHeap(b))

  def exchange(child: Int, parent: Int) {
    val parentVal = bHeap(parent)
    val childVal = bHeap(child)
    bHeap.update(parent, childVal)
    bHeap.update(child, parentVal)
  }
  def swim(k: Int, cmp: (Int, Int) => Boolean) {
    var i = k
    var j = i./(2)
    while (i > 1 && cmp(j, i)) {
      exchange(i, j)
      i = j
      j = i./(2)
    }
  }

  def sink(k: Int, cmp: (Int, Int) => Boolean) {
    val n = bHeap.length
    var i = k
    var firstChild = i.*(2)
    while (firstChild < n) {
      if (cmp(firstChild, firstChild + 1)) firstChild = firstChild + 1
      if (cmp(i, firstChild)) {
        exchange(i, firstChild)
        i = firstChild
        firstChild = i.*(2)
      }
    }
  }

  def insert(key: T, cmp: (Int, Int) => Boolean): Unit = { // Cost at most 1 + lg N compares
    bHeap += key
    println(bHeap.mkString)
    swim(bHeap.length - 1, cmp)
    println(bHeap.mkString)
  }
  def pop(cmp: (Int, Int) => Boolean): Option[T] = {
    println(bHeap.mkString)
    if (isEmpty) None else {
      val root = bHeap(1)
      val lastItem = bHeap.length - 1
      exchange(1, lastItem)
      sink(1, cmp)
      bHeap.remove(lastItem)
      Some(root)
    }
  }
}
// parent of k = k/2
// children of k = 2k until 4k
// root i = 1 level 1 = 2,3 level 2 = 4..7 level 3 = 8..15 level 4 = 16..31 level 5 = 32..63

class MinPQ[T](bHeap: ArrayBuffer[T]) extends PriorityQueue(bHeap) {
  def insert(key: T)(implicit ord: Ordering[T]): Unit = { // Cost at most 1 + lg N compares
    insert(key, greater)
  }
  def pop()(implicit ord: Ordering[T]): Option[T] = {
    pop(greater)
  }
}

class MaxPQ[T](bHeap: ArrayBuffer[T]) extends PriorityQueue(bHeap) {
  def insert(key: T)(implicit ord: Ordering[T]): Unit = { // Cost at most 1 + lg N compares
    insert(key, less)
  }
  def pop()(implicit ord: Ordering[T]): Option[T] = {
    pop(less)
  }
}

object PriorityQueue {

}