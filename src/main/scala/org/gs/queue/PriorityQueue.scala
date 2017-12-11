package org.gs.queue

import scala.annotation.tailrec
import collection.mutable.ArrayBuffer
import math.Ordering

/** Common code for MaxPQ, MinPQ
  *
  * @tparam A keys are generic and ordered
  * @param pq queue, appends keys
	* @see [[https://algs4.cs.princeton.edu/24pq/MaxPQ.java.html]]
  * @see [[https://algs4.cs.princeton.edu/24pq/MinPQ.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
abstract class PriorityQueue[A](pq: ArrayBuffer[A]) {
  if (pq.isEmpty) pq.append(null.asInstanceOf[A]) else pq(0) = null.asInstanceOf[A] // don't use index 0
  
  /** number of elements in Q */
  def size(): Int = pq.length match {
    case i if i == 0 => 0
    case j if j > 0 => j - 1
  }

  private var n = 0

  private[queue] def getNumberInQ() = n // only used to check Q

  /** Are there any items in the queue */
  def isEmpty(): Boolean = n == 0

  /** Generic ordering for [[org.gs.queue.MaxPQ]] */
  def less(a: Int, b: Int)(implicit ord: Ordering[A]): Boolean = ord.lt(pq(a), pq(b))

  /** Generic ordering for [[org.gs.queue.MinPQ]] */
  def greater(a: Int, b: Int)(implicit ord: Ordering[A]): Boolean = ord.gt(pq(a), pq(b))

  /** exchange parent and child elements in array */
  private def exchange(child: Int, parent: Int) {
    val parentVal = pq(parent)
    pq.update(parent, pq(child))
    pq.update(child, parentVal)
  }

  /** move k above its parents while its value is larger */
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

  /** move k below the larger of its two children until its value is smaller */
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

  /** Add key to end of array then swim up to ordered position
   *
   * @param key generic element
   * @param cmp less for [[org.gs.queue.MaxPQ]] greater for [[org.gs.queue.MinPQ]]
   */
  def insert(key: A, cmp: (Int, Int) => Boolean): Unit = {
    n += 1
    pq.append(key)
    swim(n, cmp)
  }

  /** Remove max or min element
   *
   * @param cmp less for [[org.gs.queue.MaxPQ]] greater for [[org.gs.queue.MinPQ]]
   */
  def pop(cmp: (Int, Int) => Boolean): Option[A] = if (isEmpty) None else {
      exchange(1, n)
      val top = pq.remove(n)
      n = n - 1
      sink(1, cmp)
      Some(top)
  }

  /** returns string of keys */
  def toString(keys: Seq[A]): String = {
    val sb = new StringBuilder()
    keys foreach (s => if (s != null) sb append (s" $s"))
    sb.toString
  }

  /** check parent in position has left child at k * 2, right child at k * 2 + 1 */
  def checkHeap(cmp: (Int, Int) => Boolean): Boolean = {

    def loop(k: Int): Boolean = {
      val n = getNumberInQ
      if (k > n) true else {
        val left = 2 * k
        val right = 2 * k + 1
        if ((left <= n && cmp(k, left)) || (right <= n && cmp(k, right))) false
        else loop(left) && loop(right)
      }
    }
    loop(1)
  }
}
