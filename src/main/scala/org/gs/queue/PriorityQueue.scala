/*
 * Common code for MaxPQ, MinPQ
 * @see http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
 * @see http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
 */
package org.gs.queue

import collection.mutable.ArrayBuffer
import math.Ordering
import scala.annotation.tailrec

/**
 *
 * @author Gary Struthers
 *
 * @param <A> keys are generic and ordered
 * @param pq priority wueue array
 */
abstract class PriorityQueue[A](pq: ArrayBuffer[A]) {
  if (pq.isEmpty) pq.append(null.asInstanceOf[A]) // don't use index 0
  else pq(0) = null.asInstanceOf[A]

  private var n = 0

  private[queue] def getNumberInQ() = n // only used for helpers

  def isEmpty(): Boolean = n == 0

  def less(a: Int, b: Int)(implicit ord: Ordering[A]): Boolean = ord.lt(pq(a), pq(b))

  def greater(a: Int, b: Int)(implicit ord: Ordering[A]): Boolean = ord.gt(pq(a), pq(b))

  private def exchange(child: Int, parent: Int) {
    val parentVal = pq(parent)
    pq.update(parent, pq(child))
    pq.update(child, parentVal)
  }

  private def swim(k: Int, cmp: (Int, Int) => Boolean) {
    @tailrec
    def loop(i: Int, j: Int) {
      if (i > 1 && cmp(j, i)) {
        exchange(i, j)
        loop(j, j / (2))
      }
    }
    loop(k, k./(2))
  }

  private def sink(k: Int, cmp: (Int, Int) => Boolean) {
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

  def insert(key: A, cmp: (Int, Int) => Boolean): Unit = {
    n += 1
    pq.append(key)
    swim(n, cmp)
  }

  def pop(cmp: (Int, Int) => Boolean): Option[A] = {
    if (isEmpty) None else {
      exchange(1, n)
      val top = pq.remove(n)
      n = n - 1
      sink(1, cmp)
      Some(top)
    }
  }

  def toString(keys: Seq[A]): String = {
    val sb = new StringBuilder()
    for {
      s <- keys
      if (s != null)
    } sb.append(s" $s")
    sb.toString
  }

  def checkHeap(cmp: (Int, Int) => Boolean): Boolean = {
    def loop(k: Int): Boolean = {
      val n = getNumberInQ
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
