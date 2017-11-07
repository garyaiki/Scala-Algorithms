/** @see https://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
  * @see https://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html
  */
package org.gs.queue

import collection.mutable.ArrayBuffer
import math.Ordering
import scala.annotation.tailrec
import scala.reflect.ClassTag

/** Common code for IndexMinPQ, IndexMaxPQ
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor called by subclass
  * @tparam A keys are generic, ClassTag retains its type at runtime
  * @param nMax maximum number of elements
  * @param ord implicit ordering
  */
abstract class IndexPriorityQueue[A: ClassTag](nMax: Int)(implicit ord: Ordering[A]) {
  require(nMax >= 0, s"nMax:$nMax can't be negative")
  private var n = 0
  private val keys = new Array[A](nMax + 1)
  private val pq = new Array[Int](keys.size)
  private val qp = Array.fill[Int](keys.size)(-1)

  /** Are there any items in the queue */
  def isEmpty(): Boolean = n == 0

  /** Generic ordering for [[org.gs.queue.IndexMaxPQ]] */
  protected def less(a: Int, b: Int): Boolean = ord.lt(keys(pq(a)), keys(pq(b)))

  /** Generic ordering for [[org.gs.queue.IndexMinPQ]] */
  protected def greater(a: Int, b: Int): Boolean = ord.gt(keys(pq(a)), keys(pq(b)))

  private def rangeGuard(x: Int): Boolean = x match {
    case x if 0 to nMax contains x => true
    case _ => false
  }

  /** does q(i) exist? */
  def contains(i: Int): Boolean = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    qp(i) != -1
  }

  /** number of elements in Q */
  def size(): Int = n

  /** exchange parent and child elements in array */
  private def exchange(i: Int, j: Int): Unit = {
    val swap = pq(i)
    pq.update(i, pq(j))
    pq.update(j, swap)
    qp(pq(j)) = j
    qp(pq(i)) = i
  }

  /** move k above its parents while its value is larger */
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

  /** move k below the larger of its two children until its value is smaller */
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

  /** Add key to end of array then swim up to ordered position
    *
    * @param i index where key will be inserted if not already there
    * @param key generic element
    * @param cmp less for [[org.gs.queue.IndexMaxPQ]] greater for [[org.gs.queue.IndexMinPQ]]
    */
  protected def insert(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(!contains(i), s"index:$i is already in the priority queue")
    n += 1
    qp(i) = n
    pq(n) = i
    keys(i) = key
    swim(n, cmp)
  }

  /** returns index associated with min or max key */
  protected def index(): Int = {
    require(n > 0, s"n:$n priority queue underflow")
    pq(1)
  }

  /** returns min or max key */
  protected def topKey(): A = {
    require(n > 0, s"n:$n priority queue underflow")
    keys(pq(1))
  }

  /** returns min or max key and removes it from q */
  protected def delTop(cmp: (Int, Int) => Boolean): Int = {
    require(n > 0, s"n:$n priority queue underflow")
    val top = pq(1)
    exchange(1, n)
    n -= 1
    sink(1, cmp)
    qp(top) = -1
    pq(n + 1) = -1
    top
  }

  /** returns key associated with index */
  def keyOf(i: Int): A = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    keys(i)
  }

  /** Change key at index to new value, because it can be > or < current, it both swims and sinks
    *
    * @param i index
    * @param key value
    * @param cmp less for [[org.gs.queue.IndexMaxPQ]] greater for [[org.gs.queue.IndexMinPQ]]
    */
  protected def changeKey(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    keys(i) = key
    swim(qp(i), cmp)
    sink(qp(i), cmp)
  }

  /** Decrease key at index to new value, because it is < current, it both swims
    *
    * @param i index
    * @param key value
    */
  protected def decreaseKey(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    require(ord.compare(keys(i), key) > 0, s"Calling decreaseKey() with i:$i, key:$key would not strictly decrease the key")
    keys(i) = key
    swim(qp(i), cmp)
  }

  /** Increase key at index to new value, because it is > current, it sinks
    *
    * @param i index
    * @param key value
    * @param cmp less for [[org.gs.queue.IndexMaxPQ]] greater for [[org.gs.queue.IndexMinPQ]]
    */
  protected def increaseKey(i: Int, key: A, cmp: (Int, Int) => Boolean): Unit = {
    val r = ord.compare(keys(i), key)
    require(rangeGuard(i), s"i:$i not in 0 - nMax:$nMax")
    require(contains(i), s"index:$i is not in the priority queue")
    require(r < 0, s"Calling increaseKey() with i:$i, key:$key would not strictly increase the key")
    keys(i) = key
    sink(qp(i), cmp)
  }

  /** Remove key at index
    *
    * @param i index
    * @param cmp less for [[org.gs.queue.IndexMaxPQ]] greater for [[org.gs.queue.IndexMinPQ]]
    */
  protected def delete(i: Int, cmp: (Int, Int) => Boolean): Unit = {
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

  /** returns string of keys */
  override def toString(): String = {
    val sb = new StringBuilder()
    val size = pq.size
    for (i <- 1 until size) {
      val key = keys(pq(i))
      if (key != null) sb append(s" $key")
    }
    sb.toString.trim
  }

  /** returns keys */
  protected def getKeys(): IndexedSeq[A] = for (i <- 1 until nMax) yield keys(pq(i))

  /** check parent in position has left child at k * 2, right child at k * 2 + 1 */
  def checkHeap(cmp: (Int, Int) => Boolean): Boolean = {

    def loop(k: Int): Boolean = {
      if (k > n) true else {
        val left = 2 * k
        val right = 2 * k + 1
        if ((left <= n && cmp(k, left)) || (right <= n && cmp(k, right))) false else loop(left) && loop(right)
      }
    }
    loop(1)
  }
}
