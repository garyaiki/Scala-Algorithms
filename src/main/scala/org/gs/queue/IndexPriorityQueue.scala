/*
 * Common code for IndexMinPQ, IndexMaxPQ
 * @see http://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
 * @see http://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html
 */
package org.gs.queue

import collection.mutable.ArrayBuffer
import math.Ordering
import scala.annotation.tailrec
import scala.reflect.ClassTag

/**
 *
 * @author Gary Struthers
 *
 * @param <A> keys are generic, ClassTag retains its type at runtime
 * @param nMax maximum number of elements
 */
abstract class IndexPriorityQueue[A: ClassTag](nMax: Int) {
  require(nMax >= 0, s"nMax:$nMax can't be negative")
  private var n = 0
  private val keys = new Array[A](nMax + 1)
  private val pq = new Array[Int](keys.size)
  private val qp = Array.fill[Int](keys.size)(-1)

  def isEmpty(): Boolean = n == 0

  def less(a: Int, b: Int)(implicit ord: Ordering[A]): Boolean = ord.lt(keys(pq(a)), keys(pq(b)))

  def greater(a: Int, b: Int)(implicit ord: Ordering[A]):Boolean =  ord.gt(keys(pq(a)), keys(pq(b)))

  private def rangeGuard(x: Int): Boolean = {
    x match {
      case x if 0 to nMax contains x => true
      case _ => false
    }
  }

  def contains(i: Int): Boolean  = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    qp(i) != -1
  }

  def size(): Int = n

  private def exchange(i: Int, j: Int): Unit = {
    val swap = pq(i)
    pq.update(i, pq(j))
    pq.update(j, swap)
    qp(pq(j)) = j
    qp(pq(i)) = i
  }

  private def swim(k: Int, cmp: (Int, Int) => Boolean): Unit = {
    @tailrec
    def loop(i: Int, j: Int) {
      if (i > 1 && cmp(j, i)) {
        exchange(i, j)
        loop(j, j / (2))
      }
    }
    loop(k, k./(2))
  }

  private def sink(k: Int, cmp: (Int, Int) => Boolean): Unit = {
    @tailrec
    def loop(k: Int): Unit = {
      def calcJ(): Int = {
        val j = k * 2
        val j1 = j + 1
        if ((j1 <= n) && cmp(j, j1)) j1 else j
      }
      val j = calcJ
      if (j <= n && cmp(k, j)) {
        exchange(k, j)
        loop(j)
      }
    }
    loop(k)
  }

  def insert(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(!contains(i), s"index:$i is already in the priority queue")
    n += 1
    qp(i) = n
    pq(n) = i
    keys(i) = key
    swim(n, cmp)
  }

  def index(): Int = {
    require(n > 0, s"n:$n priority queue underflow")
    pq(1)
  }

  def topKey(): A = {
    require(n > 0, s"n:$n priority queue underflow")
    keys(pq(1))
  }

  def delTop(cmp: (Int, Int) => Boolean): Int = {
    require(n > 0, s"n:$n priority queue underflow")
    val top = pq(1)
    exchange(1, n)
    n -= 1
    sink(1, cmp)
    qp(top) = -1
    pq(n + 1) = -1
    top
  }

  def keyOf(i: Int): A = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    keys(i)
  }

  def changeKey(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    keys(i) = key
    swim(qp(i), cmp)
    sink(qp(i), cmp)
  }

  def decreaseKey(i: Int, key: A, cmp: (Int, Int) => Boolean)(implicit ord: Ordering[A]): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    require(ord.compare(keys(i), key) > 0,
        s"Calling decreaseKey() with i:$i, key:$key would not strictly decrease the key")
    keys(i) = key
    swim(qp(i), cmp)
  }

  def increaseKey(i: Int, key: A, cmp: (Int, Int) => Boolean)(implicit ord: Ordering[A]): Unit = {
    val r = ord.compare(keys(i), key)
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    require(r < 0,
        s"Calling increaseKey() with i:$i, key:$key would not strictly increase the key")
    keys(i) = key
    sink(qp(i), cmp)
  }

  def delete(i: Int, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    val index = qp(i)
    exchange(index, n)
    n -= 1
    swim(index, cmp)
    sink(index, cmp)
    keys(i) = null.asInstanceOf[A]
    qp(i) = -1
  }

  override def toString(): String = {
    val sb = new StringBuilder()
    val size = pq.size
    for {
      i <- 0 until size
      if (i != 0)
    } {
      val key = keys(pq(i))
      if(key != null)
      sb.append(s" $key")
    }
    sb.toString.trim()
  }

  def getKeys(): IndexedSeq[A] = for(i <- 1 until nMax ) yield keys(pq(i))

  def checkHeap(cmp: (Int, Int) => Boolean): Boolean = {
    def loop(k: Int): Boolean = {
      if (k > n) true else {
        val left = 2 * k
        val right = 2 * k + 1
        if ((left <= n && cmp(k, left)) || (right <= n && cmp(k, right))) false else {
          loop(left) && loop(right)
        }
      }
    }
    loop(1)
  }
}
